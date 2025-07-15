package com.naterbobber.darkerdepths.blocks;

import net.minecraft.util.StringRepresentable;

/**
 * Represents the 6 different parts of the 2x3 tomb structure.
 * The offsets are relative to the BOTTOM_LEFT block, assuming a NORTH facing.
 * A positive xOffset moves right.
 * A positive zOffset moves forward.
 */
public enum TombPart implements StringRepresentable {
    // Row 1 (Closest to player when placing is z=0)
    BOTTOM_LEFT("bottom_left", 0, 0),
    BOTTOM_RIGHT("bottom_right", 1, 0),

    // Row 2
    MIDDLE_LEFT("middle_left", 0, 1),
    MIDDLE_RIGHT("middle_right", 1, 1),

    // Row 3 (Farthest from player)
    TOP_LEFT("top_left", 0, 2),
    TOP_RIGHT("top_right", 1, 2);

    private final String name;
    private final int xOffset;
    private final int zOffset;

    TombPart(String name, int xOffset, int zOffset) {
        this.name = name;
        this.xOffset = xOffset;
        this.zOffset = zOffset;
    }

    @Override
    public String getSerializedName() {
        return this.name;
    }

    public int getxOffset() {
        return xOffset;
    }

    public int getzOffset() {
        return zOffset;
    }

    public int getyOffset() {
        return 0; // Structure is flat
    }

    @Override
    public String toString() {
        return this.name;
    }
}