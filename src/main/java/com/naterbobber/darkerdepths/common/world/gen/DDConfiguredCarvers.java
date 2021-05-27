package com.naterbobber.darkerdepths.common.world.gen;

import com.naterbobber.darkerdepths.core.DDRegistries;
import com.naterbobber.darkerdepths.core.DarkerDepths;
import com.naterbobber.darkerdepths.core.registries.DDCarvers;
import net.minecraft.world.gen.carver.ConfiguredCarver;
import net.minecraft.world.gen.feature.ProbabilityConfig;

public class DDConfiguredCarvers {
    public static final DDRegistries HELPER = DarkerDepths.REGISTRIES;

    public static final ConfiguredCarver<ProbabilityConfig> NOISE_CAVE  = HELPER.registerConfiguredCarver("noise_cave", DDCarvers.NOISE_CAVE.get().func_242761_a(new ProbabilityConfig(1f)));

}