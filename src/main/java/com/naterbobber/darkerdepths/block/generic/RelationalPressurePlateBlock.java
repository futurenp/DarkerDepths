package com.naterbobber.darkerdepths.block.generic;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.PressurePlateBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;

public class RelationalPressurePlateBlock extends PressurePlateBlock implements IRelationalBlock {
    private final Block baseBlock;
    public RelationalPressurePlateBlock(PressurePlateBlock.Sensitivity sensitivity, Block baseBlock, BlockSetType type) {
        super(sensitivity, BlockBehaviour.Properties.copy(baseBlock), type);
        this.baseBlock = baseBlock;
    }

    @Override
    public Block getBaseBlock() {
        return baseBlock;
    }
}
