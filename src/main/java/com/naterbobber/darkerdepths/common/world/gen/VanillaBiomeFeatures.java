package com.naterbobber.darkerdepths.common.world.gen;

import com.google.common.collect.ImmutableSet;
import com.naterbobber.darkerdepths.common.blocks.AbstractGemBlock;
import com.naterbobber.darkerdepths.common.blocks.Speleothem;
import com.naterbobber.darkerdepths.core.registries.DDBlocks;
import com.naterbobber.darkerdepths.core.registries.DDFeatures;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.Direction;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.blockplacer.SimpleBlockPlacer;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.BlockClusterFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NetherackBlobReplacementFeature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.placement.ChanceRangeConfig;
import net.minecraft.world.gen.placement.CountRangeConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.registries.ForgeRegistries;

//<>

public class VanillaBiomeFeatures {
	public static final BlockState SAND 				= Blocks.SAND.getDefaultState();
	public static final BlockState SANDSTONE			= Blocks.SANDSTONE.getDefaultState();
	public static final BlockState AMBER_FLOOR			= DDBlocks.AMBER.get().getDefaultState();
	public static final BlockState AMBER_CEILING		= DDBlocks.AMBER.get().getDefaultState().with(AbstractGemBlock.FACING, Direction.DOWN);
	public static final BlockState ASH 					= DDBlocks.ASH.get().getDefaultState();
	public static final BlockState ASH_BLOCK 			= DDBlocks.ASH_BLOCK.get().getDefaultState();
	public static final BlockState STONE 				= Blocks.STONE.getDefaultState();
	public static final BlockState DIORITE 				= Blocks.DIORITE.getDefaultState();
	public static final BlockState SILVER_ORE			= DDBlocks.SILVER_ORE.get().getDefaultState();
	public static final BlockState ELYTRINE_ORE 		= DDBlocks.ELYTRINE_ORE.get().getDefaultState();
	public static final BlockState LIMESTONE 			= DDBlocks.LIMESTONE.get().getDefaultState();
	public static final BlockState SHALE	 			= DDBlocks.SHALE.get().getDefaultState();
	public static final BlockState GLOWSHROOM 			= DDBlocks.GLOWSHROOM.get().getDefaultState();
	public static final BlockState GLOWSHROOM_CAP 		= DDBlocks.GLOWSHROOM_CAP.get().getDefaultState();
	public static final BlockState SPELEOTHEM_FLOOR		= DDBlocks.SPELEOTHEM.get().getDefaultState();
	public static final BlockState SPELEOTHEM_CEILING	= DDBlocks.SPELEOTHEM.get().getDefaultState().with(Speleothem.HANGING, true);
	public static final BlockClusterFeatureConfig ASH_CONFIG 					= (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(ASH), new SimpleBlockPlacer())).tries(100).func_227317_b_().build();
	public static final BlockClusterFeatureConfig GLOWSHROOM_CONFIG 			= (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(GLOWSHROOM), new SimpleBlockPlacer())).tries(64).func_227317_b_().build();
	public static final BlockClusterFeatureConfig GLOWSHROOM_CAP_CONFIG 		= (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(GLOWSHROOM_CAP), new SimpleBlockPlacer())).tries(3).whitelist(ImmutableSet.of(DDBlocks.SHALE.get().getBlock())).func_227317_b_().build();
	public static final BlockClusterFeatureConfig SPELEOTHEM_FLOOR_CONFIG 		= (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(SPELEOTHEM_FLOOR), SimpleBlockPlacer.field_236447_c_)).tries(64).xSpread(8).ySpread(8).zSpread(8).func_227317_b_().whitelist(ImmutableSet.of(Blocks.STONE, Blocks.ANDESITE, Blocks.DIORITE, Blocks.GRANITE, DDBlocks.SHALE.get(), DDBlocks.LIMESTONE.get())).build();
	public static final BlockClusterFeatureConfig SPELEOTHEM_CEILING_CONFIG 	= (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(SPELEOTHEM_CEILING), SimpleBlockPlacer.field_236447_c_)).tries(64).xSpread(8).ySpread(8).zSpread(8).func_227317_b_().whitelist(ImmutableSet.of(Blocks.STONE, Blocks.ANDESITE, Blocks.DIORITE, Blocks.GRANITE, DDBlocks.SHALE.get(), DDBlocks.LIMESTONE.get())).build();
	public static final BlockClusterFeatureConfig AMBER_FLOOR_CONFIG 			= (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(AMBER_FLOOR), SimpleBlockPlacer.field_236447_c_)).tries(86).xSpread(8).ySpread(8).zSpread(8).func_227317_b_().whitelist(ImmutableSet.of(Blocks.STONE, Blocks.ANDESITE, Blocks.DIORITE, Blocks.GRANITE, DDBlocks.SHALE.get(), DDBlocks.LIMESTONE.get(), Blocks.WATER)).build();
	public static final BlockClusterFeatureConfig AMBER_CEILING_CONFIG 			= (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(AMBER_CEILING), SimpleBlockPlacer.field_236447_c_)).tries(86).xSpread(8).ySpread(8).zSpread(8).func_227317_b_().whitelist(ImmutableSet.of(Blocks.STONE, Blocks.ANDESITE, Blocks.DIORITE, Blocks.GRANITE, DDBlocks.SHALE.get(), DDBlocks.LIMESTONE.get(), Blocks.WATER)).build();
	public static final NetherackBlobReplacementFeature SHALE_BLOB_CONFIG 		= (new NetherackBlobReplacementFeature.Builder()).func_236624_a_(new Vector3i(3, 3, 3)).func_236626_b_(new Vector3i(7, 7, 7)).func_236623_a_(STONE).func_236625_b_(SHALE).func_236622_a_();
	public static final NetherackBlobReplacementFeature ASH_BLOB_CONFIG 		= (new NetherackBlobReplacementFeature.Builder()).func_236624_a_(new Vector3i(3, 3, 3)).func_236626_b_(new Vector3i(7, 7, 7)).func_236623_a_(DIORITE).func_236625_b_(SHALE).func_236622_a_();
	public static final NetherackBlobReplacementFeature SAND_BLOB_CONFIG 		= (new NetherackBlobReplacementFeature.Builder()).func_236624_a_(new Vector3i(3, 3, 3)).func_236626_b_(new Vector3i(7, 7, 7)).func_236623_a_(STONE).func_236625_b_(SAND).func_236622_a_();
	public static final NetherackBlobReplacementFeature SANDSTONE_BLOB_CONFIG 	= (new NetherackBlobReplacementFeature.Builder()).func_236624_a_(new Vector3i(3, 3, 3)).func_236626_b_(new Vector3i(7, 7, 7)).func_236623_a_(STONE).func_236625_b_(SANDSTONE).func_236622_a_();

	public static void addGlowshrooms(Biome biomeIn) {
		biomeIn.addFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, Feature.RANDOM_PATCH.withConfiguration(GLOWSHROOM_CONFIG).withPlacement(Placement.CHANCE_RANGE.configure(new ChanceRangeConfig(4, 0, 0, 64))));
		biomeIn.addFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, Feature.RANDOM_PATCH.withConfiguration(GLOWSHROOM_CAP_CONFIG).withPlacement(Placement.CHANCE_RANGE.configure(new ChanceRangeConfig(4, 0, 0, 64))));
	}
	
	public static void addAmber(Biome biomeIn) {		
		biomeIn.addFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, DDFeatures.SPELEOTHEM_FLOOR_FEATURE.get().withConfiguration(AMBER_FLOOR_CONFIG).withPlacement(Placement.CHANCE_RANGE.configure(new ChanceRangeConfig(75, 16, 16, 36))));
		biomeIn.addFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, DDFeatures.SPELEOTHEM_CEILING_FEATURE.get().withConfiguration(AMBER_CEILING_CONFIG).withPlacement(Placement.CHANCE_RANGE.configure(new ChanceRangeConfig(75, 16, 16, 36))));
	}
	
	public static void addAsh(Biome biomeIn) {
		biomeIn.addFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, Feature.RANDOM_PATCH.withConfiguration(ASH_CONFIG).withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(45, 0, 0, 55))));
		biomeIn.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, ASH_BLOCK, 33)).withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(10, 0, 0, 55))));
	}

	public static void addSpeleothems(Biome biomeIn) {
		biomeIn.addFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, DDFeatures.SPELEOTHEM_FLOOR_FEATURE.get().withConfiguration(SPELEOTHEM_FLOOR_CONFIG).withPlacement(Placement.CHANCE_RANGE.configure(new ChanceRangeConfig(50, 16, 16, 54))));
		biomeIn.addFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, DDFeatures.SPELEOTHEM_CEILING_FEATURE.get().withConfiguration(SPELEOTHEM_CEILING_CONFIG).withPlacement(Placement.CHANCE_RANGE.configure(new ChanceRangeConfig(50, 16, 16, 54))));
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
				addSpeleothems(biome);
			}
		}
	}
}