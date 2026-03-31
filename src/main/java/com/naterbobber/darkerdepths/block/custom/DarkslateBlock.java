package com.naterbobber.darkerdepths.block.custom;

import com.naterbobber.darkerdepths.block.DDBlockStateProperties;
import com.naterbobber.darkerdepths.block.generic.HeatableBlock;
import com.naterbobber.darkerdepths.util.DDTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import org.jetbrains.annotations.Nullable;

public class DarkslateBlock extends RotatedPillarBlock implements HeatableBlock {
    private static final IntegerProperty HEAT_LEVEL = DDBlockStateProperties.HEAT_LEVEL;
    private static final BooleanProperty INITIALIZED = DDBlockStateProperties.INITIALIZED;

    public DarkslateBlock(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(this.defaultBlockState()
                .setValue(HEAT_LEVEL, 0).setValue(INITIALIZED, false)
        );
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(HEAT_LEVEL).add(INITIALIZED);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        int neighborHeat = getHighestNeighborHeat(context.getLevel(), context.getClickedPos());
        return this.defaultBlockState().setValue(HEAT_LEVEL, calculateNewHeat(neighborHeat)).setValue(INITIALIZED, true);
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos currentPos, BlockPos neighborPos) {
        if (!level.isClientSide() && (state.is(DDTags.Blocks.HEAT_PROVIDER) || state.getFluidState().is(DDTags.Fluids.HEAT_PROVIDER))) {
            level.scheduleTick(currentPos, this, 10);
        }
        return super.updateShape(state, direction, neighborState, level, currentPos, neighborPos);
    }

    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if(!state.getValue(INITIALIZED)) {
            sendHeatUpdate(level, pos, state.setValue(INITIALIZED, true));
            return;
        }
        sendHeatUpdate(level, pos, state);
    }

    @Override
    protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if(state.getValue(INITIALIZED)) return;

        int highestNeighborHeat = getHighestNeighborHeat(level, pos);
        int newHeat = calculateNewHeat(highestNeighborHeat);

        if(newHeat == 0) {
            level.setBlock(pos, state.setValue(INITIALIZED, true), Block.UPDATE_NONE | Block.UPDATE_KNOWN_SHAPE);
        } else if (state.getValue(HEAT_LEVEL) != newHeat) {
            level.setBlock(pos, state.setValue(HEAT_LEVEL, newHeat).setValue(INITIALIZED, true), Block.UPDATE_ALL);
        }
    }
}
