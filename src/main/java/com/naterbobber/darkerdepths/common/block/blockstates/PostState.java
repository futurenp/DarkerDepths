package com.naterbobber.darkerdepths.common.block.blockstates;

import net.minecraft.util.StringRepresentable;

public enum PostState implements StringRepresentable {
    CHAIN("chain"),
    OTHER_POST("other_post"),
    NONE("none");

    private final String name;

    PostState(String name) {
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
