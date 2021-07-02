package com.naterbobber.darkerdepths.common.blocks;

import com.naterbobber.darkerdepths.core.registries.DDBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;

import java.util.Random;

public class PorousBlock extends RotatedPillarBlock {

    public PorousBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void randomTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random) {
        if (worldIn.isAirBlock(pos.down()) && random.nextInt(10) == 0) {
            net.minecraftforge.common.ForgeHooks.onCropsGrowPost(worldIn, pos, state);
            worldIn.setBlockState(pos.down(), DDBlocks.AMBER.get().getDefaultState().with(AbstractGemStoneBlock.FACING, Direction.DOWN), 2);
        }
    }
}
