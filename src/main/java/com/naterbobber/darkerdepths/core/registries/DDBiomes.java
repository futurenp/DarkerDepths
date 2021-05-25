package com.naterbobber.darkerdepths.core.registries;

import com.naterbobber.darkerdepths.core.DDRegistries;
import com.naterbobber.darkerdepths.core.DarkerDepths;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeAmbience;
import net.minecraft.world.biome.BiomeGenerationSettings;
import net.minecraft.world.biome.DefaultBiomeFeatures;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.biome.MoodSoundAmbience;
import net.minecraft.world.biome.ParticleEffectAmbience;
import net.minecraft.world.gen.feature.structure.StructureFeatures;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilders;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;

//<>

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class DDBiomes {
    public static final DDRegistries HELPER = DarkerDepths.REGISTRY_HELPER;

    public static final RegistryObject<Biome> MOLTEN_CAVERN 	= HELPER.registerBiome("molten_cavern", DDBiomes::makeMoltenCaverns);
    public static final RegistryObject<Biome> SANDY_CATACOMBS 	= HELPER.registerBiome("sandy_catacombs", DDBiomes::makeSandyCatacombs);
    public static final RegistryObject<Biome> CRYSTAL_CAVE 	    = HELPER.registerBiome("crystal_cave", DDBiomes::makeCrystalCaves);

    public static Biome makeMoltenCaverns() {
        MobSpawnInfo.Builder spawnSettings = new MobSpawnInfo.Builder();
        DefaultBiomeFeatures.withBatsAndHostiles(spawnSettings);
        BiomeGenerationSettings.Builder generationSettings = new BiomeGenerationSettings.Builder().withSurfaceBuilder(ConfiguredSurfaceBuilders.GRASS);
        DefaultBiomeFeatures.withStrongholdAndMineshaft(generationSettings);
        generationSettings.withStructure(StructureFeatures.RUINED_PORTAL);
        DefaultBiomeFeatures.withCavesAndCanyons(generationSettings);
        DefaultBiomeFeatures.withLavaAndWaterLakes(generationSettings);
        DefaultBiomeFeatures.withMonsterRoom(generationSettings);
        DefaultBiomeFeatures.withCommonOverworldBlocks(generationSettings);
        DefaultBiomeFeatures.withOverworldOres(generationSettings);
        DefaultBiomeFeatures.withNormalMushroomGeneration(generationSettings);
        DefaultBiomeFeatures.withSugarCaneAndPumpkins(generationSettings);
        DefaultBiomeFeatures.withLavaAndWaterSprings(generationSettings);
        DefaultBiomeFeatures.withFrozenTopLayer(generationSettings);
        return new Biome.Builder().precipitation(Biome.RainType.RAIN).category(Biome.Category.NONE).depth(0.1F).scale(0.2F).temperature(2.0F).downfall(0.0F).setEffects(new BiomeAmbience.Builder().setWaterColor(4159204).setWaterFogColor(329011).setFogColor(12638463).withSkyColor(getSkyColorWithTemperatureModifier(2.0F)).setMoodSound(MoodSoundAmbience.DEFAULT_CAVE).setParticle(new ParticleEffectAmbience(ParticleTypes.WHITE_ASH, 0.118093334f)).build()).withMobSpawnSettings(spawnSettings.build()).withGenerationSettings(generationSettings.build()).build();
    }

    public static Biome makeSandyCatacombs() {
        MobSpawnInfo.Builder spawnSettings = new MobSpawnInfo.Builder();
        DefaultBiomeFeatures.withBatsAndHostiles(spawnSettings);
        BiomeGenerationSettings.Builder generationSettings = new BiomeGenerationSettings.Builder().withSurfaceBuilder(ConfiguredSurfaceBuilders.GRASS);
        DefaultBiomeFeatures.withStrongholdAndMineshaft(generationSettings);
        generationSettings.withStructure(StructureFeatures.RUINED_PORTAL_DESERT);
        DefaultBiomeFeatures.withCavesAndCanyons(generationSettings);
        DefaultBiomeFeatures.withLavaAndWaterLakes(generationSettings);
        DefaultBiomeFeatures.withMonsterRoom(generationSettings);
        DefaultBiomeFeatures.withCommonOverworldBlocks(generationSettings);
        DefaultBiomeFeatures.withOverworldOres(generationSettings);
        DefaultBiomeFeatures.withNormalMushroomGeneration(generationSettings);
        DefaultBiomeFeatures.withSugarCaneAndPumpkins(generationSettings);
        DefaultBiomeFeatures.withLavaAndWaterSprings(generationSettings);
        DefaultBiomeFeatures.withFrozenTopLayer(generationSettings);
        return new Biome.Builder().precipitation(Biome.RainType.RAIN).category(Biome.Category.NONE).depth(0.125F).scale(0.05F).temperature(2.0F).downfall(0.0F).setEffects(new BiomeAmbience.Builder().setWaterColor(4159204).setWaterFogColor(329011).setFogColor(12638463).withSkyColor(getSkyColorWithTemperatureModifier(2.0F)).setMoodSound(MoodSoundAmbience.DEFAULT_CAVE).build()).withMobSpawnSettings(spawnSettings.build()).withGenerationSettings(generationSettings.build()).build();
    }

    public static Biome makeCrystalCaves() {
        MobSpawnInfo.Builder spawnSettings = new MobSpawnInfo.Builder();
        DefaultBiomeFeatures.withBatsAndHostiles(spawnSettings);
        BiomeGenerationSettings.Builder generationSettings = new BiomeGenerationSettings.Builder().withSurfaceBuilder(ConfiguredSurfaceBuilders.GRASS);
        DefaultBiomeFeatures.withStrongholdAndMineshaft(generationSettings);
        generationSettings.withStructure(StructureFeatures.RUINED_PORTAL);
        DefaultBiomeFeatures.withCavesAndCanyons(generationSettings);
        DefaultBiomeFeatures.withLavaAndWaterLakes(generationSettings);
        DefaultBiomeFeatures.withMonsterRoom(generationSettings);
        DefaultBiomeFeatures.withCommonOverworldBlocks(generationSettings);
        DefaultBiomeFeatures.withOverworldOres(generationSettings);
        DefaultBiomeFeatures.withNormalMushroomGeneration(generationSettings);
        DefaultBiomeFeatures.withSugarCaneAndPumpkins(generationSettings);
        DefaultBiomeFeatures.withLavaAndWaterSprings(generationSettings);
        DefaultBiomeFeatures.withFrozenTopLayer(generationSettings);
        return new Biome.Builder().precipitation(Biome.RainType.RAIN).category(Biome.Category.NONE).depth(0.125F).scale(0.05F).temperature(0.8F).downfall(0.4F).setEffects(new BiomeAmbience.Builder().setWaterColor(4159204).setWaterFogColor(329011).setFogColor(12638463).withSkyColor(getSkyColorWithTemperatureModifier(0.8F)).setMoodSound(MoodSoundAmbience.DEFAULT_CAVE).build()).withMobSpawnSettings(spawnSettings.build()).withGenerationSettings(generationSettings.build()).build();
    }

    private static int getSkyColorWithTemperatureModifier(float temperature) {
        float modifier = temperature / 3.0F;
        modifier = MathHelper.clamp(modifier, -1.0F, 1.0F);
        return MathHelper.hsvToRGB(0.62222224F - modifier * 0.05F, 0.5F + modifier * 0.1F, 1.0F);
    }
}