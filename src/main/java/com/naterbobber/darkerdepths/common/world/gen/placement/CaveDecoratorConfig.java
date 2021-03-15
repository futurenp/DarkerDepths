package com.naterbobber.darkerdepths.common.world.gen.placement;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.naterbobber.darkerdepths.core.util.CaveSurface;
import net.minecraft.world.gen.placement.IPlacementConfig;

//<>

public class CaveDecoratorConfig implements IPlacementConfig {
    public static final Codec<CaveDecoratorConfig> CODEC = RecordCodecBuilder.create((instance) -> {
       return instance.group(CaveSurface.CODEC.fieldOf("surface").forGetter((config) -> {
           return config.surface;
       }), Codec.INT.fieldOf("floor_to_ceiling_search_range").forGetter((config) -> {
           return config.floorToCeilingSearchRange;
       })).apply(instance, CaveDecoratorConfig::new);
    });
    public final CaveSurface surface;
    public final int floorToCeilingSearchRange;

    public CaveDecoratorConfig(CaveSurface surface, int floorToCeilingSearchRange) {
        this.surface = surface;
        this.floorToCeilingSearchRange = floorToCeilingSearchRange;
    }
}