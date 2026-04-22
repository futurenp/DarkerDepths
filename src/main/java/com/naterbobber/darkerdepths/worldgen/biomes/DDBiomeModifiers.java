package com.naterbobber.darkerdepths.worldgen.biomes;

import com.naterbobber.darkerdepths.init.DDEntityTypes;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.neoforged.neoforge.common.world.BiomeModifiers;
import net.neoforged.neoforge.common.world.BiomeModifier;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.naterbobber.darkerdepths.util.DDResourceKeys.Biomes.*;
import static com.naterbobber.darkerdepths.util.DDResourceKeys.BiomeModifiers.*;
import static com.naterbobber.darkerdepths.util.DDResourceKeys.PlacedFeatures.*;

public class DDBiomeModifiers {
    public static void bootstrap(BootstrapContext<BiomeModifier> context) {
        registerMoltenCavern(context);
        registerSandyCatacombs(context);
        registerGlowshroomForest(context);
    }

    private static void registerMoltenCavern(BootstrapContext<BiomeModifier> context) {
        context.register(ADD_MOLTEN_CAVERNS_LOCAL_MODIFICATIONS, new BiomeModifiers.AddFeaturesBiomeModifier(
                getBiome(context, MOLTEN_CAVERN),
                getPlacedFeature(
                        context,
                        MOLTEN_POOL,
                        MOLTEN_SPRING,
                        SCORCHER_PLACER
                ),
                GenerationStep.Decoration.LOCAL_MODIFICATIONS)
        );
        context.register(ADD_MOLTEN_CAVERNS_RAW_GENERATION, new BiomeModifiers.AddFeaturesBiomeModifier(
                getBiome(context, MOLTEN_CAVERN),
                getPlacedFeature(
                        context,
                        LARGE_MOLTEN_PILLAR
                ),
                GenerationStep.Decoration.RAW_GENERATION)
        );
        context.register(ADD_MOLTEN_CAVERNS_ORES, new BiomeModifiers.AddFeaturesBiomeModifier(
                getBiome(context, MOLTEN_CAVERN),
                getPlacedFeature(
                        context,
                        MAGMA_DISK,
                        CRYSTAL_HUSK_ORE,
                        SCORCHED_REMAINS_PILE
                ),
                GenerationStep.Decoration.UNDERGROUND_ORES
        ));
        context.register(ADD_MOLTEN_CAVERNS_VEGETAL_FEATURES, new BiomeModifiers.AddFeaturesBiomeModifier(
                getBiome(context, MOLTEN_CAVERN),
                getPlacedFeature(
                        context,
                        DARKSLATE_VEGETATION,
                        PETRIFIED_ROOTS
                ),
                GenerationStep.Decoration.VEGETAL_DECORATION
        ));
        context.register(ADD_MOLTEN_CAVERNS_TOP_LAYER_MODIFICATIONS, new BiomeModifiers.AddFeaturesBiomeModifier(
                getBiome(context, MOLTEN_CAVERN),
                getPlacedFeature(
                        context
                ),
                GenerationStep.Decoration.TOP_LAYER_MODIFICATION
        ));
    }

