package com.naterbobber.darkerdepths.common.block.blockentities;

import com.naterbobber.darkerdepths.common.init.DDBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class VoidSoulTorchBlockEntity extends BlockEntity {
    public VoidSoulTorchBlockEntity(BlockPos pos, BlockState state) {
        super(DDBlockEntityTypes.VOID_SOUL_TORCH.get(), pos, state);
    }
}
