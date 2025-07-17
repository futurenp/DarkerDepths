package com.naterbobber.darkerdepths.world.gen.features;

import com.mojang.serialization.Codec;
import com.naterbobber.darkerdepths.init.DDBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.LegacyRandomSource;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.synth.PerlinNoise;

public class LimestoneStripeFeature extends Feature<NoneFeatureConfiguration> {

    public LimestoneStripeFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        WorldGenLevel level = context.level();
        BlockPos origin = context.origin();
        boolean hasPlacedAny = false;

        PerlinNoise noise = PerlinNoise.create(new WorldgenRandom(new LegacyRandomSource(level.getSeed() + origin.asLong())), -4, 1.0);

        int patchRadius = 11; // How far out from the origin the patch extends.
        double noiseScaleXZ = 100.0; // Larger numbers = wider, more stretched stripes.
        double noiseScaleY = 5.0;  // Smaller numbers = thicker vertical stripes.
        double noiseThreshold = 0.68; // From 0.0 to 1.0. Higher numbers = thinner, rarer stripes.

        BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos();

        for (int x = -patchRadius; x <= patchRadius; x++) {
            for (int z = -patchRadius; z <= patchRadius; z++) {
                for (int y = -patchRadius; y <= patchRadius; y++) {
                    mutablePos.setWithOffset(origin, x, y, z);

                    if(mutablePos.distSqr(origin) > patchRadius * patchRadius) {
                        continue;
                    }
                    //normal noise?

                    double noiseValue = noise.getValue(
                            mutablePos.getX() / noiseScaleXZ,
                            mutablePos.getY() / noiseScaleY,
                            mutablePos.getZ() / noiseScaleXZ
                    );

                    if (noiseValue > noiseThreshold) {
                        BlockState currentState = level.getBlockState(mutablePos);
                        if (currentState.is(BlockTags.BASE_STONE_OVERWORLD) || currentState.is(DDBlocks.DUSKROCK.get()) || currentState.is(DDBlocks.ARID_DEEPSLATE.get())) {
                            level.setBlock(mutablePos, DDBlocks.DUSKROCK.get().defaultBlockState(), 2);
                            hasPlacedAny = true;
                        }
                    }
                }
            }
        }
        return hasPlacedAny;
    }
}