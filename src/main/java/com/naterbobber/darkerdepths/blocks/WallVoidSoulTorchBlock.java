package com.naterbobber.darkerdepths.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.WallTorchBlock;
import net.minecraft.world.level.block.state.BlockState;
import java.util.function.Supplier;

public class WallVoidSoulTorchBlock extends WallTorchBlock {
    protected final Supplier<ParticleOptions> customParticleSupplier;

    public WallVoidSoulTorchBlock(Properties properties, Supplier<ParticleOptions> particleSupplier) {
        super(properties, ParticleTypes.FLAME);
        this.customParticleSupplier = particleSupplier;
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        Direction direction = state.getValue(FACING);
        double xOffset = 0.3D * (double)direction.getStepX();
        double zOffset = 0.3D * (double)direction.getStepZ();

        double particleX = (double)pos.getX() + 0.5D - xOffset;
        double particleY = (double)pos.getY()+ 0.75D;
        double particleZ = (double)pos.getZ() + 0.5D - zOffset;

        level.addParticle(this.customParticleSupplier.get(), particleX, particleY, particleZ, 0.0D, 0.0D, 0.0D);
    }
}