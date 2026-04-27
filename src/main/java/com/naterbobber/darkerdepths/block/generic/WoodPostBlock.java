package com.naterbobber.darkerdepths.block.generic;

import com.naterbobber.darkerdepths.block.DDBlockStateProperties;
import com.naterbobber.darkerdepths.block.blockstates.PostState;
import com.naterbobber.darkerdepths.util.BlockStateUtils;
import com.naterbobber.darkerdepths.util.DDTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.common.ItemAbility;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class WoodPostBlock extends Block implements SimpleWaterloggedBlock {

    private static final VoxelShape SHAPE_X = Block.box(0F, 6F, 6F, 16F, 10F, 10F);
    private static final VoxelShape SHAPE_Y = Block.box(6F, 0F, 6F, 10F, 16F, 10F);
    private static final VoxelShape SHAPE_Z = Block.box(6F, 6F, 0F, 10F, 10F, 16F);

    private static final VoxelShape ADD_ON_DOWN = Block.box(6F, 0F, 6F, 10F, 6F, 10F);
    private static final VoxelShape ADD_ON_UP = Block.box(6F, 10F, 6F, 10F, 16F, 10F);
    private static final VoxelShape ADD_ON_EAST = Block.box(0F, 6F, 6F, 6F, 10F, 10F);
    private static final VoxelShape ADD_ON_WEST = Block.box(10F, 6F, 6F, 16F, 10F, 10F);
    private static final VoxelShape ADD_ON_NORTH = Block.box(6F, 6F, 0F, 10F, 10F, 6F);
    private static final VoxelShape ADD_ON_SOUTH = Block.box(6F, 6F, 10F, 10F, 10F, 16F);

    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final EnumProperty<Direction.Axis> AXIS = BlockStateProperties.AXIS;

    protected Block strippedBlock = null;

    public WoodPostBlock(Properties properties) {
        super(properties);

        BlockState state = this.stateDefinition.any().setValue(WATERLOGGED, false).setValue(AXIS, Direction.Axis.Y);
        for(EnumProperty<PostState> prop : DDBlockStateProperties.CONNECT)
            state = state.setValue(prop, PostState.NONE);
        registerDefaultState(state);
    }

    @Override
    public @Nullable BlockState getToolModifiedState(BlockState state, UseOnContext context, ItemAbility itemAbility, boolean simulate) {
        if(strippedBlock != null) {
            if(itemAbility == ItemAbilities.AXE_STRIP) {
                return BlockStateUtils.copyState(state, strippedBlock.defaultBlockState());
            }
        }

        return super.getToolModifiedState(state, context, itemAbility, simulate);
    }


    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        VoxelShape shape = switch (state.getValue(AXIS)) {
            case X -> SHAPE_X;
            case Y -> SHAPE_Y;
            default -> SHAPE_Z;
        };

        if (state.getValue(DDBlockStateProperties.CONNECT_UP) == PostState.OTHER_POST) {
            shape = Shapes.or(shape, ADD_ON_UP);
        }
        if (state.getValue(DDBlockStateProperties.CONNECT_DOWN) == PostState.OTHER_POST) {
            shape = Shapes.or(shape, ADD_ON_DOWN);
        }
        if (state.getValue(DDBlockStateProperties.CONNECT_EAST) == PostState.OTHER_POST) {
            shape = Shapes.or(shape, ADD_ON_EAST);
        }
        if (state.getValue(DDBlockStateProperties.CONNECT_WEST) == PostState.OTHER_POST) {
            shape = Shapes.or(shape, ADD_ON_WEST);
        }
        if (state.getValue(DDBlockStateProperties.CONNECT_NORTH) == PostState.OTHER_POST) {
            shape = Shapes.or(shape, ADD_ON_NORTH);
        }
        if (state.getValue(DDBlockStateProperties.CONNECT_SOUTH) == PostState.OTHER_POST) {
            shape = Shapes.or(shape, ADD_ON_SOUTH);
        }

        return shape;
    }

    @Override
    public boolean propagatesSkylightDown(BlockState state, BlockGetter reader, BlockPos pos) {
        return !state.getValue(WATERLOGGED);
    }

    @Override
    @SuppressWarnings("deprecation")
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return getState(context.getLevel(), context.getClickedPos(), context.getClickedFace().getAxis());
    }

    @Override
    public void neighborChanged(BlockState state, Level worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
        super.neighborChanged(state, worldIn, pos, blockIn, fromPos, isMoving);

        BlockState newState = getState(worldIn, pos, state.getValue(AXIS));
        if(!newState.equals(state))
            worldIn.setBlockAndUpdate(pos, newState);
    }

    private BlockState getState(Level world, BlockPos pos, Direction.Axis axis) {
        BlockState state = defaultBlockState().setValue(WATERLOGGED, world.getFluidState(pos).getType() == Fluids.WATER).setValue(AXIS, axis);

        for(Direction direction : Direction.values()) {
            if(direction.getAxis() == axis)
                continue;

            BlockState sideState = world.getBlockState(pos.relative(direction));
            if((sideState.getBlock() instanceof ChainBlock && sideState.getValue(BlockStateProperties.AXIS) == direction.getAxis())
                    || (direction == Direction.DOWN && sideState.getBlock() instanceof LanternBlock && sideState.getValue(LanternBlock.HANGING))) {
                EnumProperty<PostState> prop = DDBlockStateProperties.CONNECT.get(direction.ordinal());
                state = state.setValue(prop, PostState.CHAIN);
            } else if((sideState.is(DDTags.Blocks.POSTS)) && sideState.getValue(BlockStateProperties.AXIS) == direction.getAxis()) {
                EnumProperty<PostState> prop = DDBlockStateProperties.CONNECT.get(direction.ordinal());
                state = state.setValue(prop, PostState.OTHER_POST);
            }
        }

        return state;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED, AXIS);
        for (EnumProperty<PostState> prop : DDBlockStateProperties.CONNECT) {
            builder.add(prop);
        }
    }
}
