package com.naterbobber.darkerdepths.block.custom;

import com.naterbobber.darkerdepths.DarkerDepths;
import com.naterbobber.darkerdepths.init.DDBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class ScorchedRemainsFullBlock extends Block {

    public ScorchedRemainsFullBlock(Properties properties) {
        super(properties);
    }


    @Override
    public void stepOn(Level level, BlockPos pos, BlockState state, Entity entity) {
        super.stepOn(level, pos, state, entity);

        if(level.getBlockState(pos.above()).is(DDBlocks.SCORCHED_REMAINS)) {
            return;
        }

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
                        pos.getY() + 1,
                        pos.getZ() + 0.5,
                        xSpeed,
                        0.02 + ySpeed,
                        zSpeed
                );
            }
        }
    }
}
