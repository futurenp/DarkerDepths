package com.naterbobber.darkerdepths.common.blocks;

import com.naterbobber.darkerdepths.core.registries.DDBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.DeadBushBlock;
import net.minecraft.block.LilyPadBlock;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.lighting.LightEngine;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.IPlantable;

import java.util.Random;

//<>

public class LushAridrockBlock extends Block {
    public LushAridrockBlock(Properties properties) {
        super(properties);
    }

    @Override
    public boolean canSustainPlant(BlockState state, IBlockReader world, BlockPos pos, Direction facing, IPlantable plantable) {
        return !(plantable instanceof DeadBushBlock) && !(plantable instanceof LilyPadBlock);
    }

    private static boolean isDarkEnough(BlockState state, IWorldReader world, BlockPos pos) {
        int i = LightEngine.func_215613_a(world, state, pos, world.getBlockState(pos.up()), pos.up(), Direction.UP, world.getBlockState(pos.up()).getOpacity(world, pos.up()));
        return i < world.getMaxLightLevel();
    }

    @Override
    public void randomTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random) {
        if (!isDarkEnough(state, worldIn, pos)) {
            worldIn.setBlockState(pos, DDBlocks.ARIDROCK.get().getDefaultState());
        }
    }
}