    private static void registerSandyCatacombs(BootstrapContext<BiomeModifier> context){
        context.register(ADD_SANDY_CATACOMBS_LOCAL_MODIFICATIONS, new BiomeModifiers.AddFeaturesBiomeModifier(
                getBiome(context, SANDY_CATACOMBS),
                getPlacedFeature(
                        context
                ),
                GenerationStep.Decoration.LOCAL_MODIFICATIONS)
        );
        context.register(ADD_SANDY_CATACOMBS_VEGETAL_FEATURES, new BiomeModifiers.AddFeaturesBiomeModifier(
                getBiome(context, SANDY_CATACOMBS),
                getPlacedFeature(
                        context,
                        AMBER_PLACEMENT,
                        PETRIFIED_BRANCH,
                        PETRIFIED_ROOTS,
                        ARID_VEGETATION
                ),
                GenerationStep.Decoration.VEGETAL_DECORATION)
        );
        context.register(ADD_SANDY_CATACOMBS_SPAWNS, new BiomeModifiers.AddSpawnsBiomeModifier(
                getBiome(context, SANDY_CATACOMBS),
                List.of(
                        new MobSpawnSettings.SpawnerData(EntityType.CAVE_SPIDER, 80, 2, 4),
                        new MobSpawnSettings.SpawnerData(EntityType.SKELETON, 50, 1, 4),
                        new MobSpawnSettings.SpawnerData(EntityType.ENDERMAN, 10, 1, 4),
                        new MobSpawnSettings.SpawnerData(EntityType.HUSK, 95, 2, 4),
                        new MobSpawnSettings.SpawnerData(DDEntityTypes.BODY_SNATCHER.get(), 30, 1, 3)
                )
        ));
        context.register(ADD_SANDY_CATACOMBS_RAW_GENERATION, new BiomeModifiers.AddFeaturesBiomeModifier(
                        getBiome(context, SANDY_CATACOMBS),
                        getPlacedFeature(
                                context,
                                ARID_BOULDER
                        ),
                        GenerationStep.Decoration.RAW_GENERATION)
        );

        context.register(ADD_SANDY_CATACOMBS_UNDERGROUND_DECORATION, new BiomeModifiers.AddFeaturesBiomeModifier(
                        getBiome(context, SANDY_CATACOMBS),
                        getPlacedFeature(
                                context,
                                CATACOMBS_LAVA_LINING
                        ),
                        GenerationStep.Decoration.UNDERGROUND_DECORATION)
        );
    }

    private static void registerGlowshroomForest(BootstrapContext<BiomeModifier> context) {
        context.register(ADD_GLOWSHROOM_FOREST_RAW_GENERATION, new BiomeModifiers.AddFeaturesBiomeModifier(
                getBiome(context, GLOWSHROOM_FOREST),
                getPlacedFeature(
                        context,
                        GRIMESTONE_CLIFF
                ),
                GenerationStep.Decoration.RAW_GENERATION)
        );
        context.register(ADD_GLOWSHROOM_FOREST_UNDERGROUND_DECORATION, new BiomeModifiers.AddFeaturesBiomeModifier(
                getBiome(context, GLOWSHROOM_FOREST),
                getPlacedFeature(
                        context,
                        GRIME_SURFACE
                ),
                GenerationStep.Decoration.UNDERGROUND_DECORATION)
        );
        context.register(ADD_GLOWSHROOM_FOREST_VEGETAL_FEATURES, new BiomeModifiers.AddFeaturesBiomeModifier(
                getBiome(context, GLOWSHROOM_FOREST),
                getPlacedFeature(
                        context,
                        GLIMMERING_VINES,
                        SMUSHED_GLOWSHROOM,
                        GRIME_VEGETATION_PATCH
                ),
                GenerationStep.Decoration.VEGETAL_DECORATION)
        );
        context.register(ADD_GLOWSHROOM_FOREST_SPAWNS, new BiomeModifiers.AddSpawnsBiomeModifier(
                getBiome(context, GLOWSHROOM_FOREST),
                List.of(
                        new MobSpawnSettings.SpawnerData(DDEntityTypes.GLOWSHROOM_MONSTER.get(), 120, 1, 2)
                )
        ));
    }

    @SafeVarargs
    @NotNull
    private static HolderSet.Direct<PlacedFeature> getPlacedFeature(BootstrapContext<BiomeModifier> context, ResourceKey<PlacedFeature>... placedFeature) {
        return HolderSet.direct(Stream.of(placedFeature).map(resourceKey -> context.lookup(Registries.PLACED_FEATURE).getOrThrow(resourceKey)).collect(Collectors.toList()));
    }

    @NotNull
    private static HolderSet.Direct<PlacedFeature> getPlacedFeature(BootstrapContext<BiomeModifier> context, ResourceKey<PlacedFeature> placedFeature) {
        return HolderSet.direct(context.lookup(Registries.PLACED_FEATURE).getOrThrow(placedFeature));
    }

    private static HolderSet.@NotNull Direct<Biome> getBiome(BootstrapContext<BiomeModifier> bootstapContext, ResourceKey<Biome> biome) {
        return HolderSet.direct(bootstapContext.lookup(Registries.BIOME).getOrThrow(biome));
    }

}
