package com.naterbobber.darkerdepths.common.world.gen.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.naterbobber.darkerdepths.core.util.helpers.DirectionHelper;
import net.minecraft.block.BlockState;
import net.minecraft.util.Direction;
import net.minecraft.world.gen.feature.IFeatureConfig;

//<>

public class CavePillarConfig implements IFeatureConfig {
    public static final Codec<CavePillarConfig> CODEC = RecordCodecBuilder.create((instance) -> {
        return instance.group(BlockState.CODEC.fieldOf("pillar_state").forGetter((config) -> {
            return config.pillarState;
        }), DirectionHelper.CODEC.fieldOf("pointing_direction").forGetter((config) -> {
            return config.pointingDirection;
        })).apply(instance, CavePillarConfig::new);
    });
    public final BlockState pillarState;
    public final Direction pointingDirection;

    public CavePillarConfig(BlockState state, Direction direction) {
        this.pillarState = state;
        this.pointingDirection = direction;
    }
}