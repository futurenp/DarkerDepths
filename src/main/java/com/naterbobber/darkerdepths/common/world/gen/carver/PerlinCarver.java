package com.naterbobber.darkerdepths.common.world.gen.carver;

import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.*;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.ImprovedNoiseGenerator;
import net.minecraft.world.gen.OctavesNoiseGenerator;
import net.minecraft.world.gen.carver.WorldCarver;
import net.minecraft.world.gen.feature.ProbabilityConfig;

import java.util.BitSet;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.IntStream;

//<>

public class PerlinCarver extends WorldCarver<ProbabilityConfig> {
    private long seed;
    private long worldSeed;
    private double amplitude;
    private OctavesNoiseGenerator caveNoise;
    private OctavesNoiseGenerator offsetNoise;
    private OctavesNoiseGenerator scaleNoise;
    private SharedSeedRandom random;

    public PerlinCarver(Codec<ProbabilityConfig> codec, int maxHeight) {
        super(codec, maxHeight);
        this.random = new SharedSeedRandom(seed);
        this.caveNoise = new OctavesNoiseGenerator(random, IntStream.rangeClosed(-5, 0));
        this.offsetNoise = new OctavesNoiseGenerator(random, IntStream.rangeClosed(-24, 0));
        this.scaleNoise = new OctavesNoiseGenerator(random, IntStream.rangeClosed(-12, 0));
    }

    @Override
    public boolean carveRegion(IChunk chunk, Function<BlockPos, Biome> biomePos, Random rand, int seaLevel, int chunkXOffset, int chunkZOffset, int chunkX, int chunkZ, BitSet carvingMask, ProbabilityConfig config) {
        if (!(chunkXOffset == chunkX && chunkZOffset == chunkZ)) {
            return false;
        }

        int chunkStartX = chunk.getPos().getXStart();
        int chunkStartZ = chunk.getPos().getZStart();

        float[][][] noiseTerrain = new float[2][5][10];
        //Noise 3D
        //first Z
        //second X
        //third Y

        for (int noiseZ = 0; noiseZ < 5; noiseZ++) {
            noiseTerrain[0][noiseZ] = new float[10];
            samplePillar(noiseTerrain[0][noiseZ], chunkX * 4, chunkZ * 4 + noiseZ, rand, this.caveNoise, this.offsetNoise, this.scaleNoise);
            noiseTerrain[1][noiseZ] = new float[10];
        }

        for (int noiseX = 0; noiseX < 4; noiseX++) {
            int noiseZ;
            for (noiseZ = 0; noiseZ < 5; noiseZ++) {
                samplePillar(noiseTerrain[1][noiseZ], chunkX * 4 + noiseX + 1, chunkZ * 4 + noiseZ, rand, this.caveNoise, this.offsetNoise, this.scaleNoise);
            }
            for (noiseZ = 0; noiseZ < 4; noiseZ++) {
                for (int noiseY = 0; noiseY < 9; noiseY++) {
                    float x0y0z0 = noiseTerrain[0][noiseZ][noiseY];
                    float x0y0z1 = noiseTerrain[0][noiseZ + 1][noiseY];
                    float x1y0z0 = noiseTerrain[1][noiseZ][noiseY];
                    float x1y0z1 = noiseTerrain[1][noiseZ + 1][noiseY];
                    float x0y1z0 = noiseTerrain[0][noiseZ][noiseY + 1];
                    float x0y1z1 = noiseTerrain[0][noiseZ + 1][noiseY + 1];
                    float x1y1z0 = noiseTerrain[1][noiseZ][noiseY + 1];
                    float x1y1z1 = noiseTerrain[1][noiseZ + 1][noiseY + 1];

                    for (int primeY = 0; primeY < 11; primeY++) {
                        int realY = noiseY * 6 + primeY;
                        float deltaY = (float) (primeY * 0.1);

                        //Sets the angles

                        float x0z0 = MathHelper.lerp(deltaY, x0y0z0, x0y1z0);
                        float x1z0 = MathHelper.lerp(deltaY, x1y0z0, x1y1z0);
                        float x0z1 = MathHelper.lerp(deltaY, x0y0z1, x0y1z1);
                        float x1z1 = MathHelper.lerp(deltaY, x1y0z1, x1y1z1);

                        for (int primeX = 0; primeX < 4; ++primeX) {
                            int realX = chunkStartX + noiseX * 4 + primeX;
                            int localX = realX & 15;
                            float deltaX = (float)(primeX * 0.25);
                            float gradientZ0 = MathHelper.lerp(deltaX, x0z0, x1z0);
                            float gradientZ1 = MathHelper.lerp(deltaX, x0z1, x1z1);

                            for (int primeZ = 0; primeZ < 4; ++primeZ) {
                                int realZ = chunkStartZ + noiseZ * 4 + primeZ;
                                int localZ = realZ & 15;
                                float deltaZ = (float) (primeZ * 0.25);
                                float noises = MathHelper.lerp(deltaZ, gradientZ0, gradientZ1);

                                if (realY > seaLevel) {
                                    break;
                                }

                                if (realY > maxHeight()) {
                                    continue;
                                }

                                if (noises < 0.0) {
                                    BlockPos pos = new BlockPos(localX, realY, localZ);
                                    BlockState state = Blocks.CAVE_AIR.getDefaultState();
                                    if (realY <= 13) {
                                        state = Blocks.STONE.getDefaultState();
                                    }
                                    if (realY == 0) {
                                        state = Blocks.BEDROCK.getDefaultState();
                                    }
                                    chunk.setBlockState(pos, state, false);
                                }
                            }
                        }
                    }
                }
            }
            float[][] fs = noiseTerrain[0];
            noiseTerrain[0] = noiseTerrain[1];
            noiseTerrain[1] = fs;
        }
        return true;
    }

