package com.naterbobber.darkerdepths.init;

import com.naterbobber.darkerdepths.DarkerDepths;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.storage.loot.LootTable;

public class DDBuiltinLootTables {

    public static final ResourceKey<LootTable> CATACOMBS_CHEST_STANDARD = key("chests/catacombs/standard");
    public static final ResourceKey<LootTable> CATACOMBS_CHEST_TREASURE = key("chests/catacombs/treasure");
    public static final ResourceKey<LootTable> CATACOMBS_ARCHAEOLOGY_STANDARD = key("archaeology/catacombs/standard");

    private static ResourceKey<LootTable> key(String name) {
        return ResourceKey.create(Registries.LOOT_TABLE, DarkerDepths.id(name));
    }

}
