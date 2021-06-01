package com.naterbobber.darkerdepths.common.world.gen;

import com.google.common.collect.ImmutableSet;
import com.naterbobber.darkerdepths.common.math.ConstantIntProvider;
import com.naterbobber.darkerdepths.common.math.UniformIntProvider;
import com.naterbobber.darkerdepths.common.world.gen.feature.BlobReplacementConfig;
import com.naterbobber.darkerdepths.common.world.gen.feature.CavePillarConfig;
import com.naterbobber.darkerdepths.common.world.gen.feature.GemstonePlacementConfig;
import com.naterbobber.darkerdepths.common.world.gen.feature.HugeGlowshroomConfig;
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
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.blockstateprovider.WeightedBlockStateProvider;
import net.minecraft.world.gen.feature.BlockStateProvidingFeatureConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureSpread;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.LiquidsConfig;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.placement.DepthAverageConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.placement.TopSolidRangeConfig;

//<>

public class DDConfiguredFeatures {
    public static final DDRegistries HELPER = DarkerDepths.REGISTRIES;

    public static final ConfiguredFeature<?, ?> GLOWSHROOM_PATCH = HELPER.registerConfiguredFeature("glowshroom_patch", DDFeatures.SIMPLE_BLOCK.get().withConfiguration(new SimpleBlockConfig(new SimpleBlockStateProvider(DDBlocks.GLOWSHROOM.get().getDefaultState()))).withPlacement(DDPlacements.CAVE_SURFACE.get().configure(new CaveDecoratorConfig(CaveSurface.FLOOR, 12))).range(50).square().countSpread(FeatureSpread.create(20, 60)).chance(20));
//    public static final ConfiguredFeature<?, ?> GLOWSHROOM_PATCH = HELPER.registerConfiguredFeature("glowshroom_patch", DDFeatures.SIMPLE_BLOCK_FEATURE.get().withConfiguration(new SimpleBlockConfig(new SimpleBlockStateProvider(DDBlocks.GLOWSHROOM.get().getDefaultState()))).withPlacement(DDPlacements.CAVE_SURFACE.get().configure(new CaveDecoratorConfig(CaveSurface.FLOOR, 12))).range(59).square().countSpread(FeatureSpread.create(10, 10)).chance(25));

    public static final ConfiguredFeature<?, ?> CEILING_CELESTINE_PEAK = HELPER.registerConfiguredFeature("ceiling_celestine_peak", DDFeatures.CAVE_PILLAR_FEATURE.get().withConfiguration(new CavePillarConfig(DDBlocks.CELESTINE_CRYSTAL_BLOCK.get().getDefaultState(), Direction.DOWN))).withPlacement(DDPlacements.CAVE_SURFACE.get().configure(new CaveDecoratorConfig(CaveSurface.CEILING, 12))).chance(10).square().range(50);
    public static final ConfiguredFeature<?, ?> FLOOR_CELESTINE_PEAK = HELPER.registerConfiguredFeature("floor_celestine_peak", DDFeatures.CAVE_PILLAR_FEATURE.get().withConfiguration(new CavePillarConfig(DDBlocks.CELESTINE_CRYSTAL_BLOCK.get().getDefaultState(), Direction.UP))).withPlacement(DDPlacements.CAVE_SURFACE.get().configure(new CaveDecoratorConfig(CaveSurface.FLOOR, 12))).chance(10).square().range(50);
    public static final ConfiguredFeature<?, ?> CEILING_AMETHYST_PEAK = HELPER.registerConfiguredFeature("ceiling_amethyst_peak", DDFeatures.CAVE_PILLAR_FEATURE.get().withConfiguration(new CavePillarConfig(DDBlocks.AMETHYST_CRYSTAL_BLOCK.get().getDefaultState(), Direction.DOWN))).withPlacement(DDPlacements.CAVE_SURFACE.get().configure(new CaveDecoratorConfig(CaveSurface.CEILING, 12))).chance(10).square().range(50);
    public static final ConfiguredFeature<?, ?> FLOOR_AMETHYST_PEAK = HELPER.registerConfiguredFeature("floor_amethyst_peak", DDFeatures.CAVE_PILLAR_FEATURE.get().withConfiguration(new CavePillarConfig(DDBlocks.AMETHYST_CRYSTAL_BLOCK.get().getDefaultState(), Direction.UP))).withPlacement(DDPlacements.CAVE_SURFACE.get().configure(new CaveDecoratorConfig(CaveSurface.FLOOR, 12))).chance(10).square().range(50);

