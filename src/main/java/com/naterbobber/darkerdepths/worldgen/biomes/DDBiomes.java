package com.naterbobber.darkerdepths.worldgen.biomes;

import com.naterbobber.darkerdepths.client.fog.DDBiomeFogs;
import com.naterbobber.darkerdepths.util.DDResourceKeys;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.CavePlacements;
import net.minecraft.sounds.Music;
import net.minecraft.sounds.Musics;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.level.biome.*;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

import static com.naterbobber.darkerdepths.util.DDResourceKeys.Biomes.*;
public class DDBiomes {
    public static void bootstrap(BootstrapContext<Biome> context) {
        HolderGetter<PlacedFeature> holdergetter = context.lookup(Registries.PLACED_FEATURE);
        HolderGetter<ConfiguredWorldCarver<?>> holdergetter1 = context.lookup(Registries.CONFIGURED_CARVER);
        context.register(MOLTEN_CAVERN, createMoltenCavern(holdergetter, holdergetter1));
        context.register(SANDY_CATACOMBS, createSandyCatacombs(holdergetter, holdergetter1));
        context.register(GLOWSHROOM_FOREST, createGlowshroomForest(holdergetter, holdergetter1));
    }

    public static Biome createGlowshroomForest(HolderGetter<PlacedFeature> holderGetter, HolderGetter<ConfiguredWorldCarver<?>> holderGetter1) {
        MobSpawnSettings.Builder mobBuilder = new MobSpawnSettings.Builder();
        BiomeDefaultFeatures.commonSpawns(mobBuilder);
        BiomeGenerationSettings.Builder biomeBuilder = new BiomeGenerationSettings.Builder(holderGetter, holderGetter1);
        BiomeDefaultFeatures.addDefaultCarversAndLakes(biomeBuilder);
        BiomeDefaultFeatures.addDefaultCrystalFormations(biomeBuilder);
        BiomeDefaultFeatures.addDefaultMonsterRoom(biomeBuilder);
        BiomeDefaultFeatures.addDefaultSprings(biomeBuilder);
        BiomeDefaultFeatures.addSurfaceFreezing(biomeBuilder);
        BiomeDefaultFeatures.addPlainGrass(biomeBuilder);
        BiomeDefaultFeatures.addDefaultOres(biomeBuilder);
        Music music = Musics.createGameMusic(SoundEvents.MUSIC_BIOME_DRIPSTONE_CAVES);
        int fogColor = DDBiomeFogs.GLOWSHROOM_FOREST.getIntColor();

        return new DDBiomeBuilder(biomeBuilder)
                .mobBuilder(mobBuilder)
                .music(music)
                .waterColor(4169409)
                .waterFogColor(341062)
                .grassColor(5478752)
                .fogColor(fogColor)
                .build();
    }

    public static Biome createMoltenCavern(HolderGetter<PlacedFeature> holderGetter, HolderGetter<ConfiguredWorldCarver<?>> holderGetter1) {
        MobSpawnSettings.Builder mobBuilder = new MobSpawnSettings.Builder();
        BiomeDefaultFeatures.commonSpawns(mobBuilder);
        BiomeGenerationSettings.Builder biomeBuilder = new BiomeGenerationSettings.Builder(holderGetter, holderGetter1);
        biomeBuilder.addCarver(GenerationStep.Carving.AIR, DDResourceKeys.ConfiguredWorldCarvers.MAGMA_CARVER);
        biomeBuilder.addCarver(GenerationStep.Carving.AIR, DDResourceKeys.ConfiguredWorldCarvers.TUFF_CARVER);
        BiomeDefaultFeatures.addDefaultCarversAndLakes(biomeBuilder);
        BiomeDefaultFeatures.addDefaultCrystalFormations(biomeBuilder);
        BiomeDefaultFeatures.addDefaultMonsterRoom(biomeBuilder);
        BiomeDefaultFeatures.addSurfaceFreezing(biomeBuilder);
        BiomeDefaultFeatures.addPlainGrass(biomeBuilder);
        BiomeDefaultFeatures.addDefaultOres(biomeBuilder);
        Music music = Musics.createGameMusic(SoundEvents.MUSIC_BIOME_DRIPSTONE_CAVES);
        int fogColor = DDBiomeFogs.MOLTEN_CAVERN.getIntColor();

        return new DDBiomeBuilder(biomeBuilder)
                .music(music)
                .mobBuilder(mobBuilder)
                .waterColor(4169409)
                .waterFogColor(341062)
                .grassColor(5478752)
                .fogColor(fogColor)
                .build();
    }

