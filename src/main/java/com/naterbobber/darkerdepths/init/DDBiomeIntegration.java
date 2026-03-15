package com.naterbobber.darkerdepths.init;

import com.naterbobber.darkerdepths.config.builders.DDBiomeConfigBuilder;
import com.naterbobber.darkerdepths.config.DDConfig;
import com.naterbobber.darkerdepths.util.DDResourceKeys;
import com.terraformersmc.biolith.api.biome.BiomePlacement;
import net.minecraft.world.level.biome.Climate;

public class DDBiomeIntegration {

    public static void init() {
        BiomePlacement.addOverworld(DDResourceKeys.Biomes.SANDY_CATACOMBS, climateParamsFromConfig(DDConfig.CONFIG.SANDY_CATACOMBS_CLIMATE));
        BiomePlacement.addOverworld(DDResourceKeys.Biomes.GLOWSHROOM_FOREST, climateParamsFromConfig(DDConfig.CONFIG.GLOWSHROOM_FOREST_CLIMATE));
        BiomePlacement.addOverworld(DDResourceKeys.Biomes.MOLTEN_CAVERN, climateParamsFromConfig(DDConfig.CONFIG.MOLTEN_CAVERN_CLIMATE));
    }

    private static Climate.ParameterPoint climateParamsFromConfig(DDBiomeConfigBuilder config) {
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
