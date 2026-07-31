package com.naterbobber.darkerdepths.client.models;

import com.naterbobber.darkerdepths.DarkerDepths;
import com.naterbobber.darkerdepths.block.blockentities.TombBlockEntity;
import com.naterbobber.darkerdepths.block.generic.DDLogBlock;
import com.naterbobber.darkerdepths.init.DDBlocks;
import net.minecraft.core.registries.BuiltInRegistries;
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
		var tombBlock = tombBlockEntity.getBlockState().getBlock();
		var stringBuilder = new StringBuilder("textures/entity/tomb/");

		var name = BuiltInRegistries.BLOCK.getKey(tombBlock).getPath();

		if(name.equals("tomb")) {
			stringBuilder.append("red");
		} else {
			stringBuilder.append(name);
		}

		return DarkerDepths.id(stringBuilder.append(".png").toString());
	}
}