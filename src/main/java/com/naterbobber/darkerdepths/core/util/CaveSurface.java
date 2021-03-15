package com.naterbobber.darkerdepths.core.util;

import com.mojang.serialization.Codec;
import net.minecraft.util.Direction;
import net.minecraft.util.IStringSerializable;

//<>

public enum CaveSurface implements IStringSerializable {
    CEILING(Direction.UP, 1, "ceiling"),
    FLOOR(Direction.DOWN, -1, "floor");

    public static final Codec<CaveSurface> CODEC = IStringSerializable.createEnumCodec(CaveSurface::values, CaveSurface::byName);
    private final Direction direction;
    private final int y;
    private final String id;
    private static final CaveSurface[] VALUES = values();

    CaveSurface(Direction direction, int y, String id) {
        this.direction = direction;
        this.y = y;
        this.id = id;
    }

    public Direction getDirection() {
        return this.direction;
    }

    public int getY() {
        return this.y;
    }

    public static CaveSurface byName(String name) {
        for (CaveSurface surface : VALUES) {
            if (surface.getString().equals(name)) {
                return surface;
            }
        }

        throw new IllegalArgumentException("Unknown Surface type: " + name);
    }

    @Override
    public String getString() {
        return this.id;
    }
}