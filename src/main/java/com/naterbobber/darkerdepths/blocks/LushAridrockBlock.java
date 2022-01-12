package com.naterbobber.darkerdepths.blocks;

import com.naterbobber.darkerdepths.init.DDBlocks;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.DeadBushBlock;
import net.minecraft.world.level.block.WaterlilyBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.lighting.LayerLightEngine;
import net.minecraftforge.common.IPlantable;

import java.util.Random;

public class LushAridrockBlock extends Block implements BonemealableBlock {

    public LushAridrockBlock(Properties properties) {
        super(properties);
    }

    @Override
    public boolean canSustainPlant(BlockState state, BlockGetter world, BlockPos pos, Direction facing, IPlantable plantable) {
        return !(plantable instanceof DeadBushBlock) && !(plantable instanceof WaterlilyBlock);
    }

    private static boolean isDarkEnough(BlockState state, LevelReader world, BlockPos pos) {
        int i = LayerLightEngine.getLightBlockInto(world, state, pos, world.getBlockState(pos.above()), pos.above(), Direction.UP, world.getBlockState(pos.above()).getLightBlock(world, pos.above()));
        return i < world.getMaxLightLevel();
    }

    @Override
    public void randomTick(BlockState state, ServerLevel world, BlockPos pos, Random random) {
        if (!isDarkEnough(state, world, pos)) {
            world.setBlockAndUpdate(pos, DDBlocks.ARIDROCK.get().defaultBlockState());
        }
    }

    @Override
    public boolean isValidBonemealTarget(BlockGetter world, BlockPos pos, BlockState state, boolean isClient) {
        return world.getBlockState(pos.above()).isAir();
    }

    @Override
    public boolean isBonemealSuccess(Level p_50901_, Random p_50902_, BlockPos p_50903_, BlockState p_50904_) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel worldIn, Random rand, BlockPos pos, BlockState p_50896_) {
        BlockPos blockpos = pos.above();
        BlockState blockstate = DDBlocks.LUSH_ARIDROCK.get().defaultBlockState();

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
                if (rand.nextInt(15) == 0) {
                    blockstate1 = DDBlocks.ALOE.get().defaultBlockState();
                } else {
                    blockstate1 = DDBlocks.LUSH_SPROUTS.get().defaultBlockState();
                }

                if (blockstate1.canSurvive(worldIn, blockpos1)) {
                    worldIn.setBlock(blockpos1, blockstate1, 3);
                }
            }
        }
    }
}