    public static final ConfiguredFeature<?, ?> AMBER_GEMSTONE = HELPER.registerConfiguredFeature("amber_gemstone", DDFeatures.GEMSTONE_PLACEMENT_FEATURE.get().withConfiguration(new GemstonePlacementConfig(DDBlocks.AMBER.get().getDefaultState())).range(25).square().count(60));

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
    public static final ConfiguredFeature<?, ?> COBBLED_SANDSTONE_ORE = HELPER.registerConfiguredFeature("cobbled_sandstone_ore", Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, DDBlocks.COBBLED_SANDSTONE.get().getDefaultState(), 33)).withPlacement(Placement.RANGE.configure(new TopSolidRangeConfig(15, 0, 60))).count(20).square());


    public static final ConfiguredFeature<?, ?> ASH_VEGETATION = HELPER.registerConfiguredFeature("ash_vegetation", DDFeatures.SIMPLE_BLOCK.get().withConfiguration(new SimpleBlockConfig(new WeightedBlockStateProvider().addWeightedBlockstate(States.ASH_LAYER, 25).addWeightedBlockstate(States.ASH_LAYER_DOUBLE, 15).addWeightedBlockstate(States.ASH_LAYER_TRIPLE, 10).addWeightedBlockstate(States.ASH_LAYER_QUADRUPLE, 5))));
    public static final ConfiguredFeature<?, ?> ASH_PATCH = DDFeatures.VEGETATION_PATCH.get().withConfiguration(new VegetationPatchConfig(BlockTags.BASE_STONE_OVERWORLD.getName(), new SimpleBlockStateProvider(DDBlocks.ASH_BLOCK.get().getDefaultState()), () -> {
        return ASH_VEGETATION;
    }, CaveSurface.FLOOR, ConstantIntProvider.create(1), 0.0f, 5, 0.8f, UniformIntProvider.create(4, 7), 0.3f));
    public static final ConfiguredFeature<?, ?> MOLTEN_CAVE_VEGETATION = HELPER.registerConfiguredFeature("molten_cave_vegetation", ASH_PATCH.withPlacement(DDPlacements.CAVE_SURFACE.get().configure(new CaveDecoratorConfig(CaveSurface.FLOOR, 12))).range(50).square().count(20));

    public static final ConfiguredFeature<?, ?> LAVA_POOL_PATCH = HELPER.registerConfiguredFeature("lava_pool_patch", DDFeatures.LAVA_VEGETATION_PATCH.get().withConfiguration(new VegetationPatchConfig(BlockTags.BASE_STONE_OVERWORLD.getName(), new SimpleBlockStateProvider(DDBlocks.SHALE.get().getDefaultState()), () -> {
        return Feature.NO_OP.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG);
    }, CaveSurface.FLOOR, ConstantIntProvider.create(3), 0.8f, 5, 0.01f, UniformIntProvider.create(4, 7), 0.7f))).withPlacement(DDPlacements.CAVE_SURFACE.get().configure(new CaveDecoratorConfig(CaveSurface.FLOOR, 12))).range(32).square().count(10);

    public static final ConfiguredFeature<?, ?> PETRIFIED_LOG_BRANCH = HELPER.registerConfiguredFeature("petrified_log_branch", DDFeatures.PETRIFIED_LOG_BRANCH.get().withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).withPlacement(DDPlacements.CAVE_SURFACE.get().configure(new CaveDecoratorConfig(CaveSurface.CEILING, 12))).range(32).square().count(8));

    public static final ConfiguredFeature<?, ?> OASIS_POOL = HELPER.registerConfiguredFeature("oasis_pool", DDFeatures.WATERLOGGED_VEGETATION_PATCH.get().withConfiguration(new VegetationPatchConfig(BlockTags.BASE_STONE_OVERWORLD.getName(), new SimpleBlockStateProvider(DDBlocks.LUSH_ARIDROCK.get().getDefaultState()), () -> {
        return Feature.NO_OP.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG);
    }, CaveSurface.FLOOR, ConstantIntProvider.create(1), 0.0f, 5, 0.01f, UniformIntProvider.create(8, 14), 1.0f))).withPlacement(DDPlacements.CAVE_SURFACE.get().configure(new CaveDecoratorConfig(CaveSurface.FLOOR, 12))).withPlacement(Placement.RANGE.configure(new TopSolidRangeConfig(15, 0, 50))).square().count(10).chance(64);
