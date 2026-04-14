package com.naterbobber.darkerdepths.worldgen.feature.features;

import com.mojang.serialization.Codec;
import com.naterbobber.darkerdepths.worldgen.feature.config.CliffPlateuConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.neoforged.neoforge.common.Tags;

public class CliffPlateuFeature extends Feature<CliffPlateuConfig> {
    public CliffPlateuFeature(Codec<CliffPlateuConfig> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<CliffPlateuConfig> context) {
        WorldGenLevel level = context.level();
        BlockPos origin = context.origin();
        RandomSource random = context.random();
        CliffPlateuConfig config = context.config();

        int baseRadius = config.xzRadius();
        int height = config.height();

        // 1. Randomly stretch one axis to create an oval shape
        float radiusX = baseRadius;
        float radiusZ = baseRadius;

        // Stretch by a random amount (up to half the base radius)
        int stretch = random.nextInt(Math.max(1, (int)(baseRadius * 0.75)) + 1);
        if (random.nextBoolean()) {
            radiusX += stretch;
        } else {
            radiusZ += stretch;
        }

        boolean placedAnyBlocks = false;

        // Bounding box for our loops based on the max possible radii
        int boundingBoxX = (int) Math.ceil(radiusX);
        int boundingBoxZ = (int) Math.ceil(radiusZ);

        for (int x = -boundingBoxX; x <= boundingBoxX; x++) {
            for (int z = -boundingBoxZ; z <= boundingBoxZ; z++) {

                // 2. Random terrain adjustment for the bottom
                // The bottom will randomly dig down 0 to 3 blocks into the terrain
                int bottomOffset = -random.nextInt(4);

                for (int y = bottomOffset; y <= height; y++) {

                    // 3. Tapering on the top
                    // Shrink the radius slightly as it gets higher (max 20% reduction at the very top)
                    float progress = Math.max(0, (float) y / height);
                    float taper = 1.0f - (progress * 0.25f);

                    float currentRadiusX = radiusX * taper;
                    float currentRadiusZ = radiusZ * taper;

                    // Prevent division by zero just in case
                    if (currentRadiusX <= 0 || currentRadiusZ <= 0) continue;

                    // 4. Calculate if the current block is inside the oval (ellipse formula)
                    float distanceSq = (x * x) / (currentRadiusX * currentRadiusX) +
                            (z * z) / (currentRadiusZ * currentRadiusZ);

                    // Add a tiny bit of random noise to the edge so the wall isn't perfectly smooth
                    float noise = (random.nextFloat() * 0.1f) - 0.05f;

                    if (distanceSq <= 1.0f + noise) {
                        BlockPos targetPos = origin.offset(x, y, z);

                        // Only place if the block is air or replaceable (e.g., grass/water)
                        if (level.isEmptyBlock(targetPos) || level.getBlockState(targetPos).is(BlockTags.REPLACEABLE)) {
                            BlockState state = config.mainBlock().getState(random, targetPos);

                            // Feature.setBlock automatically handles block placement properly for worldgen
                            this.setBlock(level, targetPos, state);
                            placedAnyBlocks = true;
                        }
                    }
                }
            }
        }

        return placedAnyBlocks;
    }
}