package com.naterbobber.darkerdepths.core.util.helpers;

import java.util.Random;

//<>

public class MathUtils {
    public static double inverseLerp(double ptc, double start, double end) {
        return (ptc - start) / (end - start);
    }

    public static int nextBetween(Random random, int min, int max) {
        return random.nextInt(max - min + 1) + min;
    }

    public static float nextBetween(Random random, float min, float max) {
        return random.nextFloat() * (max - min) + min;
    }

    public static float nextGaussian(Random rand, float height, float heightDeviation) {
        return height + (float)rand.nextGaussian() * heightDeviation;
    }

}