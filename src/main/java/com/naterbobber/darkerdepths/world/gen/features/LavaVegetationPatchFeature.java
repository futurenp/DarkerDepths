package com.naterbobber.darkerdepths.world.gen.features;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.VegetationPatchFeature;
import net.minecraft.world.level.levelgen.feature.configurations.VegetationPatchConfiguration;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.function.Predicate;

public class LavaVegetationPatchFeature extends VegetationPatchFeature {

    public LavaVegetationPatchFeature(Codec<VegetationPatchConfiguration> codec) {
        super(codec);
    }

    @Override
    protected Set<BlockPos> placeGroundPatch(WorldGenLevel world, VegetationPatchConfiguration config, Random random, BlockPos pos, Predicate<BlockState> statePredicate, int xRadius, int zRadius) {
        Set<BlockPos> set = super.placeGroundPatch(world, config, random, pos, statePredicate, xRadius, zRadius);
        Set<BlockPos> set1 = new HashSet<>();
        BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

        for(BlockPos blockpos : set) {
            if (!isExposed(world, blockpos, blockpos$mutableblockpos)) {
                set1.add(blockpos);
            }
        }

        for(BlockPos blockpos1 : set1) {
            world.setBlock(blockpos1, Blocks.LAVA.defaultBlockState(), 2);
        }

        return set1;
    }

    private static boolean isExposed(WorldGenLevel p_160656_, BlockPos p_160658_, BlockPos.MutableBlockPos p_160659_) {
        return isExposedDirection(p_160656_, p_160658_, p_160659_, Direction.NORTH) || isExposedDirection(p_160656_, p_160658_, p_160659_, Direction.EAST) || isExposedDirection(p_160656_, p_160658_, p_160659_, Direction.SOUTH) || isExposedDirection(p_160656_, p_160658_, p_160659_, Direction.WEST) || isExposedDirection(p_160656_, p_160658_, p_160659_, Direction.DOWN);
    }

    private static boolean isExposedDirection(WorldGenLevel p_160651_, BlockPos p_160652_, BlockPos.MutableBlockPos p_160653_, Direction p_160654_) {
        p_160653_.setWithOffset(p_160652_, p_160654_);
        return !p_160651_.getBlockState(p_160653_).isFaceSturdy(p_160651_, p_160653_, p_160654_.getOpposite());
    }

}
