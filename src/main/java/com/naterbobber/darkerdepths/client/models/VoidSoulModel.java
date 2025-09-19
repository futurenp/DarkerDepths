package com.naterbobber.darkerdepths.client.models;

import com.naterbobber.darkerdepths.DarkerDepths;
import com.naterbobber.darkerdepths.entities.VoidSoulEntity;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;

@OnlyIn(Dist.CLIENT)
public class VoidSoulModel extends DefaultedEntityGeoModel<VoidSoulEntity> {
	public VoidSoulModel() {
		super(ResourceLocation.withDefaultNamespace(DarkerDepths.MOD_ID), true);
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