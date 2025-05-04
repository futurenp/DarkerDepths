package com.naterbobber.darkerdepths.init;

import com.naterbobber.darkerdepths.DarkerDepths;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.heightproviders.VeryBiasedToBottomHeight;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.EnvironmentScanPlacement;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.RandomOffsetPlacement;

import java.util.List;

public class DDPlacedFeatures {

    public static final ResourceKey<PlacedFeature> HUGE_GLOWSHROOM = createKey("huge_glowshroom");
    public static final ResourceKey<PlacedFeature> GLOWSHROOM_PATCH = createKey("glowshroom_patch");
    public static final ResourceKey<PlacedFeature> AMBER = createKey("amber");
    public static final ResourceKey<PlacedFeature> MOLTEN_SPRING = createKey("molten_spring");
    public static final ResourceKey<PlacedFeature> MOLTEN_POOL = createKey("molten_pool");
    public static final ResourceKey<PlacedFeature> SHALE_PLACEMENT = createKey("shale_placement");
    public static final ResourceKey<PlacedFeature> PETRIFIED_BRANCH = createKey("petrified_branch");
    public static final ResourceKey<PlacedFeature> MAGMA_ORE = createKey("magma_ore");
    public static final ResourceKey<PlacedFeature> SILVER_ORE = createKey("silver_ore");
    public static final ResourceKey<PlacedFeature> GRIME_SURFACE = createKey("grime_surface");
    public static final ResourceKey<PlacedFeature> ARID_SURFACE = createKey("arid_surface");
    public static final ResourceKey<PlacedFeature> SHALE_SURFACE = createKey("shale_surface");
    public static final ResourceKey<PlacedFeature> ARID_BOULDER = createKey("arid_boulder");
    public static final ResourceKey<PlacedFeature> GLIMMERING_VINES = createKey("glimmering_vines");

    public static void bootstrap(BootstapContext<PlacedFeature> context) {
        HolderGetter<ConfiguredFeature<?, ?>> lookup = context.lookup(Registries.CONFIGURED_FEATURE);
        PlacementUtils.register(context, HUGE_GLOWSHROOM, lookup.getOrThrow(DDConfiguredFeatures.HUGE_GLOWSHROOM), CountPlacement.of(UniformInt.of(140, 180)), InSquarePlacement.spread(), PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT, BiomeFilter.biome());
        PlacementUtils.register(context, GLOWSHROOM_PATCH, lookup.getOrThrow(DDConfiguredFeatures.GLOWSHROOM_PATCH), CountPlacement.of(UniformInt.of(5, 15)), InSquarePlacement.spread(), PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT, EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12));
        PlacementUtils.register(context, AMBER, lookup.getOrThrow(DDConfiguredFeatures.AMBERS_PLACEMENT), CountPlacement.of(UniformInt.of(120, 180)), InSquarePlacement.spread(), PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT, BiomeFilter.biome());
        PlacementUtils.register(context, MOLTEN_SPRING, lookup.getOrThrow(DDConfiguredFeatures.MOLTEN_SPRING), CountPlacement.of(20), InSquarePlacement.spread(), HeightRangePlacement.of(VeryBiasedToBottomHeight.of(VerticalAnchor.bottom(), VerticalAnchor.belowTop(32), 32)), BiomeFilter.biome());
        PlacementUtils.register(context, MOLTEN_POOL, lookup.getOrThrow(DDConfiguredFeatures.MOLTEN_POOL), CountPlacement.of(60), InSquarePlacement.spread(), PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT, EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12), RandomOffsetPlacement.vertical(ConstantInt.of(1)), BiomeFilter.biome());
        PlacementUtils.register(context, SHALE_PLACEMENT, lookup.getOrThrow(DDConfiguredFeatures.SHALE_PLACEMENT), CountPlacement.of(175), InSquarePlacement.spread(), PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT, BiomeFilter.biome());
        PlacementUtils.register(context, PETRIFIED_BRANCH, lookup.getOrThrow(DDConfiguredFeatures.PETRIFIED_BRANCH), CountPlacement.of(150), InSquarePlacement.spread(), PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT, BiomeFilter.biome());
        PlacementUtils.register(context, MAGMA_ORE, lookup.getOrThrow(DDConfiguredFeatures.MAGMA_ORE), CountPlacement.of(50), InSquarePlacement.spread(), PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT, BiomeFilter.biome());
        PlacementUtils.register(context, SILVER_ORE, lookup.getOrThrow(DDConfiguredFeatures.SILVER_ORE), CountPlacement.of(50), InSquarePlacement.spread(), HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(16)));
        PlacementUtils.register(context, GRIME_SURFACE, lookup.getOrThrow(DDConfiguredFeatures.GRIME_SURFACE), CountPlacement.of(UniformInt.of(192, 256)), InSquarePlacement.spread(), PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT, BiomeFilter.biome());
        PlacementUtils.register(context, ARID_SURFACE, lookup.getOrThrow(DDConfiguredFeatures.ARID_SURFACE), CountPlacement.of(UniformInt.of(192, 256)), InSquarePlacement.spread(), PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT, BiomeFilter.biome());
        PlacementUtils.register(context, SHALE_SURFACE, lookup.getOrThrow(DDConfiguredFeatures.SHALE_SURFACE), CountPlacement.of(UniformInt.of(192, 256)), InSquarePlacement.spread(), PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT, BiomeFilter.biome());
        PlacementUtils.register(context, ARID_BOULDER, lookup.getOrThrow(DDConfiguredFeatures.ARID_BOULDER), CountPlacement.of(UniformInt.of(192, 256)), InSquarePlacement.spread(), PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT, BiomeFilter.biome());
        PlacementUtils.register(context, GLIMMERING_VINES, lookup.getOrThrow(DDConfiguredFeatures.GLIMMERING_VINES), CountPlacement.of(125), InSquarePlacement.spread(), PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT, EnvironmentScanPlacement.scanningFor(Direction.UP, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12), RandomOffsetPlacement.vertical(ConstantInt.of(-1)), BiomeFilter.biome());
    }

    public static ResourceKey<PlacedFeature> createKey(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, DarkerDepths.id(name));
    }

    private static List<PlacementModifier> orePlacement(PlacementModifier modifier, PlacementModifier modifier2) {
        return List.of(modifier, InSquarePlacement.spread(), modifier2, BiomeFilter.biome());
    }

}
