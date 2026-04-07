package com.naterbobber.darkerdepths.data;

import com.naterbobber.darkerdepths.DarkerDepths;
import com.naterbobber.darkerdepths.data.assets.DDBlockStateProvider;
import com.naterbobber.darkerdepths.data.assets.DDLanguageProviderENUS;
import com.naterbobber.darkerdepths.data.loot.DDLootTableProvider;
import com.naterbobber.darkerdepths.data.tags.*;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(modid = DarkerDepths.MOD_ID)
public class DDDataGenerator {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator dataGenerator = event.getGenerator();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        PackOutput packOutput = dataGenerator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        boolean server = event.includeServer();
        boolean client = event.includeClient();

        DDDatapackBuiltinEntriesProvider datapackProvider = new DDDatapackBuiltinEntriesProvider(packOutput, lookupProvider);
        dataGenerator.addProvider(server, datapackProvider);

        CompletableFuture<HolderLookup.Provider> registryLookup = datapackProvider.getRegistryProvider();

        dataGenerator.addProvider(client, new DDBlockStateProvider(packOutput, existingFileHelper));
        dataGenerator.addProvider(client, new DDLanguageProviderENUS(packOutput));

        dataGenerator.addProvider(server, new DDAdvancementProvider(packOutput, registryLookup, existingFileHelper));
        dataGenerator.addProvider(server, new DDRecipeProvider(packOutput, registryLookup));
        dataGenerator.addProvider(server, new DDDataMapProvider(packOutput, registryLookup));
        dataGenerator.addProvider(server, new DDLootTableProvider(packOutput, registryLookup));

        DDBlockTagsProvider blockTagsProvider = dataGenerator.addProvider(server, new DDBlockTagsProvider(packOutput, registryLookup, existingFileHelper));
        dataGenerator.addProvider(server, new DDFluidTagsProvider(packOutput, registryLookup, existingFileHelper));
        dataGenerator.addProvider(server, new DDItemTagsProvider(packOutput, registryLookup, blockTagsProvider.contentsGetter(), existingFileHelper));
        dataGenerator.addProvider(server, new DDBiomeTagsProvider(packOutput, registryLookup, existingFileHelper));
        dataGenerator.addProvider(server, new DDEntityTypeTagsProvider(packOutput, registryLookup, existingFileHelper));
        dataGenerator.addProvider(server, new DDDamageTypeTagsProvider(packOutput, registryLookup, existingFileHelper));
        dataGenerator.addProvider(server, new DDEnchantmentTagsProvider(packOutput, registryLookup));
    }
}