package com.naterbobber.darkerdepths.block.blockentities;

import com.naterbobber.darkerdepths.init.DDBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class GlowshroomHeartBlockEntity extends BlockEntity {
    public GlowshroomHeartBlockEntity(BlockPos pos, BlockState state) {
        super(DDBlockEntityTypes.GLOWSHROOM_HEART.get(), pos, state);
    }

    public void clientTick(Level level, BlockPos blockPos, BlockState blockState) {
        //todo
        //circular particle ring?
    }

    public void tick(Level level, BlockPos blockPos, BlockState blockState) {
        //todo
        //scan for entities of type #glowshroom, heal them, or resistance?
        //what to do for player?
    }
}
