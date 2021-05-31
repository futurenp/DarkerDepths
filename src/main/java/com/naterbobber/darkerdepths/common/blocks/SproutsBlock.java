package com.naterbobber.darkerdepths.common.blocks;

import com.naterbobber.darkerdepths.core.registries.DDBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.BushBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;

//<>

public class SproutsBlock extends BushBlock {
    protected static final VoxelShape SHAPE = Block.makeCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 3.0D, 14.0D);

    public SproutsBlock(Properties properties) {
        super(properties);
    }
    @Override

    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return SHAPE;
    }

    @Override
    protected boolean isValidGround(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return state.matchesBlock(DDBlocks.LUSH_ARIDROCK.get()) || state.matchesBlock(DDBlocks.MOSSY_GRIMESTONE.get()) || super.isValidGround(state, worldIn, pos);
    }

    @Override
    public OffsetType getOffsetType() {
        return OffsetType.XZ;
    }
}