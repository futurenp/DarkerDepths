package com.naterbobber.darkerdepths.common.world.gen.carver;

import com.mojang.serialization.Codec;
import net.minecraft.world.gen.carver.CaveWorldCarver;
import net.minecraft.world.gen.feature.ProbabilityConfig;

import java.util.Random;

//<>

public class BasicCarver extends CaveWorldCarver {
    public BasicCarver(Codec<ProbabilityConfig> codec) {
        super(codec, 256);
    }

    protected int getCaveHeight(Random rand) {
        return rand.nextInt(rand.nextInt(120) + 8);
    }

    @Override
    public boolean shouldCarve(Random rand, int chunkX, int chunkZ, ProbabilityConfig config) {
        return rand.nextFloat() <= config.probability;
    }
}