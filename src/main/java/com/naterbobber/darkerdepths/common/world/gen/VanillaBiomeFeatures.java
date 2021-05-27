package com.naterbobber.darkerdepths.common.world.gen;

import net.minecraft.world.biome.BiomeGenerationSettings;
import net.minecraft.world.gen.GenerationStage;

//<>

public class VanillaBiomeFeatures {
    public static void addGlowshrooms(BiomeGenerationSettings.Builder builder) {
        FeaturePlacement.addFeature(builder, GenerationStage.Decoration.UNDERGROUND_DECORATION, DDConfiguredFeatures.GLOWSHROOM_PATCH);
    }
    
    public static void addCrystalPeaks(BiomeGenerationSettings.Builder builder) {
        FeaturePlacement.addFeature(builder, GenerationStage.Decoration.UNDERGROUND_DECORATION, DDConfiguredFeatures.CEILING_CELESTINE_PEAK);
        FeaturePlacement.addFeature(builder, GenerationStage.Decoration.UNDERGROUND_DECORATION, DDConfiguredFeatures.FLOOR_CELESTINE_PEAK);
        FeaturePlacement.addFeature(builder, GenerationStage.Decoration.UNDERGROUND_DECORATION, DDConfiguredFeatures.CEILING_AMETHYST_PEAK);
        FeaturePlacement.addFeature(builder, GenerationStage.Decoration.UNDERGROUND_DECORATION, DDConfiguredFeatures.FLOOR_AMETHYST_PEAK);
    }

    public static void addAmber(BiomeGenerationSettings.Builder builder) {
        FeaturePlacement.addFeature(builder, GenerationStage.Decoration.VEGETAL_DECORATION, DDConfiguredFeatures.AMBER_GEMSTONE);
    }

    public static void addAsh(BiomeGenerationSettings.Builder builder) {
        FeaturePlacement.addFeature(builder, GenerationStage.Decoration.UNDERGROUND_DECORATION, DDConfiguredFeatures.ASH_BLANKET);
        FeaturePlacement.addFeature(builder, GenerationStage.Decoration.UNDERGROUND_ORES, DDConfiguredFeatures.ASH_ORE);
    }

    public static void addMoltenCavernDecorations(BiomeGenerationSettings.Builder builder) {
//        addAsh(builder);
        FeaturePlacement.addFeature(builder, GenerationStage.Decoration.VEGETAL_DECORATION, DDConfiguredFeatures.MOLTEN_CAVE_VEGETATION);
        FeaturePlacement.addFeature(builder, GenerationStage.Decoration.VEGETAL_DECORATION, DDConfiguredFeatures.LAVA_POOL_PATCH);
        FeaturePlacement.addFeature(builder, GenerationStage.Decoration.VEGETAL_DECORATION, DDConfiguredFeatures.MOLTEN_CAVERN_LAVA_SPRING);
        FeaturePlacement.addFeature(builder, GenerationStage.Decoration.UNDERGROUND_ORES, DDConfiguredFeatures.MAGMA_FLOOR);
        FeaturePlacement.addFeature(builder, GenerationStage.Decoration.UNDERGROUND_ORES, DDConfiguredFeatures.MAGMA_ORE);
        FeaturePlacement.addFeature(builder, GenerationStage.Decoration.UNDERGROUND_ORES, DDConfiguredFeatures.SHALE_ORE);
        FeaturePlacement.addFeature(builder, GenerationStage.Decoration.UNDERGROUND_ORES, DDConfiguredFeatures.MOLTEN_CAVERN_ORE_COAL);
        FeaturePlacement.addFeature(builder, GenerationStage.Decoration.UNDERGROUND_ORES, DDConfiguredFeatures.MOLTEN_CAVERN_ORE_DIAMOND);
        FeaturePlacement.addFeature(builder, GenerationStage.Decoration.UNDERGROUND_DECORATION, DDConfiguredFeatures.SHALE_STONE_REPLACEMENT);
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
        FeaturePlacement.addFeature(builder, GenerationStage.Decoration.UNDERGROUND_DECORATION, DDConfiguredFeatures.ARIDROCK_STONE_REPLACEMENT);
        FeaturePlacement.addFeature(builder, GenerationStage.Decoration.UNDERGROUND_DECORATION, DDConfiguredFeatures.LIMESTONE_STONE_REPLACEMENT);
        FeaturePlacement.addFeature(builder, GenerationStage.Decoration.VEGETAL_DECORATION, DDConfiguredFeatures.LIMESTONE_ORE);
        FeaturePlacement.addFeature(builder, GenerationStage.Decoration.VEGETAL_DECORATION, DDConfiguredFeatures.PETRIFIED_LOG_BRANCH);
        FeaturePlacement.addFeature(builder, GenerationStage.Decoration.UNDERGROUND_ORES, DDConfiguredFeatures.COBBLED_SANDSTONE_ORE);
    }

    public static void addSandyCatacombsVegetation(BiomeGenerationSettings.Builder builder) {
        FeaturePlacement.addFeature(builder, GenerationStage.Decoration.UNDERGROUND_STRUCTURES, DDConfiguredFeatures.CAVE_FOSSILS_FEATURE);
        FeaturePlacement.addFeature(builder, GenerationStage.Decoration.VEGETAL_DECORATION, DDConfiguredFeatures.SANDY_CATACOMBS_VEGETATION);
        FeaturePlacement.addFeature(builder, GenerationStage.Decoration.VEGETAL_DECORATION, DDConfiguredFeatures.ROOTS_FEATURE);
        FeaturePlacement.addFeature(builder, GenerationStage.Decoration.VEGETAL_DECORATION, DDConfiguredFeatures.LONG_ROOTS_FEATURE);
    }

    public static void addOasis(BiomeGenerationSettings.Builder builder) {
        FeaturePlacement.addFeature(builder, GenerationStage.Decoration.VEGETAL_DECORATION, DDConfiguredFeatures.OASIS_POOL);
        FeaturePlacement.addFeature(builder, GenerationStage.Decoration.VEGETAL_DECORATION, DDConfiguredFeatures.OASIS_VEGETATION);
    }

    public static void addCarvers(BiomeGenerationSettings.Builder builder) {
    }
}