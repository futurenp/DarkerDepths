package com.naterbobber.darkerdepths.core.util;

import net.minecraft.util.IStringSerializable;

public enum RopePart implements IStringSerializable {
    TOP("top"),
    MIDDLE("middle"),
    BOTTOM("bottom");

    private final String name;

    RopePart(String string) {
        this.name = string;
    }

    public String toString() {
        return this.name;
    }

    @Override
    public String getString() {
        return this.name;
    }
}