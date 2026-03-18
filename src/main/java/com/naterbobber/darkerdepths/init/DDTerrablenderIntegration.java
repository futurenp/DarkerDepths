package com.naterbobber.darkerdepths.init;

import com.naterbobber.darkerdepths.DarkerDepths;
import com.naterbobber.darkerdepths.config.DDConfig;
import com.naterbobber.darkerdepths.worldgen.terrablender.CaveBiomeRegion;
import terrablender.api.Regions;

public class DDTerrablenderIntegration {

    public static void init() {
        Regions.register(new CaveBiomeRegion(DarkerDepths.id("overworld"),
                DDConfig.CONFIG.OVERWORLD_BIOME_WEIGHT_TERRABLENDER.get()));
    }
}
