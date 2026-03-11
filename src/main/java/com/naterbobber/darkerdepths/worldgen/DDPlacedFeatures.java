package com.naterbobber.darkerdepths.worldgen;

import com.naterbobber.darkerdepths.util.DDResourceKeys;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.heightproviders.VeryBiasedToBottomHeight;
import net.minecraft.world.level.levelgen.placement.*;

import java.util.List;

import static com.naterbobber.darkerdepths.util.DDResourceKeys.PlacedFeatures.*;

public class DDPlacedFeatures {
    public static void bootstrap(BootstrapContext<PlacedFeature> context) {
        HolderGetter<ConfiguredFeature<?, ?>> lookup = context.lookup(Registries.CONFIGURED_FEATURE);
        PlacementUtils.register(context, HUGE_GLOWSHROOM, lookup.getOrThrow(DDResourceKeys.ConfiguredFeatures.HUGE_GLOWSHROOM), CountPlacement.of(UniformInt.of(140, 180)), InSquarePlacement.spread(), PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT, BiomeFilter.biome());
        PlacementUtils.register(context, GLOWSHROOM_PATCH, lookup.getOrThrow(DDResourceKeys.ConfiguredFeatures.GLOWSHROOM_PATCH), CountPlacement.of(UniformInt.of(3, 8)), InSquarePlacement.spread(), PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT, EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12));
        PlacementUtils.register(context, AMBER_PLACEMENT, lookup.getOrThrow(DDResourceKeys.ConfiguredFeatures.AMBERS_PLACEMENT), CountPlacement.of(UniformInt.of(60, 120)), InSquarePlacement.spread(), PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT, BiomeFilter.biome());
        PlacementUtils.register(context, MOLTEN_SPRING, lookup.getOrThrow(DDResourceKeys.ConfiguredFeatures.MOLTEN_SPRING), CountPlacement.of(20), InSquarePlacement.spread(), HeightRangePlacement.of(VeryBiasedToBottomHeight.of(VerticalAnchor.bottom(), VerticalAnchor.belowTop(32), 32)), BiomeFilter.biome());
        PlacementUtils.register(context, MOLTEN_POOL, lookup.getOrThrow(DDResourceKeys.ConfiguredFeatures.MOLTEN_POOL), CountPlacement.of(60), InSquarePlacement.spread(), PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT, EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12), RandomOffsetPlacement.vertical(ConstantInt.of(1)), BiomeFilter.biome());
        PlacementUtils.register(context, DARKSLATE_PLACEMENT, lookup.getOrThrow(DDResourceKeys.ConfiguredFeatures.DARKSLATE_PLACEMENT), CountPlacement.of(175), InSquarePlacement.spread(), PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT, BiomeFilter.biome());
        PlacementUtils.register(context, CATACOMBS_SAND_PLACEMENT, lookup.getOrThrow(DDResourceKeys.ConfiguredFeatures.CATACOMBS_SAND_PLACEMENT), CountPlacement.of(48), InSquarePlacement.spread(), PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT, BiomeFilter.biome());
        PlacementUtils.register(context, CATACOMBS_LAYERED_PLACEMENT, lookup.getOrThrow(DDResourceKeys.ConfiguredFeatures.CATACOMBS_LAYERED_PLACEMENT), CountPlacement.of(150), InSquarePlacement.spread(), PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT, BiomeFilter.biome());
        PlacementUtils.register(context, PETRIFIED_BRANCH, lookup.getOrThrow(DDResourceKeys.ConfiguredFeatures.PETRIFIED_BRANCH), CountPlacement.of(150), InSquarePlacement.spread(), PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT, BiomeFilter.biome());
        PlacementUtils.register(context, DEAD_LIVING_CRYSTAL_ORE, lookup.getOrThrow(DDResourceKeys.ConfiguredFeatures.DEAD_LIVING_CRYSTAL_ORE), CountPlacement.of(256), InSquarePlacement.spread(), PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT, BiomeFilter.biome());
        PlacementUtils.register(context, MAGMA_ORE, lookup.getOrThrow(DDResourceKeys.ConfiguredFeatures.MAGMA_ORE), CountPlacement.of(30), InSquarePlacement.spread(), PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT, BiomeFilter.biome());
        PlacementUtils.register(context, MAGMA_DISK, lookup.getOrThrow(DDResourceKeys.ConfiguredFeatures.MAGMA_DISK), CountPlacement.of(60), InSquarePlacement.spread(), PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT, EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.matchesBlocks(Blocks.AIR, Blocks.CAVE_AIR, Blocks.WATER, Blocks.LAVA), 16));
        PlacementUtils.register(context, GRIME_SURFACE, lookup.getOrThrow(DDResourceKeys.ConfiguredFeatures.GRIME_SURFACE), CountPlacement.of(UniformInt.of(192, 256)), InSquarePlacement.spread(), PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT, BiomeFilter.biome());
        PlacementUtils.register(context, ARID_SURFACE, lookup.getOrThrow(DDResourceKeys.ConfiguredFeatures.ARID_SURFACE), CountPlacement.of(UniformInt.of(192, 256)), InSquarePlacement.spread(), PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT, BiomeFilter.biome());
        PlacementUtils.register(context, DARKSLATE_SURFACE, lookup.getOrThrow(DDResourceKeys.ConfiguredFeatures.DARKSLATE_SURFACE), CountPlacement.of(UniformInt.of(192, 256)), InSquarePlacement.spread(), PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT, BiomeFilter.biome());
        PlacementUtils.register(context, ARID_BOULDER, lookup.getOrThrow(DDResourceKeys.ConfiguredFeatures.ARID_BOULDER), CountPlacement.of(UniformInt.of(192, 256)), InSquarePlacement.spread(), PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT, BiomeFilter.biome());
        PlacementUtils.register(context, DUSKROCK_ORE, lookup.getOrThrow(DDResourceKeys.ConfiguredFeatures.DUSKROCK_ORE), CountPlacement.of(32), InSquarePlacement.spread(), PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT, BiomeFilter.biome());
        PlacementUtils.register(context, CATACOMBS_LAVA_LINING, lookup.getOrThrow(DDResourceKeys.ConfiguredFeatures.CATACOMBS_LAVA_LINING), List.of(CountPlacement.of(96), InSquarePlacement.spread(), HeightRangePlacement.uniform(VerticalAnchor.absolute(-63), VerticalAnchor.absolute(-1)), BlockPredicateFilter.forPredicate(BlockPredicate.not(BlockPredicate.matchesBlocks(Blocks.AIR))), BiomeFilter.biome()));
        PlacementUtils.register(context, DUSKROCK_STRIPE, lookup.getOrThrow(DDResourceKeys.ConfiguredFeatures.DUSKROCK_STRIPE), CountPlacement.of(UniformInt.of(96, 128)), InSquarePlacement.spread(), HeightRangePlacement.uniform(VerticalAnchor.BOTTOM, VerticalAnchor.belowTop(16)), BiomeFilter.biome());
        PlacementUtils.register(context, GLIMMERING_VINES, lookup.getOrThrow(DDResourceKeys.ConfiguredFeatures.GLIMMERING_VINES), CountPlacement.of(125), InSquarePlacement.spread(), PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT, EnvironmentScanPlacement.scanningFor(Direction.UP, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12), RandomOffsetPlacement.vertical(ConstantInt.of(-1)), BiomeFilter.biome());
    }
}
