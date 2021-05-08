package com.naterbobber.darkerdepths.common.world.gen.biome;

import com.naterbobber.darkerdepths.common.world.gen.VanillaBiomeFeatures;
import net.minecraft.world.biome.*;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;

//<>

public class SandyCatacombsBiome extends AbstractBiome {
//	@Override
//	public void addFeatures() {
//		DefaultBiomeFeatures.func_235196_b_(this);
//		DefaultBiomeFeatures.addCarvers(this);
//		DefaultBiomeFeatures.addOres(this);
//		DefaultBiomeFeatures.addMushrooms(this);
//		this.addFeature(GenerationStage.Decoration.UNDERGROUND_STRUCTURES, DDFeatures.CAVE_FOSSILS.get().withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).withPlacement(Placement.CHANCE_RANGE.configure(new ChanceRangeConfig(5, 0, 0, 40))));
//		this.addFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, DDFeatures.BLOB_REPLACEMENT_FEATURE.get().withConfiguration(GlobalBiomeFeatures.LIMESTONE_CONFIG).withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(250, 0, 0, 15))));
//		this.addFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, DDFeatures.BLOB_REPLACEMENT_FEATURE.get().withConfiguration(GlobalBiomeFeatures.ARIDROCK_CONFIG).withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(250, 0, 0, 60))));
//		this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, GlobalBiomeFeatures.LIMESTONE, 33)).withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(20, 0, 0, 60))));
//		this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, Blocks.SAND.getDefaultState(), 33)).withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(15, 15, 0, 60))));
//		this.addFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, Feature.RANDOM_PATCH.withConfiguration(GlobalBiomeFeatures.DEAD_BUSH_CONFIG).withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(8, 0, 0, 60))));
//		this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, DDFeatures.SIMPLE_BLOCK_FEATURE.get().withConfiguration(new SimpleBlockConfig(new SimpleBlockStateProvider(DDBlocks.ROOTS.get().getDefaultState()))).withPlacement(DDPlacements.CAVE_SURFACE.get().configure(new CaveDecoratorConfig(CaveSurface.CEILING, 12))).withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(80, 0, 0, 70))));
//		this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, DDFeatures.SIMPLE_BLOCK_FEATURE.get().withConfiguration(new SimpleBlockConfig(new SimpleBlockStateProvider(DDBlocks.LONG_ROOTS.get().getDefaultState()))).withPlacement(DDPlacements.CAVE_SURFACE.get().configure(new CaveDecoratorConfig(CaveSurface.CEILING, 12))).withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(80, 0, 0, 70))));
//		GlobalBiomeFeatures.addAridrockOres(this);
//		GlobalBiomeFeatures.addLimestoneOres(this);
//	}

	@Override
	protected Biome.Category category() {
		return Biome.Category.NONE;
	}

	@Override
	protected float depth() {
		return 0.125f;
	}

	@Override
	protected float scale() {
		return 0.05f;
	}

	@Override
	protected void ambience(BiomeAmbience.Builder ambience) {
		ambience.setWaterColor(4159204).setWaterFogColor(329011).setFogColor(12638463).withSkyColor(0).build();
	}

	@Override
	protected Biome.Climate climate() {
		return new Biome.Climate(Biome.RainType.NONE, 0.8f, Biome.TemperatureModifier.NONE, 0.4f);
	}

	@Override
	protected ConfiguredSurfaceBuilder<?> surfaceBuilder() {
		return new ConfiguredSurfaceBuilder<>(SurfaceBuilder.DEFAULT, SurfaceBuilder.STONE_STONE_GRAVEL_CONFIG);
	}

	@Override
	protected void generation(BiomeGenerationSettings.Builder builder) {
	}

	@Override
	protected void spawns(MobSpawnInfo.Builder spawns) {
	}
}