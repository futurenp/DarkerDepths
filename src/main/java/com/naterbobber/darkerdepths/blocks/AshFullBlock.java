package com.naterbobber.darkerdepths.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Fallable;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class AshFullBlock extends Block implements Fallable {

    public AshFullBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void onPlace(BlockState state, Level world, BlockPos blockPos, BlockState p_60569_, boolean p_60570_) {
        world.scheduleTick(blockPos, this, 120);
    }

    @Override
    public BlockState updateShape(BlockState blockState, Direction direction, BlockState p_60543_, LevelAccessor world, BlockPos blockPos, BlockPos p_60546_) {
        world.scheduleTick(blockPos, this, 120);
        return super.updateShape(blockState, direction, p_60543_, world, blockPos, p_60546_);
    }

    @Override
    public void stepOn(Level world, BlockPos blockPos, BlockState state, Entity p_152434_) {
        this.fall(world, blockPos, state);
    }

    @Override
    public void onProjectileHit(Level world, BlockState state, BlockHitResult hit, Projectile p_60456_) {
        this.fall(world, hit.getBlockPos(), state);
    }

    private void fall(Level world, BlockPos blockPos, BlockState state) {
        if (FallingBlock.isFree(world.getBlockState(blockPos.below())) && blockPos.getY() >= world.getMinBuildHeight()) {
            FallingBlockEntity.fall(world, blockPos, state);
            world.scheduleTick(blockPos, this, 120);
        }
    }

}
