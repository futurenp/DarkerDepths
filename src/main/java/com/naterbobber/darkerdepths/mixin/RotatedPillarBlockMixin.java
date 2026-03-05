package com.naterbobber.darkerdepths.mixin;

import com.naterbobber.darkerdepths.init.DDBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(RotatedPillarBlock.class)
public class RotatedPillarBlockMixin extends Block {

    public RotatedPillarBlockMixin(Properties properties) {
        super(properties);
    }

    @Override
    public void onPlace(BlockState blockState, Level level, BlockPos blockPos, BlockState oldState, boolean movedByPiston) {
        super.onPlace(blockState, level, blockPos, oldState, movedByPiston);

        if(!blockState.is(BlockTags.LOGS_THAT_BURN)) return;

        Block petrifiedLog;

        if(blockState.is(BlockTags.OVERWORLD_NATURAL_LOGS)) {
            petrifiedLog = DDBlocks.PETRIFIED_LOG.get();
        } else {
            petrifiedLog = DDBlocks.PETRIFIED_WOOD.get();
        }

        BlockState belowState = level.getBlockState(blockPos.below());
        if ((belowState.is(Blocks.SOUL_FIRE) || belowState.is(Blocks.SOUL_CAMPFIRE))) {
            level.setBlock(blockPos, petrifiedLog.defaultBlockState().setValue(RotatedPillarBlock.AXIS, blockState.getValue(RotatedPillarBlock.AXIS)), 2);
            level.levelEvent(1501, blockPos, 0);
        }
    }
}
