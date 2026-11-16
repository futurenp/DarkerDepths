package com.naterbobber.darkerdepths.common.worldgen.retrogen;

import com.naterbobber.darkerdepths.common.config.DDConfig;
import com.naterbobber.darkerdepths.common.init.DDDataAttachments;
import com.naterbobber.darkerdepths.common.worldgen.retrogen.processors.HeatPropagationProcessor;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.LevelChunk;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

public class ChunkPostProcessManager {
    private static final List<IChunkPostProcessor> PROCESSORS = List.of(new HeatPropagationProcessor());

    private static final Map<ServerLevel, LinkedHashMap<ChunkPos, LevelChunk>> PROCESS_QUEUE = new HashMap<>();
    private static final Map<ServerLevel, LinkedHashSet<ChunkPos>> PENDING_CHUNKS = new HashMap<>();
    private static final Map<ServerLevel, ChunkPos> ACTIVE_CHUNKS = new HashMap<>();
    private static final LinkedHashMap<ServerLevel, QueueRefresh> QUEUE_REFRESHES = new LinkedHashMap<>();

    private static int ticksUntilQueueRefresh;
    private static int firstDimension;

    private record Candidate(ChunkPos pos, long distance) {
    }

    private static final class QueueRefresh {
        private final List<ChunkProcessingPriority.PlayerChunk> players;
        private final int radius;
        private final TreeSet<Candidate> candidates = new TreeSet<>(Comparator.comparingLong(Candidate::distance)
                .thenComparingInt(candidate -> candidate.pos().x)
                .thenComparingInt(candidate -> candidate.pos().z));
        private final LinkedHashMap<ChunkPos, LevelChunk> queue = new LinkedHashMap<>();
        private int remainingChunks;

        private QueueRefresh(ServerLevel level, int radius, int remainingChunks) {
            this.players = playerChunks(level);
            this.radius = radius;
            this.remainingChunks = remainingChunks;
        }
    }

    public static void handleChunkLoadData(ChunkAccess chunk, CompoundTag data) {
        for (var processor : PROCESSORS) {
            if (data.getBoolean(processor.getNbtTag())) {
                chunk.getData(DDDataAttachments.PENDING_PROCESSORS).add(processor.getNbtTag());
                chunk.setUnsaved(true);
            }
        }
    }

    public static void handleChunkEnterWorld(ServerLevel level, ChunkAccess chunk, boolean isNewChunk) {
        if (!(chunk instanceof LevelChunk levelChunk)) {
            return;
        }

        if (isNewChunk) {
            for (var processor : PROCESSORS) {
                if (processor.requiresProcessing(chunk)) {
                    chunk.getData(DDDataAttachments.PENDING_PROCESSORS).add(processor.getNbtTag());
                    chunk.setUnsaved(true);
                }
            }
        }

        var pending = chunk.getExistingDataOrNull(DDDataAttachments.PENDING_PROCESSORS.get());

        if (pending != null && !pending.isEmpty()) {
            PENDING_CHUNKS.computeIfAbsent(level, key -> new LinkedHashSet<>()).add(levelChunk.getPos());
        }
    }

    public static void processQueueTick(MinecraftServer server) {
        if (DDConfig.CONFIG.DISABLE_HEATABLE_BLOCK_BAKING.get()) {
            PROCESSORS.forEach(IChunkPostProcessor::clearSuspendedState);
            QUEUE_REFRESHES.clear();
            ACTIVE_CHUNKS.clear();
            ticksUntilQueueRefresh = 0;
            return;
        }

        long startTime = System.nanoTime();
        long budget = (long) (DDConfig.CONFIG.HEAT_BAKE_TIME_BUDGET_MS.get() * 1_000_000);
        long deadline = startTime + budget;

        refreshQueues(server, startTime + budget / 2);

        var levels = new ArrayList<ServerLevel>();
        server.getAllLevels().forEach(levels::add);

        if (levels.isEmpty()) {
            return;
        }

        int start = Math.floorMod(firstDimension++, levels.size());

        for (int i = 0; i < levels.size() && System.nanoTime() < deadline; i++) {
            processLevel(levels.get((start + i) % levels.size()), deadline);
        }
    }

