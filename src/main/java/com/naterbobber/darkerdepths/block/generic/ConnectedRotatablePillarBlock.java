package com.naterbobber.darkerdepths.block.generic;

import com.naterbobber.darkerdepths.block.DDBlockStateProperties;
import com.naterbobber.darkerdepths.block.blockstates.PillarState;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;

import javax.annotation.Nullable;

public class ConnectedRotatablePillarBlock extends Block {
    public static final EnumProperty<PillarState> PILLAR_STATE = DDBlockStateProperties.PILLAR_STATE;
    public static final EnumProperty<Direction.Axis> AXIS = BlockStateProperties.AXIS;

    public ConnectedRotatablePillarBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(PILLAR_STATE, PillarState.DEFAULT)
                .setValue(AXIS, Direction.Axis.Y));
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        var state = this.defaultBlockState();
        var axis = context.getClickedFace().getAxis();
        return getState(context.getLevel(), context.getClickedPos(), axis, state);
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos currentPos, BlockPos neighborPos) {
        var axis = state.getValue(AXIS);
        if (direction.getAxis() == state.getValue(AXIS)) {
            return getState(level, currentPos, axis, state);
        }
        return super.updateShape(state, direction, neighborState, level, currentPos, neighborPos);
    }

    private BlockState getState(LevelAccessor level, BlockPos pos, Direction.Axis axis, BlockState state) {
        var dirPositive = Direction.get(Direction.AxisDirection.POSITIVE, axis);
        var dirNegative = Direction.get(Direction.AxisDirection.NEGATIVE, axis);

        var statePos = level.getBlockState(pos.relative(dirPositive));
        var stateNeg = level.getBlockState(pos.relative(dirNegative));

        boolean connectsPos = statePos.is(this) && statePos.getValue(AXIS) == axis;
        boolean connectsNeg = stateNeg.is(this) && stateNeg.getValue(AXIS) == axis;

        PillarState currentState;

        if (connectsPos && connectsNeg) {
            currentState = PillarState.MIDDLE;
        } else if (connectsPos) {
            currentState = PillarState.LOWER;
        } else if (connectsNeg) {
            currentState = PillarState.UPPER;
        } else {
            currentState = PillarState.DEFAULT;
        }

        return state.setValue(PILLAR_STATE, currentState).setValue(AXIS, axis);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(PILLAR_STATE, AXIS);
    }
}