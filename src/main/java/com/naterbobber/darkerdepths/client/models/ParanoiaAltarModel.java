package com.naterbobber.darkerdepths.client.models;

import com.naterbobber.darkerdepths.DarkerDepths;
import com.naterbobber.darkerdepths.blocks.blockentities.ParanoiaAltarBlockEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class ParanoiaAltarModel extends GeoModel<ParanoiaAltarBlockEntity> {

	@Override
	public ResourceLocation getModelResource(ParanoiaAltarBlockEntity paranoiaAltarBlockEntity) {
		return DarkerDepths.id("geo/block/paranoia_altar.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(ParanoiaAltarBlockEntity paranoiaAltarBlockEntity) {
		return DarkerDepths.id("textures/block/paranoia_altar.png");
	}

	@Override
	public ResourceLocation getAnimationResource(ParanoiaAltarBlockEntity paranoiaAltarBlockEntity) {
		return DarkerDepths.id("animations/paranoia_altar.animation.json");
	}
}