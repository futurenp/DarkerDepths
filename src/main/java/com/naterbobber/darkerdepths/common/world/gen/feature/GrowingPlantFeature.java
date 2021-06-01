package com.naterbobber.darkerdepths.common.world.gen.feature;

import com.mojang.serialization.Codec;
import com.naterbobber.darkerdepths.core.registries.DDBlocks;
import net.minecraft.block.AbstractTopPlantBlock;
import net.minecraft.block.BlockState;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

import java.util.Random;

//<>

public class GrowingPlantFeature extends Feature<GrowingPlantConfig> {
    public GrowingPlantFeature(Codec<GrowingPlantConfig> config) {
        super(config);
    }

    @Override
    public boolean generate(ISeedReader worldIn, ChunkGenerator generator, Random rand, BlockPos pos, GrowingPlantConfig config) {
        int heightDistribution = config.heightDistribution.getRandomValue(rand).get(rand);
        BlockPos.Mutable position = pos.toMutable();
        BlockPos.Mutable offset = position.toMutable().move(config.direction);
        BlockState state = worldIn.getBlockState(position);

        for (int i = 1; i <= heightDistribution ; i++) {
            BlockState blockState = state;
            state = worldIn.getBlockState(offset);
            if (blockState.isAir() || config.allowWater && blockState.getFluidState().isTagged(FluidTags.WATER)) {
                if (i == heightDistribution || !state.isAir()) {
                    worldIn.setBlockState(position, config.headProvider.getBlockState(rand, position), 2);
                    break;
                }
                worldIn.setBlockState(position, config.bodyProvider.getBlockState(rand, position), 2);
            }

            offset.move(config.direction);
            position.move(config.direction);
        }

        return true;
    }
}