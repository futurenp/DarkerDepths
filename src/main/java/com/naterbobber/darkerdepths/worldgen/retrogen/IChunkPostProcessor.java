package com.naterbobber.darkerdepths.worldgen.retrogen;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.LevelChunk;

public interface IChunkPostProcessor {
    String getNbtTag();
    boolean requiresProcessing(ChunkAccess chunk);

    /**
     * @return TRUE if the chunk is completely finished. FALSE if it needs to resume next tick.
     */
    boolean processChunk(ServerLevel level, ChunkAccess chunk);

    /** Called if a chunk unloads mid-process so we can clean up memory */
    default void clearSuspendedState(ChunkPos pos) {}


    /** Returns a 3x3 array of the local chunks **/
    default ChunkAccess[][] getLocalChunks(ServerLevel level, ChunkAccess chunk) {
        ChunkPos chunkPos = chunk.getPos();

        ChunkAccess[][] localChunks = new ChunkAccess[3][3];
        for (int cx = -1; cx <= 1; cx++) {
            for (int cz = -1; cz <= 1; cz++) {
                localChunks[cx + 1][cz + 1] = level.getChunkSource().getChunkNow(chunkPos.x + cx, chunkPos.z + cz);
            }
        }

        return localChunks;
    }

    /** More optimized version of setBlock for process use **/
    default void setBlockFast(ServerLevel level, BlockPos pos, BlockState state, int flags) {
        if (level.isOutsideBuildHeight(pos)) {
            return;
        }

        LevelChunk levelchunk = level.getChunkAt(pos);
        pos = pos.immutable();

        BlockState blockstate = levelchunk.setBlockState(pos, state, (flags & 64) != 0);

        if (blockstate != null) {
            level.markAndNotifyBlock(pos, levelchunk, blockstate, state, flags, 512);
        }
    }
}