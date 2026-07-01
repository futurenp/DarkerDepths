package com.naterbobber.darkerdepths.block.custom;

import com.naterbobber.darkerdepths.init.DDBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class GlowshroomPileusBlock extends Block {

    public GlowshroomPileusBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        var brightness = level.getBrightness(LightLayer.SKY, pos.above());
        if(brightness >= 7) {
            level.setBlock(pos, DDBlocks.DEAD_GLOWSHROOM_PILEUS.get().defaultBlockState(), Block.UPDATE_ALL);
        }

        super.randomTick(state, level, pos, random);
    }
}