    private static List<ChunkProcessingPriority.PlayerChunk> playerChunks(ServerLevel level) {
        var players = new ArrayList<ChunkProcessingPriority.PlayerChunk>();

        for (var player : level.players()) {
            var pos = player.chunkPosition();
            players.add(new ChunkProcessingPriority.PlayerChunk(pos.x, pos.z));
        }

        return players;
    }

    private static void refreshQueues(MinecraftServer server, long deadline) {
        if (QUEUE_REFRESHES.isEmpty()) {
            if (ticksUntilQueueRefresh-- > 0) {
                return;
            }

            int radius = server.getPlayerList().getSimulationDistance();

            for (var entry : PENDING_CHUNKS.entrySet()) {
                QUEUE_REFRESHES.put(entry.getKey(), new QueueRefresh(entry.getKey(), radius, entry.getValue().size()));
            }
        }

        while (!QUEUE_REFRESHES.isEmpty() && System.nanoTime() < deadline) {
            var entry = QUEUE_REFRESHES.firstEntry();
            var level = entry.getKey();
            var refresh = entry.getValue();

            if (!advanceQueueRefresh(level, refresh, deadline)) {
                return;
            }

            var active = ACTIVE_CHUNKS.get(level);

            if (active != null) {
                var chunk = refresh.queue.remove(active);

                if (chunk != null) {
                    refresh.queue.putFirst(active, chunk);
                } else {
                    clearActiveChunk(level);
                }
            }

            if (refresh.queue.isEmpty()) {
                PROCESS_QUEUE.remove(level);
            } else {
                PROCESS_QUEUE.put(level, refresh.queue);
            }

            QUEUE_REFRESHES.pollFirstEntry();
        }

        if (QUEUE_REFRESHES.isEmpty()) {
            ticksUntilQueueRefresh = 19;
        }
    }

    private static boolean advanceQueueRefresh(ServerLevel level, QueueRefresh refresh, long deadline) {
        var pending = PENDING_CHUNKS.get(level);

        while (System.nanoTime() < deadline) {
            if (refresh.remainingChunks > 0 && pending != null && !pending.isEmpty()) {
                var pos = pending.removeFirst();
                refresh.remainingChunks--;

                var chunk = level.getChunkSource().getChunkNow(pos.x, pos.z);
                var tags = chunk == null ? null : chunk.getExistingDataOrNull(DDDataAttachments.PENDING_PROCESSORS.get());

                if (tags == null || tags.isEmpty()) {
                    continue;
                }

                pending.addLast(pos);
                long distance = ChunkProcessingPriority.distance(pos.x, pos.z, refresh.players, refresh.radius);

                if (distance != Long.MAX_VALUE) {
                    refresh.candidates.add(new Candidate(pos, distance));
                }
            } else if (!refresh.candidates.isEmpty()) {
                refresh.remainingChunks = 0;
                var pos = refresh.candidates.pollFirst().pos();
                var chunk = level.getChunkSource().getChunkNow(pos.x, pos.z);
                var tags = chunk == null ? null : chunk.getExistingDataOrNull(DDDataAttachments.PENDING_PROCESSORS.get());

                if (tags != null && !tags.isEmpty()) {
                    refresh.queue.put(pos, chunk);
                }
            } else {
                return true;
            }
        }

        return false;
    }

    private static void clearActiveChunk(ServerLevel level) {
        var active = ACTIVE_CHUNKS.remove(level);

        if (active != null) {
            for (var processor : PROCESSORS) {
                processor.clearSuspendedState(level, active);
            }
        }
    }

