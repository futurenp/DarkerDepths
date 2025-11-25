package com.naterbobber.darkerdepths.block.generic;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;

public class RelationalSlabBlock extends SlabBlock implements IRelationalBlock {
    private final Block baseBlock;
    public RelationalSlabBlock(Block baseBlock) {
        super(baseBlock.properties());
        this.baseBlock = baseBlock;
    }

    @Override
    public Block getBaseBlock() {
        return baseBlock;
    }
}
