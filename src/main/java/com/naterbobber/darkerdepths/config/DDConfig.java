package com.naterbobber.darkerdepths.config;

import com.naterbobber.darkerdepths.config.builders.DDConfigBuilder;
import net.neoforged.neoforge.common.ModConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class DDConfig {
    public static final DDConfigBuilder CONFIG;
    public static final ModConfigSpec CONFIG_SPEC;

    static {
        final Pair<DDConfigBuilder, ModConfigSpec> pair =
                new ModConfigSpec.Builder().configure(DDConfigBuilder::new);

        CONFIG = pair.getLeft();
        CONFIG_SPEC = pair.getRight();
    }
}
