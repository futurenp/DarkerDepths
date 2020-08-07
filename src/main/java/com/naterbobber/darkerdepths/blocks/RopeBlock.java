package com.naterbobber.darkerdepths.blocks;

import com.naterbobber.darkerdepths.init.ItemInit;
import com.naterbobber.darkerdepths.util.RopePart;
import net.minecraft.block.*;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

public class RopeBlock extends Block implements IBucketPickupHandler, ILiquidContainer {

    public static final EnumProperty<RopePart> PART = EnumProperty.create("part", RopePart.class);
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public RopeBlock(Properties properties) {
        super(properties);
        this.setDefaultState(this.stateContainer.getBaseState().with(PART, RopePart.MIDDLE).with(WATERLOGGED, false));
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader reader, BlockPos pos, ISelectionContext context) {
        return state.get(PART) == RopePart.BOTTOM ? Block.makeCuboidShape(6.0F, 6.0F, 6.0F, 10.0F, 16.0F, 10.0F) : Block.makeCuboidShape(6.0F, 0.0F, 6.0F, 10.0F, 16.0F, 10.0F);
    }

    @Override
    public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
        return (worldIn.getBlockState(pos.up()).isSolidSide(worldIn, pos, Direction.DOWN) || worldIn.getBlockState(pos.up()).getBlock() instanceof RopeBlock) && (!(worldIn.getBlockState(pos.up()).getBlock() instanceof AirBlock));
    }

/*    @Override
    public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult rayTraceResult) {
        if (player.getHeldItem(hand).getItem() == ItemInit.rope && rayTraceResult.getFace() != Direction.DOWN) {
            boolean flag = false;
            int j = 1;
            while (!flag) {
                BlockPos checkingPos = pos.down(j);
                if (world.getBlockState(checkingPos).getMaterial().isReplaceable()) {
                    world.setBlockState(checkingPos, this.getDefaultState().with(PART, RopePart.BOTTOM));
                    break;
                } else if (world.getBlockState(checkingPos).getBlock() instanceof RopeBlock) {
                    j++;
                } else {
                    flag = true;
                }
            }
        }
        return super.onBlockActivated(state, world, pos, player, hand, rayTraceResult);
    }*/

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        boolean water = context.getWorld().getBlockState(context.getPos()).getBlock() == Blocks.WATER;
        BlockState state = super.getStateForPlacement(context);
        if (state != null) {
            if (isRopeBottom(context.getWorld(), context.getPos())) {
                state = state.with(PART, RopePart.BOTTOM);
            } else if (isRopeTop(context.getWorld(), context.getPos())) {
                state = state.with(PART, RopePart.TOP);
            }
        } else return null;
        return state.with(WATERLOGGED, water);
    }

    @Override
    public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
        worldIn.getPendingBlockTicks().scheduleTick(currentPos, this, 1);
        if (stateIn.get(WATERLOGGED)) {
            worldIn.getPendingFluidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickRate(worldIn));
        }
        if (isRopeBottom(worldIn, currentPos)) {
            stateIn = stateIn.with(PART, RopePart.BOTTOM);
        } else if (isRopeTop(worldIn, currentPos)) {
            stateIn = stateIn.with(PART, RopePart.TOP);
        } else {
            stateIn = stateIn.with(PART, RopePart.MIDDLE);
        }
        return isValidPosition(stateIn, worldIn, currentPos) ? super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos) : Blocks.AIR.getDefaultState();
    }

    public boolean isRopeBottom(IWorld world, BlockPos pos) {
        return (!(world.getBlockState(pos.down()).getBlock() instanceof RopeBlock));
    }

    public boolean isRopeTop(IWorld world, BlockPos pos) {
        return (!(world.getBlockState(pos.up()).getBlock() instanceof RopeBlock));
    }

    @Override
    public boolean isLadder(BlockState state, IWorldReader world, BlockPos pos, LivingEntity entity) {
        return true;
    }

    @Override
    public Fluid pickupFluid(IWorld world, BlockPos pos, BlockState state) {
        if (state.get(WATERLOGGED)) {
            world.setBlockState(pos, state.with(WATERLOGGED, false), 3);
            return Fluids.WATER;
        } else {
            return Fluids.EMPTY;
        }
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStillFluidState(false) : super.getFluidState(state);
    }

    @Override
    public boolean canContainFluid(IBlockReader world, BlockPos pos, BlockState state, Fluid fluid) {
        return fluid == Fluids.WATER;
    }

    @Override
    public boolean receiveFluid(IWorld world, BlockPos pos, BlockState state, FluidState fluidState) {
        if (fluidState.getFluid() == Fluids.WATER) {
            if (!world.isRemote()) {
                world.setBlockState(pos, state.with(WATERLOGGED, true), 3);
                world.getPendingFluidTicks().scheduleTick(pos, fluidState.getFluid(), fluidState.getFluid().getTickRate(world));
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(PART, WATERLOGGED);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }


}
