package com.naterbobber.darkerdepths.worldgen.retrogen;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.chunk.ChunkAccess;

public interface IChunkPostProcessor {
    String getNbtTag();
    boolean requiresProcessing(ChunkAccess chunk);

    /**
     * @return TRUE if the chunk is completely finished. FALSE if it needs to resume next tick.
     */
    boolean processChunk(ServerLevel level, ChunkAccess chunk);

    /** Called if a chunk unloads mid-process so we can clean up memory */
    default void clearSuspendedState(ChunkPos pos) {}
}