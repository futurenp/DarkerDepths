package com.naterbobber.darkerdepths.core.util;

import net.minecraft.block.BlockState;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.NoiseChunkGenerator;
import net.minecraft.world.gen.feature.structure.StructureManager;

import java.util.Random;

//<>

/**
 * source: Infernal Expansion
 */
public interface INoiseAccess {
    BlockState modifyNoise(NoiseChunkGenerator generator, BlockPos pos, Random rand, BlockState state, IWorld world, StructureManager manager, IChunk chunk, SharedSeedRandom seedRandom, long seed);
}