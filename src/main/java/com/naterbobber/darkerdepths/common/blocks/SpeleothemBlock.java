package com.naterbobber.darkerdepths.common.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Direction;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.Locale;

public class SpeleothemBlock extends Block implements IWaterLoggable {
    public static final EnumProperty<SpeleothemSize> SIZE = EnumProperty.create("size", SpeleothemSize.class);
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public SpeleothemBlock(Properties properties) {
        super(properties);
        this.setDefaultState(this.stateContainer.getBaseState().with(SIZE, SpeleothemSize.BIG).with(WATERLOGGED, false));
    }

    @Override
    public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
        return this.getBearing(worldIn, pos) > 0;
    }

    @Override
    @Deprecated
    public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
        if(stateIn.getBlock() instanceof SpeleothemBlock){
            boolean invalidSpot = getBearing(worldIn, currentPos) < stateIn.get(SIZE).strength + 1;
            if(invalidSpot){
                if (stateIn.get(WATERLOGGED)) {
                    stateIn = Blocks.WATER.getDefaultState();
                }
                else {
                    stateIn = Blocks.AIR.getDefaultState();
                }
            }
        }
        return stateIn;
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        SpeleothemSize size = SpeleothemSize.values()[Math.max(0, getBearing(context.getWorld(), context.getPos()) - 1)];
        return getDefaultState().with(SIZE, size).with(WATERLOGGED, context.getWorld().getFluidState(context.getPos()).getFluid() == Fluids.WATER);
    }

    @Nonnull
    @Override
    @SuppressWarnings("deprecation")
    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStillFluidState(false) : super.getFluidState(state);
    }

    @Override
    public void neighborChanged(BlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
        int size = state.get(SIZE).strength;
        if(getBearing(worldIn, pos) < size + 1)
            worldIn.destroyBlock(pos, false);
    }

    @Override
    public boolean allowsMovement(@Nonnull BlockState state, @Nonnull IBlockReader worldIn, @Nonnull BlockPos pos, PathType type) {
        return type == PathType.WATER && worldIn.getFluidState(pos).isTagged(FluidTags.WATER);
    }

    private int getBearing(IWorldReader world, BlockPos pos) {
        return Math.max(getStrength(world, pos.down()), getStrength(world, pos.up()));
    }

    private int getStrength(IWorldReader world, BlockPos pos) {
        BlockState state = world.getBlockState(pos);
        if(state.isSolid())
            return 3;

        if(state.getValues().containsKey(SIZE))
            return state.get(SIZE).strength;

        return 0;
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return state.get(SIZE).shape;
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(SIZE, WATERLOGGED);
    }

    public enum SpeleothemSize implements IStringSerializable {
        SMALL(0, 2),
        MEDIUM(1, 4),
        BIG(2, 8);

        public final int strength;
        public final VoxelShape shape;

        SpeleothemSize(int strength, int width) {
            this.strength = strength;

            int pad = (16 - width) / 2;
            shape = Block.makeCuboidShape(pad, 0, pad, 16 - pad, 16, 16 - pad);
        }

        @Override
        public String getString() {
            return name().toLowerCase(Locale.ROOT);
        }
    }
}
