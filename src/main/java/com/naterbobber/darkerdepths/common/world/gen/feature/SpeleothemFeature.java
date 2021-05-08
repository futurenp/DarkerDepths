package com.naterbobber.darkerdepths.common.world.gen.feature;

import java.util.Random;

import com.mojang.serialization.Codec;

import net.minecraft.block.BlockState;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap.Type;
import net.minecraft.world.gen.feature.BlockClusterFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.structure.StructureManager;

//<>

public class SpeleothemFeature extends Feature<BlockClusterFeatureConfig> {
	public final boolean isHanging;
	
	public SpeleothemFeature(boolean isHanging, Codec<BlockClusterFeatureConfig> codec) {
		super(codec);
		this.isHanging = isHanging;
	}

	@Override
	public boolean generate(ISeedReader seedReader, ChunkGenerator chunkGenerator, Random rand, BlockPos pos, BlockClusterFeatureConfig config) {
		BlockState state = config.stateProvider.getBlockState(rand, pos);
		BlockPos blockpos;
		if (config.project) {
			blockpos = seedReader.getHeight(Type.WORLD_SURFACE_WG, pos);
		} else {
			blockpos = pos;
		}
		
		int i = 0;
		BlockPos.Mutable mutablepos = new BlockPos.Mutable();
		
		for (int j = 0; j < config.tryCount; j++) {
			mutablepos.setAndOffset(blockpos, rand.nextInt(config.xSpread + 1) - rand.nextInt(config.xSpread + 1), rand.nextInt(config.ySpread + 1) - rand.nextInt(config.ySpread + 1), rand.nextInt(config.zSpread + 1) - rand.nextInt(config.zSpread + 1));
			BlockPos heightpos;
			if (this.isHanging) {
				heightpos = mutablepos.up();
			} else {
				heightpos = mutablepos.down();
			}
			
			BlockState heightstate = seedReader.getBlockState(heightpos);
			if ((seedReader.isAirBlock(mutablepos) || config.isReplaceable && seedReader.getBlockState(mutablepos).getMaterial().isReplaceable()) && state.isValidPosition(seedReader, mutablepos) && (config.whitelist.isEmpty() || config.whitelist.contains(heightstate.getBlock())) && !config.blacklist.contains(heightstate) && (!config.requiresWater || seedReader.getFluidState(heightpos.west()).isTagged(FluidTags.WATER) || seedReader.getFluidState(heightpos.east()).isTagged(FluidTags.WATER) || seedReader.getFluidState(heightpos.north()).isTagged(FluidTags.WATER) || seedReader.getFluidState(heightpos.south()).isTagged(FluidTags.WATER))) {
				config.blockPlacer.place(seedReader, mutablepos, state, rand);
				++i;
			}
		}
		
		return i > 0;
	}
}