package com.naterbobber.darkerdepths.common.blocks;

import com.naterbobber.darkerdepths.core.registries.DDBlocks;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.IGrowable;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.Random;

public class GrimestoneBlock extends RotatedPillarBlock implements IGrowable {

    public GrimestoneBlock(AbstractBlock.Properties properties) {
        super(properties);
    }

    @Override
    public boolean canGrow(IBlockReader worldIn, BlockPos pos, BlockState state, boolean isClient) {
        if (!worldIn.getBlockState(pos.up()).propagatesSkylightDown(worldIn, pos)) {
            return false;
        } else {
            for(BlockPos blockpos : BlockPos.getAllInBoxMutable(pos.add(-1, -1, -1), pos.add(1, 1, 1))) {
                if (worldIn.getBlockState(blockpos).getBlock() == DDBlocks.MOSSY_GRIMESTONE.get()) {
                    return true;
                }
            }
            return false;
        }
    }

    @Override
    public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void grow(ServerWorld worldIn, Random rand, BlockPos pos, BlockState state) {
        boolean flag = false;
        for(BlockPos blockpos : BlockPos.getAllInBoxMutable(pos.add(-1, -1, -1), pos.add(1, 1, 1))) {
            BlockState blockstate = worldIn.getBlockState(blockpos);
            if (blockstate.isIn(DDBlocks.MOSSY_GRIMESTONE.get())) {
                flag = true;
            }
        }
        if (flag) {
            worldIn.setBlockState(pos, DDBlocks.MOSSY_GRIMESTONE.get().getDefaultState(), 3);
        }
    }
}
