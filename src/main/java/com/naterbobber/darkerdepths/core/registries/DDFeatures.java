package com.naterbobber.darkerdepths.core.registries;

import com.naterbobber.darkerdepths.common.world.gen.feature.*;
import com.naterbobber.darkerdepths.common.world.gen.feature.BlobReplacementConfig;
import com.naterbobber.darkerdepths.core.DDRegistries;
import com.naterbobber.darkerdepths.core.DarkerDepths;

import net.minecraft.world.gen.feature.*;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;

//<>

@Mod.EventBusSubscriber(modid = DarkerDepths.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DDFeatures {
	public static final DDRegistries HELPER = DarkerDepths.REGISTRIES;

	public static final RegistryObject<Feature<BlockClusterFeatureConfig>> SPELEOTHEM_FLOOR_FEATURE 	= HELPER.registerFeature("speleothem_floor_feature", () -> new SpeleothemFeature(false ,BlockClusterFeatureConfig.CODEC));
	public static final RegistryObject<Feature<BlockClusterFeatureConfig>> SPELEOTHEM_CEILING_FEATURE 	= HELPER.registerFeature("speleothem_ceiling_feature", () -> new SpeleothemFeature(true ,BlockClusterFeatureConfig.CODEC));
	public static final RegistryObject<Feature<GemstonePlacementConfig>> GEMSTONE_PLACEMENT_FEATURE 	= HELPER.registerFeature("gemstone_placement", () -> new GemstonePlacementFeature(GemstonePlacementConfig.CODEC));
	public static final RegistryObject<Feature<BlobReplacementConfig>> BLOB_REPLACEMENT_FEATURE 		= HELPER.registerFeature("blob_replacement", () -> new BlobReplacementFeature(BlobReplacementConfig.CODEC));
	public static final RegistryObject<Feature<SimpleBlockConfig>> SIMPLE_BLOCK 						= HELPER.registerFeature("simple_block", () -> new SimpleBlockFeature(SimpleBlockConfig.CODEC));
	public static final RegistryObject<Feature<NoFeatureConfig>> CAVE_FOSSILS 							= HELPER.registerFeature("cave_fossils", () -> new CaveFossilFeature(NoFeatureConfig.CODEC));
	public static final RegistryObject<Feature<CavePillarConfig>> CAVE_PILLAR_FEATURE 					= HELPER.registerFeature("cave_pillar", () -> new CavePillarFeature(CavePillarConfig.CODEC));
	public static final RegistryObject<Feature<VegetationPatchConfig>> VEGETATION_PATCH 				= HELPER.registerFeature("vegetation_patch", () -> new VegetationPatchFeature(VegetationPatchConfig.CODEC));
	public static final RegistryObject<Feature<VegetationPatchConfig>> WATERLOGGED_VEGETATION_PATCH 	= HELPER.registerFeature("waterlogged_vegetation_patch", () -> new WaterloggedVegetationPatchFeature(VegetationPatchConfig.CODEC));
	public static final RegistryObject<Feature<VegetationPatchConfig>> LAVA_VEGETATION_PATCH 			= HELPER.registerFeature("lava_vegetation_patch", () -> new LavaVegetationPatchFeature(VegetationPatchConfig.CODEC));
	public static final RegistryObject<Feature<NoFeatureConfig>> PETRIFIED_LOG_BRANCH					= HELPER.registerFeature("petrified_log_branch", () -> new PetrifiedLogBranchFeature(NoFeatureConfig.CODEC));
	public static final RegistryObject<Feature<BlockStateProvidingFeatureConfig>> VEGETATION_FEATURE	= HELPER.registerFeature("vegetation_feature", () -> new VegetationFeature(BlockStateProvidingFeatureConfig.CODEC));
	public static final RegistryObject<Feature<HugeGlowshroomConfig>> HUGE_GLOWSHROOM_FEATURE 			= HELPER.registerFeature("huge_glowshroom", () -> new HugeGlowshroomFeature(HugeGlowshroomConfig.CODEC));
	public static final RegistryObject<Feature<GrowingPlantConfig>> GROWING_PLANT 						= HELPER.registerFeature("glowvine", () -> new GrowingPlantFeature(GrowingPlantConfig.CODEC));
	public static final RegistryObject<Feature<NoFeatureConfig>> GEISER_FEATURE							= HELPER.registerFeature("geiser_feature", () -> new GeiserFeature(NoFeatureConfig.CODEC));
}