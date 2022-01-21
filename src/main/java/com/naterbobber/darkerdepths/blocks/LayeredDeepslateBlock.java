package com.naterbobber.darkerdepths.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.lighting.LayerLightEngine;

import java.util.Random;

public class LayeredDeepslateBlock extends RotatedPillarBlock {

    public LayeredDeepslateBlock(Properties properties) {
        super(properties);
    }

    private static boolean canBeNylium(BlockState state, LevelReader world, BlockPos pos) {
        BlockPos blockpos = pos.above();
        BlockState blockstate = world.getBlockState(blockpos);
        int i = LayerLightEngine.getLightBlockInto(world, state, pos, blockstate, blockpos, Direction.UP, blockstate.getLightBlock(world, blockpos));
        return i < world.getMaxLightLevel();
    }

    @Override
    public void randomTick(BlockState state, ServerLevel world, BlockPos pos, Random random) {
        if (state.getValue(AXIS) == Direction.Axis.Y && !canBeNylium(state, world, pos)) {
            world.setBlockAndUpdate(pos, Blocks.DEEPSLATE.defaultBlockState());
        }
    }
}
