package com.naterbobber.darkerdepths.common.blocks;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.minecraft.block.*;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

//<>

public class MultifaceGemBlock extends Block {
    private static final VoxelShape UP_SHAPE = Block.makeCuboidShape(2, 12, 2, 14, 16.0D, 14);
    private static final VoxelShape DOWN_SHAPE = Block.makeCuboidShape(2, 0.0d, 2, 14, 4, 14);
    private static final VoxelShape NORTH_SHAPE = Block.makeCuboidShape(2, 2, 0.0D, 14, 14, 4);
    private static final VoxelShape SOUTH_SHAPE = Block.makeCuboidShape(2, 2, 12, 14, 14, 16.0D);
    private static final VoxelShape WEST_SHAPE = Block.makeCuboidShape(0.0D, 2, 2, 4, 14, 14);
    private static final VoxelShape EAST_SHAPE = Block.makeCuboidShape(12, 2, 2, 16.0D, 14, 14);
    private static final Map<Direction, BooleanProperty> FACING_TO_PROPERTY_MAP = SixWayBlock.FACING_TO_PROPERTY_MAP;
    private static final Map<Direction, VoxelShape> SHAPE_TO_DIRECTION_MAP = Util.make(Maps.newEnumMap(Direction.class), (enumMap) -> {
        enumMap.put(Direction.NORTH, NORTH_SHAPE);
        enumMap.put(Direction.EAST, EAST_SHAPE);
        enumMap.put(Direction.SOUTH, SOUTH_SHAPE);
        enumMap.put(Direction.WEST, WEST_SHAPE);
        enumMap.put(Direction.UP, UP_SHAPE);
        enumMap.put(Direction.DOWN, DOWN_SHAPE);
    });
    private static final Direction[] DIRECTIONS = Direction.values();
    private static ImmutableMap shapesCache;
    private static boolean canRotate;
    private static boolean canMirrorX;
    private static boolean canMirrorZ;

    public MultifaceGemBlock(AbstractBlock.Properties properties) {
        super(properties);
        this.setDefaultState(getDefaultAmberState(this.stateContainer));
        this.shapesCache = getShapes(this.stateContainer);
        this.canRotate = Direction.Plane.HORIZONTAL.func_239636_a_().allMatch(this::isFaceSupported);
        this.canMirrorX = Direction.Plane.HORIZONTAL.func_239636_a_().filter(Direction.Axis.X).filter(this::isFaceSupported).count() % 2L == 0L;
        this.canMirrorZ = Direction.Plane.HORIZONTAL.func_239636_a_().filter(Direction.Axis.Z).filter(this::isFaceSupported).count() % 2L == 0L;
    }

    protected boolean isFaceSupported(Direction direction) {
        return true;
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        Direction[] directions = DIRECTIONS;
        int length = directions.length;

        for (int i = 0; i < length; i++) {
            Direction direction = directions[i];
            if (this.isFaceSupported(direction)) {
                builder.add(getFaceProperty(direction));
            }
        }
    }

