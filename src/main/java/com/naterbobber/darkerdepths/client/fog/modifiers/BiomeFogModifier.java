package com.naterbobber.darkerdepths.client.fog.modifiers;

import com.mojang.blaze3d.shaders.FogShape;
import com.naterbobber.darkerdepths.client.fog.FogModifier;
import com.naterbobber.darkerdepths.config.DDConfig;
import com.naterbobber.darkerdepths.init.DDMobEffects;
import com.naterbobber.darkerdepths.util.DDResourceKeys;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.level.biome.Biome;
import net.neoforged.neoforge.client.event.ViewportEvent;
import software.bernie.geckolib.util.Color;
import java.util.List;

public class BiomeFogModifier implements FogModifier {
    private static final float BIOME_TRANSITION_SECONDS = 2.0f;
    private final List<BiomeFog> biomeFogs = List.of(
            new BiomeFog(DDResourceKeys.Biomes.MOLTEN_CAVERN, Color.ofRGB(0.42F, 0.27F, 0.18F), DDConfig.CONFIG.MOLTEN_CAVERN_FOG_MIN.get(), DDConfig.CONFIG.MOLTEN_CAVERN_FOG_MAX.get()),
            new BiomeFog(DDResourceKeys.Biomes.SANDY_CATACOMBS, Color.ofRGB(0.22F, 0.13F, 0.10F), DDConfig.CONFIG.SANDY_CATACOMBS_FOG_MIN.get(), DDConfig.CONFIG.SANDY_CATACOMBS_FOG_MAX.get()),
            new BiomeFog(DDResourceKeys.Biomes.GLOWSHROOM_FOREST, Color.ofRGB(0.16F, 0.34F, 0.24F), DDConfig.CONFIG.GLOWSHROOM_FOREST_FOG_MIN.get(), DDConfig.CONFIG.GLOWSHROOM_FOREST_FOG_MAX.get())
    );

    @Override
    public int getPriority() { return 10; }

    @Override
    public boolean isActive(LocalPlayer player) {
        if (!DDConfig.CONFIG.ENABLE_BIOME_FOG.get()) return false;
        if (player.hasEffect(MobEffects.BLINDNESS) || player.hasEffect(DDMobEffects.PARANOIA)) return false;

        var fluidType = player.getEyeInFluidType();
        return fluidType.isAir();
    }

    @Override
    public void tick(LocalPlayer player) {
        float biomeStep = 1.0f / (BIOME_TRANSITION_SECONDS * 20.0f);
        var currentBiome = player.level().getBiome(player.getOnPos());

        for (BiomeFog biome : biomeFogs) {
            boolean isInBiome = currentBiome.is(biome.biomeKey);
            biome.weight = Math.max(0.0f, Math.min(1.0f, biome.weight + (isInBiome ? biomeStep : -biomeStep)));
        }
    }
    @Override
    public void modifyColor(ViewportEvent.ComputeFogColor event, LocalPlayer player, float partialTick) {
        float totalWeight = Math.min(1.0f, getTotalBiomeWeight());
        if (totalWeight <= 0.0f) return;

        float targetR = 0, targetG = 0, targetB = 0;
        for (BiomeFog biome : biomeFogs) {
            if (biome.weight <= 0) continue;
            Color color = biome.color;
            float normalizedWeight = biome.weight / totalWeight;
            targetR += color.getRedFloat() * normalizedWeight;
            targetG += color.getGreenFloat() * normalizedWeight;
            targetB += color.getBlueFloat() * normalizedWeight;
        }

        event.setRed(lerp(event.getRed(), targetR, totalWeight));
        event.setGreen(lerp(event.getGreen(), targetG, totalWeight));
        event.setBlue(lerp(event.getBlue(), targetB, totalWeight));
    }

    @Override
    public void modifyRender(ViewportEvent.RenderFog event, LocalPlayer player, float partialTick) {
        float totalWeight = Math.min(1.0f, getTotalBiomeWeight());
        if (totalWeight <= 0.0f) return;

        float targetNear = 0;
        float targetFar = 0;
        for (BiomeFog biome : biomeFogs) {
            if (biome.weight <= 0) continue;
            float normalizedWeight = biome.weight / totalWeight;
            targetNear += biome.minDist * normalizedWeight;
            targetFar += biome.maxDist * normalizedWeight;
        }

        float vanillaNear = event.getNearPlaneDistance();
        float vanillaFar = event.getFarPlaneDistance();

        event.setNearPlaneDistance(lerp(vanillaNear, targetNear, totalWeight));
        event.setFarPlaneDistance(lerp(vanillaFar, targetFar, totalWeight));

        event.setFogShape(FogShape.SPHERE);
        event.setCanceled(true);
    }

    private float lerp(float start, float end, float factor) {
        return start + factor * (end - start);
    }

    private float getTotalBiomeWeight() {
        float total = 0;
        for (BiomeFog biome : biomeFogs) total += biome.weight;
        return total;
    }

    private static class BiomeFog {
        ResourceKey<Biome> biomeKey; Color color; float weight; int minDist; int maxDist;
        BiomeFog(ResourceKey<Biome> biome, Color color, int minDist, int maxDist) {
            this.biomeKey = biome; this.color = color; this.minDist = minDist; this.maxDist = maxDist;
        }
        Color getWeightedColors() { return Color.ofRGB(color.getRedFloat() * weight, color.getGreenFloat() * weight, color.getBlueFloat() * weight); }
        float getWeightedMin() { return weight * minDist; }
        float getWeightedMax() { return weight * maxDist; }
    }
}