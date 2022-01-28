package com.naterbobber.darkerdepths.init;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.naterbobber.darkerdepths.DarkerDepths;
import com.naterbobber.darkerdepths.world.gen.structures.AridTombStructure;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.world.level.levelgen.StructureSettings;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.feature.configurations.JigsawConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.StructureFeatureConfiguration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.HashMap;
import java.util.Map;

@Mod.EventBusSubscriber(modid = DarkerDepths.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DDStructures {

    public static final DeferredRegister<StructureFeature<?>> STRUCTURES = DeferredRegister.create(ForgeRegistries.STRUCTURE_FEATURES, DarkerDepths.MODID);

    public static final RegistryObject<StructureFeature<JigsawConfiguration>> ARID_CATACOMBS = STRUCTURES.register("arid_catacombs", () -> new AridTombStructure(JigsawConfiguration.CODEC));

    public static void setupStructures() {
        setupMapSpacingAndLand(
                ARID_CATACOMBS.get(),
                new StructureFeatureConfiguration(10,
                        5,
                        42069),
                true
                );
    }

    public static <F extends StructureFeature<?>> void setupMapSpacingAndLand(F structure, StructureFeatureConfiguration structureFeatureConfiguration, boolean transformSurroundingLand) {
        StructureFeature.STRUCTURES_REGISTRY.put(structure.getRegistryName().toString(), structure);

        if(transformSurroundingLand){
            StructureFeature.NOISE_AFFECTING_FEATURES =
                    ImmutableList.<StructureFeature<?>>builder()
                            .addAll(StructureFeature.NOISE_AFFECTING_FEATURES)
                            .add(structure)
                            .build();
        }

        StructureSettings.DEFAULTS =
                ImmutableMap.<StructureFeature<?>, StructureFeatureConfiguration>builder()
                        .putAll(StructureSettings.DEFAULTS)
                        .put(structure, structureFeatureConfiguration)
                        .build();


        BuiltinRegistries.NOISE_GENERATOR_SETTINGS.entrySet().forEach(settings -> {
            Map<StructureFeature<?>, StructureFeatureConfiguration> structureMap = settings.getValue().structureSettings().structureConfig();

            if(structureMap instanceof ImmutableMap){
                Map<StructureFeature<?>, StructureFeatureConfiguration> tempMap = new HashMap<>(structureMap);
                tempMap.put(structure, structureFeatureConfiguration);
                settings.getValue().structureSettings().structureConfig = tempMap;
            }
            else{
                structureMap.put(structure, structureFeatureConfiguration);
            }
        });
    }

}
