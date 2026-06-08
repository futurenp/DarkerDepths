package com.naterbobber.darkerdepths.block.generic.relational;

import com.naterbobber.darkerdepths.block.generic.IRelationalBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.PressurePlateBlock;
import net.minecraft.world.level.block.state.properties.BlockSetType;

public class RelationalPressurePlateBlock extends PressurePlateBlock implements IRelationalBlock {
    private final Block baseBlock;
    public RelationalPressurePlateBlock(Block baseBlock, BlockSetType type) {
        super(type, baseBlock.properties());
        this.baseBlock = baseBlock;
    }

    @Override
    public Block getBaseBlock() {
        return baseBlock;
    }
}
