package com.naterbobber.darkerdepths.common.world.gen;

import com.blackgear.bgcore.core.registries.BGBiomes;
import com.google.common.collect.ImmutableSet;
import com.naterbobber.darkerdepths.common.blocks.Speleothem;
import com.naterbobber.darkerdepths.common.world.gen.biome.AbstractCaveBiome;
import com.naterbobber.darkerdepths.common.world.gen.feature.*;
import com.naterbobber.darkerdepths.common.world.gen.placement.CaveDecoratorConfig;
import com.naterbobber.darkerdepths.core.registries.*;

import com.naterbobber.darkerdepths.core.util.CaveSurface;
import com.naterbobber.darkerdepths.core.util.DDFillerBlockTypes;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.util.Direction;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.DefaultBiomeFeatures;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.blockplacer.SimpleBlockPlacer;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placement.*;
import net.minecraftforge.registries.ForgeRegistries;

//<>

public class VanillaBiomeFeatures {
	public static final BlockState SAND 					= Blocks.SAND.getDefaultState();
	public static final BlockState COAL_ORE 				= Blocks.COAL_ORE.getDefaultState();
	public static final BlockState IRON_ORE 				= Blocks.IRON_ORE.getDefaultState();
	public static final BlockState GOLD_ORE 				= Blocks.GOLD_ORE.getDefaultState();
	public static final BlockState REDSTONE_ORE				= Blocks.REDSTONE_ORE.getDefaultState();
	public static final BlockState DIAMOND_ORE				= Blocks.DIAMOND_ORE.getDefaultState();
	public static final BlockState LAPIS_ORE				= Blocks.LAPIS_ORE.getDefaultState();
	public static final BlockState ARIDROCK_COAL_ORE 		= DDBlocks.ARIDROCK_COAL_ORE.get().getDefaultState();
	public static final BlockState ARIDROCK_IRON_ORE 		= DDBlocks.ARIDROCK_IRON_ORE.get().getDefaultState();
	public static final BlockState ARIDROCK_GOLD_ORE 		= DDBlocks.ARIDROCK_GOLD_ORE.get().getDefaultState();
	public static final BlockState ARIDROCK_REDSTONE_ORE	= DDBlocks.ARIDROCK_REDSTONE_ORE.get().getDefaultState();
	public static final BlockState ARIDROCK_DIAMOND_ORE		= DDBlocks.ARIDROCK_DIAMOND_ORE.get().getDefaultState();
	public static final BlockState ARIDROCK_LAPIS_ORE		= DDBlocks.ARIDROCK_LAPIS_ORE.get().getDefaultState();
	public static final BlockState ARIDROCK_SILVER_ORE		= DDBlocks.ARIDROCK_SILVER_ORE.get().getDefaultState();
	public static final BlockState LIMESTONE_COAL_ORE 		= DDBlocks.LIMESTONE_COAL_ORE.get().getDefaultState();
	public static final BlockState LIMESTONE_IRON_ORE 		= DDBlocks.LIMESTONE_IRON_ORE.get().getDefaultState();
	public static final BlockState LIMESTONE_GOLD_ORE 		= DDBlocks.LIMESTONE_GOLD_ORE.get().getDefaultState();
	public static final BlockState LIMESTONE_REDSTONE_ORE	= DDBlocks.LIMESTONE_REDSTONE_ORE.get().getDefaultState();
	public static final BlockState LIMESTONE_DIAMOND_ORE	= DDBlocks.LIMESTONE_DIAMOND_ORE.get().getDefaultState();
	public static final BlockState LIMESTONE_LAPIS_ORE		= DDBlocks.LIMESTONE_LAPIS_ORE.get().getDefaultState();
	public static final BlockState LIMESTONE_SILVER_ORE		= DDBlocks.LIMESTONE_SILVER_ORE.get().getDefaultState();
	public static final BlockState GRAVEL					= Blocks.GRAVEL.getDefaultState();
	public static final BlockState DIRT						= Blocks.DIRT.getDefaultState();
	public static final BlockState ARIDROCK 				= DDBlocks.ARIDROCK.get().getDefaultState();
	public static final BlockState ASH 						= DDBlocks.ASH.get().getDefaultState();
	public static final BlockState ASH_BLOCK 				= DDBlocks.ASH_BLOCK.get().getDefaultState();
	public static final BlockState STONE 					= Blocks.STONE.getDefaultState();
	public static final BlockState DIORITE 					= Blocks.DIORITE.getDefaultState();
	public static final BlockState ANDESITE					= Blocks.ANDESITE.getDefaultState();
	public static final BlockState GRANITE 					= Blocks.GRANITE.getDefaultState();
	public static final BlockState LIMESTONE 				= DDBlocks.LIMESTONE.get().getDefaultState();
	public static final BlockState SHALE	 				= DDBlocks.SHALE.get().getDefaultState();
	public static final BlockState GLOWSHROOM 				= DDBlocks.GLOWSHROOM.get().getDefaultState();
	public static final BlockState AMBER					= DDBlocks.AMBER.get().getDefaultState();
	public static final BlockState AMETHYST					= DDBlocks.AMETHYST_CRYSTAL.get().getDefaultState();
	public static final BlockState CELESTINE				= DDBlocks.CELESTINE_CRYSTAL.get().getDefaultState();
	public static final FluidState FLUID_LAVA 				= Fluids.LAVA.getDefaultState();
	public static final FluidState FLUID_WATER				= Fluids.WATER.getDefaultState();
	public static final LiquidsConfig MOLTEN_CAVERN_LAVA_SPRING_CONFIG 			= new LiquidsConfig(FLUID_LAVA, false, 4, 1, ImmutableSet.of(Blocks.STONE, Blocks.GRANITE, Blocks.DIORITE, Blocks.ANDESITE, Blocks.MAGMA_BLOCK, DDBlocks.SHALE.get()));
	public static final LiquidsConfig CRYSTAL_CAVERN_WATER_SPRING_CONFIG		= new LiquidsConfig(FLUID_WATER, false, 4, 1, ImmutableSet.of(Blocks.STONE, Blocks.GRANITE, Blocks.DIORITE, Blocks.ANDESITE, Blocks.MAGMA_BLOCK, DDBlocks.AMETHYST_CRYSTAL_BLOCK.get()));
	public static final BlockClusterFeatureConfig ASH_CONFIG 					= new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(ASH), new SimpleBlockPlacer()).tries(100).func_227317_b_().build();
	public static final BlockClusterFeatureConfig DEAD_BUSH_CONFIG 				= new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(Blocks.DEAD_BUSH.getDefaultState()), new SimpleBlockPlacer()).tries(32).whitelist(ImmutableSet.of(ARIDROCK.getBlock(), SAND.getBlock(), LIMESTONE.getBlock())).func_227317_b_().build();
	public static final BlobReplacementConfig SHALE_CONFIG 						= new BlobReplacementConfig.Builder().setMinReach(new Vector3i(3, 3, 3)).setMaxReach(new Vector3i(7, 7, 7)).setTarget(ImmutableSet.of(STONE, DIORITE, GRANITE, GRAVEL, DIRT)).setBlockState(SHALE).build();
	public static final BlobReplacementConfig ARIDROCK_CONFIG 					= new BlobReplacementConfig.Builder().setMinReach(new Vector3i(3, 3, 3)).setMaxReach(new Vector3i(7, 7, 7)).setTarget(ImmutableSet.of(STONE, DIORITE, ANDESITE, GRANITE, GRAVEL, DIRT, COAL_ORE, IRON_ORE, GOLD_ORE, REDSTONE_ORE, DIAMOND_ORE, LAPIS_ORE)).setBlockState(ARIDROCK).build();
	public static final BlobReplacementConfig LIMESTONE_CONFIG 					= new BlobReplacementConfig.Builder().setMinReach(new Vector3i(3, 3, 3)).setMaxReach(new Vector3i(7, 7, 7)).setTarget(ImmutableSet.of(STONE, DIORITE, ANDESITE, GRANITE, GRAVEL, DIRT, COAL_ORE, IRON_ORE, GOLD_ORE, REDSTONE_ORE, DIAMOND_ORE, LAPIS_ORE, ARIDROCK_COAL_ORE, ARIDROCK_IRON_ORE, ARIDROCK_GOLD_ORE, ARIDROCK_REDSTONE_ORE, ARIDROCK_DIAMOND_ORE, ARIDROCK_LAPIS_ORE, ARIDROCK)).setBlockState(LIMESTONE).build();

	public static void addGlowshrooms(Biome biomeIn) {
		biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, DDFeatures.SIMPLE_BLOCK_FEATURE.get().withConfiguration(new SimpleBlockConfig(new SimpleBlockStateProvider(GLOWSHROOM))).withPlacement(DDPlacements.CAVE_SURFACE.get().configure(new CaveDecoratorConfig(CaveSurface.FLOOR, 12))).withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(7, 0, 0, 60))));
