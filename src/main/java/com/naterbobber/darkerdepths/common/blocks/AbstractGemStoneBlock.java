package com.naterbobber.darkerdepths.common.blocks;

import net.minecraft.block.*;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;

@SuppressWarnings("deprecation")

//<>

public class AbstractGemStoneBlock extends Block implements IWaterLoggable {
	public static final BooleanProperty LIT = BlockStateProperties.LIT;
	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
	public static final DirectionProperty FACING = BlockStateProperties.FACING;
	protected final VoxelShape NORTH_SHAPE;
	protected final VoxelShape SOUTH_SHAPE;
	protected final VoxelShape EAST_SHAPE;
	protected final VoxelShape WEST_SHAPE;
	protected final VoxelShape UP_SHAPE;
	protected final VoxelShape DOWN_SHAPE;

	public AbstractGemStoneBlock(int x, int z, Properties properties) {
		super(properties);
		this.setDefaultState(this.stateContainer.getBaseState().with(LIT, true).with(WATERLOGGED, false).with(FACING, Direction.UP));
		this.UP_SHAPE = Block.makeCuboidShape(z, 0.0d, z, 16 - z, x, 16 - z);
		this.DOWN_SHAPE = Block.makeCuboidShape(z, 16 - x, z, 16 - z, 16.0D, 16 - z);
		this.NORTH_SHAPE = Block.makeCuboidShape(z, z, 16 - x, 16 - z, 16 - z, 16.0D);
		this.SOUTH_SHAPE = Block.makeCuboidShape(z, z, 0.0D, 16 - z, 16 - z, x);
		this.EAST_SHAPE = Block.makeCuboidShape(0.0D, z, z, x, 16 - z, 16 - z);
		this.WEST_SHAPE = Block.makeCuboidShape(16 - x, z, z, 16.0D, 16 - z, 16 - z);
	}

	public static boolean canAttachTo(IBlockReader worldIn, BlockPos pos, Direction direction) {
		BlockState state = worldIn.getBlockState(pos);
		return Block.doesSideFillSquare(state.getCollisionShape(worldIn, pos), direction.getOpposite());
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		Direction direction = state.get(FACING);
		switch (direction) {
			case NORTH:
				return this.NORTH_SHAPE;
			case SOUTH:
				return this.SOUTH_SHAPE;
			case EAST:
				return this.EAST_SHAPE;
			case WEST:
				return this.WEST_SHAPE;
			case DOWN:
				return this.DOWN_SHAPE;
			case UP:
			default:
				return this.UP_SHAPE;
		}
	}

	@Override
	public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
		Direction direction = state.get(FACING);
		BlockPos blockPos = pos.offset(direction.getOpposite());
		return worldIn.getBlockState(blockPos).isSolidSide(worldIn, blockPos, direction);
	}

	@Override
	public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
		if (stateIn.get(WATERLOGGED)) {
			worldIn.getPendingFluidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickRate(worldIn));
		}
		return facing == stateIn.get(FACING).getOpposite() && !stateIn.isValidPosition(worldIn, currentPos) ? Blocks.AIR.getDefaultState() : super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
	}

	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		IWorldReader worldIn = context.getWorld();
		BlockPos pos = context.getPos();
		return this.getDefaultState().with(WATERLOGGED, worldIn.getFluidState(pos).getFluid() == Fluids.WATER).with(FACING, context.getFace());
	}

	@Override
	public BlockState rotate(BlockState state, Rotation rot) {
		return state.with(FACING, rot.rotate(state.get(FACING)));
	}

	@Override
	public BlockState mirror(BlockState state, Mirror mirrorIn) {
		return state.with(FACING, mirrorIn.mirror(state.get(FACING)));
	}

	@Override
	public FluidState getFluidState(BlockState state) {
		return state.get(WATERLOGGED) ? Fluids.WATER.getStillFluidState(false) : super.getFluidState(state);
	}

	@Override
	protected void fillStateContainer(Builder<Block, BlockState> builder) {
		builder.add(WATERLOGGED, LIT, FACING);
	}
}