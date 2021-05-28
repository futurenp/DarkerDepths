package com.naterbobber.darkerdepths.common.blocks;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.Random;

public class GeyserBlock extends Block {

    public GeyserBlock(AbstractBlock.Properties properties) {
        super(properties);
    }

    @Override
    public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand) {
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();
        double d = x + rand.nextDouble();
        double e = y + 0.7D;
        double f = z + rand.nextDouble();
        worldIn.addParticle(ParticleTypes.SMOKE, d, e, f, 0.0D, 0.0D, 0.0D);
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        for (int l = 0; l < 14; l++) {
            mutable.setPos(x + MathHelper.nextInt(rand, -10, 10), y - rand.nextInt(10), z + MathHelper.nextInt(rand, -10, 10));
            BlockState blockState = worldIn.getBlockState(pos);
            if (!blockState.isOpaqueCube(worldIn, mutable)) {
                worldIn.addParticle(ParticleTypes.SMOKE, mutable.getX() + rand.nextDouble(), mutable.getY() + rand.nextDouble(), mutable.getZ() + rand.nextDouble(), 0.0D, 0.0D, 0.0D);
            }
        }
    }
}
