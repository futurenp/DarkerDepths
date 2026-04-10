package com.naterbobber.darkerdepths.client.fog.modifiers;

import com.mojang.blaze3d.shaders.FogShape;
import com.naterbobber.darkerdepths.client.fog.FogModifier;
import com.naterbobber.darkerdepths.init.DDItems;
import com.naterbobber.darkerdepths.init.DDMobEffects;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.client.event.ViewportEvent;

public class EffectFogModifier implements FogModifier {
    private float paranoiaFactor = 0.0f;
    private static final float TRANSITION_SECONDS = 1.5f;

    @Override
    public int getPriority() { return 50; }

    @Override
    public boolean isActive(LocalPlayer player) {
        if (player.isSpectator()) return false;

        return paranoiaFactor > 0.0f
                || player.hasEffect(MobEffects.BLINDNESS)
                || player.hasEffect(DDMobEffects.PARANOIA.get());
    }

    @Override
    public void tick(LocalPlayer player) {
        float step = 1.0f / (TRANSITION_SECONDS * 20.0f);
        if (player.hasEffect(DDMobEffects.PARANOIA.get()) && !player.isInFluidType()) {
            paranoiaFactor += step;
        } else {
            paranoiaFactor -= step;
        }
        paranoiaFactor = Math.max(0.0f, Math.min(1.0f, paranoiaFactor));
    }

    @Override
    public void modifyColor(ViewportEvent.ComputeFogColor event, LocalPlayer player, float partialTick) {
        if (paranoiaFactor > 0.0f) {
            event.setRed(lerp(event.getRed(), 0.0f, paranoiaFactor));
            event.setGreen(lerp(event.getGreen(), 0.0f, paranoiaFactor));
            event.setBlue(lerp(event.getBlue(), 0.0f, paranoiaFactor));
        }
    }

    @Override
    public void modifyRender(ViewportEvent.RenderFog event, LocalPlayer player, float partialTick) {
        boolean isParanoid = paranoiaFactor > 0.0f;
        boolean isBlind = player.hasEffect(MobEffects.BLINDNESS);

        float farPlaneMultiplier = getTorchMultiplier(player);

        if (isParanoid) {
            int amplifier = player.hasEffect(DDMobEffects.PARANOIA.get()) ? player.getEffect(DDMobEffects.PARANOIA.get()).getAmplifier() : 0;
            float defaultFarPlane = Minecraft.getInstance().gameRenderer.getRenderDistance();
            float effectFarPlane = 15.0F - (amplifier * 15.0F);
            float effectNearPlane = -5.0F;

            float newFar = lerp(defaultFarPlane, effectFarPlane, paranoiaFactor);
            float newNear = lerp(defaultFarPlane, effectNearPlane, paranoiaFactor);

            event.setNearPlaneDistance(newNear);
            event.setFarPlaneDistance(newFar * farPlaneMultiplier);
            event.setFogShape(FogShape.SPHERE);
            event.setCanceled(true);
        } else if (isBlind) {
            if (farPlaneMultiplier == 2f) {
                event.setFarPlaneDistance(event.getFarPlaneDistance() * 2F);
            }
            event.setCanceled(true);
        }
    }

    @Override
    public void modifyFov(ViewportEvent.ComputeFov event, LocalPlayer player, float partialTick) {
        if (paranoiaFactor > 0.0f) {
            int amplifier = player.hasEffect(DDMobEffects.PARANOIA.get()) ? player.getEffect(DDMobEffects.PARANOIA.get()).getAmplifier() : 0;
            double zoomedFov = event.getFOV() * (0.9 - (amplifier * 0.05));
            event.setFOV(lerp(event.getFOV(), zoomedFov, paranoiaFactor));
        }
    }

    private float getTorchMultiplier(LocalPlayer player) {
        ItemStack main = player.getMainHandItem();
        ItemStack off = player.getOffhandItem();
        Item voidTorch = DDItems.VOID_SOUL_TORCH.get();

        if (main.is(voidTorch) || off.is(voidTorch)) return 2f;
        if (main.is(Items.TORCH) || off.is(Items.TORCH)) return 1.4f;
        if (main.is(Items.REDSTONE_TORCH) || off.is(Items.REDSTONE_TORCH)) return 1.25f;
        if (main.is(Items.SOUL_TORCH) || off.is(Items.SOUL_TORCH)) return 0.7f;
        return 1f;
    }

    private float lerp(float start, float end, float factor) { return start + factor * (end - start); }
    private double lerp(double start, double end, float factor) { return start + factor * (end - start); }
}