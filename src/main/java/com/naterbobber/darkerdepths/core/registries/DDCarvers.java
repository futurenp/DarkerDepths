package com.naterbobber.darkerdepths.core.registries;

import com.naterbobber.darkerdepths.common.world.gen.carver.*;
import com.naterbobber.darkerdepths.core.DarkerDepths;

import net.minecraft.world.gen.carver.WorldCarver;
import net.minecraft.world.gen.feature.ProbabilityConfig;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

//<>

public class DDCarvers {
	public static final DeferredRegister<WorldCarver<?>> CARVER = DeferredRegister.create(ForgeRegistries.WORLD_CARVERS, DarkerDepths.MODID);

	public static final RegistryObject<WorldCarver<ProbabilityConfig>> LARGE_CAVE 		= CARVER.register("large_cave", () -> new LargeCaveCarver(ProbabilityConfig.field_236576_b_, 100));
	public static final RegistryObject<WorldCarver<ProbabilityConfig>> FLAT_CAVE		= CARVER.register("flat_cave", () -> new FlatCarver(ProbabilityConfig.field_236576_b_, 256));
	public static final RegistryObject<WorldCarver<ProbabilityConfig>> BIG_CAVE 		= CARVER.register("big_cave", () -> new BigCaveCarver(ProbabilityConfig.field_236576_b_));
	public static final RegistryObject<WorldCarver<ProbabilityConfig>> NOISE_CAVE 		= CARVER.register("noise_cave", () -> new NoiseCarver(ProbabilityConfig.field_236576_b_));
	public static final RegistryObject<WorldCarver<ProbabilityConfig>> WATER_CAVE 		= CARVER.register("water_cave", () -> new WaterCarver(ProbabilityConfig.field_236576_b_));

}