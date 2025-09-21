package com.naterbobber.darkerdepths.effects;

import com.google.common.collect.Maps;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ParanoiaEffect extends MobEffect {
    private final Map<UUID, Integer> PARANOIA_COOLDOWNS = Maps.newHashMap();

    private final double SOUND_CHANCE = 0.0015f;

    private static final List<SoundEvent> PARANOIA_SOUNDS = List.of(
            SoundEvents.STONE_STEP,
            SoundEvents.WOOD_STEP
    );

    public ParanoiaEffect() {
        super(MobEffectCategory.HARMFUL, 0x1a1a1a);
    }

    @Override
    public boolean applyEffectTick(LivingEntity entity, int amplifier) {
        int currentCooldown = this.PARANOIA_COOLDOWNS.getOrDefault(entity.getUUID(), 0);

        super.applyEffectTick(entity, amplifier);

        if (currentCooldown > 0) {
            this.PARANOIA_COOLDOWNS.put(entity.getUUID(), currentCooldown - 1);
            return false;
        }

        RandomSource random = entity.getRandom();
        if (random.nextDouble() > SOUND_CHANCE || entity.level().isClientSide()) return false;

        double minRadius = 4.0, maxRadius = 16.0;
        double radius = minRadius + random.nextDouble() * (maxRadius - minRadius);
        double angle = random.nextDouble() * 2.0 * Math.PI;

        double xOffset = Math.cos(angle) * radius;
        double zOffset = Math.sin(angle) * radius;

        Vec3 lookVector = entity.getLookAngle();
        Vec3 lookDirection = new Vec3(lookVector.x, 0, lookVector.z).normalize();
        Vec3 soundDirection = new Vec3(xOffset, 0, zOffset).normalize();
        double dotProduct = lookDirection.dot(soundDirection);

        if (dotProduct > 0) {
            return false;
        }

        SoundEvent randomSound = PARANOIA_SOUNDS.get(random.nextInt(PARANOIA_SOUNDS.size()));
        double yOffset = -1.0 + random.nextDouble() * 2.0;

        double soundX = entity.getX() + xOffset;
        double soundY = entity.getY() + entity.getEyeHeight() + yOffset;
        double soundZ = entity.getZ() + zOffset;

        entity.level().playSound(null, soundX, soundY, soundZ, randomSound, SoundSource.AMBIENT, 1.0F, 0.8F + random.nextFloat() * 0.4F);

        this.PARANOIA_COOLDOWNS.put(entity.getUUID(), currentCooldown + 360 - (amplifier * 60));

        return true;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return true;
    }
}