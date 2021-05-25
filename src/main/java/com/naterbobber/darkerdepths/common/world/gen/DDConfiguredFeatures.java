package com.naterbobber.darkerdepths.common.world.gen;

import com.google.common.collect.ImmutableSet;
import com.naterbobber.darkerdepths.common.math.ConstantIntProvider;
import com.naterbobber.darkerdepths.common.math.UniformIntProvider;
import com.naterbobber.darkerdepths.common.world.gen.feature.BlobReplacementConfig;
import com.naterbobber.darkerdepths.common.world.gen.feature.CavePillarConfig;
import com.naterbobber.darkerdepths.common.world.gen.feature.GemstonePlacementConfig;
import com.naterbobber.darkerdepths.common.world.gen.feature.SimpleBlockConfig;
import com.naterbobber.darkerdepths.common.world.gen.feature.VegetationPatchConfig;
import com.naterbobber.darkerdepths.common.world.gen.placement.CaveDecoratorConfig;
import com.naterbobber.darkerdepths.core.DDRegistries;
import com.naterbobber.darkerdepths.core.DarkerDepths;
import com.naterbobber.darkerdepths.core.registries.DDBlocks;
import com.naterbobber.darkerdepths.core.registries.DDFeatures;
import com.naterbobber.darkerdepths.core.registries.DDPlacements;
import com.naterbobber.darkerdepths.core.util.CaveSurface;
import com.naterbobber.darkerdepths.core.util.DDFillerBlockTypes;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.fluid.Fluids;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Direction;
import net.minecraft.world.gen.blockplacer.SimpleBlockPlacer;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.blockstateprovider.WeightedBlockStateProvider;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placement.DepthAverageConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.placement.TopSolidRangeConfig;

//<>

public class DDConfiguredFeatures {
    public static final DDRegistries HELPER = DarkerDepths.REGISTRY_HELPER;

    public static final ConfiguredFeature<?, ?> GLOWSHROOM_PATCH = HELPER.registerConfiguredFeature("glowshroom_patch", DDFeatures.SIMPLE_BLOCK.get().withConfiguration(new SimpleBlockConfig(new SimpleBlockStateProvider(DDBlocks.GLOWSHROOM.get().getDefaultState()))).withPlacement(DDPlacements.CAVE_SURFACE.get().configure(new CaveDecoratorConfig(CaveSurface.FLOOR, 12))).range(50).square().countSpread(FeatureSpread.create(20, 60)).chance(20));
//    public static final ConfiguredFeature<?, ?> GLOWSHROOM_PATCH = HELPER.registerConfiguredFeature("glowshroom_patch", DDFeatures.SIMPLE_BLOCK_FEATURE.get().withConfiguration(new SimpleBlockConfig(new SimpleBlockStateProvider(DDBlocks.GLOWSHROOM.get().getDefaultState()))).withPlacement(DDPlacements.CAVE_SURFACE.get().configure(new CaveDecoratorConfig(CaveSurface.FLOOR, 12))).range(59).square().countSpread(FeatureSpread.create(10, 10)).chance(25));

