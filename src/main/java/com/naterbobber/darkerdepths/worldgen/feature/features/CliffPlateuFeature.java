package com.naterbobber.darkerdepths.worldgen.feature.features;

import com.mojang.serialization.Codec;
import com.naterbobber.darkerdepths.block.DDBlockStateProperties;
import com.naterbobber.darkerdepths.init.DDBlocks;
import com.naterbobber.darkerdepths.worldgen.feature.config.CliffPlateuConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;

import java.util.Arrays;
import java.util.HashMap;

public class CliffPlateuFeature extends Feature<CliffPlateuConfig> {
    public CliffPlateuFeature(Codec<CliffPlateuConfig> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<CliffPlateuConfig> context) {
        var level = context.level();
        var origin = context.origin();
        var random = context.random();
        var config = context.config();

        int baseRadius = config.xzRadius();

        float radiusX = baseRadius;
        float radiusZ = baseRadius;

        var randomStretchSelectionArray = new int[] {0, 0, 0, 0, 1, 1, 1, 2, 3};
        int stretch = randomStretchSelectionArray[random.nextInt(randomStretchSelectionArray.length)];

        if (random.nextBoolean()) {
            radiusX += stretch;
        } else {
            radiusZ += stretch;
        }

        var boundingBox = new Bounds((int) Math.ceil(radiusX), (int) Math.ceil(radiusZ));
        var blocksToPlace = new HashMap<BlockPos, BlockState>();
        var heightmap = new int[boundingBox.x * 2 + 1][boundingBox.z * 2 + 1];
        int height = config.height() + random.nextInt(7) - 2;

        for (int[] ints : heightmap) {
            Arrays.fill(ints, -999);
        }

        generateShape(level, origin, boundingBox, blocksToPlace, heightmap, height, radiusX, radiusZ);
        decorate(level, origin, boundingBox, blocksToPlace, heightmap);

        boolean placedAnyBlocks = false;

        for (var entry : blocksToPlace.entrySet()) {
            this.setBlock(level, entry.getKey(), entry.getValue());
            placedAnyBlocks = true;
        }

        return placedAnyBlocks;
    }

    private void generateShape(WorldGenLevel level,  BlockPos origin, Bounds boundingBox, HashMap<BlockPos, BlockState> blocksToPlace, int[][] heightmap, int height, float radiusX, float radiusZ) {
        var random = level.getRandom();
        float taperAdjustment = 0.2F + random.nextFloat() * 0.2F;

        for (int x = -boundingBox.x; x <= boundingBox.x; x++) {
            for (int z = -boundingBox.z; z <= boundingBox.z; z++) {

                int bottomOffset = -random.nextInt(4);

                for (int y = bottomOffset; y <= height; y++) {

                    float progress = Math.max(0, (float) y / height);
                    float taper = 1.0f - (progress * taperAdjustment);

                    float currentRadiusX = radiusX * taper;
                    float currentRadiusZ = radiusZ * taper;

                    if (currentRadiusX <= 0 || currentRadiusZ <= 0) continue;

                    float distanceSq = (x * x) / (currentRadiusX * currentRadiusX) +
                            (z * z) / (currentRadiusZ * currentRadiusZ);

                    float noise = (random.nextFloat() * 0.1f) - 0.05f;

                    if (distanceSq <= 1.0f + noise) {
                        var targetPos = origin.offset(x, y, z);

                        if (level.isEmptyBlock(targetPos) || level.getBlockState(targetPos).is(BlockTags.REPLACEABLE)) {
                            blocksToPlace.put(targetPos, DDBlocks.GLIST.get().defaultBlockState());

                            int arrayX = x + boundingBox.x;
                            int arrayZ = z + boundingBox.z;
                            heightmap[arrayX][arrayZ] = Math.max(heightmap[arrayX][arrayZ], y);
                        }
                    }
                }
            }
        }
    }


    private void decorate(WorldGenLevel level,  BlockPos origin, Bounds boundingBox, HashMap<BlockPos, BlockState> blocksToPlace, int[][] heightmap) {
        var random = level.getRandom();
        for (int x = -boundingBox.x; x <= boundingBox.x; x++) {
            for (int z = -boundingBox.z; z <= boundingBox.z; z++) {
                int arrayX = x + boundingBox.x;
                int arrayZ = z + boundingBox.z;
                int maxY = heightmap[arrayX][arrayZ];

                if (maxY != -999) {
                    var topPos = origin.offset(x, maxY, z);
                    var secondPos = origin.offset(x, maxY - 1, z);

                    if (blocksToPlace.containsKey(topPos)) {
                        blocksToPlace.put(topPos, DDBlocks.MOSSY_GRIMESTONE.get().defaultBlockState());
                    }

                    if (blocksToPlace.containsKey(secondPos)) {
                        blocksToPlace.put(secondPos, DDBlocks.GRIMESTONE.get().defaultBlockState());
                    }

                    float vegetationChance = random.nextFloat();

                    var sproutPos = origin.offset(x, maxY + 1, z);

                    if (vegetationChance < 0.03f) {
                        if (level.isEmptyBlock(sproutPos)) {
                            blocksToPlace.put(sproutPos, DDBlocks.GLOWSHROOM.get().defaultBlockState()
                                    .setValue(DDBlockStateProperties.GLOWSHROOM_CLUSTERS, random.nextInt(1, 3)));
                        }
                    } else if (vegetationChance < 0.3f) {
                        if (level.isEmptyBlock(sproutPos)) {
                            blocksToPlace.put(sproutPos, DDBlocks.MOSSY_SPROUTS.get().defaultBlockState());
                        }
                    }
                }
            }
        }
    }

    private record Bounds(int x, int z) {}
}