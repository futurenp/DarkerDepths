package com.naterbobber.darkerdepths.worldgen.feature.features;

import com.mojang.serialization.Codec;
import com.naterbobber.darkerdepths.init.DDBlocks;
import jdk.jfr.Experimental;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;


@Experimental
public class LimestoneStripeFeature extends Feature<NoneFeatureConfiguration> {

    public LimestoneStripeFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        WorldGenLevel level = context.level();
        BlockPos origin = context.origin();
        RandomSource random = context.random();
        boolean hasPlacedAny = false;

        // --- Ore Generation Parameters ---
        // You can easily tweak these values to change the vein size and shape.
        final int veinSize = 12; // The number of blocks to attempt to place in a vein.
        final int placementRadius = 4; // The radius from the origin where blocks can be placed.
        final BlockState oreToPlace = DDBlocks.DUSKROCK.get().defaultBlockState();
        // ---------------------------------

        BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos();

        // Loop 'veinSize' times to attempt to place that many blocks.
        for (int i = 0; i < veinSize; i++) {
            // Get a random position within a sphere around the origin.
            // This creates the cluster/blob shape of the ore vein.
            int dx = random.nextInt(placementRadius * 2 + 1) - placementRadius;
            int dy = random.nextInt(placementRadius * 2 + 1) - placementRadius;
            int dz = random.nextInt(placementRadius * 2 + 1) - placementRadius;
            mutablePos.setWithOffset(origin, dx, dy, dz);

            BlockState currentState = level.getBlockState(mutablePos);

            // Check if the current block is a valid target for replacement.
            // This is the same logic you had before.
            if (currentState.is(BlockTags.BASE_STONE_OVERWORLD) || currentState.is(DDBlocks.DUSKROCK.get()) || currentState.is(DDBlocks.ARID_DEEPSLATE.get())) {
                level.setBlock(mutablePos, oreToPlace, 2);
                hasPlacedAny = true;
            }
        }

        return hasPlacedAny;
    }
}
