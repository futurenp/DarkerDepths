package com.naterbobber.darkerdepths.mixin.common.blocks;

import net.minecraft.world.server.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;

import com.naterbobber.darkerdepths.core.registries.DDBlocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

//<>

@Mixin(RotatedPillarBlock.class)
public class LogBlockMixin extends Block {
	public LogBlockMixin(Properties properties) {
		super(properties);
		properties.tickRandomly();
	}

	@Override
	public void onBlockAdded(BlockState state, World worldIn, BlockPos pos, BlockState oldState, boolean isMoving) {
		super.onBlockAdded(state, worldIn, pos, oldState, isMoving);
		worldIn.getPendingBlockTicks().scheduleTick(pos, this, 40);
	}

	@Override
	public void tick(BlockState state, ServerWorld worldIn, BlockPos pos, Random rand) {
		super.tick(state, worldIn, pos, rand);
		Block block = DDBlocks.PETRIFIED_LOG.get();
		if (worldIn.getBlockState(pos.down()).matchesBlock(Blocks.SOUL_FIRE) && worldIn.getBlockState(pos.down(2)).matchesBlock(Blocks.SOUL_SOIL) && worldIn.getBlockState(pos).isIn(BlockTags.LOGS_THAT_BURN)) {
			worldIn.setBlockState(pos, block.getDefaultState().with(RotatedPillarBlock.AXIS, state.get(RotatedPillarBlock.AXIS)), 2);
			worldIn.playEvent(1501, pos, 0);
		}
	}
}