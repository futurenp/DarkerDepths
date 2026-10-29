package com.naterbobber.darkerdepths.common.worldgen.feature.features;

import com.mojang.serialization.Codec;
import com.naterbobber.darkerdepths.common.init.DDBlocks;
import com.naterbobber.darkerdepths.common.init.DDFeatures;
import com.naterbobber.darkerdepths.common.util.DDResourceKeys;
import com.naterbobber.darkerdepths.common.worldgen.feature.config.PointedAridrockConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderSet;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.WeightedListInt;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.synth.SimplexNoise;

public class AridPatchFeature extends Feature<NoneFeatureConfiguration> {
    private static final double PATCH_SCALE = 2.025D;

    public AridPatchFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        var level = context.level();
        var origin = context.origin();
        var random = context.random();
        var center = level.getHeightmapPos(Heightmap.Types.WORLD_SURFACE_WG, origin).below();
        var biome = level.getBiome(center.above());
        boolean desert = biome.is(Biomes.DESERT);
        boolean savanna = biome.is(Biomes.SAVANNA);
        if ((!desert && !savanna)
                || !isReplaceable(level, center, desert)
                || !hasCatacombsBelow(level, center)) {
            return false;
        }
        if (savanna && random.nextInt(4) == 0) {
            return false;
        }

        double radiusX = (4 + random.nextInt(3)) * PATCH_SCALE;
        double radiusZ = (4 + random.nextInt(3)) * PATCH_SCALE;
        double innerScale = 0.35D + random.nextDouble() * 0.1D;
        var outerNoise = new SimplexNoise(random);
        var innerNoise = new SimplexNoise(random);
        int extentX = (int) Math.ceil(radiusX * 1.25D);
        int extentZ = (int) Math.ceil(radiusZ * 1.25D);
        var dryDirt = DDBlocks.DRY_DIRT.get().defaultBlockState();
        var aridrock = DDBlocks.ARIDROCK.get().defaultBlockState();
        var duskrock = DDBlocks.DUSKROCK.get().defaultBlockState();
        var drySprouts = DDBlocks.DRY_SPROUTS.get().defaultBlockState();
        var pointedAridrockConfig = new PointedAridrockConfig(
                HolderSet.direct(DDBlocks.ARIDROCK.getDelegate()),
                new WeightedListInt(SimpleWeightedRandomList.<IntProvider>builder()
                        .add(ConstantInt.of(1), 20)
                        .add(ConstantInt.of(2), 5)
                        .build()));
        boolean placed = false;

        for (int x = -extentX; x <= extentX; x++) {
            for (int z = -extentZ; z <= extentZ; z++) {
                double distance = Math.sqrt((double) (x * x) / (radiusX * radiusX)
                        + (double) (z * z) / (radiusZ * radiusZ));
                double outerEdge = 0.9D + outerNoise.getValue(x * 0.4D / PATCH_SCALE, z * 0.4D / PATCH_SCALE) * 0.25D
                        + (random.nextDouble() - 0.5D) * 0.2D;
                if (distance > outerEdge) {
                    continue;
                }

                var surface = level.getHeightmapPos(Heightmap.Types.WORLD_SURFACE_WG, center.offset(x, 0, z)).below();
                if (!isReplaceable(level, surface, desert)
                        || !level.getBiome(surface.above()).is(desert ? Biomes.DESERT : Biomes.SAVANNA)
                        || !level.getFluidState(surface.above()).isEmpty()) {
                    continue;
                }

                double innerEdge = innerScale + innerNoise.getValue(x * 0.6D / PATCH_SCALE, z * 0.6D / PATCH_SCALE) * 0.12D
                        + (random.nextDouble() - 0.5D) * 0.12D;
                var state = desert ? (distance <= innerEdge ? dryDirt : aridrock)
                        : (distance <= innerEdge ? duskrock : dryDirt);
                var cursor = surface.mutable();
                while (cursor.getY() >= level.getMinBuildHeight() && isReplaceable(level, cursor, desert)) {
                    placed |= level.setBlock(cursor, state, 2);
                    cursor.move(0, -1, 0);
                }

                var sproutPos = surface.above();
                if (random.nextFloat() < 0.072F && level.isEmptyBlock(sproutPos)
                        && drySprouts.canSurvive(level, sproutPos)) {
                    level.setBlock(sproutPos, drySprouts, 2);
                } else if (desert && state.is(DDBlocks.ARIDROCK.get()) && random.nextFloat() < 0.01F) {
                    DDFeatures.POINTED_ARIDROCK.get().place(pointedAridrockConfig, level,
                            context.chunkGenerator(), random, sproutPos);
                }
            }
        }

        return placed;
    }

    private static boolean isReplaceable(WorldGenLevel level, BlockPos pos, boolean desert) {
        var state = level.getBlockState(pos);
        return state.is(Blocks.SAND) || (!desert && (state.is(Blocks.GRASS_BLOCK) || state.is(Blocks.DIRT)));
    }

    private static boolean hasCatacombsBelow(WorldGenLevel level, BlockPos surface) {
        var cursor = surface.mutable();
        for (int y = surface.getY() - 1; y >= level.getMinBuildHeight(); y -= 4) {
            cursor.setY(y);
            if (level.getBiome(cursor).is(DDResourceKeys.Biomes.SANDY_CATACOMBS)) {
                return true;
            }
        }
        return false;
    }
}
