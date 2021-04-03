package com.naterbobber.darkerdepths.common.world.gen.biome;

import com.naterbobber.darkerdepths.common.world.gen.VanillaBiomeFeatures;
import com.naterbobber.darkerdepths.core.init.EntityTypesInit;
import com.naterbobber.darkerdepths.core.registries.DDFeatures;
import net.minecraft.entity.EntityClassification;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeAmbience;
import net.minecraft.world.biome.ParticleEffectAmbience;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.placement.ChanceRangeConfig;
import net.minecraft.world.gen.placement.CountRangeConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;

public class GlowshroomCaveBiome extends AbstractCaveBiome {

    public GlowshroomCaveBiome() {
        super((new Builder()).surfaceBuilder(SurfaceBuilder.DEFAULT, SurfaceBuilder.STONE_STONE_GRAVEL_CONFIG).precipitation(RainType.NONE).category(Category.NONE).depth(0.125f).scale(0.05f).temperature(0.8f).downfall(0.4f).func_235097_a_((new BiomeAmbience.Builder()).setWaterColor(4159204).setWaterFogColor(329011).setFogColor(12638463).build()).parent(null));
    }

    @Override
    public void addFeatures() {
        VanillaBiomeFeatures.addCarvers(this);
        VanillaBiomeFeatures.addGlowshrooms(this);
        this.addFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, DDFeatures.SHELF_FEATURE.get().withConfiguration(new NoFeatureConfig()).withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(128, 0, 0, 25))));
    }
}
