package com.naterbobber.darkerdepths.init;

import com.naterbobber.darkerdepths.DarkerDepths;
import com.naterbobber.darkerdepths.effects.ParanoiaEffect;
import com.naterbobber.darkerdepths.effects.SoulBindingEffect;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class DDMobEffects {

    public static final DeferredRegister<MobEffect> MOB_EFFECTS = DeferredRegister.create(BuiltInRegistries.MOB_EFFECT, DarkerDepths.MOD_ID);

    public static final DeferredHolder<MobEffect, MobEffect> SOUL_BINDING = MOB_EFFECTS.register("soul_binding",
            () -> new SoulBindingEffect(MobEffectCategory.NEUTRAL, 16185078));
    public static final DeferredHolder<MobEffect, ParanoiaEffect> PARANOIA = MOB_EFFECTS.register("paranoia", () -> new
            ParanoiaEffect(MobEffectCategory.HARMFUL, 2039587));

}
