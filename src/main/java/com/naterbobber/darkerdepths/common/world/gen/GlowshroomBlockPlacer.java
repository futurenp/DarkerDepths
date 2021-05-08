package com.naterbobber.darkerdepths.common.world.gen;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.blockplacer.SimpleBlockPlacer;

import java.util.Random;

public class GlowshroomBlockPlacer extends SimpleBlockPlacer {

    @Override
    public void place(IWorld world, BlockPos pos, BlockState state, Random random) {
        if (pos.getY() < 60 ) {
            world.setBlockState(pos, state, 2);
        }
    }
}
