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

public class ConnectedPillarBlock extends Block {
    private static final EnumProperty<PillarState> PILLAR_STATE = DDBlockStateProperties.PILLAR_STATE;
    public ConnectedPillarBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(PILLAR_STATE, PillarState.DEFAULT));
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return getState(context.getLevel(), context.getClickedPos());
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        if ((direction == Direction.UP || direction == Direction.DOWN) && !state.hasProperty(BlockStateProperties.AXIS)) {
            return getState(level, pos);
        }
        return super.updateShape(state, direction, neighborState, level, pos, neighborPos);
    }

    private BlockState getState(LevelAccessor level, BlockPos pos) {
        boolean above = level.getBlockState(pos.above()).is(this);
        boolean below = level.getBlockState(pos.below()).is(this);

        var pillarState = getPillarState(above, below);

        return this.defaultBlockState().setValue(PILLAR_STATE, pillarState);
    }

    protected PillarState getPillarState(boolean above, boolean below) {
        PillarState state;

        if (above && below) {
            state = PillarState.MIDDLE;
        } else if (above) {
            state = PillarState.LOWER;
        } else if (below) {
            state = PillarState.UPPER;
        } else {
            state = PillarState.DEFAULT;
        }

        return state;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(PILLAR_STATE);
    }
}