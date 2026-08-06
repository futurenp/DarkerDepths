package com.naterbobber.darkerdepths.common.block.custom;

import com.naterbobber.darkerdepths.common.block.generic.ISunlightSensitiveGlowshroomBlock;
import com.naterbobber.darkerdepths.common.init.DDBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class GlowshroomPileusBlock extends Block implements ISunlightSensitiveGlowshroomBlock {

    public GlowshroomPileusBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        checkSunlight(level, pos);
    }

    @Override
    public BlockState getDeadGlowshroomState(BlockState existingState) {
        return DDBlocks.DEAD_GLOWSHROOM_PILEUS.get().defaultBlockState();
    }
}
