package com.naterbobber.darkerdepths.client.fog;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ViewportEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Mod.EventBusSubscriber(modid = "darkerdepths", value = Dist.CLIENT)
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

        float pTick = (float) event.getPartialTick();
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

        float pTick = (float) event.getPartialTick();
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

        float pTick = (float) event.getPartialTick();
        for (FogModifier modifier : MODIFIERS) {
            if (modifier.isActive(player)) {
                modifier.modifyFov(event, player, pTick);
            }
        }
    }
}