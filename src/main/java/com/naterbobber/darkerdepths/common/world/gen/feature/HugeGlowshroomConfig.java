package com.naterbobber.darkerdepths.common.world.gen.feature;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.world.gen.feature.IFeatureConfig;

import java.util.Set;

public class HugeGlowshroomConfig implements IFeatureConfig {
    public static final Codec<HugeGlowshroomConfig> CODEC = RecordCodecBuilder.create((instance) -> {
        return instance.group(BlockState.CODEC.fieldOf("stem").forGetter((config) -> {
            return config.stem;
        }), BlockState.CODEC.fieldOf("cap").forGetter((config) -> {
            return config.cap;
        })).apply(instance, HugeGlowshroomConfig::new);
    });
    public final BlockState stem;
    public final BlockState cap;

    public HugeGlowshroomConfig(BlockState stem, BlockState cap) {
        this.stem = stem;
        this.cap = cap;
    }
}
