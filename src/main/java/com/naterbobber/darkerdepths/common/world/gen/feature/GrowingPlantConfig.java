package com.naterbobber.darkerdepths.common.world.gen.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.naterbobber.darkerdepths.common.math.IntProvider;
import com.naterbobber.darkerdepths.core.util.helpers.DirectionHelper;
import net.minecraft.util.Direction;
import net.minecraft.util.WeightedList;
import net.minecraft.world.gen.blockstateprovider.BlockStateProvider;
import net.minecraft.world.gen.feature.IFeatureConfig;

//<>

public class GrowingPlantConfig implements IFeatureConfig {
    public static final Codec<GrowingPlantConfig> CODEC = RecordCodecBuilder.create((instance) -> {
        return instance.group(WeightedList.getCodec(IntProvider.CODEC).fieldOf("height_distribution").forGetter((config) -> {
            return config.heightDistribution;
        }), DirectionHelper.CODEC.fieldOf("direction").forGetter((config) -> {
            return config.direction;
        }), BlockStateProvider.CODEC.fieldOf("body_provider").forGetter((config) -> {
            return config.bodyProvider;
        }), BlockStateProvider.CODEC.fieldOf("head_provider").forGetter((config) -> {
            return config.headProvider;
        }), Codec.BOOL.fieldOf("allow_water").forGetter((config) -> {
            return config.allowWater;
        })).apply(instance, GrowingPlantConfig::new);
    });
    public final WeightedList<IntProvider> heightDistribution;
    public final Direction direction;
    public final BlockStateProvider bodyProvider;
    public final BlockStateProvider headProvider;
    public final boolean allowWater;

    public GrowingPlantConfig(WeightedList<IntProvider> heightDistribution, Direction direction, BlockStateProvider bodyProvider, BlockStateProvider headProvider, boolean allowWater) {
        this.heightDistribution = heightDistribution;
        this.direction = direction;
        this.bodyProvider = bodyProvider;
        this.headProvider = headProvider;
        this.allowWater = allowWater;
    }
}