package com.naterbobber.darkerdepths.block.custom.darkslate;

import com.naterbobber.darkerdepths.block.DDBlockStateProperties;
import com.naterbobber.darkerdepths.block.generic.ConnectedRotatablePillarBlock;
import com.naterbobber.darkerdepths.block.generic.IHeatableBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

import java.util.function.Function;

public class ConnectedRotatableDarkslatePillarBlock extends ConnectedRotatablePillarBlock implements IHeatableBlock {
    public ConnectedRotatableDarkslatePillarBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState()
                .setValue(HEAT_LEVEL, 0));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(HEAT_LEVEL);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        int neighborHeat = getHighestNeighborHeat(context.getLevel(), context.getClickedPos());
        return super.getStateForPlacement(context).setValue(HEAT_LEVEL, IHeatableBlock.calculateNewHeat(neighborHeat));
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos currentPos, BlockPos neighborPos) {
        level.scheduleTick(currentPos, this, 10);

        var currentAxis = state.getValue(AXIS);
        int heat = state.getValue(HEAT_LEVEL);

        if (direction.getAxis() == currentAxis) {
            return getState(level, currentPos, currentAxis, heat);
        }

        return super.updateShape(state, direction, neighborState, level, currentPos, neighborPos);
    }

    private BlockState getState(LevelAccessor level, BlockPos pos, Direction.Axis axis, int heat) {
        boolean connectsPos = getConnects(Direction.AxisDirection.POSITIVE, level, pos, axis);
        boolean connectsNeg = getConnects(Direction.AxisDirection.NEGATIVE, level, pos, axis);

        var pillarState = getPillarState(connectsPos, connectsNeg);

        return this.defaultBlockState()
                .setValue(DDBlockStateProperties.PILLAR_STATE, pillarState)
                .setValue(AXIS, axis)
                .setValue(HEAT_LEVEL, heat);
    }

    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        sendHeatUpdate(level, pos, state);
    }
}