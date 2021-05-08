package com.naterbobber.darkerdepths.common.world.gen.feature;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.world.gen.blockstateprovider.BlockStateProvider;
import net.minecraft.world.gen.feature.IFeatureConfig;

import java.util.List;

//<>

public class SimpleBlockConfig implements IFeatureConfig {
    public static final Codec<SimpleBlockConfig> CODEC = RecordCodecBuilder.create((instance) -> {
       return instance.group(BlockStateProvider.CODEC.fieldOf("to_place").forGetter((config) -> {
           return config.toPlace;
       }), BlockState.CODEC.listOf().fieldOf("place_on").orElse(ImmutableList.of()).forGetter((config) -> {
           return config.placeOn;
       }), BlockState.CODEC.listOf().fieldOf("place_in").orElse(ImmutableList.of()).forGetter((config) -> {
           return config.placeIn;
       }), BlockState.CODEC.listOf().fieldOf("place_under").orElse(ImmutableList.of()).forGetter((config) -> {
           return config.placeUnder;
       })).apply(instance, SimpleBlockConfig::new);
    });
    public final BlockStateProvider toPlace;
    public final List<BlockState> placeOn;
    public final List<BlockState> placeIn;
    public final List<BlockState> placeUnder;

    public SimpleBlockConfig(BlockStateProvider toPlace, List<BlockState> placeOn, List<BlockState> placeIn, List<BlockState> placeUnder) {
        this.toPlace = toPlace;
        this.placeOn = placeOn;
        this.placeIn = placeIn;
        this.placeUnder = placeUnder;
    }

    public SimpleBlockConfig(BlockStateProvider toPlace) {
        this(toPlace, ImmutableList.of(), ImmutableList.of(), ImmutableList.of());
    }
}