    public static final ConfiguredFeature<?, ?> CEILING_CELESTINE_PEAK = HELPER.registerConfiguredFeature("ceiling_celestine_peak", DDFeatures.CAVE_PILLAR_FEATURE.get().withConfiguration(new CavePillarConfig(DDBlocks.CELESTINE_CRYSTAL_BLOCK.get().getDefaultState(), Direction.DOWN))).withPlacement(DDPlacements.CAVE_SURFACE.get().configure(new CaveDecoratorConfig(CaveSurface.CEILING, 12))).chance(10).square().range(50);
    public static final ConfiguredFeature<?, ?> FLOOR_CELESTINE_PEAK = HELPER.registerConfiguredFeature("floor_celestine_peak", DDFeatures.CAVE_PILLAR_FEATURE.get().withConfiguration(new CavePillarConfig(DDBlocks.CELESTINE_CRYSTAL_BLOCK.get().getDefaultState(), Direction.UP))).withPlacement(DDPlacements.CAVE_SURFACE.get().configure(new CaveDecoratorConfig(CaveSurface.FLOOR, 12))).chance(10).square().range(50);
    public static final ConfiguredFeature<?, ?> CEILING_AMETHYST_PEAK = HELPER.registerConfiguredFeature("ceiling_amethyst_peak", DDFeatures.CAVE_PILLAR_FEATURE.get().withConfiguration(new CavePillarConfig(DDBlocks.AMETHYST_CRYSTAL_BLOCK.get().getDefaultState(), Direction.DOWN))).withPlacement(DDPlacements.CAVE_SURFACE.get().configure(new CaveDecoratorConfig(CaveSurface.CEILING, 12))).chance(10).square().range(50);
    public static final ConfiguredFeature<?, ?> FLOOR_AMETHYST_PEAK = HELPER.registerConfiguredFeature("floor_amethyst_peak", DDFeatures.CAVE_PILLAR_FEATURE.get().withConfiguration(new CavePillarConfig(DDBlocks.AMETHYST_CRYSTAL_BLOCK.get().getDefaultState(), Direction.UP))).withPlacement(DDPlacements.CAVE_SURFACE.get().configure(new CaveDecoratorConfig(CaveSurface.FLOOR, 12))).chance(10).square().range(50);

