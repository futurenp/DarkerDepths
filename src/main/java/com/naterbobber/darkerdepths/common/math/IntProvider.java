package com.naterbobber.darkerdepths.common.math;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;

import java.util.Random;
import java.util.function.Function;

//<>

public abstract class IntProvider {
    private static final Codec<Either<Integer, IntProvider>> INT_CODEC = Codec.either(Codec.INT, ModRegistry.INT_PROVIDER_TYPE.dispatch(IntProvider::getType, IntProviderType::codec));
    public static final Codec<IntProvider> CODEC = INT_CODEC.xmap((either) -> {
        return either.map(ConstantIntProvider::create, (provider) -> {
            return provider;
        });
    }, (provider) -> {
        return provider.getType() == IntProviderType.CONSTANT ? Either.left(((ConstantIntProvider)provider).getValue()) : Either.right(provider);
    });

    public static Codec<IntProvider> createValidatingCodec(int min, int max) {
        Function<IntProvider, DataResult<IntProvider>> function = (provider) -> {
            if (provider.getMin() < min) {
                return DataResult.error("Value provider too low: " + min + " [" + provider.getMin() + "-" + provider.getMax() + "]");
            } else {
                return provider.getMax() > max ? DataResult.error("Value provider too high: " + max + " [" + provider.getMin() + "-" + provider.getMax() + "]") : DataResult.success(provider);
            }
        };
        return CODEC.flatXmap(function, function);
    }

    public abstract int get(Random rand);

    public abstract int getMin();

    public abstract int getMax();

    public abstract IntProviderType<?> getType();
}