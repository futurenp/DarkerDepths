package com.naterbobber.darkerdepths.core.util;

import com.naterbobber.darkerdepths.client.entity.render.GlowshroomMonsterRenderer;
import com.naterbobber.darkerdepths.client.entity.render.MagmaMinionRenderer;
import com.naterbobber.darkerdepths.client.entity.render.PetrifiedBoatRenderer;
import com.naterbobber.darkerdepths.common.entities.PetrifiedBoatEntity;
import com.naterbobber.darkerdepths.core.DarkerDepths;
import com.naterbobber.darkerdepths.core.registries.DDEntityTypes;

import net.minecraft.entity.EntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = DarkerDepths.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEventBusSubscriber {

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        RenderingRegistry.registerEntityRenderingHandler(DDEntityTypes.GLOWSHROOM_MONSTER.get(), GlowshroomMonsterRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(DDEntityTypes.MAGMA_MINION.get(), MagmaMinionRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler((EntityType<? extends PetrifiedBoatEntity>) DDEntityTypes.BOAT.get(), PetrifiedBoatRenderer::new);
    }
}