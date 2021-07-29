package com.naterbobber.darkerdepths.common.blocks;

import com.naterbobber.darkerdepths.core.registries.DDBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.DeadBushBlock;
import net.minecraft.block.IGrowable;
import net.minecraft.block.LilyPadBlock;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.lighting.LightEngine;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.IPlantable;

import java.util.Random;

//<>

public class LushAridrockBlock extends Block implements IGrowable {
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

    @Override
    public boolean canGrow(IBlockReader worldIn, BlockPos pos, BlockState state, boolean isClient) {
        return worldIn.getBlockState(pos.up()).isAir();
    }

    @Override
    public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void grow(ServerWorld worldIn, Random rand, BlockPos pos, BlockState state) {
        BlockPos blockpos = pos.up();
        BlockState blockstate = DDBlocks.LUSH_ARIDROCK.get().getDefaultState();

        start:
        for(int range = 0; range < 128; ++range) {
            BlockPos blockpos1 = blockpos;

            for(int tries = 0; tries < range / 16; ++tries) {
                blockpos1 = blockpos1.add(rand.nextInt(3) - 1, (rand.nextInt(3) - 1) * rand.nextInt(3) / 2, rand.nextInt(3) - 1);
                if (!worldIn.getBlockState(blockpos1.down()).isIn(this) || worldIn.getBlockState(blockpos1).hasOpaqueCollisionShape(worldIn, blockpos1)) {
                    continue start;
                }
            }

            BlockState blockstate2 = worldIn.getBlockState(blockpos1);
            if (blockstate2.isIn(blockstate.getBlock()) && rand.nextInt(10) == 0) {
                ((IGrowable)blockstate.getBlock()).grow(worldIn, rand, blockpos1, blockstate2);
            }

            if (blockstate2.isAir()) {
                BlockState blockstate1;
                if (rand.nextInt(15) == 0) {
                    blockstate1 = DDBlocks.ALOE.get().getDefaultState();
                } else {
                    blockstate1 = DDBlocks.LUSH_SPROUTS.get().getDefaultState();
                }

                if (blockstate1.isValidPosition(worldIn, blockpos1)) {
                    worldIn.setBlockState(blockpos1, blockstate1, 3);
                }
            }
        }
    }
}