package com.naterbobber.darkerdepths.init;

import com.google.common.collect.ImmutableList;
import com.naterbobber.darkerdepths.DarkerDepths;
import com.naterbobber.darkerdepths.blocks.AshBlock;
import com.naterbobber.darkerdepths.blocks.GlowshroomBlock;
import com.naterbobber.darkerdepths.blocks.GlowspursBlock;
import com.naterbobber.darkerdepths.world.gen.features.config.CorrespondentLayersConfig;
import com.naterbobber.darkerdepths.world.gen.features.config.PetrifiedBranchConfig;
import com.naterbobber.darkerdepths.world.gen.features.config.ReplaceListConfig;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.util.valueproviders.WeightedListInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.BlockColumnConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomBooleanFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SpringConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.VegetationPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import net.minecraft.world.level.levelgen.placement.CaveSurface;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;
import net.minecraft.world.level.material.Fluids;

import java.util.List;

public class DDConfiguredFeatures {

    public static final ImmutableList<BlockState> OVERWORLD_REPLACEABLES    = ImmutableList.of(Blocks.STONE.defaultBlockState(), Blocks.ANDESITE.defaultBlockState(), Blocks.GRANITE.defaultBlockState(), Blocks.DIORITE.defaultBlockState(), Blocks.DIRT.defaultBlockState(), DDBlocks.SHALE.get().defaultBlockState(), DDBlocks.ARIDROCK.get().defaultBlockState(), DDBlocks.LIMESTONE.get().defaultBlockState(), Blocks.GRAVEL.defaultBlockState(), Blocks.DEEPSLATE.defaultBlockState(), Blocks.TUFF.defaultBlockState());

