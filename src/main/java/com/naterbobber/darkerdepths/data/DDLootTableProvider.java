package com.naterbobber.darkerdepths.data;

import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

import java.util.List;
import java.util.Set;

public class DDLootTableProvider extends LootTableProvider {

    public DDLootTableProvider(PackOutput packOutput) {
        super(packOutput, Set.of(), List.of(
                new SubProviderEntry(DDEntityLoot::new, LootContextParamSets.ENTITY)
        ));
    }

}
