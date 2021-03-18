package com.naterbobber.darkerdepths.core.registries;

import com.naterbobber.darkerdepths.common.world.gen.carver.HorizontalCarver;
import com.naterbobber.darkerdepths.common.world.gen.carver.LargeCaveCarver;
import com.naterbobber.darkerdepths.common.world.gen.carver.PerlerpCarver;
import com.naterbobber.darkerdepths.core.DarkerDepths;

import net.minecraft.world.gen.carver.WorldCarver;
import net.minecraft.world.gen.feature.ProbabilityConfig;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

//<>

public class DDCarvers {
	public static final DeferredRegister<WorldCarver<?>> CARVER = DeferredRegister.create(ForgeRegistries.WORLD_CARVERS, DarkerDepths.MODID);

	public static final RegistryObject<WorldCarver<ProbabilityConfig>> LARGE_CAVE 		= CARVER.register("large_cave", () -> new LargeCaveCarver(ProbabilityConfig.field_236576_b_, 256));
}