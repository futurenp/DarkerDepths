package com.naterbobber.darkerdepths.init;

import com.naterbobber.darkerdepths.DarkerDepths;
import net.minecraft.core.registries.Registries;
import net.minecraft.util.Unit;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.Optional;

public class DDMemoryModuleTypes {
    public static final DeferredRegister<MemoryModuleType<?>> MEMORY_MODULE_TYPES =
            DeferredRegister.create(Registries.MEMORY_MODULE_TYPE, DarkerDepths.MODID);

    public static final RegistryObject<MemoryModuleType<Unit>> DASH_COOLDOWN = register("dash_cooldown");
    public static final RegistryObject<MemoryModuleType<Integer>> ATTACK_ANIMATION_COOLDOWN = register("attack_animation_cooldown");
    public static final RegistryObject<MemoryModuleType<Unit>> FIRST_HIT_DONE = register("first_hit_done");


    private static <T> RegistryObject<MemoryModuleType<T>> register(String name) {
        return MEMORY_MODULE_TYPES.register(name, () -> new MemoryModuleType<>(Optional.empty()));
    }
}