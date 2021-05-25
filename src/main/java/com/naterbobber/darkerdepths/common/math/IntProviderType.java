package com.naterbobber.darkerdepths.common.math;

import com.mojang.serialization.Codec;
import net.minecraft.util.registry.Registry;

public interface IntProviderType<P extends IntProvider> {
    IntProviderType<ConstantIntProvider> CONSTANT   = registerIntProvider("constant", ConstantIntProvider.CODEC);
    IntProviderType<UniformIntProvider> UNIFORM     = registerIntProvider("uniform", UniformIntProvider.CODEC);

    Codec<P> codec();

    static <P extends IntProvider> IntProviderType<P> registerIntProvider(String key, Codec<P> codec) {
        return Registry.register(ModRegistry.INT_PROVIDER_TYPE, key, () -> {
            return codec;
        });
    }
}