package com.naterbobber.darkerdepths.block.generic;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class RelationalSlabBlock extends SlabBlock implements IRelationalBlock {
    private final Block baseBlock;
    public RelationalSlabBlock(Block baseBlock) {
        super(BlockBehaviour.Properties.copy(baseBlock));
        this.baseBlock = baseBlock;
    }

    @Override
    public Block getBaseBlock() {
        return baseBlock;
    }
}
