package com.naterbobber.darkerdepths.init;

import com.naterbobber.darkerdepths.DarkerDepths;
import com.naterbobber.darkerdepths.config.DDConfig;
import com.naterbobber.darkerdepths.util.DDResourceKeys;
import com.naterbobber.darkerdepths.worldgen.BiomeParameters;
import com.naterbobber.darkerdepths.worldgen.surfacerules.DDSurfaceRules;
import com.terraformersmc.biolith.api.biome.BiomePlacement;
import com.terraformersmc.biolith.api.surface.SurfaceGeneration;

public class DDBiolithIntegration {

    public static void init() {
        addOverworld(BiomeParameters.fromConfig(
                DDConfig.CONFIG.SANDY_CATACOMBS,
                DDResourceKeys.Biomes.SANDY_CATACOMBS));

        addOverworld(BiomeParameters.fromConfig(
                DDConfig.CONFIG.GLOWSHROOM_FOREST,
                DDResourceKeys.Biomes.GLOWSHROOM_FOREST));

        addOverworld(BiomeParameters.fromConfig(
                DDConfig.CONFIG.MOLTEN_CAVERN,
                DDResourceKeys.Biomes.MOLTEN_CAVERN));

        SurfaceGeneration.addOverworldSurfaceRules(
                DarkerDepths.id("rules/overworld"),
                DDSurfaceRules.makeRules()
        );
    }

    public static void addOverworld(BiomeParameters parameters) {
        if(parameters.enabled()) {
            BiomePlacement.addOverworld(
                    parameters.biomeResourceKey(),
                    parameters.toParameterPoints());
        }
    }
}
