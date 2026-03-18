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
        // Gets the axis of the face the player clicked on
        Direction.Axis axis = context.getClickedFace().getAxis();
        return getState(context.getLevel(), context.getClickedPos(), axis);
    }

    @Override
    public BlockState updateShape(BlockState pState, Direction pDirection, BlockState pNeighborState, LevelAccessor pLevel, BlockPos pCurrentPos, BlockPos pNeighborPos) {
        Direction.Axis currentAxis = pState.getValue(AXIS);

        // Only update if the neighbor changing is on the same axis as this block
        if (pDirection.getAxis() == currentAxis) {
            return getState(pLevel, pCurrentPos, currentAxis);
        }
        return super.updateShape(pState, pDirection, pNeighborState, pLevel, pCurrentPos, pNeighborPos);
    }

    private BlockState getState(LevelAccessor level, BlockPos pos, Direction.Axis axis) {
        // Determine the two directions to check based on the axis
        Direction dirPositive = Direction.get(Direction.AxisDirection.POSITIVE, axis);
        Direction dirNegative = Direction.get(Direction.AxisDirection.NEGATIVE, axis);

        BlockState statePos = level.getBlockState(pos.relative(dirPositive));
        BlockState stateNeg = level.getBlockState(pos.relative(dirNegative));

        // Check if neighbor is the same block AND shares the same axis
        boolean connectsPos = statePos.is(this) && statePos.getValue(AXIS) == axis;
        boolean connectsNeg = stateNeg.is(this) && stateNeg.getValue(AXIS) == axis;

        PillarState currentState;

        if (connectsPos && connectsNeg) {
            currentState = PillarState.MIDDLE;
        } else if (connectsPos) {
            // "Lower" relative to the positive direction
            currentState = PillarState.LOWER;
        } else if (connectsNeg) {
            // "Upper" relative to the negative direction
            currentState = PillarState.UPPER;
        } else {
            currentState = PillarState.DEFAULT;
        }

        return this.defaultBlockState()
                .setValue(PILLAR_STATE, currentState)
                .setValue(AXIS, axis);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(PILLAR_STATE, AXIS);
    }
}