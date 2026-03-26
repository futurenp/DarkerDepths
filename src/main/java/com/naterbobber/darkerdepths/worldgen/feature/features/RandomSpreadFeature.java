package com.naterbobber.darkerdepths.worldgen.feature.features;

import com.mojang.serialization.Codec;
import com.naterbobber.darkerdepths.block.custom.GlowshroomBlock;
import com.naterbobber.darkerdepths.init.DDBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.feature.DripstoneUtils;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.material.Fluids;

public class RandomSpreadFeature extends Feature<NoneFeatureConfiguration> {

    public RandomSpreadFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        WorldGenLevel world = context.level();
        BlockPos blockPos = context.origin();
        RandomSource random = context.random();
        int tries = UniformInt.of(4, 10).sample(random);
        BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
        for (int i = 0; i < tries; i++) {
            mutableBlockPos.setWithOffset(blockPos, random.nextInt(7) - random.nextInt(7), random.nextInt(3) - random.nextInt(3), random.nextInt(7) - random.nextInt(7));
            if (world.getBlockState(mutableBlockPos).is(BlockTags.BASE_STONE_OVERWORLD) && world.isStateAtPosition(mutableBlockPos, DripstoneUtils::isEmptyOrWater)) {
                world.setBlock(mutableBlockPos, DDBlocks.GLOWSHROOM.get().defaultBlockState().setValue(GlowshroomBlock.WATERLOGGED, world.getFluidState(mutableBlockPos).getType() == Fluids.WATER).setValue(GlowshroomBlock.GLOWSHROOM_CLUSTERS, 1), 2);
            } else {
                break;
            }
        }
        return false;
    }
}
