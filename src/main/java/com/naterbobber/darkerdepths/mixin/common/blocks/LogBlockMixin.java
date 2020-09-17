package com.naterbobber.darkerdepths.mixin.common.blocks;

import org.spongepowered.asm.mixin.Mixin;

import com.naterbobber.darkerdepths.init.BlockInit;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

//<>

@Mixin(RotatedPillarBlock.class)
public class LogBlockMixin extends Block {

	public LogBlockMixin(Properties properties) {
		super(properties);
	}
	
	@Override
	public void onBlockAdded(BlockState state, World worldIn, BlockPos pos, BlockState oldState, boolean isMoving) {
		if (this.reactWithNeighbors(worldIn, pos, state)) {
			worldIn.getPendingBlockTicks().scheduleTick(pos, state.getBlockState().getBlock(), 10);
		}
	}
	
	@Override
	public void neighborChanged(BlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
		if (this.reactWithNeighbors(worldIn, pos, state)) {
			worldIn.getPendingBlockTicks().scheduleTick(pos, state.getBlockState().getBlock(), 10);
		}
	}
	
	private boolean reactWithNeighbors(World worldIn, BlockPos pos, BlockState state) {
		Block block = BlockInit.petrified_log;
		BlockState blockstate = worldIn.getBlockState(pos);
		boolean isAboveSoulfire = worldIn.getBlockState(pos.down()) == Blocks.SOUL_FIRE.getDefaultState() && worldIn.getBlockState(pos.down(2)) == Blocks.SOUL_SOIL.getDefaultState();
		if (isAboveSoulfire && worldIn.getBlockState(pos).isIn(BlockTags.LOGS)) {
			worldIn.setBlockState(pos, block.getDefaultState().with(RotatedPillarBlock.AXIS, blockstate.get(RotatedPillarBlock.AXIS)), 11);
			worldIn.addParticle(ParticleTypes.SOUL, pos.getX(), pos.getY(), pos.getZ(), 1.0f, 1.0f, 1.0f);
			worldIn.playEvent(1501, pos, 0);
		}
		return false;
	}
}