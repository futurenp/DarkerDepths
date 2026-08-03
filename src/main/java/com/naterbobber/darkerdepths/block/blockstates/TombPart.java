package com.naterbobber.darkerdepths.block.blockstates;

import net.minecraft.util.StringRepresentable;

public enum TombPart implements StringRepresentable {
    FRONT_CENTER("front_center", 0, 0),
    FRONT_LEFT("front_left", -1, 0),
    FRONT_RIGHT("front_right", 1, 0),
    BACK_CENTER("back_center", 0, -1),
    BACK_LEFT("back_left", -1, -1),
    BACK_RIGHT("back_right", 1, -1);

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
    public int xOffset() {
        return this.xOffset;
    }
    public int zOffset() {
        return this.zOffset;
    }
}