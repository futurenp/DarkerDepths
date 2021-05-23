package com.naterbobber.darkerdepths.core.registries;

import com.naterbobber.darkerdepths.common.world.gen.carver.*;
import com.naterbobber.darkerdepths.core.DDRegistryHelper;
import com.naterbobber.darkerdepths.core.DarkerDepths;

import net.minecraft.world.gen.carver.WorldCarver;
import net.minecraft.world.gen.feature.ProbabilityConfig;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;

//<>

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class DDCarvers {
	public static final DDRegistryHelper HELPER = DarkerDepths.REGISTRY_HELPER;

	public static final RegistryObject<WorldCarver<ProbabilityConfig>> LARGE_CAVE 		= HELPER.registerCarver("large_cave", () -> new LargeCaveCarver(ProbabilityConfig.CODEC, 100));
	public static final RegistryObject<WorldCarver<ProbabilityConfig>> FLAT_CAVE		= HELPER.registerCarver("flat_cave", () -> new FlatCarver(ProbabilityConfig.CODEC, 256));
	public static final RegistryObject<WorldCarver<ProbabilityConfig>> BIG_CAVE 		= HELPER.registerCarver("big_cave", () -> new BigCaveCarver(ProbabilityConfig.CODEC));
	public static final RegistryObject<WorldCarver<ProbabilityConfig>> NOISE_CAVE 		= HELPER.registerCarver("noise_cave", () -> new NoiseCarver(ProbabilityConfig.CODEC));

}