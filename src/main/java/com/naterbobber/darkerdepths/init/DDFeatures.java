package com.naterbobber.darkerdepths.init;

import com.naterbobber.darkerdepths.DarkerDepths;
import com.naterbobber.darkerdepths.world.gen.features.AridBoulderFeature;
import com.naterbobber.darkerdepths.world.gen.features.CorrespondentLayersFeature;
import com.naterbobber.darkerdepths.world.gen.features.GemstoneFeature;
import com.naterbobber.darkerdepths.world.gen.features.GeyserFeature;
import com.naterbobber.darkerdepths.world.gen.features.HugeGlowshroomFeature;
import com.naterbobber.darkerdepths.world.gen.features.LavaVegetationPatchFeature;
import com.naterbobber.darkerdepths.world.gen.features.PetrifiedBranchFeature;
import com.naterbobber.darkerdepths.world.gen.features.RandomSpreadFeature;
import com.naterbobber.darkerdepths.world.gen.features.ReplaceListFeature;
import com.naterbobber.darkerdepths.world.gen.features.SoulSoilFeature;
import com.naterbobber.darkerdepths.world.gen.features.config.CorrespondentLayersConfig;
import com.naterbobber.darkerdepths.world.gen.features.config.PetrifiedBranchConfig;
import com.naterbobber.darkerdepths.world.gen.features.config.ReplaceListConfig;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.VegetationPatchConfiguration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = DarkerDepths.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DDFeatures {

    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, DarkerDepths.MODID);

    public static final RegistryObject<Feature<ReplaceListConfig>> REPLACE_LIST = FEATURES.register("replace_list", () -> new ReplaceListFeature(ReplaceListConfig.CODEC));
    public static final RegistryObject<Feature<NoneFeatureConfiguration>> RANDOM_GLOWSHROOM_PATCHES = FEATURES.register("random_glowshroom_patches", () -> new RandomSpreadFeature(NoneFeatureConfiguration.CODEC));
    public static final RegistryObject<Feature<NoneFeatureConfiguration>> GEMSTONE = FEATURES.register("gemstone", () -> new GemstoneFeature(NoneFeatureConfiguration.CODEC));
    public static final RegistryObject<Feature<NoneFeatureConfiguration>> HUGE_GLOWSHROOM = FEATURES.register("huge_glowshroom", () -> new HugeGlowshroomFeature(NoneFeatureConfiguration.CODEC));
    public static final RegistryObject<Feature<PetrifiedBranchConfig>> PETRIFIED_BRANCH = FEATURES.register("petrified_branch", () -> new PetrifiedBranchFeature(PetrifiedBranchConfig.CODEC));
    public static final RegistryObject<Feature<VegetationPatchConfiguration>> LAVA_VEGETATION_PATCH_FEATURE = FEATURES.register("lava_vegetation_patch", () -> new LavaVegetationPatchFeature(VegetationPatchConfiguration.CODEC));
    public static final RegistryObject<Feature<CorrespondentLayersConfig>> CORRESPONDENT_LAYER = FEATURES.register("correspondent_layer", () -> new CorrespondentLayersFeature(CorrespondentLayersConfig.CODEC));
    public static final RegistryObject<Feature<NoneFeatureConfiguration>> ARID_BOULDER = FEATURES.register("arid_boulder", () -> new AridBoulderFeature(NoneFeatureConfiguration.CODEC));

}
