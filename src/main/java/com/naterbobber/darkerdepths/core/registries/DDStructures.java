package com.naterbobber.darkerdepths.core.registries;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.naterbobber.darkerdepths.common.world.gen.feature.structures.CaveFossilStructure;
import com.naterbobber.darkerdepths.core.DarkerDepths;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.DimensionSettings;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.settings.DimensionStructuresSettings;
import net.minecraft.world.gen.settings.StructureSeparationSettings;
import net.minecraftforge.event.RegistryEvent;

import java.util.Locale;

//<>

public class DDStructures {
    public static Structure<NoFeatureConfig> CAVE_FOSSILS = new CaveFossilStructure(NoFeatureConfig.field_236558_a_);

    public static void registerFeatures(RegistryEvent.Register<Feature<?>> eventIn) {
        registerStructure("cave_fossils", CAVE_FOSSILS, GenerationStage.Decoration.UNDERGROUND_STRUCTURES, new StructureSeparationSettings(3, 2, 1234567890), true);
    }

    public static <F extends Structure<NoFeatureConfig>> void registerStructure(String key, F structure, GenerationStage.Decoration stage, StructureSeparationSettings structureSeparationSettings, boolean transformSurroundingLand) {
        registerStructure(new ResourceLocation(DarkerDepths.MODID, key), structure, stage, structureSeparationSettings, transformSurroundingLand);
    }

    public static <F extends Structure<NoFeatureConfig>> void registerStructure(ResourceLocation resourceLocation, F structure, GenerationStage.Decoration stage, StructureSeparationSettings structureSeparationSettings, boolean transformSurroundingLand) {
        structure.setRegistryName(resourceLocation);
        addToStructureInfoMaps(resourceLocation.getNamespace(), structure, stage);

        if (transformSurroundingLand) {
            Structure.field_236384_t_ = ImmutableList.<Structure<?>>builder().addAll(Structure.field_236384_t_).add(structure).build();
        }

        DimensionStructuresSettings.field_236191_b_ = ImmutableMap.<Structure<?>, StructureSeparationSettings>builder().putAll(DimensionStructuresSettings.field_236191_b_).put(structure, structureSeparationSettings).build();

        DimensionSettings.Preset.OVERWORLD.getSettings().getStructures().func_236195_a_().put(structure, structureSeparationSettings);
        DimensionSettings.Preset.NETHER.getSettings().getStructures().func_236195_a_().put(structure, structureSeparationSettings);
        DimensionSettings.Preset.END.getSettings().getStructures().func_236195_a_().put(structure, structureSeparationSettings);
        DimensionSettings.Preset.FLOATING_ISLANDS.getSettings().getStructures().func_236195_a_().put(structure, structureSeparationSettings);
        DimensionSettings.Preset.AMPLIFIED.getSettings().getStructures().func_236195_a_().put(structure, structureSeparationSettings);
        DimensionSettings.Preset.CAVES.getSettings().getStructures().func_236195_a_().put(structure, structureSeparationSettings);
    }

    private static <F extends Structure<?>> F addToStructureInfoMaps(String name, F structure, GenerationStage.Decoration stage) {
        Structure.field_236365_a_.put(name.toLowerCase(Locale.ROOT), structure);
        Structure.field_236385_u_.put(structure, stage);
        return Registry.register(Registry.STRUCTURE_FEATURE, name.toLowerCase(Locale.ROOT), structure);
    }
}