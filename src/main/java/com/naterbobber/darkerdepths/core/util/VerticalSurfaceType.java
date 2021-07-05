package com.naterbobber.darkerdepths.core.util;

import com.mojang.serialization.Codec;
import net.minecraft.util.Direction;
import net.minecraft.util.IStringSerializable;

//<>

public enum VerticalSurfaceType implements IStringSerializable {
    CEILING(Direction.UP, 1, "ceiling"),
    FLOOR(Direction.DOWN, -1, "floor");

    public static final Codec<VerticalSurfaceType> CODEC = IStringSerializable.createEnumCodec(VerticalSurfaceType::values, VerticalSurfaceType::byName);
    private final Direction direction;
    private final int offset;
    private final String name;
    private static final VerticalSurfaceType[] VALUES = values();

    VerticalSurfaceType(Direction direction, int offset, String name) {
        this.direction = direction;
        this.offset = offset;
        this.name = name;
    }

    public Direction getDirection() {
        return this.direction;
    }

    public int getOffset() {
        return this.offset;
    }

    public static VerticalSurfaceType byName(String name) {
        for (VerticalSurfaceType surface : VALUES) {
            if (surface.getString().equals(name)) {
                return surface;
            }
        }

        throw new IllegalArgumentException("Unknown Surface type: " + name);
    }

    @Override
    public String getString() {
        return this.name;
    }
}