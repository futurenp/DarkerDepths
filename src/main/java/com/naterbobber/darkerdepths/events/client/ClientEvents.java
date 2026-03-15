package com.naterbobber.darkerdepths.events.client;

import com.mojang.blaze3d.shaders.FogShape;
import com.naterbobber.darkerdepths.client.DynamicLightHandler;
import com.naterbobber.darkerdepths.init.DDItems;
import com.naterbobber.darkerdepths.init.DDMobEffects;
import com.naterbobber.darkerdepths.util.DDResourceKeys;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.ViewportEvent;

public class ClientEvents {

    private static float paranoiaFactor = 0.0f;
    private static final float TRANSITION_SECONDS = 1.5f;

    @SubscribeEvent
    public void onClientTick(ClientTickEvent.Pre event) {
        DynamicLightHandler.onClientTick();

        LocalPlayer player = Minecraft.getInstance().player;
        if (player == null) return;

        float ticks = 20.0f;
        float step = 1.0f / (TRANSITION_SECONDS * ticks);

        if (player.hasEffect(DDMobEffects.PARANOIA)) {
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
        checkAndApplyParanoiaFog(event);
        checkAndApplyMoltenCavernFog(event);
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

    private void checkAndApplyMoltenCavernFog(ViewportEvent.RenderFog event) {
        LocalPlayer player = Minecraft.getInstance().player;

        if (player == null) {
            return;
        }

        var level = player.level();
        var blockPos = player.getOnPos();
        if (!level.getBiome(blockPos).is(DDResourceKeys.Biomes.MOLTEN_CAVERN)) {
            return;
        }

        var dist = event.getFarPlaneDistance();

        event.setNearPlaneDistance(32);
        event.setFarPlaneDistance(128);
        event.setFogShape(FogShape.SPHERE);
        event.setCanceled(true);
    }

    private void checkAndApplyParanoiaFog(ViewportEvent.RenderFog event) {
        LocalPlayer player = Minecraft.getInstance().player;

        if (player == null
                || player.isSpectator()
                || !(player.hasEffect(MobEffects.BLINDNESS) || player.hasEffect(DDMobEffects.PARANOIA))
        ) {
            return;
        }

        boolean isParanoid = paranoiaFactor > 0.0f;

        float farPlaneMultiplier = 1f;
        ItemStack mainHandItem = player.getMainHandItem();
        ItemStack offHandItem = player.getOffhandItem();

        Item voidSoulTorch = DDItems.VOID_SOUL_TORCH.get();
        Item torch = Items.TORCH;
        Item redstoneTorch = Items.REDSTONE_TORCH;
        Item soulTorch = Items.SOUL_TORCH;

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

            if(mainHandItem.is(voidSoulTorch) || offHandItem.is(voidSoulTorch)) {
                farPlaneMultiplier = 2f;
            } else if (mainHandItem.is(torch) || offHandItem.is(torch)) {
                farPlaneMultiplier = 1.4f;
            } else if (mainHandItem.is(redstoneTorch) || offHandItem.is(redstoneTorch)) {
                farPlaneMultiplier = 1.25f;
            } else if (mainHandItem.is(soulTorch) || offHandItem.is(soulTorch)) {
                farPlaneMultiplier = 0.7f;
            }

            event.setFarPlaneDistance(event.getFarPlaneDistance() * farPlaneMultiplier);

            event.setCanceled(true);
        }

        if (player.hasEffect(MobEffects.BLINDNESS)) {
            if(mainHandItem.is(voidSoulTorch) || offHandItem.is(voidSoulTorch)){
                event.setFarPlaneDistance(event.getFarPlaneDistance() * 2F);
            }

            event.setCanceled(true);
        }
    }

    private int getEffectAmplifier() {
        Player player = Minecraft.getInstance().player;
        if (player != null && player.hasEffect(DDMobEffects.PARANOIA)) {
            return player.getEffect(DDMobEffects.PARANOIA).getAmplifier();
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