    private static void processLevel(ServerLevel level, long deadline) {
        var queue = PROCESS_QUEUE.get(level);

        if (queue == null) {
            return;
        }

        int attempts = Math.min(queue.size(), 20);
        var players = playerChunks(level);
        int radius = level.getServer().getPlayerList().getSimulationDistance();

        for (int i = 0; i < attempts && System.nanoTime() < deadline; i++) {
            var entry = queue.pollFirstEntry();
            var chunk = entry.getValue();
            var pos = entry.getKey();

            if (level.getChunkSource().getChunkNow(pos.x, pos.z) != chunk) {
                if (pos.equals(ACTIVE_CHUNKS.get(level))) {
                    clearActiveChunk(level);
                }

                continue;
            }

            if (ChunkProcessingPriority.distance(pos.x, pos.z, players, radius) == Long.MAX_VALUE) {
                if (pos.equals(ACTIVE_CHUNKS.get(level))) {
                    clearActiveChunk(level);
                }

                continue;
            }

            var localChunks = getReadyArea(level, pos);

            if (localChunks == null) {
                if (pos.equals(ACTIVE_CHUNKS.get(level))) {
                    clearActiveChunk(level);
                }

                queue.putLast(pos, chunk);
                continue;
            }

            var tags = chunk.getExistingDataOrNull(DDDataAttachments.PENDING_PROCESSORS.get());

            if (tags == null || tags.isEmpty()) {
                continue;
            }

            for (var processor : PROCESSORS) {
                if (System.nanoTime() >= deadline) {
                    break;
                }

                if (tags.contains(processor.getNbtTag()) && processor.processChunk(level, chunk, localChunks, deadline)) {
                    tags.remove(processor.getNbtTag());
                    chunk.setUnsaved(true);
                }
            }

            if (!tags.isEmpty()) {
                ACTIVE_CHUNKS.put(level, pos);
                queue.putFirst(pos, chunk);
                return;
            }

            chunk.removeData(DDDataAttachments.PENDING_PROCESSORS.get());
            ACTIVE_CHUNKS.remove(level);
            var pending = PENDING_CHUNKS.get(level);

            if (pending != null) {
                pending.remove(pos);
            }
        }
    }

    public static void handleChunkUnload(ServerLevel level, ChunkPos pos) {
        var refresh = QUEUE_REFRESHES.get(level);

        if (refresh != null) {
            refresh.queue.remove(pos);
        }

        var pending = PENDING_CHUNKS.get(level);

        if (pending != null) {
            pending.remove(pos);

            if (pending.isEmpty()) {
                PENDING_CHUNKS.remove(level);
            }
        }

        if (pos.equals(ACTIVE_CHUNKS.get(level))) {
            ACTIVE_CHUNKS.remove(level);
        }

        var queue = PROCESS_QUEUE.get(level);

        if (queue != null) {
            queue.remove(pos);

            if (queue.isEmpty()) {
                PROCESS_QUEUE.remove(level);
            }
        }

        for (var processor : PROCESSORS) {
            processor.clearSuspendedState(level, pos);
        }
    }

    public static void handleChunkSaveData(ChunkAccess chunk, CompoundTag data) {
        var tags = chunk.getExistingDataOrNull(DDDataAttachments.PENDING_PROCESSORS.get());

        for (var processor : PROCESSORS) {
            var tag = processor.getNbtTag();

            if (tags != null && tags.contains(tag)) {
                data.putBoolean(tag, true);
            } else {
                data.remove(tag);
            }
        }
    }

    private static LevelChunk[][] getReadyArea(ServerLevel level, ChunkPos pos) {
        var chunks = new LevelChunk[3][3];

        for (int x = 0; x < 3; x++) {
            for (int z = 0; z < 3; z++) {
                var chunk = level.getChunkSource().getChunkNow(pos.x + x - 1, pos.z + z - 1);

                if (chunk == null) {
                    return null;
                }

                chunks[x][z] = chunk;
            }
        }

        return chunks;
    }

    public static void clearMemory() {
        PROCESS_QUEUE.clear();
        PENDING_CHUNKS.clear();
        ACTIVE_CHUNKS.clear();
        QUEUE_REFRESHES.clear();

        ticksUntilQueueRefresh = 0;
        PROCESSORS.forEach(IChunkPostProcessor::clearSuspendedState);
        firstDimension = 0;
    }
}
