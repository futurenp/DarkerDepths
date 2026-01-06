package com.naterbobber.darkerdepths.client.models;

import com.naterbobber.darkerdepths.DarkerDepths;
import com.naterbobber.darkerdepths.block.custom.ParanoiaAltarBlock;
import com.naterbobber.darkerdepths.block.blockentities.ParanoiaAltarBlockEntity;
import com.naterbobber.darkerdepths.item.ParanoiaAltarItem;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.model.GeoModel;

@OnlyIn(Dist.CLIENT)
public class ParanoiaAltarModel<T extends GeoAnimatable> extends GeoModel<T> {

	@Override
	public ResourceLocation getModelResource(T paranoiaAltar) {
		return DarkerDepths.id("geo/block/paranoia_altar.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(T animatable) {
		if (animatable instanceof ParanoiaAltarBlockEntity paranoiaAltar) {
			if (paranoiaAltar.getBlockState().getValue(ParanoiaAltarBlock.LOCKED)) {
				return DarkerDepths.id("textures/entity/paranoia_altar/paranoia_altar_locked.png");
			} else {
				return DarkerDepths.id("textures/entity/paranoia_altar/paranoia_altar.png");
			}
		}

		if (animatable instanceof ParanoiaAltarItem) {
			return DarkerDepths.id("textures/entity/paranoia_altar/paranoia_altar.png");
		}

		return DarkerDepths.id("textures/entity/paranoia_altar/paranoia_altar.png");
	}

	@Override
	public ResourceLocation getAnimationResource(T paranoiaAltar) {
		return DarkerDepths.id("animations/block/paranoia_altar.animation.json");
	}
}