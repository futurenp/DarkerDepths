package com.naterbobber.darkerdepths.common.world.gen;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.naterbobber.darkerdepths.common.blocks.Speleothem;
import com.naterbobber.darkerdepths.common.world.gen.feature.BlobReplacementConfig;
import com.naterbobber.darkerdepths.common.world.gen.feature.SimpleBlockConfig;
import com.naterbobber.darkerdepths.common.world.gen.placement.CaveDecoratorConfig;
import com.naterbobber.darkerdepths.core.registries.DDBlocks;
import com.naterbobber.darkerdepths.core.registries.DDFeatures;

import com.naterbobber.darkerdepths.core.registries.DDPlacements;
import com.naterbobber.darkerdepths.core.util.CaveSurface;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.blockplacer.SimpleBlockPlacer;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placement.ChanceRangeConfig;
import net.minecraft.world.gen.placement.CountRangeConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.registries.ForgeRegistries;

//<>

public class VanillaBiomeFeatures {
	public static final BlockState SAND 				= Blocks.SAND.getDefaultState();
	public static final BlockState SANDSTONE			= Blocks.SANDSTONE.getDefaultState();
	public static final BlockState GRAVEL				= Blocks.GRAVEL.getDefaultState();
	public static final BlockState DIRT					= Blocks.DIRT.getDefaultState();
	public static final BlockState WEATHERED_SANDSTONE	= DDBlocks.WEATHERED_SANDSTONE.get().getDefaultState();
	public static final BlockState ASH 					= DDBlocks.ASH.get().getDefaultState();
	public static final BlockState ASH_BLOCK 			= DDBlocks.ASH_BLOCK.get().getDefaultState();
	public static final BlockState STONE 				= Blocks.STONE.getDefaultState();
	public static final BlockState DIORITE 				= Blocks.DIORITE.getDefaultState();
	public static final BlockState ANDESITE				= Blocks.ANDESITE.getDefaultState();
	public static final BlockState GRANITE 				= Blocks.GRANITE.getDefaultState();
	public static final BlockState SILVER_ORE			= DDBlocks.SILVER_ORE.get().getDefaultState();
	public static final BlockState ELYTRINE_ORE 		= DDBlocks.ELYTRINE_ORE.get().getDefaultState();
	public static final BlockState LIMESTONE 			= DDBlocks.LIMESTONE.get().getDefaultState();
	public static final BlockState SHALE	 			= DDBlocks.SHALE.get().getDefaultState();
	public static final BlockState GLOWSHROOM 			= DDBlocks.GLOWSHROOM.get().getDefaultState();
	public static final BlockState GLOWSHROOM_CAP 		= DDBlocks.GLOWSHROOM_CAP.get().getDefaultState();
	public static final BlockState SPELEOTHEM_FLOOR		= DDBlocks.SPELEOTHEM.get().getDefaultState();
	public static final BlockState SPELEOTHEM_CEILING	= DDBlocks.SPELEOTHEM.get().getDefaultState().with(Speleothem.HANGING, true);
	public static final FluidState FLUID_LAVA 			= Fluids.LAVA.getDefaultState();
	public static final LiquidsConfig MOLTEN_CAVERN_LAVA_SPRING_CONFIG 			= new LiquidsConfig(FLUID_LAVA, false, 4, 1, ImmutableSet.of(Blocks.STONE, Blocks.GRANITE, Blocks.DIORITE, Blocks.ANDESITE, Blocks.MAGMA_BLOCK, DDBlocks.SHALE.get()));
	public static final BlockClusterFeatureConfig ASH_CONFIG 					= (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(ASH), new SimpleBlockPlacer())).tries(100).func_227317_b_().build();
	public static final BlockClusterFeatureConfig GLOWSHROOM_CONFIG 			= (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(GLOWSHROOM), new SimpleBlockPlacer())).tries(64).func_227317_b_().build();
	public static final BlockClusterFeatureConfig GLOWSHROOM_CAP_CONFIG 		= (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(GLOWSHROOM_CAP), new SimpleBlockPlacer())).tries(3).whitelist(ImmutableSet.of(DDBlocks.SHALE.get().getBlock())).func_227317_b_().build();
	public static final BlockClusterFeatureConfig DEAD_BUSH_CONFIG 				= new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(Blocks.DEAD_BUSH.getDefaultState()), new SimpleBlockPlacer()).tries(32).whitelist(ImmutableSet.of(WEATHERED_SANDSTONE.getBlock(), SAND.getBlock(), LIMESTONE.getBlock())).func_227317_b_().build();
	public static final BlobReplacementConfig SHALE_CONFIG 						= new BlobReplacementConfig.Builder().setMinReach(new Vector3i(3, 3, 3)).setMaxReach(new Vector3i(7, 7, 7)).setTarget(ImmutableSet.of(STONE, DIORITE, GRANITE, GRAVEL, DIRT)).setBlockState(SHALE).build();
	public static final BlobReplacementConfig WEATHERED_SANDSTONE_CONFIG 		= new BlobReplacementConfig.Builder().setMinReach(new Vector3i(3, 3, 3)).setMaxReach(new Vector3i(7, 7, 7)).setTarget(ImmutableSet.of(STONE, DIORITE, ANDESITE, GRANITE, GRAVEL, DIRT)).setBlockState(WEATHERED_SANDSTONE).build();
	public static final BlobReplacementConfig LIMESTONE_CONFIG 					= new BlobReplacementConfig.Builder().setMinReach(new Vector3i(3, 3, 3)).setMaxReach(new Vector3i(7, 7, 7)).setTarget(ImmutableSet.of(STONE, DIORITE, ANDESITE, GRANITE, GRAVEL, DIRT, WEATHERED_SANDSTONE)).setBlockState(LIMESTONE).build();

	public static void addGlowshrooms(Biome biomeIn) {
		biomeIn.addFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, Feature.RANDOM_PATCH.withConfiguration(GLOWSHROOM_CONFIG).withPlacement(Placement.CHANCE_RANGE.configure(new ChanceRangeConfig(4, 0, 0, 64))));
		biomeIn.addFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, Feature.RANDOM_PATCH.withConfiguration(GLOWSHROOM_CAP_CONFIG).withPlacement(Placement.CHANCE_RANGE.configure(new ChanceRangeConfig(4, 0, 0, 64))));
	}
	
	public static void addAmber(Biome biomeIn) {
		biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, DDFeatures.GEMSTONE_PLACEMENT_FEATURE.get().withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(40, 0, 0, 25))));
	}
	
	public static void addAsh(Biome biomeIn) {
		biomeIn.addFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, Feature.RANDOM_PATCH.withConfiguration(ASH_CONFIG).withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(45, 0, 0, 55))));
		biomeIn.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, ASH_BLOCK, 33)).withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(10, 0, 0, 55))));
	}

	public static void addSpeleothems(Biome biomeIn) {
		biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, DDFeatures.SIMPLE_BLOCK_FEATURE.get().withConfiguration(new SimpleBlockConfig(new SimpleBlockStateProvider(SPELEOTHEM_CEILING))).withPlacement(DDPlacements.CAVE_SURFACE.get().configure(new CaveDecoratorConfig(CaveSurface.CEILING, 12))).withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(40, 0, 0, 60))));
		biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, DDFeatures.SIMPLE_BLOCK_FEATURE.get().withConfiguration(new SimpleBlockConfig(new SimpleBlockStateProvider(SPELEOTHEM_FLOOR))).withPlacement(DDPlacements.CAVE_SURFACE.get().configure(new CaveDecoratorConfig(CaveSurface.FLOOR, 12))).withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(40, 0, 0, 60))));
