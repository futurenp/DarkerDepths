package com.naterbobber.darkerdepths.events;

import com.naterbobber.darkerdepths.DarkerDepths;
import com.naterbobber.darkerdepths.worldgen.retrogen.ChunkPostProcessManager;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.server.level.ServerLevel;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.CommandEvent;
import net.neoforged.neoforge.event.level.ChunkDataEvent;
import net.neoforged.neoforge.event.level.ChunkEvent;
import net.neoforged.neoforge.event.server.ServerAboutToStartEvent;
import net.neoforged.neoforge.event.tick.LevelTickEvent;

@EventBusSubscriber(modid = DarkerDepths.MOD_ID)
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
    public static void onLevelTick(LevelTickEvent.Post event) {
        if (!event.getLevel().isClientSide() && event.getLevel() instanceof ServerLevel serverLevel) {
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