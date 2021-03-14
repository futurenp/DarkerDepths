package com.naterbobber.darkerdepths.common.world.gen.feature;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import com.naterbobber.darkerdepths.common.blocks.MultifaceGemBlock;
import com.naterbobber.darkerdepths.core.registries.DDBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.structure.StructureManager;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

//<>

public class AmberFeature extends Feature<AmberConfig> {
    public AmberFeature(Codec<AmberConfig> codec) {
        super(codec);
    }

    @Override
    public boolean func_230362_a_(ISeedReader worldIn, StructureManager structure, ChunkGenerator generator, Random rand, BlockPos pos, AmberConfig config) {
        if (!isAirOrWater(worldIn.getBlockState(pos))) {
            return false;
        } else {
            List<Direction> directions = getShuffledDirections(config, rand);
            if (placeAmberIfPossible(worldIn, pos, worldIn.getBlockState(pos), config, rand, directions)) {
                return true;
            } else {
                BlockPos.Mutable blockPos = pos.toMutable();

                for (Direction direction : directions) {
                    blockPos.setPos(pos);
                    List<Direction> validDirections = getShuffledDirectionsExcept(config, rand, direction.getOpposite());

                    for(int range = 0; range < config.searchRange; ++range) {
                        blockPos.func_239622_a_(pos, direction);
                        BlockState state = worldIn.getBlockState(blockPos);
                        if (!isAirOrWater(state) && !state.isIn(DDBlocks.AMBER.get())) {
                            break;
                        }

                        if (placeAmberIfPossible(worldIn, blockPos, state, config, rand, validDirections)) {
                            return true;
                        }
                    }
                }

                return false;
            }
        }
    }

    public static boolean placeAmberIfPossible(IWorld worldIn, BlockPos pos, BlockState state, AmberConfig config, Random rand, List<Direction> validDirections) {
        BlockPos.Mutable mutablePos = pos.toMutable();

        Iterator<Direction> directionIterator = validDirections.iterator();

        Direction direction;
        BlockState blockState;
        do {
            if (!directionIterator.hasNext()) {
                return false;
            }

            direction = directionIterator.next();
            blockState = worldIn.getBlockState(mutablePos.func_239622_a_(pos, direction));
        } while (!config.canBePlacedOn(blockState.getBlock()));

        MultifaceGemBlock multifaceGemBlock = (MultifaceGemBlock)DDBlocks.AMBER.get();
        BlockState amberPlacementState = multifaceGemBlock.getStateForPlacement(state, worldIn, pos, direction);
        if (amberPlacementState == null) {
            return false;
        } else {
            worldIn.setBlockState(pos, amberPlacementState, 3);
            if (rand.nextFloat() < config.chanceOfSpreading) {
                multifaceGemBlock.spreadFromFaceTowardRandomDirection(amberPlacementState, worldIn, pos, direction, rand);
            }

            return true;
        }
    }

    public static List getShuffledDirections(AmberConfig config, Random rand) {
        List<Direction> directions = Lists.newArrayList(config.validDirections);
        Collections.shuffle(directions, rand);
        return directions;
    }

    public static List getShuffledDirectionsExcept(AmberConfig config, Random rand, Direction invalidDirection) {
        List<Direction> directions = config.validDirections.stream().filter((direction) -> direction != invalidDirection).collect(Collectors.toList());
        Collections.shuffle(directions, rand);
        return directions;
    }

    private static boolean isAirOrWater(BlockState state) {
        return state.isAir() || state.isIn(Blocks.WATER);
    }
}