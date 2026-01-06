package com.naterbobber.darkerdepths.worldgen.features.config;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

import java.util.List;
import java.util.Set;

public record ReplaceListLayeredConfig(Set<BlockState> targetList, IntProvider radius, List<BlockState> layers) implements FeatureConfiguration {
    public static final Codec<ReplaceListLayeredConfig> CODEC = RecordCodecBuilder.create((instance) -> {
        return instance.group(BlockState.CODEC.listOf().fieldOf("target").forGetter((config) -> {
            return ImmutableList.copyOf(config.targetList);
        }), IntProvider.codec(0, 12).fieldOf("radius").forGetter((config) -> {
            return config.radius;
        }), BlockState.CODEC.listOf().fieldOf("layer_target").forGetter((config) -> {
            return ImmutableList.copyOf(config.layers);
        })).apply(instance, ReplaceListLayeredConfig::new);
    });

    public ReplaceListLayeredConfig(List<BlockState> target, IntProvider radius, List<BlockState> layers) {
        this(ImmutableSet.copyOf(target), radius, ImmutableList.copyOf(layers));
    }

    public IntProvider radius() {
        return this.radius;
    }
}
