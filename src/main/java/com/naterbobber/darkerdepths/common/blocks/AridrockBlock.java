package com.naterbobber.darkerdepths.common.blocks;

import com.naterbobber.darkerdepths.core.registries.DDBlocks;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.BushBlock;
import net.minecraft.block.DeadBushBlock;
import net.minecraft.block.IGrowable;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.IPlantable;

import java.util.Random;

public class AridrockBlock extends Block implements IGrowable {

    public AridrockBlock(AbstractBlock.Properties properties) {
        super(properties);
    }

    @Override
    public boolean canGrow(IBlockReader worldIn, BlockPos pos, BlockState state, boolean isClient) {
        if (!worldIn.getBlockState(pos.up()).propagatesSkylightDown(worldIn, pos)) {
            return false;
        } else {
            for(BlockPos blockpos : BlockPos.getAllInBoxMutable(pos.add(-1, -1, -1), pos.add(1, 1, 1))) {
                if (worldIn.getBlockState(blockpos).getBlock() == DDBlocks.LUSH_ARIDROCK.get()) {
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
            if (blockstate.isIn(DDBlocks.LUSH_ARIDROCK.get())) {
                flag = true;
            }
        }
        if (flag) {
            worldIn.setBlockState(pos, DDBlocks.LUSH_ARIDROCK.get().getDefaultState(), 3);
        }
    }

    @Override
    public boolean canSustainPlant(BlockState state, IBlockReader world, BlockPos pos, Direction facing, IPlantable plantable) {
        return plantable instanceof BushBlock || plantable instanceof DeadBushBlock;
    }
}
