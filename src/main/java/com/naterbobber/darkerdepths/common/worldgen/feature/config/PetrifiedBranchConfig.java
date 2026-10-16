package com.naterbobber.darkerdepths.common.worldgen.feature.config;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

public record PetrifiedBranchConfig(int minLength, int maxLength, boolean large) implements FeatureConfiguration {
    public static final Codec<PetrifiedBranchConfig> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.intRange(0, 32).fieldOf("minLength").forGetter(PetrifiedBranchConfig::minLength),
            Codec.intRange(0, 32).fieldOf("maxLength").forGetter(PetrifiedBranchConfig::maxLength),
            Codec.BOOL.optionalFieldOf("large", false).forGetter(PetrifiedBranchConfig::large)
    ).apply(instance, PetrifiedBranchConfig::new));
}
