package com.naterbobber.darkerdepths.client;

import com.mojang.blaze3d.shaders.FogShape;
import com.naterbobber.darkerdepths.config.DDConfig;
import com.naterbobber.darkerdepths.init.DDItems;
import com.naterbobber.darkerdepths.init.DDMobEffects;
import com.naterbobber.darkerdepths.util.DDResourceKeys;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.biome.Biome;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.ViewportEvent;
import software.bernie.geckolib.util.Color;

import java.util.List;

public class FogHandler {
    private static float paranoiaFactor = 0.0f;
    private static final float TRANSITION_SECONDS = 1.5f;
    private static final float BIOME_TRANSITION_SECONDS = 2.0f;
    private static boolean useBiomeFog = true;
    private static final BiomeFog MOLTEN_CAVERN = new BiomeFog(
            DDResourceKeys.Biomes.MOLTEN_CAVERN,
            Color.ofRGB(0.42F, 0.27F, 0.18F),
            DDConfig.CONFIG.MOLTEN_CAVERN_FOG_MIN.get(),
            DDConfig.CONFIG.MOLTEN_CAVERN_FOG_MAX.get()
    );
    private static final BiomeFog SANDY_CATACOMBS = new BiomeFog(
            DDResourceKeys.Biomes.MOLTEN_CAVERN,
            Color.ofRGB(0.22F, 0.13F, 0.10F),
            DDConfig.CONFIG.MOLTEN_CAVERN_FOG_MIN.get(),
            DDConfig.CONFIG.MOLTEN_CAVERN_FOG_MAX.get()
    );
    private static final BiomeFog GLOWSHROOM_CAVERN = new BiomeFog(
            DDResourceKeys.Biomes.MOLTEN_CAVERN,
            Color.ofRGB(0.16F, 0.34F, 0.24F),
            DDConfig.CONFIG.MOLTEN_CAVERN_FOG_MIN.get(),
            DDConfig.CONFIG.MOLTEN_CAVERN_FOG_MAX.get()
    );

    private static final List<BiomeFog> BIOME_FOG_LIST = List.of(
            MOLTEN_CAVERN,
            SANDY_CATACOMBS,
            GLOWSHROOM_CAVERN
    );

    public static void onClientTick() {
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

            if(player.hasEffect(MobEffects.BLINDNESS) || player.hasEffect(DDMobEffects.PARANOIA)) {
                useBiomeFog = false;
                return;
            }

            useBiomeFog = true;

            float biomeStep = 1.0f / (BIOME_TRANSITION_SECONDS * ticks);
            var currentBiome = player.level().getBiome(player.getOnPos());

            for(var biome : BIOME_FOG_LIST) {
                boolean isInBiome = currentBiome.is(biome.biomeKey);
                biome.weight = Math.max(0.0f, Math.min(1.0f, biome.weight + (isInBiome ? biomeStep : -biomeStep)));
            }
        } else {
            resetFog();
        }
    }

    private static void resetFog(){
        for(var biome : BIOME_FOG_LIST) {
            biome.weight = 0;
        }
    }

    @SubscribeEvent
    public void onFogColor(ViewportEvent.ComputeFogColor event) {
        float red = event.getRed();
        float green = event.getGreen();
        float blue = event.getBlue();

        if (DDConfig.CONFIG.ENABLE_BIOME_FOG.get() && useBiomeFog) {
            float totalWeight = getTotalBiomeWeight();

            if (totalWeight > 0.0f) {
                float invWeight = Math.max(0.0f, 1.0f - Math.min(1.0f, totalWeight));

                float r = red * invWeight;
                float b = red * invWeight;
                float g = red * invWeight;
                for(var biome : BIOME_FOG_LIST) {
                    var color = biome.getWeightedColors();
                    r += color.getRedFloat();
                    g += color.getGreenFloat();
                    b += color.getBlueFloat();
                }

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
            float totalWeight = getTotalBiomeWeight();
            if (totalWeight > 0.0f) {
                float invWeight = Math.max(0.0f, 1.0f - Math.min(1.0f, totalWeight));


                float near = invWeight;
                for(var biome : BIOME_FOG_LIST) {
                    near += biome.getWeightedMin();
                }

                float far = invWeight;
                for(var biome : BIOME_FOG_LIST) {
                    far += biome.getWeightedMax();
                }

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

    private static float getTotalBiomeWeight() {
        float total = 0;
        for(var biome : BIOME_FOG_LIST) {
            total += biome.weight;
        }

        return total;
    }

    private static void checkAndApplyParanoiaFog(ViewportEvent.RenderFog event) {
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

    private static int getEffectAmplifier() {
        Player player = Minecraft.getInstance().player;
        if (player != null && player.hasEffect(DDMobEffects.PARANOIA)) {
            return player.getEffect(DDMobEffects.PARANOIA).getAmplifier();
        }
        return 0;
    }

    private static float lerp(float start, float end, float factor) {
        return start + factor * (end - start);
    }

    private static double lerp(double start, double end, double factor) {
        return start + factor * (end - start);
    }

    private static class BiomeFog {
        private final ResourceKey<Biome> biomeKey;
        private Color color;
        private float weight;
        private int minDist;
        private int maxDist;

        public BiomeFog(ResourceKey<Biome> biome, Color color, int minDist, int maxDist) {
            this.biomeKey = biome;
            this.color = color;
            this.minDist = minDist;
            this.maxDist = maxDist;
        }

        public Color getWeightedColors() {
            return Color.ofRGB(color.getRedFloat() * weight, color.getGreen() * weight, color.getBlueFloat() * weight);
        }

        public float getWeightedMin() {
            return weight * minDist;
        }

        public float getWeightedMax() {
            return weight * maxDist;
        }
    }

}