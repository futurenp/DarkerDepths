package com.naterbobber.darkerdepths.common.math;

import com.mojang.serialization.Codec;
import net.minecraft.util.registry.Registry;

//<>

public interface FloatProviderType<P extends FloatProvider> {
    FloatProviderType<ConstantFloatProvider> CONSTANT               = registerFloatProvider("constant", ConstantFloatProvider.CODEC);
    FloatProviderType<UniformFloatProvider> UNIFORM                 = registerFloatProvider("uniform", UniformFloatProvider.CODEC);
    FloatProviderType<ClampedNormalFloatProvider> CLAMPED_NORMAL    = registerFloatProvider("clamped_normal", ClampedNormalFloatProvider.CODEC);

    Codec<P> codec();

    static <P extends FloatProvider> FloatProviderType<P> registerFloatProvider(String key, Codec<P> codec) {
        return Registry.register(ModRegistry.FLOAT_PROVIDER_TYPE, key, () -> {
            return codec;
        });
    }
}