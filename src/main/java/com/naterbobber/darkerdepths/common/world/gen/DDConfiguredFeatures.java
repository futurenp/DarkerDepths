package com.naterbobber.darkerdepths.common.world.gen;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.naterbobber.darkerdepths.common.blocks.Glowshroom;
import com.naterbobber.darkerdepths.common.blocks.GlowspursBlock;
import com.naterbobber.darkerdepths.common.math.ConstantIntProvider;
import com.naterbobber.darkerdepths.common.math.IntProvider;
import com.naterbobber.darkerdepths.common.math.UniformIntProvider;
import com.naterbobber.darkerdepths.common.world.gen.feature.CavePillarConfig;
import com.naterbobber.darkerdepths.common.world.gen.feature.GemstonePlacementConfig;
import com.naterbobber.darkerdepths.common.world.gen.feature.GrowingPlantConfig;
import com.naterbobber.darkerdepths.common.world.gen.feature.HugeGlowshroomConfig;
import com.naterbobber.darkerdepths.common.world.gen.feature.ReplaceBlobsFeatureConfig;
import com.naterbobber.darkerdepths.common.world.gen.feature.RootSystemFeatureConfig;
import com.naterbobber.darkerdepths.common.world.gen.feature.SimpleBlockConfig;
import com.naterbobber.darkerdepths.common.world.gen.feature.SpeleothemConfig;
import com.naterbobber.darkerdepths.common.world.gen.feature.VegetationPatchConfig;
import com.naterbobber.darkerdepths.common.world.gen.placement.CaveSurfaceDecoratorConfig;
import com.naterbobber.darkerdepths.core.api.Registries;
import com.naterbobber.darkerdepths.core.DarkerDepths;
import com.naterbobber.darkerdepths.core.registries.DDBlocks;
import com.naterbobber.darkerdepths.core.registries.tags.DDBlockTags;
import com.naterbobber.darkerdepths.core.registries.worldgen.DDFeatures;
import com.naterbobber.darkerdepths.core.registries.worldgen.DDPlacements;
import com.naterbobber.darkerdepths.core.util.VerticalSurfaceType;
import com.naterbobber.darkerdepths.core.util.DDFillerBlockTypes;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Direction;
import net.minecraft.util.WeightedList;
import net.minecraft.world.gen.blockstateprovider.BlockStateProvider;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.blockstateprovider.WeightedBlockStateProvider;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.BlockStateProvidingFeatureConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureSpread;
import net.minecraft.world.gen.feature.Features;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.LiquidsConfig;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.feature.TwoLayerFeature;
import net.minecraft.world.gen.foliageplacer.BlobFoliagePlacer;
import net.minecraft.world.gen.placement.DepthAverageConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.placement.TopSolidRangeConfig;
import net.minecraft.world.gen.trunkplacer.ForkyTrunkPlacer;

//<>

public class DDConfiguredFeatures {
    public static final Registries HELPER = DarkerDepths.REGISTRIES;

    /**
     * DEFAULT CAVES FEATURES
     */
    public static final ConfiguredFeature<?, ?> GLOWSHROOM_PATCH                = HELPER.registerConfiguredFeature("glowshroom_patch", DDFeatures.SIMPLE_BLOCK.get().withConfiguration(new SimpleBlockConfig(new SimpleBlockStateProvider(States.SINGLE_GLOWSHROOM))).withPlacement(DDPlacements.CAVE_SURFACE.get().configure(new CaveSurfaceDecoratorConfig(VerticalSurfaceType.FLOOR, 12))).range(50).square().func_242730_a(FeatureSpread.func_242253_a(20, 60)).chance(20));

    /**
     * MOLTEN CAVERN FEATURES
     */
    public static final ConfiguredFeature<?, ?> MOLTEN_CAVERN_TERRAIN           = HELPER.registerConfiguredFeature("molten_cavern_terrain", DDFeatures.REPLACE_BLOBS.get().withConfiguration(new ReplaceBlobsFeatureConfig(States.OVERWORLD_REPLACEABLES, States.SHALE, UniformIntProvider.create(3, 7))).range(50).square().func_242731_b(75));

    public static final ConfiguredFeature<?, ?> MOLTEN_CAVERN_SPRING_OPEN       = HELPER.registerConfiguredFeature("molten_cavern_spring_open", Feature.SPRING_FEATURE.withConfiguration(new LiquidsConfig(States.LAVA, false, 4, 1, ImmutableSet.of(Blocks.STONE, Blocks.GRANITE, Blocks.DIORITE, Blocks.ANDESITE, DDBlocks.SHALE.get()))).withPlacement(Features.Placements.SPRING_PLACEMENT).square().func_242731_b(32));
    public static final ConfiguredFeature<?, ?> MOLTEN_CAVERN_SPRING_CLOSED     = HELPER.registerConfiguredFeature("molten_cavern_spring_closed", Feature.SPRING_FEATURE.withConfiguration(Configs.LAVA_SPRING_CLOSED_CONFIG).withPlacement(Features.Placements.NETHER_SPRING_ORE_PLACEMENT).square().func_242731_b(4).range(16));

