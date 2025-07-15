package com.naterbobber.darkerdepths.blocks;

import com.naterbobber.darkerdepths.blocks.blockentities.TombBlockEntity;
import com.naterbobber.darkerdepths.init.DDBlockEntityTypes;
import com.naterbobber.darkerdepths.init.DDBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;



public class TombBlock extends BaseEntityBlock {
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    protected static final VoxelShape SHAPE = Block.box(0, 0, 0, 16, 15, 16);

    public TombBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection());
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        Direction facing = state.getValue(FACING);
        for (TombPart part : TombPart.values()) {
            if (part == TombPart.BOTTOM_LEFT) continue;
            BlockPos partPos = getPartPos(pos, part, facing);
            if (!level.getBlockState(partPos).canBeReplaced()) {
                return false;
            }
        }
        return super.canSurvive(state, level, pos);
    }

    @Override
    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean isMoving) {
        super.onPlace(state, level, pos, oldState, isMoving);
        if (!level.isClientSide && !isMoving) {
            Direction facing = state.getValue(FACING);
            for (TombPart part : TombPart.values()) {
                if (part == TombPart.BOTTOM_LEFT) continue;
                BlockPos partPos = getPartPos(pos, part, facing);
                level.setBlock(partPos, DDBlocks.TOMB_PART.get().defaultBlockState()
                        .setValue(TombPartBlock.PART, part)
                        .setValue(TombPartBlock.FACING, facing), 3);
            }
        }
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
        if (state.getBlock() != newState.getBlock()) {
            if (!level.isClientSide) {
                Direction facing = state.getValue(FACING);
                for (TombPart part : TombPart.values()) {
                    if (part == TombPart.BOTTOM_LEFT) continue;
                    BlockPos partPos = getPartPos(pos, part, facing);
                    if (level.getBlockState(partPos).getBlock() instanceof TombPartBlock) {
                        level.destroyBlock(partPos, false);
                    }
                }
            }

            super.onRemove(state, level, pos, newState, isMoving);
        }
    }

    public static BlockPos getPartPos(BlockPos mainPos, TombPart part, Direction facing) {
        Direction right = facing.getClockWise();
        return mainPos.relative(right, part.getxOffset()).relative(facing, part.getzOffset());
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.INVISIBLE;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new TombBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return createTickerHelper(type, DDBlockEntityTypes.TOMB.get(), TombBlockEntity::tick);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (!level.isClientSide) {
            BlockEntity be = level.getBlockEntity(pos);
            if(be instanceof TombBlockEntity tombEntity) {
                // Example for opening a GUI
                // NetworkHooks.openScreen((ServerPlayer) player, tombEntity, pos);
            }
        }
        return InteractionResult.SUCCESS;
    }
}
