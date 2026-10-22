package com.naterbobber.darkerdepths.common.events;

import com.naterbobber.darkerdepths.DarkerDepths;
import com.naterbobber.darkerdepths.common.worldgen.retrogen.ChunkPostProcessManager;
import net.minecraft.server.level.ServerLevel;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.level.ChunkDataEvent;
import net.neoforged.neoforge.event.level.ChunkEvent;
import net.neoforged.neoforge.event.server.ServerAboutToStartEvent;
import net.neoforged.neoforge.event.server.ServerStoppedEvent;
import net.neoforged.neoforge.event.tick.ServerTickEvent;

@EventBusSubscriber(modid = DarkerDepths.MOD_ID)
public class ChunkPostProcessEventHandler {

    @SubscribeEvent
    public static void onChunkLoad(ChunkDataEvent.Load event) {
        ChunkPostProcessManager.handleChunkLoadData(event.getChunk(), event.getData());
    }

    @SubscribeEvent
    public static void onChunkEnterWorld(ChunkEvent.Load event) {
        if (event.getLevel() instanceof ServerLevel serverLevel) {
            ChunkPostProcessManager.handleChunkEnterWorld(serverLevel, event.getChunk(), event.isNewChunk());
        }
    }

    @SubscribeEvent
    public static void onServerTick(ServerTickEvent.Post event) {
        ChunkPostProcessManager.processQueueTick(event.getServer());
    }

    @SubscribeEvent
    public static void onChunkSave(ChunkDataEvent.Save event) {
        ChunkPostProcessManager.handleChunkSaveData(event.getChunk(), event.getData());
    }

    @SubscribeEvent
    public static void onServerStart(ServerAboutToStartEvent event) {
        ChunkPostProcessManager.clearMemory();
    }

    @SubscribeEvent
    public static void onServerStop(ServerStoppedEvent event) {
        ChunkPostProcessManager.clearMemory();
    }

    @SubscribeEvent
    public static void onChunkUnload(ChunkEvent.Unload event) {
        if (event.getLevel() instanceof ServerLevel serverLevel) {
            ChunkPostProcessManager.handleChunkUnload(serverLevel, event.getChunk().getPos());
        }
    }
}
