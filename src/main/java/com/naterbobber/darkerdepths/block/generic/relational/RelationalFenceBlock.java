package com.naterbobber.darkerdepths.block.generic.relational;

import com.naterbobber.darkerdepths.block.generic.IRelationalBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FenceBlock;

public class RelationalFenceBlock extends FenceBlock implements IRelationalBlock {
    private final Block baseBlock;
    public RelationalFenceBlock(Block baseBlock) {
        super(baseBlock.properties());
        this.baseBlock = baseBlock;
    }

    @Override
    public Block getBaseBlock() {
        return baseBlock;
    }
}
