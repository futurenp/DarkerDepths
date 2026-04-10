package com.naterbobber.darkerdepths.client.events;

import com.naterbobber.darkerdepths.client.DynamicLightHandler;
import com.naterbobber.darkerdepths.client.fog.FogManager;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ClientEvents {

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if(event.phase != TickEvent.Phase.END) {
            return;
        }

        DynamicLightHandler.onClientTick();
        FogManager.onClientTick();
    }
}