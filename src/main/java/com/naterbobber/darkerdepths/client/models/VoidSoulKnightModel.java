package com.naterbobber.darkerdepths.client.models;

import com.naterbobber.darkerdepths.DarkerDepths;
import com.naterbobber.darkerdepths.entities.VoidSoulKnightEntity;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;

@OnlyIn(Dist.CLIENT)
public class VoidSoulKnightModel extends DefaultedEntityGeoModel<VoidSoulKnightEntity> {
	public VoidSoulKnightModel() {
		super(ResourceLocation.withDefaultNamespace(DarkerDepths.MOD_ID), true);
	}

	@Override
	public ResourceLocation getModelResource(VoidSoulKnightEntity voidSoulKnightEntity) {
		return DarkerDepths.id("geo/entity/void_soul_knight.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(VoidSoulKnightEntity voidSoulKnightEntity) {
		return DarkerDepths.id("textures/entity/void_soul_knight/void_soul_knight.png");
	}

	@Override
	public ResourceLocation getAnimationResource(VoidSoulKnightEntity voidSoulKnightEntity) {
		return DarkerDepths.id("animations/entity/void_soul_knight.animation.json");
	}
}