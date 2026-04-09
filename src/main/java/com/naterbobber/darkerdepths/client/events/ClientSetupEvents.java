package com.naterbobber.darkerdepths.client.events;

import com.naterbobber.darkerdepths.DarkerDepths;
import com.naterbobber.darkerdepths.client.ClientDeathAnchorAnimationOverlay;
import com.naterbobber.darkerdepths.init.DDItems;
import com.naterbobber.darkerdepths.init.DDWoodType;
import com.naterbobber.darkerdepths.item.StilettoItem;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = DarkerDepths.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientSetupEvents {

    @SubscribeEvent
    public static void onClientSetup(final FMLClientSetupEvent event) {
        IEventBus eventBus = MinecraftForge.EVENT_BUS;

        eventBus.register(new ClientForgeEvents());
        eventBus.register(new ClientDeathAnchorAnimationOverlay());

        event.enqueueWork(() -> {
            DDWoodType.setupWoodTypes();
            registerStilettoProperties();

        });
    }

    private static void registerStilettoProperties(){
        ItemProperties.register(DDItems.STILETTO.get(), DarkerDepths.id("charge"), (itemStack, clientLevel, livingEntity, i) -> {
            CompoundTag tag = itemStack.getTag();
            if (tag != null) {
                if (tag.getInt(StilettoItem.TIME_FRAME) > 0) {
                    return 1.0F;
                }
                if (tag.getInt(StilettoItem.READY_TICKS) > 0) {
                    return 0.5F;
                }
            }

            return 0.0F;
        });
    }
}
