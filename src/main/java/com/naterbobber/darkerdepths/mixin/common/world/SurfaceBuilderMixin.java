package com.naterbobber.darkerdepths.mixin.common.world;

import com.naterbobber.darkerdepths.core.util.INoiseAccess;
import net.minecraft.block.BlockState;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.NoiseChunkGenerator;
import net.minecraft.world.gen.feature.structure.StructureManager;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import org.spongepowered.asm.mixin.Mixin;

import java.util.Random;

//<>

@Mixin(SurfaceBuilder.class)
public class SurfaceBuilderMixin implements INoiseAccess {
    @Override
    public BlockState modifyNoise(NoiseChunkGenerator generator, BlockPos pos, Random rand, BlockState state, IWorld world, StructureManager manager, IChunk chunk, SharedSeedRandom seedRandom, long seed) {
        return state;
    }
}