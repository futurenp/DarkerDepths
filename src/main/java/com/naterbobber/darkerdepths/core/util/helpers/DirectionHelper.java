package com.naterbobber.darkerdepths.core.util.helpers;

import com.mojang.serialization.Codec;
import net.minecraft.util.Direction;
import net.minecraft.util.IStringSerializable;

//<>

public class DirectionHelper {
    public static final Codec<Direction> CODEC = IStringSerializable.createEnumCodec(Direction::values, Direction::byName);
}