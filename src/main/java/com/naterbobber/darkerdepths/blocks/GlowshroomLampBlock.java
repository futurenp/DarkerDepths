package com.naterbobber.darkerdepths.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RedstoneLampBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

//Block needs custom sounds
public class GlowshroomLampBlock extends RedstoneLampBlock {

    public GlowshroomLampBlock(BlockBehaviour.Properties properties){
        super(properties);
    }

    @Override
    public void neighborChanged(BlockState state, Level level, BlockPos pos, Block neighborBlock, BlockPos neighborPos, boolean isMoving) {
        if (!level.isClientSide) {
            boolean isLit = state.getValue(LIT);

            if (isLit != level.hasNeighborSignal(pos)) {
                if (isLit) {
                    level.playSound(null, pos, SoundEvents.BEACON_DEACTIVATE, SoundSource.BLOCKS, 1.0F, 1.0F);
                    level.scheduleTick(pos, this, 4);
                } else {
                    level.playSound(null, pos, SoundEvents.BEACON_ACTIVATE, SoundSource.BLOCKS, 1.0F, 1.0F);
                    level.setBlock(pos, state.cycle(LIT), 2);
                }
            }
        }
    }
}
