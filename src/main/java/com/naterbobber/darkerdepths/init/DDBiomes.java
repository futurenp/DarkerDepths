package com.naterbobber.darkerdepths.init;

import com.naterbobber.darkerdepths.DarkerDepths;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.sounds.Music;
import net.minecraft.sounds.Musics;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.AmbientMoodSettings;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber(modid = DarkerDepths.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DDBiomes {

    public static final DeferredRegister<Biome> BIOMES = DeferredRegister.create(ForgeRegistries.BIOMES, DarkerDepths.MODID);

    public static final RegistryObject<Biome> MOLTEN_CAVERN = BIOMES.register("molten_cavern", DDBiomes::createMoltenCavern);
    public static final RegistryObject<Biome> SANDY_CATACOMBS = BIOMES.register("sandy_catacombs", DDBiomes::createSandyCatacombs);
    public static final RegistryObject<Biome> GLOWSHROOM_FOREST = BIOMES.register("glowshroom_forest", DDBiomes::createGlowshroomForest);

    public static Biome createGlowshroomForest() {
        MobSpawnSettings.Builder mobBuilder = new MobSpawnSettings.Builder();
        BiomeDefaultFeatures.commonSpawns(mobBuilder);
        BiomeGenerationSettings.Builder biomeBuilder = new BiomeGenerationSettings.Builder();
        BiomeDefaultFeatures.addDefaultCarversAndLakes(biomeBuilder);
        BiomeDefaultFeatures.addDefaultCrystalFormations(biomeBuilder);
        BiomeDefaultFeatures.addDefaultMonsterRoom(biomeBuilder);
        BiomeDefaultFeatures.addDefaultUndergroundVariety(biomeBuilder);
        BiomeDefaultFeatures.addDefaultSprings(biomeBuilder);
        BiomeDefaultFeatures.addSurfaceFreezing(biomeBuilder);
        BiomeDefaultFeatures.addPlainGrass(biomeBuilder);
        BiomeDefaultFeatures.addDefaultOres(biomeBuilder);
        Music music = Musics.createGameMusic(SoundEvents.MUSIC_BIOME_DRIPSTONE_CAVES);
        return biome(Biome.Precipitation.RAIN, Biome.BiomeCategory.UNDERGROUND, 0.5F, 0.5F, mobBuilder, biomeBuilder, music);
    }

    public static Biome createMoltenCavern() {
        MobSpawnSettings.Builder mobBuilder = new MobSpawnSettings.Builder();
        BiomeDefaultFeatures.commonSpawns(mobBuilder);
        BiomeGenerationSettings.Builder biomeBuilder = new BiomeGenerationSettings.Builder();
        BiomeDefaultFeatures.addDefaultCarversAndLakes(biomeBuilder);
        BiomeDefaultFeatures.addDefaultCrystalFormations(biomeBuilder);
        BiomeDefaultFeatures.addDefaultMonsterRoom(biomeBuilder);
        BiomeDefaultFeatures.addDefaultUndergroundVariety(biomeBuilder);
        BiomeDefaultFeatures.addSurfaceFreezing(biomeBuilder);
        BiomeDefaultFeatures.addPlainGrass(biomeBuilder);
        BiomeDefaultFeatures.addDefaultOres(biomeBuilder);
        Music music = Musics.createGameMusic(SoundEvents.MUSIC_BIOME_DRIPSTONE_CAVES);
        return biome(Biome.Precipitation.RAIN, Biome.BiomeCategory.UNDERGROUND, 0.5F, 0.5F, mobBuilder, biomeBuilder, music);
    }

    public static Biome createSandyCatacombs() {
        MobSpawnSettings.Builder mobBuilder = new MobSpawnSettings.Builder();
        BiomeDefaultFeatures.commonSpawns(mobBuilder);
        BiomeGenerationSettings.Builder biomeBuilder = new BiomeGenerationSettings.Builder();
        BiomeDefaultFeatures.addDefaultCarversAndLakes(biomeBuilder);
        BiomeDefaultFeatures.addDefaultCrystalFormations(biomeBuilder);
        BiomeDefaultFeatures.addDefaultMonsterRoom(biomeBuilder);
        BiomeDefaultFeatures.addDefaultUndergroundVariety(biomeBuilder);
        BiomeDefaultFeatures.addDefaultSprings(biomeBuilder);
        BiomeDefaultFeatures.addSurfaceFreezing(biomeBuilder);
        BiomeDefaultFeatures.addPlainGrass(biomeBuilder);
        BiomeDefaultFeatures.addDefaultOres(biomeBuilder);
        Music music = Musics.createGameMusic(SoundEvents.MUSIC_BIOME_DRIPSTONE_CAVES);
        return biome(Biome.Precipitation.RAIN, Biome.BiomeCategory.UNDERGROUND, 0.5F, 0.5F, mobBuilder, biomeBuilder, music);
    }

    private static Biome biome(Biome.Precipitation percipitation, Biome.BiomeCategory category, float p_194864_, float p_194865_, MobSpawnSettings.Builder p_194866_, BiomeGenerationSettings.Builder p_194867_, @Nullable Music p_194868_) {
        return biome(percipitation, category, p_194864_, p_194865_, 4159204, 329011, p_194866_, p_194867_, p_194868_);
    }

    private static Biome biome(Biome.Precipitation percipitation, Biome.BiomeCategory category, float p_194854_, float p_194855_, int p_194856_, int p_194857_, MobSpawnSettings.Builder p_194858_, BiomeGenerationSettings.Builder p_194859_, @Nullable Music p_194860_) {
        return (new Biome.BiomeBuilder()).precipitation(percipitation).biomeCategory(category).temperature(p_194854_).downfall(p_194855_).specialEffects((new BiomeSpecialEffects.Builder()).waterColor(p_194856_).waterFogColor(p_194857_).fogColor(12638463).skyColor(calculateSkyColor(p_194854_)).ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS).backgroundMusic(p_194860_).build()).mobSpawnSettings(p_194858_.build()).generationSettings(p_194859_.build()).build();
    }

    protected static int calculateSkyColor(float temperature) {
        float $$1 = temperature / 3.0F;
        $$1 = Mth.clamp($$1, -1.0F, 1.0F);
        return Mth.hsvToRgb(0.62222224F - $$1 * 0.05F, 0.5F + $$1 * 0.1F, 1.0F);
    }

}
