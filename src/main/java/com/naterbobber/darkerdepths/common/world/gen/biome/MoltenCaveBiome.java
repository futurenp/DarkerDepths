package com.naterbobber.darkerdepths.common.world.gen.biome;

import com.naterbobber.darkerdepths.common.world.gen.VanillaBiomeFeatures;
import com.naterbobber.darkerdepths.core.registries.DDCarvers;

import com.naterbobber.darkerdepths.core.registries.DDFeatures;
import net.minecraft.block.Blocks;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.world.biome.*;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.feature.ProbabilityConfig;
import net.minecraft.world.gen.placement.CountRangeConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;

//<>

public class MoltenCaveBiome extends AbstractCaveBiome {
    public MoltenCaveBiome() {
        super((new Builder()).surfaceBuilder(SurfaceBuilder.DEFAULT, SurfaceBuilder.STONE_STONE_GRAVEL_CONFIG).precipitation(RainType.NONE).category(Category.NONE).depth(0.125f).scale(0.05f).temperature(0.8f).downfall(0.4f).func_235097_a_((new BiomeAmbience.Builder()).setWaterColor(4159204).setWaterFogColor(329011).setFogColor(12638463).setParticle(new ParticleEffectAmbience(ParticleTypes.WHITE_ASH, 0.118093334f)).build()).parent(null));
    }

    @Override
    public void addFeatures() {
    	this.addCarver(GenerationStage.Carving.AIR, createCarver(DDCarvers.LARGE_CAVE.get(), new ProbabilityConfig(0.2f)));
    	VanillaBiomeFeatures.addAsh(this);
    	VanillaBiomeFeatures.addOres(this);
    	VanillaBiomeFeatures.addAmber(this);
    	VanillaBiomeFeatures.addSpeleothems(this);
    	this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.SPRING_FEATURE.withConfiguration(VanillaBiomeFeatures.MOLTEN_CAVERN_LAVA_SPRING_CONFIG).withPlacement(Placement.COUNT_VERY_BIASED_RANGE.configure(new CountRangeConfig(20, 8, 16, 55))));
        this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, Blocks.MAGMA_BLOCK.getDefaultState(), 15)).withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(25, 10, 7, 13))));
        this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, Blocks.MAGMA_BLOCK.getDefaultState(), 15)).withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(12, 5, 5, 55))));
        this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, VanillaBiomeFeatures.SHALE, 60)).withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(7, 5, 5, 55))));
        this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, Blocks.COAL_ORE.getDefaultState(), 17)).withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(20, 0, 0, 55))));
        this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, Blocks.DIAMOND_ORE.getDefaultState(), 1)).withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(1, 0, 0, 16))));
//        this.addFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, Feature.field_236287_R_.withConfiguration(VanillaBiomeFeatures.SHALE_BLOB_CONFIG).withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(75, 0, 0, 55))));
//        this.addFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, Feature.field_236287_R_.withConfiguration(VanillaBiomeFeatures.ASH_BLOB_CONFIG).withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(100, 0, 0, 55))));
        this.addFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, DDFeatures.BLOB_REPLACEMENT_FEATURE.get().withConfiguration(VanillaBiomeFeatures.SHALE_CONFIG).withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(100, 0, 0, 55))));
    }
}