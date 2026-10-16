package com.naterbobber.darkerdepths.common.worldgen.feature.config;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.core.registries.Registries;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

public record PointedAridrockCeilingConfig(
        HolderSet<Block> replaceableBlocks,
        IntProvider height,
        IntProvider radius,
        IntProvider depth,
        int verticalRange,
        float pointedChance
) implements FeatureConfiguration {
    public static final Codec<PointedAridrockCeilingConfig> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            RegistryCodecs.homogeneousList(Registries.BLOCK).fieldOf("replaceable_blocks").forGetter(PointedAridrockCeilingConfig::replaceableBlocks),
            IntProvider.codec(1, 16).fieldOf("height").forGetter(PointedAridrockCeilingConfig::height),
            IntProvider.codec(1, 8).fieldOf("radius").forGetter(PointedAridrockCeilingConfig::radius),
            IntProvider.codec(1, 8).fieldOf("depth").forGetter(PointedAridrockCeilingConfig::depth),
            Codec.intRange(1, 16).fieldOf("vertical_range").forGetter(PointedAridrockCeilingConfig::verticalRange),
            Codec.floatRange(0.0F, 1.0F).fieldOf("pointed_chance").forGetter(PointedAridrockCeilingConfig::pointedChance)
    ).apply(instance, PointedAridrockCeilingConfig::new));
}
