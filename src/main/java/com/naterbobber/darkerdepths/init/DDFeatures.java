package com.naterbobber.darkerdepths.init;

import com.naterbobber.darkerdepths.DarkerDepths;
import com.naterbobber.darkerdepths.worldgen.feature.config.*;
import com.naterbobber.darkerdepths.worldgen.feature.features.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.LargeDripstoneConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.VegetationPatchConfiguration;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class DDFeatures {

    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(Registries.FEATURE, DarkerDepths.MOD_ID);

    public static final DeferredHolder<Feature<?>, ReplaceListFeature> REPLACE_LIST = FEATURES.register("replace_list", () -> new ReplaceListFeature(ReplaceListConfig.CODEC));
    public static final DeferredHolder<Feature<?>, ReplaceListLayeredFeature> REPLACE_LIST_LAYERED = FEATURES.register("replace_list_layered", () -> new ReplaceListLayeredFeature(ReplaceListLayeredConfig.CODEC));
    public static final DeferredHolder<Feature<?>, RandomSpreadFeature> RANDOM_GLOWSHROOM_PATCHES = FEATURES.register("random_glowshroom_patches", () -> new RandomSpreadFeature(NoneFeatureConfiguration.CODEC));
    public static final DeferredHolder<Feature<?>, GemstoneFeature> GEMSTONE = FEATURES.register("gemstone", () -> new GemstoneFeature(NoneFeatureConfiguration.CODEC));
    public static final DeferredHolder<Feature<?>, HugeGlowshroomFeature> HUGE_GLOWSHROOM = FEATURES.register("huge_glowshroom", () -> new HugeGlowshroomFeature(NoneFeatureConfiguration.CODEC));
    public static final DeferredHolder<Feature<?>, PetrifiedBranchFeature> PETRIFIED_BRANCH = FEATURES.register("petrified_branch", () -> new PetrifiedBranchFeature(PetrifiedBranchConfig.CODEC));
    public static final DeferredHolder<Feature<?>, LavaVegetationPatchFeature> LAVA_VEGETATION_PATCH_FEATURE = FEATURES.register("lava_vegetation_patch", () -> new LavaVegetationPatchFeature(VegetationPatchConfiguration.CODEC));
    public static final DeferredHolder<Feature<?>, CorrespondentLayersFeature> CORRESPONDENT_LAYER = FEATURES.register("correspondent_layer", () -> new CorrespondentLayersFeature(CorrespondentLayersConfig.CODEC));
    public static final DeferredHolder<Feature<?>, AridBoulderFeature> ARID_BOULDER = FEATURES.register("arid_boulder", () -> new AridBoulderFeature(NoneFeatureConfiguration.CODEC));
    public static final DeferredHolder<Feature<?>, CatacombsLavaLiningFeature> CATACOMBS_LAVA_LINING = FEATURES.register("catacombs_lava_lining", () -> new CatacombsLavaLiningFeature(NoneFeatureConfiguration.CODEC));
    public static final DeferredHolder<Feature<?>, LimestoneStripeFeature> DUSKROCK_STRIPE = FEATURES.register("limestone_stripe", () -> new LimestoneStripeFeature(NoneFeatureConfiguration.CODEC));
    public static final DeferredHolder<Feature<?>, GeyserFeature> GEYSER_UNDER_FLUID = FEATURES.register("geyser_under_fluid", () -> new GeyserFeature(SimpleBlockConfiguration.CODEC));
    public static final DeferredHolder<Feature<?>, LargeMoltenPillarFeature> LARGE_MOLTEN_PILLAR = FEATURES.register("large_molten_pillar", () -> new LargeMoltenPillarFeature(LargeDripstoneConfiguration.CODEC));
    public static final DeferredHolder<Feature<?>, ScorcherFeature> SCORCHER_PLACER = FEATURES.register("scorcher_placer", () -> new ScorcherFeature(ScorcherFeatureConfig.CODEC));
    public static final DeferredHolder<Feature<?>, RandomFloorPlacementFeature> RANDOM_FLOOR_PLACEMENT = FEATURES.register("random_floor_placement", () -> new RandomFloorPlacementFeature(RandomFloorPlacementConfig.CODEC));
    public static final DeferredHolder<Feature<?>, SmallBoulderFeature> SMALL_BOULDER = FEATURES.register("small_boulder", () -> new SmallBoulderFeature(SmallBoulderConfiguration.CODEC));

}
