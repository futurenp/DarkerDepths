package com.naterbobber.darkerdepths.common.worldgen.retrogen;

import com.naterbobber.darkerdepths.common.compat.DDCompat;
import com.naterbobber.darkerdepths.common.compat.SableCompat;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.FullChunkStatus;
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
    boolean processChunk(ServerLevel level, LevelChunk chunk, LevelChunk[][] localChunks, long deadline);

    /** Called if a chunk unloads mid-process so we can clean up memory */
    default void clearSuspendedState(ServerLevel level, ChunkPos pos) {}

    default void clearSuspendedState() {}

    default void setBlockFast(ServerLevel level, LevelChunk levelChunk, BlockPos pos, BlockState state, int flags) {
        if (level.isOutsideBuildHeight(pos)) {
            return;
        }

        pos = pos.immutable();

        if(DDCompat.SABLE.isLoaded()) {
            SableCompat.beginSuppression(levelChunk, pos);
        }

        var previousState = setStateWithSuppression(levelChunk, pos, state, flags);
        if (previousState != null) {
            markAndNotifyBlock(level, pos, levelChunk, previousState, state, flags);
        }
    }

    private BlockState setStateWithSuppression(LevelChunk chunk, BlockPos pos, BlockState state, int flags) {
        try {
            return chunk.setBlockState(pos, state, (flags & 64) != 0);
        } finally {
            if(DDCompat.SABLE.isLoaded()) {
                SableCompat.endSuppression();
            }
        }

    }

    /** Simplified markAndNotify block, also needed to avoid conflict with Lithium's mixins */
    default void markAndNotifyBlock(ServerLevel level, BlockPos blockPos, LevelChunk levelChunk, BlockState previousState, BlockState newState, int flags) {
        var currentState = levelChunk.getBlockState(blockPos);
        if (currentState == newState) {
            if (previousState != currentState) {
                level.setBlocksDirty(blockPos, previousState, currentState);
            }

            if (level.isClientSide || levelChunk != null && levelChunk.getFullStatus().isOrAfter(FullChunkStatus.BLOCK_TICKING)) {
                level.sendBlockUpdated(blockPos, previousState, newState, flags);
            }

            level.onBlockStateChange(blockPos, previousState, currentState);
            newState.onBlockStateChange(level, blockPos, previousState);
        }

    }
}
