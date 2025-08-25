package com.naterbobber.darkerdepths.data.loot;

import com.naterbobber.darkerdepths.data.loot.DDEntityLoot;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

import java.util.List;
import java.util.Set;

public class DDLootTableProvider extends LootTableProvider {

    public DDLootTableProvider(PackOutput packOutput) {
        super(packOutput, Set.of(), List.of(
                new SubProviderEntry(DDEntityLoot::new, LootContextParamSets.ENTITY),
                new SubProviderEntry(DDBlockLoot::new, LootContextParamSets.BLOCK),
                new SubProviderEntry(DDChestLoot::new, LootContextParamSets.CHEST)
        ));
    }

}
