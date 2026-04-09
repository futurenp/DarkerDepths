package com.naterbobber.darkerdepths.block.generic;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ButtonBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;

public class RelationalButtonBlock extends ButtonBlock implements IRelationalBlock {
    private final Block baseBlock;
    public RelationalButtonBlock(Block baseBlock, BlockSetType type, int ticksToStayPressed) {
        super(BlockBehaviour.Properties.copy(baseBlock), type, ticksToStayPressed, true);
        this.baseBlock = baseBlock;
    }

    @Override
    public Block getBaseBlock() {
        return baseBlock;
    }
}
