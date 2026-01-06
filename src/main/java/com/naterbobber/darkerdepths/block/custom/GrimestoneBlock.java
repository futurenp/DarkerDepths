package com.naterbobber.darkerdepths.block.custom;

import com.naterbobber.darkerdepths.init.DDBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;

public class GrimestoneBlock extends RotatedPillarBlock implements BonemealableBlock {

    public GrimestoneBlock(Properties properties) {
        super(properties);
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader worldIn, BlockPos pos, BlockState p_50899_, boolean p_50900_) {
        if (worldIn.getBlockState(pos.above()).propagatesSkylightDown(worldIn, pos)) {
            for (BlockPos blockpos : BlockPos.betweenClosed(pos.offset(-1, -1, -1), pos.offset(1, 1, 1))) {
                if (worldIn.getBlockState(blockpos).getBlock() == DDBlocks.MOSSY_GRIMESTONE.get()) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean isBonemealSuccess(Level p_50901_, RandomSource p_50902_, BlockPos p_50903_, BlockState p_50904_) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel worldIn, RandomSource p_50894_, BlockPos pos, BlockState p_50896_) {
        boolean flag = false;
        for(BlockPos blockpos : BlockPos.betweenClosed(pos.offset(-1, -1, -1), pos.offset(1, 1, 1))) {
            BlockState blockstate = worldIn.getBlockState(blockpos);
            if (blockstate.is(DDBlocks.MOSSY_GRIMESTONE.get())) {
                flag = true;
            }
        }
        if (flag) {
            worldIn.setBlock(pos, DDBlocks.MOSSY_GRIMESTONE.get().defaultBlockState(), 3);
        }
    }
}
