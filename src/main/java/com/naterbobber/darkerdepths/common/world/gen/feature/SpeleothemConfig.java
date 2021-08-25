package com.naterbobber.darkerdepths.common.world.gen.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.naterbobber.darkerdepths.core.util.helpers.DirectionHelper;
import net.minecraft.block.BlockState;
import net.minecraft.util.Direction;
import net.minecraft.world.gen.feature.IFeatureConfig;

public class SpeleothemConfig implements IFeatureConfig {

    public static final Codec<SpeleothemConfig> CODEC = RecordCodecBuilder.create((instance) -> {
        return instance.group(BlockState.CODEC.fieldOf("speleothem_type").forGetter((config) -> {
            return config.speleothem_type;
        }), BlockState.CODEC.fieldOf("bottomState").forGetter(((config) -> {
            return config.bottomState;
        })), DirectionHelper.CODEC.fieldOf("direction").forGetter((config) -> {
            return config.direction;
        })).apply(instance, SpeleothemConfig::new);
    });

    public final BlockState speleothem_type;
    public final BlockState bottomState;
    public final Direction direction;

    public SpeleothemConfig(BlockState speleothem_type, BlockState bottomState, Direction direction) {
        this.speleothem_type = speleothem_type;
        this.bottomState = bottomState;
        this.direction = direction;
    }

}
