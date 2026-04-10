package com.naterbobber.darkerdepths.client.fog;

import net.minecraft.client.player.LocalPlayer;
import net.minecraftforge.client.event.ViewportEvent;

public interface FogModifier {
    /** Higher numbers run LAST, allowing them to override lower priority modifiers. */
    int getPriority();

    boolean isActive(LocalPlayer player);

    default void tick(LocalPlayer player) {}

    default void modifyColor(ViewportEvent.ComputeFogColor event, LocalPlayer player, float partialTick) {}

    default void modifyRender(ViewportEvent.RenderFog event, LocalPlayer player, float partialTick) {}

    default void modifyFov(ViewportEvent.ComputeFov event, LocalPlayer player, float partialTick) {}
}