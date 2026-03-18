package com.naterbobber.darkerdepths.worldgen;

import com.naterbobber.darkerdepths.config.builders.DDBiomeConfigBuilder;
import net.minecraft.world.level.biome.Climate;

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
}
