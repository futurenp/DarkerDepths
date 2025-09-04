package com.naterbobber.darkerdepths.entities.goals;

import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomFlyingGoal;

public class ConfigurableRandomFlyingGoal extends WaterAvoidingRandomFlyingGoal {

    public ConfigurableRandomFlyingGoal(PathfinderMob pMob, double pSpeedModifier, int interval) {
        super(pMob, pSpeedModifier);
        this.interval = interval;
    }
}
