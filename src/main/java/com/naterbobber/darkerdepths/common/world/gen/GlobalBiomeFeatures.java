package com.naterbobber.darkerdepths.common.world.gen;

import com.blackgear.cavebiomes.core.registries.CaveBiomes;
import com.naterbobber.darkerdepths.core.registries.DDBiomes;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
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
	@SubscribeEvent
	public void onBiomeLoad(BiomeLoadingEvent event) {
		if (event.getCategory() == Biome.Category.NETHER || event.getCategory() == Biome.Category.THEEND) return;
		if (event.getName() == null) return;

		ResourceLocation biomeID = event.getName();
		Biome biome = ForgeRegistries.BIOMES.getValue(event.getName());
		RegistryKey<Biome> vanillaBiome = RegistryKey.getOrCreateKey(Registry.BIOME_KEY, biomeID);

		BiomeGenerationSettings.Builder generation = event.getGeneration();

		if (biome == CaveBiomes.CAVE.get()) {
			this.generateDefaultCavesFeatures(generation);
		}
		if (biome == DDBiomes.MOLTEN_CAVERN.get()) {
			this.generateMoltenCavesFeatures(generation);
		}
		if (biome == DDBiomes.SANDY_CATACOMBS.get()) {
			this.generateSandyCavesFeatures(generation);
		}
		if (biome == DDBiomes.CRYSTAL_CAVE.get()) {
			this.generateCrystalCavesFeatures(generation);
		}
		if (biome == DDBiomes.GLOWSHROOM_CAVERN.get()) {
			this.generateGlowshroomCavernFeature(generation);
		}
		FeaturePlacement.addCarver(generation, GenerationStage.Carving.AIR, DDConfiguredCarvers.NOISE_CAVE);
	}

	private void generateDefaultCavesFeatures(BiomeGenerationSettings.Builder builder) {
		VanillaBiomeFeatures.addGlowshrooms(builder);
	}

	private void generateMoltenCavesFeatures(BiomeGenerationSettings.Builder builder) {
		VanillaBiomeFeatures.addAsh(builder);
		VanillaBiomeFeatures.addAmber(builder);
		VanillaBiomeFeatures.addMoltenCavernDecorations(builder);
	}

	private void generateSandyCavesFeatures(BiomeGenerationSettings.Builder builder) {
		VanillaBiomeFeatures.addSandyCatacombsTerrain(builder);
		VanillaBiomeFeatures.addAridrockOres(builder);
		VanillaBiomeFeatures.addLimestoneOres(builder);
		VanillaBiomeFeatures.addSandyCatacombsVegetation(builder);
		VanillaBiomeFeatures.addGlowshrooms(builder);
		VanillaBiomeFeatures.addOasis(builder);
	}

	private void generateCrystalCavesFeatures(BiomeGenerationSettings.Builder builder) {
		VanillaBiomeFeatures.addGlowshrooms(builder);
		VanillaBiomeFeatures.addCrystalPeaks(builder);
	}

	private void generateGlowshroomCavernFeature(BiomeGenerationSettings.Builder builder) {
		VanillaBiomeFeatures.addGlowshrooms(builder);
		VanillaBiomeFeatures.addGlowshroomVegetation(builder);
	}
}