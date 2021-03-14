package com.naterbobber.darkerdepths.common.world.gen.feature;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.naterbobber.darkerdepths.core.util.CodecUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.Direction;
import net.minecraft.util.datafix.codec.RangeCodec;
import net.minecraft.world.gen.feature.IFeatureConfig;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

//<>

public class AmberConfig implements IFeatureConfig {
    public static final Codec<AmberConfig> CODEC = RecordCodecBuilder.create((instance) -> {
        return instance.group(RangeCodec.func_232989_a_(1, 64).fieldOf("search_range").withDefault(10).forGetter((config) -> {
            return config.searchRange;
        }), Codec.BOOL.fieldOf("can_place_on_floor").withDefault(false).forGetter((config) -> {
            return config.canPlaceOnFloor;
        }), Codec.BOOL.fieldOf("can_place_on_ceiling").withDefault(false).forGetter((config) -> {
            return config.canPlaceOnCeiling;
        }), Codec.BOOL.fieldOf("can_place_on_wall").withDefault(false).forGetter((config) -> {
            return config.canPlaceOnWall;
        }), CodecUtils.floatRange(0.0F, 1.0F).fieldOf("chance_of_spreading").withDefault(0.5F).forGetter((config) -> {
            return config.chanceOfSpreading;
        }), BlockState.BLOCKSTATE_CODEC.listOf().fieldOf("can_be_placed_on").forGetter((config) -> {
            return new ArrayList<>(config.canBePlacedOn);
        })).apply(instance, AmberConfig::new);
    });
    public final int searchRange;
    public final boolean canPlaceOnFloor;
    public final boolean canPlaceOnCeiling;
    public final boolean canPlaceOnWall;
    public final float chanceOfSpreading;
    public final List<BlockState> canBePlacedOn;
    public final List<Direction> validDirections;

    public AmberConfig(int searchRange, boolean canPlaceOnFloor, boolean canPlaceOnCeiling, boolean canPlaceOnWall, float chanceOfSpreading, List<BlockState> canBePlacedOn) {
        this.searchRange = searchRange;
        this.canPlaceOnFloor = canPlaceOnFloor;
        this.canPlaceOnCeiling = canPlaceOnCeiling;
        this.canPlaceOnWall = canPlaceOnWall;
        this.chanceOfSpreading = chanceOfSpreading;
        this.canBePlacedOn = canBePlacedOn;
        List<Direction> directions = Lists.newArrayList();
        if (canPlaceOnCeiling) {
            directions.add(Direction.UP);
        }
        if (canPlaceOnFloor) {
            directions.add(Direction.DOWN);
        }
        if (canPlaceOnWall) {
            Direction.Plane.HORIZONTAL.forEach(directions::add);
        }

        this.validDirections = Collections.unmodifiableList(directions);
    }

    public boolean canBePlacedOn(Block validBlock) {
        return this.canBePlacedOn.stream().anyMatch((block) -> block.isIn(validBlock));
    }
}