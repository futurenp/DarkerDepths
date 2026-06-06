package com.naterbobber.darkerdepths.client.events;

import com.naterbobber.darkerdepths.DarkerDepths;
import com.naterbobber.darkerdepths.client.fog.FogManager;
import com.naterbobber.darkerdepths.client.fog.modifiers.BiomeFogModifier;
import com.naterbobber.darkerdepths.client.fog.modifiers.EffectFogModifier;
import com.naterbobber.darkerdepths.client.fog.modifiers.ScorcherFlashModifier;
import com.naterbobber.darkerdepths.client.render.EmissiveModelManager;
import com.naterbobber.darkerdepths.init.DDDataComponents;
import com.naterbobber.darkerdepths.init.DDItems;
import com.naterbobber.darkerdepths.init.DDWoodType;
import net.minecraft.client.renderer.item.ItemProperties;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.ModelEvent;
import net.neoforged.neoforge.common.NeoForge;

@OnlyIn(Dist.CLIENT)
@EventBusSubscriber(modid = DarkerDepths.MOD_ID, value = Dist.CLIENT)
public class ClientSetupEvents {

    @SubscribeEvent
    public static void onClientSetup(final FMLClientSetupEvent event) {
        IEventBus eventBus = NeoForge.EVENT_BUS;

        eventBus.register(new ClientEvents());
        FogManager.register(new BiomeFogModifier());
        FogManager.register(new EffectFogModifier());
        FogManager.register(new ScorcherFlashModifier());

        event.enqueueWork(() -> {
            DDWoodType.setupWoodTypes();
            registerStilettoProperties();
        });
    }

    private static void registerStilettoProperties() {
        ItemProperties.register(DDItems.STILETTO.get(), DarkerDepths.id("charge"), (itemStack, clientLevel, livingEntity, i) -> {
            int time = itemStack.getOrDefault(DDDataComponents.STILETTO_TIME, 0);
            int readyTime = itemStack.getOrDefault(DDDataComponents.STILETTO_READY_TIME, 0);

            if (time > 0) {
                return 1.0F;
            }
            if (readyTime > 0) {
                return 0.5F;
            }

            return 0.0F;
        });
    }

    @SubscribeEvent
    public static void onModifyBakingResult(ModelEvent.ModifyBakingResult event) {
        EmissiveModelManager.modify(event);
    }
}
