package com.naterbobber.darkerdepths.common.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.DirectionalBlock;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.BooleanProperty;
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

public class AbstractGemBlock extends DirectionalBlock implements IWaterLoggable {
	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
	protected final VoxelShape NORTH_SHAPE;
	protected final VoxelShape SOUTH_SHAPE;
	protected final VoxelShape EAST_SHAPE;
	protected final VoxelShape WEST_SHAPE;
	protected final VoxelShape UP_SHAPE;
	protected final VoxelShape DOWN_SHAPE;
	
	public AbstractGemBlock(int x, int z, Properties properties) {
		super(properties);
		this.setDefaultState(this.stateContainer.getBaseState().with(FACING, Direction.UP).with(WATERLOGGED, false));
		this.UP_SHAPE 		= Block.makeCuboidShape((double)z, 0.0d, (double)z, (double)(16 - z), (double)x, (double)(16 - z));
		this.DOWN_SHAPE 	= Block.makeCuboidShape((double)z, (double)(16 - x), (double)z, (double)(16 - z), 16.0D, (double)(16 - z));
		this.NORTH_SHAPE 	= Block.makeCuboidShape((double)z, (double)z, (double)(16 - x), (double)(16 - z), (double)(16 - z), 16.0D);
		this.SOUTH_SHAPE 	= Block.makeCuboidShape((double)z, (double)z, 0.0D, (double)(16 - z), (double)(16 - z), (double)x);
		this.EAST_SHAPE 	= Block.makeCuboidShape(0.0D, (double)z, (double)z, (double)x, (double)(16 - z), (double)(16 - z));
		this.WEST_SHAPE 	= Block.makeCuboidShape((double)(16 - x), (double)z, (double)z, 16.0D, (double)(16 - z), (double)(16 - z));
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
	public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
		if (stateIn.get(WATERLOGGED)) {
			worldIn.getPendingFluidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickRate(worldIn));
		}
		return super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
	}
	
	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		BlockPos pos= context.getPos();
		Direction direction = context.getFace();
		IWorldReader world = context.getWorld();
		BlockState blockstate = context.getWorld().getBlockState(context.getPos().offset(direction.getOpposite()));
		return blockstate.isIn(this) && blockstate.get(FACING) == direction ? this.getDefaultState().with(FACING, direction.getOpposite()) : this.getDefaultState().with(FACING, direction).with(WATERLOGGED, world.getFluidState(pos).getFluid() == Fluids.WATER);
	}
	
	@Override
	protected void fillStateContainer(Builder<Block, BlockState> builder) {
		builder.add(FACING, WATERLOGGED);
	}
	
	@Override
	public FluidState getFluidState(BlockState state) {
		return state.get(WATERLOGGED) ? Fluids.WATER.getStillFluidState(false) : super.getFluidState(state);
	}
	
	@Override
	public boolean allowsMovement(BlockState state, IBlockReader worldIn, BlockPos pos, PathType type) {
		return false;
	}
}