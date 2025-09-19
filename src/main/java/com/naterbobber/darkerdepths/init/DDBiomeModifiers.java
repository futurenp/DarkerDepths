package com.naterbobber.darkerdepths.init;

import com.naterbobber.darkerdepths.DarkerDepths;
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
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DDBiomeModifiers {

    public static final ResourceKey<BiomeModifier> ADD_MOLTEN_CAVERNS_VEGETAL_FEATURES = createKey("add_molten_caverns_vegetal_features");
    public static final ResourceKey<BiomeModifier> ADD_MOLTEN_CAVERNS_ORES = createKey("add_molten_caverns_ores");
    public static final ResourceKey<BiomeModifier> ADD_SANDY_CATACOMBS_LOCAL_MODIFICATIONS = createKey("add_sandy_catacombs_local_modifications");
    public static final ResourceKey<BiomeModifier> ADD_SANDY_CATACOMBS_VEGETAL_FEATURES = createKey("add_sandy_catacombs_vegetal_features");
    public static final ResourceKey<BiomeModifier> ADD_SANDY_CATACOMBS_SPAWNS = createKey("add_sandy_catacombs_spawns");
    public static final ResourceKey<BiomeModifier> ADD_SANDY_CATACOMBS_RAW_GENERATION = createKey("add_sandy_catacombs_raw_generation");
    public static final ResourceKey<BiomeModifier> ADD_SANDY_CATACOMBS_UNDERGROUND_DECORATION = createKey("add_sandy_catacombs_underground_decoration");
    public static final ResourceKey<BiomeModifier> ADD_GLOWSHROOM_FOREST_VEGETAL_FEATURES = createKey("add_glowshroom_forest_vegetal_features");
    public static final ResourceKey<BiomeModifier> ADD_GLOWSHROOM_FOREST_SPAWNS = createKey("add_glowshroom_forest_spawns");

    private static ResourceKey<BiomeModifier> createKey(String name) {
        return ResourceKey.create(NeoForgeRegistries.Keys.BIOME_MODIFIERS, DarkerDepths.id(name));
    }

    public static void bootstrap(BootstrapContext<BiomeModifier> context) {
        context.register(ADD_MOLTEN_CAVERNS_VEGETAL_FEATURES, new BiomeModifiers.AddFeaturesBiomeModifier(
                getBiome(context, DDBiomes.MOLTEN_CAVERN),
                getPlacedFeature(
                        context,
                        DDPlacedFeatures.DARKSLATE_PLACEMENT,
                        DDPlacedFeatures.AMBER,
                        DDPlacedFeatures.MOLTEN_POOL,
                        DDPlacedFeatures.MOLTEN_SPRING
                ),
                GenerationStep.Decoration.VEGETAL_DECORATION)
        );
        context.register(ADD_MOLTEN_CAVERNS_ORES, new BiomeModifiers.AddFeaturesBiomeModifier(
                getBiome(context, DDBiomes.MOLTEN_CAVERN),
                getPlacedFeature(
                        context,
                        DDPlacedFeatures.MAGMA_ORE,
                        DDPlacedFeatures.DEAD_LIVING_CRYSTAL_ORE
                ),
                GenerationStep.Decoration.UNDERGROUND_ORES
        ));
        context.register(ADD_SANDY_CATACOMBS_LOCAL_MODIFICATIONS, new BiomeModifiers.AddFeaturesBiomeModifier(
                        getBiome(context, DDBiomes.SANDY_CATACOMBS),
                        getPlacedFeature(
                                context
//                          Testing out this feature for 2.1
//                        DDPlacedFeatures.CATACOMBS_LAYERED_PLACEMENT
                        ),
                        GenerationStep.Decoration.LOCAL_MODIFICATIONS)
        );
        context.register(ADD_SANDY_CATACOMBS_VEGETAL_FEATURES, new BiomeModifiers.AddFeaturesBiomeModifier(
                getBiome(context, DDBiomes.SANDY_CATACOMBS),
                getPlacedFeature(
                        context,
                        DDPlacedFeatures.PETRIFIED_BRANCH,
                        DDPlacedFeatures.ARID_SURFACE
                ),
                GenerationStep.Decoration.VEGETAL_DECORATION)
        );
        context.register(ADD_SANDY_CATACOMBS_SPAWNS, new BiomeModifiers.AddSpawnsBiomeModifier(
                getBiome(context, DDBiomes.SANDY_CATACOMBS),
                List.of(
                        new MobSpawnSettings.SpawnerData(EntityType.CAVE_SPIDER, 80, 2, 4),
                        new MobSpawnSettings.SpawnerData(EntityType.SKELETON, 50, 1, 4),
                        new MobSpawnSettings.SpawnerData(EntityType.ENDERMAN, 10, 1, 4),
                        new MobSpawnSettings.SpawnerData(EntityType.HUSK, 95, 2, 4),
                        new MobSpawnSettings.SpawnerData(DDEntityTypes.BODY_SNATCHER.get(), 30, 1, 3)
                )
        ));
        context.register(ADD_SANDY_CATACOMBS_RAW_GENERATION, new BiomeModifiers.AddFeaturesBiomeModifier(
                getBiome(context, DDBiomes.SANDY_CATACOMBS),
                getPlacedFeature(
                        context,
                        DDPlacedFeatures.ARID_BOULDER,
                        DDPlacedFeatures.DUSKROCK_ORE
                        //DDPlacedFeatures.DUSKROCK_STRIPE
                ),
                GenerationStep.Decoration.RAW_GENERATION)
        );
        context.register(ADD_SANDY_CATACOMBS_UNDERGROUND_DECORATION, new BiomeModifiers.AddFeaturesBiomeModifier(
                        getBiome(context, DDBiomes.SANDY_CATACOMBS),
                        getPlacedFeature(
                                context,
                                DDPlacedFeatures.CATACOMBS_LAVA_LINING
//                        DDPlacedFeatures.CATACOMBS_SAND_PLACEMENT
                        ),
                        GenerationStep.Decoration.UNDERGROUND_DECORATION)
        );
        context.register(ADD_GLOWSHROOM_FOREST_VEGETAL_FEATURES, new BiomeModifiers.AddFeaturesBiomeModifier(
                getBiome(context, DDBiomes.GLOWSHROOM_FOREST),
                getPlacedFeature(
                        context,
                        DDPlacedFeatures.GLIMMERING_VINES,
                        DDPlacedFeatures.HUGE_GLOWSHROOM,
                        DDPlacedFeatures.GRIME_SURFACE
                ),
                GenerationStep.Decoration.VEGETAL_DECORATION)
        );
        context.register(ADD_GLOWSHROOM_FOREST_SPAWNS, new BiomeModifiers.AddSpawnsBiomeModifier(
                getBiome(context, DDBiomes.GLOWSHROOM_FOREST),
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
