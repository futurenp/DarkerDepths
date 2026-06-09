package com.naterbobber.darkerdepths.block.custom.darkslate;

import com.google.common.collect.ImmutableMap;
import com.naterbobber.darkerdepths.block.generic.IHeatableBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Map;

public class DarkslateWallBlock extends WallBlock implements IHeatableBlock {
    public DarkslateWallBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState()
                .setValue(HEAT_LEVEL, 0));

        this.shapeByIndex = this.makeShapes(4.0F, 3.0F, 16.0F, 0.0F, 14.0F, 16.0F);
        this.collisionShapeByIndex = this.makeShapes(4.0F, 3.0F, 24.0F, 0.0F, 24.0F, 24.0F);
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

        return super.updateShape(state, direction, neighborState, level, currentPos, neighborPos);
    }

    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        sendHeatUpdate(level, pos, state);
    }

    @Override
    public Map<BlockState, VoxelShape> makeShapes(float width, float depth, float wallPostHeight, float wallMinY, float wallLowHeight, float wallTallHeight) {
        Map<BlockState, VoxelShape> base = super.makeShapes(width, depth, wallPostHeight, wallMinY, wallLowHeight, wallTallHeight);
        ImmutableMap.Builder<BlockState, VoxelShape> expanded = ImmutableMap.builder();
        for (Map.Entry<BlockState, VoxelShape> entry : base.entrySet()) {
            for (int heat : HEAT_LEVEL.getPossibleValues()) {
                expanded.put(entry.getKey().setValue(HEAT_LEVEL, heat), entry.getValue());
            }
        }
        return expanded.build();
    }
}
