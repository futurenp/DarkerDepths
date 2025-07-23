package com.naterbobber.darkerdepths.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;

import javax.annotation.Nullable;

public class AridrockPillarBlock extends Block {
    public static final EnumProperty<PillarState> PILLAR_STATE = EnumProperty.create("pillar_state", PillarState.class);

    public AridrockPillarBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(PILLAR_STATE, PillarState.DEFAULT));
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return getState(context.getLevel(), context.getClickedPos());
    }

    @Override
    public BlockState updateShape(BlockState pState, Direction pDirection, BlockState pNeighborState, LevelAccessor pLevel, BlockPos pCurrentPos, BlockPos pNeighborPos) {
        if (pDirection == Direction.UP || pDirection == Direction.DOWN) {
            return getState(pLevel, pCurrentPos);
        }
        return super.updateShape(pState, pDirection, pNeighborState, pLevel, pCurrentPos, pNeighborPos);
    }

    private BlockState getState(LevelAccessor level, BlockPos pos) {
        BlockState blockAbove = level.getBlockState(pos.above());
        BlockState blockBelow = level.getBlockState(pos.below());

        boolean isPillarAbove = blockAbove.getBlock() instanceof AridrockPillarBlock;
        boolean isPillarBelow = blockBelow.getBlock() instanceof AridrockPillarBlock;

        PillarState currentState;

        if (isPillarAbove && isPillarBelow) {
            currentState = PillarState.MIDDLE;
        } else if (isPillarAbove) {
            currentState = PillarState.LOWER;
        } else if (isPillarBelow) {
            currentState = PillarState.UPPER;
        } else {
            currentState = PillarState.DEFAULT;
        }

        return this.defaultBlockState().setValue(PILLAR_STATE, currentState);
    }
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(PILLAR_STATE);
    }

    public enum PillarState implements StringRepresentable {
        DEFAULT("default"),
        LOWER("lower"),
        MIDDLE("middle"),
        UPPER("upper");

        private final String name;

        PillarState(String name) {
            this.name = name;
        }

        @Override
        public String getSerializedName() {
            return this.name;
        }

        @Override
        public String toString() {
            return this.name;
        }
    }
}
