package com.naterbobber.darkerdepths.world.gen.features;

import com.mojang.serialization.Codec;
import com.naterbobber.darkerdepths.init.DDBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

import java.util.HashMap;
import java.util.Map;

public class CatacombsLavaLiningFeature extends Feature<NoneFeatureConfiguration> {

    public CatacombsLavaLiningFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        WorldGenLevel level = context.level();
        BlockPos origin = context.origin();

        if (origin.getY() >= 0) {
            return false;
        }

        Map<BlockPos, Direction> darkslateCandidates = new HashMap<>();
        int radius = 6;
        BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos();

        for (int x = -radius; x <= radius; x++) {
            for (int y = -radius; y <= radius; y++) {
                for (int z = -radius; z <= radius; z++) {
                    mutablePos.setWithOffset(origin, x, y, z);
                    if (origin.distSqr(mutablePos) > radius * radius) {
                        continue;
                    }

                    BlockState currentState = level.getBlockState(mutablePos);
                    if (currentState.is(Blocks.STONE) || currentState.is(Blocks.DEEPSLATE) || currentState.is(DDBlocks.ARIDROCK.get()) || currentState.is(DDBlocks.ARID_DEEPSLATE.get())) {
                        Direction lavaDirection = this.getAdjacentLavaDirection(level, mutablePos);
                        if (lavaDirection != null) {
                            darkslateCandidates.put(mutablePos.immutable(), lavaDirection);
                        }
                    }
                }
            }
        }

        if (darkslateCandidates.isEmpty()) {
            return false;
        }

        Map<BlockPos, BlockState> blocksToPlace = new HashMap<>();
        for (BlockPos pos : darkslateCandidates.keySet()) {
            blocksToPlace.put(pos, DDBlocks.DARKSLATE.get().defaultBlockState());
        }

        for (Map.Entry<BlockPos, Direction> entry : darkslateCandidates.entrySet()) {
            BlockPos darkslatePos = entry.getKey();
            Direction lavaDirection = entry.getValue();

            Direction behindDirection = lavaDirection.getOpposite();
            BlockPos posBehind1 = darkslatePos.relative(behindDirection);
            if (level.getBlockState(posBehind1).is(DDBlocks.ARIDROCK.get()) && !darkslateCandidates.containsKey(posBehind1)) {
                blocksToPlace.put(posBehind1, DDBlocks.LIMESTONE.get().defaultBlockState());

                BlockPos posBehind2 = posBehind1.relative(behindDirection);
                if (level.getBlockState(posBehind2).is(DDBlocks.ARIDROCK.get()) && !darkslateCandidates.containsKey(posBehind2)) {
                    blocksToPlace.put(posBehind2, DDBlocks.LIMESTONE.get().defaultBlockState());
                }
            }

            BlockPos posAbove = darkslatePos.above();
            if (!darkslateCandidates.containsKey(posAbove)) {
                BlockState stateAbove = level.getBlockState(posAbove);
                if (stateAbove.is(DDBlocks.DRY_SPROUTS.get())) {
                    blocksToPlace.put(posAbove, Blocks.AIR.defaultBlockState());
                } else if (stateAbove.is(Blocks.STONE) || stateAbove.is(Blocks.DEEPSLATE) || stateAbove.is(DDBlocks.ARIDROCK.get()) || stateAbove.is(DDBlocks.ARID_DEEPSLATE.get())) {
                    blocksToPlace.put(posAbove, DDBlocks.LIMESTONE.get().defaultBlockState());
                }
            }
        }

        for (Map.Entry<BlockPos, BlockState> entry : blocksToPlace.entrySet()) {
            level.setBlock(entry.getKey(), entry.getValue(), 2);
        }

        return true;
    }

    private Direction getAdjacentLavaDirection(WorldGenLevel level, BlockPos pos) {
        for (Direction direction : Direction.values()) {
            if (level.getBlockState(pos.relative(direction)).is(Blocks.LAVA)) {
                return direction;
            }
        }
        return null;
    }
}