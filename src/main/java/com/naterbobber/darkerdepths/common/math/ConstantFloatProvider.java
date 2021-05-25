package com.naterbobber.darkerdepths.common.math;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import java.util.Random;

//<>

public class ConstantFloatProvider extends FloatProvider {
    public static final ConstantFloatProvider ZERO = new ConstantFloatProvider(0.0F);
    public static final Codec<ConstantFloatProvider> CODEC = Codec.either(Codec.FLOAT, RecordCodecBuilder.<ConstantFloatProvider>create((instance) -> {
        return instance.group(Codec.FLOAT.fieldOf("value").forGetter((provider) -> {
            return provider.value;
        })).apply(instance, ConstantFloatProvider::new);
    })).xmap((either) -> {
        return either.map(ConstantFloatProvider::create, (provider) -> {
            return provider;
        });
    }, (constantFloatProvider) -> {
        return Either.left(constantFloatProvider.value);
    });
    private final float value;

    public static ConstantFloatProvider create(float value) {
        return value == 0.0F ? ZERO : new ConstantFloatProvider(value);
    }

    private ConstantFloatProvider(float value) {
        this.value = value;
    }

    public float getValue() {
        return this.value;
    }

    @Override
    public float get(Random rand) {
        return this.value;
    }

    @Override
    public float getMin() {
        return this.value;
    }

    @Override
    public float getMax() {
        return this.value + 1.0F;
    }

    @Override
    public FloatProviderType<?> getType() {
        return FloatProviderType.CONSTANT;
    }

    @Override
    public String toString() {
        return Float.toString(this.value);
    }
}