//		biomeIn.addFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, DDFeatures.SPELEOTHEM_FLOOR_FEATURE.get().withConfiguration(SPELEOTHEM_FLOOR_CONFIG).withPlacement(Placement.CHANCE_RANGE.configure(new ChanceRangeConfig(50, 16, 16, 54))));
//		biomeIn.addFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, DDFeatures.SPELEOTHEM_CEILING_FEATURE.get().withConfiguration(SPELEOTHEM_CEILING_CONFIG).withPlacement(Placement.CHANCE_RANGE.configure(new ChanceRangeConfig(50, 16, 16, 54))));
	}

	public static void addStoneVariants(Biome biomeIn) {
		biomeIn.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, LIMESTONE, 33)).withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(10, 0, 0, 80))));
		biomeIn.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, SHALE, 33)).withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(10, 0, 0, 80))));
	}

	public static void addOres(Biome biomeIn) {
		addSilverOre(biomeIn);
		addElytrineOre(biomeIn);
	}

	public static void addSilverOre(Biome biomeIn) {
		biomeIn.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, SILVER_ORE, 4)).withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(1, 5, 5, 16))));
	}

	public static void addElytrineOre(Biome biomeIn) {
		biomeIn.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, ELYTRINE_ORE, 4)).withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(2, 5, 5, 16))));
	}

	/**
	 * @note this method add features to ALL the biomes, or to specific vanilla biomes
	 */
	public static void addVanillaBiomeFeatures() {
		for (Biome biome : ForgeRegistries.BIOMES) {
			if (!biome.getCategory().equals(Biome.Category.NETHER) && !biome.getCategory().equals(Biome.Category.THEEND)) {
				//addSpeleothems(biome);
			}
		}
	}
}