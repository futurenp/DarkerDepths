package com.naterbobber.darkerdepths.entities.goals;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

public class DashGoal extends Goal {

    private final Mob mob;
    private LivingEntity target;

    private final double dashSpeed;
    private final double yBoost;
    private final double triggerDistanceSquare;
    private final int dashDurationTicks;
    private final int cooldownTicks;
    private final int prepareTicks;
    private int actionTicks;
    private int currentCooldown;

    // Updated constructor
    public DashGoal(Mob mob, double dashSpeed, double yBoost, double triggerDistance, int dashDurationTicks, int cooldownTicks, int prepareTicks) {
        this.mob = mob;
        this.dashSpeed = dashSpeed;
        this.yBoost = yBoost;
        this.triggerDistanceSquare = triggerDistance * triggerDistance;
        this.dashDurationTicks = dashDurationTicks;
        this.cooldownTicks = cooldownTicks;
        this.prepareTicks = prepareTicks;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.JUMP));
    }

    @Override
    public boolean canUse() {
        if (this.currentCooldown > 0) {
            this.currentCooldown--;
            return false;
        }

        this.target = this.mob.getTarget();
        if (target == null) {
            return false;
        }

        return this.mob.distanceToSqr(this.target) > this.triggerDistanceSquare && this.mob.onGround();
    }

    @Override
    public void start() {
        this.actionTicks = this.prepareTicks + this.dashDurationTicks;

        if (this.mob instanceof IDashable dashable) {
            dashable.setPreparingToDash(true);

        }
    }

    @Override
    public void stop() {
        if (this.mob instanceof IDashable dashable) {
            dashable.setPreparingToDash(false);
            dashable.setDashing(false);
        }
        this.currentCooldown = this.cooldownTicks;
        this.mob.getNavigation().stop();
    }

    @Override
    public void tick() {
        this.actionTicks--;

        if (this.actionTicks == this.dashDurationTicks) {
            Vec3 initialVelocity = this.mob.position().vectorTo(this.target.position()).normalize().scale(this.dashSpeed);
            Vec3 finalVelocity = new Vec3(initialVelocity.x(), this.yBoost, initialVelocity.z());

            this.mob.setDeltaMovement(finalVelocity);
            this.mob.playSound(SoundEvents.PLAYER_ATTACK_SWEEP, 1.0F, 1.2F);

            if (this.mob.level() instanceof ServerLevel serverLevel) {
                serverLevel.sendParticles(ParticleTypes.POOF, this.mob.getX(), this.mob.getY(0.5), this.mob.getZ(), 15, 0.3D, 0.2D, 0.3D, 0.05D);
            }

            if (this.mob instanceof IDashable dashable) {
                dashable.setPreparingToDash(false);
                dashable.setDashing(true);
            }
        }

        if (this.target != null) {
            this.mob.getLookControl().setLookAt(this.target);
        }
    }

    @Override
    public boolean canContinueToUse() {
        return this.actionTicks > 0 && this.target != null && this.target.isAlive();
    }
}
