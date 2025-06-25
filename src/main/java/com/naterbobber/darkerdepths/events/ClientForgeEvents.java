package com.naterbobber.darkerdepths.events;

import com.mojang.blaze3d.shaders.FogShape;
import com.naterbobber.darkerdepths.init.DDMobEffects;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.event.ViewportEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ClientForgeEvents {

    private static float paranoiaFactor = 0.0f;
    private static final float TRANSITION_SECONDS = 1.5f;

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END) {
            return;
        }

        LocalPlayer player = Minecraft.getInstance().player;
        if (player == null) return;

        float step = 1.0f / (TRANSITION_SECONDS * 20.0f);

        if (player.hasEffect(DDMobEffects.PARANOIA.get())) {
            if (paranoiaFactor < 1.0f) {
                paranoiaFactor = Math.min(1.0f, paranoiaFactor + step);
            }
        } else {
            if (paranoiaFactor > 0.0f) {
                paranoiaFactor = Math.max(0.0f, paranoiaFactor - step);
            }
        }
    }

    @SubscribeEvent
    public void onFogColor(ViewportEvent.ComputeFogColor event) {
        if (paranoiaFactor > 0.0f) {
            float normalRed = event.getRed();
            float normalGreen = event.getGreen();
            float normalBlue = event.getBlue();

            event.setRed(lerp(normalRed, 0.0f, paranoiaFactor));
            event.setGreen(lerp(normalGreen, 0.0f, paranoiaFactor));
            event.setBlue(lerp(normalBlue, 0.0f, paranoiaFactor));
        }
    }

    @SubscribeEvent
    public void onRenderFog(ViewportEvent.RenderFog event) {
        if (paranoiaFactor > 0.0f) {
            float defaultFarPlane = Minecraft.getInstance().gameRenderer.getRenderDistance();

            int amplifier = getEffectAmplifier();
            float effectFarPlane = 15.0F - (amplifier * 15.0F);
            float effectNearPlane = -5.0F;

            float newFar = lerp(defaultFarPlane, effectFarPlane, paranoiaFactor);
            float newNear = lerp(defaultFarPlane, effectNearPlane, paranoiaFactor);

            event.setNearPlaneDistance(newNear);
            event.setFarPlaneDistance(newFar);
            event.setFogShape(FogShape.SPHERE);
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void onComputeFov(ViewportEvent.ComputeFov event) {
        if (paranoiaFactor > 0.0f) {
            double defaultFov = event.getFOV();

            int amplifier = getEffectAmplifier();
            double zoomedFov = defaultFov * (0.9 - (amplifier * 0.05));

            event.setFOV(lerp(defaultFov, zoomedFov, paranoiaFactor));
        }
    }

    private int getEffectAmplifier() {
        Player player = Minecraft.getInstance().player;
        if (player != null && player.hasEffect(DDMobEffects.PARANOIA.get())) {
            return player.getEffect(DDMobEffects.PARANOIA.get()).getAmplifier();
        }
        return 0;
    }

    private float lerp(float start, float end, float factor) {
        return start + factor * (end - start);
    }

    private double lerp(double start, double end, double factor) {
        return start + factor * (end - start);
    }
}