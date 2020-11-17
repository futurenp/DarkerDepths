package com.naterbobber.darkerdepths.common.world.gen.biome;

import net.minecraft.world.biome.Biome;

//<>

public abstract class AbstractCaveBiome extends Biome  {
    public AbstractCaveBiome(Builder biomeBuilder) {
        super(biomeBuilder);
    }

    public void addFeatures() {}
}