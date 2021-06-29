package com.naterbobber.darkerdepths.core;

import com.blackgear.cavebiomes.core.api.CaveBiomeAPI;
import com.naterbobber.darkerdepths.core.registries.DDBiomes;
import net.minecraft.world.biome.Biome;

//<>

public class CaveBiomeImplementation {
    public static void addCaveBiomes() {
        //TODO: update the biome distribution
        CaveBiomeAPI.addCaveBiome(DDBiomes.SANDY_CATACOMBS.get(), new Biome.Attributes(0.475F, -0.345F, 0.145F, 0.0F, 0.0F));
        CaveBiomeAPI.addCaveBiome(DDBiomes.MOLTEN_CAVERN.get(), new Biome.Attributes(0.0F, -0.325F, -0.275F, 0.2F, 0.0F));
        CaveBiomeAPI.addCaveBiome(DDBiomes.GLOWSHROOM_CAVES.get(), new Biome.Attributes(-0.2F, 0.2F, 0.325F, -0.15F, 0.0F));

//        CaveBiomeAPI.addCaveBiome(DDBiomes.SANDY_CATACOMBS.get(), new Biome.Attributes(0.0F, -0.5F, 0.0F, 0.0F, 0.0F));
//        CaveBiomeAPI.addCaveBiome(DDBiomes.MOLTEN_CAVERN.get(), new Biome.Attributes(-0.5F, 0.0F, 0.0F, 0.0F, 0.175F));
//        CaveBiomeAPI.addCaveBiome(DDBiomes.GLOWSHROOM_CAVES.get(), new Biome.Attributes(0.0F, 0.5F, 0.0F, 0.0F, 0.375F));
//        CaveBiomeAPI.addCaveBiome(DDBiomes.CRYSTAL_CAVE.get());
    }
}