package com.naterbobber.darkerdepths.common.world.gen.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.world.gen.feature.IFeatureConfig;

//<>

public class GemstonePlacementConfig implements IFeatureConfig {
    public static final Codec<GemstonePlacementConfig> CODEC = RecordCodecBuilder.create((instance) -> {
        return instance.group(BlockState.CODEC.fieldOf("state").forGetter((config) -> {
            return config.state;
        })).apply(instance, GemstonePlacementConfig::new);
    });

    public final BlockState state;

    public GemstonePlacementConfig(BlockState state) {
        this.state = state;
    }
}