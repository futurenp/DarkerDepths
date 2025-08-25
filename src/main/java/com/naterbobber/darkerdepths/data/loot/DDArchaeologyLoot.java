package com.naterbobber.darkerdepths.data.loot;

import com.naterbobber.darkerdepths.DarkerDepths;
import com.naterbobber.darkerdepths.init.DDItems;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;

import java.util.function.BiConsumer;

public class DDArchaeologyLoot implements LootTableSubProvider {

    public static final ResourceLocation CATACOMBS_POT_STANDARD =
            ResourceLocation.fromNamespaceAndPath(DarkerDepths.MODID, "archaeology/catacombs/standard");
    @Override
    public void generate(BiConsumer<ResourceLocation, LootTable.Builder> consumer) {

        LootTable.Builder catacombs_pot_standard_loot = LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1.0F))
                        .add(LootItem.lootTableItem(Items.BONE_MEAL).setWeight(4))
                        .add(LootItem.lootTableItem(DDItems.AMBER.get()).setWeight(4))
                        .add(LootItem.lootTableItem(Items.COAL).setWeight(2))
                        .add(LootItem.lootTableItem(Items.BONE).setWeight(2))
                        .add(LootItem.lootTableItem(Items.CLAY_BALL).setWeight(2))
                        .add(LootItem.lootTableItem(Items.BOWL).setWeight(2))
                        .add(LootItem.lootTableItem(Items.BOOK).setWeight(2))
                        .add(LootItem.lootTableItem(DDItems.VOID_SOUL_TORCH.get()).setWeight(2))
                        .add(LootItem.lootTableItem(DDItems.ROPE.get()).setWeight(2))
                        .add(LootItem.lootTableItem(Items.IRON_NUGGET).setWeight(2))
                        .add(LootItem.lootTableItem(Items.GOLD_NUGGET).setWeight(2))
                        .add(LootItem.lootTableItem(Items.IRON_INGOT).setWeight(2))
                        .add(LootItem.lootTableItem(Items.GLASS_BOTTLE).setWeight(1))
                        .add(LootItem.lootTableItem(Items.PAPER).setWeight(1))
                        .add(LootItem.lootTableItem(Items.RAW_COPPER).setWeight(1))
                        .add(LootItem.lootTableItem(Items.RAW_IRON).setWeight(1))
                        .add(LootItem.lootTableItem(Items.RAW_GOLD).setWeight(1))
                        .add(LootItem.lootTableItem(Items.GOLD_INGOT).setWeight(1))
                        .add(LootItem.lootTableItem(Items.BURN_POTTERY_SHERD).setWeight(1))
                        .add(LootItem.lootTableItem(Items.BLADE_POTTERY_SHERD).setWeight(1))
                        .add(LootItem.lootTableItem(Items.SKULL_POTTERY_SHERD).setWeight(1))
                        .add(LootItem.lootTableItem(Items.DUNE_ARMOR_TRIM_SMITHING_TEMPLATE).setWeight(1))
                        .add(LootItem.lootTableItem(DDItems.VOID_SOUL_REQUIEM.get()).setWeight(1))
                        .add(LootItem.lootTableItem(Items.DIAMOND).setWeight(1))
                        .add(LootItem.lootTableItem(DDItems.FORSAKEN_BRONZE_SCRAP.get()).setWeight(1))
                );
        consumer.accept(CATACOMBS_POT_STANDARD, catacombs_pot_standard_loot);
    }
}