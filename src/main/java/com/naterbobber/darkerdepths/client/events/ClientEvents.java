package com.naterbobber.darkerdepths.client.events;

import com.naterbobber.darkerdepths.DarkerDepths;
import com.naterbobber.darkerdepths.client.DynamicLightHandler;
import com.naterbobber.darkerdepths.client.ScorcherCameraRenderer;
import com.naterbobber.darkerdepths.client.events.listeners.DDClientReloadListener;
import com.naterbobber.darkerdepths.client.fog.FogManager;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RegisterClientReloadListenersEvent;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;

@OnlyIn(Dist.CLIENT)
public class ClientEvents {

    @SubscribeEvent
    public void onClientTick(ClientTickEvent.Pre event) {
        DynamicLightHandler.onClientTick();
        FogManager.onClientTick();
    }

    @SubscribeEvent
    public void onRenderLevel(RenderLevelStageEvent event) {
        var camera = event.getCamera();

        if(event.getStage() == RenderLevelStageEvent.Stage.AFTER_LEVEL) {
            ScorcherCameraRenderer.render(camera);
        }
    }
}
