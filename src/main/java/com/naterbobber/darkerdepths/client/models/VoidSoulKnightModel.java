package com.naterbobber.darkerdepths.client.models;

import com.naterbobber.darkerdepths.DarkerDepths;
import com.naterbobber.darkerdepths.entities.BodySnatcherEntity;
import com.naterbobber.darkerdepths.entities.VoidSoulKnightEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class VoidSoulKnightModel extends DefaultedEntityGeoModel<VoidSoulKnightEntity> {
	public VoidSoulKnightModel() {
		super(ResourceLocation.withDefaultNamespace(DarkerDepths.MODID), true);
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