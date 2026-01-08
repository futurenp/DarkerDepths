package com.naterbobber.darkerdepths.data;

import com.naterbobber.darkerdepths.DarkerDepths;
import com.naterbobber.darkerdepths.data.assets.DDBlockStateProvider;
import com.naterbobber.darkerdepths.data.assets.DDLanguageProviderENUS;
import com.naterbobber.darkerdepths.data.loot.DDBlockLoot;
import com.naterbobber.darkerdepths.data.loot.DDLootTableProvider;
import com.naterbobber.darkerdepths.data.tags.DDBiomeTagsProvider;
import com.naterbobber.darkerdepths.data.tags.DDBlockTagsProvider;
import com.naterbobber.darkerdepths.data.tags.DDDamageTypeTagsProvider;
import com.naterbobber.darkerdepths.data.tags.DDItemTagsProvider;
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

        dataGenerator.addProvider(server, new DDRecipeProvider(packOutput));
        dataGenerator.addProvider(server, new DDLootTableProvider(packOutput));

        DDBlockTagsProvider blockTagsProvider = dataGenerator.addProvider(server, new DDBlockTagsProvider(packOutput, registryLookup, existingFileHelper));
        dataGenerator.addProvider(server, new DDItemTagsProvider(packOutput, registryLookup, blockTagsProvider.contentsGetter(), existingFileHelper));

        dataGenerator.addProvider(server, new DDBiomeTagsProvider(packOutput, registryLookup, existingFileHelper));
        dataGenerator.addProvider(server, new DDDamageTypeTagsProvider(packOutput, registryLookup, existingFileHelper));}
}
