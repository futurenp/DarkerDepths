package com.naterbobber.darkerdepths.common.world.gen.feature;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.world.gen.feature.FeatureSpread;
import net.minecraft.world.gen.feature.IFeatureConfig;

import java.util.List;
import java.util.Set;

//<>

public class BlobReplacementConfig implements IFeatureConfig {
    public static final Codec<BlobReplacementConfig> CODEC = RecordCodecBuilder.create((instance) -> {
        return instance.group(BlockState.CODEC.listOf().fieldOf("target").forGetter((config) -> {
            return ImmutableList.copyOf(config.target);
        }), BlockState.CODEC.fieldOf("state").forGetter((config) -> {
            return config.blockState;
        }), FeatureSpread.CODEC.fieldOf("radius").forGetter((config) -> {
            return config.radius;
        })).apply(instance, BlobReplacementConfig::new);
    });
    public final Set<BlockState> target;
    public final BlockState blockState;
    public final FeatureSpread radius;

    public BlobReplacementConfig(List<BlockState> target, BlockState blockState, FeatureSpread radius) {
        this(ImmutableSet.copyOf(target), blockState, radius);
    }

    public BlobReplacementConfig(Set<BlockState> target, BlockState blockState, FeatureSpread radius) {
        this.target = target;
        this.blockState = blockState;
        this.radius = radius;
    }

    public FeatureSpread getRadius() {
        return this.radius;
    }
}