    public static final ConfiguredFeature<?, ?> AMBER_GEMSTONE = HELPER.registerConfiguredFeature("amber_gemstone", DDFeatures.GEMSTONE_PLACEMENT_FEATURE.get().withConfiguration(new GemstonePlacementConfig(DDBlocks.AMBER.get().getDefaultState())).range(25).square().count(60));
    public static final ConfiguredFeature<?, ?> ASH_BLANKET = HELPER.registerConfiguredFeature("ash_blanket", Feature.RANDOM_PATCH.withConfiguration(new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(DDBlocks.ASH.get().getDefaultState()), new SimpleBlockPlacer()).tries(100).preventProjection().build()).count(45).range(55)).square();
    public static final ConfiguredFeature<?, ?> ASH_ORE = HELPER.registerConfiguredFeature("ash_ore", Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, DDBlocks.ASH_BLOCK.get().getDefaultState(), 33)).count(10).square().range(55));

    public static final ConfiguredFeature<?, ?> MOLTEN_CAVERN_LAVA_SPRING = HELPER.registerConfiguredFeature("molten_cavern_lava_spring", Feature.SPRING_FEATURE.withConfiguration(new LiquidsConfig(Fluids.LAVA.getDefaultState(), false, 4, 1, ImmutableSet.of(Blocks.STONE, Blocks.GRANITE, Blocks.DIORITE, Blocks.ANDESITE, Blocks.MAGMA_BLOCK, DDBlocks.SHALE.get()))).withPlacement(Placement.RANGE_VERY_BIASED.configure(new TopSolidRangeConfig(8, 16, 55)).square().count(40)));
    public static final ConfiguredFeature<?, ?> MAGMA_FLOOR = HELPER.registerConfiguredFeature("magma_floor", Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, Blocks.MAGMA_BLOCK.getDefaultState(), 15)).count(25).withPlacement(Placement.RANGE.configure(new TopSolidRangeConfig(10, 7, 13))).square());
    public static final ConfiguredFeature<?, ?> MAGMA_ORE = HELPER.registerConfiguredFeature("magma_ore", Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, Blocks.MAGMA_BLOCK.getDefaultState(), 15)).count(12).withPlacement(Placement.RANGE.configure(new TopSolidRangeConfig(5, 5, 55))).square());
    public static final ConfiguredFeature<?, ?> SHALE_ORE = HELPER.registerConfiguredFeature("shale_ore", Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, DDBlocks.SHALE.get().getDefaultState(), 64)).count(7).withPlacement(Placement.RANGE.configure(new TopSolidRangeConfig(5, 5, 55))).square());
    public static final ConfiguredFeature<?, ?> MOLTEN_CAVERN_ORE_COAL = HELPER.registerConfiguredFeature("molten_cavern_coal_ore", Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, Blocks.COAL_ORE.getDefaultState(), 17)).count(20).square().range(80));
    public static final ConfiguredFeature<?, ?> MOLTEN_CAVERN_ORE_DIAMOND = HELPER.registerConfiguredFeature("molten_cavern_ore_diamond", Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, Blocks.DIAMOND_ORE.getDefaultState(), 1)).range(16).square());
    public static final ConfiguredFeature<?, ?> SHALE_STONE_REPLACEMENT = HELPER.registerConfiguredFeature("shale_stone_replacement", DDFeatures.BLOB_REPLACEMENT_FEATURE.get().withConfiguration(new BlobReplacementConfig(ImmutableSet.of(Blocks.STONE.getDefaultState(), Blocks.DIORITE.getDefaultState(), Blocks.GRANITE.getDefaultState(), Blocks.GRAVEL.getDefaultState(), Blocks.DIRT.getDefaultState()), DDBlocks.SHALE.get().getDefaultState(), FeatureSpread.create(3, 4))).range(55).square().count(100));

    public static final ConfiguredFeature<?, ?> ARIDROCK_ORE_COAL = HELPER.registerConfiguredFeature("aridrock_coal_ore", Feature.ORE.withConfiguration(new OreFeatureConfig(DDFillerBlockTypes.ARIDROCK, DDBlocks.ARIDROCK_COAL_ORE.get().getDefaultState(), 17)).count(128).square().range(80));
    public static final ConfiguredFeature<?, ?> ARIDROCK_ORE_IRON = HELPER.registerConfiguredFeature("aridrock_ore_iron", Feature.ORE.withConfiguration(new OreFeatureConfig(DDFillerBlockTypes.ARIDROCK, DDBlocks.ARIDROCK_IRON_ORE.get().getDefaultState(), 9)).range(64).square().count(80));
    public static final ConfiguredFeature<?, ?> ARIDROCK_ORE_GOLD = HELPER.registerConfiguredFeature("aridrock_ore_gold", Feature.ORE.withConfiguration(new OreFeatureConfig(DDFillerBlockTypes.ARIDROCK, DDBlocks.ARIDROCK_GOLD_ORE.get().getDefaultState(), 9)).range(32).square().count(8));
    public static final ConfiguredFeature<?, ?> ARIDROCK_ORE_REDSTONE = HELPER.registerConfiguredFeature("aridrock_ore_redstone", Feature.ORE.withConfiguration(new OreFeatureConfig(DDFillerBlockTypes.ARIDROCK, DDBlocks.ARIDROCK_REDSTONE_ORE.get().getDefaultState(), 8)).range(16).square().count(32));
    public static final ConfiguredFeature<?, ?> ARIDROCK_ORE_DIAMOND = HELPER.registerConfiguredFeature("aridrock_ore_diamond", Feature.ORE.withConfiguration(new OreFeatureConfig(DDFillerBlockTypes.ARIDROCK, DDBlocks.ARIDROCK_DIAMOND_ORE.get().getDefaultState(), 8)).range(16).square());
    public static final ConfiguredFeature<?, ?> ARIDROCK_ORE_SILVER = HELPER.registerConfiguredFeature("aridrock_ore_silver", Feature.ORE.withConfiguration(new OreFeatureConfig(DDFillerBlockTypes.ARIDROCK, DDBlocks.ARIDROCK_SILVER_ORE.get().getDefaultState(), 4)).range(16).square());
    public static final ConfiguredFeature<?, ?> ARIDROCK_ORE_LAPIS = HELPER.registerConfiguredFeature("aridrock_ore_lapis", Feature.ORE.withConfiguration(new OreFeatureConfig(DDFillerBlockTypes.ARIDROCK, DDBlocks.ARIDROCK_LAPIS_ORE.get().getDefaultState(), 7)).withPlacement(Placement.DEPTH_AVERAGE.configure(new DepthAverageConfig(16, 16))).square());

    public static final ConfiguredFeature<?, ?> LIMESTONE_ORE_COAL = HELPER.registerConfiguredFeature("limestone_coal_ore", Feature.ORE.withConfiguration(new OreFeatureConfig(DDFillerBlockTypes.LIMESTONE, DDBlocks.LIMESTONE_COAL_ORE.get().getDefaultState(), 17)).count(128).square().range(80));
    public static final ConfiguredFeature<?, ?> LIMESTONE_ORE_IRON = HELPER.registerConfiguredFeature("limestone_ore_iron", Feature.ORE.withConfiguration(new OreFeatureConfig(DDFillerBlockTypes.LIMESTONE, DDBlocks.LIMESTONE_IRON_ORE.get().getDefaultState(), 9)).range(64).square().count(80));
    public static final ConfiguredFeature<?, ?> LIMESTONE_ORE_GOLD = HELPER.registerConfiguredFeature("limestone_ore_gold", Feature.ORE.withConfiguration(new OreFeatureConfig(DDFillerBlockTypes.LIMESTONE, DDBlocks.LIMESTONE_GOLD_ORE.get().getDefaultState(), 9)).range(32).square().count(8));
    public static final ConfiguredFeature<?, ?> LIMESTONE_ORE_REDSTONE = HELPER.registerConfiguredFeature("limestone_ore_redstone", Feature.ORE.withConfiguration(new OreFeatureConfig(DDFillerBlockTypes.LIMESTONE, DDBlocks.LIMESTONE_REDSTONE_ORE.get().getDefaultState(), 8)).range(16).square().count(32));
    public static final ConfiguredFeature<?, ?> LIMESTONE_ORE_DIAMOND = HELPER.registerConfiguredFeature("limestone_ore_diamond", Feature.ORE.withConfiguration(new OreFeatureConfig(DDFillerBlockTypes.LIMESTONE, DDBlocks.LIMESTONE_DIAMOND_ORE.get().getDefaultState(), 8)).range(16).square());
    public static final ConfiguredFeature<?, ?> LIMESTONE_ORE_SILVER = HELPER.registerConfiguredFeature("limestone_ore_silver", Feature.ORE.withConfiguration(new OreFeatureConfig(DDFillerBlockTypes.LIMESTONE, DDBlocks.LIMESTONE_SILVER_ORE.get().getDefaultState(), 4)).range(16).square());
    public static final ConfiguredFeature<?, ?> LIMESTONE_ORE_LAPIS = HELPER.registerConfiguredFeature("limestone_ore_lapis", Feature.ORE.withConfiguration(new OreFeatureConfig(DDFillerBlockTypes.LIMESTONE, DDBlocks.LIMESTONE_LAPIS_ORE.get().getDefaultState(), 7)).withPlacement(Placement.DEPTH_AVERAGE.configure(new DepthAverageConfig(16, 16))).square());

    public static final ConfiguredFeature<?, ?> ARIDROCK_STONE_REPLACEMENT      = HELPER.registerConfiguredFeature("aridrock_stone_replacement", DDFeatures.BLOB_REPLACEMENT_FEATURE.get().withConfiguration(new BlobReplacementConfig(ImmutableSet.of(Blocks.STONE.getDefaultState(), Blocks.DIORITE.getDefaultState(), Blocks.ANDESITE.getDefaultState(), Blocks.GRANITE.getDefaultState(), Blocks.GRAVEL.getDefaultState(), Blocks.DIRT.getDefaultState(), Blocks.COAL_ORE.getDefaultState(), Blocks.IRON_ORE.getDefaultState(), Blocks.GOLD_ORE.getDefaultState(), Blocks.REDSTONE_ORE.getDefaultState(), Blocks.DIAMOND_ORE.getDefaultState(), Blocks.LAPIS_ORE.getDefaultState()), DDBlocks.ARIDROCK.get().getDefaultState(), FeatureSpread.create(3, 4))).range(55).square().count(250));
    public static final ConfiguredFeature<?, ?> LIMESTONE_STONE_REPLACEMENT     = HELPER.registerConfiguredFeature("limestone_stone_replacement", DDFeatures.BLOB_REPLACEMENT_FEATURE.get().withConfiguration(new BlobReplacementConfig(ImmutableSet.of(Blocks.STONE.getDefaultState(), Blocks.DIORITE.getDefaultState(), Blocks.ANDESITE.getDefaultState(), Blocks.GRANITE.getDefaultState(), Blocks.GRAVEL.getDefaultState(), Blocks.DIRT.getDefaultState(), Blocks.COAL_ORE.getDefaultState(), Blocks.IRON_ORE.getDefaultState(), Blocks.GOLD_ORE.getDefaultState(), Blocks.REDSTONE_ORE.getDefaultState(), Blocks.DIAMOND_ORE.getDefaultState(), Blocks.LAPIS_ORE.getDefaultState(), DDBlocks.ARIDROCK_COAL_ORE.get().getDefaultState(), DDBlocks.ARIDROCK_IRON_ORE.get().getDefaultState(), DDBlocks.ARIDROCK_GOLD_ORE.get().getDefaultState(), DDBlocks.ARIDROCK_REDSTONE_ORE.get().getDefaultState(), DDBlocks.ARIDROCK_DIAMOND_ORE.get().getDefaultState(), DDBlocks.ARIDROCK_LAPIS_ORE.get().getDefaultState(), DDBlocks.ARIDROCK.get().getDefaultState()), DDBlocks.LIMESTONE.get().getDefaultState(), FeatureSpread.create(3, 4))).range(16).square().count(250));

    public static final ConfiguredFeature<?, ?> LIMESTONE_ORE = HELPER.registerConfiguredFeature("limestone_ore", Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, DDBlocks.LIMESTONE.get().getDefaultState(), 33)).range(60).count(20).square());
    public static final ConfiguredFeature<?, ?> SAND_ORE = HELPER.registerConfiguredFeature("sand_ore", Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, Blocks.SAND.getDefaultState(), 33)).withPlacement(Placement.RANGE.configure(new TopSolidRangeConfig(15, 0, 60))).count(20).square());

    public static final ConfiguredFeature<?, ?> CAVE_FOSSILS_FEATURE = HELPER.registerConfiguredFeature("cave_fossils_feature", DDFeatures.CAVE_FOSSILS.get().withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).chance(5).range(40).square());
    public static final ConfiguredFeature<?, ?> DEAD_BUSH_FEATURE = HELPER.registerConfiguredFeature("dead_bush_feature", Feature.RANDOM_PATCH.withConfiguration(new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(Blocks.DEAD_BUSH.getDefaultState()), new SimpleBlockPlacer()).tries(32).whitelist(ImmutableSet.of(DDBlocks.ARIDROCK.get(), Blocks.SAND, DDBlocks.LIMESTONE.get())).preventProjection().build()).count(8).range(60).square());
