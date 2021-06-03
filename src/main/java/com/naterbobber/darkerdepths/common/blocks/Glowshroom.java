package com.naterbobber.darkerdepths.common.blocks;

import com.naterbobber.darkerdepths.common.world.gen.DDConfiguredFeatures;
import com.naterbobber.darkerdepths.core.registries.DDBlocks;
import com.naterbobber.darkerdepths.core.registries.DDItems;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.IGrowable;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;
import java.util.Random;
import java.util.function.ToIntFunction;

public class Glowshroom extends Block implements IGrowable, IWaterLoggable {
    public static final IntegerProperty CLUSTERS_1_3 = IntegerProperty.create("clusters", 1, 3);
    private static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    protected static final VoxelShape SHAPE_1  = Block.makeCuboidShape(5.0D, 0.0D, 5.0D, 11.0D, 9.0D, 11.0D);
    protected static final VoxelShape SHAPE_2  = Block.makeCuboidShape(3.0D, 0.0D, 3.0D, 13.0D, 9.0D, 13.0D);
    protected static final VoxelShape SHAPE_3  = Block.makeCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 9.0D, 14.0D);

    public Glowshroom(Properties properties) {
        super(properties);
        this.setDefaultState(this.stateContainer.getBaseState().with(WATERLOGGED, false).with(CLUSTERS_1_3, 1));
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        int i = state.get(CLUSTERS_1_3);
        ItemStack stack = player.getHeldItem(handIn);
        if (stack.getItem() == DDBlocks.GLOWSHROOM.get().asItem() && i < 3) {
            worldIn.playSound(null, pos, SoundEvents.BLOCK_SLIME_BLOCK_PLACE, SoundCategory.BLOCKS, 1.0F, 1.0F);
            worldIn.setBlockState(pos, state.with(CLUSTERS_1_3, i + 1), 2);
            if (!player.abilities.isCreativeMode) {
                stack.shrink(1);
            }
            return ActionResultType.SUCCESS;
        } else {
            return ActionResultType.FAIL;
        }
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        FluidState state = context.getWorld().getFluidState(context.getPos());
        return this.getDefaultState().with(WATERLOGGED, state.getFluid() == Fluids.WATER);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStillFluidState(false) : super.getFluidState(state);
    }

    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        switch (state.get(CLUSTERS_1_3)) {
            case 1:
            default:
                return SHAPE_1;

            case 2:
                return SHAPE_2;

            case 3:
                return SHAPE_3;
        }
    }

    public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
        return facing == Direction.DOWN && !this.isValidPosition(stateIn, worldIn, currentPos) ? Blocks.AIR.getDefaultState() : super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
    }

    public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
        return hasEnoughSolidSide(worldIn, pos.down(), Direction.UP);
    }

    @Override
    public boolean canGrow(IBlockReader worldIn, BlockPos pos, BlockState state, boolean isClient) {
        return state.get(CLUSTERS_1_3) == 1 && worldIn.getBlockState(pos.down()) == DDBlocks.MOSSY_GRIMESTONE.get().getDefaultState();
    }

    @Override
    public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, BlockState state) {
        return rand.nextFloat() < 0.4D && state.get(CLUSTERS_1_3) == 1;
    }

    @Override
    public void grow(ServerWorld worldIn, Random rand, BlockPos pos, BlockState stateIn) {
        DDConfiguredFeatures.HUGE_GLOWSHROOM_PLANTED.generate(worldIn, worldIn.getChunkProvider().getChunkGenerator(), rand, pos);
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        super.fillStateContainer(builder);
        builder.add(WATERLOGGED, CLUSTERS_1_3);
    }
}