    @Override
    public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
        return hasFace(stateIn, facing) && !canAttachTo(worldIn, facing, facingPos, facingState) ? removeFace(stateIn, getFaceProperty(facing)) : stateIn;
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return (VoxelShape)this.shapesCache.get(state);
    }

    @Override
    public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
        boolean isInValidPosition = false;
        Direction[] directions = DIRECTIONS;
        int length = directions.length;

        for (int i = 0; i < length; i++) {
            Direction direction = directions[i];
            if (hasFace(state, direction)) {
                BlockPos blockPos = pos.offset(direction);
                if (!canAttachTo(worldIn, direction, blockPos, worldIn.getBlockState(blockPos))) {
                    return false;
                }

                isInValidPosition = true;
            }
        }

        return isInValidPosition;
    }

    @Override
    public boolean isReplaceable(BlockState state, BlockItemUseContext useContext) {
        return hasAnyVacantFace(state);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        World world = context.getWorld();
        BlockPos pos = context.getPos();
        BlockState state = world.getBlockState(pos);
        return Arrays.stream(context.getNearestLookingDirections()).map((direction) -> this.getStateForPlacement(state, world, pos, direction)).filter(Objects::nonNull).findFirst().orElse(null);
    }

    @Nullable
    public BlockState getStateForPlacement(BlockState state, IWorld worldIn, BlockPos pos, Direction direction) {
        if (!this.isFaceSupported(direction)) {
            return null;
        } else {
            BlockState blockState;
            if (state.getBlock().equals(this)) {
                if (hasFace(state, direction)) {
                    return null;
                }

                blockState = state;
            } else if (this.isWaterloggable() && state.getFluidState().isSource()) {
                blockState = this.getDefaultState().with(BlockStateProperties.WATERLOGGED, true);
            } else {
                blockState = this.getDefaultState();
            }

            BlockPos offset = pos.offset(direction);
            return canAttachTo(worldIn, direction, offset, worldIn.getBlockState(offset)) ? blockState.with(getFaceProperty(direction), true) : null;
        }
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rot) {
        if (!this.canRotate) {
            return state;
        } else {
            rot.getClass();
            return this.mapDirections(state, rot::rotate);
        }
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirrorIn) {
        if (mirrorIn == Mirror.FRONT_BACK && !this.canMirrorX) {
            return state;
        } else if (mirrorIn == Mirror.LEFT_RIGHT && !this.canMirrorZ) {
            return state;
        } else {
            mirrorIn.getClass();
            return this.mapDirections(state, mirrorIn::mirror);
        }
    }

    private BlockState mapDirections(BlockState state, Function<Direction, Direction> function) {
        BlockState blockState = state;
        Direction[] directions = DIRECTIONS;
        int length = directions.length;

        for (int i = 0; i < length; i++) {
            Direction direction = directions[i];
            if (this.isFaceSupported(direction)) {
                blockState = blockState.with(getFaceProperty(function.apply(direction)), state.get(getFaceProperty(direction)));
            }
        }

        return blockState;
    }

    public boolean spreadFromRandomFaceTowardRandomDirection(BlockState state, ServerWorld worldIn, BlockPos pos, Random rand) {
        List<Direction> directions = Lists.newArrayList(DIRECTIONS);
        Collections.shuffle(directions);
        return directions.stream().filter((direction) -> hasFace(state, direction)).anyMatch((direction) -> this.spreadFromFaceTowardRandomDirection(state, worldIn, pos, direction, rand));
    }

    public boolean spreadFromFaceTowardRandomDirection(BlockState state, IWorld worldIn, BlockPos pos, Direction direction, Random rand) {
        List<Direction> directions = Arrays.asList(DIRECTIONS);
        Collections.shuffle(directions, rand);
        return directions.stream().anyMatch((directionx) -> this.spreadFromFaceTowardDirection(state, worldIn, pos, direction, directionx));
    }

    public boolean spreadFromFaceTowardDirection(BlockState state, IWorld worldIn, BlockPos pos, Direction face, Direction direction) {
        if (direction.getAxis() != face.getAxis() && hasFace(state, face) && !hasFace(state, direction)) {
            if (this.spreadToFace(worldIn, pos, direction)) {
                return true;
            } else {
                return this.spreadToFace(worldIn, pos.offset(direction), face) ? true : this.spreadToFace(worldIn, pos.offset(direction).offset(face), direction.getOpposite());
            }
        } else {
            return false;
        }
    }

    private boolean spreadToFace(IWorld worldIn, BlockPos blockPos, Direction direction) {
        BlockState state = worldIn.getBlockState(blockPos);
        if (!this.canSpreadInto(state)) {
            return false;
        } else {
            BlockState stateForPlacement = this.getStateForPlacement(state, worldIn, blockPos, direction);
            return stateForPlacement != null ? worldIn.setBlockState(blockPos, stateForPlacement, 2) : false;
        }
    }

    private boolean canSpreadInto(BlockState state) {
        return state.isAir() || state.getBlock().equals(this) || state.getBlock().equals(Blocks.WATER) && state.getFluidState().isSource();
    }

    private static boolean hasFace(BlockState state, Direction direction) {
        BooleanProperty property = getFaceProperty(direction);
        return state.hasProperty(property) && state.get(property);
    }

    private static boolean canAttachTo(IBlockReader reader, Direction direction, BlockPos pos, BlockState state) {
        return Block.doesSideFillSquare(state.getCollisionShape(reader, pos), direction.getOpposite());
    }

    private boolean isWaterloggable() {
        return this.stateContainer.getProperties().contains(BlockStateProperties.WATERLOGGED);
    }

    private static BlockState removeFace(BlockState state, BooleanProperty property) {
        BlockState blockState = state.with(property, false);
        if (hasAnyFace(blockState)) {
            return blockState;
        } else {
            return state.hasProperty(BlockStateProperties.WATERLOGGED) && state.get(BlockStateProperties.WATERLOGGED) ? Blocks.WATER.getDefaultState() : Blocks.AIR.getDefaultState();
        }
    }

    public static BooleanProperty getFaceProperty(Direction gc) {
        return FACING_TO_PROPERTY_MAP.get(gc);
    }

    private static BlockState getDefaultAmberState(StateContainer<Block, BlockState> stateContainer) {
        BlockState state = stateContainer.getBaseState();

        for (BooleanProperty property : FACING_TO_PROPERTY_MAP.values()) {
            if (state.hasProperty(property)) {
                state = state.with(property, false);
            }
        }

        return state;
    }

    private static ImmutableMap getShapes(StateContainer stateContainer) {
        Map stateMap = (Map)stateContainer.getValidStates().stream().collect(Collectors.toMap(Function.identity(), MultifaceGemBlock::calculateAmberShape));
        return ImmutableMap.copyOf(stateMap);
    }

    private static VoxelShape calculateAmberShape(BlockState state) {
        VoxelShape shape = VoxelShapes.empty();
        Direction[] directions = DIRECTIONS;
        int length = directions.length;

        for(int i = 0; i < length; ++i) {
            Direction direction = directions[i];
            if (hasFace(state, direction)) {
                shape = VoxelShapes.or(shape, SHAPE_TO_DIRECTION_MAP.get(direction));
            }
        }

        return shape;
    }

    private static boolean hasAnyFace(BlockState state) {
        return Arrays.stream(DIRECTIONS).anyMatch((direction) -> hasFace(state, direction));
    }

    private static boolean hasAnyVacantFace(BlockState state) {
        return Arrays.stream(DIRECTIONS).anyMatch((direction) -> !hasFace(state, direction));
    }
}