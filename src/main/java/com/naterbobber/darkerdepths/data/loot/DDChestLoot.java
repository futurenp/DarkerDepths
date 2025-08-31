package com.naterbobber.darkerdepths.data.loot;

import com.naterbobber.darkerdepths.DarkerDepths;
import com.naterbobber.darkerdepths.init.DDBlocks;
import com.naterbobber.darkerdepths.init.DDItems;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.EnchantRandomlyFunction;
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
                        .setRolls(UniformGenerator.between(5.0F, 10.0F))
                        .add(LootItem.lootTableItem(DDItems.ROPE.get()).setWeight(8)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 6.0F))))
                        .add(LootItem.lootTableItem(DDItems.AMBER.get()).setWeight(8)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))))
                        .add(LootItem.lootTableItem(Items.IRON_INGOT).setWeight(8)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))))
                        .add(LootItem.lootTableItem(Items.IRON_NUGGET).setWeight(8)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 5.0F))))
                        .add(LootItem.lootTableItem(Items.BONE).setWeight(5)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))))
                        .add(LootItem.lootTableItem(Items.BONE_MEAL).setWeight(5)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 4.0F))))
                        .add(LootItem.lootTableItem(DDItems.VOID_SOUL_TORCH.get()).setWeight(5))
                        .add(LootItem.lootTableItem(Items.STICK).setWeight(2))
                        .add(LootItem.lootTableItem(Items.COAL).setWeight(3))
                        .add(LootItem.lootTableItem(Items.GOLD_NUGGET).setWeight(5)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))))
                        .add(LootItem.lootTableItem(DDBlocks.VOID_SOUL_JAR.get().asItem()).setWeight(3))
                        .add(LootItem.lootTableItem(Items.BREAD).setWeight(4)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))))
                        .add(LootItem.lootTableItem(Items.GOLD_INGOT).setWeight(4))
                        .add(LootItem.lootTableItem(Items.PAPER).setWeight(2)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 4.0F))))
                        .add(LootItem.lootTableItem(Items.GLASS_BOTTLE).setWeight(2))
                        .add(LootItem.lootTableItem(Items.BOOK).setWeight(2))
                        .add(LootItem.lootTableItem(Items.BOWL).setWeight(2))
                        .add(LootItem.lootTableItem(DDBlocks.ARIDROCK.get().asItem()).setWeight(2)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 8.0F))))
                        .add(LootItem.lootTableItem(DDBlocks.DARKSLATE.get().asItem()).setWeight(2)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 8.0F))))
                        .add(LootItem.lootTableItem(DDBlocks.POROUS_PETRIFIED_LOG.get().asItem()).setWeight(2)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))))
                        .add(LootItem.lootTableItem(Items.BOOK).setWeight(5)
                                .apply(new EnchantRandomlyFunction.Builder()))
                );
        consumer.accept(CATACOMBS_CHEST_STANDARD, catacombs_chest_standard);

        LootTable.Builder catacombs_chest_treasure = LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(UniformGenerator.between(5.0F, 10.0F))
                        .add(LootItem.lootTableItem(DDItems.AMBER.get()).setWeight(5)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 4.0F))))
                        .add(LootItem.lootTableItem(DDItems.ROPE.get()).setWeight(3)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 8.0F))))
                        .add(LootItem.lootTableItem(Items.IRON_INGOT).setWeight(8)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 5.0F))))
                        .add(LootItem.lootTableItem(Items.GOLD_INGOT).setWeight(5))
                        .add(LootItem.lootTableItem(DDItems.VOID_SOUL_TORCH.get()).setWeight(3))
                        .add(LootItem.lootTableItem(Items.GOLD_NUGGET).setWeight(3)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(3.0F, 7.0F))))
                        .add(LootItem.lootTableItem(DDBlocks.VOID_SOUL_JAR.get().asItem()).setWeight(3))
                        .add(LootItem.lootTableItem(Items.PAPER).setWeight(3)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 5.0F))))
                        .add(LootItem.lootTableItem(Items.BOOK).setWeight(3))
                        .add(LootItem.lootTableItem(Items.DIAMOND).setWeight(4)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))))
                        .add(LootItem.lootTableItem(DDItems.FORSAKEN_BRONZE_SCRAP.get()).setWeight(2))
                        .add(LootItem.lootTableItem(DDItems.VOID_SOUL_REQUIEM.get()).setWeight(4))
                        .add(LootItem.lootTableItem(DDBlocks.CRYSTAL_MELON.get().asItem()).setWeight(5))
                        .add(LootItem.lootTableItem(Items.BOOK).setWeight(8)
                                .apply(new EnchantRandomlyFunction.Builder()))
                );
        consumer.accept(CATACOMBS_CHEST_TREASURE, catacombs_chest_treasure);
    }

}