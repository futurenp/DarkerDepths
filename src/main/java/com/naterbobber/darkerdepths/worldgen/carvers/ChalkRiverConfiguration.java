package com.naterbobber.darkerdepths.worldgen.carvers;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderSet;
import net.minecraft.util.valueproviders.FloatProvider;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.carver.CarverConfiguration;
import net.minecraft.world.level.levelgen.carver.CarverDebugSettings;
import net.minecraft.world.level.levelgen.heightproviders.HeightProvider;

public class ChalkRiverConfiguration extends CarverConfiguration {
    // These are your custom fields
    public final int startY;
    public final double slope;

    // The Codec MUST include all fields from the parent CarverConfiguration
    public static final Codec<ChalkRiverConfiguration> CODEC = RecordCodecBuilder.create((instance) ->
            instance.group(
                    CarverConfiguration.CODEC.forGetter((config) -> config), // Standard fields
                    Codec.INT.fieldOf("start_y").forGetter((config) -> config.startY),
                    Codec.DOUBLE.fieldOf("slope").forGetter((config) -> config.slope)
            ).apply(instance, (parent, startY, slope) ->
                    new ChalkRiverConfiguration(parent.probability, parent.y, parent.yScale, parent.lavaLevel, parent.debugSettings, parent.replaceable, startY, slope)
            )
    );

    public ChalkRiverConfiguration(float probability, HeightProvider y, FloatProvider yScale, VerticalAnchor lavaLevel, CarverDebugSettings debugSettings, HolderSet<Block> replaceable, int startY, double slope) {
        super(probability, y, yScale, lavaLevel, debugSettings, replaceable);
        this.startY = startY;
        this.slope = slope;
    }
}