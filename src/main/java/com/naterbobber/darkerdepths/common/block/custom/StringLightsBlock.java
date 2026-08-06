package com.naterbobber.darkerdepths.common.block.custom;

import com.naterbobber.darkerdepths.common.api.StringLightHandler;
import com.naterbobber.darkerdepths.common.util.VoxelShapeUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

public class StringLightsBlock extends Block {
    private static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    private static final BooleanProperty BOTTOM = BlockStateProperties.BOTTOM;
    private static final VoxelShape SHAPE = Block.box(0, 8, 15, 16, 16, 16);
    private static final VoxelShape SHAPE_BOTTOM = Block.box(0, 0, 15, 16, 8, 16);

    private static final Map<Direction, VoxelShape> SHAPES = new EnumMap<>(Direction.class);
    private static final Map<Direction, VoxelShape> SHAPES_BOTTOM = new EnumMap<>(Direction.class);

    static {
        for (var direction : Direction.Plane.HORIZONTAL) {
            SHAPES.put(direction, VoxelShapeUtils.rotateHorizontal(SHAPE, direction));
            SHAPES_BOTTOM.put(direction, VoxelShapeUtils.rotateHorizontal(SHAPE_BOTTOM, direction));
        }
    }

    public StringLightsBlock(Properties properties) {
        super(properties);
        registerDefaultState(this.stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
                .setValue(BOTTOM, false)
        );
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if(stack.is(state.getBlock().asItem()) && !level.isClientSide() && !player.isCrouching()) {
            if(StringLightHandler.getBlockInfo(player.getUUID()) == null) {
                StringLightHandler.setPlacement(player, pos, state);
                level.playSound(null, pos, SoundEvents.CANDLE_HIT, SoundSource.BLOCKS, 1.0F, 1.0F);
            } else {
                if(connectStrings(level, state, pos, player)) {
                    StringLightHandler.removePlacement(player.getUUID());
                } else {
                    StringLightHandler.setPlacement(player, pos, state);
                }
                level.playSound(null, pos, SoundEvents.CANDLE_HIT, SoundSource.BLOCKS, 1.0F, 1.0F);
            }
            return ItemInteractionResult.SUCCESS;
        }

        return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        var clickedFace = context.getClickedFace();
        var relativeY = context.getClickLocation().y - (double)context.getClickedPos().getY();
        var bottom = !clickedFace.getAxis().isVertical() && relativeY <= 0.5;

        var facing = clickedFace.getAxis().isHorizontal()
                ? clickedFace
                : context.getHorizontalDirection().getOpposite();

        return this.defaultBlockState()
                .setValue(FACING, facing)
                .setValue(BOTTOM, bottom);
    }

    @Override
    protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        var facing = state.getValue(FACING);
        boolean isBottom = state.getValue(BOTTOM);

        var behindPos = pos.relative(facing.getOpposite());
        var behindState = level.getBlockState(behindPos);

        if (behindState.isFaceSturdy(level, behindPos, facing)
                || hasPartialSideSupport(level, behindPos, behindState, facing, isBottom)
                || behindState.is(BlockTags.LEAVES)) {
            return true;
        }

