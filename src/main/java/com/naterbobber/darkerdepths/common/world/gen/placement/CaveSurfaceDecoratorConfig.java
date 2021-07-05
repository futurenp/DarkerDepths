package com.naterbobber.darkerdepths.common.world.gen.placement;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.naterbobber.darkerdepths.core.util.VerticalSurfaceType;
import net.minecraft.world.gen.placement.IPlacementConfig;

//<>

public class CaveSurfaceDecoratorConfig implements IPlacementConfig {
    public static final Codec<CaveSurfaceDecoratorConfig> CODEC = RecordCodecBuilder.create((instance) -> {
       return instance.group(VerticalSurfaceType.CODEC.fieldOf("surface").forGetter((config) -> {
           return config.surface;
       }), Codec.INT.fieldOf("floor_to_ceiling_search_range").forGetter((config) -> {
           return config.searchRange;
       })).apply(instance, CaveSurfaceDecoratorConfig::new);
    });
    public final VerticalSurfaceType surface;
    public final int searchRange;

    public CaveSurfaceDecoratorConfig(VerticalSurfaceType surface, int searchRange) {
        this.surface = surface;
        this.searchRange = searchRange;
    }
}