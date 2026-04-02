package com.naterbobber.darkerdepths.worldgen.retrogen;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.chunk.ChunkAccess;

public interface IChunkPostProcessor {
    String getNbtTag();

    boolean requiresProcessing(ChunkAccess chunk);

    void processChunk(ServerLevel level, ChunkAccess chunk);
}