package com.naterbobber.darkerdepths.client.models;

import com.naterbobber.darkerdepths.DarkerDepths;
import com.naterbobber.darkerdepths.entities.VoidSoulEntity;
import com.naterbobber.darkerdepths.entities.VoidSoulKnightEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;

public class VoidSoulModel extends DefaultedEntityGeoModel<VoidSoulEntity> {
	public VoidSoulModel() {
		super(ResourceLocation.withDefaultNamespace(DarkerDepths.MODID), true);
	}

	@Override
	public ResourceLocation getModelResource(VoidSoulEntity voidSoulEntity) {
		return DarkerDepths.id("geo/entity/void_soul.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(VoidSoulEntity voidSoulEntity) {
		return DarkerDepths.id("textures/entity/void_soul/void_soul.png");
	}

	@Override
	public ResourceLocation getAnimationResource(VoidSoulEntity voidSoulEntity) {
		return DarkerDepths.id("animations/entity/void_soul.animation.json");
	}
}