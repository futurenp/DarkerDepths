package com.naterbobber.darkerdepths.client.models;

import com.naterbobber.darkerdepths.DarkerDepths;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.model.GeoModel;

public class VoidSoulJarModel<T extends GeoAnimatable> extends GeoModel<T> {

	@Override
	public ResourceLocation getModelResource(T voidSoulJar) {
		return DarkerDepths.id("geo/block/void_soul_jar.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(T voidSoulJar) {
		return DarkerDepths.id("textures/entity/void_soul_jar/void_soul_jar.png");
	}

	@Override
	public ResourceLocation getAnimationResource(T voidSoulJar) {
		return DarkerDepths.id("animations/block/void_soul_jar.animation.json");
	}
}