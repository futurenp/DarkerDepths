package com.naterbobber.darkerdepths.common.world.gen.biome;

import com.naterbobber.darkerdepths.common.world.gen.VanillaBiomeFeatures;

import net.minecraft.world.biome.BiomeAmbience;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.placement.CountRangeConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;

//<>

public class SandyCatacombsBiome extends AbstractCaveBiome {
	public SandyCatacombsBiome() {
		super((new Builder()).surfaceBuilder(SurfaceBuilder.DEFAULT, SurfaceBuilder.STONE_STONE_GRAVEL_CONFIG).precipitation(RainType.NONE).category(Category.NONE).depth(0.125f).scale(0.05f).temperature(0.8f).downfall(0.4f).func_235097_a_((new BiomeAmbience.Builder()).setWaterColor(4159204).setWaterFogColor(329011).setFogColor(12638463).build()).parent((String)null));
	}
	
	@Override
	public void addFeatures() {
		this.addFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, Feature.field_236287_R_.withConfiguration(VanillaBiomeFeatures.SANDSTONE_BLOB_CONFIG).withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(100, 0, 0, 55))));
	}
}