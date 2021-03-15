package com.naterbobber.darkerdepths.common.world.gen.biome;

import com.naterbobber.darkerdepths.common.world.gen.VanillaBiomeFeatures;

import com.naterbobber.darkerdepths.common.world.gen.feature.SimpleBlockConfig;
import com.naterbobber.darkerdepths.common.world.gen.placement.CaveDecoratorConfig;
import com.naterbobber.darkerdepths.core.registries.DDBlocks;
import com.naterbobber.darkerdepths.core.registries.DDFeatures;
import com.naterbobber.darkerdepths.core.registries.DDPlacements;
import com.naterbobber.darkerdepths.core.util.CaveSurface;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeAmbience;
import net.minecraft.world.biome.DefaultBiomeFeatures;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.placement.ChanceRangeConfig;
import net.minecraft.world.gen.placement.CountRangeConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraftforge.common.Tags;

//<>

public class SandyCatacombsBiome extends AbstractCaveBiome {
	public SandyCatacombsBiome() {
		super((new Builder()).surfaceBuilder(SurfaceBuilder.DEFAULT, SurfaceBuilder.STONE_STONE_GRAVEL_CONFIG).precipitation(RainType.NONE).category(Category.NONE).depth(0.125f).scale(0.05f).temperature(0.8f).downfall(0.4f).func_235097_a_((new BiomeAmbience.Builder()).setWaterColor(4159204).setWaterFogColor(329011).setFogColor(12638463).build()).parent((String)null));
	}
	
	@Override
	public void addFeatures() {
		DefaultBiomeFeatures.func_235196_b_(this);
		this.func_235063_a_(DefaultBiomeFeatures.RUINED_PORTAL_DESERT);
		DefaultBiomeFeatures.addCarvers(this);
		DefaultBiomeFeatures.addOres(this);
		DefaultBiomeFeatures.addMushrooms(this);
		this.addFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, DDFeatures.BLOB_REPLACEMENT_FEATURE.get().withConfiguration(VanillaBiomeFeatures.LIMESTONE_CONFIG).withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(100, 0, 0, 15))));
		this.addFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, DDFeatures.BLOB_REPLACEMENT_FEATURE.get().withConfiguration(VanillaBiomeFeatures.WEATHERED_SANDSTONE_CONFIG).withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(100, 0, 0, 60))));
		this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, VanillaBiomeFeatures.LIMESTONE, 33)).withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(20, 0, 0, 60))));
		this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, Blocks.SAND.getDefaultState(), 33)).withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(15, 15, 0, 60))));
		this.addFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, Feature.RANDOM_PATCH.withConfiguration(VanillaBiomeFeatures.DEAD_BUSH_CONFIG).withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(8, 0, 0, 60))));
		this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, DDFeatures.SIMPLE_BLOCK_FEATURE.get().withConfiguration(new SimpleBlockConfig(new SimpleBlockStateProvider(DDBlocks.ROOTS.get().getDefaultState()))).withPlacement(DDPlacements.CAVE_SURFACE.get().configure(new CaveDecoratorConfig(CaveSurface.CEILING, 12))).withPlacement(Placement.COUNT_RANGE.configure(new CountRangeConfig(80, 0, 0, 70))));
	}
}