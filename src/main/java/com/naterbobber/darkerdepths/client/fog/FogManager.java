package com.naterbobber.darkerdepths.client.fog;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.neoforged.neoforge.client.event.ViewportEvent;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class FogManager {

    private static final List<FogModifier> MODIFIERS = new ArrayList<>();

    public static void register(FogModifier modifier) {
        MODIFIERS.add(modifier);
        MODIFIERS.sort(Comparator.comparingInt(FogModifier::getPriority));
    }

    public static void tick() {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player == null || Minecraft.getInstance().isPaused()) return;

        for (FogModifier modifier : MODIFIERS) {
            if (modifier.isActive(player)) {
                modifier.tick(player);
            }
        }
    }

    public static void modifyFogColor(ViewportEvent.ComputeFogColor event, LocalPlayer player) {
        if(player == null) return;
        float pTick = event.getCamera().getPartialTickTime();
        for (FogModifier modifier : MODIFIERS) {
            if (modifier.isActive(player)) {
                modifier.modifyColor(event, player, pTick);
            }
        }
    }

    public static void modifyFog(ViewportEvent.RenderFog event, LocalPlayer player) {
        if(player == null) return;
        float pTick = event.getCamera().getPartialTickTime();
        for (FogModifier modifier : MODIFIERS) {
            if (modifier.isActive(player)) {
                modifier.modifyRender(event, player, pTick);
            }
        }
    }

    public static void modifyFov(ViewportEvent.ComputeFov event, LocalPlayer player) {
        if(player == null) return;
        float pTick = event.getCamera().getPartialTickTime();
        for (FogModifier modifier : MODIFIERS) {
            if (modifier.isActive(player)) {
                modifier.modifyFov(event, player, pTick);
            }
        }
    }
}