package com.naterbobber.darkerdepths.client.handlers;

import com.naterbobber.darkerdepths.common.items.DDSpawnEggItem;
import com.naterbobber.darkerdepths.core.DarkerDepths;
import net.minecraft.entity.EntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

//<>

@Mod.EventBusSubscriber(modid = DarkerDepths.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEventHandler {

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void postRegisterEntities(final RegistryEvent.Register<EntityType<?>> event) {
        DDSpawnEggItem.addSpawnEggs();
    }

}