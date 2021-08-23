package com.naterbobber.darkerdepths.common.world.gen;

import com.blackgear.cavebiomes.core.registries.CaveBiomes;
import com.naterbobber.darkerdepths.core.registries.DDEntityTypes;
import com.naterbobber.darkerdepths.core.registries.worldgen.DDBiomes;
import net.minecraft.entity.EntityClassification;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Features;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;
import net.minecraftforge.common.world.MobSpawnInfoBuilder;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

//<>

@Mod.EventBusSubscriber
public class GlobalBiomeFeatures {
	@SubscribeEvent
	public void onBiomeLoad(BiomeLoadingEvent event) {
		GlobalBiomeManager manager = new GlobalBiomeManager(event);

		if (manager.matches(CaveBiomes.CAVE.get())) {
			BiomeFeatures.generateCaveFeatures(manager);
		}
		if (manager.matches(DDBiomes.MOLTEN_CAVERN.get())) {
			BiomeFeatures.generateMoltenCavernFeatures(manager);
		}
		if (manager.matches(DDBiomes.SANDY_CATACOMBS.get())) {
			BiomeFeatures.generateSandyCatacombsFeatures(manager);
		}
		if (manager.matches(DDBiomes.CRYSTAL_CAVE.get())) {
			BiomeFeatures.generateCrystalCaveFeatures(manager);
		}
		if (manager.matches(DDBiomes.GLOWSHROOM_CAVES.get())) {
			BiomeFeatures.generateGlowshroomCaveFeatures(manager);
		}
	}

	static class BiomeFeatures {
		private static void generateCaveFeatures(GlobalBiomeManager manager) {
			VanillaBiomeFeatures.addGlowshrooms(manager.generation());
			VanillaBiomeFeatures.addMineables(manager.generation());
		}

		private static void generateMoltenCavernFeatures(GlobalBiomeManager manager) {
			VanillaBiomeFeatures.addAmber(manager.generation());
			VanillaBiomeFeatures.addMoltenCavernDecorations(manager.generation());
			VanillaBiomeFeatures.addMineables(manager.generation());
			manager.generation().withFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, DDConfiguredFeatures.MOLTEN_CAVERN_SPRING_OPEN);
			manager.generation().withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, DDConfiguredFeatures.GEYSER);
			manager.generation().withFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, DDConfiguredFeatures.MOLTEN_CAVERN_TERRAIN);
		}

		private static void generateSandyCatacombsFeatures(GlobalBiomeManager manager) {
			manager.generation().withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, DDConfiguredFeatures.PETRIFIED_LOG_BRANCH);
			VanillaBiomeFeatures.addSandyCatacombsTerrain(manager.generation());
			VanillaBiomeFeatures.addCaveFossils(manager.generation());
			VanillaBiomeFeatures.addSandyCatacombsVegetation(manager.generation());
			VanillaBiomeFeatures.addSandyCatacombsCompat(manager.generation());
			VanillaBiomeFeatures.addMineables(manager.generation(), false);
		}

		private static void generateCrystalCaveFeatures(GlobalBiomeManager manager) {
			VanillaBiomeFeatures.addGlowshrooms(manager.generation());
			VanillaBiomeFeatures.addMineables(manager.generation());
//			VanillaBiomeFeatures.addCrystalPeaks(manager.generation());
		}

		private static void generateGlowshroomCaveFeatures(GlobalBiomeManager manager) {
			VanillaBiomeFeatures.addGlowshroomVegetation(manager.generation());
			VanillaBiomeFeatures.addMineables(manager.generation());
			manager.generation().withFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, DDConfiguredFeatures.GLOWSHROOM_CAVE_TERRAIN);
			manager.spawns().withSpawner(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(DDEntityTypes.GLOWSHROOM_MONSTER.get(), 50, 1, 2));
		}
	}

	private static class GlobalBiomeManager {
		private final BiomeLoadingEvent event;

		public GlobalBiomeManager(BiomeLoadingEvent event) {
			this.event = event;
		}

		public Biome.Category getCategory() {
			return this.event.getCategory();
		}

		public ResourceLocation getName() {
			return this.event.getName();
		}

		public BiomeGenerationSettingsBuilder generation() {
			return this.event.getGeneration();
		}

		public MobSpawnInfoBuilder spawns() {
			return this.event.getSpawns();
		}

		public boolean matches(Biome object) {
			Biome biome = ForgeRegistries.BIOMES.getValue(this.getName());
			return object == biome;
		}

		public boolean matches(RegistryKey<Biome> object) {
			RegistryKey<Biome> key = RegistryKey.getOrCreateKey(Registry.BIOME_KEY, this.getName());
			return object == key;
		}
	}
}