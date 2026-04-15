package com.naterbobber.darkerdepths.init;

import com.naterbobber.darkerdepths.DarkerDepths;
import com.naterbobber.darkerdepths.compat.CompatID;
import com.naterbobber.darkerdepths.util.DDResourceKeys;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class DDCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, DarkerDepths.MOD_ID);
    private static final Map<DeferredBlock<? extends Block>, List<DeferredHolder<Item, ? extends Item>>> ITEM_PLACEMENT_MODIFIER_MAP = Map.of(
            DDBlocks.PETRIFIED_BUTTON, List.of(
                    DDItems.PETRIFIED_SIGN,
                    DDItems.PETRIFIED_HANGING_SIGN,
                    DDItems.PETRIFIED_BOAT,
                    DDItems.PETRIFIED_CHEST_BOAT,
                    DDItems.VOID_SOUL_TORCH
            ),
            DDBlocks.FORSAKEN_BRONZE_BLOCK, List.of(
                    DDItems.FORSAKEN_BRONZE_INGOT,
                    DDItems.FORSAKEN_BRONZE_SCRAP,
                    DDItems.STILETTO
            ),
            DDBlocks.DEATH_ANCHOR, List.of(
                    DDItems.PARANOIA_ALTAR,
                    DDItems.VOID_SOUL_JAR
            ),
            DDBlocks.AMBER_CLUSTER, List.of(
                    DDItems.AMBER,
                    DDItems.VOID_SOUL_REQUIEM,
                    DDItems.BODY_SNATCHER_SPAWN_EGG,
                    DDItems.VOID_SOUL_KNIGHT_SPAWN_EGG,
                    DDItems.VOID_SOUL_SPAWN_EGG
            ),
            DDBlocks.GLOWSHROOM_LANTERN, List.of(
                    DDItems.GLOWSHROOM_CAP,
                    DDItems.GLOW_GRIME,
                    DDItems.GLOWSHROOM_MONSTER_SPAWN_EGG
            ),
            DDBlocks.GEYSER, List.of(
                    DDItems.MAGMA_PAD,
                    DDItems.SCORCHER_SPAWN_EGG
            )
    );

    private static final Set<DeferredBlock<? extends Block>> HIDDEN_BLOCKS = Set.of(
            DDBlocks.ASH,
            DDBlocks.ASH_BLOCK
    );

    private static final Map<String, DeferredBlock> ITEM_PLACEMENT_MODIFIER_MAP_IDS = ITEM_PLACEMENT_MODIFIER_MAP
            .entrySet()
            .stream()
            .collect(Collectors.toMap(
                    entry -> entry.getKey().getId().getPath(),
                    Map.Entry::getKey
            ));

    private static final Map<String, Holder> HIDDEN_BLOCK_MAP_IDS = HIDDEN_BLOCKS
            .stream()
            .collect(Collectors.toMap(
                    entry -> entry.getId().getPath(),
                    DeferredBlock::getDelegate
            ));

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> DARKER_DEPTHS = CREATIVE_MODE_TABS.register(DarkerDepths.MOD_ID, () -> {
        var ddCreativeTabBuilder =  CreativeModeTab.builder()
                .title(Component.translatable("itemGroup." + DarkerDepths.MOD_ID + ".creative_tab"))
                .icon(() -> new ItemStack(DDItems.GLOWSHROOM_CAP.get()));

        ddCreativeTabBuilder.displayItems((itemDisplayParameters, output) -> {
            for (DeferredHolder<Item, ? extends Item> item : DDItems.ITEMS.getEntries()) {
                if (DDBlocks.COMPAT.containsKey(item) && DDBlocks.COMPAT.get(item).stream().noneMatch(CompatID::isLoaded)) {
                    continue;
                }

                if(ITEM_PLACEMENT_MODIFIER_MAP
                        .values()
                        .stream()
                        .anyMatch(list -> list.contains(item))) {
                    continue;
                }

                insertItem(item, output);


            }

            itemDisplayParameters
                    .holders()
                    .lookup(Registries.ENCHANTMENT)
                    .ifPresent((registryLookup) -> {
                generateEnchantmentBookTypesOnlyMaxLevel(output, registryLookup, CreativeModeTab.TabVisibility.PARENT_TAB_ONLY);
            });
        });

        return ddCreativeTabBuilder.build();
    });


    private static void insertItem(DeferredHolder<Item, ? extends Item> item, CreativeModeTab.Output output) {
        String blockItemId = item.getId().getPath();

        if(ITEM_PLACEMENT_MODIFIER_MAP_IDS.containsKey(blockItemId)) {
            List<DeferredHolder<Item, ? extends Item>> itemsAfter = ITEM_PLACEMENT_MODIFIER_MAP.get(ITEM_PLACEMENT_MODIFIER_MAP_IDS.get(blockItemId));

            output.accept(item.get());

            itemsAfter.forEach(itemRegistryObject -> output.accept(itemRegistryObject.get()));

        } else if(HIDDEN_BLOCK_MAP_IDS.containsKey(blockItemId)) {

        }
        else {
            output.accept(item.get());
        }
    }

    private static void generateEnchantmentBookTypesOnlyMaxLevel(CreativeModeTab.Output output, HolderLookup<Enchantment> enchantments, CreativeModeTab.TabVisibility tabVisibility) {
        enchantments.listElements()
                .filter(enchantmentReference -> DDResourceKeys.Enchantments.ENCHANTMENTS.contains(enchantmentReference.getKey()))
                .map((enchantmentHolder) ->
                        EnchantedBookItem.createForEnchantment(new EnchantmentInstance(enchantmentHolder, (enchantmentHolder.value()).getMaxLevel()))
                )
                .forEach((enchantment) -> output.accept(enchantment, tabVisibility));
    }
}

