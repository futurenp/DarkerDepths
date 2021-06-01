package com.naterbobber.darkerdepths.common.world.gen.carver;

import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkSection;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.ImprovedNoiseGenerator;
import net.minecraft.world.gen.OctavesNoiseGenerator;
import net.minecraft.world.gen.carver.WorldCarver;
import net.minecraft.world.gen.feature.ProbabilityConfig;

import java.util.BitSet;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.IntStream;

public class CavernCarver extends WorldCarver<ProbabilityConfig> {
    private final ChunkSection[] sections = new ChunkSection[16];
    private long seed;
    private long worldSeed;
    private OctavesNoiseGenerator caveNoise;
    private OctavesNoiseGenerator offsetNoise;
    private OctavesNoiseGenerator scaleNoise;

    public CavernCarver(Codec<ProbabilityConfig> codec) {
        super(codec, 256);
    }

    protected boolean func_227208_a_(IChunk chunk, Function<BlockPos, Biome> biomePos, long seed, int seaLevel, int chunkX, int chunkZ, double randOffsetXCoord, double startY, double randOffsetZCoord, double p_227208_14_, double p_227208_16_, BitSet carvingMask) {
        this.worldSeed = seed;
        return super.func_227208_a_(chunk, biomePos, this.worldSeed, seaLevel, chunkX, chunkZ, randOffsetXCoord, startY, randOffsetZCoord, p_227208_14_, p_227208_16_, carvingMask);
    }

    public boolean carveRegion(IChunk chunkIn, Function<BlockPos, Biome> biomePos, Random rand, int seaLevel, int chunkX, int chunkZ, int mainChunkX, int mainChunkZ, BitSet carvingMask, ProbabilityConfig config) {
        if (mainChunkX == chunkX && mainChunkZ == chunkZ) {
            Heightmap floor = chunkIn.getHeightmap(Heightmap.Type.OCEAN_FLOOR_WG);
            int[] heights = new int[256];

            for(int x = 0; x < 16; ++x) {
                for(int z = 0; z < 16; ++z) {
                    heights[x * 16 + z] = floor.getHeight(x, z);
                }
            }

            long seed = this.worldSeed;
            if (this.caveNoise == null || this.seed == seed) {
                SharedSeedRandom chunkRandom = new SharedSeedRandom(seed);
                this.caveNoise = new OctavesNoiseGenerator(chunkRandom, IntStream.rangeClosed(-5, 0));
                this.offsetNoise = new OctavesNoiseGenerator(chunkRandom, IntStream.rangeClosed(-2, 0));
                this.scaleNoise = new OctavesNoiseGenerator(chunkRandom, IntStream.rangeClosed(0, 0));
                this.seed = seed;
            }

            int chunkStartX = chunkX << 4;
            int chunkStartZ = chunkZ << 4;
            double[][][] noiseData = new double[2][5][9];

            int noiseX;
            for(noiseX = 0; noiseX < 5; ++noiseX) {
                noiseData[0][noiseX] = new double[9];
                sampleNoiseColumn(noiseData[0][noiseX], chunkX * 4, chunkZ * 4 + noiseX, this.caveNoise, this.offsetNoise, this.scaleNoise);
                noiseData[1][noiseX] = new double[9];
            }

            for(noiseX = 0; noiseX < 4; ++noiseX) {
                int noiseZ;
                for(noiseZ = 0; noiseZ < 5; ++noiseZ) {
                    sampleNoiseColumn(noiseData[1][noiseZ], chunkX * 4 + noiseX + 1, chunkZ * 4 + noiseZ, this.caveNoise, this.offsetNoise, this.scaleNoise);
                }

                for(noiseZ = 0; noiseZ < 4; ++noiseZ) {
                    ChunkSection section = this.getSection(15);

                    for(int noiseY = 7; noiseY >= 0; --noiseY) {
                        double x0z0y0 = noiseData[0][noiseZ][noiseY];
                        double x0z1y0 = noiseData[0][noiseZ + 1][noiseY];
                        double x1z0y0 = noiseData[1][noiseZ][noiseY];
                        double x1z1y0 = noiseData[1][noiseZ + 1][noiseY];
                        double x0z0y1 = noiseData[0][noiseZ][noiseY + 1];
                        double x0z1y1 = noiseData[0][noiseZ + 1][noiseY + 1];
                        double x1z0y1 = noiseData[1][noiseZ][noiseY + 1];
                        double x1z1y1 = noiseData[1][noiseZ + 1][noiseY + 1];

                        for(int pieceY = 7; pieceY >= 0; --pieceY) {
                            int realY = noiseY * 8 + pieceY;
                            int sectionY = realY >> 4;
                            if (section.getYLocation() >> 4 != sectionY) {
                                section = this.getSection(sectionY);
                            }

                            double yLerp = (double)pieceY / 8.0D;
                            double x0z0 = MathHelper.lerp(yLerp, x0z0y0, x0z0y1);
                            double x1z0 = MathHelper.lerp(yLerp, x1z0y0, x1z0y1);
                            double x0z1 = MathHelper.lerp(yLerp, x0z1y0, x0z1y1);
                            double x1z1 = MathHelper.lerp(yLerp, x1z1y0, x1z1y1);

                            for(int pieceX = 0; pieceX < 4; ++pieceX) {
                                int realX = chunkStartX + noiseX * 4 + pieceX;
                                int localX = realX & 15;
                                double xLerp = (double)pieceX / 4.0D;
                                double z0 = MathHelper.lerp(xLerp, x0z0, x1z0);
                                double z1 = MathHelper.lerp(xLerp, x0z1, x1z1);

                                for(int pieceZ = 0; pieceZ < 4; ++pieceZ) {
                                    int realZ = chunkStartZ + noiseZ * 4 + pieceZ;
                                    int localZ = realZ & 15;
                                    double zLerp = (double)pieceZ / 4.0D;
                                    double density = MathHelper.lerp(zLerp, z0, z1);
                                    int heightAt = heights[localX * 16 + localZ];
                                    if (realY > heightAt - 12) {
                                        density += 4.8D;
                                    }

                                    if (realY <= heightAt && density < 0.0D) {
                                        BlockState state = Blocks.CAVE_AIR.getDefaultState();
                                        if (realY < 11) {
                                            state = Blocks.LAVA.getDefaultState();
                                        }

                                        chunkIn.setBlockState(new BlockPos(localX, realY, localZ), state, false);
                                        int i = localX | localZ << 4 | realY << 8;
                                        carvingMask.set(i);
                                    }
                                }
                            }
                        }
                    }
                }

                double[][] xColumn = noiseData[0];
                noiseData[0] = noiseData[1];
                noiseData[1] = xColumn;
            }

            return true;
        } else {
            return false;
        }
    }

