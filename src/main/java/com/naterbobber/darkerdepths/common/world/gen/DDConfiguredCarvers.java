package com.naterbobber.darkerdepths.common.world.gen;

import com.naterbobber.darkerdepths.core.DDRegistries;
import com.naterbobber.darkerdepths.core.DarkerDepths;
import com.naterbobber.darkerdepths.core.registries.DDCarvers;
import net.minecraft.world.gen.carver.ConfiguredCarver;
import net.minecraft.world.gen.feature.ProbabilityConfig;

public class DDConfiguredCarvers {
    public static final DDRegistries HELPER = DarkerDepths.REGISTRY_HELPER;

    public static final ConfiguredCarver<ProbabilityConfig> FLAT_CAVE   = HELPER.registerConfiguredCarver("flat_cave", DDCarvers.FLAT_CAVE.get().func_242761_a(new ProbabilityConfig(0.2f)));
    public static final ConfiguredCarver<ProbabilityConfig> LARGE_CAVE  = HELPER.registerConfiguredCarver("large_cave", DDCarvers.LARGE_CAVE.get().func_242761_a(new ProbabilityConfig(0.1f)));
    public static final ConfiguredCarver<ProbabilityConfig> BIG_CAVE    = HELPER.registerConfiguredCarver("big_cave", DDCarvers.BIG_CAVE.get().func_242761_a(new ProbabilityConfig(0.2f)));
}