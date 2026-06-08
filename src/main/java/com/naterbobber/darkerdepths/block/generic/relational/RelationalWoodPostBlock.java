package com.naterbobber.darkerdepths.block.generic.relational;

import com.naterbobber.darkerdepths.block.generic.IRelationalBlock;
import com.naterbobber.darkerdepths.block.generic.WoodPostBlock;
import net.minecraft.world.level.block.Block;

public class RelationalWoodPostBlock extends WoodPostBlock implements IRelationalBlock {
    private final Block baseBlock;

    public RelationalWoodPostBlock(Block baseBlock) {
        super(baseBlock.properties().noOcclusion());
        this.baseBlock = baseBlock;
    }

    public RelationalWoodPostBlock(Block baseBlock, WoodPostBlock strippedPostBlock) {
        super(baseBlock.properties().noOcclusion());
        this.baseBlock = baseBlock;
        this.strippedBlock = strippedPostBlock;
    }


    @Override
    public Block getBaseBlock() {
        return baseBlock;
    }
}
