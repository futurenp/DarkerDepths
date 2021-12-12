package com.naterbobber.darkerdepths.datagen;

import com.naterbobber.darkerdepths.core.DarkerDepths;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

@Mod.EventBusSubscriber(modid = DarkerDepths.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DDDataGenerator {

    private DDDataGenerator() {
    }

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator dataGenerator = event.getGenerator();
        ExistingFileHelper helper = event.getExistingFileHelper();

        dataGenerator.addProvider(new DDBlockstateProvider(dataGenerator, helper));
        dataGenerator.addProvider(new DDItemModelProvider(dataGenerator, helper));

    }

}
