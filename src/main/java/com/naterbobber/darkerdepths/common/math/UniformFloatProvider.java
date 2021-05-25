package com.naterbobber.darkerdepths.common.math;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.naterbobber.darkerdepths.core.util.helpers.MathUtils;

import java.util.Random;
import java.util.function.Function;

//<>

public class UniformFloatProvider extends FloatProvider {
    public static final Codec<UniformFloatProvider> CODEC = RecordCodecBuilder.<UniformFloatProvider>create((instance) -> {
        return instance.group(Codec.FLOAT.fieldOf("min_inclusive").forGetter((config) -> {
            return config.min;
        }), Codec.FLOAT.fieldOf("max_exclusive").forGetter((config) -> {
            return config.max;
        })).apply(instance, UniformFloatProvider::new);
    }).comapFlatMap((config) -> {
        return config.max < config.min ? DataResult.error("Max must be at least min, min_inclusive: " + config.min + ", max_inclusive: " + config.max) : DataResult.success(config);
    }, Function.identity());
    private final float min;
    private final float max;

    private UniformFloatProvider(float min, float max) {
        this.min = min;
        this.max = max;
    }

    public static UniformFloatProvider create(float min, float max) {
        if (max <= min) {
            throw new IllegalArgumentException("Max must exceed min");
        } else {
            return new UniformFloatProvider(min, max);
        }
    }

    @Override
    public float get(Random rand) {
        return MathUtils.nextBetween(rand, this.min, this.max);
    }

    @Override
    public float getMin() {
        return this.min;
    }

    @Override
    public float getMax() {
        return this.max;
    }

    @Override
    public FloatProviderType<?> getType() {
        return FloatProviderType.UNIFORM;
    }

    @Override
    public String toString() {
        return "[" + this.min + '-' + this.max + ']';
    }
}