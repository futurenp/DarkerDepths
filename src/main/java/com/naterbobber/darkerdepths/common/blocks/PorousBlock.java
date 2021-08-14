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
        if (worldIn.isAirBlock(pos.down()) && random.nextInt(17) == 0) {
            Direction direction = Direction.values()[random.nextInt(Direction.values().length)];
            worldIn.setBlockState(pos.offset(direction), DDBlocks.AMBER.get().getDefaultState().with(AbstractGemStoneBlock.FACING, direction), 2);
        }
    }
}
