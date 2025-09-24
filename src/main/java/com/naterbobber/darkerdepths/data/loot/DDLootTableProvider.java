package com.naterbobber.darkerdepths.data.loot;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class DDLootTableProvider extends LootTableProvider {

    public DDLootTableProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> registries) {
        super(packOutput, Set.of(), List.of(
                new SubProviderEntry(DDEntityLoot::new, LootContextParamSets.ENTITY),
                //new SubProviderEntry(DDBlockLoot::new, LootContextParamSets.BLOCK),
                new SubProviderEntry(DDChestLoot::new, LootContextParamSets.CHEST),
                new SubProviderEntry(DDArchaeologyLoot::new, LootContextParamSets.ARCHAEOLOGY)
        ), registries);
    }

}
