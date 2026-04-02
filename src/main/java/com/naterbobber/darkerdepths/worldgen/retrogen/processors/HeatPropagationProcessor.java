package com.naterbobber.darkerdepths.worldgen.retrogen.processors;

import com.naterbobber.darkerdepths.block.DDBlockStateProperties;
import com.naterbobber.darkerdepths.block.generic.HeatableBlock;
import com.naterbobber.darkerdepths.util.DDTags;
import com.naterbobber.darkerdepths.worldgen.retrogen.IChunkPostProcessor;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.LevelChunkSection;

public class HeatPropagationProcessor implements IChunkPostProcessor {

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
    public void processChunk(ServerLevel level, ChunkAccess chunk) {
        ChunkPos chunkPos = chunk.getPos();
        int startX = chunkPos.getMinBlockX();
        int startZ = chunkPos.getMinBlockZ();

        LevelChunkSection[] sections = chunk.getSections();
        int minSectionIndex = chunk.getMinSection();

        for (int i = 0; i < sections.length; i++) {
            LevelChunkSection section = sections[i];
            if (section.hasOnlyAir()) continue;

            boolean hasHeatProvider = section.getStates().maybeHas(state -> state.is(DDTags.Blocks.HEAT_PROVIDER));
            if (!hasHeatProvider) continue;

            int sectionMinY = (minSectionIndex + i) * 16;

            for (int x = 0; x < 16; x++) {
                for (int y = 0; y < 16; y++) {
                    for (int z = 0; z < 16; z++) {
                        BlockState currentState = section.getBlockState(x, y, z);

                        if (currentState.is(DDTags.Blocks.HEAT_PROVIDER)) {
                            int worldX = startX + x;
                            int worldY = sectionMinY + y;
                            int worldZ = startZ + z;
                            BlockPos currentPos = new BlockPos(worldX, worldY, worldZ);

                            pushHeatOutward(level, currentPos, HeatableBlock.determineHeat(currentState));
                        }
                    }
                }
            }
        }
    }

    private void pushHeatOutward(WorldGenLevel level, BlockPos sourcePos, int heatLevelToPass) {
        if (heatLevelToPass <= 0) return;

        for (Direction direction : Direction.values()) {
            BlockPos neighborPos = sourcePos.relative(direction);

            if (level instanceof ServerLevel serverLevel && !serverLevel.isLoaded(neighborPos)) {
                continue;
            }

            BlockState neighborState = level.getBlockState(neighborPos);

            if (neighborState.is(DDTags.Blocks.HEATABLE)) {
                if(!neighborState.hasProperty(DDBlockStateProperties.HEAT_LEVEL)) continue;

                int currentHeat = neighborState.getValue(DDBlockStateProperties.HEAT_LEVEL);

                if (heatLevelToPass > currentHeat) {
                    level.setBlock(neighborPos, neighborState.setValue(DDBlockStateProperties.HEAT_LEVEL, HeatableBlock.calculateNewHeat(heatLevelToPass)), Block.UPDATE_CLIENTS);
                    pushHeatOutward(level, neighborPos, heatLevelToPass - 1);
                }
            }
        }
    }
}