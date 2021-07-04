package com.naterbobber.darkerdepths.core.util.helpers;

import net.minecraft.util.SharedSeedRandom;

//<>

public class SeedRandomUtils {
    public static long setLayeredSeed(SharedSeedRandom seedRandom, long seed, int x, int y, int z) {
        seedRandom.setSeed(seed);
        long xIn = seedRandom.nextLong();
        long yIn = seedRandom.nextLong();
        long zIn = seedRandom.nextLong();
        long layeredSeed = x * xIn ^ y * yIn ^ z * zIn ^ seed;
        seedRandom.setSeed(layeredSeed);
        return layeredSeed;
    }
}