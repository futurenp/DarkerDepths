package com.naterbobber.darkerdepths.data;

import com.naterbobber.darkerdepths.DarkerDepths;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = DarkerDepths.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DDDataGenerator {

    private DDDataGenerator() {
    }

    @SubscribeEvent
    public static void onGatherData(GatherDataEvent event) {
        DataGenerator dataGenerator = event.getGenerator();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        dataGenerator.addProvider(event.includeServer(), new DDRecipeProvider(dataGenerator));
        dataGenerator.addProvider(event.includeServer(), new DDBlockTagsProvider(dataGenerator, existingFileHelper));
        dataGenerator.addProvider(event.includeServer(), new DDItemTagsProvider(dataGenerator, existingFileHelper));
        dataGenerator.addProvider(event.includeServer(), new DDBiomeTagsProvider(dataGenerator, existingFileHelper));
        dataGenerator.addProvider(event.includeServer(), new DDLootTableProvider(dataGenerator));
    }

}