        return doesNeighborSupport(level, pos, state, facing.getClockWise()) && doesNeighborSupport(level, pos, state, facing.getCounterClockWise());
    }

    @Override
    public boolean onDestroyedByPlayer(BlockState state, Level level, BlockPos pos, Player player, boolean willHarvest, FluidState fluid) {
        var blockInfo = StringLightHandler.getBlockInfo(player.getUUID());
        if(blockInfo != null && blockInfo.pos() == pos) {
            StringLightHandler.removePlacement(player.getUUID());
        }

        return super.onDestroyedByPlayer(state, level, pos, player, willHarvest, fluid);
    }

    private boolean hasPartialSideSupport(LevelReader level, BlockPos neighborPos, BlockState neighborState, Direction facing, boolean isBottom) {
        double minY = isBottom ? 0 : 8;
        double maxY = isBottom ? 8 : 16;

        var requiredSupport = switch (facing) {
            case NORTH -> Block.box(0, minY, 0, 16, maxY, 1);
            case SOUTH -> Block.box(0, minY, 15, 16, maxY, 16);
            case WEST  -> Block.box(0, minY, 0, 1, maxY, 16);
            case EAST  -> Block.box(15, minY, 0, 16, maxY, 16);
            default -> Shapes.empty();
        };

        if (requiredSupport.isEmpty()) {
            return false;
        }

        var neighborShape = neighborState.getBlockSupportShape(level, neighborPos);

        return !Shapes.joinIsNotEmpty(requiredSupport, neighborShape, BooleanOp.ONLY_FIRST);
    }

    private boolean doesNeighborSupport(LevelReader level, BlockPos pos, BlockState state, Direction direction) {
        var neighborState = level.getBlockState(pos.relative(direction));
        if(neighborState.getBlock() == this) {
            var facing = state.getValue(FACING);
            var bottom = state.getValue(BOTTOM);

            return neighborState.getValue(FACING) == facing && neighborState.getValue(BOTTOM) == bottom;
        }

        return false;
    }

    @Override
    protected void neighborChanged(BlockState state, Level level, BlockPos pos, Block neighborBlock, BlockPos neighborPos, boolean movedByPiston) {
        if (!state.canSurvive(level, pos)) {
            level.destroyBlock(pos, true);
        }
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        super.setPlacedBy(level, pos, state, placer, stack);

        if (level.isClientSide() || !(placer instanceof Player player)) {
            return;
        }

        if (connectStrings(level, state, pos, player)) {
            StringLightHandler.removePlacement(player.getUUID());
        }
    }

    private boolean connectStrings(Level level, BlockState state, BlockPos pos, Player player) {
        var uuid = player.getUUID();
        var blockInfo = StringLightHandler.getBlockInfo(uuid);

        if (blockInfo == null) {
           return false;
        }

        if(!level.getBlockState(blockInfo.pos()).equals(state)) {
            return false;
        }

        var baseState = blockInfo.state();
        var baseFacing = baseState.getValue(FACING);
        var basePos = blockInfo.pos();

        if(pos.equals(basePos)) {
            return false;
        }

        if (state.getValue(BOTTOM) != baseState.getValue(BOTTOM) || state.getValue(FACING) != baseFacing) {
            return false;
        }

        var axis = state.getValue(FACING).getAxis();

        if (baseFacing.getAxis() != axis || basePos.get(axis) != pos.get(axis) || pos.getY() != basePos.getY()) {
            return false;
        }

        var perpendicularAxis = baseFacing.getClockWise().getAxis();
        int currentPosVal = pos.get(perpendicularAxis);
        int basePosVal = basePos.get(perpendicularAxis);

        int distance = Math.abs(currentPosVal - basePosVal);

        if(distance > 16) {
            return false;
        }

        var axisDirection = (basePosVal > currentPosVal)
                ? Direction.AxisDirection.POSITIVE
                : Direction.AxisDirection.NEGATIVE;
        var moveDirection = Direction.fromAxisAndDirection(perpendicularAxis, axisDirection);

        Map<BlockPos, BlockState> blocksToPlace = new HashMap<>();
        for (int i = 1; i < distance; i++) {
            var placePos = pos.relative(moveDirection, i);

            if (!level.getBlockState(placePos).canBeReplaced()) {
                return false;
            }

            blocksToPlace.put(placePos, state);
        }

        int requiredAmount = blocksToPlace.size();
        var requiredItem = state.getBlock().asItem();

        if (!player.isCreative()) {
            int availableCount = 0;
            var inventory = player.getInventory();

            for (int i = 0; i < inventory.getContainerSize(); i++) {
                ItemStack stack = inventory.getItem(i);
                if (stack.is(requiredItem)) {
                    availableCount += stack.getCount();
                }
            }

            if (availableCount < requiredAmount) {
                return false;
            }

            int remainingToTake = requiredAmount;
            for (int i = 0; i < inventory.getContainerSize(); i++) {
                ItemStack stack = inventory.getItem(i);
                if (stack.is(requiredItem)) {
                    int takeAmount = Math.min(stack.getCount(), remainingToTake);
                    stack.shrink(takeAmount);
                    remainingToTake -= takeAmount;

                    if (remainingToTake <= 0) break;
                }
            }
        }

        blocksToPlace.forEach((placePos, placeState) -> level.setBlock(placePos, placeState, Block.UPDATE_CLIENTS));

        return true;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FACING);
        builder.add(BOTTOM);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return state.getValue(BOTTOM)
                ? SHAPES_BOTTOM.get(state.getValue(FACING))
                : SHAPES.get(state.getValue(FACING));
    }
}
