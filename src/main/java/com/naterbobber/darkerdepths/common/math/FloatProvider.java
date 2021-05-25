package com.naterbobber.darkerdepths.common.math;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;

import java.util.Random;
import java.util.function.Function;

//<>

public abstract class FloatProvider {
    private static final Codec<Either<Float, FloatProvider>> FLOAT_CODEC = Codec.either(Codec.FLOAT, ModRegistry.FLOAT_PROVIDER_TYPE.dispatch(FloatProvider::getType, FloatProviderType::codec));
    public static final Codec<FloatProvider> CODEC = FLOAT_CODEC.xmap((either) -> {
        return either.map(ConstantFloatProvider::create, (provider) -> {
            return provider;
        });
    }, (provider) -> {
        return provider.getType() == FloatProviderType.CONSTANT ? Either.left(((ConstantFloatProvider)provider).getValue()) : Either.right(provider);
    });

    public static Codec<FloatProvider> createValidatedCodec(float min, float max) {
        Function<FloatProvider, DataResult<FloatProvider>> function = (provider) -> {
            if (provider.getMin() < min) {
                return DataResult.error("Value provider too low: " + min + " [" + provider.getMin() + "-" + provider.getMax() + "]");
            } else {
                return provider.getMax() > max ? DataResult.error("Value provider too high: " + max + " [" + provider.getMin() + "-" + provider.getMax() + "]") : DataResult.success(provider);
            }
        };
        return CODEC.flatXmap(function, function);
    }

    public abstract float get(Random rand);

    public abstract float getMin();

    public abstract float getMax();

    public abstract FloatProviderType<?> getType();
}