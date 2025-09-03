package com.naterbobber.darkerdepths.client.models;

import com.naterbobber.darkerdepths.DarkerDepths;
import com.naterbobber.darkerdepths.blocks.ParanoiaAltarBlock;
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
		if(paranoiaAltarBlockEntity.getBlockState().getValue(ParanoiaAltarBlock.LOCKED)){
			return DarkerDepths.id("textures/entity/paranoia_altar/paranoia_altar_locked.png");
		} else {
			return DarkerDepths.id("textures/entity/paranoia_altar/paranoia_altar.png");
		}
	}

	@Override
	public ResourceLocation getAnimationResource(ParanoiaAltarBlockEntity paranoiaAltarBlockEntity) {
		return DarkerDepths.id("animations/block/paranoia_altar.animation.json");
	}
}