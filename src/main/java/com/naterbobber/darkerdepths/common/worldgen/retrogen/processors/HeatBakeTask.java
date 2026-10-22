package com.naterbobber.darkerdepths.common.worldgen.retrogen.processors;

import java.util.ArrayDeque;
import java.util.function.LongSupplier;

public final class HeatBakeTask {
    public static final int BORDER = 4;
    private static final int CHUNK_SIZE = 16;
    private static final int SECTION_HEIGHT = 16;
    private static final int SCAN_WIDTH = CHUNK_SIZE + BORDER * 2;
    private static final int BLOCKS_PER_LAYER = SCAN_WIDTH * SCAN_WIDTH;
    private static final int[][] NEIGHBOR_OFFSETS = {
            {1, 0, 0}, {-1, 0, 0},
            {0, 1, 0}, {0, -1, 0},
            {0, 0, 1}, {0, 0, -1}
    };

    public interface Access {
        boolean hasSourcesInSection(int sectionY);
        int sourceHeat(int x, int y, int z);
        boolean isHeatable(int x, int y, int z);
        void applyHeat(int x, int y, int z, int heat);
    }

    private record HeatNode(int blockIndex, int heat) {}

    private final int minX;
    private final int minY;
    private final int minZ;
    private final int height;
    private final byte[] highestVisitedHeat;
    private final ArrayDeque<HeatNode> pendingHeat = new ArrayDeque<>();
    private int nextSourceIndex;

    public HeatBakeTask(int chunkX, int chunkZ, int minY, int height) {
        this.minX = chunkX * CHUNK_SIZE - BORDER;
        this.minZ = chunkZ * CHUNK_SIZE - BORDER;
        this.minY = minY;
        this.height = height;
        this.highestVisitedHeat = new byte[BLOCKS_PER_LAYER * height];
    }

    public boolean advance(Access access, long deadline, LongSupplier clock) {
        while (clock.getAsLong() < deadline) {
            if (!pendingHeat.isEmpty()) {
                propagateNextNode(access);
            } else if (nextSourceIndex < highestVisitedHeat.length) {
                scanNextSource(access);
            } else {
                return true;
            }
        }
        return nextSourceIndex == highestVisitedHeat.length && pendingHeat.isEmpty();
    }

    private void propagateNextNode(Access access) {
        var node = pendingHeat.removeFirst();
        if (highestVisitedHeat[node.blockIndex()] == node.heat()) {
            spreadHeat(access, node.blockIndex(), node.heat());
        }
    }

    private void scanNextSource(Access access) {
        int worldY = localY(nextSourceIndex) + minY;
        boolean startsLayer = nextSourceIndex % BLOCKS_PER_LAYER == 0;
        boolean startsSection = Math.floorMod(worldY, SECTION_HEIGHT) == 0;

        if (startsLayer && startsSection && !access.hasSourcesInSection(Math.floorDiv(worldY, SECTION_HEIGHT))) {
            nextSourceIndex = Math.min(highestVisitedHeat.length, nextSourceIndex + BLOCKS_PER_LAYER * SECTION_HEIGHT);
            return;
        }

        int sourceIndex = nextSourceIndex++;
        int worldX = localX(sourceIndex) + minX;
        int worldZ = localZ(sourceIndex) + minZ;
        int sourceHeat = access.sourceHeat(worldX, worldY, worldZ);

        if (sourceHeat > 1) {
            spreadHeat(access, sourceIndex, sourceHeat);
        }
    }

    private void spreadHeat(Access access, int sourceIndex, int sourceHeat) {
        int neighborHeat = sourceHeat - 1;
        if (neighborHeat <= 0) return;

        int sourceX = localX(sourceIndex);
        int sourceY = localY(sourceIndex);
        int sourceZ = localZ(sourceIndex);

        for (var offset : NEIGHBOR_OFFSETS) {
            visitNeighbor(access, sourceX + offset[0], sourceY + offset[1], sourceZ + offset[2], neighborHeat);
        }
    }

    private void visitNeighbor(Access access, int x, int y, int z, int heat) {
        if (!isWithinScanArea(x, y, z)) return;

        int blockIndex = y * BLOCKS_PER_LAYER + z * SCAN_WIDTH + x;
        if (highestVisitedHeat[blockIndex] >= heat) return;

        int worldX = x + minX;
        int worldY = y + minY;
        int worldZ = z + minZ;
        if (!access.isHeatable(worldX, worldY, worldZ)) return;

        highestVisitedHeat[blockIndex] = (byte) heat;
        if (isInsideTargetChunk(x, z)) {
            access.applyHeat(worldX, worldY, worldZ, heat);
        }
        if (heat > 1) {
            pendingHeat.addLast(new HeatNode(blockIndex, heat));
        }
    }

    private boolean isWithinScanArea(int x, int y, int z) {
        return x >= 0 && x < SCAN_WIDTH && z >= 0 && z < SCAN_WIDTH && y >= 0 && y < height;
    }

    private static boolean isInsideTargetChunk(int x, int z) {
        return x >= BORDER && x < BORDER + CHUNK_SIZE && z >= BORDER && z < BORDER + CHUNK_SIZE;
    }

    private static int localX(int blockIndex) {
        return blockIndex % SCAN_WIDTH;
    }

    private static int localY(int blockIndex) {
        return blockIndex / BLOCKS_PER_LAYER;
    }

    private static int localZ(int blockIndex) {
        return blockIndex / SCAN_WIDTH % SCAN_WIDTH;
    }
}
