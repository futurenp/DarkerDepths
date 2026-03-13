package com.naterbobber.darkerdepths.init;

import com.naterbobber.darkerdepths.DarkerDepths;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.List;
import java.util.Map;

public class DDCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, DarkerDepths.MOD_ID);
    private static final Map<String, List<DeferredHolder<Item, ? extends Item>>> itemPlacementModifierMap = Map.of(
            "petrified_button", List.of(
                    DDItems.PETRIFIED_SIGN,
                    DDItems.PETRIFIED_HANGING_SIGN,
                    DDItems.PETRIFIED_BOAT,
                    DDItems.PETRIFIED_CHEST_BOAT,
                    DDItems.VOID_SOUL_TORCH
            ),
            "forsaken_bronze_block", List.of(
                    DDItems.FORSAKEN_BRONZE_INGOT,
                    DDItems.FORSAKEN_BRONZE_SCRAP,
                    DDItems.STILETTO
            ),
            "death_anchor", List.of(
                    DDItems.PARANOIA_ALTAR,
                    DDItems.VOID_SOUL_JAR
            ),
            "amber_cluster", List.of(
                    DDItems.AMBER,
                    DDItems.VOID_SOUL_REQUIEM,
                    DDItems.BODY_SNATCHER_SPAWN_EGG,
                    DDItems.VOID_SOUL_KNIGHT_SPAWN_EGG,
                    DDItems.VOID_SOUL_SPAWN_EGG
            ),
            "glowshroom_lantern", List.of(
                    DDItems.GLOWSHROOM_CAP,
                    DDItems.GLOW_GRIME,
                    DDItems.GLOWSHROOM_MONSTER_SPAWN_EGG
            ),
            "geyser", List.of(
                    DDItems.MAGMA_PAD,
                    DDItems.SCORCHER_SPAWN_EGG
            )
    );

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> DARKER_DEPTHS = CREATIVE_MODE_TABS.register("darker_depths", () -> {
        return CreativeModeTab.builder()
                .title(Component.translatable("itemGroup.darkerdepths.creative_tab"))
                .icon(() -> new ItemStack(DDItems.GLOWSHROOM_CAP.get()))
                .displayItems((itemDisplayParameters, output) -> {
                    for (DeferredHolder<Item, ? extends Item> item : DDItems.ITEMS.getEntries()) {
                        if (DDBlocks.COMPAT.containsKey(item) && DDBlocks.COMPAT.get(item).stream().noneMatch(modID -> ModList.get().isLoaded(modID.toString()))) {
                            continue;
                        }

                        if(itemPlacementModifierMap.values().stream().anyMatch(list -> list.contains(item))) {
                            continue;
                        }

                        insertItem(item, output);
                    }
                })
                .build();
    });


    private static void insertItem(DeferredHolder<Item, ? extends Item> item, CreativeModeTab.Output output) {
        String blockItemId = item.getId().getPath();

        if(itemPlacementModifierMap.containsKey(blockItemId)){
            List<DeferredHolder<Item, ? extends Item>> itemsAfter = itemPlacementModifierMap.get(blockItemId);

            output.accept(item.get());

            itemsAfter.forEach(itemRegistryObject -> output.accept(itemRegistryObject.get()));

        } else {
            output.accept(item.get());
        }
    }
}

