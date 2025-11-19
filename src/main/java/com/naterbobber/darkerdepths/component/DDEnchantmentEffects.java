package com.naterbobber.darkerdepths.component;

import com.naterbobber.darkerdepths.DarkerDepths;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.enchantment.ConditionalEffect;
import net.minecraft.world.item.enchantment.effects.EnchantmentValueEffect;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.List;
import java.util.function.Supplier;

public class DDEnchantmentEffects {
    public static final DeferredRegister<DataComponentType<?>> DATA_COMPONENTS = DeferredRegister.create(Registries.ENCHANTMENT_EFFECT_COMPONENT_TYPE, DarkerDepths.MOD_ID);

    public static final Supplier<DataComponentType<List<ConditionalEffect<EnchantmentValueEffect>>>> SWIFT_STRIKE_HIT = DATA_COMPONENTS.register("swift_strike_hit", () -> {
        return DataComponentType.<List<ConditionalEffect<EnchantmentValueEffect>>>builder().persistent(ConditionalEffect.codec(EnchantmentValueEffect.CODEC, LootContextParamSets.ENCHANTED_ENTITY).listOf()).build();
    });

    public static final Supplier<DataComponentType<List<ConditionalEffect<EnchantmentValueEffect>>>> QUICK_DASH_DURATION = DATA_COMPONENTS.register("quick_dash_duration", () -> {
        return DataComponentType.<List<ConditionalEffect<EnchantmentValueEffect>>>builder().persistent(ConditionalEffect.codec(EnchantmentValueEffect.CODEC, LootContextParamSets.ENCHANTED_ENTITY).listOf()).build();
    });

}
