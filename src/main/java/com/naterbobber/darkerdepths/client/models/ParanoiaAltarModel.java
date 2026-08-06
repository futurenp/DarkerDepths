package com.naterbobber.darkerdepths.client.models;

import com.naterbobber.darkerdepths.DarkerDepths;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.model.GeoModel;

@OnlyIn(Dist.CLIENT)
public class ParanoiaAltarModel<T extends GeoAnimatable> extends GeoModel<T> {

	@Override
	public ResourceLocation getModelResource(T paranoiaAltar) {
		return DarkerDepths.id("geo/block/paranoia_altar.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(T animatable) {
		return DarkerDepths.id("textures/entity/paranoia_altar/paranoia_altar.png");
	}

	@Override
	public ResourceLocation getAnimationResource(T paranoiaAltar) {
		return DarkerDepths.id("animations/block/paranoia_altar.animation.json");
	}
}