    private int maxHeight() {
        return this.maxHeight;
    }

    private void samplePillar(float[] buffer, int x, int z, Random rand, OctavesNoiseGenerator caveNoise, OctavesNoiseGenerator offsetNoise, OctavesNoiseGenerator scaleNoise) {
        double offset = offsetNoise.func_205563_a(x, 10, z);
        if (rand.nextInt(15) == 0) {
            offset += 4 + rand.nextFloat() * 2;
        }
        for (int y = 0; y < buffer.length; y++) {
            buffer[y] = (float) ((float) sampleNoise(caveNoise, scaleNoise, x, y, z) + makeColumns((float) offset, x, y, z) + ((offset + makeWalls((float) offset, y))));// + cutNoise(scaleNoise, (float) offset, x, y, z)
        }
    }

    private double sampleNoise(OctavesNoiseGenerator caveNoise, OctavesNoiseGenerator scaleNoise, int x, int y, int z) {
        float noise = 0;
        float amplitude = 1;
        for (int i = 0; i < 6; i++) {
            ImprovedNoiseGenerator sampler = caveNoise.getOctave(i);
            noise += sampler.func_215456_a(x * 5 * amplitude, y * 12 * amplitude, z * 5 * amplitude, 0, 0) / amplitude;
            amplitude *= 0.5;
        }
        noise *= 1.2;
        float scale = (float)((scaleNoise.getOctave(0).func_215456_a(x / 96.0, y / 96.0, z / 96.0, 0, 0) + 0.2) + 15);
        noise += Math.min(scale, 0);
        return noise;
    }

    private double makeWalls(float offset, int y) {
        float scale = 10 + offset;
        float cutOff = (float) Math.max(scale / 9.6, 0);
        cutOff += cutOff + 0.5 * y;
        return cutOff;
    }

    private double makeColumns(float offset, int x, int y, int z) {
//        offset *= MathHelper.floor(2F) + MathHelper.perlinFade(1) * Math.PI / 2;
//        offset *= 1.35F;
//        y *= MathHelper.sin((float) (Math.PI / 0.25));
//        offset += y;
//        return offset;

        offset *= MathHelper.floor(2F) + MathHelper.perlinFade(1) * Math.PI / 2 ;
        y *= MathHelper.sin((float) (Math.PI));
        offset += y;
        return offset;
    }

    @Override
    public boolean shouldCarve(Random rand, int chunkX, int chunkZ, ProbabilityConfig config) {
        return true;
    }

    @Override
    protected boolean func_222708_a(double p_222708_1_, double p_222708_3_, double p_222708_5_, int p_222708_7_) {
        return false;
    }
}