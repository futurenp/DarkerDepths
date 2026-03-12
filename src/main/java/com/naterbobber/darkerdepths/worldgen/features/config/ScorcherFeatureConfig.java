package com.naterbobber.darkerdepths.worldgen.features.config;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

public record ScorcherFeatureConfig(int minHeight, int maxHeight, int baseRadius) implements FeatureConfiguration {
    public static final Codec<ScorcherFeatureConfig> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
            Codec.INT.fieldOf("min_height").orElse(5).forGetter(ScorcherFeatureConfig::minHeight),
            Codec.INT.fieldOf("max_height").orElse(8).forGetter(ScorcherFeatureConfig::maxHeight),
            Codec.INT.fieldOf("base_radius").orElse(2).forGetter(ScorcherFeatureConfig::baseRadius)
    ).apply(instance, ScorcherFeatureConfig::new));
}