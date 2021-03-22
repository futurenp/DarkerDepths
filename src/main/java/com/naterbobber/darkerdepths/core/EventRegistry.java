package com.naterbobber.darkerdepths.core;

import com.naterbobber.darkerdepths.core.registries.DDStructures;
import net.minecraft.world.gen.feature.Feature;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class EventRegistry {
    @SubscribeEvent
    public static void registerFeatures(final RegistryEvent.Register<Feature<?>> event) {
        DDStructures.registerFeatures(event);
    }
}