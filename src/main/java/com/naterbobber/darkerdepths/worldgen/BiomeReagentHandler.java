package com.naterbobber.darkerdepths.worldgen;

import com.mojang.datafixers.util.Pair;
import com.naterbobber.darkerdepths.DarkerDepths;
import com.naterbobber.darkerdepths.config.DDConfig;
import com.naterbobber.darkerdepths.config.builders.DDBiomeConfigBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Climate;

import java.util.function.Consumer;

public class BiomeReagentHandler {
    public static final ResourceKey<Biome> MOLTEN_CAVERN = register("molten_cavern");
    public static final ResourceKey<Biome> SANDY_CATACOMBS = register("sandy_catacombs");
    public static final ResourceKey<Biome> GLOWSHROOM_FOREST = register("glowshroom_forest");

    public static void init(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> consumer) {
        consumer.accept(Pair.of(climateParamsFromConfig(DDConfig.SANDY_CATACOMBS_CLIMATE), SANDY_CATACOMBS));
        consumer.accept(Pair.of(climateParamsFromConfig(DDConfig.GLOWSHROOM_FOREST_CLIMATE), GLOWSHROOM_FOREST));
        consumer.accept(Pair.of(climateParamsFromConfig(DDConfig.MOLTEN_CAVERN_CLIMATE), MOLTEN_CAVERN));
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

    private static ResourceKey<Biome> register(String name) {
        return ResourceKey.create(Registries.BIOME, DarkerDepths.id(name));
    }
}
