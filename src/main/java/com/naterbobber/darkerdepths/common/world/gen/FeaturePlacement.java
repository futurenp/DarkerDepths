package com.naterbobber.darkerdepths.common.world.gen;

import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.world.biome.BiomeGenerationSettings;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.carver.ConfiguredCarver;
import net.minecraft.world.gen.feature.ConfiguredFeature;

//<>

public class FeaturePlacement {
    public static void addFeature(BiomeGenerationSettings.Builder builder, GenerationStage.Decoration decoration, ConfiguredFeature<?, ?> configuredFeature) {
        builder.withFeature(decoration, configuredFeature);
    }

    public static void addCarver(BiomeGenerationSettings.Builder builder, GenerationStage.Carving carving, ConfiguredCarver<?> configuredCarver) {
        builder.withCarver(carving, configuredCarver);
    }

    public static void addSpawn(MobSpawnInfo.Builder builder, EntityClassification classification, EntityType<?> entityType, int weight, int minCount, int maxCount) {
        builder.withSpawner(classification, new MobSpawnInfo.Spawners(entityType, weight, minCount, maxCount));
    }
}
