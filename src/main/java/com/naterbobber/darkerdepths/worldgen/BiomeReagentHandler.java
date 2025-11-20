package com.naterbobber.darkerdepths.worldgen;

import com.mojang.datafixers.util.Pair;
import com.naterbobber.darkerdepths.config.DDBiomeConfig;
import com.naterbobber.darkerdepths.util.DDResourceKeys;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Climate;

import java.util.function.Consumer;

//this is a complete mess
public class BiomeReagentHandler {
    private static final DDBiomeConfig.Defaults SANDY_CATACOMBS_DEFAULTS = new DDBiomeConfig.Defaults(
            0.5, 1.2, -1.0, -0.4, 0.1, 0.3,
            -0.25, 1.0, -1.0, 1.0, 0.3, 2.0, 0.0
    );

    private static final DDBiomeConfig.Defaults GLOWSHROOM_FOREST_DEFAULTS = new DDBiomeConfig.Defaults(
            0.4, 1.0, 0.6, 1.2, -0.5, 0.5,
            0.1, 0.5, -1.0, 1.0, 0.3, 0.9, 0.0
    );

    private static final DDBiomeConfig.Defaults MOLTEN_CAVERN_DEFAULTS = new DDBiomeConfig.Defaults(
            -0.75, 0.75, -1.0, 1.0, 0.65, 0.8,
            -0.325, 0.0, -1.0, 1.0, 0.8, 2.0, 0.0
    );

    public static void init(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> consumer) {
        consumer.accept(Pair.of(climateParamsFromConfig(SANDY_CATACOMBS_DEFAULTS), DDResourceKeys.Biomes.SANDY_CATACOMBS));
        consumer.accept(Pair.of(climateParamsFromConfig(GLOWSHROOM_FOREST_DEFAULTS), DDResourceKeys.Biomes.GLOWSHROOM_FOREST));
        consumer.accept(Pair.of(climateParamsFromConfig(MOLTEN_CAVERN_DEFAULTS), DDResourceKeys.Biomes.MOLTEN_CAVERN));
    }

//    public static void init(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> consumer) {
//        consumer.accept(Pair.of(climateParamsFromConfig(DDConfig.CONFIG.SANDY_CATACOMBS_CLIMATE), SANDY_CATACOMBS));
//        consumer.accept(Pair.of(climateParamsFromConfig(DDConfig.CONFIG.GLOWSHROOM_FOREST_CLIMATE), GLOWSHROOM_FOREST));
//        consumer.accept(Pair.of(climateParamsFromConfig(DDConfig.CONFIG.MOLTEN_CAVERN_CLIMATE), MOLTEN_CAVERN));
//    }

    private static Climate.ParameterPoint climateParamsFromConfig(DDBiomeConfig config) {
        return Climate.parameters(
                Climate.Parameter.span(config.tempMin().get().floatValue(), config.tempMax().get().floatValue()),
                Climate.Parameter.span(config.humidityMin().get().floatValue(), config.humidityMax().get().floatValue()),
                Climate.Parameter.span(config.continentalnessMin().get().floatValue(), config.continentalnessMax().get().floatValue()),
                Climate.Parameter.span(config.erosionMin().get().floatValue(), config.erosionMax().get().floatValue()),
                Climate.Parameter.span(config.depthMin().get().floatValue(), config.depthMax().get().floatValue()),
                Climate.Parameter.span(config.weirdnessMin().get().floatValue(), config.weirdnessMax().get().floatValue()),
                config.offset().get().floatValue());
    }
    private static Climate.ParameterPoint climateParamsFromConfig(DDBiomeConfig.Defaults config) {
        return Climate.parameters(
                Climate.Parameter.span((float) config.tempMin(), (float) config.tempMax()),
                Climate.Parameter.span((float) config.humidityMin(), (float) config.humidityMax()),
                Climate.Parameter.span((float) config.continentalnessMin(), (float) config.continentalnessMax()),
                Climate.Parameter.span((float) config.erosionMin(), (float) config.erosionMax()),
                Climate.Parameter.span((float) config.depthMin(), (float) config.depthMax()),
                Climate.Parameter.span((float) config.weirdnessMin(), (float) config.weirdnessMax()),
                (float) config.offset());
    }
}
