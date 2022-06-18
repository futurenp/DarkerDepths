package com.naterbobber.darkerdepths.blocks;

import com.naterbobber.darkerdepths.init.DDBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Random;

public class DecayedRootBlock extends BushBlock implements BonemealableBlock {
    private static final VoxelShape SHAPE = Block.box(2.0D, 3.0D, 2.0D, 14.0D, 16.0D, 14.0D);

    public DecayedRootBlock(Properties properties) {
        super(properties);
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
        return Block.canSupportCenter(world, pos.above(), Direction.DOWN);
    }

    @Override
    public boolean isFlammable(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
        return true;
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState facingState, LevelAccessor world, BlockPos pos, BlockPos facingPos) {
        return world.getBlockState(pos.above()).is(Blocks.AIR) ? Blocks.AIR.defaultBlockState() : super.updateShape(state, direction, facingState, world, pos, facingPos);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }


    @Override
    public boolean isValidBonemealTarget(BlockGetter world, BlockPos pos, BlockState state, boolean isClient) {
        return true;
    }

    @Override
    public boolean isBonemealSuccess(Level world, RandomSource random, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel worldIn, RandomSource random, BlockPos pos, BlockState state) {
        if(!worldIn.isClientSide() && !worldIn.isEmptyBlock(pos.below(2))) {
            worldIn.setBlock(pos, DDBlocks.LONG_ROOTS.get().defaultBlockState(), 2);
            worldIn.setBlock(pos.below(), DDBlocks.ROOTS.get().defaultBlockState(), 2);
        }
    }
}
