package com.naterbobber.darkerdepths.common.world.gen.biome;

import com.naterbobber.darkerdepths.common.world.gen.VanillaBiomeFeatures;
import com.naterbobber.darkerdepths.core.registries.DDCarvers;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeAmbience;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.ProbabilityConfig;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;

//<>

public class CaveBiome extends AbstractCaveBiome {
    public CaveBiome() {
        super((new Builder()).surfaceBuilder(SurfaceBuilder.DEFAULT, SurfaceBuilder.STONE_STONE_GRAVEL_CONFIG).precipitation(Biome.RainType.NONE).category(Category.NONE).depth(0.125f).scale(0.05f).temperature(0.8f).downfall(0.4f).func_235097_a_((new BiomeAmbience.Builder()).setWaterColor(4159204).setWaterFogColor(329011).setFogColor(12638463).build()).parent((String)null));
    }

    @Override
    public void addFeatures() {
        super.addFeatures();
        VanillaBiomeFeatures.addSpeleothems(this);
    }
}