package com.naterbobber.darkerdepths.worldgen.features.config;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;

public record RandomFloorPlacementConfig(
        WeightedStateProvider stateProvider,
        HolderSet<Block> validFloorBlocks,
        int tries,
        int xzRadius,
        int yHeight,
        int placeCount
) implements FeatureConfiguration {
    public static final Codec<RandomFloorPlacementConfig> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            WeightedStateProvider.CODEC.fieldOf("state_provider").forGetter(RandomFloorPlacementConfig::stateProvider),
            RegistryCodecs.homogeneousList(Registries.BLOCK).fieldOf("valid_floor_blocks").forGetter(RandomFloorPlacementConfig::validFloorBlocks),
            Codec.INT.fieldOf("tries").forGetter(RandomFloorPlacementConfig::tries),
            Codec.INT.fieldOf("xz_radius").forGetter(RandomFloorPlacementConfig::xzRadius),
            Codec.INT.fieldOf("y_height").forGetter(RandomFloorPlacementConfig::yHeight),
            Codec.INT.fieldOf("placement_count").forGetter(RandomFloorPlacementConfig::placeCount)
    ).apply(instance, RandomFloorPlacementConfig::new));
}