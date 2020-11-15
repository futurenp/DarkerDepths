package com.naterbobber.darkerdepths.common.world.gen;

import com.google.common.collect.ImmutableSet;
import com.naterbobber.darkerdepths.common.blocks.Speleothem;
import com.naterbobber.darkerdepths.core.registries.DDBlocks;
import com.naterbobber.darkerdepths.core.registries.DDFeatures;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.blockplacer.SimpleBlockPlacer;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.BlockClusterFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.placement.ChanceRangeConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.registries.ForgeRegistries;

//<>

public class VanillaBiomeFeatures {
	public static final BlockState GLOWSHROOM 			= DDBlocks.GLOWSHROOM.get().getDefaultState();
	public static final BlockState GLOWSHROOM_CAP 		= DDBlocks.GLOWSHROOM_CAP.get().getDefaultState();
	public static final BlockState SPELEOTHEM_FLOOR		= DDBlocks.SPELEOTHEM.get().getDefaultState();
	public static final BlockState SPELEOTHEM_CEILING	= DDBlocks.SPELEOTHEM.get().getDefaultState().with(Speleothem.HANGING, true);
	public static final BlockClusterFeatureConfig GLOWSHROOM_CONFIG 		= (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(GLOWSHROOM), new SimpleBlockPlacer())).tries(64).func_227317_b_().build();
	public static final BlockClusterFeatureConfig GLOWSHROOM_CAP_CONFIG 	= (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(GLOWSHROOM_CAP), new SimpleBlockPlacer())).tries(3).whitelist(ImmutableSet.of(DDBlocks.SHALE.get().getBlock())).func_227317_b_().build();
	public static final BlockClusterFeatureConfig SPELEOTHEM_FLOOR_CONFIG 	= (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(SPELEOTHEM_FLOOR), SimpleBlockPlacer.field_236447_c_)).tries(64).xSpread(8).ySpread(8).zSpread(8).func_227317_b_().whitelist(ImmutableSet.of(Blocks.STONE, Blocks.ANDESITE, Blocks.DIORITE, Blocks.GRANITE, DDBlocks.SHALE.get(), DDBlocks.LIMESTONE.get())).build();
	public static final BlockClusterFeatureConfig SPELEOTHEM_CEILING_CONFIG = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(SPELEOTHEM_CEILING), SimpleBlockPlacer.field_236447_c_)).tries(64).xSpread(8).ySpread(8).zSpread(8).func_227317_b_().whitelist(ImmutableSet.of(Blocks.STONE, Blocks.ANDESITE, Blocks.DIORITE, Blocks.GRANITE, DDBlocks.SHALE.get(), DDBlocks.LIMESTONE.get())).build();
	
	public static void addCaveFeatures(Biome biomeIn) {
		biomeIn.addFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, Feature.RANDOM_PATCH.withConfiguration(GLOWSHROOM_CONFIG).withPlacement(Placement.CHANCE_RANGE.configure(new ChanceRangeConfig(4, 0, 0, 64))));
		biomeIn.addFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, Feature.RANDOM_PATCH.withConfiguration(GLOWSHROOM_CAP_CONFIG).withPlacement(Placement.CHANCE_RANGE.configure(new ChanceRangeConfig(4, 0, 0, 64))));
		biomeIn.addFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, DDFeatures.SPELEOTHEM_FLOOR_FEATURE.get().withConfiguration(SPELEOTHEM_FLOOR_CONFIG).withPlacement(Placement.CHANCE_RANGE.configure(new ChanceRangeConfig(50, 16, 16, 54))));
		biomeIn.addFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, DDFeatures.SPELEOTHEM_CEILING_FEATURE.get().withConfiguration(SPELEOTHEM_CEILING_CONFIG).withPlacement(Placement.CHANCE_RANGE.configure(new ChanceRangeConfig(50, 16, 16, 54))));
	}
	
	public static void addBiomeFeatures() {
		for (Biome biome : ForgeRegistries.BIOMES) {
			if (!biome.getCategory().equals(Biome.Category.NETHER) && !biome.getCategory().equals(Biome.Category.THEEND)) {
				addCaveFeatures(biome);
			}
		}
	}
}