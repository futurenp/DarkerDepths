package com.naterbobber.darkerdepths.common.blocks;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.Random;

public class GeyserBlock extends Block {

    public GeyserBlock(AbstractBlock.Properties properties) {
        super(properties);
    }

    @Override
    public void onEntityWalk(World worldIn, BlockPos pos, Entity entityIn) {
        if (entityIn instanceof LivingEntity && !(entityIn instanceof PlayerEntity)) {
            Vector3d vector3d = entityIn.getMotion();
            entityIn.setMotion(vector3d.x, vector3d.y + 1.2D, vector3d.z);
        }
    }

    @Override
    public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand) {
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();
        double d0 = x + 0.5D;
        double d1 = y + rand.nextDouble() + rand.nextDouble();
        double d2 = z + 0.5D;
        worldIn.addParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE, d0, d1, d2, 0.0D, 0.07D, 0.0D);
    }
}
