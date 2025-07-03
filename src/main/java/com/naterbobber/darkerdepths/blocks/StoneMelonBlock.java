package com.naterbobber.darkerdepths.blocks;

import com.naterbobber.darkerdepths.init.DDBlockStateProperties;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

public class StoneMelonBlock extends Block {
    public static final IntegerProperty CRYSTAL_LEVEL = DDBlockStateProperties.CRYSTAL_LEVEL;

    public StoneMelonBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(CRYSTAL_LEVEL, 0));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(CRYSTAL_LEVEL);
    }

}
