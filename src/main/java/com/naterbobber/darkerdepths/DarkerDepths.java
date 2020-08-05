package com.naterbobber.darkerdepths;

import com.naterbobber.darkerdepths.client.render.BlockRenderHandler;
import com.naterbobber.darkerdepths.entities.GlowshroomMonsterEntity;
import com.naterbobber.darkerdepths.entities.MagmaMinionEntity;
import com.naterbobber.darkerdepths.init.EntityTypesInit;
import com.naterbobber.darkerdepths.registry.VanillaIntegrationRegistry;
import net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DeferredWorkQueue;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod("darkerdepths")
public class DarkerDepths
{
    public static DarkerDepths INSTANCE;
    public static final String MODID = "darkerdepths";
    public static final Logger LOGGER = LogManager.getLogger(MODID);

    public DarkerDepths() {
        INSTANCE = this;

        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);

        EntityTypesInit.ENTITY_TYPES.register(FMLJavaModLoadingContext.get().getModEventBus());

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event) {
        DeferredWorkQueue.runLater(() -> {
            GlobalEntityTypeAttributes.put(EntityTypesInit.GLOWSHROOM_MONSTER.get(), GlowshroomMonsterEntity.setCustomAttributes().create());
            GlobalEntityTypeAttributes.put(EntityTypesInit.MAGMA_MINION.get(), MagmaMinionEntity.setCustomAttributes().create());
        });

        VanillaIntegrationRegistry.setup();

        LOGGER.info("Pre-initializing.");
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        BlockRenderHandler.blockRenders();
        LOGGER.info("Performed client-side tasks.");
    }

    @SubscribeEvent
    public void onServerStarting(FMLServerStartingEvent event) {
        LOGGER.info("Server starting.");
    }
}