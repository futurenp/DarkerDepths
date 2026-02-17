package com.naterbobber.darkerdepths.init;

import com.naterbobber.darkerdepths.DarkerDepths;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class DDActivities {
    public static final DeferredRegister<Activity> ACTIVITIES =
            DeferredRegister.create(Registries.ACTIVITY, DarkerDepths.MOD_ID);

    public static final RegistryObject<Activity> DASH = register("dash");
    public static final RegistryObject<Activity> DORMANT = register("dormant");
    public static final RegistryObject<Activity> AWAKENING = register("awakening");

    private static RegistryObject<Activity> register(String name) {
        return ACTIVITIES.register(name, () -> new Activity(name));
    }
}