//		biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, DDFeatures.SIMPLE_BLOCK_FEATURE.get().withConfiguration(new SimpleBlockConfig(new SimpleBlockStateProvider(DDBlocks.GLOWSHROOM_CAP.get().getDefaultState()))).withPlacement(DDPlacements.CAVE_SURFACE.get().configure(new CaveDecoratorConfig(CaveSurface.FLOOR, 12))).withPlacement(Placement.CHANCE_RANGE.configure(new ChanceRangeConfig(5, 0, 0, 60))));
	}

	public static void addCrystalPeaks(Biome biomeIn) {
		biomeIn.addFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, DDFeatures.CAVE_PILLAR_FEATURE.get().withConfiguration(new CavePillarConfig(DDBlocks.CELESTINE_CRYSTAL_BLOCK.get().getDefaultState(), Direction.DOWN)).withPlacement(DDPlacements.CAVE_SURFACE.get().configure(new CaveDecoratorConfig(CaveSurface.CEILING, 12))).withPlacement(Placement.CHANCE_RANGE.configure(new ChanceRangeConfig(10, 15, 0, 50))));
		biomeIn.addFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, DDFeatures.CAVE_PILLAR_FEATURE.get().withConfiguration(new CavePillarConfig(DDBlocks.CELESTINE_CRYSTAL_BLOCK.get().getDefaultState(), Direction.UP)).withPlacement(DDPlacements.CAVE_SURFACE.get().configure(new CaveDecoratorConfig(CaveSurface.FLOOR, 12))).withPlacement(Placement.CHANCE_RANGE.configure(new ChanceRangeConfig(10, 0, 0, 25))));
		biomeIn.addFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, DDFeatures.CAVE_PILLAR_FEATURE.get().withConfiguration(new CavePillarConfig(DDBlocks.AMETHYST_CRYSTAL_BLOCK.get().getDefaultState(), Direction.DOWN)).withPlacement(DDPlacements.CAVE_SURFACE.get().configure(new CaveDecoratorConfig(CaveSurface.CEILING, 12))).withPlacement(Placement.CHANCE_RANGE.configure(new ChanceRangeConfig(10, 15, 0, 50))));
		biomeIn.addFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, DDFeatures.CAVE_PILLAR_FEATURE.get().withConfiguration(new CavePillarConfig(DDBlocks.AMETHYST_CRYSTAL_BLOCK.get().getDefaultState(), Direction.UP)).withPlacement(DDPlacements.CAVE_SURFACE.get().configure(new CaveDecoratorConfig(CaveSurface.FLOOR, 12))).withPlacement(Placement.CHANCE_RANGE.configure(new ChanceRangeConfig(10, 0, 0, 25))));
	}

	public static void addAmber(Biome biomeIn) {
		biomeIn.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, DDFeatures.GEMSTONE_PLACEMENT_FEATURE.get().withConfiguration(new GemstonePlacementConfig(AMBER)).withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(64, 0, 0, 25))));
	}

	public static void addAsh(Biome biomeIn) {
		biomeIn.addFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, Feature.RANDOM_PATCH.withConfiguration(ASH_CONFIG).withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(45, 0, 0, 55))));
		biomeIn.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, ASH_BLOCK, 33)).withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(10, 0, 0, 55))));
	}

	public static void addCarvers(Biome biomeIn) {
		biomeIn.addCarver(GenerationStage.Carving.AIR, Biome.createCarver(DDCarvers.FLAT_CAVE.get(), new ProbabilityConfig(0.2F)));
		biomeIn.addCarver(GenerationStage.Carving.AIR, Biome.createCarver(DDCarvers.LARGE_CAVE.get(), new ProbabilityConfig(0.1F)));
		biomeIn.addCarver(GenerationStage.Carving.AIR, Biome.createCarver(DDCarvers.BIG_CAVE.get(), new ProbabilityConfig(0.2F)));
		biomeIn.addCarver(GenerationStage.Carving.AIR, Biome.createCarver(DDCarvers.NOISE_CAVE.get(), new ProbabilityConfig(1F)));
		biomeIn.addCarver(GenerationStage.Carving.AIR, Biome.createCarver(DDCarvers.WATER_CAVE.get(), new ProbabilityConfig(1F)));
	}

	public static void addAridrockOres(Biome biomeIn) {
		biomeIn.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.withConfiguration(new OreFeatureConfig(DDFillerBlockTypes.ARIDROCK, ARIDROCK_COAL_ORE, 17)).withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(80, 0, 0, 128))));
		biomeIn.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.withConfiguration(new OreFeatureConfig(DDFillerBlockTypes.ARIDROCK, ARIDROCK_IRON_ORE, 9)).withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(80, 0, 0, 64))));
		biomeIn.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.withConfiguration(new OreFeatureConfig(DDFillerBlockTypes.ARIDROCK, ARIDROCK_GOLD_ORE, 9)).withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(8, 0, 0, 32))));
		biomeIn.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.withConfiguration(new OreFeatureConfig(DDFillerBlockTypes.ARIDROCK, ARIDROCK_REDSTONE_ORE, 8)).withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(32, 0, 0, 16))));
		biomeIn.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.withConfiguration(new OreFeatureConfig(DDFillerBlockTypes.ARIDROCK, ARIDROCK_DIAMOND_ORE, 8)).withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(4, 0, 0, 16))));
		biomeIn.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.withConfiguration(new OreFeatureConfig(DDFillerBlockTypes.ARIDROCK, ARIDROCK_SILVER_ORE, 4)).withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(4, 0, 0, 16))));
		biomeIn.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.withConfiguration(new OreFeatureConfig(DDFillerBlockTypes.ARIDROCK, ARIDROCK_LAPIS_ORE, 7)).withPlacement(Placement.COUNT_DEPTH_AVERAGE.configure(new DepthAverageConfig(4, 16, 16))));
	}

	public static void addLimestoneOres(Biome biomeIn) {
		biomeIn.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.withConfiguration(new OreFeatureConfig(DDFillerBlockTypes.LIMESTONE, LIMESTONE_COAL_ORE, 17)).withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(80, 0, 0, 128))));
		biomeIn.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.withConfiguration(new OreFeatureConfig(DDFillerBlockTypes.LIMESTONE, LIMESTONE_IRON_ORE, 9)).withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(80, 0, 0, 64))));
		biomeIn.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.withConfiguration(new OreFeatureConfig(DDFillerBlockTypes.LIMESTONE, LIMESTONE_GOLD_ORE, 9)).withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(8, 0, 0, 32))));
		biomeIn.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.withConfiguration(new OreFeatureConfig(DDFillerBlockTypes.LIMESTONE, LIMESTONE_REDSTONE_ORE, 8)).withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(32, 0, 0, 16))));
		biomeIn.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.withConfiguration(new OreFeatureConfig(DDFillerBlockTypes.LIMESTONE, LIMESTONE_DIAMOND_ORE, 8)).withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(4, 0, 0, 16))));
		biomeIn.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.withConfiguration(new OreFeatureConfig(DDFillerBlockTypes.LIMESTONE, LIMESTONE_SILVER_ORE, 4)).withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(4, 0, 0, 16))));
		biomeIn.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.withConfiguration(new OreFeatureConfig(DDFillerBlockTypes.LIMESTONE, LIMESTONE_LAPIS_ORE, 7)).withPlacement(Placement.COUNT_DEPTH_AVERAGE.configure(new DepthAverageConfig(4, 16, 16))));
	}

	public static void addFarmAnimals(Biome biomeIn) {
		biomeIn.addSpawn(EntityClassification.CREATURE, new Biome.SpawnListEntry(EntityType.SHEEP, 12, 4, 4));
		biomeIn.addSpawn(EntityClassification.CREATURE, new Biome.SpawnListEntry(EntityType.PIG, 10, 4, 4));
		biomeIn.addSpawn(EntityClassification.CREATURE, new Biome.SpawnListEntry(EntityType.CHICKEN, 10, 4, 4));
		biomeIn.addSpawn(EntityClassification.CREATURE, new Biome.SpawnListEntry(EntityType.COW, 8, 4, 4));
	}

	public static void addAmbientEntities(Biome biomeIn) {
		biomeIn.addSpawn(EntityClassification.AMBIENT, new Biome.SpawnListEntry(EntityType.BAT, 10, 8, 8));
	}

	public static void addCommonEntities(Biome biomeIn) {
		addAmbientEntities(biomeIn);
		addMonsters(biomeIn);
		addFarmAnimals(biomeIn);
	}

	public static void addMonsters(Biome biomeIn) {
		biomeIn.addSpawn(EntityClassification.MONSTER, new Biome.SpawnListEntry(EntityType.SPIDER, 100, 4, 4));
		biomeIn.addSpawn(EntityClassification.MONSTER, new Biome.SpawnListEntry(EntityType.ZOMBIE, 95, 4, 4));
		biomeIn.addSpawn(EntityClassification.MONSTER, new Biome.SpawnListEntry(EntityType.ZOMBIE_VILLAGER, 5, 1, 1));
		biomeIn.addSpawn(EntityClassification.MONSTER, new Biome.SpawnListEntry(EntityType.SKELETON, 100, 4, 4));
		biomeIn.addSpawn(EntityClassification.MONSTER, new Biome.SpawnListEntry(EntityType.CREEPER, 100, 4, 4));
		biomeIn.addSpawn(EntityClassification.MONSTER, new Biome.SpawnListEntry(EntityType.SLIME, 100, 4, 4));
		biomeIn.addSpawn(EntityClassification.MONSTER, new Biome.SpawnListEntry(EntityType.ENDERMAN, 10, 1, 4));
		biomeIn.addSpawn(EntityClassification.MONSTER, new Biome.SpawnListEntry(EntityType.WITCH, 5, 1, 1));
	}

	/**
	 * @note this method add features to ALL the biomes, or to specific vanilla biomes
	 */
	public static void addVanillaBiomeFeatures() {
		for (Biome biome : ForgeRegistries.BIOMES) {
			if (!biome.getCategory().equals(Biome.Category.NETHER) && !biome.getCategory().equals(Biome.Category.THEEND) && !(biome instanceof AbstractCaveBiome)) {
				addCarvers(biome);
				addCommonEntities(biome);
				if (biome == BGBiomes.DEFAULT_CAVE.get()) {
					addGlowshrooms(biome);
				}
			}
		}
	}
}