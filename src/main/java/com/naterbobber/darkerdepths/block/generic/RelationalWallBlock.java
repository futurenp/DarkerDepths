package com.naterbobber.darkerdepths.block.generic;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class RelationalWallBlock extends WallBlock implements IRelationalBlock {
    private final Block baseBlock;
    public RelationalWallBlock(Block baseBlock) {
        super(BlockBehaviour.Properties.copy(baseBlock));
        this.baseBlock = baseBlock;
    }

    @Override
    public Block getBaseBlock() {
        return baseBlock;
    }
}
