package com.naterbobber.darkerdepths.common.world.gen.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.gen.blockstateprovider.BlockStateProvider;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.IFeatureConfig;

import java.util.function.Supplier;

//<>

public class RootSystemFeatureConfig implements IFeatureConfig {
    public static final Codec<RootSystemFeatureConfig> CODEC = RecordCodecBuilder.create(instance -> {
        return instance.group(ConfiguredFeature.field_236264_b_.fieldOf("feature").forGetter(config -> {
            return config.feature;
        }), Codec.intRange(1, 64).fieldOf("required_vertical_space_for_tree").forGetter(config -> {
            return config.requiredVerticalSpaceForTree;
        }), Codec.intRange(1, 64).fieldOf("root_radius").forGetter(config -> {
            return config.rootRadius;
        }), ResourceLocation.CODEC.fieldOf("root_replaceable").forGetter(config -> {
            return config.rootReplaceable;
        }), BlockStateProvider.CODEC.fieldOf("root_state_provider").forGetter(config -> {
            return config.rootStateProvider;
        }), Codec.intRange(1, 256).fieldOf("root_placement_attempts").forGetter(config -> {
            return config.rootPlacementAttempts;
        }), Codec.intRange(1, 4096).fieldOf("root_column_max_height").forGetter(config -> {
            return config.maxRootColumnHeight;
        }), Codec.intRange(1, 64).fieldOf("hanging_root_radius").forGetter(config -> {
            return config.hangingRootRadius;
        }), Codec.intRange(0, 16).fieldOf("hanging_roots_vertical_span").forGetter(config -> {
            return config.hangingRootVerticalSpan;
        }), BlockStateProvider.CODEC.fieldOf("hanging_root_state_provider").forGetter(config -> {
            return config.hangingRootStateProvider;
        }), Codec.intRange(1, 256).fieldOf("hanging_root_placement_attempts").forGetter(config -> {
            return config.hangingRootPlacementAttempts;
        }), Codec.intRange(1, 64).fieldOf("allowed_vertical_water_for_tree").forGetter(config -> {
            return config.allowedVerticalWaterForTree;
        })).apply(instance, RootSystemFeatureConfig::new);
    });
    public final Supplier<ConfiguredFeature<?, ?>> feature;
    public final int requiredVerticalSpaceForTree;
    public final int rootRadius;
    public final ResourceLocation rootReplaceable;
    public final BlockStateProvider rootStateProvider;
    public final int rootPlacementAttempts;
    public final int maxRootColumnHeight;
    public final int hangingRootRadius;
    public final int hangingRootVerticalSpan;
    public final BlockStateProvider hangingRootStateProvider;
    public final int hangingRootPlacementAttempts;
    public final int allowedVerticalWaterForTree;

    public RootSystemFeatureConfig(Supplier<ConfiguredFeature<?, ?>> feature, int requiredVerticalSpaceForTree, int rootRadius, ResourceLocation rootReplaceable, BlockStateProvider rootStateProvider, int rootPlacementAttempts, int maxRootColumnHeight, int hangingRootRadius, int hangingRootVerticalSpan, BlockStateProvider hangingRootStateProvider, int hangingRootPlacementAttempts, int allowedVerticalWaterForTree) {
        this.feature = feature;
        this.requiredVerticalSpaceForTree = requiredVerticalSpaceForTree;
        this.rootRadius = rootRadius;
        this.rootReplaceable = rootReplaceable;
        this.rootStateProvider = rootStateProvider;
        this.rootPlacementAttempts = rootPlacementAttempts;
        this.maxRootColumnHeight = maxRootColumnHeight;
        this.hangingRootRadius = hangingRootRadius;
        this.hangingRootVerticalSpan = hangingRootVerticalSpan;
        this.hangingRootStateProvider = hangingRootStateProvider;
        this.hangingRootPlacementAttempts = hangingRootPlacementAttempts;
        this.allowedVerticalWaterForTree = allowedVerticalWaterForTree;
    }
}