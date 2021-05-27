package com.naterbobber.darkerdepths.common.blocks;

import com.naterbobber.darkerdepths.core.registries.DDBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;

import java.util.Random;

public class PourusBlock extends RotatedPillarBlock {

    public PourusBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void randomTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random) {
        if (worldIn.isAirBlock(pos.down()) && random.nextInt(25) == 0 && !(state.get(AXIS) == Direction.Axis.Y)) {
            net.minecraftforge.common.ForgeHooks.onCropsGrowPost(worldIn, pos, state);
            worldIn.setBlockState(pos.down(), DDBlocks.AMBER.get().getDefaultState().with(AbstractGemStoneBlock.FACING, Direction.DOWN), 2);
        }
    }
}
