package com.naterbobber.darkerdepths.client.models;

import com.google.common.collect.Maps;
import com.naterbobber.darkerdepths.DarkerDepths;
import com.naterbobber.darkerdepths.common.block.blockentities.TombBlockEntity;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import software.bernie.geckolib.model.DefaultedBlockGeoModel;

import java.util.Map;

@OnlyIn(Dist.CLIENT)
public class TombModel extends DefaultedBlockGeoModel<TombBlockEntity> {

	private static final Map<Block, ResourceLocation> TEXTURES = Maps.newHashMap();

	public TombModel() {
		super(DarkerDepths.id("tomb_block"));
	}

	@Override
	public ResourceLocation getTextureResource(TombBlockEntity tombBlockEntity) {
		var tombBlock = tombBlockEntity.getBlockState().getBlock();

		return TEXTURES.computeIfAbsent(tombBlock, block -> {
			var blockName = BuiltInRegistries.BLOCK.getKey(tombBlock).getPath();
			var textureName = blockName.equals("tomb") ? "duskrock_tomb" : blockName;
            return DarkerDepths.id("textures/entity/tomb/" + textureName + ".png");
		});
	}
}