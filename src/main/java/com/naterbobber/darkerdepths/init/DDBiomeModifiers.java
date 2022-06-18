package com.naterbobber.darkerdepths.init;

import com.mojang.serialization.Codec;
import com.naterbobber.darkerdepths.DarkerDepths;
import com.naterbobber.darkerdepths.world.DarkerDepthsBiomeModifier;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = DarkerDepths.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DDBiomeModifiers {

    public static final DeferredRegister<Codec<? extends BiomeModifier>> BIOME_MODIFIERS = DeferredRegister.create(ForgeRegistries.Keys.BIOME_MODIFIER_SERIALIZERS, DarkerDepths.MODID);

    public static final RegistryObject<Codec<? extends BiomeModifier>> DD_BIOME_MODIFIER = BIOME_MODIFIERS.register("dd_biome_modifier", () -> Codec.unit(DarkerDepthsBiomeModifier::new));

}
