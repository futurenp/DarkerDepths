package com.naterbobber.darkerdepths.client.events;

import com.naterbobber.darkerdepths.client.DynamicLightHandler;
import com.naterbobber.darkerdepths.client.screen_effects.SoulBindingBlackoutHandler;
import com.naterbobber.darkerdepths.client.screen_effects.render.SoulBindingBlackoutRenderer;
import com.naterbobber.darkerdepths.client.screen_effects.render.ScorcherFlashRenderer;
import com.naterbobber.darkerdepths.client.screen_effects.ScorcherFlashHandler;
import com.naterbobber.darkerdepths.client.fog.FogManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RenderGuiLayerEvent;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;
import net.neoforged.neoforge.client.event.ViewportEvent;

@OnlyIn(Dist.CLIENT)
public class ClientEvents {

    @SubscribeEvent
    public void onClientTick(ClientTickEvent.Pre event) {
        DynamicLightHandler.tick();
        FogManager.tick();
        ScorcherFlashHandler.tick();
        SoulBindingBlackoutHandler.tick();
    }

    @SubscribeEvent
    public void onRenderLevel(RenderLevelStageEvent event) {
        var camera = event.getCamera();

        if(event.getStage() == RenderLevelStageEvent.Stage.AFTER_LEVEL) {
            ScorcherFlashRenderer.render(camera);
        }
    }

    @SubscribeEvent
    public void onRenderGuiOverlay(RenderGuiLayerEvent.Post event) {
        SoulBindingBlackoutRenderer.render(event.getGuiGraphics(), Minecraft.getInstance().getWindow());
    }

    @SubscribeEvent
    public void onComputeFogColor(ViewportEvent.ComputeFogColor event) {
        FogManager.modifyFogColor(event, player());
    }

        @SubscribeEvent
    public void modifyFog(ViewportEvent.RenderFog event) {
        FogManager.modifyFog(event, player());
    }

    @SubscribeEvent
    public void onComputeFov(ViewportEvent.ComputeFov event) {
        FogManager.modifyFov(event, player());
    }

    private static LocalPlayer player() {
        return Minecraft.getInstance().player;
    }
}
