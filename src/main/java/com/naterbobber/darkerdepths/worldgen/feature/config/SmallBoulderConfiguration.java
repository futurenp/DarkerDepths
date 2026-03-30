package com.naterbobber.darkerdepths.worldgen.feature.config;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

import java.util.Optional;

public record SmallBoulderConfiguration(
        BlockStateProvider mainBlock,
        Optional<BlockStateProvider> decoratorBlock,
        float decoratorChance
) implements FeatureConfiguration {

    public static final Codec<SmallBoulderConfiguration> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            BlockStateProvider.CODEC.fieldOf("main_block").forGetter(SmallBoulderConfiguration::mainBlock),
            BlockStateProvider.CODEC.optionalFieldOf("decorator_block").forGetter(SmallBoulderConfiguration::decoratorBlock),
            Codec.FLOAT.fieldOf("decorator_chance").forGetter(SmallBoulderConfiguration::decoratorChance)
    ).apply(instance, SmallBoulderConfiguration::new));
}