package com.naterbobber.darkerdepths.common.math;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.naterbobber.darkerdepths.core.util.helpers.MathUtils;
import net.minecraft.util.math.MathHelper;

import java.util.Random;
import java.util.function.Function;

//<>

public class ClampedNormalFloatProvider extends FloatProvider {
    public static final Codec<ClampedNormalFloatProvider> CODEC = RecordCodecBuilder.<ClampedNormalFloatProvider>create((instance) -> {
        return instance.group(Codec.FLOAT.fieldOf("mean").forGetter((provider) -> {
            return provider.mean;
        }), Codec.FLOAT.fieldOf("deviation").forGetter((provider) -> {
            return provider.deviation;
        }), Codec.FLOAT.fieldOf("min").forGetter((provider) -> {
            return provider.min;
        }), Codec.FLOAT.fieldOf("max").forGetter((provider) -> {
            return provider.max;
        })).apply(instance, ClampedNormalFloatProvider::new);
    }).comapFlatMap((provider) -> {
        return provider.max < provider.min ? DataResult.error("Max must be larger than min: [" + provider.min + ", " + provider.max + "]") : DataResult.success(provider);
    }, Function.identity());
    private final float mean;
    private final float deviation;
    private final float min;
    private final float max;

    public static ClampedNormalFloatProvider of(float mean, float deviation, float min, float max) {
        return new ClampedNormalFloatProvider(mean, deviation, min, max);
    }

    public ClampedNormalFloatProvider(float mean, float deviation, float min, float max) {
        this.mean = mean;
        this.deviation = deviation;
        this.min = min;
        this.max = max;
    }

    @Override
    public float get(Random rand) {
        return get(rand, this.mean, this.deviation, this.min, this.max);
    }

    public static float get(Random rand, float mean, float deviation, float min, float max) {
        return MathHelper.clamp(MathUtils.nextGaussian(rand, mean, deviation), min, max);
    }

    @Override
    public float getMin() {
        return this.min;
    }

    @Override
    public float getMax() {
        return this.max;
    }

    @Override
    public FloatProviderType<?> getType() {
        return FloatProviderType.CLAMPED_NORMAL;
    }

    @Override
    public String toString() {
        return "normal(" + this.mean + ", " + this.deviation + ") in [" + this.min + '-' + this.max + ']';
    }
}