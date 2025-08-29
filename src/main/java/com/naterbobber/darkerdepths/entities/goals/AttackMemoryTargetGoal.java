package com.naterbobber.darkerdepths.entities.goals;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import org.jetbrains.annotations.Nullable;

import java.util.function.Predicate;

public class AttackMemoryTargetGoal<T extends LivingEntity> extends NearestAttackableTargetGoal<T> {

    private final int memoryTicks;

    public AttackMemoryTargetGoal(Mob mob, Class<T> targetType, int memoryTicks, boolean mustSee, boolean mustReach, @Nullable Predicate<LivingEntity> predicate) {
        super(mob, targetType, 10, mustSee, mustReach, predicate);
        this.memoryTicks = memoryTicks;
    }

    public AttackMemoryTargetGoal(Mob mob, Class<T> targetType, int memoryTicks, boolean mustSee) {
        this(mob, targetType, memoryTicks, mustSee, false, null);
    }

    @Override
    public void start() {
        super.start();
        this.setUnseenMemoryTicks(this.memoryTicks);
    }
}