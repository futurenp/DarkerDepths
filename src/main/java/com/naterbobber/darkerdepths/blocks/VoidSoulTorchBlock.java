package com.naterbobber.darkerdepths.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.TorchBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.function.Supplier;

public class VoidSoulTorchBlock extends TorchBlock {
    protected final Supplier<ParticleOptions> customParticleSupplier;
    private static final VoxelShape SHAPE = Block.box(6.0, 0.0, 6.0, 10.0, 11.0, 10.0);


    public VoidSoulTorchBlock(Supplier<ParticleOptions> particleSupplier, Properties properties) {
        super(ParticleTypes.FLAME, properties);
        this.customParticleSupplier = particleSupplier;
    }

    @Override
    public VoxelShape getShape(BlockState stateIn, BlockGetter worldIn, BlockPos pos, CollisionContext p_57513_) {
        return SHAPE;
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        double x = (double)pos.getX() + 0.5D;
        double y = (double)pos.getY() + 0.62D;
        double z = (double)pos.getZ() + 0.5D;
        level.addParticle(this.customParticleSupplier.get(), x, y, z, 0.0D, 0.0D, 0.0D);
    }
}