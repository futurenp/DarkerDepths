package com.naterbobber.darkerdepths.common.math;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import java.util.Random;

//<>

public class ConstantIntProvider extends IntProvider {
    public static final ConstantIntProvider ZERO = new ConstantIntProvider(0);
    public static final Codec<ConstantIntProvider> CODEC = Codec.either(Codec.INT, RecordCodecBuilder.<ConstantIntProvider>create((instance) -> {
        return instance.group(Codec.INT.fieldOf("value").forGetter((config) -> {
            return config.value;
        })).apply(instance, ConstantIntProvider::new);
    })).xmap((provider) -> {
        return provider.map(ConstantIntProvider::create, (config) -> {
            return config;
        });
    }, (config) -> {
        return Either.left(config.value);
    });
    private final int value;

    public static ConstantIntProvider create(int value) {
        return value == 0 ? ZERO : new ConstantIntProvider(value);
    }

    private ConstantIntProvider(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    @Override
    public int get(Random rand) {
        return this.value;
    }

    @Override
    public int getMin() {
        return this.value;
    }

    @Override
    public int getMax() {
        return this.value;
    }

    @Override
    public IntProviderType<?> getType() {
        return IntProviderType.CONSTANT;
    }

    @Override
    public String toString() {
        return Integer.toString(this.value);
    }
}