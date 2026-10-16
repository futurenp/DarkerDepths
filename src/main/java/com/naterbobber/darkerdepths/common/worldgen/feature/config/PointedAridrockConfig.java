package com.naterbobber.darkerdepths.common.worldgen.feature.config;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.core.registries.Registries;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

public record PointedAridrockConfig(HolderSet<Block> validFloorBlocks, IntProvider height) implements FeatureConfiguration {
    public static final Codec<PointedAridrockConfig> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            RegistryCodecs.homogeneousList(Registries.BLOCK).fieldOf("valid_floor_blocks").forGetter(PointedAridrockConfig::validFloorBlocks),
            IntProvider.codec(1, 16).fieldOf("height").forGetter(PointedAridrockConfig::height)
    ).apply(instance, PointedAridrockConfig::new));
}
