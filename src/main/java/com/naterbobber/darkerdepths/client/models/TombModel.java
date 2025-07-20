package com.naterbobber.darkerdepths.client.models;

import com.naterbobber.darkerdepths.DarkerDepths;
import com.naterbobber.darkerdepths.blocks.blockentities.TombBlockEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class TombModel extends GeoModel<TombBlockEntity> {

	@Override
	public ResourceLocation getModelResource(TombBlockEntity tombBlockEntity) {
		return DarkerDepths.id("geo/block/tomb_block.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(TombBlockEntity tombBlockEntity) {
		return DarkerDepths.id("textures/block/tomb.png");
	}

	@Override
	public ResourceLocation getAnimationResource(TombBlockEntity tombBlockEntity) {
		return DarkerDepths.id("animations/tomb_block.animation.json");
	}
}
