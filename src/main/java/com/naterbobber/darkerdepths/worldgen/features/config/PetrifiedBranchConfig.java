package com.naterbobber.darkerdepths.worldgen.features.config;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

public record PetrifiedBranchConfig(int minLength, int maxLength) implements FeatureConfiguration {
    public static final Codec<PetrifiedBranchConfig> CODEC = RecordCodecBuilder.create((instance) -> {
        return instance.group(
                Codec.intRange(0, 32).fieldOf("minLength").forGetter((config) -> config.minLength),
                Codec.intRange(0, 32).fieldOf("maxLength").forGetter((config) -> config.maxLength)
        ).apply(instance, PetrifiedBranchConfig::new);
    });
}
