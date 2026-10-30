package com.naterbobber.darkerdepths.common.worldgen.feature.config;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

public record AridPatchConfig(boolean requiresSandyCatacombs) implements FeatureConfiguration {
    public static final Codec<AridPatchConfig> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.BOOL.fieldOf("requires_sandy_catacombs").forGetter(AridPatchConfig::requiresSandyCatacombs)
    ).apply(instance, AridPatchConfig::new));
}
