package com.naterbobber.darkerdepths.common.math;

import net.minecraft.util.RegistryKey;
import net.minecraft.util.registry.Registry;

//<>

public class ModRegistry {
    public static final RegistryKey<Registry<FloatProviderType<?>>> FLOAT_PROVIDER_TYPE_KEY     = Registry.createKey("float_provider_type");
    public static final Registry<FloatProviderType<?>> FLOAT_PROVIDER_TYPE                      = Registry.createRegistry(FLOAT_PROVIDER_TYPE_KEY, () -> FloatProviderType.CONSTANT);

    public static final RegistryKey<Registry<IntProviderType<?>>> INT_PROVIDER_TYPE_KEY         = Registry.createKey("int_provider_type");
    public static final Registry<IntProviderType<?>> INT_PROVIDER_TYPE                          = Registry.createRegistry(INT_PROVIDER_TYPE_KEY, () -> IntProviderType.CONSTANT);
}