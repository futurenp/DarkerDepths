package com.naterbobber.darkerdepths.block.generic;

import net.minecraft.world.level.block.Block;

public class RelationalVerticalSlabBlock extends  VerticalSlabBlock implements IRelationalBlock{
    private final Block baseBlock;

    public RelationalVerticalSlabBlock(Block baseBlock) {
        super(baseBlock.properties());
        this.baseBlock = baseBlock;
    }

    @Override
    public Block getBaseBlock() {
        return baseBlock;
    }
}
