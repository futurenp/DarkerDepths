package com.naterbobber.darkerdepths.worldgen.carvers;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.valueproviders.FloatProvider;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.carver.CarverConfiguration;
import net.minecraft.world.level.levelgen.carver.CarverDebugSettings;
import net.minecraft.world.level.levelgen.heightproviders.HeightProvider;

public class BlockCarverConfiguration extends CarverConfiguration {
    public static final Codec<BlockCarverConfiguration> CODEC = RecordCodecBuilder.create((configurationInstance) -> configurationInstance.group(
            CarverConfiguration.CODEC.forGetter((p_159192_) -> p_159192_),
            FloatProvider.CODEC.fieldOf("horizontal_radius_multiplier").forGetter((p_159190_) -> p_159190_.horizontalRadiusMultiplier),
            FloatProvider.CODEC.fieldOf("vertical_radius_multiplier").forGetter((p_159188_) -> p_159188_.verticalRadiusMultiplier),
            FloatProvider.codec(-1.0F, 1.0F).fieldOf("floor_level").forGetter((p_159186_) -> p_159186_.floorLevel),
            BuiltInRegistries.BLOCK.byNameCodec().fieldOf("placeable").forGetter(config -> config.placeableBlock),
            BuiltInRegistries.BLOCK.byNameCodec().fieldOf("border_block").forGetter(config -> config.borderBlock)
    ).apply(configurationInstance, BlockCarverConfiguration::new));

    public final FloatProvider horizontalRadiusMultiplier;
    public final FloatProvider verticalRadiusMultiplier;
    public final FloatProvider floorLevel;
    public final Block placeableBlock;
    public final Block borderBlock;

    public BlockCarverConfiguration(float probability, HeightProvider y, FloatProvider yScale, VerticalAnchor lavaLevel, CarverDebugSettings debugSettings, HolderSet<Block> replaceable, FloatProvider horizontalRadiusMultiplier, FloatProvider verticalRadiusMultiplier, FloatProvider floorLevel, Block placeableBlock, Block borderBlock) {
        super(probability, y, yScale, lavaLevel, debugSettings, replaceable);
        this.horizontalRadiusMultiplier = horizontalRadiusMultiplier;
        this.verticalRadiusMultiplier = verticalRadiusMultiplier;
        this.floorLevel = floorLevel;
        this.placeableBlock = placeableBlock;
        this.borderBlock = borderBlock;
    }

    public BlockCarverConfiguration(float probability, HeightProvider y, FloatProvider yScale, VerticalAnchor lavaLevel, CarverDebugSettings debugSettings, HolderSet<Block> replaceable, FloatProvider horizontalRadiusMultiplier, FloatProvider verticalRadiusMultiplier, FloatProvider floorLevel, Block placeableBlock) {
        super(probability, y, yScale, lavaLevel, debugSettings, replaceable);
        this.horizontalRadiusMultiplier = horizontalRadiusMultiplier;
        this.verticalRadiusMultiplier = verticalRadiusMultiplier;
        this.floorLevel = floorLevel;
        this.placeableBlock = placeableBlock;
        this.borderBlock = placeableBlock;
    }


    public BlockCarverConfiguration(CarverConfiguration config, FloatProvider horizontalRadiusMultiplier, FloatProvider verticalRadiusMultiplier, FloatProvider floorLevel, Block placeableBlock, Block borderBlock) {
        this(config.probability, config.y, config.yScale, config.lavaLevel, config.debugSettings, config.replaceable, horizontalRadiusMultiplier, verticalRadiusMultiplier, floorLevel, placeableBlock, borderBlock);
    }
}