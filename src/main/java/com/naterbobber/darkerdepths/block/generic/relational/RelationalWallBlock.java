package com.naterbobber.darkerdepths.block.generic.relational;

import com.naterbobber.darkerdepths.block.generic.IRelationalBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.WallBlock;

public class RelationalWallBlock extends WallBlock implements IRelationalBlock {
    private final Block baseBlock;
    public RelationalWallBlock(Block baseBlock) {
        super(baseBlock.properties());
        this.baseBlock = baseBlock;
    }

    @Override
    public Block getBaseBlock() {
        return baseBlock;
    }
}
