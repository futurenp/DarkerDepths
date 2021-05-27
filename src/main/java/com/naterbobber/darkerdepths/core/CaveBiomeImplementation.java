package com.naterbobber.darkerdepths.core;

import com.blackgear.cavebiomes.core.api.CaveBiomeAPI;
import com.naterbobber.darkerdepths.core.registries.DDBiomes;

//<>

public class CaveBiomeImplementation {
    public static void addCaveBiomes() {
        CaveBiomeAPI.addCaveBiome(DDBiomes.SANDY_CATACOMBS.get());
        CaveBiomeAPI.addCaveBiome(DDBiomes.MOLTEN_CAVERN.get());
//        CaveBiomeAPI.addCaveBiome(DDBiomes.CRYSTAL_CAVE.get());
    }
}