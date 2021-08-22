package com.naterbobber.darkerdepths.core;

import com.blackgear.cavebiomes.core.api.CaveBiomeAPI;
import com.naterbobber.darkerdepths.core.registries.worldgen.DDBiomes;
import net.minecraft.world.biome.Biome;

//<>

public class CaveBiomeImplementation {
    public static void addCaveBiomes() {
        CaveBiomeAPI.addCaveBiome(DDBiomes.SANDY_CATACOMBS.get(), new Biome.Attributes(0.6F, -0.7F, 0.0F, 0F, 0.0F));
        CaveBiomeAPI.addCaveBiome(DDBiomes.MOLTEN_CAVERN.get(), new Biome.Attributes(0.7F, -0.7F, -0.1F, 0.0F, 0F));
        CaveBiomeAPI.addCaveBiome(DDBiomes.GLOWSHROOM_CAVES.get(), new Biome.Attributes(-0.25F, 0.75F, 0.1F, -0.05F, 0.2F));
//        CaveBiomeAPI.addCaveBiome(DDBiomes.CRYSTAL_CAVE.get());
    }
}