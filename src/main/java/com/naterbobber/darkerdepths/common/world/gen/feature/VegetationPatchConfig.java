package com.naterbobber.darkerdepths.common.world.gen.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.naterbobber.darkerdepths.common.math.IntProvider;
import com.naterbobber.darkerdepths.core.util.CaveSurface;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.gen.blockstateprovider.BlockStateProvider;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.IFeatureConfig;

import java.util.function.Supplier;

//<>

public class VegetationPatchConfig implements IFeatureConfig {
    public static final Codec<VegetationPatchConfig> CODEC = RecordCodecBuilder.create((instance) -> {
        return instance.group(ResourceLocation.CODEC.fieldOf("replaceable").forGetter((config) -> {
            return config.replaceable;
        }), BlockStateProvider.CODEC.fieldOf("ground_state").forGetter((config) -> {
            return config.groundState;
        }), ConfiguredFeature.field_236264_b_.fieldOf("vegetation_feature").forGetter((config) -> {
            return config.vegetationFeature;
        }), CaveSurface.CODEC.fieldOf("surface").forGetter((config) -> {
            return config.surface;
        }), IntProvider.createValidatingCodec(1, 128).fieldOf("depth").forGetter((config) -> {
            return config.depth;
        }), Codec.floatRange(0.0F, 1.0F).fieldOf("extra_bottom_block_chance").forGetter((config) -> {
            return config.extraBottomBlockChance;
        }), Codec.intRange(1, 256).fieldOf("vertical_range").forGetter((config) -> {
            return config.verticalRange;
        }), Codec.floatRange(0.0F, 1.0F).fieldOf("vegetation_chance").forGetter((config) -> {
            return config.vegetationChance;
        }), IntProvider.CODEC.fieldOf("xz_radius").forGetter((config) -> {
            return config.xzRadius;
        }), Codec.floatRange(0.0F, 1.0F).fieldOf("extra_edge_column_chance").forGetter((config) -> {
            return config.extraEdgeColumnChance;
        })).apply(instance, VegetationPatchConfig::new);
    });
    public final ResourceLocation replaceable;
    public final BlockStateProvider groundState;
    public final Supplier<ConfiguredFeature<?, ?>> vegetationFeature;
    public final CaveSurface surface;
    public final IntProvider depth;
    public final float extraBottomBlockChance;
    public final int verticalRange;
    public final float vegetationChance;
    public final IntProvider xzRadius;
    public final float extraEdgeColumnChance;

    public VegetationPatchConfig(ResourceLocation replaceable, BlockStateProvider groundState, Supplier<ConfiguredFeature<?, ?>> vegetationFeature, CaveSurface surface, IntProvider depth, float extraBottomBlockChance, int verticalRange, float vegetationChance, IntProvider xzRadius, float extraEdgeColumnChance) {
        this.replaceable = replaceable;
        this.groundState = groundState;
        this.vegetationFeature = vegetationFeature;
        this.surface = surface;
        this.depth = depth;
        this.extraBottomBlockChance = extraBottomBlockChance;
        this.verticalRange = verticalRange;
        this.vegetationChance = vegetationChance;
        this.xzRadius = xzRadius;
        this.extraEdgeColumnChance = extraEdgeColumnChance;
    }
}