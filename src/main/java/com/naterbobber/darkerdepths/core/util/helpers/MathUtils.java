package com.naterbobber.darkerdepths.core.util.helpers;

import net.minecraft.util.math.MathHelper;

import java.util.Random;

//<>

public class MathUtils {
    public static double inverseLerp(double ptc, double start, double end) {
        return (ptc - start) / (end - start);
    }

    public static double square(double num) {
        return num * num;
    }

    public static double clampedLerpFromProgress(double ptc, double start, double end, double lowerBnd, double upperBnd) {
        return MathHelper.clampedLerp(lowerBnd, upperBnd, inverseLerp(ptc, start, end));
    }

    public static double lerpFromProgress(double ptc, double start, double end, double lowerBnd, double upperBnd) {
        return MathHelper.lerp(inverseLerp(ptc, start, end), lowerBnd, upperBnd);
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

    public static double magnitude(int x, double y, int z) {
        return MathHelper.sqrt((double)(x * x) + y * y + (double)(z * z));
    }
}