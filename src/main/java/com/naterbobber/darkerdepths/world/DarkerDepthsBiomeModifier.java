package com.naterbobber.darkerdepths.world;

import com.mojang.serialization.Codec;
import com.naterbobber.darkerdepths.init.DDBiomeModifiers;
import com.naterbobber.darkerdepths.init.DDBiomes;
import com.naterbobber.darkerdepths.init.DDEntityTypes;
import com.naterbobber.darkerdepths.init.DDPlacedFeatures;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.MobSpawnSettingsBuilder;
import net.minecraftforge.common.world.ModifiableBiomeInfo;

public class DarkerDepthsBiomeModifier implements BiomeModifier {

    @Override
    public void modify(Holder<Biome> biome, Phase phase, ModifiableBiomeInfo.BiomeInfo.Builder builder) {
        BiomeGenerationSettingsBuilder generationSettings = builder.getGenerationSettings();
        MobSpawnSettingsBuilder mobSpawnSettings = builder.getMobSpawnSettings();
        if (phase == Phase.ADD) {
//            generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, DDPlacedFeatures.GLOWSHROOM_PATCH);
//            generationSettings.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, DDPlacedFeatures.SILVER_ORE);
            if (biome.is(DDBiomes.MOLTEN_CAVERN.getId())) {
                generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, DDPlacedFeatures.SHALE_PLACEMENT);
                generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, DDPlacedFeatures.AMBER);
                generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, DDPlacedFeatures.MOLTEN_POOL);
                generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, DDPlacedFeatures.MOLTEN_SPRING);
                generationSettings.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, DDPlacedFeatures.MAGMA_ORE);
            }
            if (biome.is(DDBiomes.SANDY_CATACOMBS.getId())) {
                generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, DDPlacedFeatures.ARID_SURFACE);
                generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, DDPlacedFeatures.ARID_BOULDER);
                generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, DDPlacedFeatures.PETRIFIED_BRANCH);
            }
            if (biome.is(DDBiomes.GLOWSHROOM_FOREST.getId())) {
                generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, DDPlacedFeatures.GLIMMERING_VINES);
                generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, DDPlacedFeatures.HUGE_GLOWSHROOM);
                generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, DDPlacedFeatures.GRIME_SURFACE);
                mobSpawnSettings.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(DDEntityTypes.GLOWSHROOM_MONSTER.get(), 50, 1, 2));
            }
        }
    }

    @Override
    public Codec<? extends BiomeModifier> codec() {
        return DDBiomeModifiers.DD_BIOME_MODIFIER.get();
    }

}
