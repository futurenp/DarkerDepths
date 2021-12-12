package com.naterbobber.darkerdepths.core.registries.worldgen;

import com.naterbobber.darkerdepths.common.world.gen.feature.*;
import com.naterbobber.darkerdepths.core.api.Registries;
import com.naterbobber.darkerdepths.core.DarkerDepths;

import net.minecraft.world.gen.feature.*;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;

//<>

@Mod.EventBusSubscriber(modid = DarkerDepths.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DDFeatures {
	public static final Registries HELPER = DarkerDepths.REGISTRIES;

	public static final RegistryObject<Feature<ReplaceBlobsFeatureConfig>> REPLACE_BLOBS 				= HELPER.registerFeature("replace_blobs", () -> new ReplaceBlobsFeature(ReplaceBlobsFeatureConfig.CODEC));
	public static final RegistryObject<Feature<GemstonePlacementConfig>> GEMSTONE_PLACEMENT_FEATURE 	= HELPER.registerFeature("gemstone_placement", () -> new GemstonePlacementFeature(GemstonePlacementConfig.CODEC));
	public static final RegistryObject<Feature<SimpleBlockConfig>> SIMPLE_BLOCK 						= HELPER.registerFeature("simple_block", () -> new SimpleBlockFeature(SimpleBlockConfig.CODEC));
	public static final RegistryObject<Feature<NoFeatureConfig>> CAVE_FOSSILS 							= HELPER.registerFeature("cave_fossils", () -> new CaveFossilFeature(NoFeatureConfig.field_236558_a_));
	public static final RegistryObject<Feature<CavePillarConfig>> CRYSTAL_PEAK 							= HELPER.registerFeature("crystal_peak", () -> new CrystalPeakFeature(CavePillarConfig.CODEC));
	public static final RegistryObject<Feature<VegetationPatchConfig>> VEGETATION_PATCH 				= HELPER.registerFeature("vegetation_patch", () -> new VegetationPatchFeature(VegetationPatchConfig.CODEC));
	public static final RegistryObject<Feature<VegetationPatchConfig>> WATERLOGGED_VEGETATION_PATCH 	= HELPER.registerFeature("waterlogged_vegetation_patch", () -> new WaterloggedVegetationPatchFeature(VegetationPatchConfig.CODEC));
	public static final RegistryObject<Feature<VegetationPatchConfig>> LAVA_VEGETATION_PATCH 			= HELPER.registerFeature("lava_vegetation_patch", () -> new LavaVegetationPatchFeature(VegetationPatchConfig.CODEC));
	public static final RegistryObject<Feature<NoFeatureConfig>> PETRIFIED_LOG_BRANCH					= HELPER.registerFeature("petrified_log_branch", () -> new PetrifiedLogBranchFeature(NoFeatureConfig.field_236558_a_));
	public static final RegistryObject<Feature<BlockStateProvidingFeatureConfig>> VEGETATION_FEATURE	= HELPER.registerFeature("vegetation_feature", () -> new VegetationFeature(BlockStateProvidingFeatureConfig.field_236453_a_));
	public static final RegistryObject<Feature<HugeGlowshroomConfig>> HUGE_GLOWSHROOM_FEATURE 			= HELPER.registerFeature("huge_glowshroom", () -> new HugeGlowshroomFeature(HugeGlowshroomConfig.CODEC));
	public static final RegistryObject<Feature<GrowingPlantConfig>> GROWING_PLANT 						= HELPER.registerFeature("growing_plant", () -> new GrowingPlantFeature(GrowingPlantConfig.CODEC));
	public static final RegistryObject<Feature<NoFeatureConfig>> GEYSER_FEATURE 						= HELPER.registerFeature("geyser_feature", () -> new GeyserFeature(NoFeatureConfig.field_236558_a_));
	public static final RegistryObject<Feature<SpeleothemConfig>> SPELEOTHEM_FEATURE					= HELPER.registerFeature("speleothem_feature", () -> new SpeleothemFeature(SpeleothemConfig.CODEC));
	public static final RegistryObject<Feature<BaseTreeFeatureConfig>> TREE								= HELPER.registerFeature("tree", () -> new DDTreeFeature(BaseTreeFeatureConfig.CODEC));
	public static final RegistryObject<Feature<RootSystemFeatureConfig>> ROOT_SYSTEM					= HELPER.registerFeature("root_system", () -> new RootSystemFeature(RootSystemFeatureConfig.CODEC));
}