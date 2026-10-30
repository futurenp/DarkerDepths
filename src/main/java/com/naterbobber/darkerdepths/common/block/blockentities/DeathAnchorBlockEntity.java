package com.naterbobber.darkerdepths.common.block.blockentities;

import com.naterbobber.darkerdepths.common.init.DDBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class DeathAnchorBlockEntity extends BlockEntity {
    public DeathAnchorBlockEntity(BlockPos pos, BlockState state) {
        super(DDBlockEntityTypes.DEATH_ANCHOR.get(), pos, state);
    }
}
