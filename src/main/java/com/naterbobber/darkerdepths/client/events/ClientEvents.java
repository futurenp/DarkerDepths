package com.naterbobber.darkerdepths.client.events;

import com.naterbobber.darkerdepths.client.DynamicLightHandler;
import com.naterbobber.darkerdepths.client.FogHandler;
import com.naterbobber.darkerdepths.config.DDConfig;
import com.naterbobber.darkerdepths.init.DDMobEffects;
import com.naterbobber.darkerdepths.util.DDResourceKeys;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent;

public class ClientEvents {

    @SubscribeEvent
    public void onClientTick(ClientTickEvent.Pre event) {
        DynamicLightHandler.onClientTick();
        FogHandler.onClientTick();
    }
}
