package com.naterbobber.darkerdepths.blocks;

import com.naterbobber.darkerdepths.init.DDBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.StandingSignBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.WoodType;

public class DDStandingSignBlock extends StandingSignBlock {

    public DDStandingSignBlock(WoodType type, BlockBehaviour.Properties properties) {
        super(type, properties);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return DDBlockEntityTypes.DD_SIGN.get().create(pos, state);
    }
}
