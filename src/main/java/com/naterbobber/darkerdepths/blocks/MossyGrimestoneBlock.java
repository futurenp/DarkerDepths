package com.naterbobber.darkerdepths.blocks;

import com.naterbobber.darkerdepths.init.DDBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.lighting.LayerLightEngine;
import net.minecraftforge.common.IPlantable;

public class MossyGrimestoneBlock extends Block implements BonemealableBlock {

    public MossyGrimestoneBlock(Properties properties) {
        super(properties);
    }

    private static boolean isDarkEnough(BlockState state, LevelReader world, BlockPos pos) {
        BlockPos above = pos.above();
        BlockState aboveState = world.getBlockState(above);
        int lightLevel = LayerLightEngine.getLightBlockInto(world, state, pos, aboveState, above, Direction.UP, aboveState.getLightBlock(world, above));
        return lightLevel < world.getMaxLightLevel();
    }

    @Override
    public void randomTick(BlockState state, ServerLevel worldIn, BlockPos pos, RandomSource random) {
        if (!isDarkEnough(state, worldIn, pos)) {
            worldIn.setBlockAndUpdate(pos, DDBlocks.GRIMESTONE.get().defaultBlockState());
        }
    }

    @Override
    public boolean isValidBonemealTarget(BlockGetter worldIn, BlockPos pos, BlockState state, boolean isClient) {
        return worldIn.getBlockState(pos.above()).isAir();
    }

    @Override
    public boolean isBonemealSuccess(Level worldIn, RandomSource random, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel worldIn, RandomSource rand, BlockPos pos, BlockState state) {
        BlockPos blockpos = pos.above();
        BlockState blockstate = DDBlocks.MOSSY_GRIMESTONE.get().defaultBlockState();

        start:
        for(int range = 0; range < 128; ++range) {
            BlockPos blockpos1 = blockpos;

            for(int tries = 0; tries < range / 16; ++tries) {
                blockpos1 = blockpos1.offset(rand.nextInt(3) - 1, (rand.nextInt(3) - 1) * rand.nextInt(3) / 2, rand.nextInt(3) - 1);
                if (!worldIn.getBlockState(blockpos1.below()).is(this) || worldIn.getBlockState(blockpos1).isCollisionShapeFullBlock(worldIn, blockpos1)) {
                    continue start;
                }
            }

            BlockState blockstate2 = worldIn.getBlockState(blockpos1);
            if (blockstate2.is(blockstate.getBlock()) && rand.nextInt(10) == 0) {
                ((BonemealableBlock)blockstate.getBlock()).performBonemeal(worldIn, rand, blockpos1, blockstate2);
            }

            if (blockstate2.isAir()) {
                BlockState blockstate1;
                if (rand.nextInt(10) == 0) {
                    blockstate1 = DDBlocks.GLOWSHROOM.get().defaultBlockState().setValue(GlowshroomBlock.CLUSTERS_1_3, Mth.nextInt(rand, 1, 3));
                } else {
                    blockstate1 = DDBlocks.MOSSY_SPROUTS.get().defaultBlockState();
                }

                if (blockstate1.canSurvive(worldIn, blockpos1)) {
                    worldIn.setBlock(blockpos1, blockstate1, 3);
                }
            }
        }
    }

    @Override
    public boolean canSustainPlant(BlockState state, BlockGetter world, BlockPos pos, Direction facing, IPlantable plantable) {
        return true;
    }
}
