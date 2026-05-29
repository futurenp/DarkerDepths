package com.naterbobber.darkerdepths.client.models;

import com.naterbobber.darkerdepths.DarkerDepths;
import com.naterbobber.darkerdepths.block.blockentities.TombBlockEntity;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import software.bernie.geckolib.model.DefaultedBlockGeoModel;
import software.bernie.geckolib.model.GeoModel;

@OnlyIn(Dist.CLIENT)
public class TombModel extends DefaultedBlockGeoModel<TombBlockEntity> {

	public TombModel() {
		super(DarkerDepths.id("tomb_block"));
	}

	@Override
	public ResourceLocation getTextureResource(TombBlockEntity tombBlockEntity) {
		return DarkerDepths.id("textures/entity/tomb/red.png");
	}
}