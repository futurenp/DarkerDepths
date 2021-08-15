package com.naterbobber.darkerdepths.core;

import com.blackgear.cavebiomes.core.api.CaveBiomeAPI;
import com.naterbobber.darkerdepths.core.registries.worldgen.DDBiomes;
import net.minecraft.world.biome.Biome;

//<>

public class CaveBiomeImplementation {
    public static void addCaveBiomes() {
//        CaveBiomeAPI.addCaveBiome(DDBiomes.SANDY_CATACOMBS.get(), new Biome.Attributes(0.0F, -0.5F, 0.0F, 0.0F, 0.0F));
//        CaveBiomeAPI.addCaveBiome(DDBiomes.MOLTEN_CAVERN.get(), new Biome.Attributes(-0.5F, 0.0F, 0.0F, 0.0F, 0.175F));
//        CaveBiomeAPI.addCaveBiome(DDBiomes.GLOWSHROOM_CAVES.get(), new Biome.Attributes(0.0F, 0.5F, 0.0F, 0.0F, 0.375F));

        //TODO: update the biome distribution
        CaveBiomeAPI.addCaveBiome(DDBiomes.SANDY_CATACOMBS.get(), new Biome.Attributes(0.4F, -0.6F, 0.0F, -0.35F, -0.2F));
        CaveBiomeAPI.addCaveBiome(DDBiomes.MOLTEN_CAVERN.get(), new Biome.Attributes(-0.5F, 0.0F, 0.0F, 0.0F, 0.175F));
        CaveBiomeAPI.addCaveBiome(DDBiomes.GLOWSHROOM_CAVES.get(), new Biome.Attributes(0.0F, 0.5F, 0.0F, 0.0F, 0.375F));

//        CaveBiomeAPI.addCaveBiome(DDBiomes.CRYSTAL_CAVE.get());
    }
}