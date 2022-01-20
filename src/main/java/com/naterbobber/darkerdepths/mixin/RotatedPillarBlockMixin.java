package com.naterbobber.darkerdepths.mixin;

import com.naterbobber.darkerdepths.init.DDBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;

import java.util.Random;

@Mixin(RotatedPillarBlock.class)
public class RotatedPillarBlockMixin extends Block {

    public RotatedPillarBlockMixin(Properties properties) {
        super(properties);
    }

    @Override
    public void onPlace(BlockState p_60566_, Level p_60567_, BlockPos p_60568_, BlockState p_60569_, boolean p_60570_) {
        super.onPlace(p_60566_, p_60567_, p_60568_, p_60569_, p_60570_);
        p_60567_.scheduleTick(p_60568_, this, 40);
    }

    @Override
    public void tick(BlockState state, ServerLevel world, BlockPos pos, Random random) {
        super.tick(state, world, pos, random);
        BlockState blockState = world.getBlockState(pos);
        BlockState belowState = world.getBlockState(pos.below());
        if ((belowState.is(Blocks.SOUL_FIRE) || belowState.is(Blocks.SOUL_CAMPFIRE)) && blockState.is(BlockTags.LOGS_THAT_BURN)) {
            world.setBlock(pos, DDBlocks.PETRIFIED_LOG.get().defaultBlockState().setValue(RotatedPillarBlock.AXIS, blockState.getValue(RotatedPillarBlock.AXIS)), 2);
            world.levelEvent(1501, pos, 0);
        }
    }
}
