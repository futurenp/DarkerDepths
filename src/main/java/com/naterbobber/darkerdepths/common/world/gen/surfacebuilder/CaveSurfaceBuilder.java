package com.naterbobber.darkerdepths.common.world.gen.surfacebuilder;

import com.mojang.serialization.Codec;
import com.naterbobber.darkerdepths.core.util.INoiseAccess;
import com.naterbobber.darkerdepths.core.util.helpers.MathUtils;
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
    private SharedSeedRandom random = new SharedSeedRandom();
    private long seed = this.random.nextLong();
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
        this.seed = seed;
        this.random = new SharedSeedRandom(seed);

        BlockPos.Mutable mutable = new BlockPos.Mutable();

        int xPos = x & 15;
        int zPos = z & 15;

        for (int yPos = 50; yPos >= 0; yPos--) {
            mutable.setPos(xPos, yPos, zPos);

            BlockState replaceable = chunkIn.getBlockState(mutable);
            BlockState airCheck = chunkIn.getBlockState(mutable.up());

            if (replaceable.isIn(BlockTags.BASE_STONE_OVERWORLD) && airCheck.isAir()) {
                chunkIn.setBlockState(mutable, this.defaultState, false);
            }
        }
    }

    @Override
    public BlockState modifyNoise(NoiseChunkGenerator generator, BlockPos pos, Random rand, BlockState state, IWorld world, StructureManager manager, IChunk chunk) {
        if (state.getBlock().isIn(BlockTags.BASE_STONE_OVERWORLD)) {
            if (pos.getY() < 14) {
                return this.baseState;
            } else if (pos.getY() > 16) {
                return this.defaultState;
            } else {
                double chance = MathUtils.lerpFromProgress(pos.getY(), -8.0D, 0.0D, 1.0D, 0.0D);
                this.setLayeredSeed(this.random, this.seed, pos.getX(), pos.getY(), pos.getZ());
                return this.random.nextFloat() < chance ? this.baseState : this.defaultState;
            }
        }
        return state;
    }

    private void setLayeredSeed(SharedSeedRandom random, long seed, int x, int y, int z) {
        random.setSeed(seed);
        long xIn = random.nextLong();
        long yIn = random.nextLong();
        long zIn = random.nextLong();
        long layerSeed = x * xIn ^ y * yIn ^ z * zIn ^ seed;
        random.setSeed(layerSeed);
    }
}