package com.naterbobber.darkerdepths.worldgen;

import com.mojang.datafixers.util.Pair;
import com.naterbobber.darkerdepths.config.builders.DDBiomeConfigBuilder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Climate;

import javax.annotation.Nullable;
import java.util.function.Consumer;

public record BiomeParameters(
        @Nullable Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> mapper,
        Climate.Parameter temperature,
        Climate.Parameter humidity,
        Climate.Parameter continentalness,
        Climate.Parameter erosion,
        Climate.Parameter weirdness,
        Climate.Parameter depth,
        float offset,
        @Nullable ResourceKey<Biome> biomeResourceKey,
        boolean enabled
) {
    public static BiomeParameters fromConfigWithMapper(@Nullable Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> mapper, DDBiomeConfigBuilder config, @Nullable ResourceKey<Biome> biomeResourceKey) {
        return new BiomeParameters(
                mapper,
                Climate.Parameter.span(config.tempMin().get().floatValue(), config.tempMax().get().floatValue()),
                Climate.Parameter.span(config.humidityMin().get().floatValue(), config.humidityMax().get().floatValue()),
                Climate.Parameter.span(config.continentalnessMin().get().floatValue(), config.continentalnessMax().get().floatValue()),
                Climate.Parameter.span(config.erosionMin().get().floatValue(), config.erosionMax().get().floatValue()),
                Climate.Parameter.span(config.weirdnessMin().get().floatValue(), config.weirdnessMax().get().floatValue()),
                Climate.Parameter.span(config.depthMin().get().floatValue(), config.depthMax().get().floatValue()),
                config.offset().get().floatValue(),
                biomeResourceKey,
                config.enabled().get()
        );
    }

    public static BiomeParameters fromConfig(DDBiomeConfigBuilder config, ResourceKey<Biome> biomeResourceKey) {
        return BiomeParameters.fromConfigWithMapper(null, config, biomeResourceKey);
    }

    public static BiomeParameters fromConfig(DDBiomeConfigBuilder config) {
        return BiomeParameters.fromConfig(config, null);
    }

    public static Climate.ParameterPoint parametersFromConfig(DDBiomeConfigBuilder config) {
        return BiomeParameters.fromConfig(config).toParameterPoints();
    }

    public Climate.ParameterPoint toParameterPoints() {
        return Climate.parameters(
                temperature,
                humidity,
                continentalness,
                erosion,
                depth,
                weirdness,
                offset
        );
    }
}
