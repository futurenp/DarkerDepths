package com.naterbobber.darkerdepths.core;

import com.naterbobber.darkerdepths.client.render.BlockRenderHandler;
import com.naterbobber.darkerdepths.common.entities.GlowshroomMonsterEntity;
import com.naterbobber.darkerdepths.common.entities.MagmaMinionEntity;
import com.naterbobber.darkerdepths.common.world.gen.VanillaBiomeFeatures;
import com.naterbobber.darkerdepths.core.init.EntityTypesInit;
import com.naterbobber.darkerdepths.core.registries.DDBiomes;
import com.naterbobber.darkerdepths.core.registries.VanillaIntegrationRegistry;
import com.naterbobber.darkerdepths.core.util.DarkerDepthsItemGroup;

import net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DeferredWorkQueue;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@SuppressWarnings("deprecation")

//<>

@Mod("darkerdepths")
public class DarkerDepths {
    public static DarkerDepths INSTANCE;
    public static final String MODID = "darkerdepths";
    public static final Logger LOGGER = LogManager.getLogger(MODID);
    public static final ItemGroup DARKER_DEPTHS = new DarkerDepthsItemGroup("DarkerDepths");
    public static final RegistryHelper REGISTRY_HELPER = new RegistryHelper(MODID);
    
    public DarkerDepths() {
        INSTANCE = this;

        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        
        REGISTRY_HELPER.getBlockRegister().register(modEventBus);
        REGISTRY_HELPER.getItemRegister().register(modEventBus);
        REGISTRY_HELPER.getFeatureRegister().register(modEventBus);
        
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);

        EntityTypesInit.ENTITY_TYPES.register(FMLJavaModLoadingContext.get().getModEventBus());
        DDBiomes.BIOMES.register(modEventBus);

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event) {
    	VanillaBiomeFeatures.addVanillaBiomeFeatures();
        DDBiomes.applyBiomeFeatures();
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