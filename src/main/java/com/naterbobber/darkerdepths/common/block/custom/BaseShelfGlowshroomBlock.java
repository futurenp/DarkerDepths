package com.naterbobber.darkerdepths.common.block.custom;

import com.naterbobber.darkerdepths.common.block.DDBlockStateProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;

import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.common.Tags;
import org.jetbrains.annotations.Nullable;

public class BaseShelfGlowshroomBlock extends Block {

    protected static final DirectionProperty FACING;
    protected static final BooleanProperty LARGE = DDBlockStateProperties.LARGE;
    protected static final VoxelShape NORTH_AABB;
    protected static final VoxelShape SOUTH_AABB;
    protected static final VoxelShape WEST_AABB;
    protected static final VoxelShape EAST_AABB;
    protected static final VoxelShape LARGE_NORTH_AABB;
    protected static final VoxelShape LARGE_SOUTH_AABB;
    protected static final VoxelShape LARGE_WEST_AABB;
    protected static final VoxelShape LARGE_EAST_AABB;

    static {
        FACING = HorizontalDirectionalBlock.FACING;
        NORTH_AABB = Block.box(3.0F, 5.0F, 8.0F, 13.0F, 11.0F, 16.0F);
        SOUTH_AABB = Block.box(3.0F, 5.0F, 0.0F, 13.0F, 11.0F, 8.0F);
        WEST_AABB = Block.box(8.0F, 5.0F, 3.0F, 16.0F, 11.0F, 13.0F);
        EAST_AABB = Block.box(0.0F, 5.0F, 3.0F, 8.0F, 11.0F, 13.0F);

        LARGE_NORTH_AABB = Block.box(0.0F, 3.0F, 3.0F, 16.0F, 13.0F, 16.0F);
        LARGE_SOUTH_AABB = Block.box(0.0F, 3.0F, 0.0F, 16.0F, 13.0F, 13.0F);
        LARGE_WEST_AABB = Block.box(3.0F, 3.0F, 0.0F, 16.0F, 13.0F, 16.0F);
        LARGE_EAST_AABB = Block.box(0.0F, 3.0F, 0.0F, 13.0F, 13.0F, 16.0F);
    }

    public BaseShelfGlowshroomBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(
                this.stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
                .setValue(LARGE, false));
    }

    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        var facing = state.getValue(FACING);
        if(!state.getValue(LARGE)) {
            return switch (facing) {
                case WEST -> WEST_AABB;
                case SOUTH -> SOUTH_AABB;
                case NORTH -> NORTH_AABB;
                default -> EAST_AABB;
            };
        } else {
            return switch (facing) {
                case WEST -> LARGE_WEST_AABB;
                case SOUTH -> LARGE_SOUTH_AABB;
                case NORTH -> LARGE_NORTH_AABB;
                default -> LARGE_EAST_AABB;
            };
        }
    }

    @Override
    protected BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        return direction.getOpposite() == state.getValue(FACING) && !state.canSurvive(level, pos) ? Blocks.AIR.defaultBlockState() : super.updateShape(state, direction, neighborState, level, pos, neighborPos);
    }

    @Override
    protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        var direction = state.getValue(FACING);
        var blockpos = pos.relative(direction.getOpposite());
        var blockstate = level.getBlockState(blockpos);
        return direction.getAxis().isHorizontal() && (blockstate.isFaceSturdy(level, blockpos, direction) || blockstate.is(Tags.Blocks.VILLAGER_FARMLANDS));
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        var blockstate = this.defaultBlockState();

        var direction = context.getClickedFace();
        if(direction.getAxis().isHorizontal()) {
            return blockstate.setValue(FACING, direction);
        }

        return null;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FACING);
        builder.add(LARGE);
    }
}
