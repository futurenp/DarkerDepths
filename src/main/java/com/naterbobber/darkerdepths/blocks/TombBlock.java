package com.naterbobber.darkerdepths.blocks;

import com.naterbobber.darkerdepths.blocks.blockentities.TombBlockEntity;
import com.naterbobber.darkerdepths.init.DDBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class TombBlock extends BaseEntityBlock implements SimpleWaterloggedBlock {

    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final EnumProperty<Part> PART = EnumProperty.create("part", Part.class);
    private static final VoxelShape CORNER_PART = Shapes.or(
            Block.box(2, 0, 5, 16, 3, 16),
            Block.box(3, 3, 6, 16, 10, 16),
            Block.box(1, 10, 4, 16, 15, 16));
    private static final VoxelShape CORNER_PART_MIRRORED = Shapes.or(
            Block.box(0, 0, 5, 14, 3, 16),
            Block.box(0, 3, 6, 13, 10, 16),
            Block.box(0, 10, 4, 15, 15, 16));
    private static final VoxelShape CENTER_PART = Shapes.or(
            Block.box(0, 0, 5, 16, 3, 16),
            Block.box(0, 3, 6, 16, 10, 16),
            Block.box(0, 10, 4, 16, 15, 16));

    private static final VoxelShape FRONT_CENTER_SHAPE = CENTER_PART;
    private static final VoxelShape BACK_CENTER_SHAPE = rotateVoxelShape(CENTER_PART, Direction.SOUTH);
    private static final VoxelShape FRONT_LEFT_SHAPE = CORNER_PART;
    private static final VoxelShape FRONT_RIGHT_SHAPE = CORNER_PART_MIRRORED;
    private static final VoxelShape BACK_LEFT_SHAPE = rotateVoxelShape(CORNER_PART_MIRRORED, Direction.SOUTH);
    private static final VoxelShape BACK_RIGHT_SHAPE = rotateVoxelShape(CORNER_PART, Direction.SOUTH);

    public enum Part implements StringRepresentable {
        FRONT_CENTER("front_center", 0, 0),
        FRONT_LEFT("front_left", -1, 0),
        FRONT_RIGHT("front_right", 1, 0),
        BACK_CENTER("back_center", 0, -1),
        BACK_LEFT("back_left", -1, -1),
        BACK_RIGHT("back_right", 1, -1);

        private final String name;
        private final int xOffset;
        private final int zOffset;

        Part(String name, int xOffset, int zOffset) {
            this.name = name;
            this.xOffset = xOffset;
            this.zOffset = zOffset;
        }

        @Override
        public String getSerializedName() {
            return this.name;
        }

        public int getxOffset() {
            return xOffset;
        }

        public int getzOffset() {
            return zOffset;
        }
    }

    public TombBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
                .setValue(PART, Part.FRONT_CENTER)
                .setValue(WATERLOGGED, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, PART, WATERLOGGED);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        Part part = state.getValue(PART);
        Direction facing = state.getValue(FACING);

        switch (part) {
            case FRONT_CENTER: return rotateVoxelShape(FRONT_CENTER_SHAPE, facing);
            case FRONT_LEFT:   return rotateVoxelShape(FRONT_LEFT_SHAPE, facing);
            case FRONT_RIGHT:  return rotateVoxelShape(FRONT_RIGHT_SHAPE, facing);
            case BACK_CENTER:  return rotateVoxelShape(BACK_CENTER_SHAPE, facing);
            case BACK_LEFT:    return rotateVoxelShape(BACK_LEFT_SHAPE, facing);
            case BACK_RIGHT:   return rotateVoxelShape(BACK_RIGHT_SHAPE, facing);
        }
        return Shapes.block();
    }


    @Override
    public RenderShape getRenderShape(BlockState state) {
        return state.getValue(PART) == Part.FRONT_CENTER ? RenderShape.ENTITYBLOCK_ANIMATED : RenderShape.MODEL;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        FluidState fluidstate = context.getLevel().getFluidState(context.getClickedPos());
        return this.defaultBlockState()
                .setValue(FACING, context.getHorizontalDirection().getOpposite())
                .setValue(PART, Part.FRONT_CENTER)
                .setValue(WATERLOGGED, fluidstate.getType() == Fluids.WATER);
    }

    @Override
    public FluidState getFluidState(BlockState pState) {
        return pState.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(pState);
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        if (state.getValue(PART) != Part.FRONT_CENTER) {
            return super.canSurvive(state, level, pos);
        }

        Direction facing = state.getValue(FACING);
        for (Part part : Part.values()) {
            if (part == Part.FRONT_CENTER) continue;
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
        if (!level.isClientSide && !isMoving && state.getValue(PART) == Part.FRONT_CENTER) {
            Direction facing = state.getValue(FACING);
            for (Part part : Part.values()) {
                if (part == Part.FRONT_CENTER) continue;
                BlockPos partPos = getPartPos(pos, part, facing);
                BlockState partState = this.defaultBlockState()
                        .setValue(FACING, facing)
                        .setValue(PART, part)
                        .setValue(WATERLOGGED, state.getValue(WATERLOGGED));
                level.setBlock(partPos, partState, 3);
            }
        }
    }

    @Override
    public void playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
        if (!level.isClientSide) {
            BlockPos mainPos = getMainBlockPos(pos, state);
            if (level.getBlockState(mainPos).getBlock() == this) {
                level.setBlock(mainPos, Blocks.AIR.defaultBlockState(), 35);
                level.addDestroyBlockEffect(mainPos, level.getBlockState(mainPos));
            }
        }
        super.playerWillDestroy(level, pos, state, player);
    }

    @Override
    public BlockState updateShape(BlockState pState, Direction pFacing, BlockState pFacingState, LevelAccessor pLevel, BlockPos pCurrentPos, BlockPos pFacingPos) {
        if (pState.getValue(WATERLOGGED)) {
            pLevel.scheduleTick(pCurrentPos, Fluids.WATER, Fluids.WATER.getTickDelay(pLevel));
        }
        Part part = pState.getValue(PART);
        if (part != Part.FRONT_CENTER) {
            BlockPos mainPos = getMainBlockPos(pCurrentPos, pState);
            if (pLevel.getBlockState(mainPos).getBlock() != this) {
                return Blocks.AIR.defaultBlockState();
            }
        }
        return super.updateShape(pState, pFacing, pFacingState, pLevel, pCurrentPos, pFacingPos);
    }

    public static BlockPos getPartPos(BlockPos mainPos, Part part, Direction facing) {
        Direction right = facing.getClockWise();
        return mainPos.relative(part.getxOffset() > 0 ? right : right.getOpposite(), Math.abs(part.getxOffset()))
                .relative(part.getzOffset() > 0 ? facing : facing.getOpposite(), Math.abs(part.getzOffset()));
    }

    private BlockPos getMainBlockPos(BlockPos partPos, BlockState state) {
        Part part = state.getValue(PART);
        Direction facing = state.getValue(FACING);
        Direction left = facing.getCounterClockWise();
        return partPos.relative(part.getxOffset() > 0 ? left : left.getOpposite(), Math.abs(part.getxOffset()))
                .relative(part.getzOffset() > 0 ? facing.getOpposite() : facing, Math.abs(part.getzOffset()));
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return state.getValue(PART) == Part.FRONT_CENTER ? new TombBlockEntity(pos, state) : null;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        if (state.getValue(PART) == Part.FRONT_CENTER) {
            return createTickerHelper(type, DDBlockEntityTypes.TOMB.get(), TombBlockEntity::tick);
        }
        return null;
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (!level.isClientSide) {
            BlockPos mainPos = getMainBlockPos(pos, state);
            BlockEntity be = level.getBlockEntity(mainPos);
            if (be instanceof TombBlockEntity) {
                player.displayClientMessage(net.minecraft.network.chat.Component.literal("Interacted with Tomb at " + mainPos), true);
            }
        }
        return InteractionResult.SUCCESS;
    }

    private static VoxelShape rotateVoxelShape(VoxelShape shape, Direction direction) {
        if (direction == Direction.NORTH) {
            return shape;
        }

        List<VoxelShape> rotatedBoxes = new ArrayList<>();
        shape.toAabbs().forEach(aabb -> {
            double minX = aabb.minX, minY = aabb.minY, minZ = aabb.minZ;
            double maxX = aabb.maxX, maxY = aabb.maxY, maxZ = aabb.maxZ;

            VoxelShape rotatedBox;
            switch (direction) {
                case SOUTH:
                    rotatedBox = Shapes.box(1 - maxX, minY, 1 - maxZ, 1 - minX, maxY, 1 - minZ);
                    break;
                case WEST:
                    rotatedBox = Shapes.box(minZ, minY, 1 - maxX, maxZ, maxY, 1 - minX);
                    break;
                case EAST:
                    rotatedBox = Shapes.box(1 - maxZ, minY, minX, 1 - minZ, maxY, maxX);
                    break;
                default:
                    rotatedBox = shape;
                    break;
            }
            rotatedBoxes.add(rotatedBox);
        });

        return Shapes.or(Shapes.empty(), rotatedBoxes.toArray(new VoxelShape[0]));
    }
}
