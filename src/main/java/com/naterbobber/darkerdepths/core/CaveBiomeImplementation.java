package com.naterbobber.darkerdepths.core;

import com.blackgear.bgcore.common.world.biome.CaveBiomeManager;
import com.naterbobber.darkerdepths.core.registries.DDBiomes;
import net.minecraft.world.biome.Biome;

//<>

public class CaveBiomeImplementation {
    public static void addCaveBiomes() {
        CaveBiomeManager.addCaveBiome(DDBiomes.SANDY_CATACOMBS.get());
        CaveBiomeManager.addCaveBiome(DDBiomes.MOLTEN_CAVERN.get());
        CaveBiomeManager.addCaveBiome(Biome.Category.DESERT, DDBiomes.SANDY_CATACOMBS.get());
//        CaveBiomeManager.addCaveBiome(DDBiomes.CRYSTAL_CAVE.get());
    }
}