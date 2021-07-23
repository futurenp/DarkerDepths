package com.naterbobber.darkerdepths.common.world.gen.feature;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.naterbobber.darkerdepths.common.math.IntProvider;
import net.minecraft.block.BlockState;
import net.minecraft.world.gen.feature.IFeatureConfig;

import java.util.List;
import java.util.Set;

//<>

public class ReplaceBlobsFeatureConfig implements IFeatureConfig {
    public static final Codec<ReplaceBlobsFeatureConfig> CODEC = RecordCodecBuilder.create((instance) -> {
        return instance.group(BlockState.CODEC.listOf().fieldOf("target").forGetter((config) -> {
            return ImmutableList.copyOf(config.target);
        }), BlockState.CODEC.fieldOf("state").forGetter((config) -> {
            return config.state;
        }), IntProvider.createValidatingCodec(0, 12).fieldOf("radius").forGetter((config) -> {
            return config.radius;
        })).apply(instance, ReplaceBlobsFeatureConfig::new);
    });
    public final Set<BlockState> target;
    public final BlockState state;
    public final IntProvider radius;

    public ReplaceBlobsFeatureConfig(List<BlockState> target, BlockState state, IntProvider radius) {
        this(ImmutableSet.copyOf(target), state, radius);
    }

    public ReplaceBlobsFeatureConfig(Set<BlockState> target, BlockState state, IntProvider radius) {
        this.target = target;
        this.state = state;
        this.radius = radius;
    }

    public IntProvider getRadius() {
        return this.radius;
    }
}