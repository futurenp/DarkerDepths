package com.naterbobber.darkerdepths.common.world.gen.biome;

import com.naterbobber.darkerdepths.common.world.gen.VanillaBiomeFeatures;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeAmbience;
import net.minecraft.world.biome.BiomeGenerationSettings;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;

//<>

public class CrystalCaveBiome extends AbstractBiome {
    @Override
    protected Biome.Category category() {
        return Biome.Category.NONE;
    }

    @Override
    protected float depth() {
        return 0.125f;
    }

    @Override
    protected float scale() {
        return 0.05f;
    }

    @Override
    protected void ambience(BiomeAmbience.Builder ambience) {
        ambience.setWaterColor(10001097).setWaterFogColor(329011).setFogColor(12638463).withSkyColor(0);
    }

    @Override
    protected Biome.Climate climate() {
        return new Biome.Climate(Biome.RainType.NONE, 0.8f, Biome.TemperatureModifier.NONE, 0.4f);
    }

    @Override
    protected ConfiguredSurfaceBuilder<?> surfaceBuilder() {
        return new ConfiguredSurfaceBuilder<>(SurfaceBuilder.DEFAULT, SurfaceBuilder.STONE_STONE_GRAVEL_CONFIG);
    }

    @Override
    protected void generation(BiomeGenerationSettings.Builder builder) {
    }

    @Override
    protected void spawns(MobSpawnInfo.Builder spawns) {
    }
}