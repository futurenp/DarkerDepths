package com.naterbobber.darkerdepths.common.world.gen.biome;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeAmbience;
import net.minecraft.world.biome.BiomeGenerationSettings;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilder;

//<>

public abstract class AbstractBiome {
    protected abstract Biome.Category category();

    protected abstract float depth();

    protected abstract float scale();

    protected abstract void ambience(BiomeAmbience.Builder ambience);

    protected abstract Biome.Climate climate();

    protected abstract ConfiguredSurfaceBuilder<?> surfaceBuilder();

    protected abstract void generation(BiomeGenerationSettings.Builder generation);

    protected abstract void spawns(MobSpawnInfo.Builder spawns);

    public final Biome build() {
        Biome.Builder builder = new Biome.Builder();

        builder.category(this.category());
        builder.depth(this.depth());
        builder.scale(this.scale());

        // Configure biome ambience
        BiomeAmbience.Builder ambience = new BiomeAmbience.Builder();
        this.ambience(ambience);
        builder.setEffects(ambience.build());

        // Configure biome climate
        Biome.Climate climate = climate();
        builder.precipitation(climate.precipitation);
        builder.temperature(climate.temperature);
        builder.withTemperatureModifier(climate.temperatureModifier);
        builder.downfall(climate.downfall);

        // Configure biome generation settings
        BiomeGenerationSettings.Builder generation = new BiomeGenerationSettings.Builder();
        this.generation(generation);
        generation.withSurfaceBuilder(this.surfaceBuilder());
        builder.withGenerationSettings(generation.build());

        // Configure biome mob spawn settings
        MobSpawnInfo.Builder spawns = new MobSpawnInfo.Builder();
        this.spawns(spawns);
        builder.withMobSpawnSettings(spawns.build());

        return builder.build();
    }
}