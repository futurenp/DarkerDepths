package com.naterbobber.darkerdepths.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class ScorchedRemainsBushBlock extends BushBlock {
    public static final MapCodec<ScorchedRemainsBushBlock> CODEC = simpleCodec(ScorchedRemainsBushBlock::new);
    protected static final VoxelShape SHAPE = Block.box((double)2.0F, (double)0.0F, (double)2.0F, (double)14.0F, (double)10.0F, (double)14.0F);

    public ScorchedRemainsBushBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected MapCodec<? extends BushBlock> codec() {
        return CODEC;
    }

    @Override
    protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos) {
        return state.isSolid();
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    protected void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        super.entityInside(state, level, pos, entity);

        if(level.isClientSide) {
            var vec3 = entity.getDeltaMovement();
            var speedMultiplier = 0.5;
            var xSpeed = vec3.x * speedMultiplier;
            var ySpeed = vec3.y * speedMultiplier;
            var zSpeed = vec3.z * speedMultiplier;
            var chance = 0.85;
            if(xSpeed == 0 || zSpeed == 0) {
                chance = 1;
            }
            if(level.getRandom().nextFloat() > chance) {
                level.addParticle(
                        ParticleTypes.LARGE_SMOKE,
                        pos.getX() + 0.5,
                        pos.getY(),
                        pos.getZ() + 0.5,
                        xSpeed,
                        0.02 + ySpeed,
                        zSpeed
                );
            }
        }
    }
}
