package com.naterbobber.darkerdepths.block.generic;

import com.naterbobber.darkerdepths.block.DDBlockStateProperties;
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
import java.util.function.Function;

public class ConnectedRotatablePillarBlock extends ConnectedPillarBlock {
    private static final EnumProperty<Direction.Axis> AXIS = BlockStateProperties.AXIS;

    public ConnectedRotatablePillarBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState()
                .setValue(AXIS, Direction.Axis.Y));
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        var axis = context.getClickedFace().getAxis();
        return getState(context.getLevel(), context.getClickedPos(), axis);
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        var currentAxis = state.getValue(AXIS);

        if (direction.getAxis() == currentAxis) {
            return getState(level, pos, currentAxis);
        }
        return super.updateShape(state, direction, neighborState, level, pos, neighborPos);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(AXIS);
    }

    private BlockState getState(LevelAccessor level, BlockPos pos, Direction.Axis axis) {
        Function<Direction.AxisDirection, Boolean> connects = (Direction.AxisDirection axisDirection) -> {
            var direction = Direction.get(axisDirection, axis);
            var state = level.getBlockState(pos.relative(direction));
            return state.is(this) && state.getValue(AXIS) == axis;
        };

        boolean connectsPos = connects.apply(Direction.AxisDirection.POSITIVE);
        boolean connectsNeg = connects.apply(Direction.AxisDirection.NEGATIVE);

        var pillarState = getPillarState(connectsPos, connectsNeg);

        return this.defaultBlockState()
                .setValue(DDBlockStateProperties.PILLAR_STATE, pillarState)
                .setValue(AXIS, axis);
    }
}