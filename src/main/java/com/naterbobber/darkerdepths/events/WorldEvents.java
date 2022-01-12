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

        Biome biome = ForgeRegistries.BIOMES.getValue(event.getName());

        if (biome == DDBiomes.MOLTEN_CAVERN.get()) {
            builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, DDPlacedFeatures.AMBER);
            builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, DDPlacedFeatures.MOLTEN_POOL);
            builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, DDPlacedFeatures.MOLTEN_SPRING);
            builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, DDPlacedFeatures.SHALE_PLACEMENT);
        }

        if (biome == DDBiomes.SANDY_CATACOMBS.get()) {
            builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, DDPlacedFeatures.ARIDROCK_PLACEMENT);
            builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, DDPlacedFeatures.LIMESTONE_PLACEMENT);
            builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, DDPlacedFeatures.PETRIFIED_BRANCH);
            builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, DDPlacedFeatures.SOUL_SOIL);
        }
    }


}
