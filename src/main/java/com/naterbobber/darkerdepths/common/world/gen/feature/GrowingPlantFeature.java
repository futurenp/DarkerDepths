package com.naterbobber.darkerdepths.common.world.gen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;

import java.util.Random;

//<>

public class GrowingPlantFeature extends Feature<GrowingPlantConfig> {
    public GrowingPlantFeature(Codec<GrowingPlantConfig> config) {
        super(config);
    }

    @Override
    public boolean generate(ISeedReader worldIn, ChunkGenerator generator, Random rand, BlockPos pos, GrowingPlantConfig config) {
        int height = config.heightDistribution.getRandomValue(rand).get(rand);
        BlockPos.Mutable mutable = pos.toMutable();
        BlockPos.Mutable offset = mutable.toMutable().move(config.direction);
        BlockState state = worldIn.getBlockState(mutable);

        for (int i = 1; i <= height ; i++) {
            BlockState blockState = state;
            state = worldIn.getBlockState(offset);
            if (blockState.isAir() || config.allowWater && blockState.getFluidState().isTagged(FluidTags.WATER)) {
                if (i == height || !state.isAir()) {
                    worldIn.setBlockState(mutable, config.headProvider.getBlockState(rand, mutable), 2);
                    break;
                }
                worldIn.setBlockState(mutable, config.bodyProvider.getBlockState(rand, mutable), 2);
            }

            offset.move(config.direction);
            mutable.move(config.direction);
        }

        return true;
    }
}