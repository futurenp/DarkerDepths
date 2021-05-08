package com.naterbobber.darkerdepths.common.world.gen;

import com.blackgear.bgcore.core.api.FeaturePlacement;
import com.blackgear.bgcore.core.registries.BGBiomes;
import com.naterbobber.darkerdepths.core.registries.DDBiomes;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeGenerationSettings;
import net.minecraft.world.gen.GenerationStage;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

//<>

@Mod.EventBusSubscriber
public class GlobalBiomeFeatures {
	/**
	 * @note this method add features to ALL the biomes, or to specific vanilla biomes
	 */
	@SubscribeEvent
	public void onBiomeLoad(final BiomeLoadingEvent event) {
		if (event.getCategory() == Biome.Category.NETHER || event.getCategory() == Biome.Category.THEEND) return;
		if (event.getName() != null) {
			Biome biome = ForgeRegistries.BIOMES.getValue(event.getName());
			BiomeGenerationSettings.Builder builder = event.getGeneration();
			if (biome == BGBiomes.DEFAULT_CAVE.get()) {
				VanillaBiomeFeatures.addCarvers(builder);
				VanillaBiomeFeatures.addGlowshrooms(builder);
			}
			if (biome == DDBiomes.MOLTEN_CAVERN.get()) {
				VanillaBiomeFeatures.addAsh(builder);
				VanillaBiomeFeatures.addAmber(builder);
				VanillaBiomeFeatures.addMoltenCavernDecorations(builder);
				VanillaBiomeFeatures.addCarvers(builder);
			}
			if (biome == DDBiomes.SANDY_CATACOMBS.get()) {
				VanillaBiomeFeatures.addSandyCatacombsTerrain(builder);
				VanillaBiomeFeatures.addAridrockOres(builder);
				VanillaBiomeFeatures.addLimestoneOres(builder);
				VanillaBiomeFeatures.addSandyCatacombsVegetation(builder);
				VanillaBiomeFeatures.addCarvers(builder);
			}
			if (biome == DDBiomes.CRYSTAL_CAVE.get()) {
				VanillaBiomeFeatures.addCarvers(builder);
				VanillaBiomeFeatures.addCrystalPeaks(builder);
			}
		}
	}
}