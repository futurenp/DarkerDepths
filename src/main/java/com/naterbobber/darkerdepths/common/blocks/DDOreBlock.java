package com.naterbobber.darkerdepths.common.blocks;

import com.naterbobber.darkerdepths.core.registries.DDBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

import java.util.Random;

public class DDOreBlock extends Block {
    public DDOreBlock(Properties properties) {
        super(properties);
    }

    protected int getExperience(Random rand) {
        if(this == DDBlocks.ARIDROCK_COAL_ORE.get() || this == DDBlocks.LIMESTONE_COAL_ORE.get()) {
            return MathHelper.nextInt(rand, 0, 2);
        } else if(this == DDBlocks.ARIDROCK_LAPIS_ORE.get() || this == DDBlocks.LIMESTONE_LAPIS_ORE.get()) {
            return MathHelper.nextInt(rand, 2, 5);
        } else {
            return this == DDBlocks.ARIDROCK_DIAMOND_ORE.get() || this == DDBlocks.LIMESTONE_DIAMOND_ORE.get() ? MathHelper.nextInt(rand, 3, 7) : 0;
        }
    }

    @Override
    public int getExpDrop(BlockState state, net.minecraft.world.IWorldReader reader, BlockPos pos, int fortune, int silktouch) {
        return silktouch == 0 ? this.getExperience(RANDOM) : 0;
    }
}
