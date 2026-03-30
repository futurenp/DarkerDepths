package com.naterbobber.darkerdepths.client.fog;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ViewportEvent;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@EventBusSubscriber(modid = "darkerdepths", value = Dist.CLIENT)
public class FogManager {

    private static final List<FogModifier> MODIFIERS = new ArrayList<>();

    public static void register(FogModifier modifier) {
        MODIFIERS.add(modifier);
        MODIFIERS.sort(Comparator.comparingInt(FogModifier::getPriority));
    }

    public static void onClientTick() {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player == null || Minecraft.getInstance().isPaused()) return;

        for (FogModifier modifier : MODIFIERS) {
            if (modifier.isActive(player)) {
                modifier.tick(player);
            }
        }
    }

    @SubscribeEvent
    public static void onComputeFogColor(ViewportEvent.ComputeFogColor event) {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player == null) return;

        float pTick = (float) event.getCamera().getPartialTickTime();
        for (FogModifier modifier : MODIFIERS) {
            if (modifier.isActive(player)) {
                modifier.modifyColor(event, player, pTick);
            }
        }
    }

    @SubscribeEvent
    public static void onRenderFog(ViewportEvent.RenderFog event) {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player == null) return;

        float pTick = (float) event.getCamera().getPartialTickTime();
        for (FogModifier modifier : MODIFIERS) {
            if (modifier.isActive(player)) {
                modifier.modifyRender(event, player, pTick);
            }
        }
    }

    @SubscribeEvent
    public static void onComputeFov(ViewportEvent.ComputeFov event) {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player == null) return;

        float pTick = (float) event.getCamera().getPartialTickTime();
        for (FogModifier modifier : MODIFIERS) {
            if (modifier.isActive(player)) {
                modifier.modifyFov(event, player, pTick);
            }
        }
    }
}