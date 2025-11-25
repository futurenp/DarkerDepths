package com.naterbobber.darkerdepths.block.generic;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ButtonBlock;
import net.minecraft.world.level.block.state.properties.BlockSetType;

public class RelationalButtonBlock extends ButtonBlock implements IRelationalBlock {
    private final Block baseBlock;
    public RelationalButtonBlock(Block baseBlock, BlockSetType type, int ticksToStayPressed) {
        super(type, ticksToStayPressed, baseBlock.properties());
        this.baseBlock = baseBlock;
    }

    @Override
    public Block getBaseBlock() {
        return baseBlock;
    }
}
