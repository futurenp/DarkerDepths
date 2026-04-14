package com.naterbobber.darkerdepths.worldgen;

import com.mojang.datafixers.util.Pair;
import com.naterbobber.darkerdepths.config.DDConfig;
import com.naterbobber.darkerdepths.config.builders.DDBiomeConfigBuilder;
import com.naterbobber.darkerdepths.util.DDResourceKeys;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Climate;

import java.util.function.Consumer;

public class ClimateParamsUtil {
    public static Climate.ParameterPoint climateParamsFromConfig(DDBiomeConfigBuilder config) {
        return Climate.parameters(
                Climate.Parameter.span(config.tempMin().get().floatValue(), config.tempMax().get().floatValue()),
                Climate.Parameter.span(config.humidityMin().get().floatValue(), config.humidityMax().get().floatValue()),
                Climate.Parameter.span(config.continentalnessMin().get().floatValue(), config.continentalnessMax().get().floatValue()),
                Climate.Parameter.span(config.erosionMin().get().floatValue(), config.erosionMax().get().floatValue()),
                Climate.Parameter.span(config.depthMin().get().floatValue(), config.depthMax().get().floatValue()),
                Climate.Parameter.span(config.weirdnessMin().get().floatValue(), config.weirdnessMax().get().floatValue()),
                config.offset().get().floatValue());
    }

    public static void createBiomes(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> consumer) {
        consumer.accept(Pair.of(climateParamsFromConfig(DDConfig.SANDY_CATACOMBS_CLIMATE), DDResourceKeys.Biomes.SANDY_CATACOMBS));
        consumer.accept(Pair.of(climateParamsFromConfig(DDConfig.GLOWSHROOM_FOREST_CLIMATE), DDResourceKeys.Biomes.GLOWSHROOM_FOREST));
        consumer.accept(Pair.of(climateParamsFromConfig(DDConfig.MOLTEN_CAVERN_CLIMATE), DDResourceKeys.Biomes.MOLTEN_CAVERN));
    }
}
