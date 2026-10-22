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
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ChunkPostProcessManager {
    private static final List<IChunkPostProcessor> PROCESSORS = List.of(new HeatPropagationProcessor());

    private static final Map<ServerLevel, LinkedHashMap<ChunkPos, LevelChunk>> PROCESS_QUEUE = new HashMap<>();
    private static int firstDimension;

    public static void handleChunkLoadData(ChunkAccess chunk, CompoundTag data) {
        for (var processor : PROCESSORS) {
            if (data.getBoolean(processor.getNbtTag())) {
                chunk.getData(DDDataAttachments.PENDING_PROCESSORS).add(processor.getNbtTag());
                chunk.setUnsaved(true);
            }
        }
    }

    public static void handleChunkEnterWorld(ServerLevel level, ChunkAccess chunk, boolean isNewChunk) {
        if (!(chunk instanceof LevelChunk levelChunk)) return;
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
            PROCESS_QUEUE.computeIfAbsent(level, key -> new LinkedHashMap<>()).put(chunk.getPos(), levelChunk);
        }
    }

    public static void processQueueTick(MinecraftServer server) {
        if (DDConfig.CONFIG.DISABLE_HEATABLE_BLOCK_BAKING.get()) {
            PROCESSORS.forEach(IChunkPostProcessor::clearSuspendedState);
            return;
        }
        long deadline = System.nanoTime() + (long) (DDConfig.CONFIG.HEAT_BAKE_TIME_BUDGET_MS.get() * 1_000_000);
        var levels = new ArrayList<ServerLevel>();
        server.getAllLevels().forEach(levels::add);
        if (levels.isEmpty()) return;

        int start = Math.floorMod(firstDimension++, levels.size());
        for (int i = 0; i < levels.size() && System.nanoTime() < deadline; i++) {
            processLevel(levels.get((start + i) % levels.size()), deadline);
        }
    }

    private static void processLevel(ServerLevel level, long deadline) {
        var queue = PROCESS_QUEUE.get(level);
        if (queue == null) return;
        int attempts = Math.min(queue.size(), 20);

        for (int i = 0; i < attempts && System.nanoTime() < deadline; i++) {
            var entry = queue.pollFirstEntry();
            var chunk = entry.getValue();
            var pos = entry.getKey();
            if (level.getChunkSource().getChunkNow(pos.x, pos.z) != chunk) continue;
            var localChunks = getReadyArea(level, pos);
            if (localChunks == null) {
                queue.putLast(pos, chunk);
                continue;
            }

            var tags = chunk.getExistingDataOrNull(DDDataAttachments.PENDING_PROCESSORS.get());
            if (tags == null || tags.isEmpty()) continue;
            for (var processor : PROCESSORS) {
                if (System.nanoTime() >= deadline) break;
                if (tags.contains(processor.getNbtTag()) && processor.processChunk(level, chunk, localChunks, deadline)) {
                    tags.remove(processor.getNbtTag());
                    chunk.setUnsaved(true);
                }
            }
            if (!tags.isEmpty()) {
                queue.putFirst(pos, chunk);
                return;
            }
            chunk.removeData(DDDataAttachments.PENDING_PROCESSORS.get());
        }
    }

    public static void handleChunkUnload(ServerLevel level, ChunkPos pos) {
        var queue = PROCESS_QUEUE.get(level);
        if (queue != null) {
            queue.remove(pos);
            if (queue.isEmpty()) PROCESS_QUEUE.remove(level);
        }
        for (var processor : PROCESSORS) {
            processor.clearSuspendedState(level, pos);
        }
    }

    public static void handleChunkSaveData(ChunkAccess chunk, CompoundTag data) {
        var tags = chunk.getExistingDataOrNull(DDDataAttachments.PENDING_PROCESSORS.get());
        for (var processor : PROCESSORS) {
            var tag = processor.getNbtTag();
            if (tags != null && tags.contains(tag)) data.putBoolean(tag, true);
            else data.remove(tag);
        }
    }

    private static LevelChunk[][] getReadyArea(ServerLevel level, ChunkPos pos) {
        var chunks = new LevelChunk[3][3];
        for (int x = 0; x < 3; x++) {
            for (int z = 0; z < 3; z++) {
                var chunk = level.getChunkSource().getChunkNow(pos.x + x - 1, pos.z + z - 1);
                if (chunk == null) return null;
                chunks[x][z] = chunk;
            }
        }
        return chunks;
    }

    public static void clearMemory() {
        PROCESS_QUEUE.clear();
        PROCESSORS.forEach(IChunkPostProcessor::clearSuspendedState);
        firstDimension = 0;
    }
}
