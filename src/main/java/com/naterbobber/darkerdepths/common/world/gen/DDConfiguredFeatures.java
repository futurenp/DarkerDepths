package com.naterbobber.darkerdepths.common.world.gen;

import com.naterbobber.darkerdepths.common.blocks.Glowshroom;
import com.naterbobber.darkerdepths.common.math.ConstantIntProvider;
import com.naterbobber.darkerdepths.common.math.IntProvider;
import com.naterbobber.darkerdepths.common.math.UniformIntProvider;
import com.naterbobber.darkerdepths.common.world.gen.feature.CavePillarConfig;
import com.naterbobber.darkerdepths.common.world.gen.feature.GemstonePlacementConfig;
import com.naterbobber.darkerdepths.common.world.gen.feature.GrowingPlantConfig;
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
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Direction;
import net.minecraft.util.WeightedList;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.blockstateprovider.WeightedBlockStateProvider;
import net.minecraft.world.gen.feature.BlockStateProvidingFeatureConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureSpread;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.placement.DepthAverageConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.placement.TopSolidRangeConfig;

//<>

public class DDConfiguredFeatures {
    public static final DDRegistries HELPER = DarkerDepths.REGISTRIES;

    //DEFAULT_CAVES
    
    //vegetation
    public static final ConfiguredFeature<?, ?> GLOWSHROOM_PATCH            = HELPER.registerConfiguredFeature("glowshroom_patch", DDFeatures.SIMPLE_BLOCK.get().withConfiguration(new SimpleBlockConfig(new SimpleBlockStateProvider(States.SINGLE_GLOWSHROOM))).withPlacement(DDPlacements.CAVE_SURFACE.get().configure(new CaveDecoratorConfig(CaveSurface.FLOOR, 12))).range(50).square().countSpread(FeatureSpread.create(20, 60)).chance(20));
    
    //ores
    
    //MOLTEN_CAVERNS