    public static Biome createSandyCatacombs(HolderGetter<PlacedFeature> holderGetter, HolderGetter<ConfiguredWorldCarver<?>> holderGetter1) {
        BiomeGenerationSettings.Builder biomeBuilder = new BiomeGenerationSettings.Builder(holderGetter, holderGetter1);
        biomeBuilder.addCarver(GenerationStep.Carving.AIR, DDResourceKeys.ConfiguredWorldCarvers.DUSKROCK_CARVER);
        BiomeDefaultFeatures.addDefaultCarversAndLakes(biomeBuilder);
        BiomeDefaultFeatures.addDefaultCrystalFormations(biomeBuilder);
        BiomeDefaultFeatures.addDefaultMonsterRoom(biomeBuilder);
        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, CavePlacements.GLOW_LICHEN);
        BiomeDefaultFeatures.addDefaultSprings(biomeBuilder);
        BiomeDefaultFeatures.addSurfaceFreezing(biomeBuilder);
        BiomeDefaultFeatures.addPlainGrass(biomeBuilder);
        BiomeDefaultFeatures.addDefaultOres(biomeBuilder);
        Music music = Musics.createGameMusic(SoundEvents.MUSIC_BIOME_DRIPSTONE_CAVES);
        int fogColor = DDBiomeFogs.SANDY_CATACOMBS.getIntColor();

        return new DDBiomeBuilder(biomeBuilder)
                .precipitation(false)
                .music(music)
                .waterColor(4169409)
                .waterFogColor(341062)
                .grassColor(11976546)
                .fogColor(fogColor)
                .build();
    }

    private static class DDBiomeBuilder {
        private int waterColor = 4159204;
        private int waterFogColor = 329011;
        private int fogColor = 12638463;
        private int grassColor = 5478752;
        private float temperature = 0.5F;
        private float downfall = 0.5F;
        private int skyColor = calculateSkyColor(temperature);
        private boolean precipitation = true;
        private MobSpawnSettings.Builder mobBuilder = new MobSpawnSettings.Builder();
        private final BiomeGenerationSettings.Builder biomeBuilder;
        private Music music = null;

        public DDBiomeBuilder(BiomeGenerationSettings.Builder biomeBuilder) {
            this.biomeBuilder = biomeBuilder;
        }

        public DDBiomeBuilder waterColor(int waterColor) {
            this.waterColor = waterColor;
            return this;
        }

        public DDBiomeBuilder waterFogColor(int waterFogColor) {
            this.waterFogColor = waterFogColor;
            return this;
        }

        public DDBiomeBuilder fogColor(int fogColor) {
            this.fogColor = fogColor;
            return this;
        }

        public DDBiomeBuilder grassColor(int grassColor) {
            this.grassColor = grassColor;
            return this;
        }

        public DDBiomeBuilder music(Music music) {
            this.music = music;
            return this;
        }

        public DDBiomeBuilder temperature(float temperature) {
            this.temperature = temperature;
            return this;
        }

        public DDBiomeBuilder downfall(float downfall) {
            this.downfall = downfall;
            return this;
        }

        public DDBiomeBuilder skyColor(int skyColor) {
            this.skyColor = skyColor;
            return this;
        }

        public DDBiomeBuilder precipitation(boolean precipitation) {
            this.precipitation = precipitation;
            return this;
        }

        public DDBiomeBuilder mobBuilder(MobSpawnSettings.Builder mobBuilder) {
            this.mobBuilder = mobBuilder;
            return this;
        }

        public Biome build() {
            var biomeSpecialEffects = new BiomeSpecialEffects.Builder()
                    .waterColor(waterColor)
                    .waterFogColor(waterFogColor)
                    .fogColor(fogColor)
                    .grassColorOverride(grassColor)
                    .skyColor(skyColor)
                    .ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
                    .backgroundMusic(music)
                    .build();

            return (new Biome.BiomeBuilder())
                    .hasPrecipitation(precipitation)
                    .temperature(temperature)
                    .downfall(downfall)
                    .specialEffects(biomeSpecialEffects)
                    .mobSpawnSettings(mobBuilder.build())
                    .generationSettings(biomeBuilder.build())
                    .build();
        }

        private static int calculateSkyColor(float temperature) {
            float $$1 = temperature / 3.0F;
            $$1 = Mth.clamp($$1, -1.0F, 1.0F);
            return Mth.hsvToRgb(0.62222224F - $$1 * 0.05F, 0.5F + $$1 * 0.1F, 1.0F);
        }

    }

}
