package com.naterbobber.darkerdepths.worldgen.retrogen.processors;

import com.naterbobber.darkerdepths.block.DDBlockStateProperties;
import com.naterbobber.darkerdepths.block.generic.HeatableBlock;
import com.naterbobber.darkerdepths.config.DDConfig;
import com.naterbobber.darkerdepths.init.DDBlocks;
import com.naterbobber.darkerdepths.util.DDTags;
import com.naterbobber.darkerdepths.worldgen.retrogen.IChunkPostProcessor;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.FullChunkStatus;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.chunk.LevelChunkSection;
import net.neoforged.neoforge.common.util.BlockSnapshot;

import javax.annotation.Nullable;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;

public class HeatPropagationProcessor implements IChunkPostProcessor {

    private record HeatNode(BlockPos pos, int heat) {}

    private final Map<ChunkPos, Queue<HeatNode>> suspendedQueues = new ConcurrentHashMap<>();

    private static final int OPS_BUDGET_PER_TICK = DDConfig.CONFIG.HEAT_BAKE_BUDGET.get();

    @Override
    public String getNbtTag() {
        return "darkerdepths_needs_heat_bake";
    }

    @Override
    public boolean requiresProcessing(ChunkAccess chunk) {
        for (LevelChunkSection section : chunk.getSections()) {
            if (section.hasOnlyAir()) continue;

            if (section.getStates().maybeHas(state -> state.is(DDTags.Blocks.HEATABLE))) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean processChunk(ServerLevel level, ChunkAccess chunk) {
        ChunkPos chunkPos = chunk.getPos();

        ChunkAccess[][] localChunks = new ChunkAccess[3][3];
        for (int cx = -1; cx <= 1; cx++) {
            for (int cz = -1; cz <= 1; cz++) {
                localChunks[cx + 1][cz + 1] = level.getChunkSource().getChunkNow(chunkPos.x + cx, chunkPos.z + cz);
            }
        }

        Queue<HeatNode> queue = suspendedQueues.get(chunkPos);

        if (queue == null) {
            queue = buildInitialQueue(chunk);
        }

        int opsCompletedThisTick = 0;
        BlockPos.MutableBlockPos mutableNeighbor = new BlockPos.MutableBlockPos();

        while (!queue.isEmpty() && opsCompletedThisTick < OPS_BUDGET_PER_TICK) {
            HeatNode node = queue.poll();
            opsCompletedThisTick++;

            int targetHeat = HeatableBlock.calculateNewHeat(node.heat());
            if (targetHeat <= 0) continue;

            for (Direction direction : Direction.values()) {
                mutableNeighbor.setWithOffset(node.pos(), direction);

                int gridX = ((mutableNeighbor.getX() >> 4) - chunkPos.x) + 1;
                int gridZ = ((mutableNeighbor.getZ() >> 4) - chunkPos.z) + 1;


                if (gridX < 0 || gridX > 2 || gridZ < 0 || gridZ > 2) continue;

                ChunkAccess neighborChunk = localChunks[gridX][gridZ];
                if (neighborChunk == null) continue;


                BlockState neighborState = neighborChunk.getBlockState(mutableNeighbor);

                if (neighborState.is(DDTags.Blocks.HEATABLE) && neighborState.hasProperty(DDBlockStateProperties.HEAT_LEVEL)) {
                    int currentHeat = neighborState.getValue(DDBlockStateProperties.HEAT_LEVEL);

                    if (targetHeat > currentHeat) {
                        setBlockFast(level, mutableNeighbor, neighborState.setValue(DDBlockStateProperties.HEAT_LEVEL, targetHeat), Block.UPDATE_CLIENTS | Block.UPDATE_KNOWN_SHAPE | Block.UPDATE_NONE);
                        queue.add(new HeatNode(mutableNeighbor.immutable(), targetHeat));
                    }
                }
            }
        }

        if (queue.isEmpty()) {
            suspendedQueues.remove(chunkPos);
            return true;
        } else {
            suspendedQueues.put(chunkPos, queue);
            return false;
        }
    }

    @Override
    public void clearSuspendedState(ChunkPos pos) {
        suspendedQueues.remove(pos);
    }

    private Queue<HeatNode> buildInitialQueue(ChunkAccess chunk) {
        Queue<HeatNode> queue = new LinkedList<>();
        int startX = chunk.getPos().getMinBlockX();
        int startZ = chunk.getPos().getMinBlockZ();
        int minSectionIndex = chunk.getMinSection();

        for (int i = 0; i < chunk.getSections().length; i++) {
            LevelChunkSection section = chunk.getSections()[i];
            if (section.hasOnlyAir()) continue;

            boolean hasHeatProvider = section.getStates().maybeHas(state -> state.is(DDTags.Blocks.HEAT_PROVIDER));

            if (hasHeatProvider) {
                int sectionMinY = (minSectionIndex + i) * 16;

                for (int x = 0; x < 16; x++) {
                    for (int y = 0; y < 16; y++) {
                        for (int z = 0; z < 16; z++) {
                            BlockState currentState = section.getBlockState(x, y, z);
                            if (currentState.is(DDTags.Blocks.HEAT_PROVIDER)) {
                                queue.add(new HeatNode(new BlockPos(startX + x, sectionMinY + y, startZ + z), HeatableBlock.determineHeat(currentState)));
                            }
                        }
                    }
                }
            }
        }
        return queue;
    }

    private static void setBlockFast(ServerLevel level, BlockPos pos, BlockState oldBlockState, int flags) {
        if (level.isOutsideBuildHeight(pos)) {
            return;
        }

        LevelChunk levelchunk = level.getChunkAt(pos);
        pos = pos.immutable();

        BlockState newBlockState = levelchunk.setBlockState(pos, oldBlockState, false);

        if (newBlockState != null) {
            markAndNotifyBlock(level, pos, levelchunk, newBlockState, oldBlockState, flags);
        }
    }

    public static void markAndNotifyBlock(ServerLevel level, BlockPos blockPos, @Nullable LevelChunk levelchunk, BlockState newBlockState, BlockState oldBlockState, int flags) {
        BlockState currentState = level.getBlockState(blockPos);
        if (currentState == oldBlockState) {
            if (newBlockState != currentState) {
                level.setBlocksDirty(blockPos, newBlockState, currentState);
            }

            if (level.isClientSide || levelchunk != null && levelchunk.getFullStatus().isOrAfter(FullChunkStatus.BLOCK_TICKING)) {
                level.sendBlockUpdated(blockPos, newBlockState, oldBlockState, flags);
            }

            level.onBlockStateChange(blockPos, newBlockState, currentState);
            oldBlockState.onBlockStateChange(level, blockPos, newBlockState);
        }

    }
}