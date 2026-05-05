package com.naterbobber.darkerdepths.worldgen.retrogen;

import com.naterbobber.darkerdepths.config.DDConfig;
import com.naterbobber.darkerdepths.worldgen.retrogen.processors.HeatPropagationProcessor;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.ChunkAccess;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ChunkPostProcessManager {

    private static final List<IChunkPostProcessor> PROCESSORS = List.of(new HeatPropagationProcessor());

    private static final Map<ResourceKey<Level>, Queue<ChunkPos>> PROCESS_QUEUE = new ConcurrentHashMap<>();
    private static final Map<ResourceKey<Level>, Map<ChunkPos, Set<String>>> PENDING_SAVE_TAGS = new ConcurrentHashMap<>();

    public static void handleChunkLoadData(ServerLevel level, ChunkPos pos, CompoundTag data) {
        if (DDConfig.DISABLE_HEATABLE_BLOCK_BAKING.get()) return;
        ResourceKey<Level> dim = level.dimension();
        for (IChunkPostProcessor processor : PROCESSORS) {
            if (data.getBoolean(processor.getNbtTag())) {
                addPendingTag(dim, pos, processor.getNbtTag());
            }
        }
    }

    public static void handleChunkEnterWorld(ServerLevel level, ChunkAccess chunk, boolean isNewChunk) {
        if (DDConfig.DISABLE_HEATABLE_BLOCK_BAKING.get()) return;
        ResourceKey<Level> dim = level.dimension();
        ChunkPos pos = chunk.getPos();

        if (isNewChunk) {
            for (IChunkPostProcessor processor : PROCESSORS) {
                if (processor.requiresProcessing(chunk)) {
                    addPendingTag(dim, pos, processor.getNbtTag());
                    enqueue(dim, pos);
                }
            }
        } else {
            Map<ChunkPos, Set<String>> dimTags = PENDING_SAVE_TAGS.get(dim);
            if (dimTags != null && dimTags.containsKey(pos)) {
                enqueue(dim, pos);
            }
        }
    }

    public static void processQueueTick(ServerLevel level) {
        if (DDConfig.DISABLE_HEATABLE_BLOCK_BAKING.get()) return;
        ResourceKey<Level> dimension = level.dimension();
        Queue<ChunkPos> queue = PROCESS_QUEUE.get(dimension);

        if (queue == null || queue.isEmpty()) return;

        ChunkPos targetPos = null;
        int maxAttempts = Math.min(queue.size(), 20);

        for (int i = 0; i < maxAttempts; i++) {
            ChunkPos polled = queue.poll();
            if (polled == null) continue;

            if (isAreaReady(level, polled)) {
                targetPos = polled;
                break;
            } else {
                queue.add(polled);
            }
        }

        if (targetPos == null) return;

        Map<ChunkPos, Set<String>> dimTags = PENDING_SAVE_TAGS.get(dimension);
        Set<String> chunkTags = dimTags != null ? dimTags.get(targetPos) : null;

        if (chunkTags != null && !chunkTags.isEmpty()) {
            ChunkAccess chunk = level.getChunk(targetPos.x, targetPos.z);
            Iterator<String> iterator = chunkTags.iterator();

            while (iterator.hasNext()) {
                String tag = iterator.next();
                for (IChunkPostProcessor processor : PROCESSORS) {
                    if (processor.getNbtTag().equals(tag)) {
                        if (processor.processChunk(level, chunk)) {
                            iterator.remove();
                        }
                    }
                }
            }

            if (chunkTags.isEmpty()) {
                dimTags.remove(targetPos);
            } else {
                queue.add(targetPos);
            }
            chunk.setUnsaved(true);
        }
    }

    public static void handleChunkUnload(ServerLevel level, ChunkPos pos) {
        ResourceKey<Level> dim = level.dimension();
        Queue<ChunkPos> queue = PROCESS_QUEUE.get(dim);
        if (queue != null) queue.remove(pos);

        for (IChunkPostProcessor processor : PROCESSORS) {
            processor.clearSuspendedState(pos);
        }
    }

    public static void handleChunkSaveData(ServerLevel level, ChunkPos pos, CompoundTag data) {
        Map<ChunkPos, Set<String>> dimTags = PENDING_SAVE_TAGS.get(level.dimension());
        Set<String> activeTags = dimTags != null ? dimTags.get(pos) : null;

        for (IChunkPostProcessor processor : PROCESSORS) {
            String tag = processor.getNbtTag();
            if (activeTags != null && activeTags.contains(tag)) {
                data.putBoolean(tag, true);
            } else {
                data.remove(tag);
            }
        }

        if (dimTags != null && activeTags != null && !level.hasChunk(pos.x, pos.z)) {
            dimTags.remove(pos);
        }
    }

    private static void addPendingTag(ResourceKey<Level> dim, ChunkPos pos, String tag) {
        PENDING_SAVE_TAGS.computeIfAbsent(dim, k -> new ConcurrentHashMap<>())
                .computeIfAbsent(pos, k -> ConcurrentHashMap.newKeySet()).add(tag);
    }

    private static void enqueue(ResourceKey<Level> dim, ChunkPos pos) {
        PROCESS_QUEUE.computeIfAbsent(dim, k -> new ConcurrentLinkedQueue<>()).add(pos);
    }

    private static boolean isAreaReady(ServerLevel level, ChunkPos p) {
        return level.hasChunk(p.x, p.z) &&
                level.hasChunk(p.x + 1, p.z) && level.hasChunk(p.x - 1, p.z) &&
                level.hasChunk(p.x, p.z + 1) && level.hasChunk(p.x, p.z - 1);
    }

    public static void clearMemory() {
        PROCESS_QUEUE.clear();
        PENDING_SAVE_TAGS.clear();
    }
}