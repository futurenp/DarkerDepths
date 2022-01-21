package com.naterbobber.darkerdepths.init;

import com.naterbobber.darkerdepths.DarkerDepths;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.worldgen.features.CaveFeatures;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.heightproviders.VeryBiasedToBottomHeight;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.EnvironmentScanPlacement;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.RandomOffsetPlacement;

public class DDPlacedFeatures {

    public static final PlacedFeature HUGE_GLOWSHROOM = registerPlacedFeature("huge_glowshroom", DDConfiguredFeatures.HUGE_GLOWSHROOM.placed(CountPlacement.of(120), InSquarePlacement.spread(), PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT, BiomeFilter.biome()));
    public static final PlacedFeature GLOWSHROOM_PATCH = registerPlacedFeature("glowshroom_patch", DDConfiguredFeatures.GLOWSHROOM_PATCH.placed(CountPlacement.of(UniformInt.of(5, 15)), InSquarePlacement.spread(), PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT, EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12)));
    public static final PlacedFeature AMBER = registerPlacedFeature("amber", DDConfiguredFeatures.AMBERS_PLACEMENT.placed(CountPlacement.of(UniformInt.of(120, 180)), InSquarePlacement.spread(), PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT, BiomeFilter.biome()));
    public static final PlacedFeature MOLTEN_SPRING = registerPlacedFeature("molten_spring", DDConfiguredFeatures.MOLTEN_SPRING.placed(CountPlacement.of(20), InSquarePlacement.spread(), HeightRangePlacement.of(VeryBiasedToBottomHeight.of(VerticalAnchor.bottom(), VerticalAnchor.belowTop(32), 32)), BiomeFilter.biome()));
    public static final PlacedFeature MOLTEN_POOL = registerPlacedFeature("molten_pool", DDConfiguredFeatures.MOLTEN_POOL.placed(CountPlacement.of(60), InSquarePlacement.spread(), PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT, EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12), RandomOffsetPlacement.vertical(ConstantInt.of(1)), BiomeFilter.biome()));
    public static final PlacedFeature SHALE_PLACEMENT = registerPlacedFeature("shale_placement", DDConfiguredFeatures.SHALE_PLACEMENT.placed(CountPlacement.of(175), InSquarePlacement.spread(), PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT, BiomeFilter.biome()));
//    public static final PlacedFeature ARIDROCK_PLACEMENT = registerPlacedFeature("aridrock_placement", DDConfiguredFeatures.ARIDROCK_PLACEMENT.placed(CountPlacement.of(175), InSquarePlacement.spread(), HeightRangePlacement.uniform(VerticalAnchor.absolute(0), VerticalAnchor.absolute(256))));
//    public static final PlacedFeature LIMESTONE_PLACEMENT = registerPlacedFeature("limestone_placement", DDConfiguredFeatures.LIMESTONE_PLACEMENT.placed(CountPlacement.of(150), InSquarePlacement.spread(), HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(0))));
    public static final PlacedFeature PETRIFIED_BRANCH = registerPlacedFeature("petrified_branch", DDConfiguredFeatures.PETRIFIED_BRANCH.placed(CountPlacement.of(150), InSquarePlacement.spread(), PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT, BiomeFilter.biome()));
    public static final PlacedFeature SOUL_SOIL = registerPlacedFeature("soul_soil", DDConfiguredFeatures.SOUL_SOIL.placed(CountPlacement.of(125), InSquarePlacement.spread(), PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT));
    public static final PlacedFeature MAGMA_ORE = registerPlacedFeature("magma_ore", DDConfiguredFeatures.MAGMA_ORE.placed(CountPlacement.of(50), InSquarePlacement.spread(), PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT));
    public static final PlacedFeature SILVER_ORE = registerPlacedFeature("silver_ore", DDConfiguredFeatures.SILVER_ORE.placed(CountPlacement.of(50), InSquarePlacement.spread(), HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(16))));
    public static final PlacedFeature GRIME_SURFACE = registerPlacedFeature("grime_surface", DDConfiguredFeatures.GRIME_SURFACE.placed(CountPlacement.of(UniformInt.of(192, 256)), InSquarePlacement.spread(), PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT, BiomeFilter.biome()));
    public static final PlacedFeature ARID_SURFACE = registerPlacedFeature("arid_surface", DDConfiguredFeatures.ARID_SURFACE.placed(CountPlacement.of(UniformInt.of(192, 256)), InSquarePlacement.spread(), PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT, BiomeFilter.biome()));
    public static final PlacedFeature ARID_BOULDER = registerPlacedFeature("arid_boulder", DDConfiguredFeatures.ARID_BOULDER.placed(CountPlacement.of(UniformInt.of(192, 256)), InSquarePlacement.spread(), PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT, BiomeFilter.biome()));
    public static final PlacedFeature GLIMMERING_VINES = registerPlacedFeature("glimmering_vines", DDConfiguredFeatures.GLIMMERING_VINES.placed(CountPlacement.of(125), InSquarePlacement.spread(), PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT, EnvironmentScanPlacement.scanningFor(Direction.UP, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12), RandomOffsetPlacement.vertical(ConstantInt.of(-1)), BiomeFilter.biome()));

    public static PlacedFeature registerPlacedFeature(String key, PlacedFeature configuredFeature) {
        ResourceLocation ID = new ResourceLocation(DarkerDepths.MODID, key);
        if (BuiltinRegistries.PLACED_FEATURE.keySet().contains(ID))
            throw new IllegalStateException("The Placed Feature " + key + "already exists in the registry");

        Registry.register(BuiltinRegistries.PLACED_FEATURE, ID, configuredFeature);
        return configuredFeature;
    }

}
