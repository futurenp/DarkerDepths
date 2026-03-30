package com.naterbobber.darkerdepths.client.fog.modifiers;

import com.naterbobber.darkerdepths.client.fog.FogModifier;
import net.minecraft.client.player.LocalPlayer;
import net.neoforged.neoforge.client.event.ViewportEvent;

public class ScorcherFlashModifier implements FogModifier {
    private static int flashTicksRemaining = 0;
    private static int maxFlashTicks = 0;

    public static void triggerFlash(int ticks) {
        flashTicksRemaining = Math.max(flashTicksRemaining, ticks);
        maxFlashTicks = flashTicksRemaining;
    }

    public static boolean isFlashed() {
        return flashTicksRemaining > 0;
    }

    public static int getTicksRemaining() { return flashTicksRemaining; }
    public static int getMaxTicks() { return maxFlashTicks; }

    @Override
    public int getPriority() { return 100; }

    @Override
    public boolean isActive(LocalPlayer player) {
        return flashTicksRemaining > 0;
    }

    @Override
    public void tick(LocalPlayer player) {
        if (flashTicksRemaining > 0) flashTicksRemaining--;
    }

    @Override
    public void modifyColor(ViewportEvent.ComputeFogColor event, LocalPlayer player, float partialTick) {
        event.setRed(0.0f);
        event.setGreen(0.0f);
        event.setBlue(0.0f);
    }

    @Override
    public void modifyRender(ViewportEvent.RenderFog event, LocalPlayer player, float partialTick) {
        event.setNearPlaneDistance(0.0f);
        event.setFarPlaneDistance(3.0f);
        event.setCanceled(true);
    }
}