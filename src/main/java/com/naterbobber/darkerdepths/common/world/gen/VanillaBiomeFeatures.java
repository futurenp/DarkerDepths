package com.naterbobber.darkerdepths.common.world.gen;

import net.minecraft.world.gen.GenerationStage;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;

//<>

public class VanillaBiomeFeatures {
    public static void addGlowshrooms(BiomeGenerationSettingsBuilder builder) {
        builder.withFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, DDConfiguredFeatures.GLOWSHROOM_PATCH);
    }

    public static void addGlowshroomVegetation(BiomeGenerationSettingsBuilder builder) {
        builder.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, DDConfiguredFeatures.GLOWSPIRE_VINES);
        builder.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, DDConfiguredFeatures.HUGE_GLOWSHROOM);
        builder.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, DDConfiguredFeatures.GLOWSHROOM_VEGETATION_PATCH);
    }

    public static void addCrystalPeaks(BiomeGenerationSettingsBuilder builder) {
        FeaturePlacement.addFeature(builder, GenerationStage.Decoration.UNDERGROUND_DECORATION, DDConfiguredFeatures.CEILING_CRYSTAL_PEAK);
        FeaturePlacement.addFeature(builder, GenerationStage.Decoration.UNDERGROUND_DECORATION, DDConfiguredFeatures.FLOOR_CRYSTAL_PEAK);
    }

    public static void addAmber(BiomeGenerationSettingsBuilder builder) {
        FeaturePlacement.addFeature(builder, GenerationStage.Decoration.VEGETAL_DECORATION, DDConfiguredFeatures.AMBER);
    }

    public static void addMoltenCavernDecorations(BiomeGenerationSettingsBuilder builder) {
        FeaturePlacement.addFeature(builder, GenerationStage.Decoration.VEGETAL_DECORATION, DDConfiguredFeatures.MOLTEN_CAVE_VEGETATION);
        FeaturePlacement.addFeature(builder, GenerationStage.Decoration.VEGETAL_DECORATION, DDConfiguredFeatures.LAVA_POOL_PATCH);
        FeaturePlacement.addFeature(builder, GenerationStage.Decoration.UNDERGROUND_ORES, DDConfiguredFeatures.MAGMA_ORE);
        FeaturePlacement.addFeature(builder, GenerationStage.Decoration.VEGETAL_DECORATION, DDConfiguredFeatures.GEYSER);
    }
 
    public static void addAridrockOres(BiomeGenerationSettingsBuilder builder) {
        FeaturePlacement.addFeature(builder, GenerationStage.Decoration.UNDERGROUND_ORES, DDConfiguredFeatures.ARIDROCK_ORE_COAL);
        FeaturePlacement.addFeature(builder, GenerationStage.Decoration.UNDERGROUND_ORES, DDConfiguredFeatures.ARIDROCK_ORE_IRON);
        FeaturePlacement.addFeature(builder, GenerationStage.Decoration.UNDERGROUND_ORES, DDConfiguredFeatures.ARIDROCK_ORE_GOLD);
        FeaturePlacement.addFeature(builder, GenerationStage.Decoration.UNDERGROUND_ORES, DDConfiguredFeatures.ARIDROCK_ORE_REDSTONE);
        FeaturePlacement.addFeature(builder, GenerationStage.Decoration.UNDERGROUND_ORES, DDConfiguredFeatures.ARIDROCK_ORE_DIAMOND);
        FeaturePlacement.addFeature(builder, GenerationStage.Decoration.UNDERGROUND_ORES, DDConfiguredFeatures.ARIDROCK_ORE_SILVER);
        FeaturePlacement.addFeature(builder, GenerationStage.Decoration.UNDERGROUND_ORES, DDConfiguredFeatures.ARIDROCK_ORE_LAPIS);
    }
    
    public static void addLimestoneOres(BiomeGenerationSettingsBuilder builder) {
        FeaturePlacement.addFeature(builder, GenerationStage.Decoration.UNDERGROUND_ORES, DDConfiguredFeatures.LIMESTONE_ORE_COAL);
        FeaturePlacement.addFeature(builder, GenerationStage.Decoration.UNDERGROUND_ORES, DDConfiguredFeatures.LIMESTONE_ORE_IRON);
        FeaturePlacement.addFeature(builder, GenerationStage.Decoration.UNDERGROUND_ORES, DDConfiguredFeatures.LIMESTONE_ORE_GOLD);
        FeaturePlacement.addFeature(builder, GenerationStage.Decoration.UNDERGROUND_ORES, DDConfiguredFeatures.LIMESTONE_ORE_REDSTONE);
        FeaturePlacement.addFeature(builder, GenerationStage.Decoration.UNDERGROUND_ORES, DDConfiguredFeatures.LIMESTONE_ORE_DIAMOND);
        FeaturePlacement.addFeature(builder, GenerationStage.Decoration.UNDERGROUND_ORES, DDConfiguredFeatures.LIMESTONE_ORE_SILVER);
        FeaturePlacement.addFeature(builder, GenerationStage.Decoration.UNDERGROUND_ORES, DDConfiguredFeatures.LIMESTONE_ORE_LAPIS);
    }

    public static void addSandyCatacombsTerrain(BiomeGenerationSettingsBuilder builder) {
        FeaturePlacement.addFeature(builder, GenerationStage.Decoration.VEGETAL_DECORATION, DDConfiguredFeatures.PETRIFIED_LOG_BRANCH);
    }

    public static void addSandyCatacombsVegetation(BiomeGenerationSettingsBuilder builder) {
        FeaturePlacement.addFeature(builder, GenerationStage.Decoration.UNDERGROUND_STRUCTURES, DDConfiguredFeatures.CAVE_FOSSILS);
        FeaturePlacement.addFeature(builder, GenerationStage.Decoration.VEGETAL_DECORATION, DDConfiguredFeatures.SANDY_CATACOMBS_VEGETATION);
        FeaturePlacement.addFeature(builder, GenerationStage.Decoration.VEGETAL_DECORATION, DDConfiguredFeatures.ROOTS);
        FeaturePlacement.addFeature(builder, GenerationStage.Decoration.VEGETAL_DECORATION, DDConfiguredFeatures.LONG_ROOTS);
    }

    public static void addOasis(BiomeGenerationSettingsBuilder builder) {
        FeaturePlacement.addFeature(builder, GenerationStage.Decoration.VEGETAL_DECORATION, DDConfiguredFeatures.OASIS_POOL);
        FeaturePlacement.addFeature(builder, GenerationStage.Decoration.VEGETAL_DECORATION, DDConfiguredFeatures.OASIS_VEGETATION);
    }
}