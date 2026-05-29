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
		super(DarkerDepths.id("void_soul_knight"), true);
	}

	@Override
	public ResourceLocation getTextureResource(VoidSoulKnightEntity voidSoulKnightEntity) {
		return DarkerDepths.id("textures/entity/void_soul_knight/void_soul_knight.png");
	}
}