package com.naterbobber.darkerdepths.worldgen.feature.config;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.placement.CaveSurface;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

import java.util.List;

public class CorrespondentLayersConfig implements FeatureConfiguration {
    public static final Codec<CorrespondentLayersConfig> CODEC = RecordCodecBuilder.create((p_161304_) -> {
        return p_161304_.group(TagKey.hashedCodec(Registries.BLOCK).fieldOf("replaceable").forGetter((config) -> {
                    return config.replaceable;
                }), BlockStateProvider.CODEC.listOf().fieldOf("layers").forGetter((config) -> {
                    return ImmutableList.copyOf(config.layers);
                }), PlacedFeature.CODEC.fieldOf("vegetation_feature").forGetter((p_161320_) -> {
                    return p_161320_.vegetationFeature;
                }), CaveSurface.CODEC.fieldOf("surface").forGetter((p_161318_) -> {
                    return p_161318_.surface;
                }), IntProvider.codec(1, 128).fieldOf("depth").forGetter((p_161316_) -> {
                    return p_161316_.depth;
                }), Codec.floatRange(0.0F, 1.0F).fieldOf("extra_bottom_block_chance").forGetter((p_161314_) -> {
                    return p_161314_.extraBottomBlockChance;
                }), Codec.intRange(1, 256).fieldOf("vertical_range").forGetter((p_161312_) -> {
                    return p_161312_.verticalRange;
                }), Codec.floatRange(0.0F, 1.0F).fieldOf("vegetation_chance").forGetter((p_161310_) -> {
                    return p_161310_.vegetationChance;
                }), IntProvider.codec(1, 256).fieldOf("xz_radius").forGetter((p_161308_) -> {
                    return p_161308_.xzRadius;
                }), Codec.floatRange(0.0F, 1.0F).fieldOf("extra_edge_column_chance").forGetter((p_161306_) -> {
                    return p_161306_.extraEdgeColumnChance;
                }), Codec.BOOL.fieldOf("xz_replace").forGetter((config) -> config.xzReplace))
                .apply(p_161304_, CorrespondentLayersConfig::new);
    });
    public final TagKey<Block> replaceable;
    public final List<BlockStateProvider> layers;
    public final Holder<PlacedFeature> vegetationFeature;
    public final CaveSurface surface;
    public final IntProvider depth;
    public final float extraBottomBlockChance;
    public final int verticalRange;
    public final float vegetationChance;
    public final IntProvider xzRadius;
    public final float extraEdgeColumnChance;
    public final boolean xzReplace;

    public CorrespondentLayersConfig(TagKey<Block> replaceableTags, List<BlockStateProvider> layers, Holder<PlacedFeature> vegetationFeature, CaveSurface surface, IntProvider depth, float extraBottomBlockChance, int verticalRange, float vegetationChance, IntProvider xzRadius, float extraEdgeColumnChance, boolean xzReplace) {
        this.replaceable = replaceableTags;
        this.layers = layers;
        this.vegetationFeature = vegetationFeature;
        this.surface = surface;
        this.depth = depth;
        this.extraBottomBlockChance = extraBottomBlockChance;
        this.verticalRange = verticalRange;
        this.vegetationChance = vegetationChance;
        this.xzRadius = xzRadius;
        this.extraEdgeColumnChance = extraEdgeColumnChance;
        this.xzReplace = xzReplace;
    }
}
