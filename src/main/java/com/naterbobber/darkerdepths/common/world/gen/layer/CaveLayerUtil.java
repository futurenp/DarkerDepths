package com.naterbobber.darkerdepths.common.world.gen.layer;

import java.util.function.LongFunction;

import net.minecraft.world.gen.IExtendedNoiseRandom;
import net.minecraft.world.gen.LazyAreaLayerContext;
import net.minecraft.world.gen.area.IArea;
import net.minecraft.world.gen.area.IAreaFactory;
import net.minecraft.world.gen.layer.Layer;
import net.minecraft.world.gen.layer.ZoomLayer;

//<>

public class CaveLayerUtil {
	public static <T extends IArea, C extends IExtendedNoiseRandom<T>> IAreaFactory<T> areaFactories(LongFunction<C> context) {
		IAreaFactory<T> biomeFactory = CaveBiomeLayer.INSTANCE.apply(context.apply(1000l));
		
		for (int caveBiomeSize = 0; caveBiomeSize <= 2; caveBiomeSize++) {
			biomeFactory = ZoomLayer.NORMAL.apply(context.apply(1000l + caveBiomeSize), biomeFactory);
		}
		biomeFactory = ZoomLayer.FUZZY.apply(context.apply(1000l), biomeFactory);
		biomeFactory = ZoomLayer.NORMAL.apply(context.apply(1000l), biomeFactory);
		
		return biomeFactory;
	}
	
	public static Layer createLayers(long seed) {
		return new Layer(areaFactories(salt -> new LazyAreaLayerContext(25, seed, salt)));
	}
}
