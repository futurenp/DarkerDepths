package com.naterbobber.darkerdepths.block.generic;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.WoodType;

public class RelationalFenceGateBlock extends FenceGateBlock implements IRelationalBlock {
    private final Block baseBlock;
    public RelationalFenceGateBlock(Block baseBlock, WoodType type) {
        super(BlockBehaviour.Properties.copy(baseBlock), type);
        this.baseBlock = baseBlock;
    }

    @Override
    public Block getBaseBlock() {
        return baseBlock;
    }
}
