package com.naterbobber.darkerdepths.data;

import com.naterbobber.darkerdepths.DarkerDepths;
import com.naterbobber.darkerdepths.data.assets.DDBlockStateProvider;
import com.naterbobber.darkerdepths.data.assets.DDLanguageProviderENUS;
import com.naterbobber.darkerdepths.data.loot.DDLootTableProvider;
import com.naterbobber.darkerdepths.data.tags.DDBiomeTagsProvider;
import com.naterbobber.darkerdepths.data.tags.DDBlockTagsProvider;
import com.naterbobber.darkerdepths.data.tags.DDDamageTypeTagsProvider;
import com.naterbobber.darkerdepths.data.tags.DDEnchantmentTagsProvider;
import com.naterbobber.darkerdepths.data.tags.DDItemTagsProvider;
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

        dataGenerator.addProvider(client, new DDBlockStateProvider(packOutput, existingFileHelper));
        dataGenerator.addProvider(client, new DDLanguageProviderENUS(packOutput));

        dataGenerator.addProvider(server, new DDRecipeProvider(packOutput, lookupProvider));
        dataGenerator.addProvider(server, new DDLootTableProvider(packOutput, lookupProvider));

        DDBlockTagsProvider blockTagsProvider = dataGenerator.addProvider(server, new DDBlockTagsProvider(packOutput, lookupProvider, existingFileHelper));
        dataGenerator.addProvider(server, new DDItemTagsProvider(packOutput, lookupProvider, blockTagsProvider.contentsGetter(), existingFileHelper));

        dataGenerator.addProvider(server, new DDBiomeTagsProvider(packOutput, lookupProvider, existingFileHelper));
        dataGenerator.addProvider(server, new DDDamageTypeTagsProvider(packOutput, lookupProvider, existingFileHelper));
        dataGenerator.addProvider(server, new DDEnchantmentTagsProvider(packOutput, lookupProvider));

        dataGenerator.addProvider(server, new DDDatapackBuiltinEntriesProvider(packOutput, lookupProvider));
    }
}

