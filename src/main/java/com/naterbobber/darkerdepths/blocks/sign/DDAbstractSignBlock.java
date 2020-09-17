package com.naterbobber.darkerdepths.blocks.sign;

import net.minecraft.block.AbstractSignBlock;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tileentity.SignTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

//<>

public abstract class DDAbstractSignBlock extends AbstractSignBlock {
	private final ResourceLocation textureLocation;

	public DDAbstractSignBlock(Properties propertiesIn, ResourceLocation textureLocation) {
		super(propertiesIn, null);
		this.textureLocation = textureLocation;
	}

	@Override
	public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
		return super.onBlockActivated(state, worldIn, pos, player, handIn, hit);
	}
	
	public ResourceLocation getTextureLocation() {
		return this.textureLocation;
	}
	
	@Override
	public TileEntity createNewTileEntity(IBlockReader worldIn) {
		return new SignTileEntity();
	}

	@Override
	public BlockRenderType getRenderType(BlockState state) {
		return BlockRenderType.ENTITYBLOCK_ANIMATED;
	}
	
}
