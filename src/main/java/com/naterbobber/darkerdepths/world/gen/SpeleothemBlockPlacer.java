package com.naterbobber.darkerdepths.world.gen;

import com.naterbobber.darkerdepths.blocks.Speleothem;
import net.minecraft.block.BlockState;
import net.minecraft.state.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.blockplacer.SimpleBlockPlacer;

import java.util.Random;

public class SpeleothemBlockPlacer extends SimpleBlockPlacer {

    @Override
    public void func_225567_a_(IWorld p_225567_1_, BlockPos p_225567_2_, BlockState p_225567_3_, Random p_225567_4_) {
        if (p_225567_2_.getY() < 62 ) {
            p_225567_1_.setBlockState(p_225567_2_, p_225567_3_, 2);
        }
    }
}
