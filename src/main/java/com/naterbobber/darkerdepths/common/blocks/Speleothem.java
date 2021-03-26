package com.naterbobber.darkerdepths.common.blocks;

import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.PushReaction;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;

import javax.annotation.Nullable;

//<>

@SuppressWarnings("deprecation")
public class Speleothem extends Block implements IWaterLoggable {
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final BooleanProperty HANGING = BlockStateProperties.HANGING;
    protected static final VoxelShape BOTTOM  = VoxelShapes.or(Block.makeCuboidShape(6.5D, 0.0D, 6.5D, 9.5D, 5.0D, 9.5D), Block.makeCuboidShape(5.5D, 5.0D, 5.5D, 10.5D, 11.0D, 10.5D), Block.makeCuboidShape(4.0D, 11.0D, 4.0D, 12.0D, 16.0D, 12.0D));
    protected static final VoxelShape TOP = VoxelShapes.or(Block.makeCuboidShape(6.5D, 11.0D, 6.5D, 9.5D, 16.0D, 9.5D), Block.makeCuboidShape(5.5D, 5.0D, 5.5D, 10.5D, 11.0D, 10.5D), Block.makeCuboidShape(4.0D, 0.0D, 4.0D, 12.0D, 5.0D, 12.0D));

    public Speleothem() {
        super(Block.Properties.create(Material.ROCK)
                .hardnessAndResistance(1.5f,6.0f)
                .sound(SoundType.STONE)
                .harvestLevel(0)
                .setRequiresTool());
        this.setDefaultState(this.stateContainer.getBaseState().with(HANGING, Boolean.valueOf(false)).with(WATERLOGGED, false));
    }

    @Nullable
    public BlockState getStateForPlacement(BlockItemUseContext context) {
    	FluidState fluidstate = context.getWorld().getFluidState(context.getPos());
    	Direction[] i = context.getNearestLookingDirections();
    	int j = i.length;
    	
    	for (int k = 0; k < j; ++k) {
    		Direction direction = i[k];
    		if (direction.getAxis() == Direction.Axis.Y) {
    			BlockState blockstate = this.getDefaultState().with(HANGING, direction == Direction.UP);
    			if (blockstate.isValidPosition(context.getWorld(), context.getPos())) {
    				return blockstate.with(WATERLOGGED, fluidstate.getFluid() == Fluids.WATER);
    			}
    		}
    	}

        return null;
    }

    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return state.get(HANGING) ? BOTTOM : TOP;
    }

    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(HANGING, WATERLOGGED);
    }

    public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
        Direction direction = func_220277_j(state).getOpposite();
        return Block.hasEnoughSolidSide(worldIn, pos.offset(direction), direction.getOpposite());
    }

    protected static Direction func_220277_j(BlockState p_220277_0_) {
        return p_220277_0_.get(HANGING) ? Direction.DOWN : Direction.UP;
    }

    public PushReaction getPushReaction(BlockState state) {
        return PushReaction.DESTROY;
    }

    public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
    	if (stateIn.get(WATERLOGGED)) {
    		worldIn.getPendingFluidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickRate(worldIn));
    	}
    	return func_220277_j(stateIn).getOpposite() == facing && !stateIn.isValidPosition(worldIn, currentPos) ? Blocks.AIR.getDefaultState() : super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
    }

	@Override
    public FluidState getFluidState(BlockState state) {
    	return state.get(WATERLOGGED) ? Fluids.WATER.getStillFluidState(false) : super.getFluidState(state);
    }
    
    public boolean allowsMovement(BlockState state, IBlockReader worldIn, BlockPos pos, PathType type) {
        return false;
    }
}