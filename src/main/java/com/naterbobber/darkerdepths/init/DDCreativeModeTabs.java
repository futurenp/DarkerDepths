package com.naterbobber.darkerdepths.init;

import com.naterbobber.darkerdepths.DarkerDepths;
import com.naterbobber.darkerdepths.compat.CompatID;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Mod.EventBusSubscriber(modid = DarkerDepths.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DDCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, DarkerDepths.MOD_ID);

    private static final Map<RegistryObject<? extends Block>, List<RegistryObject<? extends Item>>> ITEM_PLACEMENT_MODIFIER_MAP = Map.of(
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

    private static final Set<RegistryObject<? extends Block>> HIDDEN_BLOCKS = Set.of(
            DDBlocks.ASH,
            DDBlocks.ASH_BLOCK
    );

    private static final Map<String, RegistryObject<? extends Block>> ITEM_PLACEMENT_MODIFIER_MAP_IDS = ITEM_PLACEMENT_MODIFIER_MAP
            .entrySet()
            .stream()
            .collect(Collectors.toMap(
                    entry -> entry.getKey().getId().getPath(),
                    Map.Entry::getKey
            ));

    private static final Map<String, RegistryObject<? extends Block>> HIDDEN_BLOCK_MAP_IDS = HIDDEN_BLOCKS
            .stream()
            .collect(Collectors.toMap(
                    entry -> entry.getId().getPath(),
                    entry -> entry
            ));

    public static final RegistryObject<CreativeModeTab> DARKER_DEPTHS = CREATIVE_MODE_TABS.register("darker_depths", () -> {
        return CreativeModeTab.builder()
                .title(Component.translatable("itemGroup.darkerdepths.creative_tab"))
                .icon(() -> new ItemStack(DDItems.GLOWSHROOM_CAP.get()))
                .displayItems((itemDisplayParameters, output) -> {
                    for (RegistryObject<Item> item : DDItems.ITEMS.getEntries()) {

                        if (DDBlocks.COMPAT.containsKey(item) && DDBlocks.COMPAT.get(item).stream().noneMatch(CompatID::isLoaded)) {
                            continue;
                        }

                        if (ITEM_PLACEMENT_MODIFIER_MAP.values().stream().anyMatch(list -> list.contains(item))) {
                            continue;
                        }

                        insertItem(item, output);
                    }

                    DDEnchantments.ENCHANTMENTS.getEntries().forEach(
                            enchantment -> output.accept(EnchantedBookItem.createForEnchantment(
                                    new EnchantmentInstance(enchantment.get(), enchantment.get().getMaxLevel())))
                    );
                })
                .build();
    });

    private static void insertItem(RegistryObject<? extends Item> item, CreativeModeTab.Output output) {
        String blockItemId = item.getId().getPath();

        if (ITEM_PLACEMENT_MODIFIER_MAP_IDS.containsKey(blockItemId)) {
            List<RegistryObject<? extends Item>> itemsAfter = ITEM_PLACEMENT_MODIFIER_MAP.get(ITEM_PLACEMENT_MODIFIER_MAP_IDS.get(blockItemId));

            output.accept(item.get());

            itemsAfter.forEach(itemRegistryObject -> output.accept(itemRegistryObject.get()));

        } else if (!HIDDEN_BLOCK_MAP_IDS.containsKey(blockItemId)) {
            output.accept(item.get());
        }
    }
}