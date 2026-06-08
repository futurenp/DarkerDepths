package com.naterbobber.darkerdepths.block.generic.relational;

import com.naterbobber.darkerdepths.block.generic.IRelationalBlock;
import com.naterbobber.darkerdepths.block.generic.VerticalSlabBlock;
import net.minecraft.world.level.block.Block;

public class RelationalVerticalSlabBlock extends VerticalSlabBlock implements IRelationalBlock {
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
