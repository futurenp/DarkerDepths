package com.naterbobber.darkerdepths.common.block.generic;

import com.google.common.collect.ImmutableMap;
import com.naterbobber.darkerdepths.common.api.StringLightHandler;
import com.naterbobber.darkerdepths.common.util.DDTags;
import com.naterbobber.darkerdepths.common.util.VoxelShapeUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
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
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;

public class StringLightsBlock extends Block {
    public static final BooleanProperty NORTH = BlockStateProperties.NORTH;
    public static final BooleanProperty EAST = BlockStateProperties.EAST;
    public static final BooleanProperty SOUTH = BlockStateProperties.SOUTH;
    public static final BooleanProperty WEST = BlockStateProperties.WEST;

    public static final ImmutableMap<Direction, BooleanProperty> PROPERTY_BY_DIRECTION = ImmutableMap.of(
            Direction.NORTH, NORTH,
            Direction.EAST, EAST,
            Direction.SOUTH, SOUTH,
            Direction.WEST, WEST
    );

    private static final BooleanProperty BOTTOM = BlockStateProperties.BOTTOM;
    private static final int MAX_CONNECTION_DISTANCE = 16;
    private static final int HANGING_BREAK_DELAY = 1;

    private static final VoxelShape TOP_BASE_SHAPE = Block.box(0, 8, 15, 16, 16, 16);
    private static final VoxelShape BOTTOM_BASE_SHAPE = Block.box(0, 0, 15, 16, 8, 16);

    private static final StringLightShapes SHAPES = StringLightShapes.create(TOP_BASE_SHAPE, BOTTOM_BASE_SHAPE);

    public StringLightsBlock(Properties properties) {
        super(properties);
        registerDefaultState(this.stateDefinition.any()
                .setValue(NORTH, false)
                .setValue(EAST, false)
                .setValue(SOUTH, false)
                .setValue(WEST, false)
                .setValue(BOTTOM, false)
        );
    }

    @Override
    protected ItemInteractionResult useItemOn(
            ItemStack stack,
            BlockState state,
            Level level,
            BlockPos pos,
            Player player,
            InteractionHand hand,
            BlockHitResult hitResult
    ) {
        if (!stack.is(state.getBlock().asItem()) || player.isCrouching()) {
            return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
        }

        if (level.isClientSide()) {
            return ItemInteractionResult.SUCCESS;
        }

        var selectedDirection = getTargetedDirection(state, pos, hitResult);
        if (selectedDirection == null) {
            return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
        }

        var uuid = player.getUUID();
        var selectedState = createSingleSideState(state, selectedDirection);

        if (StringLightHandler.getBlockInfo(uuid) == null) {
            StringLightHandler.setPlacement(player, pos, selectedState);
        } else if (connectStrings(level, state, pos, player, selectedDirection)) {
            StringLightHandler.removePlacement(uuid);
        } else {
            StringLightHandler.setPlacement(player, pos, selectedState);
        }

        level.playSound(null, pos, SoundEvents.CANDLE_HIT, SoundSource.BLOCKS, 1.0F, 1.0F);
        return ItemInteractionResult.SUCCESS;
    }

    @Override
    protected boolean canBeReplaced(BlockState state, BlockPlaceContext context) {
        if (!context.getItemInHand().is(this.asItem())) {
            return super.canBeReplaced(state, context);
        }

        var direction = getPlacementDirection(context);
        var property = PROPERTY_BY_DIRECTION.get(direction);

        return property != null
                && state.getValue(BOTTOM) == getPlacementBottom(context)
                && !state.getValue(property);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        var level = context.getLevel();
        var pos = context.getClickedPos();
        var existingState = level.getBlockState(pos);
        var direction = getPlacementDirection(context);
        var property = PROPERTY_BY_DIRECTION.get(direction);

        if (property == null) {
            return null;
        }

        boolean bottom = getPlacementBottom(context);
        var placementState = existingState.is(this)
                ? existingState
                : this.defaultBlockState().setValue(BOTTOM, bottom);

        if (existingState.is(this) && existingState.getValue(BOTTOM) != bottom) {
            return null;
        }

        if (placementState.getValue(property)) {
            return null;
        }

        var candidateState = placementState.setValue(property, true);
        return getSupportType(candidateState, level, pos, direction) == SupportType.NONE
                ? null
                : candidateState;
    }

