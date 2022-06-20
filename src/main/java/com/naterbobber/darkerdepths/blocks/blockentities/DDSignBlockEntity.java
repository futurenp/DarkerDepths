package com.naterbobber.darkerdepths.blocks.blockentities;

import com.naterbobber.darkerdepths.init.DDBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class DDSignBlockEntity extends SignBlockEntity {

    public DDSignBlockEntity(BlockPos pos, BlockState state) {
        super(pos, state);
    }

    @Override
    public BlockEntityType<?> getType() {
        return DDBlockEntities.DD_SIGN.get();
    }
}
