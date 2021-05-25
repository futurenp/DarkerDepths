package com.naterbobber.darkerdepths.common.math;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.naterbobber.darkerdepths.core.util.helpers.MathUtils;

import java.util.Random;
import java.util.function.Function;

//<>

public class UniformIntProvider extends IntProvider {
    public static final Codec<UniformIntProvider> CODEC = RecordCodecBuilder.<UniformIntProvider>create((instance) -> {
        return instance.group(Codec.INT.fieldOf("min_inclusive").forGetter((config) -> {
            return config.min;
        }), Codec.INT.fieldOf("max_inclusive").forGetter((config) -> {
            return config.max;
        })).apply(instance, UniformIntProvider::new);
    }).comapFlatMap((config) -> {
        return config.max < config.min ? DataResult.error("Max must be at least min, min_inclusive: " + config.min + ", max_inclusive: " + config.max) : DataResult.success(config);
    }, Function.identity());
    private final int min;
    private final int max;

    private UniformIntProvider(int min, int max) {
        this.min = min;
        this.max = max;
    }

    public static UniformIntProvider create(int min, int max) {
        return new UniformIntProvider(min, max);
    }

    @Override
    public int get(Random rand) {
        return MathUtils.nextBetween(rand, this.min, this.max);
    }

    @Override
    public int getMin() {
        return this.min;
    }

    @Override
    public int getMax() {
        return this.max;
    }

    @Override
    public IntProviderType<?> getType() {
        return IntProviderType.UNIFORM;
    }

    @Override
    public String toString() {
        return "[" + this.min + '-' + (this.min + this.max) + ']';
    }
}
