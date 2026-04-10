package com.naterbobber.darkerdepths.events;

import com.naterbobber.darkerdepths.DarkerDepths;
import com.naterbobber.darkerdepths.worldgen.retrogen.ChunkPostProcessManager;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.level.ChunkDataEvent;
import net.minecraftforge.event.level.ChunkEvent;
import net.minecraftforge.event.server.ServerAboutToStartEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = DarkerDepths.MOD_ID)
public class ChunkPostProcessEventHandler {

    @SubscribeEvent
    public static void onChunkLoad(ChunkDataEvent.Load event) {
        if (event.getLevel() instanceof ServerLevel serverLevel) {
            ChunkPostProcessManager.handleChunkLoadData(serverLevel, event.getChunk().getPos(), event.getData());
        }
    }

    @SubscribeEvent
    public static void onChunkEnterWorld(ChunkEvent.Load event) {
        if (event.getLevel() instanceof ServerLevel serverLevel) {
            ChunkPostProcessManager.handleChunkEnterWorld(serverLevel, event.getChunk(), event.isNewChunk());
        }
    }

    @SubscribeEvent
    public static void onLevelTick(TickEvent.LevelTickEvent event) {
        if (event.phase == TickEvent.Phase.START) {
            return;
        }

        if (!event.level.isClientSide() && event.level instanceof ServerLevel serverLevel) {
            ChunkPostProcessManager.processQueueTick(serverLevel);
        }
    }

    @SubscribeEvent
    public static void onChunkSave(ChunkDataEvent.Save event) {
        if (event.getLevel() instanceof ServerLevel serverLevel) {
            ChunkPostProcessManager.handleChunkSaveData(serverLevel, event.getChunk().getPos(), event.getData());
        }
    }

    @SubscribeEvent
    public static void onServerStart(ServerAboutToStartEvent event) {
        ChunkPostProcessManager.clearMemory();
    }

    @SubscribeEvent
    public static void onChunkUnload(ChunkEvent.Unload event) {
        if (event.getLevel() instanceof ServerLevel serverLevel) {
            ChunkPostProcessManager.handleChunkUnload(serverLevel, event.getChunk().getPos());
        }
    }
}