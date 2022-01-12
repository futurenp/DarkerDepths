package com.naterbobber.darkerdepths.world.gen.features.config;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

import java.util.List;
import java.util.Set;

public class ReplaceListConfig implements FeatureConfiguration {
    public static final Codec<ReplaceListConfig> CODEC = RecordCodecBuilder.create((instance) -> {
        return instance.group(BlockState.CODEC.listOf().fieldOf("target").forGetter((config) -> {
            return ImmutableList.copyOf(config.targetList);
        }), BlockState.CODEC.fieldOf("state").forGetter((config) -> {
            return config.replaceState;
        }), IntProvider.codec(0, 12).fieldOf("radius").forGetter((config) -> {
            return config.radius;
        })).apply(instance, ReplaceListConfig::new);
    });

    public final Set<BlockState> targetList;
    public final BlockState replaceState;
    private final IntProvider radius;

    public ReplaceListConfig(List<BlockState> target, BlockState state, IntProvider radius) {
        this(ImmutableSet.copyOf(target), state, radius);
    }

    public ReplaceListConfig(Set<BlockState> targetList, BlockState replaceState, IntProvider radius) {
        this.targetList = targetList;
        this.replaceState = replaceState;
        this.radius = radius;
    }

    public IntProvider radius() {
        return this.radius;
    }
}
