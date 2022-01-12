package com.naterbobber.darkerdepths.init;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.naterbobber.darkerdepths.DarkerDepths;
import com.naterbobber.darkerdepths.blocks.AshBlock;
import com.naterbobber.darkerdepths.blocks.GlowshroomBlock;
import com.naterbobber.darkerdepths.world.gen.features.config.ReplaceListConfig;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.ReplaceBlobsFeature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.ReplaceSphereConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SpringConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.VegetationPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import net.minecraft.world.level.levelgen.placement.CaveSurface;
import net.minecraft.world.level.material.Fluids;

public class DDConfiguredFeatures {

    public static final ImmutableList<BlockState> OVERWORLD_REPLACEABLES    = ImmutableList.of(Blocks.STONE.defaultBlockState(), Blocks.ANDESITE.defaultBlockState(), Blocks.GRANITE.defaultBlockState(), Blocks.DIORITE.defaultBlockState(), Blocks.DIRT.defaultBlockState(), DDBlocks.SHALE.get().defaultBlockState(), Blocks.GRAVEL.defaultBlockState(), Blocks.DEEPSLATE.defaultBlockState(), Blocks.TUFF.defaultBlockState(), Blocks.CALCITE.defaultBlockState());

    public static final ConfiguredFeature<?, ?> GLOWSHROOM_PATCH = registerConfiguredFeature("glowshroom_patch", Feature.RANDOM_PATCH.configured(FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK.configured(new SimpleBlockConfiguration(BlockStateProvider.simple(DDBlocks.GLOWSHROOM.get().defaultBlockState().setValue(GlowshroomBlock.CLUSTERS_1_3, 1)))))));
    public static final ConfiguredFeature<?, ?> HUGE_GLOWSHROOM_PLANTED = registerConfiguredFeature("huge_glowshroom", DDFeatures.HUGE_GLOWSHROOM.get().configured(FeatureConfiguration.NONE));
    public static final ConfiguredFeature<?, ?> AMBERS_PLACEMENT = registerConfiguredFeature("amber_placement", DDFeatures.GEMSTONE.get().configured(FeatureConfiguration.NONE));
    public static final ConfiguredFeature<?, ?> MOLTEN_SPRING = registerConfiguredFeature("molten_spring", Feature.SPRING.configured(new SpringConfiguration(Fluids.LAVA.defaultFluidState(), false, 4, 1, ImmutableSet.of(Blocks.STONE, Blocks.GRANITE, Blocks.DIORITE, Blocks.ANDESITE, Blocks.DEEPSLATE, Blocks.TUFF, Blocks.CALCITE, Blocks.DIRT, DDBlocks.SHALE.get()))));
    public static final ConfiguredFeature<?, ?> ASH_PLACEMENTS = registerConfiguredFeature("ash_placement", Feature.SIMPLE_BLOCK.configured(new SimpleBlockConfiguration(new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder().add(DDBlocks.ASH.get().defaultBlockState().setValue(AshBlock.LAYERS, 1), 25).add(DDBlocks.ASH.get().defaultBlockState().setValue(AshBlock.LAYERS, 2), 15).build()))));
    public static final ConfiguredFeature<?, ?> MOLTEN_POOL = registerConfiguredFeature("molten_pool", DDFeatures.LAVA_VEGETATION_PATCH_FEATURE.get().configured(new VegetationPatchConfiguration(BlockTags.BASE_STONE_OVERWORLD.getName(), BlockStateProvider.simple(DDBlocks.ASH_BLOCK.get().defaultBlockState()), ASH_PLACEMENTS::placed, CaveSurface.FLOOR, ConstantInt.of(1), 0.0F, 5, 0.8F, UniformInt.of(4, 7), 0.3F)));
    public static final ConfiguredFeature<?, ?> SHALE_PLACEMENT = registerConfiguredFeature("shale_placement", DDFeatures.REPLACE_LIST.get().configured(new ReplaceListConfig(OVERWORLD_REPLACEABLES, DDBlocks.SHALE.get().defaultBlockState(), UniformInt.of(6, 7))));
    public static final ConfiguredFeature<?, ?> ARIDROCK_PLACEMENT = registerConfiguredFeature("aridrock_placement", DDFeatures.REPLACE_LIST.get().configured(new ReplaceListConfig(OVERWORLD_REPLACEABLES, DDBlocks.ARIDROCK.get().defaultBlockState(), UniformInt.of(6, 7))));
    public static final ConfiguredFeature<?, ?> LIMESTONE_PLACEMENT = registerConfiguredFeature("limestone_placement", DDFeatures.REPLACE_LIST.get().configured(new ReplaceListConfig(OVERWORLD_REPLACEABLES, DDBlocks.LIMESTONE.get().defaultBlockState(), UniformInt.of(6, 7))));
    public static final ConfiguredFeature<?, ?> PETRIFIED_BRANCH = registerConfiguredFeature("petrified_branch", DDFeatures.PETRIFIED_BRANCH.get().configured(FeatureConfiguration.NONE));
    public static final ConfiguredFeature<?, ?> SOUL_SOIL = registerConfiguredFeature("soul_soil", DDFeatures.SOUL_SOIL.get().configured(FeatureConfiguration.NONE));

    public static <C extends FeatureConfiguration, F extends Feature<C>, CF extends ConfiguredFeature<C, F>> CF registerConfiguredFeature(String key, CF configuredFeature) {
        ResourceLocation ID = new ResourceLocation(DarkerDepths.MODID, key);
        if (BuiltinRegistries.CONFIGURED_FEATURE.keySet().contains(ID))
            throw new IllegalStateException("The Configured Feature " + key + "already exists in the registry");

        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, ID, configuredFeature);
        return configuredFeature;
    }

}
