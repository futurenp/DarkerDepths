package com.naterbobber.darkerdepths.init;

import com.naterbobber.darkerdepths.config.builders.DDBiomeConfigBuilder;
import com.naterbobber.darkerdepths.config.DDConfig;
import com.naterbobber.darkerdepths.util.DDResourceKeys;
import com.naterbobber.darkerdepths.worldgen.ClimateParamsUtil;
import com.terraformersmc.biolith.api.biome.BiomePlacement;
import net.minecraft.world.level.biome.Climate;

public class DDBiolithIntegration {

    public static void init() {
        BiomePlacement.addOverworld(DDResourceKeys.Biomes.SANDY_CATACOMBS,
                ClimateParamsUtil.climateParamsFromConfig(DDConfig.CONFIG.SANDY_CATACOMBS_CLIMATE));

        BiomePlacement.addOverworld(DDResourceKeys.Biomes.GLOWSHROOM_FOREST,
                ClimateParamsUtil.climateParamsFromConfig(DDConfig.CONFIG.GLOWSHROOM_FOREST_CLIMATE));

        BiomePlacement.addOverworld(DDResourceKeys.Biomes.MOLTEN_CAVERN,
                ClimateParamsUtil.climateParamsFromConfig(DDConfig.CONFIG.MOLTEN_CAVERN_CLIMATE));
    }
}
