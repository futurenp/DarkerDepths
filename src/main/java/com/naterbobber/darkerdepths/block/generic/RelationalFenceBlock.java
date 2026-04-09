package com.naterbobber.darkerdepths.block.generic;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class RelationalFenceBlock extends FenceBlock implements IRelationalBlock {
    private final Block baseBlock;
    public RelationalFenceBlock(Block baseBlock) {
        super(BlockBehaviour.Properties.copy(baseBlock));
        this.baseBlock = baseBlock;
    }

    @Override
    public Block getBaseBlock() {
        return baseBlock;
    }
}