    public static final ConfiguredFeature<?, ?> AMBER                           = HELPER.registerConfiguredFeature("amber", DDFeatures.GEMSTONE_PLACEMENT_FEATURE.get().withConfiguration(new GemstonePlacementConfig(States.AMBER)).range(25).square().func_242731_b(60));
    public static final ConfiguredFeature<?, ?> ASH_VEGETATION                  = HELPER.registerConfiguredFeature("ash_vegetation", DDFeatures.SIMPLE_BLOCK.get().withConfiguration(new SimpleBlockConfig(new WeightedBlockStateProvider().addWeightedBlockstate(States.SINGLE_ASH_LAYER, 25).addWeightedBlockstate(States.DOUBLE_ASH_LAYER, 15))));
    public static final ConfiguredFeature<?, ?> MOLTEN_CAVE_VEGETATION          = HELPER.registerConfiguredFeature("molten_cave_vegetation", DDFeatures.VEGETATION_PATCH.get().withConfiguration(new VegetationPatchConfig(BlockTags.BASE_STONE_OVERWORLD.getName(), new SimpleBlockStateProvider(DDBlocks.ASH_BLOCK.get().getDefaultState()), () -> ASH_VEGETATION, VerticalSurfaceType.FLOOR, ConstantIntProvider.create(1), 0.0f, 5, 0.8f, UniformIntProvider.create(4, 7), 0.3f)).withPlacement(DDPlacements.CAVE_SURFACE.get().configure(new CaveSurfaceDecoratorConfig(VerticalSurfaceType.FLOOR, 12))).range(50).square().func_242731_b(20));
    public static final ConfiguredFeature<?, ?> LAVA_POOL_PATCH                 = HELPER.registerConfiguredFeature("lava_pool_patch", DDFeatures.LAVA_VEGETATION_PATCH.get().withConfiguration(new VegetationPatchConfig(BlockTags.BASE_STONE_OVERWORLD.getName(), new SimpleBlockStateProvider(DDBlocks.SHALE.get().getDefaultState()), () -> Feature.NO_OP.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG), VerticalSurfaceType.FLOOR, ConstantIntProvider.create(3), 0.8f, 5, 0.01f, UniformIntProvider.create(4, 7), 0.7f)).withPlacement(DDPlacements.CAVE_SURFACE.get().configure(new CaveSurfaceDecoratorConfig(VerticalSurfaceType.FLOOR, 12))).range(32).square().func_242731_b(10));
    public static final ConfiguredFeature<?, ?> GEYSER                          = HELPER.registerConfiguredFeature("geyser", DDFeatures.GEYSER_FEATURE.get().withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).range(50).square().func_242731_b(12));

    public static final ConfiguredFeature<?, ?> SHALE_SPELEOTHEM_UP             = HELPER.registerConfiguredFeature("shale_speleothem_up", DDFeatures.SPELEOTHEM_FEATURE.get().withConfiguration(new SpeleothemConfig(DDBlocks.SHALE_SPELEOTHEM.get().getDefaultState(), DDBlocks.SHALE.get().getDefaultState(), Direction.UP)).withPlacement(DDPlacements.CAVE_SURFACE.get().configure(new CaveSurfaceDecoratorConfig(VerticalSurfaceType.FLOOR, 12))).range(50).square().func_242731_b(7));
    public static final ConfiguredFeature<?, ?> SHALE_SPELEOTHEM_BOTTOM         = HELPER.registerConfiguredFeature("shale_speleothem_bottom", DDFeatures.SPELEOTHEM_FEATURE.get().withConfiguration(new SpeleothemConfig(DDBlocks.SHALE_SPELEOTHEM.get().getDefaultState(), DDBlocks.SHALE.get().getDefaultState(), Direction.DOWN)).withPlacement(DDPlacements.CAVE_SURFACE.get().configure(new CaveSurfaceDecoratorConfig(VerticalSurfaceType.FLOOR, 12))).range(50).square().func_242731_b(7));

    //ores
    public static final ConfiguredFeature<?, ?> MAGMA_ORE                       = HELPER.registerConfiguredFeature("magma_ore", Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, States.MAGMA_BLOCK, 15)).func_242731_b(12).range(50).square());

    /**
     * SANDY CATACOMBS FEATURES
     */
    public static final ConfiguredFeature<?, ?> ARIDROCK_CAVE_TERRAIN           = HELPER.registerConfiguredFeature("aridrock_cave_terrain", DDFeatures.REPLACE_BLOBS.get().withConfiguration(new ReplaceBlobsFeatureConfig(States.ARIDROCK_REPLACEABLES, States.ARIDROCK, UniformIntProvider.create(3, 7))).range(50).square().func_242731_b(128));
    public static final ConfiguredFeature<?, ?> LIMESTONE_CAVE_TERRAIN          = HELPER.registerConfiguredFeature("limestone_cave_terrain", DDFeatures.REPLACE_BLOBS.get().withConfiguration(new ReplaceBlobsFeatureConfig(States.LIMESTONE_REPLACEABLES, States.LIMESTONE, UniformIntProvider.create(3, 7))).range(16).square().func_242731_b(128));

    public static final ConfiguredFeature<?, ?> ARIDROCK_VEGETATION             = HELPER.registerConfiguredFeature("sandy_catacombs_vegetation", DDFeatures.VEGETATION_FEATURE.get().withConfiguration(Configs.SANDY_CATACOMBS_CONFIG).range(50).square().func_242731_b(35));
    public static final ConfiguredFeature<?, ?> ROOTS                           = HELPER.registerConfiguredFeature("roots", DDFeatures.SIMPLE_BLOCK.get().withConfiguration(new SimpleBlockConfig(new SimpleBlockStateProvider(States.ROOTS))).withPlacement(DDPlacements.CAVE_SURFACE.get().configure(new CaveSurfaceDecoratorConfig(VerticalSurfaceType.CEILING, 12))).range(50).square().func_242731_b(20));
    public static final ConfiguredFeature<?, ?> LONG_ROOTS                      = HELPER.registerConfiguredFeature("long_roots", DDFeatures.SIMPLE_BLOCK.get().withConfiguration(new SimpleBlockConfig(new SimpleBlockStateProvider(States.LONG_ROOTS))).withPlacement(DDPlacements.CAVE_SURFACE.get().configure(new CaveSurfaceDecoratorConfig(VerticalSurfaceType.CEILING, 12))).range(70).square().func_242731_b(20));
    public static final ConfiguredFeature<?, ?> PETRIFIED_LOG_BRANCH            = HELPER.registerConfiguredFeature("petrified_log_branch", DDFeatures.PETRIFIED_LOG_BRANCH.get().withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).withPlacement(DDPlacements.CAVE_SURFACE.get().configure(new CaveSurfaceDecoratorConfig(VerticalSurfaceType.CEILING, 12))).range(32).square().func_242731_b(8));
    public static final ConfiguredFeature<?, ?> CAVE_FOSSILS                    = HELPER.registerConfiguredFeature("cave_fossils", DDFeatures.CAVE_FOSSILS.get().withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).chance(5).range(40).square());

    public static final ConfiguredFeature<?, ?> ARIDROCK_SPELEOTHEM_UP          = HELPER.registerConfiguredFeature("aridrock_speleothem_up", DDFeatures.SPELEOTHEM_FEATURE.get().withConfiguration(new SpeleothemConfig(DDBlocks.ARIDROCK_SPELEOTHEM.get().getDefaultState(), DDBlocks.ARIDROCK.get().getDefaultState(), Direction.UP)).withPlacement(DDPlacements.CAVE_SURFACE.get().configure(new CaveSurfaceDecoratorConfig(VerticalSurfaceType.FLOOR, 12))).range(50).square().func_242731_b(7));
    public static final ConfiguredFeature<?, ?> ARIDROCK_SPELEOTHEM_BOTTOM      = HELPER.registerConfiguredFeature("aridrock_speleothem_bottom", DDFeatures.SPELEOTHEM_FEATURE.get().withConfiguration(new SpeleothemConfig(DDBlocks.ARIDROCK_SPELEOTHEM.get().getDefaultState(), DDBlocks.ARIDROCK.get().getDefaultState(), Direction.DOWN)).withPlacement(DDPlacements.CAVE_SURFACE.get().configure(new CaveSurfaceDecoratorConfig(VerticalSurfaceType.FLOOR, 12))).range(50).square().func_242731_b(7));
    public static final ConfiguredFeature<?, ?> LIMESTONE_SPELEOTHEM_UP         = HELPER.registerConfiguredFeature("limestone_speleothem_up", DDFeatures.SPELEOTHEM_FEATURE.get().withConfiguration(new SpeleothemConfig(DDBlocks.LIMESTONE_SPELEOTHEM.get().getDefaultState(), DDBlocks.LIMESTONE.get().getDefaultState(), Direction.UP)).withPlacement(DDPlacements.CAVE_SURFACE.get().configure(new CaveSurfaceDecoratorConfig(VerticalSurfaceType.FLOOR, 12))).range(50).square().func_242731_b(7));
    public static final ConfiguredFeature<?, ?> LIMESTONE_SPELEOTHEM_BOTTOM     = HELPER.registerConfiguredFeature("limestone_speleothem_bottom", DDFeatures.SPELEOTHEM_FEATURE.get().withConfiguration(new SpeleothemConfig(DDBlocks.LIMESTONE_SPELEOTHEM.get().getDefaultState(), DDBlocks.LIMESTONE.get().getDefaultState(), Direction.DOWN)).withPlacement(DDPlacements.CAVE_SURFACE.get().configure(new CaveSurfaceDecoratorConfig(VerticalSurfaceType.FLOOR, 12))).range(50).square().func_242731_b(7));

    public static final ConfiguredFeature<?, ?> ARIDROCK_ORE_COAL               = HELPER.registerConfiguredFeature("aridrock_coal_ore", Feature.ORE.withConfiguration(new OreFeatureConfig(DDFillerBlockTypes.ARIDROCK, States.ARIDROCK_COAL_ORE, 17)).range(128).square().func_242731_b(20));
    public static final ConfiguredFeature<?, ?> ARIDROCK_ORE_IRON               = HELPER.registerConfiguredFeature("aridrock_ore_iron", Feature.ORE.withConfiguration(new OreFeatureConfig(DDFillerBlockTypes.ARIDROCK, States.ARIDROCK_IRON_ORE, 9)).range(64).square().func_242731_b(20));
    public static final ConfiguredFeature<?, ?> ARIDROCK_ORE_GOLD               = HELPER.registerConfiguredFeature("aridrock_ore_gold", Feature.ORE.withConfiguration(new OreFeatureConfig(DDFillerBlockTypes.ARIDROCK, States.ARIDROCK_GOLD_ORE, 9)).range(32).square().func_242731_b(2));
    public static final ConfiguredFeature<?, ?> ARIDROCK_ORE_REDSTONE           = HELPER.registerConfiguredFeature("aridrock_ore_redstone", Feature.ORE.withConfiguration(new OreFeatureConfig(DDFillerBlockTypes.ARIDROCK, States.ARIDROCK_REDSTONE_ORE, 8)).range(16).square().func_242731_b(8));
    public static final ConfiguredFeature<?, ?> ARIDROCK_ORE_DIAMOND            = HELPER.registerConfiguredFeature("aridrock_ore_diamond", Feature.ORE.withConfiguration(new OreFeatureConfig(DDFillerBlockTypes.ARIDROCK, States.ARIDROCK_DIAMOND_ORE, 8)).range(16).square());
    public static final ConfiguredFeature<?, ?> ARIDROCK_ORE_SILVER             = HELPER.registerConfiguredFeature("aridrock_ore_silver", Feature.ORE.withConfiguration(new OreFeatureConfig(DDFillerBlockTypes.ARIDROCK, States.ARIDROCK_SILVER_ORE, 8)).range(16).square());
    public static final ConfiguredFeature<?, ?> ARIDROCK_ORE_LAPIS              = HELPER.registerConfiguredFeature("aridrock_ore_lapis", Feature.ORE.withConfiguration(new OreFeatureConfig(DDFillerBlockTypes.ARIDROCK, States.ARIDROCK_LAPIS_ORE, 7)).withPlacement(Placement.DEPTH_AVERAGE.configure(new DepthAverageConfig(16, 16))).square());

    public static final ConfiguredFeature<?, ?> LIMESTONE_ORE                   = HELPER.registerConfiguredFeature("limestone_ore", Feature.ORE.withConfiguration(new OreFeatureConfig(DDFillerBlockTypes.ARIDROCK, States.LIMESTONE, 15)).func_242731_b(12).withPlacement(Placement.RANGE.configure(new TopSolidRangeConfig(16, 0, 32))).square());
    public static final ConfiguredFeature<?, ?> SILVER_ORE                      = HELPER.registerConfiguredFeature("silver_ore", Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, States.SILVER_ORE, 8)).range(16).square());

    public static final ConfiguredFeature<?, ?> LIMESTONE_ORE_COAL              = HELPER.registerConfiguredFeature("limestone_coal_ore", Feature.ORE.withConfiguration(new OreFeatureConfig(DDFillerBlockTypes.LIMESTONE, States.LIMESTONE_COAL_ORE, 17)).func_242731_b(128).square().range(20));
    public static final ConfiguredFeature<?, ?> LIMESTONE_ORE_IRON              = HELPER.registerConfiguredFeature("limestone_ore_iron", Feature.ORE.withConfiguration(new OreFeatureConfig(DDFillerBlockTypes.LIMESTONE, States.LIMESTONE_IRON_ORE, 9)).range(64).square().func_242731_b(20));
    public static final ConfiguredFeature<?, ?> LIMESTONE_ORE_GOLD              = HELPER.registerConfiguredFeature("limestone_ore_gold", Feature.ORE.withConfiguration(new OreFeatureConfig(DDFillerBlockTypes.LIMESTONE, States.LIMESTONE_GOLD_ORE, 9)).range(32).square().func_242731_b(2));
    public static final ConfiguredFeature<?, ?> LIMESTONE_ORE_REDSTONE          = HELPER.registerConfiguredFeature("limestone_ore_redstone", Feature.ORE.withConfiguration(new OreFeatureConfig(DDFillerBlockTypes.LIMESTONE, States.LIMESTONE_REDSTONE_ORE, 8)).range(16).square().func_242731_b(8));
    public static final ConfiguredFeature<?, ?> LIMESTONE_ORE_DIAMOND           = HELPER.registerConfiguredFeature("limestone_ore_diamond", Feature.ORE.withConfiguration(new OreFeatureConfig(DDFillerBlockTypes.LIMESTONE, States.LIMESTONE_DIAMOND_ORE, 8)).range(16).square());
    public static final ConfiguredFeature<?, ?> LIMESTONE_ORE_SILVER            = HELPER.registerConfiguredFeature("limestone_ore_silver", Feature.ORE.withConfiguration(new OreFeatureConfig(DDFillerBlockTypes.LIMESTONE, States.LIMESTONE_SILVER_ORE, 8)).range(16).square());
    public static final ConfiguredFeature<?, ?> LIMESTONE_ORE_LAPIS             = HELPER.registerConfiguredFeature("limestone_ore_lapis", Feature.ORE.withConfiguration(new OreFeatureConfig(DDFillerBlockTypes.LIMESTONE, States.LIMESTONE_LAPIS_ORE, 7)).withPlacement(Placement.DEPTH_AVERAGE.configure(new DepthAverageConfig(16, 16))).square());

    public static final ConfiguredFeature<?, ?> OASIS_POOL                      = HELPER.registerConfiguredFeature("oasis_pool", DDFeatures.WATERLOGGED_VEGETATION_PATCH.get().withConfiguration(new VegetationPatchConfig(BlockTags.BASE_STONE_OVERWORLD.getName(), new SimpleBlockStateProvider(States.LUSH_ARIDROCK), () -> Feature.NO_OP.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG), VerticalSurfaceType.FLOOR, ConstantIntProvider.create(1), 0.0f, 5, 0.01f, UniformIntProvider.create(8, 14), 1.0f)).withPlacement(DDPlacements.CAVE_SURFACE.get().configure(new CaveSurfaceDecoratorConfig(VerticalSurfaceType.FLOOR, 12))).range(50).square().func_242731_b(10).chance(64));
    public static final ConfiguredFeature<?, ?> OASIS_VEGETATION                = HELPER.registerConfiguredFeature("oasis_vegetation", DDFeatures.VEGETATION_FEATURE.get().withConfiguration(Configs.OASIS_VEGETATION_CONFIG).range(50).square().func_242731_b(60));

    public static final ConfiguredFeature<?, ?> PETRIFIED_TREE                  = HELPER.registerConfiguredFeature("petrified_tree", DDFeatures.TREE.get().withConfiguration(new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(States.PETRIFIED_LOG), new SimpleBlockStateProvider(States.AIR), new BlobFoliagePlacer(FeatureSpread.func_242252_a(0), FeatureSpread.func_242252_a(0), 0), new ForkyTrunkPlacer(5, 2, 2), new TwoLayerFeature(1, 0, 2)).setIgnoreVines().build()));

    public static final ConfiguredFeature<?, ?> ROOTED_PETRIFIED_TREE           = HELPER.registerConfiguredFeature("rooted_petrified_tree", PETRIFIED_TREE.withPlacement(DDPlacements.CAVE_SURFACE.get().configure(new CaveSurfaceDecoratorConfig(VerticalSurfaceType.FLOOR, 12)).range(60).square().func_242731_b(3)));

