package com.naterbobber.darkerdepths.worldgen.feature.features;

import com.mojang.serialization.Codec;
import com.naterbobber.darkerdepths.block.DDBlockStateProperties;
import com.naterbobber.darkerdepths.block.generic.IHeatableBlock;
import com.naterbobber.darkerdepths.util.DDTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.LevelChunkSection;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class DarkslateHeatBakeFeature extends Feature<NoneFeatureConfiguration> {

    public DarkslateHeatBakeFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    // Note: If you are using Retrogen exclusively, you can actually delete
    // the place() method entirely and just use applyBakeToChunk!
    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        return false;
    }

    public static void applyBakeToChunk(ServerLevel level, ChunkAccess chunk) {
        ChunkPos chunkPos = chunk.getPos();
        int startX = chunkPos.getMinBlockX();
        int startZ = chunkPos.getMinBlockZ();

        // Get the array of sections and the starting index (usually -4 for Overworld)
        LevelChunkSection[] sections = chunk.getSections();
        int minSectionIndex = chunk.getMinSection();

        for (int i = 0; i < sections.length; i++) {
            LevelChunkSection section = sections[i];

            // Instantly skip empty air/cave sections
            if (section.hasOnlyAir()) continue;

            // Does this 16x16x16 cube contain ANY heat providers?
            boolean hasHeatProvider = section.getStates().maybeHas(state -> state.is(DDTags.Blocks.HEAT_PROVIDER));

            if (!hasHeatProvider) continue;

            // CALCULATE THE Y LEVEL: (minSectionIndex + arrayIndex) * 16
            int sectionMinY = (minSectionIndex + i) * 16;

            // We only run this loop inside sections that we KNOW have heat providers
            for (int x = 0; x < 16; x++) {
                for (int y = 0; y < 16; y++) {
                    for (int z = 0; z < 16; z++) {

                        BlockState currentState = section.getBlockState(x, y, z);

                        if (currentState.is(DDTags.Blocks.HEAT_PROVIDER)) {
                            int worldX = startX + x;
                            int worldY = sectionMinY + y;
                            int worldZ = startZ + z;
                            BlockPos currentPos = new BlockPos(worldX, worldY, worldZ);

                            pushHeatOutward(level, currentPos, IHeatableBlock.determineHeat(currentState));
                        }
                    }
                }
            }
        }
    }

    private static void pushHeatOutward(WorldGenLevel level, BlockPos sourcePos, int heatLevelToPass) {
        if (heatLevelToPass <= 0) return;

        for (Direction direction : Direction.values()) {
            BlockPos neighborPos = sourcePos.relative(direction);

            // OPTIMIZATION 4 & SAFETY FIX: Prevent Cascading Chunk Loads!
            // If the neighbor block is across a chunk border into an unloaded chunk, ABORT.
            if (level instanceof ServerLevel serverLevel && !serverLevel.isLoaded(neighborPos)) {
                continue;
            }

            BlockState neighborState = level.getBlockState(neighborPos);

            if (neighborState.is(DDTags.Blocks.HEATABLE)) {
                if(!neighborState.hasProperty(DDBlockStateProperties.HEAT_LEVEL)) continue;

                int currentHeat = neighborState.getValue(DDBlockStateProperties.HEAT_LEVEL);

                if (heatLevelToPass > currentHeat) {
                    level.setBlock(neighborPos, neighborState.setValue(DDBlockStateProperties.HEAT_LEVEL, IHeatableBlock.calculateNewHeat(heatLevelToPass)), Block.UPDATE_CLIENTS);
                    pushHeatOutward(level, neighborPos, heatLevelToPass - 1);
                }
            }
        }
    }
}