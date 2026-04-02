package com.naterbobber.darkerdepths.events;

import com.naterbobber.darkerdepths.DarkerDepths;
import com.naterbobber.darkerdepths.worldgen.retrogen.ChunkPostProcessManager;
import com.naterbobber.darkerdepths.worldgen.retrogen.IChunkPostProcessor;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.level.ChunkDataEvent;
import net.neoforged.neoforge.event.level.ChunkEvent;
import net.neoforged.neoforge.event.server.ServerAboutToStartEvent;
import net.neoforged.neoforge.event.tick.LevelTickEvent;

import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

@EventBusSubscriber(modid = DarkerDepths.MOD_ID)
public class ChunkPostProcessEventHandler {

    private static final Map<ResourceKey<Level>, Set<ChunkPos>> KNOWN_DISK_CHUNKS = new ConcurrentHashMap<>();
    private static final Map<ResourceKey<Level>, Queue<ChunkPos>> PROCESS_QUEUE = new ConcurrentHashMap<>();
    private static final Map<ResourceKey<Level>, Map<ChunkPos, Set<String>>> PENDING_SAVE_TAGS = new ConcurrentHashMap<>();

    @SubscribeEvent
    public static void onChunkLoad(ChunkDataEvent.Load event) {
        if (!(event.getLevel() instanceof ServerLevel serverLevel)) return;

        ResourceKey<Level> dim = serverLevel.dimension();
        ChunkPos pos = event.getChunk().getPos();

        KNOWN_DISK_CHUNKS.computeIfAbsent(dim, k -> ConcurrentHashMap.newKeySet()).add(pos);
        CompoundTag data = event.getData();

        for (IChunkPostProcessor processor : ChunkPostProcessManager.PROCESSORS) {
            String tag = processor.getNbtTag();
            if (data.getBoolean(tag)) {
                markForProcessing(dim, pos, tag);
            }
        }
    }

    @SubscribeEvent
    public static void onChunkEnterWorld(ChunkEvent.Load event) {
        if (!(event.getLevel() instanceof ServerLevel serverLevel)) return;

        ResourceKey<Level> dim = serverLevel.dimension();
        ChunkPos pos = event.getChunk().getPos();
        Set<ChunkPos> diskChunks = KNOWN_DISK_CHUNKS.get(dim);

        if (diskChunks != null && diskChunks.remove(pos)) {
            return;
        }

        ChunkAccess chunk = event.getChunk();
        boolean requiresSave = false;

        for (IChunkPostProcessor processor : ChunkPostProcessManager.PROCESSORS) {
            if (processor.requiresProcessing(chunk)) {
                markForProcessing(dim, pos, processor.getNbtTag());
                requiresSave = true;
            }
        }

        if (requiresSave) {
            chunk.setUnsaved(true);
        }
    }

    @SubscribeEvent
    public static void onLevelTick(LevelTickEvent.Post event) {
        if (event.getLevel().isClientSide()) return;

        ServerLevel serverLevel = (ServerLevel) event.getLevel();
        ResourceKey<Level> dimension = serverLevel.dimension();

        Queue<ChunkPos> queue = PROCESS_QUEUE.get(dimension);
        if (queue == null || queue.isEmpty()) return;

        ChunkPos pos = queue.poll();

        if (pos != null) {
            Map<ChunkPos, Set<String>> dimSaveTags = PENDING_SAVE_TAGS.get(dimension);

            Set<String> tagsToProcess = dimSaveTags != null ? dimSaveTags.remove(pos) : null;

            if (tagsToProcess != null && !tagsToProcess.isEmpty() && serverLevel.hasChunk(pos.x, pos.z)) {
                ChunkAccess chunk = serverLevel.getChunk(pos.x, pos.z);

                for (IChunkPostProcessor processor : ChunkPostProcessManager.PROCESSORS) {
                    if (tagsToProcess.contains(processor.getNbtTag())) {
                        processor.processChunk(serverLevel, chunk);
                    }
                }

                chunk.setUnsaved(true);
            }
        }
    }

    @SubscribeEvent
    public static void onChunkSave(ChunkDataEvent.Save event) {
        if (!(event.getLevel() instanceof ServerLevel serverLevel)) return;

        ResourceKey<Level> dimension = serverLevel.dimension();
        Map<ChunkPos, Set<String>> dimSaveTags = PENDING_SAVE_TAGS.get(dimension);

        ChunkPos pos = event.getChunk().getPos();
        Set<String> activeTagsForChunk = dimSaveTags != null ? dimSaveTags.get(pos) : null;

        for (IChunkPostProcessor processor : ChunkPostProcessManager.PROCESSORS) {
            String tag = processor.getNbtTag();
            if (activeTagsForChunk != null && activeTagsForChunk.contains(tag)) {
                event.getData().putBoolean(tag, true);
            } else {
                event.getData().remove(tag);
            }
        }
    }

    @SubscribeEvent
    public static void onServerStart(ServerAboutToStartEvent event) {
        PROCESS_QUEUE.clear();
        KNOWN_DISK_CHUNKS.clear();
        PENDING_SAVE_TAGS.clear();
    }

    private static void markForProcessing(ResourceKey<Level> dim, ChunkPos pos, String tag) {
        PROCESS_QUEUE.computeIfAbsent(dim, k -> new ConcurrentLinkedQueue<>()).add(pos);
        PENDING_SAVE_TAGS.computeIfAbsent(dim, k -> new ConcurrentHashMap<>())
                .computeIfAbsent(pos, k -> ConcurrentHashMap.newKeySet()).add(tag);
    }
}