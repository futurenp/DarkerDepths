package com.naterbobber.darkerdepths.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.AirBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BucketPickup;
import net.minecraft.world.level.block.LiquidBlockContainer;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class RopeBlock extends Block implements BucketPickup, LiquidBlockContainer {
    public static final EnumProperty<RopePart> PART = EnumProperty.create("part", RopePart.class);
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public RopeBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(PART, RopePart.MIDDLE).setValue(WATERLOGGED, false));
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter p_60556_, BlockPos p_60557_, CollisionContext p_60558_) {
        if (state.getValue(PART) == RopePart.BOTTOM) {
            return Block.box(6.0F, 8.0F, 6.0F, 10.0F, 16.0F, 10.0F);
        } else if (state.getValue(PART) == RopePart.MIDDLE) {
            return Block.box(6.0F, 0.0F, 6.0F, 10.0F, 16.0F, 10.0F);
        } else return Shapes.or(Block.box(6.0F, 0.0F, 6.0F, 10.0F, 15.0F, 10.0F), Block.box(7.0F, 12.0F, 11.0F, 9.0F, 16.0F, 13.0F), Block.box(7.0F, 12.0F, 3.0F, 9.0F, 16.0F, 5.0F), Block.box(7.0F, 12.0F, 5.0F, 9.0F, 14.0F, 11.0F));
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader worldIn, BlockPos pos) {
        return (worldIn.getBlockState(pos.above()).isFaceSturdy(worldIn, pos, Direction.DOWN) || worldIn.getBlockState(pos.above()).getBlock() instanceof RopeBlock) && !(worldIn.getBlockState(pos.above()).getBlock() instanceof AirBlock);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        boolean water = context.getLevel().getBlockState(context.getClickedPos()).getBlock() == Blocks.WATER;
        BlockState state = super.getStateForPlacement(context);
        if (state != null) {
            if (isRopeBottom(context.getLevel(), context.getClickedPos())) {
                state = state.setValue(PART, RopePart.BOTTOM);
            } else if (isRopeTop(context.getLevel(), context.getClickedPos())) {
                state = state.setValue(PART, RopePart.TOP);
            }
        } else return null;
        return state.setValue(WATERLOGGED, water);
    }

    @Override
    public boolean isLadder(BlockState state, LevelReader world, BlockPos pos, LivingEntity entity) {
        return true;
    }

    @Override
    public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor worldIn, BlockPos currentPos, BlockPos facingPos) {
        worldIn.scheduleTick(currentPos, this, 1);
        if (stateIn.getValue(WATERLOGGED)) {
            worldIn.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(worldIn));
        }
        if (isRopeBottom(worldIn, currentPos)) {
            stateIn = stateIn.setValue(PART, RopePart.BOTTOM);
        } else if (isRopeTop(worldIn, currentPos)) {
            stateIn = stateIn.setValue(PART, RopePart.TOP);
        } else {
            stateIn = stateIn.setValue(PART, RopePart.MIDDLE);
        }
        return canSurvive(stateIn, worldIn, currentPos) ? super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos) : Blocks.AIR.defaultBlockState();

    }

    public boolean isRopeBottom(LevelAccessor world, BlockPos pos) {
        return (!(world.getBlockState(pos.below()).getBlock() instanceof RopeBlock));
    }

    public boolean isRopeTop(LevelAccessor world, BlockPos pos) {
        return (!(world.getBlockState(pos.above()).getBlock() instanceof RopeBlock));
    }

    @Override
    public boolean isFlammable(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
        return true;
    }

    @Override
    public ItemStack pickupBlock(Player player, LevelAccessor world, BlockPos pos, BlockState state) {
        if (state.getValue(WATERLOGGED)) {
            world.setBlock(pos, state.setValue(WATERLOGGED, false), 3);
            return new ItemStack(Items.WATER_BUCKET);
        } else {
            return ItemStack.EMPTY;
        }
    }

    @Override
    public Optional<SoundEvent> getPickupSound() {
        return Optional.empty();
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }


    @Override
    public boolean canPlaceLiquid(Player player, BlockGetter blockGetter, BlockPos blockPos, BlockState blockState, Fluid fluid) {
        return fluid == Fluids.WATER;
    }

    @Override
    public boolean placeLiquid(LevelAccessor world, BlockPos pos, BlockState state, FluidState fluidState) {
        if (fluidState.getType() == Fluids.WATER) {
            if (!world.isClientSide()) {
                world.setBlock(pos, state.setValue(WATERLOGGED, true), 3);
                world.scheduleTick(pos, fluidState.getType(), fluidState.getType().getTickDelay(world));
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(PART, WATERLOGGED);
    }

    @Override
    public RenderShape getRenderShape(BlockState p_60550_) {
        return RenderShape.MODEL;
    }

    public enum RopePart implements StringRepresentable {
        TOP("top"),
        MIDDLE("middle"),
        BOTTOM("bottom");

        private final String name;

        RopePart(String string) {
            this.name = string;
        }

        public String toString() {
            return this.name;
        }

        @Override
        public String getSerializedName() {
            return this.name;
        }
    }
}
