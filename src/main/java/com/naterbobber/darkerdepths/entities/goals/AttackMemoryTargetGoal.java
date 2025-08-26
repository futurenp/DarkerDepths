package com.naterbobber.darkerdepths.entities.goals;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import org.jetbrains.annotations.Nullable;

import java.util.function.Predicate;

/**
 * @param <T> The type of LivingEntity to target.
 */
public class AttackMemoryTargetGoal<T extends LivingEntity> extends NearestAttackableTargetGoal<T> {

    private final int memoryTicks;

    /**
     * Creates a target goal with a custom memory duration.
     * @param mob The mob that will use this goal.
     * @param targetType The class of the entity to target.
     * @param memoryTicks The duration in ticks (20 ticks = 1 second) the mob should remember its target without seeing it.
     * @param mustSee Whether the mob must have line of sight to acquire the target initially.
     * @param mustReach Whether the mob must be able to reach the target.
     * @param predicate An additional condition to filter potential targets.
     */
    public AttackMemoryTargetGoal(Mob mob, Class<T> targetType, int memoryTicks, boolean mustSee, boolean mustReach, @Nullable Predicate<LivingEntity> predicate) {
        super(mob, targetType, 10, mustSee, mustReach, predicate);
        this.memoryTicks = memoryTicks;
    }

    /**
     * A simpler constructor for common use cases.
     * @param mob The mob that will use this goal.
     * @param targetType The class of the entity to target.
     * @param memoryTicks The duration in ticks the mob should remember its target.
     * @param mustSee Whether the mob must have line of sight to acquire the target.
     */
    public AttackMemoryTargetGoal(Mob mob, Class<T> targetType, int memoryTicks, boolean mustSee) {
        this(mob, targetType, memoryTicks, mustSee, false, null);
    }

    @Override
    public void start() {
        super.start();
        this.setUnseenMemoryTicks(this.memoryTicks);
    }
}