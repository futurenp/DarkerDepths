package com.naterbobber.darkerdepths.init;

import com.naterbobber.darkerdepths.DarkerDepths;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = DarkerDepths.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DDCreativeModeTabs {

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, DarkerDepths.MODID);

    public static final RegistryObject<CreativeModeTab> DARKER_DEPTHS = CREATIVE_MODE_TABS.register("darker_depths", () -> {
        return CreativeModeTab.builder()
                .title(Component.translatable("itemGroup.darkerdepths.darkerdepths"))
                .icon(() -> new ItemStack(DDItems.GLOWSHROOM_CAP.get()))
                .displayItems((itemDisplayParameters, output) -> {
                    DDItems.ITEMS.getEntries().stream().map(RegistryObject::get).forEach(output::accept);
                })
                .build();
    });

}
