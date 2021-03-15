package com.naterbobber.darkerdepths.common.blocks;

import com.naterbobber.darkerdepths.core.registries.DDBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

//<>

public class HeartChamberBlock extends Block {
    public static final IntegerProperty CHARGES = BlockStateProperties.CHARGES;

    public HeartChamberBlock(Properties properties) {
        super(properties);
        this.setDefaultState(this.stateContainer.getBaseState().with(CHARGES, 0));
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        ItemStack stack = player.getHeldItem(handIn);
        if (handIn == Hand.MAIN_HAND && !isValidFuel(stack) && isValidFuel(player.getHeldItem(Hand.OFF_HAND))) {
            return ActionResultType.PASS;
        } else if (isValidFuel(stack) && notFullyCharged(state)) {
            chargeChamber(worldIn, pos, state);
            if (!player.abilities.isCreativeMode) {
                stack.shrink(1);
            }

            return ActionResultType.SUCCESS;
        } else if (state.get(CHARGES) == 0) {
            return ActionResultType.PASS;
        } else {
            return notFullyCharged(state) ? ActionResultType.PASS : ActionResultType.CONSUME;
        }
    }

    @Override
    public int getLightValue(BlockState state, IBlockReader world, BlockPos pos) {
        return (int)Math.floor((state.get(CHARGES) / 4.0f) * 15.0f);
    }

    private static boolean isValidFuel(ItemStack stack) {
        return stack.getItem().equals(DDBlocks.AMBER.get().asItem());
    }

    private static boolean notFullyCharged(BlockState state) {
        return state.get(CHARGES) < 4;
    }

    public static void chargeChamber(World worldIn, BlockPos pos, BlockState state) {
        worldIn.setBlockState(pos, state.with(CHARGES, state.get(CHARGES) + 1), 3);
        worldIn.playSound(null, pos.getX() + 0.5d, pos.getY() + 0.5d, pos.getZ() + 0.5d, SoundEvents.ITEM_FIRECHARGE_USE, SoundCategory.BLOCKS, 1.0f, 1.0f);
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(CHARGES);
    }

    @Override
    public boolean hasComparatorInputOverride(BlockState state) {
        return true;
    }

    public static int getChargeScale(BlockState state, int scale) {
        return MathHelper.floor(state.get(CHARGES) / 4.0f * scale);
    }

    @Override
    public int getComparatorInputOverride(BlockState blockState, World worldIn, BlockPos pos) {
        return getChargeScale(blockState, 15);
    }

    @Override
    public boolean allowsMovement(BlockState state, IBlockReader worldIn, BlockPos pos, PathType type) {
        return false;
    }
}