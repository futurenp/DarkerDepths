package com.naterbobber.darkerdepths.worldgen.features;

import com.mojang.serialization.Codec;
import com.naterbobber.darkerdepths.DarkerDepths;
import com.naterbobber.darkerdepths.block.blockentities.MobPlacerBlockEntity;
import com.naterbobber.darkerdepths.init.DDBlocks;
import com.naterbobber.darkerdepths.init.DDEntityTypes;
import com.naterbobber.darkerdepths.worldgen.features.config.ScorcherFeatureConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;

public class ScorcherFeature extends Feature<ScorcherFeatureConfig> {

    public ScorcherFeature(Codec<ScorcherFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<ScorcherFeatureConfig> context) {
        WorldGenLevel level = context.level();
        BlockPos origin = context.origin();
        var random = context.random();
        ScorcherFeatureConfig config = context.config();
        // 1. SMART SNAP TO FLOOR
        // The worldgen often feeds us bad coordinates (mid-air or inside a wall).
        // Let's scan up or down to find the actual floor of the cave.
        BlockPos.MutableBlockPos mutableOrigin = origin.mutable();

        // If we are in a wall, scan UP until we hit air
        while (!level.isEmptyBlock(mutableOrigin) && mutableOrigin.getY() < level.getMaxBuildHeight()) {
            mutableOrigin.move(0, 1, 0);
        }
        // If we are in the air, scan DOWN until we hit a solid block
        while (level.isEmptyBlock(mutableOrigin.below()) && mutableOrigin.getY() > level.getMinBuildHeight()) {
            mutableOrigin.move(0, -1, 0);
        }

        // Final sanity check: are we actually on solid ground now?
        if (!level.getBlockState(mutableOrigin.below()).isSolid() || !level.isEmptyBlock(mutableOrigin)) {
            return false;
        }

        // Update our origin to the true cave floor
        origin = mutableOrigin.immutable();

        int height = config.minHeight() + random.nextInt(config.maxHeight() - config.minHeight() + 1);
        int baseRadius = config.baseRadius() + random.nextInt(2);

        // 2. FORGIVING CLEARANCE CHECK (Cross Pattern)
        // Check 10 blocks N/S/E/W and 5 blocks above the exact peak where the Scorcher spawns
        BlockPos peakPos = origin.offset(0, height, 0);
        for (int d = 0; d <= 4; d++) { // Distance outwards
            for (int yOffset = 0; yOffset <= 2; yOffset++) { // Distance upwards
                if (d == 0) {
                    if (!level.isEmptyBlock(peakPos.offset(0, yOffset, 0))) {
                        return false;
                    }
                } else {
                    // Check the 4 cardinal directions (North, South, East, West)
                    if (!level.isEmptyBlock(peakPos.offset(d, yOffset, 0)) ||
                            !level.isEmptyBlock(peakPos.offset(-d, yOffset, 0)) ||
                            !level.isEmptyBlock(peakPos.offset(0, yOffset, d)) ||
                            !level.isEmptyBlock(peakPos.offset(0, yOffset, -d))) {
                        return false;
                    }
                }
            }
        }

        BlockState darkslate = DDBlocks.DARKSLATE.get().defaultBlockState();

        // 3. Generate the spike
        for (int y = 0; y < height; y++) {
            float progress = (float) y / height;
            float currentRadius = baseRadius * (1.0F - progress) + (random.nextFloat() * 0.5F);

            for (int x = -(int)Math.ceil(currentRadius); x <= Math.ceil(currentRadius); x++) {
                for (int z = -(int)Math.ceil(currentRadius); z <= Math.ceil(currentRadius); z++) {
                    if (x * x + z * z <= currentRadius * currentRadius) {
                        BlockPos placePos = origin.offset(x, y, z);
                        if (level.isEmptyBlock(placePos)) {
                            level.setBlock(placePos, darkslate, 2);

                            // ROOTING SYSTEM: Fill downwards to prevent any part of the base from floating
                            BlockPos.MutableBlockPos downPos = placePos.mutable().move(0, -1, 0);
                            // Keep going down until we hit a solid block (stone, dirt, deepslate, etc.)
                            while (!level.getBlockState(downPos).isSolid() && downPos.getY() > level.getMinBuildHeight()) {
                                level.setBlock(downPos, darkslate, 2);
                                downPos.move(0, -1, 0);
                            }
                        }
                    }
                }
            }
        }

        // 4. Place the Mob Placer at the peak
        BlockPos topPos = origin.offset(0, height, 0);
        level.setBlock(topPos, DDBlocks.MOB_PLACER.get().defaultBlockState(), 2);

        BlockEntity blockEntity = level.getBlockEntity(topPos);
        if (blockEntity instanceof MobPlacerBlockEntity placerEntity) {
            String entityId = net.minecraft.core.registries.BuiltInRegistries.ENTITY_TYPE.getKey(DDEntityTypes.SCORCHER.get()).toString();
            placerEntity.setEntityType(entityId);
            placerEntity.setRotation(90F);
        }

        return true;
    }
}