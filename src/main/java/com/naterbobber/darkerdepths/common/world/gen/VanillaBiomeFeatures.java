package com.naterbobber.darkerdepths.common.world.gen;

import com.naterbobber.darkerdepths.client.compat.DDCompatibilty;
import com.naterbobber.darkerdepths.core.DarkerDepthsConfig;
import com.naterbobber.darkerdepths.core.api.DarkerDepthsCompat;
import com.orcinus.config.ConfigHolder;
import net.minecraft.world.gen.GenerationStage;
import net.minecraftforge.common.ForgeConfig;
import net.minecraftforge.common.ForgeConfigSpec;
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
        if (DarkerDepthsCompat.QUARK && DarkerDepthsConfig.generateDarkerDepthsSpeleothems.get()) {
            builder.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, DDConfiguredFeatures.GRIMESTONE_SPELEOTHEM_BOTTOM);
            builder.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, DDConfiguredFeatures.GRIMESTONE_SPELEOTHEM_UP);
        }
    }

    public static void addCrystalPeaks(BiomeGenerationSettingsBuilder builder) {
        builder.withFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, DDConfiguredFeatures.CEILING_CRYSTAL_PEAK);
        builder.withFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, DDConfiguredFeatures.FLOOR_CRYSTAL_PEAK);
    }

    public static void addAmber(BiomeGenerationSettingsBuilder builder) {
        builder.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, DDConfiguredFeatures.AMBER);
    }

    public static void addMoltenCavernDecorations(BiomeGenerationSettingsBuilder builder) {
        builder.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, DDConfiguredFeatures.MOLTEN_CAVE_VEGETATION);
        builder.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, DDConfiguredFeatures.LAVA_POOL_PATCH);
        builder.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, DDConfiguredFeatures.MAGMA_ORE);
        builder.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, DDConfiguredFeatures.GEYSER);
        if (DarkerDepthsCompat.QUARK && DarkerDepthsConfig.generateDarkerDepthsSpeleothems.get()) {
            builder.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, DDConfiguredFeatures.SHALE_SPELEOTHEM_BOTTOM);
            builder.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, DDConfiguredFeatures.SHALE_SPELEOTHEM_UP);
        }
    }

    public static void addMineables(BiomeGenerationSettingsBuilder builder) {
        addMineables(builder, true);
    }

    public static void addMineables(BiomeGenerationSettingsBuilder builder, boolean spawnDefaultOre) {
        builder.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, DDConfiguredFeatures.ARIDROCK_ORE_COAL);
        builder.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, DDConfiguredFeatures.LIMESTONE_ORE_COAL);
        builder.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, DDConfiguredFeatures.ARIDROCK_ORE_IRON);
        builder.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, DDConfiguredFeatures.LIMESTONE_ORE_IRON);
        builder.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, DDConfiguredFeatures.ARIDROCK_ORE_GOLD);
        builder.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, DDConfiguredFeatures.LIMESTONE_ORE_GOLD);
        builder.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, DDConfiguredFeatures.ARIDROCK_ORE_REDSTONE);
        builder.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, DDConfiguredFeatures.LIMESTONE_ORE_REDSTONE);
        builder.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, DDConfiguredFeatures.ARIDROCK_ORE_DIAMOND);
        builder.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, DDConfiguredFeatures.LIMESTONE_ORE_DIAMOND);
        if (spawnDefaultOre) builder.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, DDConfiguredFeatures.SILVER_ORE);
        builder.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, DDConfiguredFeatures.ARIDROCK_ORE_SILVER);
        builder.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, DDConfiguredFeatures.LIMESTONE_ORE_SILVER);
        builder.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, DDConfiguredFeatures.ARIDROCK_ORE_LAPIS);
        builder.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, DDConfiguredFeatures.LIMESTONE_ORE_LAPIS);
    }

    public static void addSandyCatacombsTerrain(BiomeGenerationSettingsBuilder builder) {
        builder.withFeature(GenerationStage.Decoration.LOCAL_MODIFICATIONS, DDConfiguredFeatures.ARIDROCK_CAVE_TERRAIN);
        builder.withFeature(GenerationStage.Decoration.LOCAL_MODIFICATIONS, DDConfiguredFeatures.LIMESTONE_CAVE_TERRAIN);
        builder.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, DDConfiguredFeatures.LIMESTONE_ORE);
    }

    public static void addCaveFossils(BiomeGenerationSettingsBuilder builder) {
        builder.withFeature(GenerationStage.Decoration.UNDERGROUND_STRUCTURES, DDConfiguredFeatures.CAVE_FOSSILS);
    }

    public static void addSandyCatacombsCompat(BiomeGenerationSettingsBuilder builder) {
        if (DarkerDepthsCompat.QUARK && DarkerDepthsConfig.generateDarkerDepthsSpeleothems.get()) {
            builder.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, DDConfiguredFeatures.ARIDROCK_SPELEOTHEM_BOTTOM);
            builder.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, DDConfiguredFeatures.ARIDROCK_SPELEOTHEM_UP);
            builder.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, DDConfiguredFeatures.LIMESTONE_SPELEOTHEM_BOTTOM);
            builder.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, DDConfiguredFeatures.LIMESTONE_SPELEOTHEM_UP);
        }
    }

    public static void addSandyCatacombsVegetation(BiomeGenerationSettingsBuilder builder) {
        builder.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, DDConfiguredFeatures.ARIDROCK_VEGETATION);
        builder.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, DDConfiguredFeatures.ROOTS);
        builder.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, DDConfiguredFeatures.LONG_ROOTS);
    }

    public static void addOasis(BiomeGenerationSettingsBuilder builder) {
        builder.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, DDConfiguredFeatures.OASIS_POOL);
        builder.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, DDConfiguredFeatures.OASIS_VEGETATION);
    }
}