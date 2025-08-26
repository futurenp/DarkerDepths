package com.naterbobber.darkerdepths.entities.goals;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;

public class ConfigurableReachMeleeAttackGoal extends MeleeAttackGoal {
    private final float attackRadius;

    public ConfigurableReachMeleeAttackGoal(PathfinderMob pMob, double pSpeedModifier, boolean pFollowingTargetEvenIfNotSeen, float pAttackRadius) {
        super(pMob, pSpeedModifier, pFollowingTargetEvenIfNotSeen);
        this.attackRadius = pAttackRadius;
    }

    @Override
    protected double getAttackReachSqr(LivingEntity pAttackTarget) {
        return this.attackRadius * this.attackRadius;
    }
}