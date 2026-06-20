package com.naterbobber.darkerdepths.init;

import com.mojang.datafixers.util.Pair;
import com.naterbobber.darkerdepths.DarkerDepths;
import com.naterbobber.darkerdepths.config.DDConfig;
import com.naterbobber.darkerdepths.util.DDResourceKeys;
import com.naterbobber.darkerdepths.worldgen.BiomeParameters;
import com.naterbobber.darkerdepths.worldgen.surfacerules.DDSurfaceRules;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Climate;
import terrablender.api.*;

import java.util.function.Consumer;

public class DDTerrablenderIntegration {

    public static void init() {
        Regions.register(new DDCaveBiomeRegion());

        SurfaceRuleManager.addSurfaceRules(
                SurfaceRuleManager.RuleCategory.OVERWORLD,
                DarkerDepths.MOD_ID,
                DDSurfaceRules.makeRules()
        );
    }

    private static class DDCaveBiomeRegion extends Region {
        private static final Climate.Parameter WEIRDNESS = Climate.Parameter.span(-1.0F, 1.0F);

        public DDCaveBiomeRegion() {
            super(DarkerDepths.id("overworld"), RegionType.OVERWORLD, DDConfig.CONFIG.OVERWORLD_BIOME_WEIGHT_TERRABLENDER.get());
        }

        @Override
        public void addBiomes(Registry<Biome> registry, Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> mapper)
        {
            addModifiedVanillaOverworldBiomes(mapper, b -> {});

            if(!DDConfig.CONFIG.USE_DEFAULTS_TERRABLENDER.get()) {
                addBiome(BiomeParameters.fromConfigWithMapper(mapper,
                        DDConfig.CONFIG.GLOWSHROOM_FOREST,
                        DDResourceKeys.Biomes.GLOWSHROOM_FOREST));

                addBiome(BiomeParameters.fromConfigWithMapper(mapper,
                        DDConfig.CONFIG.SANDY_CATACOMBS,
                        DDResourceKeys.Biomes.SANDY_CATACOMBS));

                addBiome(BiomeParameters.fromConfigWithMapper(mapper,
                        DDConfig.CONFIG.MOLTEN_CAVERN,
                        DDResourceKeys.Biomes.MOLTEN_CAVERN));
            }
            else {
                var glowshroomForestParameters = new BiomeParameters(
                        mapper,
                        ParameterUtils.Temperature.span(ParameterUtils.Temperature.NEUTRAL, ParameterUtils.Temperature.HOT),
                        ParameterUtils.Humidity.span(ParameterUtils.Humidity.NEUTRAL, ParameterUtils.Humidity.HUMID),
                        ParameterUtils.Continentalness.span(ParameterUtils.Continentalness.OCEAN, ParameterUtils.Continentalness.NEAR_INLAND),
                        ParameterUtils.Erosion.FULL_RANGE.parameter(),
                        WEIRDNESS,
                        ParameterUtils.Depth.UNDERGROUND.parameter(),
                        0.0f,
                        DDResourceKeys.Biomes.GLOWSHROOM_FOREST,
                        DDConfig.CONFIG.GLOWSHROOM_FOREST.enabled().get()
                );

                var sandyCatacombsParameters = new BiomeParameters(
                        mapper,
                        ParameterUtils.Temperature.span(ParameterUtils.Temperature.WARM, ParameterUtils.Temperature.HOT),
                        ParameterUtils.Humidity.span(ParameterUtils.Humidity.DRY, ParameterUtils.Humidity.ARID),
                        ParameterUtils.Continentalness.span(ParameterUtils.Continentalness.NEAR_INLAND, ParameterUtils.Continentalness.MID_INLAND),
                        ParameterUtils.Erosion.FULL_RANGE.parameter(),
                        WEIRDNESS,
                        ParameterUtils.Depth.UNDERGROUND.parameter(),
                        0.0f,
                        DDResourceKeys.Biomes.SANDY_CATACOMBS,
                        DDConfig.CONFIG.SANDY_CATACOMBS.enabled().get()
                );

                var moltenCavernParameters = new BiomeParameters(
                        mapper,
                        ParameterUtils.Temperature.FULL_RANGE.parameter(),
                        ParameterUtils.Humidity.span(ParameterUtils.Humidity.DRY, ParameterUtils.Humidity.WET),
                        ParameterUtils.Continentalness.span(ParameterUtils.Continentalness.MID_INLAND, ParameterUtils.Continentalness.FAR_INLAND),
                        ParameterUtils.Erosion.span(ParameterUtils.Erosion.EROSION_0, ParameterUtils.Erosion.EROSION_2),
                        WEIRDNESS,
                        ParameterUtils.Depth.span(ParameterUtils.Depth.UNDERGROUND, ParameterUtils.Depth.FLOOR),
                        0.0f,
                        DDResourceKeys.Biomes.MOLTEN_CAVERN,
                        DDConfig.CONFIG.MOLTEN_CAVERN.enabled().get()
                );

                addBiome(glowshroomForestParameters);
                addBiome(sandyCatacombsParameters);
                addBiome(moltenCavernParameters);
            }
        }

        private void addBiome(BiomeParameters parameters) {
            if(parameters.mapper() == null || !parameters.enabled()) return;

            this.addBiome(parameters.mapper(),
                    parameters.temperature(),
                    parameters.humidity(),
                    parameters.continentalness(),
                    parameters.erosion(),
                    parameters.weirdness(),
                    parameters.depth(),
                    parameters.offset(),
                    parameters.biomeResourceKey()
            );
        }
    }
}
