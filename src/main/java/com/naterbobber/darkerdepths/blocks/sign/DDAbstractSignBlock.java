package com.naterbobber.darkerdepths.blocks.sign;

import net.minecraft.block.AbstractSignBlock;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.util.ResourceLocation;

//<>

public abstract class DDAbstractSignBlock extends AbstractSignBlock {
	private final ResourceLocation textureLocation;

	public DDAbstractSignBlock(Properties propertiesIn, ResourceLocation textureLocation) {
		super(propertiesIn, null);
		this.textureLocation = textureLocation;
	}

	public ResourceLocation getTextureLocation() {
		return this.textureLocation;
	}

	@Override
	public BlockRenderType getRenderType(BlockState state) {
		return BlockRenderType.ENTITYBLOCK_ANIMATED;
	}
	
}
