package com.naterbobber.darkerdepths.common.world.gen;

import com.naterbobber.darkerdepths.core.registries.DDEntityTypes;
import net.minecraft.entity.EntityClassification;
import net.minecraft.world.biome.BiomeGenerationSettings;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.gen.GenerationStage;

//<>

public class VanillaBiomeFeatures {
    public static void addGlowshrooms(BiomeGenerationSettings.Builder builder) {
        FeaturePlacement.addFeature(builder, GenerationStage.Decoration.UNDERGROUND_DECORATION, DDConfiguredFeatures.GLOWSHROOM_PATCH);
    }

    public static void addGlowshroomVegetation(BiomeGenerationSettings.Builder builder) {
        FeaturePlacement.addFeature(builder, GenerationStage.Decoration.VEGETAL_DECORATION, DDConfiguredFeatures.GLOWSPIRE_VINES);
        FeaturePlacement.addFeature(builder, GenerationStage.Decoration.VEGETAL_DECORATION, DDConfiguredFeatures.HUGE_GLOWSHROOM);
        FeaturePlacement.addFeature(builder, GenerationStage.Decoration.VEGETAL_DECORATION, DDConfiguredFeatures.GLOWSHROOM_CAVE_VEGETATION);
    }

    public static void addCrystalPeaks(BiomeGenerationSettings.Builder builder) {
        FeaturePlacement.addFeature(builder, GenerationStage.Decoration.UNDERGROUND_DECORATION, DDConfiguredFeatures.CEILING_CRYSTAL_PEAK);
        FeaturePlacement.addFeature(builder, GenerationStage.Decoration.UNDERGROUND_DECORATION, DDConfiguredFeatures.FLOOR_CRYSTAL_PEAK);
    }

    public static void addAmber(BiomeGenerationSettings.Builder builder) {
        FeaturePlacement.addFeature(builder, GenerationStage.Decoration.VEGETAL_DECORATION, DDConfiguredFeatures.AMBER);
    }

    public static void addMoltenCavernDecorations(BiomeGenerationSettings.Builder builder) {
        FeaturePlacement.addFeature(builder, GenerationStage.Decoration.VEGETAL_DECORATION, DDConfiguredFeatures.MOLTEN_CAVE_VEGETATION);
        FeaturePlacement.addFeature(builder, GenerationStage.Decoration.VEGETAL_DECORATION, DDConfiguredFeatures.LAVA_POOL_PATCH);
        FeaturePlacement.addFeature(builder, GenerationStage.Decoration.UNDERGROUND_ORES, DDConfiguredFeatures.MAGMA_ORE);
        FeaturePlacement.addFeature(builder, GenerationStage.Decoration.VEGETAL_DECORATION, DDConfiguredFeatures.GEYSER);
    }
 
    public static void addAridrockOres(BiomeGenerationSettings.Builder builder) {
        FeaturePlacement.addFeature(builder, GenerationStage.Decoration.UNDERGROUND_ORES, DDConfiguredFeatures.ARIDROCK_ORE_COAL);
        FeaturePlacement.addFeature(builder, GenerationStage.Decoration.UNDERGROUND_ORES, DDConfiguredFeatures.ARIDROCK_ORE_IRON);
        FeaturePlacement.addFeature(builder, GenerationStage.Decoration.UNDERGROUND_ORES, DDConfiguredFeatures.ARIDROCK_ORE_GOLD);
        FeaturePlacement.addFeature(builder, GenerationStage.Decoration.UNDERGROUND_ORES, DDConfiguredFeatures.ARIDROCK_ORE_REDSTONE);
        FeaturePlacement.addFeature(builder, GenerationStage.Decoration.UNDERGROUND_ORES, DDConfiguredFeatures.ARIDROCK_ORE_DIAMOND);
        FeaturePlacement.addFeature(builder, GenerationStage.Decoration.UNDERGROUND_ORES, DDConfiguredFeatures.ARIDROCK_ORE_SILVER);
        FeaturePlacement.addFeature(builder, GenerationStage.Decoration.UNDERGROUND_ORES, DDConfiguredFeatures.ARIDROCK_ORE_LAPIS);
    }
    
    public static void addLimestoneOres(BiomeGenerationSettings.Builder builder) {
        FeaturePlacement.addFeature(builder, GenerationStage.Decoration.UNDERGROUND_ORES, DDConfiguredFeatures.LIMESTONE_ORE_COAL);
        FeaturePlacement.addFeature(builder, GenerationStage.Decoration.UNDERGROUND_ORES, DDConfiguredFeatures.LIMESTONE_ORE_IRON);
        FeaturePlacement.addFeature(builder, GenerationStage.Decoration.UNDERGROUND_ORES, DDConfiguredFeatures.LIMESTONE_ORE_GOLD);
        FeaturePlacement.addFeature(builder, GenerationStage.Decoration.UNDERGROUND_ORES, DDConfiguredFeatures.LIMESTONE_ORE_REDSTONE);
        FeaturePlacement.addFeature(builder, GenerationStage.Decoration.UNDERGROUND_ORES, DDConfiguredFeatures.LIMESTONE_ORE_DIAMOND);
        FeaturePlacement.addFeature(builder, GenerationStage.Decoration.UNDERGROUND_ORES, DDConfiguredFeatures.LIMESTONE_ORE_SILVER);
        FeaturePlacement.addFeature(builder, GenerationStage.Decoration.UNDERGROUND_ORES, DDConfiguredFeatures.LIMESTONE_ORE_LAPIS);
    }

    public static void addSandyCatacombsTerrain(BiomeGenerationSettings.Builder builder) {
        FeaturePlacement.addFeature(builder, GenerationStage.Decoration.VEGETAL_DECORATION, DDConfiguredFeatures.PETRIFIED_LOG_BRANCH);
    }

    public static void addSandyCatacombsVegetation(BiomeGenerationSettings.Builder builder) {
        FeaturePlacement.addFeature(builder, GenerationStage.Decoration.UNDERGROUND_STRUCTURES, DDConfiguredFeatures.CAVE_FOSSILS);
        FeaturePlacement.addFeature(builder, GenerationStage.Decoration.VEGETAL_DECORATION, DDConfiguredFeatures.SANDY_CATACOMBS_VEGETATION);
        FeaturePlacement.addFeature(builder, GenerationStage.Decoration.VEGETAL_DECORATION, DDConfiguredFeatures.ROOTS);
        FeaturePlacement.addFeature(builder, GenerationStage.Decoration.VEGETAL_DECORATION, DDConfiguredFeatures.LONG_ROOTS);
    }

    public static void addOasis(BiomeGenerationSettings.Builder builder) {
        FeaturePlacement.addFeature(builder, GenerationStage.Decoration.VEGETAL_DECORATION, DDConfiguredFeatures.OASIS_POOL);
        FeaturePlacement.addFeature(builder, GenerationStage.Decoration.VEGETAL_DECORATION, DDConfiguredFeatures.OASIS_VEGETATION);
    }

    public static void addEntities(MobSpawnInfo.Builder builder) {
        builder.withSpawner(EntityClassification.AMBIENT, new MobSpawnInfo.Spawners(DDEntityTypes.MAGMA_MINION.get(), 5, 1, 2));
    }
}