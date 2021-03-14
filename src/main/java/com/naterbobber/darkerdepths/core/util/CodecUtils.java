package com.naterbobber.darkerdepths.core.util;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import net.minecraft.util.datafix.codec.RangeCodec;

import java.util.function.Function;

//<>

public class CodecUtils extends RangeCodec {
    static <N extends Number & Comparable<N>> Function<N, DataResult<N>> checkRange(final N minInclusive, final N maxInclusive) {
        return value -> {
            if (value.compareTo(minInclusive) >= 0 && value.compareTo(maxInclusive) <= 0) {
                return DataResult.success(value);
            }
            return DataResult.error("Value " + value + " outside of range [" + minInclusive + ":" + maxInclusive + "]", value);
        };
    }

    public static Codec<Float> floatRange(final float minInclusive, final float maxInclusive) {
        final Function<Float, DataResult<Float>> checker = checkRange(minInclusive, maxInclusive);
        return Codec.FLOAT.flatXmap(checker, checker);
    }
}