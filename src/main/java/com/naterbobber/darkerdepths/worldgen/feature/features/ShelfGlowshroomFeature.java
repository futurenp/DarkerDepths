package com.naterbobber.darkerdepths.worldgen.feature.features;

import com.mojang.serialization.Codec;
import com.naterbobber.darkerdepths.block.DDBlockStateProperties;
import com.naterbobber.darkerdepths.init.DDBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class ShelfGlowshroomFeature extends Feature<NoneFeatureConfiguration> {

    public ShelfGlowshroomFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> featurePlaceContext) {
        var level = featurePlaceContext.level();
        var random = level.getRandom();
        boolean placed = false;

        var directionPos = findWallDirectionPos(level, featurePlaceContext.origin());
        if(directionPos == null) return false;
        var direction = directionPos.direction();
        var pos = directionPos.blockPos;

        var mutablePos = BlockPos.MutableBlockPos.of(pos.asLong());
        double placementChance = 1;

        while(random.nextDouble() < placementChance) {
            var currentState = level.getBlockState(mutablePos);
            var forwardState = level.getBlockState(mutablePos.relative(direction));
            var backwardState = level.getBlockState(mutablePos.relative(direction.getOpposite()));
            backwardState.isFaceSturdy(level, mutablePos, direction);

            if(currentState.isCollisionShapeFullBlock(level, mutablePos) && forwardState.isEmpty()) {
                placeShelf(level, mutablePos.relative(direction), direction, random.nextBoolean());
            }
            else if(forwardState.isCollisionShapeFullBlock(level, mutablePos.relative(direction)) && level.isEmptyBlock(mutablePos.relative(direction, 2))) {
                placeShelf(level, mutablePos.relative(direction, 2), direction, random.nextBoolean());
                mutablePos = mutablePos.relative(direction);
            }
            else if(backwardState.isCollisionShapeFullBlock(level, mutablePos.relative(direction.getOpposite())) && currentState.isEmpty()) {
                placeShelf(level, mutablePos, direction, random.nextBoolean());
                mutablePos = mutablePos.relative(direction.getOpposite());
            } else {
                return placed;
            }

            if(!placed) {
                placed = true;
            }
            mutablePos = mutablePos.relative(direction.getClockWise());
            placementChance /= 1.65;
        }

        return placed;
    }

    private static DirectionPos findWallDirectionPos(WorldGenLevel level, BlockPos pos) {
        int maxSteps = 8;
        var mutablePos = BlockPos.MutableBlockPos.of(pos.asLong());

        var random = level.getRandom();

        var directions = new Direction[]{
                Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST
        };

        for (int i = directions.length - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            var temp = directions[i];
            directions[i] = directions[j];
            directions[j] = temp;
        }

        for(var dir : directions) {
            for(int i = 1; i <= maxSteps; i++) {
                mutablePos = pos.relative(dir, i);
                if(level.getBlockState(mutablePos).isCollisionShapeFullBlock(level, mutablePos)) {
                    return new DirectionPos(mutablePos.relative(dir.getOpposite(), 1), dir.getOpposite());
                } else {
                    if(!level.isEmptyBlock(mutablePos)) {
                        break;
                    }
                }
            }

        }
        return null;
    }

    private void placeShelf(WorldGenLevel level, BlockPos pos, Direction direction, boolean large) {
        var state = DDBlocks.SHELF_GLOWSHROOM.get().defaultBlockState()
                .setValue(DDBlockStateProperties.LARGE, large)
                .setValue(HorizontalDirectionalBlock.FACING, direction);

        setBlock(level, pos, state);
    }

    private record DirectionPos(BlockPos blockPos, Direction direction) {}
}