//    }, CaveSurface.FLOOR, ConstantIntProvider.create(1), 0.0f, 5, 0.01f, UniformIntProvider.create(4, 7), 1.0f))).withPlacement(DDPlacements.CAVE_SURFACE.get().configure(new CaveDecoratorConfig(CaveSurface.FLOOR, 12))).range(50).square().count(5).chance(45);

    public static final ConfiguredFeature<?, ?> OASIS_VEGETATION = HELPER.registerConfiguredFeature("oasis_vegetation", DDFeatures.VEGETATION_FEATURE.get().withConfiguration(Configs.OASIS_VEGETATION_CONFIG).range(50).square().count(60));

    public static final ConfiguredFeature<?, ?> CAVE_FOSSILS_FEATURE = HELPER.registerConfiguredFeature("cave_fossils_feature", DDFeatures.CAVE_FOSSILS.get().withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).chance(5).range(40).square());

    public static final ConfiguredFeature<?, ?> SANDY_CATACOMBS_VEGETATION = HELPER.registerConfiguredFeature("sandy_catacombs_vegetation", DDFeatures.VEGETATION_FEATURE.get().withConfiguration(Configs.SANDY_CATACOMBS_VEGETATION_CONFIG).range(50).square().count(40));

    public static final ConfiguredFeature<?, ?> ROOTS_FEATURE = HELPER.registerConfiguredFeature("roots_feature", DDFeatures.SIMPLE_BLOCK.get().withConfiguration(new SimpleBlockConfig(new SimpleBlockStateProvider(DDBlocks.ROOTS.get().getDefaultState()))).withPlacement(DDPlacements.CAVE_SURFACE.get().configure(new CaveDecoratorConfig(CaveSurface.CEILING, 12))).range(50).square().count(20));

    public static final ConfiguredFeature<?, ?> LONG_ROOTS_FEATURE = HELPER.registerConfiguredFeature("long_roots_feature", DDFeatures.SIMPLE_BLOCK.get().withConfiguration(new SimpleBlockConfig(new SimpleBlockStateProvider(DDBlocks.LONG_ROOTS.get().getDefaultState()))).withPlacement(DDPlacements.CAVE_SURFACE.get().configure(new CaveDecoratorConfig(CaveSurface.CEILING, 12))).range(70).square().count(10).chance(25));

    public static final ConfiguredFeature<?, ?> GRIMESTONE_ORE = HELPER.registerConfiguredFeature("grimestone_ore", Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, DDBlocks.GRIMESTONE.get().getDefaultState(), 64)).count(7).withPlacement(Placement.RANGE.configure(new TopSolidRangeConfig(5, 5, 55))).square());
    public static final ConfiguredFeature<?, ?> HUGE_GLOWSHROOM = HELPER.registerConfiguredFeature("huge_glowshroom", DDFeatures.HUGE_GLOWSHROOM_FEATURE.get().withConfiguration(new HugeGlowshroomConfig(DDBlocks.GLOWSHROOM_STEM.get().getDefaultState(), DDBlocks.GLOWSHROOM_BLOCK.get().getDefaultState())).withPlacement(DDPlacements.CAVE_SURFACE.get().configure(new CaveDecoratorConfig(CaveSurface.FLOOR, 12))).range(32).square().count(8));
    public static final ConfiguredFeature<?, ?> HUGE_TWISTED_GLOWSHROOM = HELPER.registerConfiguredFeature("huge_twisted_glowshroom", DDFeatures.HUGE_TWISTED_GLOWSHROOM_FEATURE.get().withConfiguration(new HugeGlowshroomConfig(DDBlocks.GLOWSHROOM_STEM.get().getDefaultState(), DDBlocks.TWISTED_GLOWSHROOM_BLOCK.get().getDefaultState())).withPlacement(DDPlacements.CAVE_SURFACE.get().configure(new CaveDecoratorConfig(CaveSurface.FLOOR, 12))).range(32).square().count(8));
    public static final ConfiguredFeature<?, ?> HUGE_GLOWSHROOM_PLANTED = HELPER.registerConfiguredFeature("huge_glowshroom_planted", DDFeatures.HUGE_GLOWSHROOM_FEATURE.get().withConfiguration(new HugeGlowshroomConfig(DDBlocks.GLOWSHROOM_STEM.get().getDefaultState(), DDBlocks.GLOWSHROOM_BLOCK.get().getDefaultState())));
    public static final ConfiguredFeature<?, ?> HUGE_TWISTED_GLOWSHROOM_PLANTED = HELPER.registerConfiguredFeature("huge_twisted_glowshroom_planted", DDFeatures.HUGE_TWISTED_GLOWSHROOM_FEATURE.get().withConfiguration(new HugeGlowshroomConfig(DDBlocks.GLOWSHROOM_STEM.get().getDefaultState(), DDBlocks.TWISTED_GLOWSHROOM_BLOCK.get().getDefaultState())));
    public static final ConfiguredFeature<?, ?> GRIMESTONE_STONE_REPLACEMENT = HELPER.registerConfiguredFeature("grimestone_stone_replacement", DDFeatures.BLOB_REPLACEMENT_FEATURE.get().withConfiguration(new BlobReplacementConfig(ImmutableSet.of(Blocks.STONE.getDefaultState(), Blocks.DIORITE.getDefaultState(), Blocks.GRANITE.getDefaultState(), Blocks.GRAVEL.getDefaultState(), Blocks.DIRT.getDefaultState(), DDBlocks.ARIDROCK.get().getDefaultState()), DDBlocks.GRIMESTONE.get().getDefaultState(), FeatureSpread.create(3, 4))).range(55).square().count(100));
    public static final ConfiguredFeature<?, ?> GLOWVINE_FEATURE = HELPER.registerConfiguredFeature("glowvine", DDFeatures.GLOWVINE_FEATURE.get().withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).withPlacement(DDPlacements.CAVE_SURFACE.get().configure(new CaveDecoratorConfig(CaveSurface.CEILING, 12)).range(50).square().count(12)));

    public static final ConfiguredFeature<?, ?> GLOWSHROOM_VEGETATION = HELPER.registerConfiguredFeature("glowshroom_vegetation", DDFeatures.VEGETATION_FEATURE.get().withConfiguration(Configs.GLOWSHROOM_VEGETATION_CONFIG).range(50).square().count(40));
    public static final ConfiguredFeature<?, ?> TWISTED_GLOWSHROOM_VEGETATION = HELPER.registerConfiguredFeature("twisted_glowshroom_vegetation", DDFeatures.VEGETATION_FEATURE.get().withConfiguration(Configs.TWISTED_GLOWSHROOM_VEGETATION_CONFIG).range(50).square().count(40));
    public static final ConfiguredFeature<?, ?> GLOWSHROOM_WATERFALL = HELPER.registerConfiguredFeature("glowshroom_waterfall", DDFeatures.GLOWSHROOM_WATERFALL_FEATURE.get().withConfiguration(new HugeGlowshroomConfig(DDBlocks.GLOWSHROOM_STEM.get().getDefaultState(), DDBlocks.GLOWSHROOM_BLOCK.get().getDefaultState())).range(50).square().count(12));
    public static final ConfiguredFeature<?, ?> TWISTED_GLOWSHROOM_WATERFALL = HELPER.registerConfiguredFeature("twisted_glowshroom_waterfall", DDFeatures.GLOWSHROOM_WATERFALL_FEATURE.get().withConfiguration(new HugeGlowshroomConfig(DDBlocks.GLOWSHROOM_STEM.get().getDefaultState(), DDBlocks.TWISTED_GLOWSHROOM_BLOCK.get().getDefaultState())).range(50).square().count(8));

    //BG_GLOWSHROOM_CAVES

    public static final ConfiguredFeature<?, ?> BG_GLOWSHROOM_VEGETATION = HELPER.registerConfiguredFeature("bg_glowshroom_vegetation", DDFeatures.SIMPLE_BLOCK.get().withConfiguration(new SimpleBlockConfig(new WeightedBlockStateProvider()
            .addWeightedBlockstate(DDBlocks.GLOWSHROOM.get().getDefaultState(), 11)
            .addWeightedBlockstate(DDBlocks.GLOWSPURS.get().getDefaultState(), 1)
            .addWeightedBlockstate(DDBlocks.MOSSY_SPROUTS.get().getDefaultState(), 25)
            .addWeightedBlockstate(Blocks.CAVE_AIR.getDefaultState(), 63))));

    public static final ConfiguredFeature<?, ?> MOSSY_GRIMESTONE_PATCH = DDFeatures.VEGETATION_PATCH.get().withConfiguration(new VegetationPatchConfig(BlockTags.BASE_STONE_OVERWORLD.getName(), new WeightedBlockStateProvider().addWeightedBlockstate(DDBlocks.MOSSY_GRIMESTONE.get().getDefaultState(), 53).addWeightedBlockstate(DDBlocks.GRIMESTONE.get().getDefaultState(), 37), () -> {
        return GLOWSHROOM_VEGETATION;
    }, CaveSurface.FLOOR, ConstantIntProvider.create(1), 0.0f, 5, 0.8f, UniformIntProvider.create(4, 7), 0.3f));

    public static final ConfiguredFeature<?, ?> BG_HUGE_GLOWSHROOM = HELPER.registerConfiguredFeature("bg_huge_glowshroom", DDFeatures.GLOWSHROOM_FEATURE.get().withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).withPlacement(DDPlacements.CAVE_SURFACE.get().configure(new CaveDecoratorConfig(CaveSurface.FLOOR, 12))).range(50).square().count(7));

    static class States {
        public static final BlockState ASH_LAYER = DDBlocks.ASH.get().getDefaultState().with(BlockStateProperties.LAYERS_1_8, 1);
        public static final BlockState ASH_LAYER_DOUBLE = DDBlocks.ASH.get().getDefaultState().with(BlockStateProperties.LAYERS_1_8, 2);
        public static final BlockState ASH_LAYER_TRIPLE = DDBlocks.ASH.get().getDefaultState().with(BlockStateProperties.LAYERS_1_8, 3);
        public static final BlockState ASH_LAYER_QUADRUPLE = DDBlocks.ASH.get().getDefaultState().with(BlockStateProperties.LAYERS_1_8, 4);
    }

    static class Configs {
        //DEAD_BUSH, 25
        //DRY_SPROUTS, 33
        //DETRITUS, 17
        //ROOTS, 15
        //LONG_ROOTS, 10
        public static final BlockStateProvidingFeatureConfig SANDY_CATACOMBS_VEGETATION_CONFIG = new BlockStateProvidingFeatureConfig(new WeightedBlockStateProvider()
                .addWeightedBlockstate(Blocks.DEAD_BUSH.getDefaultState(), 11)
                .addWeightedBlockstate(DDBlocks.DRY_SPROUTS.get().getDefaultState(), 50)
                .addWeightedBlockstate(DDBlocks.DETRITUS.get().getDefaultState(), 10)
                .addWeightedBlockstate(DDBlocks.ROOTS.get().getDefaultState(), 25));

        public static final BlockStateProvidingFeatureConfig OASIS_VEGETATION_CONFIG = new BlockStateProvidingFeatureConfig(new WeightedBlockStateProvider()
                .addWeightedBlockstate(Blocks.GRASS.getDefaultState(), 50)
                .addWeightedBlockstate(DDBlocks.ALOE.get().getDefaultState(), 10)
                .addWeightedBlockstate(DDBlocks.LUSH_SPROUTS.get().getDefaultState(), 25)
                .addWeightedBlockstate(Blocks.LILY_PAD.getDefaultState(), 11));

        public static final BlockStateProvidingFeatureConfig GLOWSHROOM_VEGETATION_CONFIG = new BlockStateProvidingFeatureConfig(new WeightedBlockStateProvider()
        .addWeightedBlockstate(DDBlocks.GLOWSPURS.get().getDefaultState(), 35)
        .addWeightedBlockstate(DDBlocks.GLOWSHROOM.get().getDefaultState(), 20));

        public static final BlockStateProvidingFeatureConfig TWISTED_GLOWSHROOM_VEGETATION_CONFIG = new BlockStateProvidingFeatureConfig(new WeightedBlockStateProvider()
        .addWeightedBlockstate(DDBlocks.TWISTED_GLOWSHROOM.get().getDefaultState(), 15)
        .addWeightedBlockstate(DDBlocks.TWISTED_GLOWSHROOM_ROOT.get().getDefaultState(), 25));

//        public static final BlockStateProvidingFeatureConfig SANDY_CATACOMBS_VEGETATION_CONFIG = new BlockStateProvidingFeatureConfig(new WeightedBlockStateProvider()
//                .addWeightedBlockstate(Blocks.DEAD_BUSH.getDefaultState(), 25)
//                .addWeightedBlockstate(DDBlocks.DRY_SPROUTS.get().getDefaultState(), 33)
//                .addWeightedBlockstate(DDBlocks.DETRITUS.get().getDefaultState(), 17)
//                .addWeightedBlockstate(DDBlocks.ROOTS.get().getDefaultState(), 15)
//                .addWeightedBlockstate(DDBlocks.LONG_ROOTS.get().getDefaultState(), 10));
    }
}