    public static void sampleNoiseColumn(double[] buffer, int x, int z, OctavesNoiseGenerator caveNoise, OctavesNoiseGenerator offsetNoise, OctavesNoiseGenerator scaleNoise) {
        double offset = offsetNoise.func_205563_a((double)x / 128.0D, 5423.434D, (double)z / 128.0D) * 5.45D;
        Random random = new Random(((long)x << 1) * 341873128712L + ((long)z << 1) * 132897987541L);
        if (random.nextInt(24) == 0) {
            offset += 4.0D + random.nextDouble() * 6.0D;
        }

        for(int y = 0; y < buffer.length; ++y) {
            buffer[y] = sampleNoise(caveNoise, scaleNoise, x, y, z) + getFalloff(offset, y);
        }

    }

    private static double sampleNoise(OctavesNoiseGenerator caveNoise, OctavesNoiseGenerator scaleNoise, int x, int y, int z) {
        double noise = 0.0D;
        double amplitude = 1.0D;

        for(int i = 0; i < 6; ++i) {
            ImprovedNoiseGenerator sampler = caveNoise.getOctave(i);
            noise += sampler.func_215456_a((double)x * 2.63D * amplitude, (double)y * 12.18D * amplitude, (double)z * 2.63D * amplitude, 0.0D, 0.0D) / amplitude;
            amplitude /= 2.0D;
        }

        noise /= 1.25D;
        double scale = (scaleNoise.getOctave(0).func_215456_a((double)x / 96.0D, (double)y / 96.0D, (double)z / 96.0D, 0.0D, 0.0D) + 0.2D) * 30.0D;
        noise += Math.min(scale, 0.0D);
        return noise;
    }

    private static double getFalloff(double offset, int y) {
        double falloffScale = 43D + offset;
        double falloff = Math.max(falloffScale / (double)y, 0.0D);
        falloff += Math.max(falloffScale / (double)(8 - y), 0.0D);
        double scaledY = (double)y + 10.0D;
        falloff = 1.5D * falloff - 0.1D * scaledY * scaledY - -4.0D * (double)y;
        return falloff;
    }

    protected boolean func_222708_a(double scaledRelativeX, double scaledRelativeY, double scaledRelativeZ, int y) {
        return false;
    }

    public boolean shouldCarve(Random rand, int chunkX, int chunkZ, ProbabilityConfig config) {
        return true;
    }

    public ChunkSection getSection(int sectionId) {
        if (this.sections[sectionId] == Chunk.EMPTY_SECTION) {
            this.sections[sectionId] = new ChunkSection(sectionId << 4);
        }

        return this.sections[sectionId];
    }
}
