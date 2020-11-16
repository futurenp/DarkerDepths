package com.naterbobber.darkerdepths.common.world.gen.layer;

import com.naterbobber.darkerdepths.common.world.gen.biome.provider.CaveBiomeReceiver;

import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.layer.traits.IAreaTransformer0;

//<>

public enum CaveBiomeLayer implements IAreaTransformer0 {
	INSTANCE;

	@Override
	public int apply(INoiseRandom context, int x, int z) {
		return CaveBiomeReceiver.getRandomBiomes(context);
	}
}