    public static final ResourceKey<ConfiguredFeature<?, ?>> GLOWSHROOM_PATCH = createKey("glowshroom_patch");
    public static final ResourceKey<ConfiguredFeature<?, ?>> HUGE_GLOWSHROOM = createKey("huge_glowshroom");
    public static final ResourceKey<ConfiguredFeature<?, ?>> HUGE_GLOWSHROOM_PLANTED = createKey("huge_glowshroom_planted");
    public static final ResourceKey<ConfiguredFeature<?, ?>> AMBERS_PLACEMENT = createKey("amber_placement");
    public static final ResourceKey<ConfiguredFeature<?, ?>> MOLTEN_SPRING = createKey("molten_spring");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ASH_PLACEMENTS = createKey("ash_placement");
    public static final ResourceKey<ConfiguredFeature<?, ?>> GEYSER_PLACEMENT = createKey("geyser_placement");
    public static final ResourceKey<ConfiguredFeature<?, ?>> MOLTEN_POOL = createKey("molten_pool");
    public static final ResourceKey<ConfiguredFeature<?, ?>> GRIME_VEGETATION = createKey("grime_vegetation");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ARID_VEGETATION = createKey("arid_vegetation");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SHALE_VEGETATION = createKey("shale_vegetation");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SHALE_PLACEMENT = createKey("shale_placement");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SHORT_PETRIFIED_BRANCH = createKey("short_petrified_branch");
    public static final ResourceKey<ConfiguredFeature<?, ?>> LONG_PETRIFIED_BRANCH = createKey("long_petrified_branch");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PETRIFIED_BRANCH = createKey("petrified_branch");
    public static final ResourceKey<ConfiguredFeature<?, ?>> MAGMA_ORE = createKey("magma_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SILVER_ORE = createKey("silver_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SHALE_SURFACE = createKey("shale_surface");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ARID_SURFACE = createKey("arid_surface");
    public static final ResourceKey<ConfiguredFeature<?, ?>> GRIME_SURFACE = createKey("grime_surface");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ARID_BOULDER = createKey("arid_boulder");
    public static final ResourceKey<ConfiguredFeature<?, ?>> GLIMMERING_VINES = createKey("glimmering_vines");

    public static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> context) {
        HolderGetter<ConfiguredFeature<?, ?>> lookup = context.lookup(Registries.CONFIGURED_FEATURE);
        FeatureUtils.register(context, GLOWSHROOM_PATCH, DDFeatures.RANDOM_GLOWSHROOM_PATCHES.get(), FeatureConfiguration.NONE);
        FeatureUtils.register(context, HUGE_GLOWSHROOM, DDFeatures.HUGE_GLOWSHROOM.get(), FeatureConfiguration.NONE);
        FeatureUtils.register(context, HUGE_GLOWSHROOM_PLANTED, DDFeatures.HUGE_GLOWSHROOM.get(), FeatureConfiguration.NONE);
        FeatureUtils.register(context, AMBERS_PLACEMENT, DDFeatures.GEMSTONE.get(), FeatureConfiguration.NONE);
        FeatureUtils.register(context, MOLTEN_SPRING, Feature.SPRING, new SpringConfiguration(Fluids.LAVA.defaultFluidState(), false, 4, 1, HolderSet.direct(Block::builtInRegistryHolder, Blocks.STONE, Blocks.GRANITE, Blocks.DIORITE, Blocks.ANDESITE, Blocks.DEEPSLATE, Blocks.TUFF, Blocks.CALCITE, Blocks.DIRT, DDBlocks.SHALE.get())));
        FeatureUtils.register(context, ASH_PLACEMENTS, Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder().add(DDBlocks.ASH.get().defaultBlockState().setValue(AshBlock.LAYERS, 1), 25).add(DDBlocks.ASH.get().defaultBlockState().setValue(AshBlock.LAYERS, 2), 15).build())));
        FeatureUtils.register(context, GEYSER_PLACEMENT, Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(DDBlocks.GEYSER.get().defaultBlockState())));
        FeatureUtils.register(context, MOLTEN_POOL, DDFeatures.LAVA_VEGETATION_PATCH_FEATURE.get(), new VegetationPatchConfiguration(BlockTags.BASE_STONE_OVERWORLD, BlockStateProvider.simple(DDBlocks.ASH_BLOCK.get().defaultBlockState()), PlacementUtils.inlinePlaced(lookup.getOrThrow(GEYSER_PLACEMENT)), CaveSurface.FLOOR, ConstantInt.of(1), 0.0F, 5, 0.8F, UniformInt.of(4, 7), 0.3F));
        FeatureUtils.register(context, GRIME_VEGETATION, Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder().add(DDBlocks.GLOWSHROOM.get().defaultBlockState().setValue(GlowshroomBlock.CLUSTERS_1_3, 1), 3).add(DDBlocks.GLOWSHROOM.get().defaultBlockState().setValue(GlowshroomBlock.CLUSTERS_1_3, 2), 1).add(DDBlocks.GLOWSHROOM.get().defaultBlockState().setValue(GlowshroomBlock.CLUSTERS_1_3, 3), 1).add(DDBlocks.GLOWSPURS.get().defaultBlockState().setValue(GlowspursBlock.FACING, Direction.NORTH), 1).add(DDBlocks.GLOWSPURS.get().defaultBlockState().setValue(GlowspursBlock.FACING, Direction.EAST), 1).add(DDBlocks.GLOWSPURS.get().defaultBlockState().setValue(GlowspursBlock.FACING, Direction.SOUTH), 1).add(DDBlocks.GLOWSPURS.get().defaultBlockState().setValue(GlowspursBlock.FACING, Direction.WEST), 1).add(DDBlocks.MOSSY_SPROUTS.get().defaultBlockState(), 30).add(Blocks.AIR.defaultBlockState(), 100))));
        FeatureUtils.register(context, ARID_VEGETATION, Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder().add(DDBlocks.DRY_SPROUTS.get().defaultBlockState(), 3).add(Blocks.AIR.defaultBlockState(), 60).build())));
        FeatureUtils.register(context, SHALE_VEGETATION, Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder().add(Blocks.AIR.defaultBlockState(), 60).build())));
        FeatureUtils.register(context, SHALE_PLACEMENT, DDFeatures.REPLACE_LIST.get(), new ReplaceListConfig(OVERWORLD_REPLACEABLES, DDBlocks.SHALE.get().defaultBlockState(), UniformInt.of(6, 7)));
        FeatureUtils.register(context, SHORT_PETRIFIED_BRANCH, DDFeatures.PETRIFIED_BRANCH.get(), new PetrifiedBranchConfig(4, 8));
        FeatureUtils.register(context, LONG_PETRIFIED_BRANCH, DDFeatures.PETRIFIED_BRANCH.get(), new PetrifiedBranchConfig(8, 16));
        FeatureUtils.register(context, PETRIFIED_BRANCH, Feature.RANDOM_BOOLEAN_SELECTOR, new RandomBooleanFeatureConfiguration(PlacementUtils.inlinePlaced(lookup.getOrThrow(SHORT_PETRIFIED_BRANCH)), PlacementUtils.inlinePlaced(lookup.getOrThrow(LONG_PETRIFIED_BRANCH))));
        FeatureUtils.register(context, MAGMA_ORE, Feature.ORE, new OreConfiguration(new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES), Blocks.MAGMA_BLOCK.defaultBlockState(), 15));
        FeatureUtils.register(context, SHALE_SURFACE, DDFeatures.CORRESPONDENT_LAYER.get(), new CorrespondentLayersConfig(BlockTags.LUSH_GROUND_REPLACEABLE, BlockStateProvider.simple(DDBlocks.SHALE.get()), BlockStateProvider.simple(DDBlocks.SHALE.get()), PlacementUtils.inlinePlaced(lookup.getOrThrow(SHALE_VEGETATION)), CaveSurface.FLOOR, ConstantInt.of(1), 0.0F, 5, 0.8F, UniformInt.of(4, 7), 0.3F, false));
        FeatureUtils.register(context, ARID_SURFACE, DDFeatures.CORRESPONDENT_LAYER.get(), new CorrespondentLayersConfig(BlockTags.LUSH_GROUND_REPLACEABLE, BlockStateProvider.simple(DDBlocks.ARIDROCK.get()), BlockStateProvider.simple(DDBlocks.ARID_DEEPSLATE.get()), PlacementUtils.inlinePlaced(lookup.getOrThrow(ARID_VEGETATION)), CaveSurface.FLOOR, ConstantInt.of(1), 0.0F, 5, 0.8F, UniformInt.of(4, 7), 0.3F, true));
        FeatureUtils.register(context, GRIME_SURFACE, DDFeatures.CORRESPONDENT_LAYER.get(), new CorrespondentLayersConfig(BlockTags.LUSH_GROUND_REPLACEABLE, BlockStateProvider.simple(DDBlocks.MOSSY_GRIMESTONE.get()), BlockStateProvider.simple(DDBlocks.GRIMESTONE.get()), PlacementUtils.inlinePlaced(lookup.getOrThrow(GRIME_VEGETATION)), CaveSurface.FLOOR, ConstantInt.of(1), 0.0F, 5, 0.8F, UniformInt.of(4, 7), 0.3F, false));
        FeatureUtils.register(context, ARID_BOULDER, DDFeatures.ARID_BOULDER.get(), FeatureConfiguration.NONE);
        FeatureUtils.register(context, GLIMMERING_VINES, Feature.BLOCK_COLUMN, new BlockColumnConfiguration(List.of(BlockColumnConfiguration.layer(new WeightedListInt(SimpleWeightedRandomList.<IntProvider>builder().add(UniformInt.of(0, 19), 2).add(UniformInt.of(0, 2), 3).add(UniformInt.of(0, 6), 10).build()), BlockStateProvider.simple(DDBlocks.GLIMMERING_VINE_PLANT.get().defaultBlockState())), BlockColumnConfiguration.layer(ConstantInt.of(1), BlockStateProvider.simple(DDBlocks.GLIMMERING_VINES.get().defaultBlockState()))), Direction.DOWN, BlockPredicate.ONLY_IN_AIR_PREDICATE, true));
    }

    public static ResourceKey<ConfiguredFeature<?, ?>> createKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, DarkerDepths.id(name));
    }

}
