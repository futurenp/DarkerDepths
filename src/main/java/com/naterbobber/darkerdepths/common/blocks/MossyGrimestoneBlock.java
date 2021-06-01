package com.naterbobber.darkerdepths.common.blocks;

import com.naterbobber.darkerdepths.core.registries.DDBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.lighting.LightEngine;
import net.minecraft.world.server.ServerWorld;

import java.util.Random;

//<>

public class MossyGrimestoneBlock extends Block {

    public MossyGrimestoneBlock(Properties properties) {
        super(properties);
    }

    private static boolean isDarkEnough(BlockState state, IWorldReader world, BlockPos pos) {
        int i = LightEngine.func_215613_a(world, state, pos, world.getBlockState(pos.up()), pos.up(), Direction.UP, world.getBlockState(pos.up()).getOpacity(world, pos.up()));
        return i < world.getMaxLightLevel();
    }

    @Override
    public void randomTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random) {
        if (!isDarkEnough(state, worldIn, pos)) {
            worldIn.setBlockState(pos, DDBlocks.GRIMESTONE.get().getDefaultState());
        }
    }

}