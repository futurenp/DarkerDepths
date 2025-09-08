package com.naterbobber.darkerdepths.init;

import com.naterbobber.darkerdepths.DarkerDepths;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.schedule.Activity;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;

public class DDActivities {
    public static final DeferredRegister<Activity> ACTIVITIES =
            DeferredRegister.create(Registries.ACTIVITY, DarkerDepths.MOD_ID);

    public static final DeferredHolder<Activity, Activity> DASH = register("dash");
    public static final DeferredHolder<Activity, Activity> DORMANT = register("dormant");
    public static final DeferredHolder<Activity, Activity> AWAKENING = register("awakening");

    private static DeferredHolder<Activity, Activity> register(String name) {
        return ACTIVITIES.register(name, () -> new Activity(name));
    }
}