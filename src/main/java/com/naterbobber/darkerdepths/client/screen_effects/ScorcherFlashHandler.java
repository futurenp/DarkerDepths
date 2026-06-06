package com.naterbobber.darkerdepths.client.screen_effects;

public class ScorcherFlashHandler {
    private static int flashTicksRemaining = 0;
    private static int maxFlashTicks = 0;

    public static void triggerFlash(int ticks) {
        flashTicksRemaining = Math.max(flashTicksRemaining, ticks);
        maxFlashTicks = flashTicksRemaining;
    }

    public static boolean isFlashed() {
        return flashTicksRemaining > 0;
    }

    public static int getTicksRemaining() {
        return flashTicksRemaining;
    }

    public static int getMaxTicks() {
        return maxFlashTicks;
    }

    public static void setFlashTicksRemaining(int ticks) {
        flashTicksRemaining = ticks;
    }

    public static void tick() {
        if (flashTicksRemaining > 0) flashTicksRemaining--;
    }
}
