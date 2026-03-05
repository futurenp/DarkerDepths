package com.naterbobber.darkerdepths.mixin;

import com.naterbobber.darkerdepths.init.DDBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SoulFireBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;

@Mixin(BlockBehaviour.class)
public abstract class SoulFireMixin {

    @Inject(method = "neighborChanged", at = @At("HEAD"))
    protected void DD$neighborChanged(BlockState state, Level level, BlockPos pos, Block neighborBlock, BlockPos neighborPos, boolean movedByPiston) {
        if((Object) this instanceof SoulFireBlock) {
            checkForLogAndReplace(level, state, pos, neighborPos);
        }
    }

    @Inject(method = "onPlace", at = @At("HEAD"))
    protected void DD$onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean movedByPiston) {
        if((Object) this instanceof SoulFireBlock) {
            checkForLogAndReplace(level, state, pos, pos.above());
        }
    }


    private void checkForLogAndReplace(Level level, BlockState state, BlockPos pos, BlockPos neighborPos) {
        if(pos.above() != neighborPos) {
            return;
        }

        BlockState aboveState = level.getBlockState(neighborPos);

        if(!aboveState.is(BlockTags.LOGS_THAT_BURN)) {
            return;
        }

        Block petrifiedLog;

        if(aboveState.is(BlockTags.OVERWORLD_NATURAL_LOGS)) {
            petrifiedLog = DDBlocks.PETRIFIED_LOG.get();
        } else {
            petrifiedLog = DDBlocks.PETRIFIED_WOOD.get();
        }

        level.setBlock(pos, petrifiedLog.defaultBlockState().setValue(RotatedPillarBlock.AXIS, state.getValue(RotatedPillarBlock.AXIS)), 2);
        level.levelEvent(1501, pos, 0);
    }
}
