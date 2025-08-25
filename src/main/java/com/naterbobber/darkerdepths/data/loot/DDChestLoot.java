package com.naterbobber.darkerdepths.data.loot;

import com.naterbobber.darkerdepths.DarkerDepths;
import com.naterbobber.darkerdepths.init.DDItems;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.function.BiConsumer;

public class DDChestLoot implements LootTableSubProvider {
    public static final ResourceLocation CATACOMBS_CHEST_STANDARD =
            ResourceLocation.fromNamespaceAndPath(DarkerDepths.MODID, "chests/catacombs/standard");

    public static final ResourceLocation CATACOMBS_CHEST_TREASURE =
            ResourceLocation.fromNamespaceAndPath(DarkerDepths.MODID, "chests/catacombs/treasure");

    @Override
    public void generate(BiConsumer<ResourceLocation, LootTable.Builder> consumer) {
        LootTable.Builder catacombs_chest_standard = LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(UniformGenerator.between(3.0F, 6.0F))
                        .add(LootItem.lootTableItem(DDItems.AMBER.get())
                                .setWeight(10)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))))
                        .add(LootItem.lootTableItem(DDItems.VOID_SOUL_REQUIEM.get())
                                .setWeight(1))
                );
        consumer.accept(CATACOMBS_CHEST_STANDARD, catacombs_chest_standard);

        LootTable.Builder catacombs_chest_treasure = LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(UniformGenerator.between(3.0F, 6.0F))
                        .add(LootItem.lootTableItem(DDItems.AMBER.get())
                                .setWeight(10)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))))
                        .add(LootItem.lootTableItem(DDItems.VOID_SOUL_REQUIEM.get())
                                .setWeight(1))
                );
        consumer.accept(CATACOMBS_CHEST_TREASURE, catacombs_chest_treasure);
    }

}