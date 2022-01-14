package com.naterbobber.darkerdepths.events;

import com.naterbobber.darkerdepths.DarkerDepths;
import com.naterbobber.darkerdepths.init.DDBiomes;
import com.naterbobber.darkerdepths.init.DDPlacedFeatures;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(modid = DarkerDepths.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class WorldEvents {

    @SubscribeEvent
    public void onBiomeLoad(BiomeLoadingEvent event) {
        BiomeGenerationSettingsBuilder builder = event.getGeneration();
        Biome.BiomeCategory category = event.getCategory();

        if (category == Biome.BiomeCategory.NONE || category == Biome.BiomeCategory.THEEND || category == Biome.BiomeCategory.NETHER) return;

        builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, DDPlacedFeatures.GLOWSHROOM_PATCH);
        builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, DDPlacedFeatures.SILVER_ORE);

        Biome biome = ForgeRegistries.BIOMES.getValue(event.getName());

        if (biome == DDBiomes.MOLTEN_CAVERN.get()) {
            builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, DDPlacedFeatures.SHALE_PLACEMENT);
            builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, DDPlacedFeatures.AMBER);
            builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, DDPlacedFeatures.MOLTEN_POOL);
            builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, DDPlacedFeatures.MOLTEN_SPRING);
            builder.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, DDPlacedFeatures.MAGMA_ORE);
        }

        if (biome == DDBiomes.SANDY_CATACOMBS.get()) {
            builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, DDPlacedFeatures.ARID_SURFACE);
            builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, DDPlacedFeatures.ARID_BOULDER);
//            builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, DDPlacedFeatures.LIMESTONE_PLACEMENT);
            builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, DDPlacedFeatures.PETRIFIED_BRANCH);
            builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, DDPlacedFeatures.SOUL_SOIL);
        }

        if (biome == DDBiomes.GLOWSHROOM_FOREST.get()) {
            builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, DDPlacedFeatures.GLIMMERING_VINES);
            builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, DDPlacedFeatures.GRIMESTONE_PLACEMENT);
            builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, DDPlacedFeatures.HUGE_GLOWSHROOM);
            builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, DDPlacedFeatures.GRIME_VEGETATION);
        }
    }


}
