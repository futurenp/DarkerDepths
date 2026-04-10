package com.naterbobber.darkerdepths.damage;

import com.naterbobber.darkerdepths.DarkerDepths;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageScaling;
import net.minecraft.world.damagesource.DamageType;

public class DDDamageTypes {

    public static final ResourceKey<DamageType> SOUL_BINDING_DAMAGE = createKey("soul_binding_damage");

    private static ResourceKey<DamageType> createKey(String name) {
        return ResourceKey.create(Registries.DAMAGE_TYPE, DarkerDepths.id(name));
    }

    public static void bootstrap(BootstapContext<DamageType> context) {
        context.register(SOUL_BINDING_DAMAGE, new DamageType("soul_binding_damage", DamageScaling.NEVER, 0.0f));
    }
}