package com.naterbobber.darkerdepths.core.registries;

import com.naterbobber.darkerdepths.common.events.RawOreLootModifier;
import com.naterbobber.darkerdepths.core.DarkerDepths;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

//<>

@Mod.EventBusSubscriber(modid = DarkerDepths.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DDLootModifiers {
    public static final DeferredRegister<GlobalLootModifierSerializer<?>> LOOT_MODIFIERS = DeferredRegister.create(ForgeRegistries.LOOT_MODIFIER_SERIALIZERS, DarkerDepths.MODID);

    public static final RegistryObject<RawOreLootModifier.Serializer> RAW_SILVER = LOOT_MODIFIERS.register("raw_silver", RawOreLootModifier.Serializer::new);
}