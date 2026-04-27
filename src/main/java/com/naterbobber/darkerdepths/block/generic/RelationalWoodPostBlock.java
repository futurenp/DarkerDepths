package com.naterbobber.darkerdepths.block.generic;

import net.minecraft.world.level.block.Block;

public class RelationalWoodPostBlock extends WoodPostBlock implements IRelationalBlock{
    private final Block baseBlock;

    public RelationalWoodPostBlock(Block baseBlock) {
        super(baseBlock.properties().noOcclusion());
        this.baseBlock = baseBlock;
    }

    @Override
    public Block getBaseBlock() {
        return baseBlock;
    }
}
