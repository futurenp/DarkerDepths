package com.naterbobber.darkerdepths.worldgen;

import com.naterbobber.darkerdepths.init.DDEntityTypes;
import com.naterbobber.darkerdepths.util.DDResourceKeys;
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

public class DDBiomeModifiers {
    public static void bootstrap(BootstrapContext<BiomeModifier> context) {
        context.register(DDResourceKeys.BiomeModifiers.ADD_MOLTEN_CAVERNS_VEGETAL_FEATURES, new BiomeModifiers.AddFeaturesBiomeModifier(
                getBiome(context, DDResourceKeys.Biomes.MOLTEN_CAVERN),
                getPlacedFeature(
                        context,
                        DDResourceKeys.PlacedFeatures.DARKSLATE_PLACEMENT,
                        DDResourceKeys.PlacedFeatures.AMBER,
                        DDResourceKeys.PlacedFeatures.MOLTEN_POOL,
                        DDResourceKeys.PlacedFeatures.MOLTEN_SPRING
                ),
                GenerationStep.Decoration.VEGETAL_DECORATION)
        );
        context.register(DDResourceKeys.BiomeModifiers.ADD_MOLTEN_CAVERNS_ORES, new BiomeModifiers.AddFeaturesBiomeModifier(
                getBiome(context, DDResourceKeys.Biomes.MOLTEN_CAVERN),
                getPlacedFeature(
                        context,
                        DDResourceKeys.PlacedFeatures.MAGMA_ORE,
                        DDResourceKeys.PlacedFeatures.DEAD_LIVING_CRYSTAL_ORE
                ),
                GenerationStep.Decoration.UNDERGROUND_ORES
        ));
        context.register(DDResourceKeys.BiomeModifiers.ADD_SANDY_CATACOMBS_LOCAL_MODIFICATIONS, new BiomeModifiers.AddFeaturesBiomeModifier(
                        getBiome(context, DDResourceKeys.Biomes.SANDY_CATACOMBS),
                        getPlacedFeature(
                                context
//                          Testing out this feature for 2.1
//                        DDResourceKeys.PlacedFeatures.CATACOMBS_LAYERED_PLACEMENT
                        ),
                        GenerationStep.Decoration.LOCAL_MODIFICATIONS)
        );
        context.register(DDResourceKeys.BiomeModifiers.ADD_SANDY_CATACOMBS_VEGETAL_FEATURES, new BiomeModifiers.AddFeaturesBiomeModifier(
                getBiome(context, DDResourceKeys.Biomes.SANDY_CATACOMBS),
                getPlacedFeature(
                        context,
                        DDResourceKeys.PlacedFeatures.PETRIFIED_BRANCH,
                        DDResourceKeys.PlacedFeatures.ARID_SURFACE
                ),
                GenerationStep.Decoration.VEGETAL_DECORATION)
        );
        context.register(DDResourceKeys.BiomeModifiers.ADD_SANDY_CATACOMBS_SPAWNS, new BiomeModifiers.AddSpawnsBiomeModifier(
                getBiome(context, DDResourceKeys.Biomes.SANDY_CATACOMBS),
                List.of(
                        new MobSpawnSettings.SpawnerData(EntityType.CAVE_SPIDER, 80, 2, 4),
                        new MobSpawnSettings.SpawnerData(EntityType.SKELETON, 50, 1, 4),
                        new MobSpawnSettings.SpawnerData(EntityType.ENDERMAN, 10, 1, 4),
                        new MobSpawnSettings.SpawnerData(EntityType.HUSK, 95, 2, 4),
                        new MobSpawnSettings.SpawnerData(DDEntityTypes.BODY_SNATCHER.get(), 30, 1, 3)
                )
        ));
        context.register(DDResourceKeys.BiomeModifiers.ADD_SANDY_CATACOMBS_RAW_GENERATION, new BiomeModifiers.AddFeaturesBiomeModifier(
                getBiome(context, DDResourceKeys.Biomes.SANDY_CATACOMBS),
                getPlacedFeature(
                        context,
                        DDResourceKeys.PlacedFeatures.ARID_BOULDER,
                        DDResourceKeys.PlacedFeatures.DUSKROCK_ORE
                        //DDResourceKeys.PlacedFeatures.DUSKROCK_STRIPE
                ),
                GenerationStep.Decoration.RAW_GENERATION)
        );
        context.register(DDResourceKeys.BiomeModifiers.ADD_SANDY_CATACOMBS_UNDERGROUND_DECORATION, new BiomeModifiers.AddFeaturesBiomeModifier(
                        getBiome(context, DDResourceKeys.Biomes.SANDY_CATACOMBS),
                        getPlacedFeature(
                                context,
                                DDResourceKeys.PlacedFeatures.CATACOMBS_LAVA_LINING
//                        DDResourceKeys.PlacedFeatures.CATACOMBS_SAND_PLACEMENT
                        ),
                        GenerationStep.Decoration.UNDERGROUND_DECORATION)
        );
        context.register(DDResourceKeys.BiomeModifiers.ADD_GLOWSHROOM_FOREST_VEGETAL_FEATURES, new BiomeModifiers.AddFeaturesBiomeModifier(
                getBiome(context, DDResourceKeys.Biomes.GLOWSHROOM_FOREST),
                getPlacedFeature(
                        context,
                        DDResourceKeys.PlacedFeatures.GLIMMERING_VINES,
                        DDResourceKeys.PlacedFeatures.HUGE_GLOWSHROOM,
                        DDResourceKeys.PlacedFeatures.GRIME_SURFACE
                ),
                GenerationStep.Decoration.VEGETAL_DECORATION)
        );
        context.register(DDResourceKeys.BiomeModifiers.ADD_GLOWSHROOM_FOREST_SPAWNS, new BiomeModifiers.AddSpawnsBiomeModifier(
                getBiome(context, DDResourceKeys.Biomes.GLOWSHROOM_FOREST),
                List.of(
                        new MobSpawnSettings.SpawnerData(DDEntityTypes.GLOWSHROOM_MONSTER.get(), 50, 1, 2)
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
