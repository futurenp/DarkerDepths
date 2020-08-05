package com.naterbobber.darkerdepths.util;


import com.naterbobber.darkerdepths.DarkerDepths;
import com.naterbobber.darkerdepths.client.entity.render.GlowshroomMonsterRenderer;
import com.naterbobber.darkerdepths.client.entity.render.MagmaMinionRenderer;
import com.naterbobber.darkerdepths.init.EntityTypesInit;
import net.minecraft.client.tutorial.Tutorial;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = DarkerDepths.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEventBusSubscriber {

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        RenderingRegistry.registerEntityRenderingHandler(EntityTypesInit.GLOWSHROOM_MONSTER.get(), GlowshroomMonsterRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityTypesInit.MAGMA_MINION.get(), MagmaMinionRenderer::new);
    }
}