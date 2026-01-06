package com.naterbobber.darkerdepths.block.blockentities;

import com.naterbobber.darkerdepths.init.DDBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.HangingSignBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class DDHangingSignBlockEntity extends HangingSignBlockEntity {
    public DDHangingSignBlockEntity(BlockPos pos, BlockState state) {
        super(pos, state);
    }

    @Override
    public BlockEntityType<?> getType() {
        return DDBlockEntityTypes.DD_HANGING_SIGN.get();
    }
}
