package com.naterbobber.darkerdepths.init;

import com.naterbobber.darkerdepths.DarkerDepths;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
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

    public static void init() {
    }
    
    public static final Holder<PlacedFeature> HUGE_GLOWSHROOM = registerPlacedFeature("huge_glowshroom", DDConfiguredFeatures.HUGE_GLOWSHROOM, CountPlacement.of(UniformInt.of(140, 180)), InSquarePlacement.spread(), PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT, BiomeFilter.biome());
    public static final Holder<PlacedFeature> GLOWSHROOM_PATCH = registerPlacedFeature("glowshroom_patch", DDConfiguredFeatures.GLOWSHROOM_PATCH, CountPlacement.of(UniformInt.of(5, 15)), InSquarePlacement.spread(), PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT, EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12));
    public static final Holder<PlacedFeature> AMBER = registerPlacedFeature("amber", DDConfiguredFeatures.AMBERS_PLACEMENT, CountPlacement.of(UniformInt.of(120, 180)), InSquarePlacement.spread(), PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT, BiomeFilter.biome());
    public static final Holder<PlacedFeature> MOLTEN_SPRING = registerPlacedFeature("molten_spring", DDConfiguredFeatures.MOLTEN_SPRING, CountPlacement.of(20), InSquarePlacement.spread(), HeightRangePlacement.of(VeryBiasedToBottomHeight.of(VerticalAnchor.bottom(), VerticalAnchor.belowTop(32), 32)), BiomeFilter.biome());
    public static final Holder<PlacedFeature> MOLTEN_POOL = registerPlacedFeature("molten_pool", DDConfiguredFeatures.MOLTEN_POOL, CountPlacement.of(60), InSquarePlacement.spread(), PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT, EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12), RandomOffsetPlacement.vertical(ConstantInt.of(1)), BiomeFilter.biome());
    public static final Holder<PlacedFeature> SHALE_PLACEMENT = registerPlacedFeature("shale_placement", DDConfiguredFeatures.SHALE_PLACEMENT, CountPlacement.of(175), InSquarePlacement.spread(), PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT, BiomeFilter.biome());
    public static final Holder<PlacedFeature> PETRIFIED_BRANCH = registerPlacedFeature("petrified_branch", DDConfiguredFeatures.PETRIFIED_BRANCH, CountPlacement.of(150), InSquarePlacement.spread(), PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT, BiomeFilter.biome());
    public static final Holder<PlacedFeature> MAGMA_ORE = registerPlacedFeature("magma_ore", DDConfiguredFeatures.MAGMA_ORE, CountPlacement.of(50), InSquarePlacement.spread(), PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT);
    public static final Holder<PlacedFeature> SILVER_ORE = registerPlacedFeature("silver_ore", DDConfiguredFeatures.SILVER_ORE, CountPlacement.of(50), InSquarePlacement.spread(), HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(16)));
    public static final Holder<PlacedFeature> GRIME_SURFACE = registerPlacedFeature("grime_surface", DDConfiguredFeatures.GRIME_SURFACE, CountPlacement.of(UniformInt.of(192, 256)), InSquarePlacement.spread(), PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT, BiomeFilter.biome());
    public static final Holder<PlacedFeature> ARID_SURFACE = registerPlacedFeature("arid_surface", DDConfiguredFeatures.ARID_SURFACE, CountPlacement.of(UniformInt.of(192, 256)), InSquarePlacement.spread(), PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT, BiomeFilter.biome());
    public static final Holder<PlacedFeature> ARID_BOULDER = registerPlacedFeature("arid_boulder", DDConfiguredFeatures.ARID_BOULDER, CountPlacement.of(UniformInt.of(192, 256)), InSquarePlacement.spread(), PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT, BiomeFilter.biome());
    public static final Holder<PlacedFeature> GLIMMERING_VINES = registerPlacedFeature("glimmering_vines", DDConfiguredFeatures.GLIMMERING_VINES, CountPlacement.of(125), InSquarePlacement.spread(), PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT, EnvironmentScanPlacement.scanningFor(Direction.UP, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12), RandomOffsetPlacement.vertical(ConstantInt.of(-1)), BiomeFilter.biome());

    public static <FC extends FeatureConfiguration> Holder<PlacedFeature> registerPlacedFeature(String id, Holder<ConfiguredFeature<FC, ?>> feature, PlacementModifier... placementModifiers) {
        return registerPlacedFeature(id, feature, List.of(placementModifiers));
    }

    public static <FC extends FeatureConfiguration> Holder<PlacedFeature> registerPlacedFeature(String id, Holder<ConfiguredFeature<FC, ?>> feature, List<PlacementModifier> placementModifiers) {
        ResourceLocation resourceLocation = new ResourceLocation(DarkerDepths.MODID, id);
        if (BuiltinRegistries.PLACED_FEATURE.keySet().contains(resourceLocation))
            throw new IllegalStateException("Placed Feature ID: \"" + resourceLocation + "\" already exists in the Placed Features registry!");

        PlacedFeature placedFeature = new PlacedFeature(Holder.hackyErase(feature), List.copyOf(placementModifiers));

        return BuiltinRegistries.register(BuiltinRegistries.PLACED_FEATURE, resourceLocation, placedFeature);
    }

    private static List<PlacementModifier> orePlacement(PlacementModifier modifier, PlacementModifier modifier2) {
        return List.of(modifier, InSquarePlacement.spread(), modifier2, BiomeFilter.biome());
    }

    private static List<PlacementModifier> commonOrePlacement(int count, PlacementModifier modifier) {
        return orePlacement(CountPlacement.of(count), modifier);
    }

}
