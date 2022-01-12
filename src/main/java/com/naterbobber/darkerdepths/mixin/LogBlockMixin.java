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
public class LogBlockMixin extends Block {

    public LogBlockMixin(Properties properties) {
        super(properties);
    }

    @Override
    public void onPlace(BlockState state, Level world, BlockPos pos, BlockState oldState, boolean isMoving) {
        super.onPlace(state, world, pos, oldState, isMoving);
        world.scheduleTick(pos, this, 40);
    }

    @Override
    public void tick(BlockState state, ServerLevel world, BlockPos pos, Random random) {
        super.tick(state, world, pos, random);
        Block block = DDBlocks.PETRIFIED_LOG.get();
        if ((world.getBlockState(pos.below()).is(Blocks.SOUL_FIRE) || world.getBlockState(pos.below()).is(Blocks.SOUL_CAMPFIRE)) && world.getBlockState(pos).is(BlockTags.LOGS_THAT_BURN)) {
            world.setBlock(pos, block.defaultBlockState().setValue(RotatedPillarBlock.AXIS, state.getValue(RotatedPillarBlock.AXIS)), 2);
            world.levelEvent(1501, pos, 0);
        }
    }
}
