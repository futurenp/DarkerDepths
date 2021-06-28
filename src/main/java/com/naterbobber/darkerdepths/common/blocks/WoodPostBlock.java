package com.naterbobber.darkerdepths.common.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ChainBlock;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.block.LanternBlock;
import net.minecraft.block.SoundType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.Property;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;

public class WoodPostBlock extends Block implements IWaterLoggable {

    private static final VoxelShape SHAPE_X = Block.makeCuboidShape(0F, 6F, 6F, 16F, 10F, 10F);
    private static final VoxelShape SHAPE_Y = Block.makeCuboidShape(6F, 0F, 6F, 10F, 16F, 10F);
    private static final VoxelShape SHAPE_Z = Block.makeCuboidShape(6F, 6F, 0F, 10F, 10F, 16F);

    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final EnumProperty<Direction.Axis> AXIS = BlockStateProperties.AXIS;

    public static final BooleanProperty[] CHAINED = new BooleanProperty[] {
            BooleanProperty.create("chain_down"),
            BooleanProperty.create("chain_up"),
            BooleanProperty.create("chain_north"),
            BooleanProperty.create("chain_south"),
            BooleanProperty.create("chain_west"),
            BooleanProperty.create("chain_east")
    };

    public Block strippedBlock = null;

    public WoodPostBlock(Properties properties) {
        super(properties);

        BlockState state = stateContainer.getBaseState().with(WATERLOGGED, false).with(AXIS, Direction.Axis.Y);
        for(BooleanProperty prop : CHAINED)
            state = state.with(prop, false);
        setDefaultState(state);
    }

    @Override
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public BlockState getToolModifiedState(BlockState state, World world, BlockPos pos, PlayerEntity player, ItemStack stack, ToolType toolType) {
        if(strippedBlock == null || toolType != ToolType.AXE)
            return super.getToolModifiedState(state, world, pos, player, stack, toolType);

        BlockState newState = strippedBlock.getDefaultState();
        for(Property p : state.getProperties())
            newState = newState.with(p, state.get(p));

        return newState;
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        switch(state.get(AXIS)) {
            case X: return SHAPE_X;
            case Y: return SHAPE_Y;
            default: return SHAPE_Z;
        }
    }

    @Override
    public boolean propagatesSkylightDown(BlockState state, IBlockReader reader, BlockPos pos) {
        return !state.get(WATERLOGGED);
    }

    @Override
    @SuppressWarnings("deprecation")
    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStillFluidState(false) : super.getFluidState(state);
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return getState(context.getWorld(), context.getPos(), context.getFace().getAxis());
    }

    @Override
    @SuppressWarnings("deprecation")
    public void neighborChanged(BlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
        super.neighborChanged(state, worldIn, pos, blockIn, fromPos, isMoving);

        BlockState newState = getState(worldIn, pos, state.get(AXIS));
        if(!newState.equals(state))
            worldIn.setBlockState(pos, newState);
    }

    private BlockState getState(World world, BlockPos pos, Direction.Axis axis) {
        BlockState state = getDefaultState().with(WATERLOGGED, world.getFluidState(pos).getFluid() == Fluids.WATER).with(AXIS, axis);

        for(Direction d : Direction.values()) {
            if(d.getAxis() == axis)
                continue;

            BlockState sideState = world.getBlockState(pos.offset(d));
            if((sideState.getBlock() instanceof ChainBlock && sideState.get(BlockStateProperties.AXIS) == d.getAxis())
                    || (d == Direction.DOWN && sideState.getBlock() instanceof LanternBlock && sideState.get(LanternBlock.HANGING))) {
                BooleanProperty prop = CHAINED[d.ordinal()];
                state = state.with(prop, true);
            }
        }

        return state;
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED, AXIS);
        for(BooleanProperty prop : CHAINED)
            builder.add(prop);
    }

}
