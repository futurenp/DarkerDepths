package com.naterbobber.darkerdepths.block.custom;

import com.naterbobber.darkerdepths.init.DDBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.GrowingPlantHeadBlock;
import net.minecraft.world.level.block.NetherVines;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class PetrifiedRootBlock extends GrowingPlantHeadBlock {
    private static final VoxelShape SHAPE = Block.box(2.0D, 3.0D, 2.0D, 14.0D, 16.0D, 14.0D);

    public PetrifiedRootBlock(Properties properties) {
        super(properties, Direction.DOWN, SHAPE, false, 0.1D);
    }

    @Override
    public boolean isRandomlyTicking(BlockState p_53961_) {
        return false;
    }

    @Override
    public VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext ctx) {
        Vec3 vec3 = blockState.getOffset(blockGetter, blockPos);
        return super.getShape(blockState, blockGetter, blockPos, ctx).move(vec3.x, 0.0D, vec3.z);
    }

    @Override
    protected int getBlocksToGrowWhenBonemealed(RandomSource randomSource) {
        return NetherVines.getBlocksToGrowWhenBonemealed(randomSource);
    }

    @Override
    protected boolean canGrowInto(BlockState state) {
        return state.isAir();
    }

    @Override
    protected Block getBodyBlock() {
        return DDBlocks.PETRIFIED_ROOTS_PLANT.get();
    }
}
