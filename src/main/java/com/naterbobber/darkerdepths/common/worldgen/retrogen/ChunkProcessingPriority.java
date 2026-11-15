package com.naterbobber.darkerdepths.common.worldgen.retrogen;

import java.util.List;

public final class ChunkProcessingPriority {
    public record PlayerChunk(int x, int z) {
    }

    public static long distance(int x, int z, List<PlayerChunk> players, int radius) {
        long nearest = Long.MAX_VALUE;

        for (var player : players) {
            long dx = (long) x - player.x();
            long dz = (long) z - player.z();

            if (Math.abs(dx) <= radius && Math.abs(dz) <= radius) {
                nearest = Math.min(nearest, dx * dx + dz * dz);
            }
        }

        return nearest;
    }
}
