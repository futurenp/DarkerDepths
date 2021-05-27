package com.naterbobber.darkerdepths.core.registries;

import com.naterbobber.darkerdepths.common.world.gen.carver.*;
import com.naterbobber.darkerdepths.core.DDRegistries;
import com.naterbobber.darkerdepths.core.DarkerDepths;

import net.minecraft.world.gen.carver.WorldCarver;
import net.minecraft.world.gen.feature.ProbabilityConfig;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;

//<>

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class DDCarvers {
	public static final DDRegistries HELPER = DarkerDepths.REGISTRIES;

	public static final RegistryObject<WorldCarver<ProbabilityConfig>> NOISE_CAVE 		= HELPER.registerCarver("noise_cave", () -> new PerlinCarver(ProbabilityConfig.CODEC, 256));

}