//    public static final ConfiguredFeature<?, ?> ROOTS_FEATURE = HELPER.registerConfiguredFeature("roots_feature", DDFeatures.SIMPLE_BLOCK.get().withConfiguration(new SimpleBlockConfig(new WeightedBlockStateProvider().addWeightedBlockstate(DDBlocks.ROOTS.get().getDefaultState(), 10).addWeightedBlockstate(DDBlocks.LONG_ROOTS.get().getDefaultState(), 7))).withPlacement(DDPlacements.CAVE_SURFACE.get().configure(new CaveDecoratorConfig(CaveSurface.CEILING, 12))).range(70).square().count(40));
    public static final ConfiguredFeature<?, ?> ROOTS_FEATURE = HELPER.registerConfiguredFeature("roots_feature", DDFeatures.SIMPLE_BLOCK.get().withConfiguration(new SimpleBlockConfig(new SimpleBlockStateProvider(DDBlocks.ROOTS.get().getDefaultState()))).withPlacement(DDPlacements.CAVE_SURFACE.get().configure(new CaveDecoratorConfig(CaveSurface.CEILING, 12))).range(70).square().count(20));
    public static final ConfiguredFeature<?, ?> LONG_ROOTS_FEATURE = HELPER.registerConfiguredFeature("long_roots_feature", DDFeatures.SIMPLE_BLOCK.get().withConfiguration(new SimpleBlockConfig(new SimpleBlockStateProvider(DDBlocks.LONG_ROOTS.get().getDefaultState()))).withPlacement(DDPlacements.CAVE_SURFACE.get().configure(new CaveDecoratorConfig(CaveSurface.CEILING, 12))).range(70).square().count(20));

    public static final ConfiguredFeature<?, ?> ASH_VEGETATION = HELPER.registerConfiguredFeature("ash_vegetation", DDFeatures.SIMPLE_BLOCK.get().withConfiguration(new SimpleBlockConfig(new WeightedBlockStateProvider().addWeightedBlockstate(States.ASH_LAYER, 25).addWeightedBlockstate(States.ASH_LAYER_DOUBLE, 15).addWeightedBlockstate(States.ASH_LAYER_TRIPLE, 10).addWeightedBlockstate(States.ASH_LAYER_QUADRUPLE, 5))));
    public static final ConfiguredFeature<?, ?> ASH_PATCH = DDFeatures.VEGETATION_PATCH.get().withConfiguration(new VegetationPatchConfig(BlockTags.BASE_STONE_OVERWORLD.getName(), new SimpleBlockStateProvider(DDBlocks.ASH_BLOCK.get().getDefaultState()), () -> {
        return ASH_VEGETATION;
    }, CaveSurface.FLOOR, ConstantIntProvider.create(1), 0.0f, 5, 0.8f, UniformIntProvider.create(4, 7), 0.3f));
    public static final ConfiguredFeature<?, ?> MOLTEN_CAVE_VEGETATION = HELPER.registerConfiguredFeature("molten_cave_vegetation", ASH_PATCH.withPlacement(DDPlacements.CAVE_SURFACE.get().configure(new CaveDecoratorConfig(CaveSurface.FLOOR, 12))).range(50).square().count(20));

    public static final ConfiguredFeature<?, ?> LAVA_POOL_PATCH = HELPER.registerConfiguredFeature("lava_pool_patch", DDFeatures.LAVA_VEGETATION_PATCH.get().withConfiguration(new VegetationPatchConfig(BlockTags.BASE_STONE_OVERWORLD.getName(), new SimpleBlockStateProvider(DDBlocks.SHALE.get().getDefaultState()), () -> {
        return Feature.NO_OP.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG);
    }, CaveSurface.FLOOR, ConstantIntProvider.create(3), 0.8f, 5, 0.01f, UniformIntProvider.create(4, 7), 0.7f))).withPlacement(DDPlacements.CAVE_SURFACE.get().configure(new CaveDecoratorConfig(CaveSurface.FLOOR, 12))).range(32).square().count(10);

    static class States {
        public static final BlockState ASH_LAYER = DDBlocks.ASH.get().getDefaultState().with(BlockStateProperties.LAYERS_1_8, 1);
        public static final BlockState ASH_LAYER_DOUBLE = DDBlocks.ASH.get().getDefaultState().with(BlockStateProperties.LAYERS_1_8, 2);
        public static final BlockState ASH_LAYER_TRIPLE = DDBlocks.ASH.get().getDefaultState().with(BlockStateProperties.LAYERS_1_8, 3);
        public static final BlockState ASH_LAYER_QUADRUPLE = DDBlocks.ASH.get().getDefaultState().with(BlockStateProperties.LAYERS_1_8, 4);
    }
}