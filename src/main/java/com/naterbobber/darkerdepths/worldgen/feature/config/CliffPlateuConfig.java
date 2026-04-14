package com.naterbobber.darkerdepths.worldgen.feature.config;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public record CliffPlateuConfig(BlockStateProvider mainBlock, int xzRadius, int height) implements FeatureConfiguration {

    public static final Codec<CliffPlateuConfig> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            BlockStateProvider.CODEC.fieldOf("main_block").forGetter(CliffPlateuConfig::mainBlock),
            Codec.INT.fieldOf("xz_radius").forGetter(CliffPlateuConfig::xzRadius),
            Codec.INT.fieldOf("height").forGetter(CliffPlateuConfig::height)
    ).apply(instance, CliffPlateuConfig::new));
}
