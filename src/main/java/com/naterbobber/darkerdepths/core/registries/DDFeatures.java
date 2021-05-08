package com.naterbobber.darkerdepths.core.registries;

import com.naterbobber.darkerdepths.common.world.gen.feature.*;
import com.naterbobber.darkerdepths.common.world.gen.feature.BlobReplacementConfig;
import com.naterbobber.darkerdepths.core.DDRegistryHelper;
import com.naterbobber.darkerdepths.core.DarkerDepths;

import net.minecraft.world.gen.feature.*;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;

//<>

@Mod.EventBusSubscriber(modid = DarkerDepths.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DDFeatures {
	public static final DDRegistryHelper HELPER = DarkerDepths.REGISTRY_HELPER;

	public static final RegistryObject<Feature<BlockClusterFeatureConfig>> SPELEOTHEM_FLOOR_FEATURE 	= HELPER.registerFeature("speleothem_floor_feature", () -> new SpeleothemFeature(false ,BlockClusterFeatureConfig.CODEC));
	public static final RegistryObject<Feature<BlockClusterFeatureConfig>> SPELEOTHEM_CEILING_FEATURE 	= HELPER.registerFeature("speleothem_ceiling_feature", () -> new SpeleothemFeature(true ,BlockClusterFeatureConfig.CODEC));
	public static final RegistryObject<Feature<GemstonePlacementConfig>> GEMSTONE_PLACEMENT_FEATURE 	= HELPER.registerFeature("gemstone_placement", () -> new GemstonePlacementFeature(GemstonePlacementConfig.CODEC));
	public static final RegistryObject<Feature<BlobReplacementConfig>> BLOB_REPLACEMENT_FEATURE 		= HELPER.registerFeature("blob_replacement", () -> new BlobReplacementFeature(BlobReplacementConfig.CODEC));
	public static final RegistryObject<Feature<SimpleBlockConfig>> SIMPLE_BLOCK_FEATURE 				= HELPER.registerFeature("simple_block", () -> new SimpleBlockFeature(SimpleBlockConfig.CODEC));
	public static final RegistryObject<Feature<NoFeatureConfig>> CAVE_FOSSILS 							= HELPER.registerFeature("cave_fossils", () -> new CaveFossilFeature(NoFeatureConfig.CODEC));
	public static final RegistryObject<Feature<CavePillarConfig>> CAVE_PILLAR_FEATURE 					= HELPER.registerFeature("cave_pillar", () -> new CavePillarFeature(CavePillarConfig.CODEC));
}