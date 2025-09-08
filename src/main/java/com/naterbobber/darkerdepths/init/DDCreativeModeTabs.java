package com.naterbobber.darkerdepths.init;

import com.naterbobber.darkerdepths.DarkerDepths;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class DDCreativeModeTabs {

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, DarkerDepths.MOD_ID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> DARKER_DEPTHS_TAB = CREATIVE_MODE_TABS.register("darker_depths_tab", () ->
            CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.darkerdepths.creative_tab"))
                    .icon(() -> new ItemStack(DDItems.GLOWSHROOM_CAP.get()))
                    .displayItems((itemDisplayParameters, output) -> {
                        for (DeferredHolder<Item, ? extends Item> item : DDItems.ITEMS.getEntries()) {
                            output.accept(item.get());
                        }

                        for (DeferredHolder<Item, ? extends Item> item : DDBlocks.COMPAT.keySet()) {
                            String modId = DDBlocks.COMPAT.get(item);
                            if (ModList.get().isLoaded(modId)) {
                                output.accept(item.get());
                            }
                        }
                    })
                    .build()
    );
}

