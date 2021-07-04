package com.naterbobber.darkerdepths.common.world.gen.surfacebuilder;

import com.mojang.serialization.Codec;
import com.naterbobber.darkerdepths.core.util.INoiseAccess;
import net.minecraft.block.BlockState;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.NoiseChunkGenerator;
import net.minecraft.world.gen.feature.structure.StructureManager;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;

import java.util.Random;

//<>

public class CaveSurfaceBuilder extends SurfaceBuilder<SurfaceBuilderConfig> implements INoiseAccess {
    private final BlockState defaultState;
    private final BlockState baseState;

    public CaveSurfaceBuilder(BlockState defaultState, BlockState baseState, Codec<SurfaceBuilderConfig> codec) {
        super(codec);
        this.defaultState = defaultState;
        this.baseState = baseState;
    }

    public CaveSurfaceBuilder(BlockState defaultState, Codec<SurfaceBuilderConfig> codec) {
        this(defaultState, defaultState, codec);
    }

    @Override
    public void buildSurface(Random random, IChunk chunkIn, Biome biomeIn, int x, int z, int startHeight, double noise, BlockState defaultBlock, BlockState defaultFluid, int seaLevel, long seed, SurfaceBuilderConfig config) {
    }

    @Override
    public BlockState modifyNoise(NoiseChunkGenerator generator, BlockPos pos, Random rand, BlockState state, IWorld world, StructureManager manager, IChunk chunk, SharedSeedRandom seedRandom, long seed) {
        if (state.getBlock().isIn(BlockTags.BASE_STONE_OVERWORLD)) {
            if (pos.getY() < 16) {
                return this.baseState;
            } else {
                return this.defaultState;
            }
        }
        return state;
    }
}