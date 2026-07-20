package com.naterbobber.darkerdepths.block.generic;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public interface ISunlightSensitiveGlowshroomBlock {
    default void checkSunlight(ServerLevel level, BlockPos pos) {
        if(level.isNight()) return;
        var brightness = level.getBrightness(LightLayer.SKY, pos.above());

        if(brightness >= 7) {
            replaceWithDeadPileus(level, pos);
        } else {
            for(var direction : Direction.Plane.HORIZONTAL) {
                if(!(level.getBrightness(LightLayer.SKY, pos.relative(direction)) >= 8)) {
                    continue;
                }

                replaceWithDeadPileus(level, pos);
                break;
            }
        }
    }

    default void replaceWithDeadPileus(ServerLevel level, BlockPos pos) {
        for(var direction : Direction.values()) {
            var relativePos = pos.relative(direction);
            if(!level.getBlockState(relativePos).isEmpty()) continue;

            spawnParticleOnFace(level, pos, direction, ParticleTypes.CLOUD, 0.05F, 0.1F);
        }
        level.playSound(null, pos, SoundEvents.LAVA_EXTINGUISH, SoundSource.BLOCKS, 0.5F, 1.25F);
        level.setBlock(pos, getDeadGlowshroomState(level.getBlockState(pos)), Block.UPDATE_ALL);
    }

    default void spawnParticleOnFace(ServerLevel level, BlockPos pos, Direction direction, ParticleOptions particle, float speed, double spread) {
        var vec3 = Vec3.atCenterOf(pos);
        int i = direction.getStepX();
        int j = direction.getStepY();
        int k = direction.getStepZ();
        double x = vec3.x + (i == 0 ? Mth.nextDouble(level.random, -0.5F, 0.5F) : i * spread);
        double y = vec3.y + (j == 0 ? Mth.nextDouble(level.random, -0.5F, 0.5F) : j * spread);
        double z = vec3.z + (k == 0 ? Mth.nextDouble(level.random, -0.5F, 0.5F) : k * spread);
        level.sendParticles(particle, x, y, z, 2, 0, 0, 0, speed);
    }

    BlockState getDeadGlowshroomState(BlockState existingState);
}
