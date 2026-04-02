package com.naterbobber.darkerdepths.worldgen.retrogen.processors;

import com.naterbobber.darkerdepths.block.DDBlockStateProperties;
import com.naterbobber.darkerdepths.block.generic.HeatableBlock;
import com.naterbobber.darkerdepths.config.DDConfig;
import com.naterbobber.darkerdepths.util.DDTags;
import com.naterbobber.darkerdepths.worldgen.retrogen.IChunkPostProcessor;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.LevelChunkSection;

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

        Queue<HeatNode> queue = suspendedQueues.get(chunkPos);

        if (queue == null) {
            queue = buildInitialQueue(chunk);
        }

        int opsCompletedThisTick = 0;

        while (!queue.isEmpty() && opsCompletedThisTick < OPS_BUDGET_PER_TICK) {
            HeatNode node = queue.poll();
            opsCompletedThisTick++;

            int targetHeat = HeatableBlock.calculateNewHeat(node.heat());
            if (targetHeat <= 0) continue;

            for (Direction direction : Direction.values()) {
                BlockPos neighborPos = node.pos().relative(direction);

                if (!level.isLoaded(neighborPos)) continue;

                BlockState neighborState = level.getBlockState(neighborPos);

                if (neighborState.is(DDTags.Blocks.HEATABLE) && neighborState.hasProperty(DDBlockStateProperties.HEAT_LEVEL)) {
                    int currentHeat = neighborState.getValue(DDBlockStateProperties.HEAT_LEVEL);

                    if (targetHeat > currentHeat) {
                        level.setBlock(neighborPos, neighborState.setValue(DDBlockStateProperties.HEAT_LEVEL, targetHeat), Block.UPDATE_CLIENTS | Block.UPDATE_KNOWN_SHAPE | Block.UPDATE_NONE);
                        queue.add(new HeatNode(neighborPos, targetHeat));
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
}