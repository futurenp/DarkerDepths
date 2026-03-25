package com.naterbobber.darkerdepths.events.client;

import com.mojang.blaze3d.shaders.FogShape;
import com.naterbobber.darkerdepths.client.DynamicLightHandler;
import com.naterbobber.darkerdepths.config.DDConfig;
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

    private static float moltenCavernWeight = 0.0f;
    private static float glowshroomWeight = 0.0f;
    private static float catacombsWeight = 0.0f;
    private static final float BIOME_TRANSITION_SECONDS = 2.0f;
    private boolean useBiomeFog = true;

    @SubscribeEvent
    public void onClientTick(ClientTickEvent.Pre event) {
        DynamicLightHandler.onClientTick();

        LocalPlayer player = Minecraft.getInstance().player;
        if (player == null) return;

        float ticks = 20.0f;
        float step = 1.0f / (TRANSITION_SECONDS * ticks);

        if (player.hasEffect(DDMobEffects.PARANOIA) && !player.isInFluidType()) {
            paranoiaFactor += step;
        } else {
            paranoiaFactor -= step;
        }
        paranoiaFactor = Math.max(0.0f, Math.min(1.0f, paranoiaFactor));

        if (DDConfig.CONFIG.ENABLE_BIOME_FOG.get()) {
            if(player.isInFluidType()) {
                var fluidType = player.getEyeInFluidType();
                if(!fluidType.isAir()) {
                    useBiomeFog = false;
                    return;
                }
            }
            useBiomeFog = true;

            float biomeStep = 1.0f / (BIOME_TRANSITION_SECONDS * ticks);
            var currentBiome = player.level().getBiome(player.getOnPos());

            boolean inMolten = currentBiome.is(DDResourceKeys.Biomes.MOLTEN_CAVERN);
            boolean inGlow = currentBiome.is(DDResourceKeys.Biomes.GLOWSHROOM_FOREST);
            boolean inSand = currentBiome.is(DDResourceKeys.Biomes.SANDY_CATACOMBS);

            moltenCavernWeight = Math.max(0.0f, Math.min(1.0f, moltenCavernWeight + (inMolten ? biomeStep : -biomeStep)));
            glowshroomWeight = Math.max(0.0f, Math.min(1.0f, glowshroomWeight + (inGlow ? biomeStep : -biomeStep)));
            catacombsWeight = Math.max(0.0f, Math.min(1.0f, catacombsWeight + (inSand ? biomeStep : -biomeStep)));
        } else {
            resetFog();
        }
    }

    private void resetFog(){
        moltenCavernWeight = 0.0f;
        glowshroomWeight = 0.0f;
        catacombsWeight = 0.0f;
    }

    @SubscribeEvent
    public void onFogColor(ViewportEvent.ComputeFogColor event) {
        float red = event.getRed();
        float green = event.getGreen();
        float blue = event.getBlue();

        if (DDConfig.CONFIG.ENABLE_BIOME_FOG.get() && useBiomeFog) {
            float totalWeight = moltenCavernWeight + glowshroomWeight + catacombsWeight;
            if (totalWeight > 0.0f) {
                float invWeight = Math.max(0.0f, 1.0f - Math.min(1.0f, totalWeight));

                float r = red * invWeight + 0.42f * moltenCavernWeight + 0.16f * glowshroomWeight + 0.22f * catacombsWeight;
                float g = green * invWeight + 0.27f * moltenCavernWeight + 0.34f * glowshroomWeight + 0.13f * catacombsWeight;
                float b = blue * invWeight + 0.18f * moltenCavernWeight + 0.24f * glowshroomWeight + 0.10f * catacombsWeight;

                float scale = 1.0f / (invWeight + totalWeight);

                red = r * scale;
                green = g * scale;
                blue = b * scale;
            }
        }

        if (paranoiaFactor > 0.0f) {
            red = lerp(red, 0.0f, paranoiaFactor);
            green = lerp(green, 0.0f, paranoiaFactor);
            blue = lerp(blue, 0.0f, paranoiaFactor);
        }

        event.setRed(red);
        event.setGreen(green);
        event.setBlue(blue);
    }

    @SubscribeEvent
    public void onRenderFog(ViewportEvent.RenderFog event) {
        if (DDConfig.CONFIG.ENABLE_BIOME_FOG.get() && useBiomeFog) {
            float totalWeight = moltenCavernWeight + glowshroomWeight + catacombsWeight;
            if (totalWeight > 0.0f) {
                float invWeight = Math.max(0.0f, 1.0f - Math.min(1.0f, totalWeight));

                float near = invWeight
                        + DDConfig.CONFIG.MOLTEN_CAVERN_FOG_MIN.get() * moltenCavernWeight
                        + DDConfig.CONFIG.GLOWSHROOM_FOREST_FOG_MIN.get() * glowshroomWeight
                        + DDConfig.CONFIG.SANDY_CATACOMBS_FOG_MIN.get() * catacombsWeight;

                float far = invWeight
                        + DDConfig.CONFIG.MOLTEN_CAVERN_FOG_MAX.get() * moltenCavernWeight
                        + DDConfig.CONFIG.GLOWSHROOM_FOREST_FOG_MAX.get() * glowshroomWeight
                        + DDConfig.CONFIG.SANDY_CATACOMBS_FOG_MAX.get() * catacombsWeight;

                event.setNearPlaneDistance(near);
                event.setFarPlaneDistance(far);
                event.setFogShape(FogShape.SPHERE);
                event.setCanceled(true);
            }
        }

        checkAndApplyParanoiaFog(event);
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