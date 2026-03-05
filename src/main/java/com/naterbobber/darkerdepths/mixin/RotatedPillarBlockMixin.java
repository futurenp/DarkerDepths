package com.naterbobber.darkerdepths.mixin;

import com.naterbobber.darkerdepths.init.DDBlocks;
import com.naterbobber.darkerdepths.util.DDTags;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;

import java.util.stream.Collectors;

@Mixin(RotatedPillarBlock.class)
public class RotatedPillarBlockMixin extends Block {

    public RotatedPillarBlockMixin(Properties properties) {
        super(properties);
    }

    @Override
    public void onPlace(BlockState blockState, Level level, BlockPos blockPos, BlockState oldState, boolean movedByPiston) {
        super.onPlace(blockState, level, blockPos, oldState, movedByPiston);
        checkForPetrifiedConversion(level, blockState, blockPos);
    }

    @Override
    public void neighborChanged(BlockState state, Level level, BlockPos pos, Block neighborBlock, BlockPos neighborPos, boolean movedByPiston) {
        super.neighborChanged(state, level, pos, neighborBlock, neighborPos, movedByPiston);
        checkForPetrifiedConversion(level, state, pos);
    }

    private void checkForPetrifiedConversion(Level level, BlockState blockState, BlockPos blockPos) {
        if(!blockState.is(BlockTags.LOGS_THAT_BURN)) return;

        BlockState belowState = level.getBlockState(blockPos.below());

        if(!(belowState.is(Blocks.SOUL_FIRE) || belowState.is(Blocks.SOUL_CAMPFIRE))) return;

        Block petrifiedLog;

        var tags = blockState.getTags().collect(Collectors.toSet());

        if(tags.contains(BlockTags.OVERWORLD_NATURAL_LOGS)) {
            petrifiedLog = DDBlocks.PETRIFIED_LOG.get();
        } else if ((tags.contains(DDTags.Blocks.STRIPPED_LOGS)) || tags.contains(DDTags.Blocks.STRIPPED_LOGS_FORGE)) {
            petrifiedLog = DDBlocks.STRIPPED_PETRIFIED_LOG.get();
        } else if ((tags.contains(DDTags.Blocks.STRIPPED_WOODS)) || tags.contains(DDTags.Blocks.STRIPPED_WOODS_FORGE)) {
            petrifiedLog = DDBlocks.STRIPPED_PETRIFIED_WOOD.get();
        } else {
            petrifiedLog = DDBlocks.PETRIFIED_WOOD.get();
        }

        level.setBlock(blockPos, petrifiedLog.defaultBlockState().setValue(RotatedPillarBlock.AXIS, blockState.getValue(RotatedPillarBlock.AXIS)), 2);
        level.levelEvent(1501, blockPos, 0);
    }
}