    //vegetation
    public static final ConfiguredFeature<?, ?> AMBER                       = HELPER.registerConfiguredFeature("amber", DDFeatures.GEMSTONE_PLACEMENT_FEATURE.get().withConfiguration(new GemstonePlacementConfig(States.AMBER)).range(25).square().count(60));
    public static final ConfiguredFeature<?, ?> ASH_VEGETATION              = HELPER.registerConfiguredFeature("ash_vegetation", DDFeatures.SIMPLE_BLOCK.get().withConfiguration(new SimpleBlockConfig(new WeightedBlockStateProvider().addWeightedBlockstate(States.SINGLE_ASH_LAYER, 25).addWeightedBlockstate(States.DOUBLE_ASH_LAYER, 15).addWeightedBlockstate(States.TRIPLE_ASH_LAYER, 10))));
    public static final ConfiguredFeature<?, ?> MOLTEN_CAVE_VEGETATION      = HELPER.registerConfiguredFeature("molten_cave_vegetation", DDFeatures.VEGETATION_PATCH.get().withConfiguration(new VegetationPatchConfig(BlockTags.BASE_STONE_OVERWORLD.getName(), new SimpleBlockStateProvider(DDBlocks.ASH_BLOCK.get().getDefaultState()), () -> ASH_VEGETATION, CaveSurface.FLOOR, ConstantIntProvider.create(1), 0.0f, 5, 0.8f, UniformIntProvider.create(4, 7), 0.3f)).withPlacement(DDPlacements.CAVE_SURFACE.get().configure(new CaveDecoratorConfig(CaveSurface.FLOOR, 12))).range(50).square().count(20));
    public static final ConfiguredFeature<?, ?> LAVA_POOL_PATCH             = HELPER.registerConfiguredFeature("lava_pool_patch", DDFeatures.LAVA_VEGETATION_PATCH.get().withConfiguration(new VegetationPatchConfig(BlockTags.BASE_STONE_OVERWORLD.getName(), new SimpleBlockStateProvider(DDBlocks.SHALE.get().getDefaultState()), () -> Feature.NO_OP.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG), CaveSurface.FLOOR, ConstantIntProvider.create(3), 0.8f, 5, 0.01f, UniformIntProvider.create(4, 7), 0.7f))).withPlacement(DDPlacements.CAVE_SURFACE.get().configure(new CaveDecoratorConfig(CaveSurface.FLOOR, 12))).range(32).square().count(10);
    public static final ConfiguredFeature<?, ?> GEYSER                      = HELPER.registerConfiguredFeature("geyser", DDFeatures.GEYSER_FEATURE.get().withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG)).withPlacement(DDPlacements.CAVE_SURFACE.get().configure(new CaveDecoratorConfig(CaveSurface.FLOOR, 12))).range(50).square().count(7);
    
    //ores
    public static final ConfiguredFeature<?, ?> MAGMA_ORE                   = HELPER.registerConfiguredFeature("magma_ore", Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD, States.MAGMA_BLOCK, 15)).count(12).withPlacement(Placement.RANGE.configure(new TopSolidRangeConfig(5, 5, 55))).square());

    //SANDY_CATACOMBS

    //vegetation
    public static final ConfiguredFeature<?, ?> SANDY_CATACOMBS_VEGETATION  = HELPER.registerConfiguredFeature("sandy_catacombs_vegetation", DDFeatures.VEGETATION_FEATURE.get().withConfiguration(Configs.SANDY_CATACOMBS_VEGETATION_CONFIG).range(50).square().count(40));
    public static final ConfiguredFeature<?, ?> ROOTS                       = HELPER.registerConfiguredFeature("roots", DDFeatures.SIMPLE_BLOCK.get().withConfiguration(new SimpleBlockConfig(new SimpleBlockStateProvider(States.ROOTS))).withPlacement(DDPlacements.CAVE_SURFACE.get().configure(new CaveDecoratorConfig(CaveSurface.CEILING, 12))).range(50).square().count(20));
    public static final ConfiguredFeature<?, ?> LONG_ROOTS                  = HELPER.registerConfiguredFeature("long_roots", DDFeatures.SIMPLE_BLOCK.get().withConfiguration(new SimpleBlockConfig(new SimpleBlockStateProvider(States.LONG_ROOTS))).withPlacement(DDPlacements.CAVE_SURFACE.get().configure(new CaveDecoratorConfig(CaveSurface.CEILING, 12))).range(70).square().count(10).chance(25));
    public static final ConfiguredFeature<?, ?> PETRIFIED_LOG_BRANCH        = HELPER.registerConfiguredFeature("petrified_log_branch", DDFeatures.PETRIFIED_LOG_BRANCH.get().withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).withPlacement(DDPlacements.CAVE_SURFACE.get().configure(new CaveDecoratorConfig(CaveSurface.CEILING, 12))).range(32).square().count(8));
    public static final ConfiguredFeature<?, ?> CAVE_FOSSILS                = HELPER.registerConfiguredFeature("cave_fossils", DDFeatures.CAVE_FOSSILS.get().withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).chance(5).range(40).square());

    //aridrock_ores
    public static final ConfiguredFeature<?, ?> ARIDROCK_ORE_COAL           = HELPER.registerConfiguredFeature("aridrock_coal_ore", Feature.ORE.withConfiguration(new OreFeatureConfig(DDFillerBlockTypes.ARIDROCK, States.ARIDROCK_COAL_ORE, 17)).count(128).square().range(80));
    public static final ConfiguredFeature<?, ?> ARIDROCK_ORE_IRON           = HELPER.registerConfiguredFeature("aridrock_ore_iron", Feature.ORE.withConfiguration(new OreFeatureConfig(DDFillerBlockTypes.ARIDROCK, States.ARIDROCK_IRON_ORE, 9)).range(64).square().count(80));
    public static final ConfiguredFeature<?, ?> ARIDROCK_ORE_GOLD           = HELPER.registerConfiguredFeature("aridrock_ore_gold", Feature.ORE.withConfiguration(new OreFeatureConfig(DDFillerBlockTypes.ARIDROCK, States.ARIDROCK_GOLD_ORE, 9)).range(32).square().count(8));
    public static final ConfiguredFeature<?, ?> ARIDROCK_ORE_REDSTONE       = HELPER.registerConfiguredFeature("aridrock_ore_redstone", Feature.ORE.withConfiguration(new OreFeatureConfig(DDFillerBlockTypes.ARIDROCK, States.ARIDROCK_REDSTONE_ORE, 8)).range(16).square().count(32));
    public static final ConfiguredFeature<?, ?> ARIDROCK_ORE_DIAMOND        = HELPER.registerConfiguredFeature("aridrock_ore_diamond", Feature.ORE.withConfiguration(new OreFeatureConfig(DDFillerBlockTypes.ARIDROCK, States.ARIDROCK_DIAMOND_ORE, 8)).range(16).square());
    public static final ConfiguredFeature<?, ?> ARIDROCK_ORE_SILVER         = HELPER.registerConfiguredFeature("aridrock_ore_silver", Feature.ORE.withConfiguration(new OreFeatureConfig(DDFillerBlockTypes.ARIDROCK, States.ARIDROCK_SILVER_ORE, 4)).range(16).square());
    public static final ConfiguredFeature<?, ?> ARIDROCK_ORE_LAPIS          = HELPER.registerConfiguredFeature("aridrock_ore_lapis", Feature.ORE.withConfiguration(new OreFeatureConfig(DDFillerBlockTypes.ARIDROCK, States.ARIDROCK_LAPIS_ORE, 7)).withPlacement(Placement.DEPTH_AVERAGE.configure(new DepthAverageConfig(16, 16))).square());

    //limestone_ores
    public static final ConfiguredFeature<?, ?> LIMESTONE_ORE_COAL          = HELPER.registerConfiguredFeature("limestone_coal_ore", Feature.ORE.withConfiguration(new OreFeatureConfig(DDFillerBlockTypes.LIMESTONE, States.LIMESTONE_COAL_ORE, 17)).count(128).square().range(80));
    public static final ConfiguredFeature<?, ?> LIMESTONE_ORE_IRON          = HELPER.registerConfiguredFeature("limestone_ore_iron", Feature.ORE.withConfiguration(new OreFeatureConfig(DDFillerBlockTypes.LIMESTONE, States.LIMESTONE_IRON_ORE, 9)).range(64).square().count(80));
    public static final ConfiguredFeature<?, ?> LIMESTONE_ORE_GOLD          = HELPER.registerConfiguredFeature("limestone_ore_gold", Feature.ORE.withConfiguration(new OreFeatureConfig(DDFillerBlockTypes.LIMESTONE, States.LIMESTONE_GOLD_ORE, 9)).range(32).square().count(8));
    public static final ConfiguredFeature<?, ?> LIMESTONE_ORE_REDSTONE      = HELPER.registerConfiguredFeature("limestone_ore_redstone", Feature.ORE.withConfiguration(new OreFeatureConfig(DDFillerBlockTypes.LIMESTONE, States.LIMESTONE_REDSTONE_ORE, 8)).range(16).square().count(32));
    public static final ConfiguredFeature<?, ?> LIMESTONE_ORE_DIAMOND       = HELPER.registerConfiguredFeature("limestone_ore_diamond", Feature.ORE.withConfiguration(new OreFeatureConfig(DDFillerBlockTypes.LIMESTONE, States.LIMESTONE_DIAMOND_ORE, 8)).range(16).square());
    public static final ConfiguredFeature<?, ?> LIMESTONE_ORE_SILVER        = HELPER.registerConfiguredFeature("limestone_ore_silver", Feature.ORE.withConfiguration(new OreFeatureConfig(DDFillerBlockTypes.LIMESTONE, States.LIMESTONE_SILVER_ORE, 4)).range(16).square());
    public static final ConfiguredFeature<?, ?> LIMESTONE_ORE_LAPIS         = HELPER.registerConfiguredFeature("limestone_ore_lapis", Feature.ORE.withConfiguration(new OreFeatureConfig(DDFillerBlockTypes.LIMESTONE, States.LIMESTONE_LAPIS_ORE, 7)).withPlacement(Placement.DEPTH_AVERAGE.configure(new DepthAverageConfig(16, 16))).square());

    //oasis
    public static final ConfiguredFeature<?, ?> OASIS_POOL                  = HELPER.registerConfiguredFeature("oasis_pool", DDFeatures.WATERLOGGED_VEGETATION_PATCH.get().withConfiguration(new VegetationPatchConfig(BlockTags.BASE_STONE_OVERWORLD.getName(), new SimpleBlockStateProvider(States.LUSH_ARIDROCK), () -> Feature.NO_OP.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG), CaveSurface.FLOOR, ConstantIntProvider.create(1), 0.0f, 5, 0.01f, UniformIntProvider.create(8, 14), 1.0f))).withPlacement(DDPlacements.CAVE_SURFACE.get().configure(new CaveDecoratorConfig(CaveSurface.FLOOR, 12))).withPlacement(Placement.RANGE.configure(new TopSolidRangeConfig(15, 0, 50))).square().count(10).chance(64);
    public static final ConfiguredFeature<?, ?> OASIS_VEGETATION            = HELPER.registerConfiguredFeature("oasis_vegetation", DDFeatures.VEGETATION_FEATURE.get().withConfiguration(Configs.OASIS_VEGETATION_CONFIG).range(50).square().count(60));

    //GLOWSHROOM_CAVES

    //vegetation
    public static final ConfiguredFeature<?, ?> HUGE_GLOWSHROOM             = HELPER.registerConfiguredFeature("huge_glowshroom", DDFeatures.HUGE_GLOWSHROOM_FEATURE.get().withConfiguration(new HugeGlowshroomConfig(States.GLOWSHROOM_STEM, States.GLOWSHROOM_BLOCK)).withPlacement(DDPlacements.CAVE_SURFACE.get().configure(new CaveDecoratorConfig(CaveSurface.FLOOR, 12))).range(50).square().count(7));
    public static final ConfiguredFeature<?, ?> HUGE_GLOWSHROOM_PLANTED     = HELPER.registerConfiguredFeature("huge_glowshroom_planted", DDFeatures.HUGE_GLOWSHROOM_FEATURE.get().withConfiguration(new HugeGlowshroomConfig(States.GLOWSHROOM_STEM, States.GLOWSHROOM_BLOCK)));
    public static final ConfiguredFeature<?, ?> GLOWSPIRE_VINES             = HELPER.registerConfiguredFeature("glowspire_vines", DDFeatures.GROWING_PLANT.get().withConfiguration(new GrowingPlantConfig(new WeightedList<IntProvider>().addWeighted(UniformIntProvider.create(1, 20), 2).addWeighted(UniformIntProvider.create(1, 3), 3).addWeighted(UniformIntProvider.create(1, 7), 10), Direction.DOWN, new SimpleBlockStateProvider(DDBlocks.GLOWSPIRE_PLANT.get().getDefaultState()), new SimpleBlockStateProvider(DDBlocks.GLOWSPIRE.get().getDefaultState()), false)).withPlacement(DDPlacements.CAVE_SURFACE.get().configure(new CaveDecoratorConfig(CaveSurface.CEILING, 12)).range(50).square().count(12)));
    public static final ConfiguredFeature<?, ?> GLOWSHROOM_VEGETATION       = HELPER.registerConfiguredFeature("glowshroom_vegetation", DDFeatures.SIMPLE_BLOCK.get().withConfiguration(new SimpleBlockConfig(new WeightedBlockStateProvider().addWeightedBlockstate(States.SINGLE_GLOWSHROOM, 6).addWeightedBlockstate(States.DOUBLE_GLOWSHROOM, 6).addWeightedBlockstate(States.TRIPLE_GLOWSHROOM, 6).addWeightedBlockstate(DDBlocks.GLOWSPURS.get().getDefaultState(), 5).addWeightedBlockstate(DDBlocks.MOSSY_SPROUTS.get().getDefaultState(), 50).addWeightedBlockstate(Blocks.CAVE_AIR.getDefaultState(), 43))));
    public static final ConfiguredFeature<?, ?> GLOWSHROOM_CAVE_VEGETATION  = HELPER.registerConfiguredFeature("glowshroom_cave_vegetation", DDFeatures.VEGETATION_PATCH.get().withConfiguration(new VegetationPatchConfig(BlockTags.BASE_STONE_OVERWORLD.getName(), new WeightedBlockStateProvider().addWeightedBlockstate(DDBlocks.MOSSY_GRIMESTONE.get().getDefaultState(), 53).addWeightedBlockstate(DDBlocks.GRIMESTONE.get().getDefaultState(), 37), () -> GLOWSHROOM_VEGETATION, CaveSurface.FLOOR, ConstantIntProvider.create(1), 0.0f, 5, 0.8f, UniformIntProvider.create(4, 7), 0.3f)).withPlacement(DDPlacements.CAVE_SURFACE.get().configure(new CaveDecoratorConfig(CaveSurface.FLOOR, 12))).range(50).square().count(13));

    //ores

    //CRYSTAL_CAVES

    //vegetation
    public static final ConfiguredFeature<?, ?> CEILING_CRYSTAL_PEAK        = HELPER.registerConfiguredFeature("ceiling_crystal_peak", DDFeatures.CRYSTAL_PEAK.get().withConfiguration(new CavePillarConfig(States.CELESTINE_CRYSTAL_BLOCK, Direction.DOWN))).withPlacement(DDPlacements.CAVE_SURFACE.get().configure(new CaveDecoratorConfig(CaveSurface.CEILING, 12))).chance(10).square().range(50);
    public static final ConfiguredFeature<?, ?> FLOOR_CRYSTAL_PEAK          = HELPER.registerConfiguredFeature("floor_crystal_peak", DDFeatures.CRYSTAL_PEAK.get().withConfiguration(new CavePillarConfig(States.CELESTINE_CRYSTAL_BLOCK, Direction.UP))).withPlacement(DDPlacements.CAVE_SURFACE.get().configure(new CaveDecoratorConfig(CaveSurface.FLOOR, 12))).chance(10).square().range(50);

    static class States {
        //DEFAULT_CAVES
        public static final BlockState STONE                    = Blocks.STONE.getDefaultState();
        public static final BlockState ANDESITE                 = Blocks.ANDESITE.getDefaultState();
        public static final BlockState GRANITE                  = Blocks.GRANITE.getDefaultState();
        public static final BlockState DIORITE                  = Blocks.DIORITE.getDefaultState();
        
        //MOLTEN_CAVERN
        public static final BlockState MAGMA_BLOCK              = Blocks.MAGMA_BLOCK.getDefaultState();
        public static final BlockState AMBER                    = DDBlocks.AMBER.get().getDefaultState();
        public static final BlockState SINGLE_ASH_LAYER         = DDBlocks.ASH.get().getDefaultState().with(BlockStateProperties.LAYERS_1_8, 1);
        public static final BlockState DOUBLE_ASH_LAYER         = DDBlocks.ASH.get().getDefaultState().with(BlockStateProperties.LAYERS_1_8, 2);
        public static final BlockState TRIPLE_ASH_LAYER         = DDBlocks.ASH.get().getDefaultState().with(BlockStateProperties.LAYERS_1_8, 3);
        
        //SANDY_CATACOMBS
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
        public static final BlockState GLOWSHROOM_STEM          = DDBlocks.GLOWSHROOM_STEM.get().getDefaultState();
        public static final BlockState GLOWSHROOM_BLOCK         = DDBlocks.GLOWSHROOM_BLOCK.get().getDefaultState();
        public static final BlockState SINGLE_GLOWSHROOM        = DDBlocks.GLOWSHROOM.get().getDefaultState().with(Glowshroom.CLUSTERS_1_3, 1);
        public static final BlockState DOUBLE_GLOWSHROOM        = DDBlocks.GLOWSHROOM.get().getDefaultState().with(Glowshroom.CLUSTERS_1_3, 2);
        public static final BlockState TRIPLE_GLOWSHROOM        = DDBlocks.GLOWSHROOM.get().getDefaultState().with(Glowshroom.CLUSTERS_1_3, 3);
        
        //CRYSTAL_CAVES
        public static final BlockState CELESTINE_CRYSTAL_BLOCK  = DDBlocks.CELESTINE_CRYSTAL_BLOCK.get().getDefaultState();
    }

    static class Configs {
        //MOLTEN_CAVERN

        //SANDY_CATACOMBS
        public static final BlockStateProvidingFeatureConfig SANDY_CATACOMBS_VEGETATION_CONFIG  = new BlockStateProvidingFeatureConfig(new WeightedBlockStateProvider().addWeightedBlockstate(States.DEAD_BUSH, 11).addWeightedBlockstate(States.DRY_SPROUTS, 50).addWeightedBlockstate(States.DETRITUS, 10).addWeightedBlockstate(States.ROOTS, 25));
        public static final BlockStateProvidingFeatureConfig OASIS_VEGETATION_CONFIG            = new BlockStateProvidingFeatureConfig(new WeightedBlockStateProvider().addWeightedBlockstate(States.GRASS, 50).addWeightedBlockstate(States.ALOE, 10).addWeightedBlockstate(States.LUSH_SPROUTS, 25).addWeightedBlockstate(States.LILY_PAD, 11));

        //GLOWSHROOM_CAVES
        
        //CRYSTAL_CAVES

    }
}