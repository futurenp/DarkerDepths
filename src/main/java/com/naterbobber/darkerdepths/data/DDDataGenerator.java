package com.naterbobber.darkerdepths.data;

import com.naterbobber.darkerdepths.DarkerDepths;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;

@Mod.EventBusSubscriber(modid = DarkerDepths.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DDDataGenerator {

    private DDDataGenerator() {
    }

    @SubscribeEvent
    public static void onGatherData(GatherDataEvent event) {
        DataGenerator dataGenerator = event.getGenerator();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
//        dataGenerator.addProvider(new DDRecipeProvider(dataGenerator));
        dataGenerator.addProvider(new DDBlockTagsProvider(dataGenerator, existingFileHelper));
        dataGenerator.addProvider(new DDItemTagsProvider(dataGenerator, existingFileHelper));
        dataGenerator.addProvider(new DDBiomeTagsProvider(dataGenerator, existingFileHelper));
    }

}