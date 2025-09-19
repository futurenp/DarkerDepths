package com.naterbobber.darkerdepths.events;

import com.mojang.blaze3d.shaders.FogShape;
import com.naterbobber.darkerdepths.client.DynamicLightHandler;
import com.naterbobber.darkerdepths.init.DDItems;
import com.naterbobber.darkerdepths.init.DDMobEffects;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.ViewportEvent;

public class ClientForgeEvents {

    private static float paranoiaFactor = 0.0f;
    private static final float TRANSITION_SECONDS = 1.5f;

    @SubscribeEvent
    public void onClientTick(ClientTickEvent event) {
        DynamicLightHandler.onClientTick(event);

        if (event.phase != TickEvent.Phase.END) {
            return;
        }

        LocalPlayer player = Minecraft.getInstance().player;
        if (player == null) return;

        float ticks = 20.0f;
        float step = 1.0f / (TRANSITION_SECONDS * ticks);

        if (player.hasEffect(DDMobEffects.PARANOIA.get())) {
            paranoiaFactor += step;
        } else {
            paranoiaFactor -= step;
        }

        paranoiaFactor = Math.max(0.0f, Math.min(1.0f, paranoiaFactor));
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
        LocalPlayer player = Minecraft.getInstance().player;

        if (player == null || player.isSpectator()) {
            return;
        }

        boolean isParanoid = paranoiaFactor > 0.0f;

        if (isParanoid) {
            int amplifier = getEffectAmplifier();
            float defaultFarPlane = Minecraft.getInstance().gameRenderer.getRenderDistance();
            float effectFarPlane = 15.0F - (amplifier * 15.0F);
            float effectNearPlane = -5.0F;

            float newFar = lerp(defaultFarPlane, effectFarPlane, paranoiaFactor);
            float newNear = lerp(defaultFarPlane, effectNearPlane, paranoiaFactor);

            event.setNearPlaneDistance(newNear);
            event.setFarPlaneDistance(newFar);
            event.setFogShape(FogShape.SPHERE);
        }

        float farPlaneMultiplier = 1f;
        ItemStack mainHandItem = player.getMainHandItem();
        ItemStack offHandItem = player.getOffhandItem();

        Item voidSoulTorch = DDItems.VOID_SOUL_TORCH.get();
        Item torch = Items.TORCH;
        Item redstoneTorch = Items.REDSTONE_TORCH;
        Item soulTorch = Items.SOUL_TORCH;

        if(isParanoid) {
            if(mainHandItem.is(voidSoulTorch) || offHandItem.is(voidSoulTorch)) {
                farPlaneMultiplier = 2f;
            } else if (mainHandItem.is(torch) || offHandItem.is(torch)) {
                farPlaneMultiplier = 1.4f;
            } else if (mainHandItem.is(redstoneTorch) || offHandItem.is(redstoneTorch)) {
                farPlaneMultiplier = 1.25f;
            } else if (mainHandItem.is(soulTorch) || offHandItem.is(soulTorch)) {
                farPlaneMultiplier = 0.7f;
            }
        }

        if(isParanoid){
            event.setFarPlaneDistance(event.getFarPlaneDistance() * farPlaneMultiplier);
        }

        if (player.hasEffect(MobEffects.BLINDNESS)) {
            if(mainHandItem.is(voidSoulTorch) || offHandItem.is(voidSoulTorch)){
                event.setFarPlaneDistance(event.getFarPlaneDistance() * 2F);
            }
        }

        event.setCanceled(true);
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