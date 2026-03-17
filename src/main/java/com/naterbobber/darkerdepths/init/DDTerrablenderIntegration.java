package com.naterbobber.darkerdepths.init;

import com.naterbobber.darkerdepths.DarkerDepths;
import com.naterbobber.darkerdepths.worldgen.terrablender.CaveBiomeRegion;
import terrablender.api.Regions;

public class DDTerrablenderIntegration {

    public static void init() {
        Regions.register(new CaveBiomeRegion(DarkerDepths.id("overworld"), 4));
    }
}
