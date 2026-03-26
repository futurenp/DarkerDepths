package com.naterbobber.darkerdepths.worldgen.feature.features;

import com.mojang.serialization.Codec;
import com.naterbobber.darkerdepths.worldgen.feature.config.RandomFloorPlacementConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;

public class RandomFloorPlacementFeature extends Feature<RandomFloorPlacementConfig> {

    public RandomFloorPlacementFeature(Codec<RandomFloorPlacementConfig> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<RandomFloorPlacementConfig> context) {
        WorldGenLevel level = context.level();
        BlockPos origin = context.origin();
        RandomSource random = context.random();
        RandomFloorPlacementConfig config = context.config();
        int maxPlacement = context.config().placeCount();
        int placed = 0;
        BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos();

        for (int i = 0; i < config.tries(); i++) {
            int dx = random.nextInt(config.xzRadius() * 2 + 1) - config.xzRadius();
            int dy = random.nextInt(config.yHeight() * 2 + 1) - config.yHeight();
            int dz = random.nextInt(config.xzRadius() * 2 + 1) - config.xzRadius();

            mutablePos.setWithOffset(origin, dx, dy, dz);

            if (level.isEmptyBlock(mutablePos)) {

                BlockState floorState = level.getBlockState(mutablePos.below());

                if (config.validFloorBlocks().contains(floorState.getBlock().builtInRegistryHolder())) {
                    BlockState stateToPlace = config.stateProvider().getState(random, mutablePos);
                    level.setBlock(mutablePos, stateToPlace, 2);
                    placed++;

                    if(placed >= maxPlacement) {
                        break;
                    }
                }
            }
        }

        return placed > 0;
    }
}