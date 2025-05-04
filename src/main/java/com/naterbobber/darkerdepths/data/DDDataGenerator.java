package com.naterbobber.darkerdepths.data;

import com.naterbobber.darkerdepths.DarkerDepths;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.concurrent.CompletableFuture;

@Mod.EventBusSubscriber(modid = DarkerDepths.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DDDataGenerator {

    private DDDataGenerator() {
    }

    @SubscribeEvent
    public static void onGatherData(GatherDataEvent event) {
        DataGenerator dataGenerator = event.getGenerator();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        PackOutput packOutput = dataGenerator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        boolean server = event.includeServer();

        dataGenerator.addProvider(server, new DDRecipeProvider(packOutput));
        dataGenerator.addProvider(server, new DDLootTableProvider(packOutput));

        DDBlockTagsProvider blockTagsProvider = new DDBlockTagsProvider(packOutput, lookupProvider, existingFileHelper);

        dataGenerator.addProvider(server, blockTagsProvider);
        dataGenerator.addProvider(server, new DDItemTagsProvider(packOutput, lookupProvider, blockTagsProvider.contentsGetter(), existingFileHelper));
        dataGenerator.addProvider(server, new DDBiomeTagsProvider(packOutput, lookupProvider, existingFileHelper));
        dataGenerator.addProvider(server, new DDDatapackBuiltinEntriesProvider(packOutput, lookupProvider));
    }

}
