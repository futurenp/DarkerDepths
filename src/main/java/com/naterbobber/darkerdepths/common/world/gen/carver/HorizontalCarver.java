package com.naterbobber.darkerdepths.common.world.gen.carver;

import com.mojang.serialization.Codec;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.feature.ProbabilityConfig;

import java.util.BitSet;
import java.util.Random;
import java.util.function.Function;

//<>

public class HorizontalCarver extends BasicCarver {
    public HorizontalCarver(Codec<ProbabilityConfig> codec) {
        super(codec);
    }

    @Override
    public boolean carveRegion(IChunk chunk, Function<BlockPos, Biome> posToBiome, Random random, int seaLevel, int chunkX, int chunkZ, int mainChunkX, int mainChunkZ, BitSet carvingMask, ProbabilityConfig carverConfig) {
        double x = (chunkX * 16 + random.nextInt(16));
        double y = this.getCaveHeight(random);
        double z = (chunkZ * 16 + random.nextInt(16));
        float yaw = 1.5f + 4.5F + random.nextFloat() * 3.5F; // Base val: 1.5
        float pitch = yaw * 0.35f * (random.nextFloat() + 0.65f);

        for (int i = 0; i < random.nextInt(4) + 6; i++) {
            this.func_227208_a_(chunk, posToBiome, random.nextLong(), seaLevel, mainChunkX, mainChunkZ, x + 1.0D, y, z, yaw, pitch, carvingMask);

            x += (random.nextDouble() - random.nextDouble()) * yaw;
            y += MathHelper.sin(pitch) * 0.25;
            z += (random.nextDouble() - random.nextDouble()) * yaw;

            yaw += (random.nextFloat() - random.nextFloat()) * random.nextFloat() * 0.5F;
            pitch += (random.nextFloat() - random.nextFloat()) * random.nextFloat() * 0.5F;
        }

        return true;
    }

    @Override
    protected boolean func_222708_a(double scaledRelativeX, double scaledRelativeY, double scaledRelativeZ, int y) {
        return scaledRelativeX * scaledRelativeX + scaledRelativeY * scaledRelativeY + scaledRelativeZ * scaledRelativeZ >= 0.65D;
    }
}