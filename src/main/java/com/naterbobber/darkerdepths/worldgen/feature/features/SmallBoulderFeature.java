package com.naterbobber.darkerdepths.worldgen.feature.features;

import com.mojang.serialization.Codec;
import com.naterbobber.darkerdepths.worldgen.feature.config.SmallBoulderConfiguration;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

import java.util.HashMap;
import java.util.Map;

public class SmallBoulderFeature extends Feature<SmallBoulderConfiguration> {

    public SmallBoulderFeature(Codec<SmallBoulderConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<SmallBoulderConfiguration> context) {
        WorldGenLevel level = context.level();
        BlockPos origin = context.origin();
        RandomSource random = context.random();
        SmallBoulderConfiguration config = context.config();

        int tallHeight = 2;
        int shortHeight = 1;

        Map<BlockPos, Integer> footprint = new HashMap<>();
        boolean isNorthWestToSouthEast = random.nextBoolean();
        int heightA = random.nextBoolean() ? tallHeight : shortHeight;
        int heightB = (heightA == tallHeight) ? shortHeight : tallHeight;

        if (isNorthWestToSouthEast) {
            for (int x = -1; x <= 0; x++) {
                for (int z = -1; z <= 0; z++) {
                    footprint.put(new BlockPos(x, 0, z), heightA);
                }
            }
            for (int x = 0; x <= 1; x++) {
                for (int z = 0; z <= 1; z++) {
                    footprint.merge(new BlockPos(x, 0, z), heightB, Math::max);
                }
            }
        } else {
            for (int x = 0; x <= 1; x++) {
                for (int z = -1; z <= 0; z++) {
                    footprint.put(new BlockPos(x, 0, z), heightA);
                }
            }
            for (int x = -1; x <= 0; x++) {
                for (int z = 0; z <= 1; z++) {
                    footprint.merge(new BlockPos(x, 0, z), heightB, Math::max);
                }
            }
        }

        // Ground Stability Check
        boolean needsShift = false;
        for (BlockPos localPos : footprint.keySet()) {
            BlockPos groundCheck = origin.offset(localPos.getX(), -1, localPos.getZ());
            if (!level.getBlockState(groundCheck).isSolidRender(level, groundCheck)) {
                needsShift = true;
                break;
            }
        }

        BlockPos placementOrigin = needsShift ? origin.below() : origin;

        if (needsShift) {
            for (BlockPos localPos : footprint.keySet()) {
                BlockPos groundCheck = placementOrigin.offset(localPos.getX(), -1, localPos.getZ());
                if (!level.getBlockState(groundCheck).isSolidRender(level, groundCheck)) {
                    return false;
                }
            }
        }

        // Strict Replaceable Check
        for (Map.Entry<BlockPos, Integer> entry : footprint.entrySet()) {
            int x = entry.getKey().getX();
            int z = entry.getKey().getZ();
            int height = entry.getValue();

            for (int y = 0; y < height; y++) {
                BlockPos checkPos = placementOrigin.offset(x, y, z);
                BlockState checkState = level.getBlockState(checkPos);

                if (!checkState.isAir() && !checkState.canBeReplaced()) {
                    return false;
                }
            }
        }

        // Build the Main Boulder
        boolean placedAnyBlock = false;
        for (Map.Entry<BlockPos, Integer> entry : footprint.entrySet()) {
            int x = entry.getKey().getX();
            int z = entry.getKey().getZ();
            int height = entry.getValue();

            for (int y = 0; y < height; y++) {
                BlockPos targetPos = placementOrigin.offset(x, y, z);
                BlockState mainState = config.mainBlock().getState(random, targetPos);
                setBlock(level, targetPos, mainState);
                placedAnyBlock = true;
            }
        }

        // --- NEW DECORATION LOGIC ---
        // Only run if the boulder was successfully placed AND a decorator is configured
        if (placedAnyBlock && config.decoratorBlock().isPresent() && config.decoratorChance() > 0) {
            BlockStateProvider decoratorProvider = config.decoratorBlock().get();

            // Iterate a 5x5 area centered on the boulder
            for (int x = -2; x <= 2; x++) {
                for (int z = -2; z <= 2; z++) {

                    // Skip the 4 extreme corners to make a rounded splash radius
                    if (Math.abs(x) == 2 && Math.abs(z) == 2) continue;

                    // Roll the dice for this specific column
                    if (random.nextFloat() >= config.decoratorChance() / 2) continue;

                    // Scan top-down in this column to find the surface (boulder top or ground)
                    // We start at y=3 (above the highest possible boulder block) and scan down to y=-1
                    for (int y = 3; y >= -1; y--) {
                        BlockPos surfaceCheck = placementOrigin.offset(x, y, z);
                        BlockState surfaceState = level.getBlockState(surfaceCheck);

                        // Once we hit a solid block (the boulder or the ground)
                        if (surfaceState.isSolidRender(level, surfaceCheck)) {
                            BlockPos decoratorPos = surfaceCheck.above();

                            // Make sure the block immediately above it is empty
                            if (level.isEmptyBlock(decoratorPos)) {
                                setBlock(level, decoratorPos, decoratorProvider.getState(random, decoratorPos));
                            }
                            break; // Break the vertical scan loop once surface is found
                        }
                    }
                }
            }
        }

        return placedAnyBlock;
    }
}