    @Override
    protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        for (var entry : PROPERTY_BY_DIRECTION.entrySet()) {
            if (state.getValue(entry.getValue())
                    && getSupportType(state, level, pos, entry.getKey()) != SupportType.NONE) {
                return true;
            }
        }

        return false;
    }

    @Override
    protected void neighborChanged(
            BlockState state,
            Level level,
            BlockPos pos,
            Block neighborBlock,
            BlockPos neighborPos,
            boolean movedByPiston
    ) {
        super.neighborChanged(state, level, pos, neighborBlock, neighborPos, movedByPiston);

        if (level.isClientSide()) {
            return;
        }

        var currentState = level.getBlockState(pos);
        if (!currentState.is(this)) {
            return;
        }

        var directlyPrunedState = getPrunedState(currentState, level, pos, neighborPos);
        if (!directlyPrunedState.equals(currentState)) {
            applyPrunedState(level, pos, currentState, directlyPrunedState);
        }

        var remainingState = level.getBlockState(pos);
        if (remainingState.is(this) && hasUnsupportedSide(remainingState, level, pos)) {
            level.scheduleTick(pos, this, HANGING_BREAK_DELAY);
        }
    }

    @Override
    protected void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        var currentState = level.getBlockState(pos);
        if (!currentState.is(this)) {
            return;
        }

        var prunedState = getPrunedState(currentState, level, pos, null);
        if (!prunedState.equals(currentState)) {
            applyPrunedState(level, pos, currentState, prunedState);
        }
    }

    @Override
    public boolean onDestroyedByPlayer(
            BlockState state,
            Level level,
            BlockPos pos,
            Player player,
            boolean willHarvest,
            FluidState fluid
    ) {
        var blockInfo = StringLightHandler.getBlockInfo(player.getUUID());
        if (blockInfo != null && blockInfo.pos().equals(pos)) {
            StringLightHandler.removePlacement(player.getUUID());
        }

        return super.onDestroyedByPlayer(state, level, pos, player, willHarvest, fluid);
    }

    @Override
    public void setPlacedBy(
            Level level,
            BlockPos pos,
            BlockState state,
            @Nullable LivingEntity placer,
            ItemStack stack
    ) {
        super.setPlacedBy(level, pos, state, placer, stack);

        if (level.isClientSide() || !(placer instanceof Player player)) {
            return;
        }

        var placedDirection = getOnlyDirection(state);
        if (placedDirection != null && connectStrings(level, state, pos, player, placedDirection)) {
            StringLightHandler.removePlacement(player.getUUID());
        }
    }

    @Override
    protected List<ItemStack> getDrops(BlockState state, LootParams.Builder params) {
        var drops = super.getDrops(state, params);
        int sideCount = countSides(state);

        if (sideCount <= 1) {
            return drops;
        }

        for (var drop : drops) {
            if (drop.is(this.asItem())) {
                drop.setCount(drop.getCount() * sideCount);
                break;
            }
        }

        return drops;
    }

    private SupportType getSupportType(
            BlockState state,
            LevelReader level,
            BlockPos pos,
            Direction direction
    ) {
        if (hasDirectSupport(state, level, pos, direction)) {
            return SupportType.ANCHORED;
        }

        if (hasHangingSupport(state, level, pos, direction)) {
            return SupportType.HANGING;
        }

        return SupportType.NONE;
    }

    private boolean hasDirectSupport(
            BlockState state,
            LevelReader level,
            BlockPos pos,
            Direction direction
    ) {
        var behindPos = pos.relative(direction.getOpposite());
        var behindState = level.getBlockState(behindPos);
        boolean bottom = state.getValue(BOTTOM);

        return behindState.isFaceSturdy(level, behindPos, direction)
                || hasPartialSideSupport(level, behindPos, behindState, direction, bottom)
                || behindState.is(BlockTags.LEAVES);
    }

    private boolean hasHangingSupport(
            BlockState state,
            LevelReader level,
            BlockPos pos,
            Direction direction
    ) {
        return doesNeighborSupport(level, pos, state, direction.getClockWise(), direction)
                && doesNeighborSupport(level, pos, state, direction.getCounterClockWise(), direction);
    }

    private boolean hasPartialSideSupport(
            LevelReader level,
            BlockPos neighborPos,
            BlockState neighborState,
            Direction facing,
            boolean bottom
    ) {
        double minY = bottom ? 0 : 8;
        double maxY = bottom ? 8 : 16;

        var requiredSupport = switch (facing) {
            case NORTH -> Block.box(0, minY, 0, 16, maxY, 1);
            case EAST -> Block.box(15, minY, 0, 16, maxY, 16);
            case SOUTH -> Block.box(0, minY, 15, 16, maxY, 16);
            case WEST -> Block.box(0, minY, 0, 1, maxY, 16);
            default -> Shapes.empty();
        };

        var neighborShape = neighborState.getBlockSupportShape(level, neighborPos);
        return !Shapes.joinIsNotEmpty(requiredSupport, neighborShape, BooleanOp.ONLY_FIRST);
    }

    private boolean doesNeighborSupport(
            LevelReader level,
            BlockPos pos,
            BlockState state,
            Direction relative,
            Direction lightDirection
    ) {
        var neighborState = level.getBlockState(pos.relative(relative));
        if (!neighborState.is(DDTags.Blocks.STRING_LIGHTS) || neighborState.getValue(BOTTOM) != state.getValue(BOTTOM)) {
            return false;
        }

        return neighborState.getValue(PROPERTY_BY_DIRECTION.get(lightDirection));
    }

    private boolean hasUnsupportedSide(BlockState state, LevelReader level, BlockPos pos) {
        for (var entry : PROPERTY_BY_DIRECTION.entrySet()) {
            if (state.getValue(entry.getValue())
                    && getSupportType(state, level, pos, entry.getKey()) == SupportType.NONE) {
                return true;
            }
        }

        return false;
    }

    private BlockState getPrunedState(
            BlockState state,
            LevelReader level,
            BlockPos pos,
            @Nullable BlockPos changedNeighborPos
    ) {
        var updatedState = state;

        for (var entry : PROPERTY_BY_DIRECTION.entrySet()) {
            var direction = entry.getKey();
            var property = entry.getValue();

            if (!state.getValue(property)
                    || getSupportType(state, level, pos, direction) != SupportType.NONE) {
                continue;
            }

            if (changedNeighborPos != null
                    && !pos.relative(direction.getOpposite()).equals(changedNeighborPos)) {
                continue;
            }

            updatedState = updatedState.setValue(property, false);
        }

        return updatedState;
    }

    private void applyPrunedState(
            Level level,
            BlockPos pos,
            BlockState oldState,
            BlockState newState
    ) {
        int removedCount = countSides(oldState) - countSides(newState);
        if (removedCount <= 0) {
            return;
        }

        popResource(level, pos, new ItemStack(this.asItem(), removedCount));

        if (countSides(newState) == 0) {
            level.removeBlock(pos, false);
        } else {
            level.setBlock(pos, newState, Block.UPDATE_ALL);
        }
    }

    private boolean connectStrings(
            Level level,
            BlockState state,
            BlockPos pos,
            Player player,
            Direction selectedDirection
    ) {
        var blockInfo = StringLightHandler.getBlockInfo(player.getUUID());
        if (blockInfo == null) {
            return false;
        }

        var baseState = blockInfo.state();
        if (!baseState.is(this)) {
            return false;
        }

        var baseDirection = getOnlyDirection(baseState);
        if (baseDirection == null || baseDirection != selectedDirection) {
            return false;
        }

        var basePos = blockInfo.pos();
        if (pos.equals(basePos)) {
            return false;
        }

        var baseWorldState = level.getBlockState(basePos);
        var directionProperty = PROPERTY_BY_DIRECTION.get(baseDirection);
        boolean bottom = state.getValue(BOTTOM);

        if (!state.is(this)
                || !state.getValue(directionProperty)
                || !baseWorldState.is(this)
                || !baseWorldState.getValue(directionProperty)
                || baseWorldState.getValue(BOTTOM) != bottom
                || baseState.getValue(BOTTOM) != bottom
                || pos.getY() != basePos.getY()) {
            return false;
        }

        var faceAxis = baseDirection.getAxis();
        if (pos.get(faceAxis) != basePos.get(faceAxis)) {
            return false;
        }

        var lineAxis = baseDirection.getClockWise().getAxis();
        int currentPosition = pos.get(lineAxis);
        int basePosition = basePos.get(lineAxis);
        int distance = Math.abs(currentPosition - basePosition);

        if (distance > MAX_CONNECTION_DISTANCE) {
            return false;
        }

        var axisDirection = basePosition > currentPosition
                ? Direction.AxisDirection.POSITIVE
                : Direction.AxisDirection.NEGATIVE;
        var moveDirection = Direction.fromAxisAndDirection(lineAxis, axisDirection);
        var blocksToPlace = new HashMap<BlockPos, BlockState>();

        for (int i = 1; i < distance; i++) {
            var placePos = pos.relative(moveDirection, i);
            var currentState = level.getBlockState(placePos);

            if (currentState.is(this)) {
                if (currentState.getValue(BOTTOM) != bottom) {
                    return false;
                }

                if (currentState.getValue(directionProperty)) {
                    continue;
                }

                blocksToPlace.put(placePos, currentState.setValue(directionProperty, true));
                continue;
            }

            if (!currentState.canBeReplaced()) {
                return false;
            }

            blocksToPlace.put(
                    placePos,
                    this.defaultBlockState()
                            .setValue(BOTTOM, bottom)
                            .setValue(directionProperty, true)
            );
        }

        int requiredAmount = blocksToPlace.size();
        var requiredItem = this.asItem();

        if (!player.isCreative()) {
            int availableCount = 0;
            var inventory = player.getInventory();

            for (int i = 0; i < inventory.getContainerSize(); i++) {
                var inventoryStack = inventory.getItem(i);
                if (inventoryStack.is(requiredItem)) {
                    availableCount += inventoryStack.getCount();
                }
            }

            if (availableCount < requiredAmount) {
                return false;
            }

            int remainingToTake = requiredAmount;
            for (int i = 0; i < inventory.getContainerSize(); i++) {
                var inventoryStack = inventory.getItem(i);
                if (!inventoryStack.is(requiredItem)) {
                    continue;
                }

                int takeAmount = Math.min(inventoryStack.getCount(), remainingToTake);
                inventoryStack.shrink(takeAmount);
                remainingToTake -= takeAmount;

                if (remainingToTake == 0) {
                    break;
                }
            }
        }

        blocksToPlace.forEach((placePos, placeState) ->
                level.setBlock(placePos, placeState, Block.UPDATE_ALL));

        return true;
    }

    private BlockState createSingleSideState(BlockState state, Direction direction) {
        return this.defaultBlockState()
                .setValue(BOTTOM, state.getValue(BOTTOM))
                .setValue(PROPERTY_BY_DIRECTION.get(direction), true);
    }

    @Nullable
    private static Direction getOnlyDirection(BlockState state) {
        Direction foundDirection = null;

        for (var entry : PROPERTY_BY_DIRECTION.entrySet()) {
            if (!state.getValue(entry.getValue())) {
                continue;
            }

            if (foundDirection != null) {
                return null;
            }

            foundDirection = entry.getKey();
        }

        return foundDirection;
    }

    @Nullable
    private static Direction getTargetedDirection(
            BlockState state,
            BlockPos pos,
            BlockHitResult hitResult
    ) {
        var location = hitResult.getLocation();
        double localX = location.x - pos.getX();
        double localZ = location.z - pos.getZ();
        Direction closestDirection = null;
        double closestDistance = Double.MAX_VALUE;

        for (var entry : PROPERTY_BY_DIRECTION.entrySet()) {
            if (!state.getValue(entry.getValue())) {
                continue;
            }

            double distance = switch (entry.getKey()) {
                case NORTH -> 1.0 - localZ;
                case EAST -> localX;
                case SOUTH -> localZ;
                case WEST -> 1.0 - localX;
                default -> Double.MAX_VALUE;
            };

            if (distance < closestDistance) {
                closestDirection = entry.getKey();
                closestDistance = distance;
            }
        }

        return closestDirection;
    }

    private static Direction getPlacementDirection(BlockPlaceContext context) {
        var clickedFace = context.getClickedFace();
        return clickedFace.getAxis().isHorizontal()
                ? clickedFace
                : context.getHorizontalDirection().getOpposite();
    }

    private static boolean getPlacementBottom(BlockPlaceContext context) {
        if (context.getClickedFace().getAxis().isVertical()) {
            return false;
        }

        double relativeY = context.getClickLocation().y - context.getClickedPos().getY();
        return relativeY <= 0.5;
    }

    private static int countSides(BlockState state) {
        int count = 0;

        for (var property : PROPERTY_BY_DIRECTION.values()) {
            if (state.getValue(property)) {
                count++;
            }
        }

        return count;
    }

    private static int getShapeIndex(BlockState state) {
        int mask = 0;

        for (var entry : PROPERTY_BY_DIRECTION.entrySet()) {
            if (state.getValue(entry.getValue())) {
                mask |= getDirectionMask(entry.getKey());
            }
        }

        return mask;
    }

    private static int getDirectionMask(Direction direction) {
        return switch (direction) {
            case NORTH -> 1;
            case EAST -> 2;
            case SOUTH -> 4;
            case WEST -> 8;
            default -> 0;
        };
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(NORTH, EAST, SOUTH, WEST, BOTTOM);
    }

    @Override
    protected VoxelShape getShape(
            BlockState state,
            BlockGetter level,
            BlockPos pos,
            CollisionContext context
    ) {
        return SHAPES.get(state);
    }

    private enum SupportType {
        ANCHORED,
        HANGING,
        NONE
    }

    private record StringLightShapes(VoxelShape[] top, VoxelShape[] bottom) {
        private static StringLightShapes create(VoxelShape topBaseShape, VoxelShape bottomBaseShape) {
            return new StringLightShapes(
                    createShapes(topBaseShape),
                    createShapes(bottomBaseShape)
            );
        }

        private static VoxelShape[] createShapes(VoxelShape baseShape) {
            var directionalShapes = new EnumMap<Direction, VoxelShape>(Direction.class);

            for (var direction : Direction.Plane.HORIZONTAL) {
                directionalShapes.put(
                        direction,
                        VoxelShapeUtils.rotateHorizontal(baseShape, direction)
                );
            }

            var shapes = new VoxelShape[16];

            for (int mask = 0; mask < shapes.length; mask++) {
                var combinedShape = Shapes.empty();

                for (var direction : Direction.Plane.HORIZONTAL) {
                    if ((mask & getDirectionMask(direction)) != 0) {
                        combinedShape = Shapes.or(combinedShape, directionalShapes.get(direction));
                    }
                }

                shapes[mask] = combinedShape.optimize();
            }

            return shapes;
        }

        private VoxelShape get(BlockState state) {
            var shapes = state.getValue(BOTTOM) ? bottom : top;
            return shapes[getShapeIndex(state)];
        }
    }
}