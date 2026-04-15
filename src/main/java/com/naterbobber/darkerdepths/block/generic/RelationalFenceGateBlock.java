package com.naterbobber.darkerdepths.block.generic;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.state.properties.WoodType;

public class RelationalFenceGateBlock extends FenceGateBlock implements IRelationalBlock {
    private final Block baseBlock;
    public RelationalFenceGateBlock(Block baseBlock, WoodType type) {
        super(type, baseBlock.properties().forceSolidOn());
        this.baseBlock = baseBlock;
    }

    @Override
    public Block getBaseBlock() {
        return baseBlock;
    }
}
