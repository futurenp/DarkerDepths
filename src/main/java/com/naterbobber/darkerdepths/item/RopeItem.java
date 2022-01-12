package com.naterbobber.darkerdepths.item;

import com.naterbobber.darkerdepths.blocks.RopeBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluids;
import org.jetbrains.annotations.Nullable;

public class RopeItem extends BlockItem {

    public RopeItem(Block block, Properties properties) {
        super(block, properties);
    }

    @Nullable
    @Override
    public BlockPlaceContext updatePlacementContext(BlockPlaceContext context) {
        Level world = context.getLevel();
        BlockPos pos = context.getClickedPos();
        Direction face = context.getClickedFace();
        if (face != Direction.DOWN && face != Direction.UP) {
            boolean flag = false;
            int j = 1;
            while (!flag) {
                BlockPos checkingPos = pos.relative(face.getOpposite()).below(j);
                if (world.getBlockState(checkingPos).getMaterial().isReplaceable() && (world.getBlockState(checkingPos).getFluidState().isEmpty()) || world.getBlockState(checkingPos).getFluidState().getType() == Fluids.WATER) {
                    return BlockPlaceContext.at(context, checkingPos, Direction.DOWN);
                } else if (world.getBlockState(checkingPos).getBlock() instanceof RopeBlock) {
                    j++;
                } else {
                    flag = true;
                }
            }
        }
        return super.updatePlacementContext(context);
    }
}
