package com.naterbobber.darkerdepths.client.fog.modifiers;

import com.mojang.blaze3d.shaders.Effect;
import com.mojang.blaze3d.shaders.FogShape;
import com.naterbobber.darkerdepths.client.fog.BiomeFog;
import com.naterbobber.darkerdepths.client.fog.DDBiomeFogs;
import com.naterbobber.darkerdepths.client.fog.FogModifier;
import com.naterbobber.darkerdepths.config.DDConfig;
import com.naterbobber.darkerdepths.init.DDBlocks;
import com.naterbobber.darkerdepths.init.DDMobEffects;
import com.naterbobber.darkerdepths.util.DDTags;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.effect.MobEffects;
import net.minecraftforge.client.event.ViewportEvent;
import software.bernie.geckolib.core.object.Color;
import net.minecraft.world.item.Items;

public class BiomeFogModifier implements FogModifier {
    private static final float BIOME_TRANSITION_SECONDS = 2.0f;

    @Override
    public int getPriority() { return 10; }

    @Override
    public boolean isActive(LocalPlayer player) {
        if (!DDConfig.CONFIG.ENABLE_BIOME_FOG.get()) return false;
        if (player.hasEffect(MobEffects.BLINDNESS)
                || player.hasEffect(DDMobEffects.PARANOIA.get())
                || player.hasEffect(MobEffects.DARKNESS)
        ) return false;

        var fluidType = player.getEyeInFluidType();
        return fluidType.isAir();
    }

    @Override
    public void tick(LocalPlayer player) {
        float biomeStep = 1.0f / (BIOME_TRANSITION_SECONDS * 20.0f);
        var currentBiome = player.level().getBiome(player.getOnPos());

        for (BiomeFog biome : DDBiomeFogs.BIOME_FOGS) {
            boolean isInBiome = currentBiome.is(biome.getBiomeKey());
            biome.setWeight(Math.max(0.0f, Math.min(1.0f, biome.getWeight() + (isInBiome ? biomeStep : -biomeStep))));
        }
    }
    @Override
    public void modifyColor(ViewportEvent.ComputeFogColor event, LocalPlayer player, float partialTick) {
        float totalWeight = Math.min(1.0f, getTotalBiomeWeight());
        if (totalWeight <= 0.0f) return;

        float targetR = 0, targetG = 0, targetB = 0;
        for (BiomeFog biome : DDBiomeFogs.BIOME_FOGS) {
            if (biome.getWeight() <= 0) continue;
            Color color = biome.getColor();
            float normalizedWeight = biome.getWeight() / totalWeight;
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
        for (BiomeFog biome : DDBiomeFogs.BIOME_FOGS) {
            if (biome.getWeight() <= 0) continue;
            float normalizedWeight = biome.getWeight() / totalWeight;
            targetNear += biome.getMinDist() * normalizedWeight;
            targetFar += biome.getMaxDist() * normalizedWeight;
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
        for (BiomeFog biome : DDBiomeFogs.BIOME_FOGS) total += biome.getWeight();
        return total;
    }


}