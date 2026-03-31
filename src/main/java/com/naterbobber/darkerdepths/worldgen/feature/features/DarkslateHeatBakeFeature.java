package com.naterbobber.darkerdepths.worldgen.feature.features;

import com.mojang.serialization.Codec;
import com.naterbobber.darkerdepths.block.DDBlockStateProperties;
import com.naterbobber.darkerdepths.block.generic.HeatableBlock;
import com.naterbobber.darkerdepths.util.DDTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class DarkslateHeatBakeFeature extends Feature<NoneFeatureConfiguration> {

    public DarkslateHeatBakeFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }
    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        WorldGenLevel level = context.level();
        ChunkPos chunkPos = new ChunkPos(context.origin());

        int minY = level.getMinBuildHeight();
        int startX = chunkPos.getMinBlockX();
        int startZ = chunkPos.getMinBlockZ();

        boolean modifiedAny = false;

        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                int worldX = startX + x;
                int worldZ = startZ + z;
                int columnMaxY = level.getHeight(Heightmap.Types.OCEAN_FLOOR_WG, worldX, worldZ);
                for (int y = minY; y <= columnMaxY; y++) {

                    BlockPos currentPos = new BlockPos(worldX, y, worldZ);
                    BlockState currentState = level.getBlockState(currentPos);

                    if (currentState.is(DDTags.Blocks.HEAT_PROVIDER)) {
                        pushHeatOutward(level, currentPos, HeatableBlock.determineHeat(currentState));
                        modifiedAny = true;
                    }
                }
            }
        }

        return modifiedAny;
    }

    private void pushHeatOutward(WorldGenLevel level, BlockPos sourcePos, int heatLevelToPass) {
        if (heatLevelToPass <= 0) return;

        for (Direction direction : Direction.values()) {
            BlockPos neighborPos = sourcePos.relative(direction);
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