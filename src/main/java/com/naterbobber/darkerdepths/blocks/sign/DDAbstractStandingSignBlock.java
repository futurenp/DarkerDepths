package com.naterbobber.darkerdepths.blocks.sign;

import net.minecraft.block.BlockState;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IWorldReader;

//<>

public class DDAbstractStandingSignBlock extends DDAbstractSignBlock {
	public static final IntegerProperty ROTATION = BlockStateProperties.ROTATION_0_15;

	public DDAbstractStandingSignBlock(Properties propertiesIn, ResourceLocation textureLocation) {
		super(propertiesIn, textureLocation);
		this.setDefaultState(this.stateContainer.getBaseState().with(ROTATION, 0).with(WATERLOGGED, false));
	}

	@Override
	public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
		return worldIn.getBlockState(pos.down()).getMaterial().isSolid();
	}
	
	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		FluidState ifluidstate = context.getWorld().getFluidState(context.getPos());
		return this.getDefaultState().with(ROTATION, Integer.valueOf(MathHelper.floor((double)((180.0f + context.getPlacementYaw()) * 16.0f / 360.0f) + 0.5f) & 15)).with(WATERLOGGED, Boolean.valueOf(ifluidstate.getFluid() == Fluids.WATER));
	}
	
}
