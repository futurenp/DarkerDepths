package com.naterbobber.darkerdepths.common.worldgen.feature.features;

import com.mojang.serialization.Codec;
import com.naterbobber.darkerdepths.common.block.blockentities.MobPlacerBlockEntity;
import com.naterbobber.darkerdepths.common.init.DDBlocks;
import com.naterbobber.darkerdepths.common.init.DDEntityTypes;
import com.naterbobber.darkerdepths.common.util.DDResourceKeys;
import com.naterbobber.darkerdepths.common.worldgen.feature.config.ScorcherFeatureConfig;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;

public class ScorcherFeature extends Feature<ScorcherFeatureConfig> {
    private static final int MAX_FLOOR_SEARCH_DISTANCE = 32;

    public ScorcherFeature(Codec<ScorcherFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<ScorcherFeatureConfig> context) {
        var level = context.level();
        var origin = context.origin();
        var random = context.random();
        var config = context.config();
        var mutableOrigin = origin.mutable();

        if (mutableOrigin.getY() <= level.getMinBuildHeight() || mutableOrigin.getY() >= level.getMaxBuildHeight()) {
            return false;
        }
        if (!level.isEmptyBlock(mutableOrigin)) {
            mutableOrigin.move(0, 1, 0);
        }
        if (mutableOrigin.getY() >= level.getMaxBuildHeight() || !level.isEmptyBlock(mutableOrigin)) {
            return false;
        }
        for (int distance = 0; distance < MAX_FLOOR_SEARCH_DISTANCE
                && mutableOrigin.getY() > level.getMinBuildHeight() + 1
                && level.isEmptyBlock(mutableOrigin.below()); distance++) {
            mutableOrigin.move(0, -1, 0);
        }
        if (!level.getBlockState(mutableOrigin.below()).isSolid()) {
            return false;
        }

        origin = mutableOrigin.immutable();
        int height = config.minHeight() + random.nextInt(config.maxHeight() - config.minHeight() + 1);
        int baseRadius = config.baseRadius() + random.nextInt(2);
        var peakPos = origin.offset(0, height, 0);

        if (peakPos.getY() + 2 >= level.getMaxBuildHeight()
                || peakPos.getY() + 2 >= level.getHeight(Heightmap.Types.OCEAN_FLOOR_WG, peakPos.getX(), peakPos.getZ()) - 1
                || !level.getBiome(origin).is(DDResourceKeys.Biomes.MOLTEN_CAVERN)
                || !level.getBiome(peakPos).is(DDResourceKeys.Biomes.MOLTEN_CAVERN)) {
            return false;
        }

        for (int d = 0; d <= 4; d++) {
            for (int yOffset = 0; yOffset <= 2; yOffset++) {
                if (d == 0) {
                    if (!level.isEmptyBlock(peakPos.offset(0, yOffset, 0))) {
                        return false;
                    }
                } else {
                    if (!level.isEmptyBlock(peakPos.offset(d, yOffset, 0)) ||
                            !level.isEmptyBlock(peakPos.offset(-d, yOffset, 0)) ||
                            !level.isEmptyBlock(peakPos.offset(0, yOffset, d)) ||
                            !level.isEmptyBlock(peakPos.offset(0, yOffset, -d))) {
                        return false;
                    }
                }
            }
        }

        var darkslate = DDBlocks.DARKSLATE.get().defaultBlockState();

        for (int y = 0; y < height; y++) {
            float progress = (float) y / height;
            float currentRadius = baseRadius * (1.0F - progress) + (random.nextFloat() * 0.5F);

            for (int x = -(int)Math.ceil(currentRadius); x <= Math.ceil(currentRadius); x++) {
                for (int z = -(int)Math.ceil(currentRadius); z <= Math.ceil(currentRadius); z++) {
                    if (x * x + z * z <= currentRadius * currentRadius) {
                        var placePos = origin.offset(x, y, z);
                        if (level.isEmptyBlock(placePos)) {
                            level.setBlock(placePos, darkslate, 2);

                            var downPos = placePos.mutable().move(0, -1, 0);
                            while (!level.getBlockState(downPos).isSolid() && downPos.getY() > level.getMinBuildHeight()) {
                                level.setBlock(downPos, darkslate, 2);
                                downPos.move(0, -1, 0);
                            }
                        }
                    }
                }
            }
        }

        level.setBlock(peakPos, DDBlocks.MOB_PLACER.get().defaultBlockState(), 2);

        var blockEntity = level.getBlockEntity(peakPos);
        if (blockEntity instanceof MobPlacerBlockEntity placerEntity) {
            var entityId = net.minecraft.core.registries.BuiltInRegistries.ENTITY_TYPE.getKey(DDEntityTypes.SCORCHER.get()).toString();
            placerEntity.setEntityType(entityId);
            placerEntity.setRotation(90F);
        }

        return true;
    }
}
