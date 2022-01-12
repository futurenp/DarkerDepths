package com.naterbobber.darkerdepths.blocks;

import com.mojang.math.Vector3d;
import com.naterbobber.darkerdepths.init.DDBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Random;

public class AshFullBlock extends FallingBlock {

    public AshFullBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void entityInside(BlockState p_60495_, Level worldIn, BlockPos pos, Entity entityIn) {
        if (!(entityIn instanceof LivingEntity) || entityIn.level.getBlockState(entityIn.getOnPos()).getBlock().equals(DDBlocks.ASH_BLOCK.get())) {
            if (!entityIn.isSpectator() && (entityIn.xo != entityIn.getX() || entityIn.zo != entityIn.getZ()) && worldIn.random.nextBoolean()) {
                spawnAshParticles(worldIn, new Vector3d(entityIn.getX(), pos.getY(), entityIn.getZ()));
            }
        }
    }

    private void spawnAshParticles(Level worldIn, Vector3d vector3d) {
        if (worldIn.isClientSide()) {
            Random rand = worldIn.getRandom();
            double getY = vector3d.y + 1.0d;

            for (int i = 0; i < rand.nextInt(3); ++i) {
                worldIn.addParticle(ParticleTypes.CLOUD, vector3d.x, getY, vector3d.z, ((-1.0f + rand.nextFloat() * 2.0f) / 12.0f), 0.05000000074505806d, ((-1.0f + rand.nextFloat() * 2.0f) / 12.0f));
            }
        }
    }

    @Override
    public void animateTick(BlockState stateIn, Level worldIn, BlockPos pos, Random rand) {
        if (rand.nextInt(16) == 0) {
            BlockPos blockPos = pos.below();
            boolean isSolidAsh = DDBlocks.ASH.get().defaultBlockState().getValue(AshBlock.LAYERS) == 8;
            if (worldIn.isEmptyBlock(blockPos) || (worldIn.getBlockState(blockPos).is(DDBlocks.ASH.get()) && !isSolidAsh)) {
                double x = (double)pos.getX() + rand.nextDouble();
                double y = (double)pos.getY() - 0.05d;
                double z = (double)pos.getZ() + rand.nextDouble();
                worldIn.addParticle(new BlockParticleOption(ParticleTypes.FALLING_DUST, stateIn), x, y, z,0.0d, 0.0d, 0.0d);
            }
        }
    }

    @Override
    public int getDustColor(BlockState p_53238_, BlockGetter p_53239_, BlockPos p_53240_) {
        return 5132116;
    }
}
