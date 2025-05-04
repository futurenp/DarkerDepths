package com.naterbobber.darkerdepths.world.gen.features.config;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.placement.CaveSurface;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class CorrespondentLayersConfig implements FeatureConfiguration {
    public static final Codec<CorrespondentLayersConfig> CODEC = RecordCodecBuilder.create((p_161304_) -> {
        return p_161304_.group(TagKey.hashedCodec(Registries.BLOCK).fieldOf("replaceable").forGetter((config) -> {
            return config.replaceable;
        }), BlockStateProvider.CODEC.fieldOf("ground_state").forGetter((p_161322_) -> {
            return p_161322_.groundState;
        }), BlockStateProvider.CODEC.fieldOf("below_state").forGetter((config) -> {
            return config.belowState;
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
        }), IntProvider.CODEC.fieldOf("xz_radius").forGetter((p_161308_) -> {
            return p_161308_.xzRadius;
        }), Codec.floatRange(0.0F, 1.0F).fieldOf("extra_edge_column_chance").forGetter((p_161306_) -> {
            return p_161306_.extraEdgeColumnChance;
        })).apply(p_161304_, CorrespondentLayersConfig::new);
    });
    public final TagKey<Block> replaceable;
    public final BlockStateProvider groundState;
    public final BlockStateProvider belowState;
    public final Holder<PlacedFeature> vegetationFeature;
    public final CaveSurface surface;
    public final IntProvider depth;
    public final float extraBottomBlockChance;
    public final int verticalRange;
    public final float vegetationChance;
    public final IntProvider xzRadius;
    public final float extraEdgeColumnChance;

    public CorrespondentLayersConfig(TagKey<Block> replaceableTags, BlockStateProvider groundState, BlockStateProvider belowState, Holder<PlacedFeature> feature, CaveSurface p_161296_, IntProvider p_161297_, float p_161298_, int p_161299_, float p_161300_, IntProvider p_161301_, float p_161302_) {
        this.replaceable = replaceableTags;
        this.groundState = groundState;
        this.belowState = belowState;
        this.vegetationFeature = feature;
        this.surface = p_161296_;
        this.depth = p_161297_;
        this.extraBottomBlockChance = p_161298_;
        this.verticalRange = p_161299_;
        this.vegetationChance = p_161300_;
        this.xzRadius = p_161301_;
        this.extraEdgeColumnChance = p_161302_;
    }
}
