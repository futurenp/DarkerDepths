package com.naterbobber.darkerdepths.items;

import com.naterbobber.darkerdepths.blocks.RopeBlock;
import com.naterbobber.darkerdepths.init.ItemInit;
import com.naterbobber.darkerdepths.util.RopePart;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItem;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class RopeItem extends BlockItem {

    public RopeItem(Block block, Properties properties) {
        super(block, properties);
    }

    @Nullable
    @Override
    public BlockItemUseContext getBlockItemUseContext(BlockItemUseContext context) {
        World world = context.getWorld();
        BlockPos pos = context.getPos();
        Direction face = context.getFace();
        if (face != Direction.DOWN && face != Direction.UP) {
            boolean flag = false;
            int j = 1;
            while (!flag) {
                BlockPos checkingPos = pos.offset(face.getOpposite()).down(j);
                if (world.getBlockState(checkingPos).getMaterial().isReplaceable() && (world.getBlockState(checkingPos).getFluidState().isEmpty()) || world.getBlockState(checkingPos).getFluidState().getFluid() == Fluids.WATER) {
                    return BlockItemUseContext.func_221536_a(context, checkingPos, Direction.DOWN);
                } else if (world.getBlockState(checkingPos).getBlock() instanceof RopeBlock) {
                    j++;
                } else {
                    flag = true;
                }
            }
        }
        return super.getBlockItemUseContext(context);
    }
}
