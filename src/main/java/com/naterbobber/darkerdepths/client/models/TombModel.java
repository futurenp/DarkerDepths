package com.naterbobber.darkerdepths.client.models;

import com.naterbobber.darkerdepths.DarkerDepths;
import com.naterbobber.darkerdepths.block.blockentities.TombBlockEntity;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import software.bernie.geckolib.model.GeoModel;

@OnlyIn(Dist.CLIENT)
public class TombModel extends GeoModel<TombBlockEntity> {

	@Override
	public ResourceLocation getModelResource(TombBlockEntity tombBlockEntity) {
		return DarkerDepths.id("geo/block/tomb_block.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(TombBlockEntity tombBlockEntity) {
		return DarkerDepths.id("textures/entity/tomb/red.png");
	}

	@Override
	public ResourceLocation getAnimationResource(TombBlockEntity tombBlockEntity) {
		return DarkerDepths.id("animations/block/tomb_block.animation.json");
	}
}