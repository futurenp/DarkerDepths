package com.naterbobber.darkerdepths.effects;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3; // Import Vec3

import java.util.List;

public class ParanoiaEffect extends MobEffect {

    private static final List<SoundEvent> PARANOIA_SOUNDS = List.of(
            SoundEvents.STONE_STEP,
            SoundEvents.WOOD_STEP
    );

    public ParanoiaEffect() {
        super(MobEffectCategory.HARMFUL, 0x1a1a1a);
    }

    //random sound generator
    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        Level level = entity.level();

        if (!level.isClientSide()) {
            if (entity.getRandom().nextFloat() < 0.007f) {

                RandomSource random = entity.getRandom();
                for (int i = 0; i < 10; i++) {
                    double minRadius = 4.0;
                    double maxRadius = 16.0;
                    double radius = minRadius + random.nextDouble() * (maxRadius - minRadius);
                    double angle = random.nextDouble() * 2.0 * Math.PI;

                    double xOffset = Math.cos(angle) * radius;
                    double zOffset = Math.sin(angle) * radius;

                    Vec3 lookVector = entity.getLookAngle();
                    Vec3 lookDirection = new Vec3(lookVector.x, 0, lookVector.z).normalize();
                    Vec3 soundDirection = new Vec3(xOffset, 0, zOffset).normalize();
                    double dotProduct = lookDirection.dot(soundDirection);
                    if (dotProduct <= 0) {
                        SoundEvent randomSound = PARANOIA_SOUNDS.get(random.nextInt(PARANOIA_SOUNDS.size()));
                        double yOffset = -1.0 + random.nextDouble() * 2.0;

                        double soundX = entity.getX() + xOffset;
                        double soundY = entity.getY() + entity.getEyeHeight() + yOffset;
                        double soundZ = entity.getZ() + zOffset;

                        level.playSound(null, soundX, soundY, soundZ, randomSound, SoundSource.AMBIENT, 1.0F, 0.8F + random.nextFloat() * 0.4F);
                        break;
                    }
                }
            }
        }

        super.applyEffectTick(entity, amplifier);
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }
}