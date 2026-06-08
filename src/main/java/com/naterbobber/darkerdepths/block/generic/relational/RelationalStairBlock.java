package com.naterbobber.darkerdepths.block.generic.relational;

import com.naterbobber.darkerdepths.block.generic.IRelationalBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.StairBlock;

public class RelationalStairBlock extends StairBlock implements IRelationalBlock {
    private final Block baseBlock;
    public RelationalStairBlock(Block baseBlock) {
        super(baseBlock.defaultBlockState(), baseBlock.properties());
        this.baseBlock = baseBlock;
    }
    @Override
    public Block getBaseBlock() {
        return baseBlock;
    }

}
