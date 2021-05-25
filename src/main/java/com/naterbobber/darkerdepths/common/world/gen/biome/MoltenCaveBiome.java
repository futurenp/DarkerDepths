package com.naterbobber.darkerdepths.common.world.gen.biome;

import com.naterbobber.darkerdepths.common.world.gen.VanillaBiomeFeatures;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.world.biome.*;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilders;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;

//<>

public class MoltenCaveBiome extends AbstractBiome {
//    @Override
//    public void addFeatures() {
//        GlobalBiomeFeatures.addCarvers(this);
//    	GlobalBiomeFeatures.addAsh(this);
//    	GlobalBiomeFeatures.addAmber(this);
//        this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.SPRING_FEATURE.withConfiguration(GlobalBiomeFeatures.MOLTEN_CAVERN_LAVA_SPRING_CONFIG).withPlacement(Placement.COUNT_VERY_BIASED_RANGE.configure(new CountRangeConfig(20, 8, 16, 55))));
//        this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, Blocks.MAGMA_BLOCK.getDefaultState(), 15)).withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(25, 10, 7, 13))));
//        this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, Blocks.MAGMA_BLOCK.getDefaultState(), 15)).withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(12, 5, 5, 55))));
//        this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, GlobalBiomeFeatures.SHALE, 60)).withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(7, 5, 5, 55))));
//        this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, Blocks.COAL_ORE.getDefaultState(), 17)).withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(20, 0, 0, 55))));
//        this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, Blocks.DIAMOND_ORE.getDefaultState(), 1)).withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(1, 0, 0, 16))));
//        this.addFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, DDFeatures.BLOB_REPLACEMENT_FEATURE.get().withConfiguration(GlobalBiomeFeatures.SHALE_CONFIG).withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(100, 0, 0, 55))));
//        this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, DDFeatures.GEMSTONE_PLACEMENT_FEATURE.get().withConfiguration(new GemstonePlacementConfig(DDBlocks.AMBER.get().getDefaultState())).withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(64, 0, 0, 25))));
//    }

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
        ambience.setWaterColor(4159204).setWaterFogColor(329011).setFogColor(12638463).withSkyColor(0).setParticle(new ParticleEffectAmbience(ParticleTypes.WHITE_ASH, 0.118093334f)).build();
    }

    @Override
    protected Biome.Climate climate() {
        return new Biome.Climate(Biome.RainType.NONE, 0.8f, Biome.TemperatureModifier.NONE, 0.4f);
    }

    @Override
    protected ConfiguredSurfaceBuilder<?> surfaceBuilder() {
        return ConfiguredSurfaceBuilders.GRASS;
    }

    @Override
    protected void generation(BiomeGenerationSettings.Builder builder) {
        DefaultBiomeFeatures.withCavesAndCanyons(builder);
    }

    @Override
    protected void spawns(MobSpawnInfo.Builder spawns) {

    }
}