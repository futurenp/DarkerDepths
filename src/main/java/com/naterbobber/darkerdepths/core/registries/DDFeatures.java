package com.naterbobber.darkerdepths.core.registries;

import com.naterbobber.darkerdepths.common.world.gen.feature.*;
import com.naterbobber.darkerdepths.core.DarkerDepths;
import com.naterbobber.darkerdepths.core.RegistryHelper;

import net.minecraft.world.gen.feature.BlockClusterFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

//<>

@Mod.EventBusSubscriber(bus = Bus.MOD)
public class DDFeatures {
	public static final RegistryHelper HELPER = DarkerDepths.REGISTRY_HELPER;

	public static final RegistryObject<Feature<BlockClusterFeatureConfig>> SPELEOTHEM_FLOOR_FEATURE 	= HELPER.createFeature("speleothem_floor_feature", () -> new SpeleothemFeature(false ,BlockClusterFeatureConfig.field_236587_a_));
	public static final RegistryObject<Feature<BlockClusterFeatureConfig>> SPELEOTHEM_CEILING_FEATURE 	= HELPER.createFeature("speleothem_ceiling_feature", () -> new SpeleothemFeature(true ,BlockClusterFeatureConfig.field_236587_a_));
	public static final RegistryObject<Feature<GemstonePlacementConfig>> GEMSTONE_PLACEMENT_FEATURE 	= HELPER.createFeature("gemstone_placement", () -> new GemstonePlacementFeature(GemstonePlacementConfig.CODEC));
	public static final RegistryObject<Feature<BlobReplacementConfig>> BLOB_REPLACEMENT_FEATURE 		= HELPER.createFeature("blob_replacement", () -> new BlobReplacementFeature(BlobReplacementConfig.CODEC));
	public static final RegistryObject<Feature<SimpleBlockConfig>> SIMPLE_BLOCK_FEATURE 				= HELPER.createFeature("simple_block", () -> new SimpleBlockFeature(SimpleBlockConfig.CODEC));
	public static final RegistryObject<Feature<NoFeatureConfig>> CAVE_FOSSILS 							= HELPER.createFeature("cave_fossils", () -> new CaveFossilFeature(NoFeatureConfig.field_236558_a_));
	public static final RegistryObject<Feature<CavePillarConfig>> CAVE_PILLAR_FEATURE 					= HELPER.createFeature("cave_pillar", () -> new CavePillarFeature(CavePillarConfig.CODEC));

}