package com.naterbobber.darkerdepths.block.blockstates;

import net.minecraft.util.StringRepresentable;

public enum PillarState implements StringRepresentable {
    DEFAULT("default"),
    LOWER("lower"),
    MIDDLE("middle"),
    UPPER("upper");

    private final String name;

    PillarState(String name) {
        this.name = name;
    }

    @Override
    public String getSerializedName() {
        return this.name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}