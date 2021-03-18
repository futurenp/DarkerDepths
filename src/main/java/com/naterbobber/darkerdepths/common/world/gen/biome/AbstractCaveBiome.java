package com.naterbobber.darkerdepths.common.world.gen.biome;

import com.naterbobber.darkerdepths.core.registries.DDCarvers;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.ProbabilityConfig;

//<>

public abstract class AbstractCaveBiome extends Biome  {
    public AbstractCaveBiome(Builder biomeBuilder) {
        super(biomeBuilder);
    }

    public void addFeatures() {
    }
}