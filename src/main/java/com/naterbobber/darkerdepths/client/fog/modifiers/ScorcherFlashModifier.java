package com.naterbobber.darkerdepths.client.fog.modifiers;

import com.naterbobber.darkerdepths.client.screen_effects.ScorcherFlashHandler;
import com.naterbobber.darkerdepths.client.fog.FogModifier;
import net.minecraft.client.player.LocalPlayer;
import net.neoforged.neoforge.client.event.ViewportEvent;

public class ScorcherFlashModifier implements FogModifier {
    @Override
    public int getPriority() { return 100; }

    @Override
    public boolean isActive(LocalPlayer player) {
        return ScorcherFlashHandler.isFlashed();
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