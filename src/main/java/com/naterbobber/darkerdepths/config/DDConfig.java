package com.naterbobber.darkerdepths.config;

import net.neoforged.neoforge.common.ModConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class DDConfig {
    public static final DDConfigValues CONFIG;
    public static final ModConfigSpec CONFIG_SPEC;

    static {
        final Pair<DDConfigValues, ModConfigSpec> pair =
                new ModConfigSpec.Builder().configure(DDConfigValues::new);

        CONFIG = pair.getLeft();
        CONFIG_SPEC = pair.getRight();
    }
}
