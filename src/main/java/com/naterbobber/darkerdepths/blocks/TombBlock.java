package com.naterbobber.darkerdepths.blocks;

import com.naterbobber.darkerdepths.blocks.blockentities.TombBlockEntity;
import com.naterbobber.darkerdepths.init.DDBlockEntityTypes;
import com.naterbobber.darkerdepths.init.DDBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
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
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.*;

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

        public int xOffset() {
            return this.xOffset;
        }

        public int zOffset() {
            return this.zOffset;
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

        VoxelShape shape = switch (part) {
            case FRONT_CENTER -> FRONT_CENTER_SHAPE;
            case FRONT_LEFT -> FRONT_LEFT_SHAPE;
            case FRONT_RIGHT -> FRONT_RIGHT_SHAPE;
            case BACK_CENTER -> BACK_CENTER_SHAPE;
            case BACK_LEFT -> BACK_LEFT_SHAPE;
            case BACK_RIGHT -> BACK_RIGHT_SHAPE;
        };

        return rotateVoxelShape(shape, facing);
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return state.getValue(PART) == Part.FRONT_CENTER ? RenderShape.ENTITYBLOCK_ANIMATED : RenderShape.MODEL;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        FluidState fluid = context.getLevel().getFluidState(context.getClickedPos());
        return this.defaultBlockState()
                .setValue(FACING, context.getHorizontalDirection().getOpposite())
                .setValue(PART, Part.FRONT_CENTER)
                .setValue(WATERLOGGED, fluid.getType() == Fluids.WATER);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
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
            this.placeMultiblockParts(level, pos, state);
        }
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
        if (!level.isClientSide && !state.is(newState.getBlock())) {
            this.removeMultiblockParts(level, pos, state);
        }

        super.onRemove(state, level, pos, newState, isMoving);
    }

    @Override
    public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor level, BlockPos currentPos, BlockPos facingPos) {
        if (state.getValue(WATERLOGGED)) {
            level.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        }

        Part part = state.getValue(PART);
        if (part != Part.FRONT_CENTER) {
            BlockPos mainPos = getMainBlockPos(currentPos, state);
            if (!level.getBlockState(mainPos).is(this)) {
                return Blocks.AIR.defaultBlockState();
            }
        }

        return super.updateShape(state, facing, facingState, level, currentPos, facingPos);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (!level.isClientSide) {
            BlockPos mainPos = getMainBlockPos(pos, state);
            BlockEntity blockEntity = level.getBlockEntity(mainPos);
            if (blockEntity instanceof TombBlockEntity tombEntity) {
                tombEntity.toggleTomb();
            }
        }

        return InteractionResult.SUCCESS;
    }

    @Override
    public List<ItemStack> getDrops(BlockState state, LootParams.Builder params) {
        return super.getDrops(state, params);
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

    private record MultiblockPartData(BlockPos pos, BlockState state) {}

    private static Map<Part, MultiblockPartData> generateMultiblockPartData(BlockPos mainPos, Direction facing, LevelReader level) {
        Map<Part, MultiblockPartData> parts = new HashMap<>();

        for (Part part : Part.values()) {
            BlockPos partPos = getPartPos(mainPos, part, facing);
            boolean isWaterlogged = level.getFluidState(partPos).getType() == Fluids.WATER;
            BlockState partState = DDBlocks.TOMB.get().defaultBlockState().setValue(FACING, facing).setValue(PART, part).setValue(WATERLOGGED, isWaterlogged);


            parts.put(part, new MultiblockPartData(partPos, partState));
        }

        return parts;
    }

    public static void generateMultiblockForProcessor(BlockPos mainPos, Direction facing, LevelReader level, List<StructureTemplate.StructureBlockInfo> targetList, BlockPos relativePos) {
        targetList.clear();
        Map<Part, MultiblockPartData> parts = generateMultiblockPartData(mainPos, facing, level);

        for (MultiblockPartData partData : parts.values()) {
            targetList.add(new StructureTemplate.StructureBlockInfo(relativePos.offset(partData.pos().subtract(mainPos)), partData.state(), null));
        }
    }

    private void placeMultiblockParts(Level level, BlockPos mainPos, BlockState mainState) {
        Direction facing = mainState.getValue(FACING);
        Map<Part, MultiblockPartData> parts = generateMultiblockPartData(mainPos, facing, level);

        for (MultiblockPartData partData : parts.values()) {
            level.setBlock(partData.pos(), partData.state(), 3);
        }
    }

    private void removeMultiblockParts(Level level, BlockPos pos, BlockState state) {
        Direction facing = state.getValue(FACING);
        Map<Part, MultiblockPartData> parts = generateMultiblockPartData(getMainBlockPos(pos, state), facing, level);

        for (MultiblockPartData partData : parts.values()) {
            BlockState replace = partData.state().getValue(WATERLOGGED) ? Blocks.WATER.defaultBlockState() : Blocks.AIR.defaultBlockState();
            level.setBlock(partData.pos(), replace, 3);
            //particle effect only works on center state?
            level.addDestroyBlockEffect(partData.pos(), partData.state());
        }
    }

    public static BlockPos getPartPos(BlockPos mainPos, Part part, Direction facing) {
        Direction right = facing.getClockWise();
        return mainPos.relative(part.xOffset() > 0 ? right : right.getOpposite(), Math.abs(part.xOffset()))
                .relative(part.zOffset() > 0 ? facing : facing.getOpposite(), Math.abs(part.zOffset()));
    }

    private BlockPos getMainBlockPos(BlockPos partPos, BlockState state) {
        Part part = state.getValue(PART);
        Direction facing = state.getValue(FACING);
        Direction left = facing.getCounterClockWise();
        return partPos.relative(part.xOffset() > 0 ? left : left.getOpposite(), Math.abs(part.xOffset()))
                .relative(part.zOffset() > 0 ? facing.getOpposite() : facing, Math.abs(part.zOffset()));
    }

    private static VoxelShape rotateVoxelShape(VoxelShape shape, Direction direction) {
        if (direction == Direction.NORTH) {
            return shape;
        }

        List<VoxelShape> rotatedBoxes = new ArrayList<>();
        shape.toAabbs().forEach(aabb -> {
            double minX = aabb.minX, minY = aabb.minY, minZ = aabb.minZ;
            double maxX = aabb.maxX, maxY = aabb.maxY, maxZ = aabb.maxZ;

            VoxelShape rotatedBox = switch (direction) {
                case SOUTH -> Shapes.box(1 - maxX, minY, 1 - maxZ, 1 - minX, maxY, 1 - minZ);
                case WEST -> Shapes.box(minZ, minY, 1 - maxX, maxZ, maxY, 1 - minX);
                case EAST -> Shapes.box(1 - maxZ, minY, minX, 1 - minZ, maxY, maxX);
                default -> shape;
            };
            rotatedBoxes.add(rotatedBox);
        });

        return Shapes.or(Shapes.empty(), rotatedBoxes.toArray(new VoxelShape[0]));
    }
}