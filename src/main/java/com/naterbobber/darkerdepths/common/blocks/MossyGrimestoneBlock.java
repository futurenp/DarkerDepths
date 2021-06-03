package com.naterbobber.darkerdepths.common.blocks;

import com.naterbobber.darkerdepths.core.registries.DDBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.IGrowable;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.lighting.LightEngine;
import net.minecraft.world.server.ServerWorld;

import java.util.Random;

//<>

public class MossyGrimestoneBlock extends Block implements IGrowable {

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
        BlockState blockstate = DDBlocks.MOSSY_GRIMESTONE.get().getDefaultState();

        start:
        for(int range = 0; range < 128; ++range) {
            BlockPos blockpos1 = blockpos;

            for(int tries = 0; tries < range / 16; ++tries) {
                blockpos1 = blockpos1.add(rand.nextInt(3) - 1, (rand.nextInt(3) - 1) * rand.nextInt(3) / 2, rand.nextInt(3) - 1);
                if (!worldIn.getBlockState(blockpos1.down()).matchesBlock(this) || worldIn.getBlockState(blockpos1).hasOpaqueCollisionShape(worldIn, blockpos1)) {
                    continue start;
                }
            }

            BlockState blockstate2 = worldIn.getBlockState(blockpos1);
            if (blockstate2.matchesBlock(blockstate.getBlock()) && rand.nextInt(10) == 0) {
                ((IGrowable)blockstate.getBlock()).grow(worldIn, rand, blockpos1, blockstate2);
            }

            if (blockstate2.isAir()) {
                BlockState blockstate1;
                if (rand.nextInt(10) == 0) {
                    blockstate1 = DDBlocks.GLOWSHROOM.get().getDefaultState().with(Glowshroom.CLUSTERS_1_3, MathHelper.nextInt(rand, 1, 3));
                } else {
                    blockstate1 = DDBlocks.MOSSY_SPROUTS.get().getDefaultState();
                }

                if (blockstate1.isValidPosition(worldIn, blockpos1)) {
                    worldIn.setBlockState(blockpos1, blockstate1, 3);
                }
            }
        }
    }
}