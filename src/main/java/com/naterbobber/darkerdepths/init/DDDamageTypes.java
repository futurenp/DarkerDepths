package com.naterbobber.darkerdepths.init;

import com.naterbobber.darkerdepths.DarkerDepths;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageType;

public class DDDamageTypes {

    public static final ResourceKey<DamageType> SOUL_BINDING_DAMAGE = ResourceKey.create(Registries.DAMAGE_TYPE,
            DarkerDepths.id("soul_binding_damage"));
}