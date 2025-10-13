package com.naterbobber.darkerdepths.component;

import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.enchantment.ConditionalEffect;
import net.minecraft.world.item.enchantment.effects.EnchantmentValueEffect;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

import java.util.List;
import java.util.function.UnaryOperator;

public interface EnchantmentEffects {

    DataComponentType<List<ConditionalEffect<EnchantmentValueEffect>>> SWIFT_STRIKE_HIT = register("swift_strike_hit", (bulider) -> {
        return bulider.persistent(ConditionalEffect.codec(EnchantmentValueEffect.CODEC, LootContextParamSets.ENCHANTED_ENTITY).listOf());
    });

    DataComponentType<List<ConditionalEffect<EnchantmentValueEffect>>> QUICK_DASH_DURATION = register("quick_dash_duration", (bulider) -> {
        return bulider.persistent(ConditionalEffect.codec(EnchantmentValueEffect.CODEC, LootContextParamSets.ENCHANTED_ENTITY).listOf());
    });

    private static <T> DataComponentType<T> register(String name, UnaryOperator<DataComponentType.Builder<T>> operator) {
        return Registry.register(BuiltInRegistries.ENCHANTMENT_EFFECT_COMPONENT_TYPE, name, operator.apply(DataComponentType.builder()).build());
    }


}