//    public static final ConfiguredFeature<?, ?> ROOTED_PETRIFIED_TREE           = HELPER.registerConfiguredFeature("rooted_petrified_tree", DDFeatures.ROOT_SYSTEM.get().withConfiguration(new RootSystemFeatureConfig(() -> {
//        return PETRIFIED_TREE;
//    }, 3, 3, DDBlockTags.SANDY_GROUND_REPLACEABLE.getName(), new SimpleBlockStateProvider(DDBlocks.PETRIFIED_LOG.get().getDefaultState()), 20, 100, 3, 2, new SimpleBlockStateProvider(DDBlocks.ROOTS.get().getDefaultState()), 20, 2)).withPlacement(DDPlacements.CAVE_SURFACE.get().configure(new CaveSurfaceDecoratorConfig(VerticalSurfaceType.CEILING, 12))).range(60).square());

    /**
     * GLOWSHROOM CAVE FEATURES
     */
    public static final ConfiguredFeature<?, ?> GLOWSHROOM_CAVE_TERRAIN         = HELPER.registerConfiguredFeature("glowshroom_cave_terrain", DDFeatures.REPLACE_BLOBS.get().withConfiguration(new ReplaceBlobsFeatureConfig(States.OVERWORLD_REPLACEABLES, States.GRIMESTONE, UniformIntProvider.create(3, 7))).range(50).square().func_242731_b(75));

    public static final ConfiguredFeature<?, ?> HUGE_GLOWSHROOM                 = HELPER.registerConfiguredFeature("huge_glowshroom", DDFeatures.HUGE_GLOWSHROOM_FEATURE.get().withConfiguration(new HugeGlowshroomConfig(States.GLOWSHROOM_STEM, States.GLOWSHROOM_BLOCK)).withPlacement(DDPlacements.CAVE_SURFACE.get().configure(new CaveSurfaceDecoratorConfig(VerticalSurfaceType.FLOOR, 12))).range(50).square().func_242731_b(7));
    public static final ConfiguredFeature<?, ?> HUGE_GLOWSHROOM_PLANTED         = HELPER.registerConfiguredFeature("huge_glowshroom_planted", DDFeatures.HUGE_GLOWSHROOM_FEATURE.get().withConfiguration(new HugeGlowshroomConfig(States.GLOWSHROOM_STEM, States.GLOWSHROOM_BLOCK)));

    public static final ConfiguredFeature<?, ?> GLOWSPIRE_VINE                  = HELPER.registerConfiguredFeature("glowspire_vine", DDFeatures.GROWING_PLANT.get().withConfiguration(new GrowingPlantConfig(new WeightedList<IntProvider>().func_226313_a_(UniformIntProvider.create(1, 20), 2).func_226313_a_(UniformIntProvider.create(1, 3), 3).func_226313_a_(UniformIntProvider.create(1, 7), 10), Direction.DOWN, new SimpleBlockStateProvider(States.GLOWSPIRE_VINE_BODY), new SimpleBlockStateProvider(States.GLOWSPIRE_VINE_HEAD), false)));
    public static final ConfiguredFeature<?, ?> GLOWSPIRE_VINES                 = HELPER.registerConfiguredFeature("glowspire_vines", GLOWSPIRE_VINE.withPlacement(DDPlacements.CAVE_SURFACE.get().configure(new CaveSurfaceDecoratorConfig(VerticalSurfaceType.CEILING, 12))).range(60).square().func_242731_b(60));

    public static final ConfiguredFeature<?, ?> GLOWSHROOM_VEGETATION           = HELPER.registerConfiguredFeature("glowshroom_vegetation", DDFeatures.SIMPLE_BLOCK.get().withConfiguration(new SimpleBlockConfig(new WeightedBlockStateProvider().addWeightedBlockstate(States.SINGLE_GLOWSHROOM, 3).addWeightedBlockstate(States.DOUBLE_GLOWSHROOM, 1).addWeightedBlockstate(States.TRIPLE_GLOWSHROOM, 1).addWeightedBlockstate(States.GLOWSPURS_NORTH, 1).addWeightedBlockstate(States.GLOWSPURS_EAST, 1).addWeightedBlockstate(States.GLOWSPURS_SOUTH, 1).addWeightedBlockstate(States.GLOWSPURS_WEST, 1).addWeightedBlockstate(States.MOSSY_SPROUTS, 30).addWeightedBlockstate(Blocks.AIR.getDefaultState(), 100))));
    public static final ConfiguredFeature<?, ?> GLOWSHROOM_VEGETATION_PATCH     = HELPER.registerConfiguredFeature("glowshroom_vegetation_patch", DDFeatures.VEGETATION_PATCH.get().withConfiguration(Configs.GLOWSHROOM_VEGETATION_PATCH_CONFIG).withPlacement(DDPlacements.CAVE_SURFACE.get().configure(new CaveSurfaceDecoratorConfig(VerticalSurfaceType.FLOOR, 12))).range(50).square().func_242731_b(13));

    public static final ConfiguredFeature<?, ?> GRIMESTONE_SPELEOTHEM_UP        = HELPER.registerConfiguredFeature("grimestone_speleothem_up", DDFeatures.SPELEOTHEM_FEATURE.get().withConfiguration(new SpeleothemConfig(DDBlocks.GRIMESTONE_SPELEOTHEM.get().getDefaultState(), DDBlocks.GRIMESTONE.get().getDefaultState(), Direction.UP)).withPlacement(DDPlacements.CAVE_SURFACE.get().configure(new CaveSurfaceDecoratorConfig(VerticalSurfaceType.FLOOR, 12))).range(50).square().func_242731_b(7));
    public static final ConfiguredFeature<?, ?> GRIMESTONE_SPELEOTHEM_BOTTOM    = HELPER.registerConfiguredFeature("grimestone_speleothem_bottom", DDFeatures.SPELEOTHEM_FEATURE.get().withConfiguration(new SpeleothemConfig(DDBlocks.GRIMESTONE_SPELEOTHEM.get().getDefaultState(), DDBlocks.GRIMESTONE.get().getDefaultState(), Direction.DOWN)).withPlacement(DDPlacements.CAVE_SURFACE.get().configure(new CaveSurfaceDecoratorConfig(VerticalSurfaceType.FLOOR, 12))).range(50).square().func_242731_b(7));

    //ores

    /**
     * CRYSTAL CAVES FEATURES
     */

    //vegetation
    public static final ConfiguredFeature<?, ?> CEILING_CRYSTAL_PEAK            = HELPER.registerConfiguredFeature("ceiling_crystal_peak", DDFeatures.CRYSTAL_PEAK.get().withConfiguration(new CavePillarConfig(States.CELESTINE_CRYSTAL_BLOCK, Direction.DOWN)).withPlacement(DDPlacements.CAVE_SURFACE.get().configure(new CaveSurfaceDecoratorConfig(VerticalSurfaceType.CEILING, 12))).chance(10).square().range(50));
    public static final ConfiguredFeature<?, ?> FLOOR_CRYSTAL_PEAK              = HELPER.registerConfiguredFeature("floor_crystal_peak", DDFeatures.CRYSTAL_PEAK.get().withConfiguration(new CavePillarConfig(States.CELESTINE_CRYSTAL_BLOCK, Direction.UP)).withPlacement(DDPlacements.CAVE_SURFACE.get().configure(new CaveSurfaceDecoratorConfig(VerticalSurfaceType.FLOOR, 12))).chance(10).square().range(50));

    static class States {
        //DEFAULT_CAVES
        public static final BlockState AIR                      = Blocks.AIR.getDefaultState();
        public static final BlockState STONE                    = Blocks.STONE.getDefaultState();
        public static final BlockState ANDESITE                 = Blocks.ANDESITE.getDefaultState();
        public static final BlockState GRANITE                  = Blocks.GRANITE.getDefaultState();
        public static final BlockState DIORITE                  = Blocks.DIORITE.getDefaultState();
        public static final BlockState DIRT                     = Blocks.DIRT.getDefaultState();
        public static final BlockState GRAVEL                   = Blocks.GRAVEL.getDefaultState();
        public static final BlockState COAL_ORE                 = Blocks.COAL_ORE.getDefaultState();
        public static final BlockState IRON_ORE                 = Blocks.IRON_ORE.getDefaultState();
        public static final BlockState GOLD_ORE                 = Blocks.GOLD_ORE.getDefaultState();
        public static final BlockState REDSTONE_ORE             = Blocks.REDSTONE_ORE.getDefaultState();
        public static final BlockState DIAMOND_ORE              = Blocks.DIAMOND_ORE.getDefaultState();
        public static final BlockState LAPIS_ORE                = Blocks.LAPIS_ORE.getDefaultState();
        public static final BlockState SILVER_ORE               = DDBlocks.SILVER_ORE.get().getDefaultState();
        public static final FluidState LAVA                     = Fluids.LAVA.getDefaultState();

        //MOLTEN_CAVERN
        public static final BlockState MAGMA_BLOCK              = Blocks.MAGMA_BLOCK.getDefaultState();
        public static final BlockState SHALE                    = DDBlocks.SHALE.get().getDefaultState();
        public static final BlockState AMBER                    = DDBlocks.AMBER.get().getDefaultState();
        public static final BlockState SINGLE_ASH_LAYER         = DDBlocks.ASH.get().getDefaultState().with(BlockStateProperties.LAYERS_1_8, 1);
        public static final BlockState DOUBLE_ASH_LAYER         = DDBlocks.ASH.get().getDefaultState().with(BlockStateProperties.LAYERS_1_8, 2);

        //SANDY_CATACOMBS
        public static final BlockState PETRIFIED_LOG            = DDBlocks.PETRIFIED_LOG.get().getDefaultState();
        public static final BlockState ARIDROCK                 = DDBlocks.ARIDROCK.get().getDefaultState();
        public static final BlockState LIMESTONE                = DDBlocks.LIMESTONE.get().getDefaultState();
        public static final BlockState DEAD_BUSH                = Blocks.DEAD_BUSH.getDefaultState();
        public static final BlockState DRY_SPROUTS              = DDBlocks.DRY_SPROUTS.get().getDefaultState();
        public static final BlockState DETRITUS                 = DDBlocks.DETRITUS.get().getDefaultState();
        public static final BlockState ROOTS                    = DDBlocks.ROOTS.get().getDefaultState();
        public static final BlockState LONG_ROOTS               = DDBlocks.LONG_ROOTS.get().getDefaultState();
        public static final BlockState ARIDROCK_COAL_ORE        = DDBlocks.ARIDROCK_COAL_ORE.get().getDefaultState();
        public static final BlockState ARIDROCK_IRON_ORE        = DDBlocks.ARIDROCK_IRON_ORE.get().getDefaultState();
        public static final BlockState ARIDROCK_GOLD_ORE        = DDBlocks.ARIDROCK_GOLD_ORE.get().getDefaultState();
        public static final BlockState ARIDROCK_REDSTONE_ORE    = DDBlocks.ARIDROCK_REDSTONE_ORE.get().getDefaultState();
        public static final BlockState ARIDROCK_DIAMOND_ORE     = DDBlocks.ARIDROCK_DIAMOND_ORE.get().getDefaultState();
        public static final BlockState ARIDROCK_SILVER_ORE      = DDBlocks.ARIDROCK_SILVER_ORE.get().getDefaultState();
        public static final BlockState ARIDROCK_LAPIS_ORE       = DDBlocks.ARIDROCK_LAPIS_ORE.get().getDefaultState();
        public static final BlockState LIMESTONE_COAL_ORE       = DDBlocks.LIMESTONE_COAL_ORE.get().getDefaultState();
        public static final BlockState LIMESTONE_IRON_ORE       = DDBlocks.LIMESTONE_IRON_ORE.get().getDefaultState();
        public static final BlockState LIMESTONE_GOLD_ORE       = DDBlocks.LIMESTONE_GOLD_ORE.get().getDefaultState();
        public static final BlockState LIMESTONE_REDSTONE_ORE   = DDBlocks.LIMESTONE_REDSTONE_ORE.get().getDefaultState();
        public static final BlockState LIMESTONE_DIAMOND_ORE    = DDBlocks.LIMESTONE_DIAMOND_ORE.get().getDefaultState();
        public static final BlockState LIMESTONE_SILVER_ORE     = DDBlocks.LIMESTONE_SILVER_ORE.get().getDefaultState();
        public static final BlockState LIMESTONE_LAPIS_ORE      = DDBlocks.LIMESTONE_LAPIS_ORE.get().getDefaultState();

        //OASIS
        public static final BlockState LUSH_ARIDROCK            = DDBlocks.LUSH_ARIDROCK.get().getDefaultState();
        public static final BlockState GRASS                    = Blocks.GRASS.getDefaultState();
        public static final BlockState ALOE                     = DDBlocks.ALOE.get().getDefaultState();
        public static final BlockState LUSH_SPROUTS             = DDBlocks.LUSH_SPROUTS.get().getDefaultState();
        public static final BlockState LILY_PAD                 = Blocks.LILY_PAD.getDefaultState();

        //GLOWSHROOM_CAVES
        public static final BlockState GRIMESTONE               = DDBlocks.GRIMESTONE.get().getDefaultState();
        public static final BlockState MOSSY_GRIMESTONE         = DDBlocks.MOSSY_GRIMESTONE.get().getDefaultState();
        public static final BlockState GLOWSHROOM_STEM          = DDBlocks.GLOWSHROOM_STEM.get().getDefaultState();
        public static final BlockState GLOWSHROOM_BLOCK         = DDBlocks.GLOWSHROOM_BLOCK.get().getDefaultState();
        public static final BlockState GLOWSPIRE_VINE_BODY      = DDBlocks.GLOWSPIRE_PLANT.get().getDefaultState();
        public static final BlockState GLOWSPIRE_VINE_HEAD      = DDBlocks.GLOWSPIRE.get().getDefaultState();
        public static final BlockState GLOWSPURS_NORTH          = DDBlocks.GLOWSPURS.get().getDefaultState().with(GlowspursBlock.FACING, Direction.NORTH);
        public static final BlockState GLOWSPURS_SOUTH          = DDBlocks.GLOWSPURS.get().getDefaultState().with(GlowspursBlock.FACING, Direction.SOUTH);
        public static final BlockState GLOWSPURS_EAST           = DDBlocks.GLOWSPURS.get().getDefaultState().with(GlowspursBlock.FACING, Direction.EAST);
        public static final BlockState GLOWSPURS_WEST           = DDBlocks.GLOWSPURS.get().getDefaultState().with(GlowspursBlock.FACING, Direction.WEST);
        public static final BlockState MOSSY_SPROUTS            = DDBlocks.MOSSY_SPROUTS.get().getDefaultState();
        public static final BlockState SINGLE_GLOWSHROOM        = DDBlocks.GLOWSHROOM.get().getDefaultState().with(Glowshroom.CLUSTERS_1_3, 1);
        public static final BlockState DOUBLE_GLOWSHROOM        = DDBlocks.GLOWSHROOM.get().getDefaultState().with(Glowshroom.CLUSTERS_1_3, 2);
        public static final BlockState TRIPLE_GLOWSHROOM        = DDBlocks.GLOWSHROOM.get().getDefaultState().with(Glowshroom.CLUSTERS_1_3, 3);

        //CRYSTAL_CAVES
        public static final BlockState CELESTINE_CRYSTAL_BLOCK  = DDBlocks.CELESTINE_CRYSTAL_BLOCK.get().getDefaultState();

        //REPLACEABLES
        public static final ImmutableList<BlockState> OVERWORLD_REPLACEABLES    = ImmutableList.of(STONE, ANDESITE, GRANITE, DIORITE, DIRT, SHALE, GRAVEL);
        public static final ImmutableList<BlockState> ARIDROCK_REPLACEABLES     = ImmutableList.of(STONE, ANDESITE, GRANITE, DIORITE, DIRT, COAL_ORE, IRON_ORE, GOLD_ORE, REDSTONE_ORE, DIAMOND_ORE, LAPIS_ORE);
        public static final ImmutableList<BlockState> LIMESTONE_REPLACEABLES    = ImmutableList.of(STONE, ANDESITE, GRANITE, DIORITE, DIRT, COAL_ORE, IRON_ORE, GOLD_ORE, REDSTONE_ORE, DIAMOND_ORE, LAPIS_ORE, ARIDROCK, ARIDROCK_COAL_ORE, ARIDROCK_IRON_ORE, ARIDROCK_GOLD_ORE, ARIDROCK_REDSTONE_ORE, ARIDROCK_DIAMOND_ORE, ARIDROCK_LAPIS_ORE);
    }

    static class Configs {
        //MOLTEN_CAVERN
        public static final LiquidsConfig LAVA_SPRING_CLOSED_CONFIG = new LiquidsConfig(States.LAVA, false, 5, 0, ImmutableSet.of(Blocks.STONE, Blocks.GRANITE, Blocks.DIORITE, Blocks.ANDESITE, DDBlocks.SHALE.get()));
        public static final LiquidsConfig LAVA_SPRING_CONFIG        = new LiquidsConfig(States.LAVA, true, 4, 1, ImmutableSet.of(Blocks.STONE, Blocks.GRANITE, Blocks.DIORITE, Blocks.ANDESITE, DDBlocks.SHALE.get()));

        //SANDY_CATACOMBS
        public static final BlockStateProvidingFeatureConfig SANDY_CATACOMBS_CONFIG         = new BlockStateProvidingFeatureConfig(new WeightedBlockStateProvider().addWeightedBlockstate(States.DEAD_BUSH, 10).addWeightedBlockstate(States.DRY_SPROUTS, 50).addWeightedBlockstate(States.DETRITUS, 5).addWeightedBlockstate(States.ROOTS, 15).addWeightedBlockstate(Blocks.AIR.getDefaultState(), 20));
        public static final BlockStateProvidingFeatureConfig OASIS_VEGETATION_CONFIG        = new BlockStateProvidingFeatureConfig(new WeightedBlockStateProvider().addWeightedBlockstate(States.GRASS, 50).addWeightedBlockstate(States.ALOE, 10).addWeightedBlockstate(States.LUSH_SPROUTS, 25).addWeightedBlockstate(States.LILY_PAD, 11));

        //GLOWSHROOM_CAVES
        public static final VegetationPatchConfig GLOWSHROOM_VEGETATION_PATCH_CONFIG = new VegetationPatchConfig(DDBlockTags.GRIMESTONE_REPLACEMENT.getName(), new WeightedBlockStateProvider().addWeightedBlockstate(States.MOSSY_GRIMESTONE, 53).addWeightedBlockstate(States.GRIMESTONE, 37), () -> {
//        public static final VegetationPatchConfig GLOWSHROOM_VEGETATION_PATCH_CONFIG = new VegetationPatchConfig(BlockTags.BASE_STONE_OVERWORLD.getName(), new WeightedBlockStateProvider().addWeightedBlockstate(States.MOSSY_GRIMESTONE, 53).addWeightedBlockstate(States.GRIMESTONE, 37), () -> {
            return GLOWSHROOM_VEGETATION;
        }, VerticalSurfaceType.FLOOR, ConstantIntProvider.create(1), 0.0F, 5, 0.8F, UniformIntProvider.create(4, 7), 0.3F);

        //CRYSTAL_CAVES

    }
}