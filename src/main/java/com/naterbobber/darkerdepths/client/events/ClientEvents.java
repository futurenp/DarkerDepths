package com.naterbobber.darkerdepths.client.events;

import com.naterbobber.darkerdepths.client.DynamicLightHandler;
import com.naterbobber.darkerdepths.client.events.listeners.DDClientReloadListener;
import com.naterbobber.darkerdepths.client.fog.FogManager;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RegisterClientReloadListenersEvent;

public class ClientEvents {

    @SubscribeEvent
    public void onClientTick(ClientTickEvent.Pre event) {
        DynamicLightHandler.onClientTick();
        FogManager.onClientTick();
    }
}
