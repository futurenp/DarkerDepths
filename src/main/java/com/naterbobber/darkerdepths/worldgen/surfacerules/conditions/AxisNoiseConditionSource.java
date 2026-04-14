package com.naterbobber.darkerdepths.worldgen.surfacerules.conditions;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.KeyDispatchDataCodec;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.minecraft.world.level.levelgen.synth.NormalNoise;

public record AxisNoiseConditionSource(
        ResourceKey<NormalNoise.NoiseParameters> noise,
        double minThreshold,
        double maxThreshold,
        boolean useX,
        boolean useY,
        boolean useZ
) implements SurfaceRules.ConditionSource {

    public static final KeyDispatchDataCodec<AxisNoiseConditionSource> CODEC = KeyDispatchDataCodec.of(
            RecordCodecBuilder.mapCodec(instance -> instance.group(
                    ResourceKey.codec(Registries.NOISE).fieldOf("noise").forGetter(AxisNoiseConditionSource::noise),
                    Codec.DOUBLE.fieldOf("min_threshold").forGetter(AxisNoiseConditionSource::minThreshold),
                    Codec.DOUBLE.fieldOf("max_threshold").forGetter(AxisNoiseConditionSource::maxThreshold),
                    Codec.BOOL.optionalFieldOf("use_x", true).forGetter(AxisNoiseConditionSource::useX),
                    Codec.BOOL.optionalFieldOf("use_y", true).forGetter(AxisNoiseConditionSource::useY),
                    Codec.BOOL.optionalFieldOf("use_z", true).forGetter(AxisNoiseConditionSource::useZ)
            ).apply(instance, AxisNoiseConditionSource::new))
    );

    @Override
    public KeyDispatchDataCodec<? extends SurfaceRules.ConditionSource> codec() {
        return CODEC;
    }

    @Override
    public SurfaceRules.Condition apply(final SurfaceRules.Context p_context) {
        final NormalNoise normalnoise = p_context.randomState.getOrCreateNoise(this.noise);
        class AxisNoiseCondition extends SurfaceRules.LazyYCondition {

            AxisNoiseCondition() {
                super(p_context);
            }

            @Override
            protected boolean compute() {
                double evalX = useX ? (double) this.context.blockX : 0.0D;
                double evalY = useY ? (double) this.context.blockY : 0.0D;
                double evalZ = useZ ? (double) this.context.blockZ : 0.0D;

                double currentNoiseValue = normalnoise.getValue(evalX, evalY, evalZ);
                return currentNoiseValue >= minThreshold && currentNoiseValue <= maxThreshold;
            }
        }

        return new AxisNoiseCondition();
    }
}