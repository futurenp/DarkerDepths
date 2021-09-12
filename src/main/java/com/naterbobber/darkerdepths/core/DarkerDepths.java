package com.naterbobber.darkerdepths.core;

import com.naterbobber.darkerdepths.client.compat.DDCompatibilty;
import com.naterbobber.darkerdepths.client.render.RendererManager;
import com.naterbobber.darkerdepths.common.entities.GlowshroomMonsterEntity;
import com.naterbobber.darkerdepths.common.entities.MagmaMinionEntity;
import com.naterbobber.darkerdepths.common.events.DynamicLightHandler;
import com.naterbobber.darkerdepths.common.world.gen.GlobalBiomeFeatures;
import com.naterbobber.darkerdepths.core.api.Registries;
import com.naterbobber.darkerdepths.core.registries.DDEntitiesSpawnPlacements;
import com.naterbobber.darkerdepths.core.registries.DDEntityTypes;
import com.naterbobber.darkerdepths.core.registries.DDLootModifiers;
import com.naterbobber.darkerdepths.core.registries.DDWoodTypes;
import com.naterbobber.darkerdepths.core.registries.VanillaIntegrationRegistry;
import com.naterbobber.darkerdepths.core.util.DarkerDepthsItemGroup;
import net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DeferredWorkQueue;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

//<>

@Mod(value = DarkerDepths.MODID)
@Mod.EventBusSubscriber(modid = DarkerDepths.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DarkerDepths {
    public static final Logger LOGGER = LogManager.getLogger();
    public static final String MODID = "darkerdepths";
    public static final ItemGroup DARKER_DEPTHS = new DarkerDepthsItemGroup("DarkerDepths");
    public static final Registries REGISTRIES = new Registries();

    public DarkerDepths() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        MinecraftForge.EVENT_BUS.register(this);

        MinecraftForge.EVENT_BUS.register(new GlobalBiomeFeatures());

        REGISTRIES.initializeRegistries(bus);
        DDLootModifiers.LOOT_MODIFIERS.register(bus);

        bus.addListener(this::commonSetup);
        bus.addListener(this::clientSetup);

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, DarkerDepthsConfig.common);
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        DeferredWorkQueue.runLater(() -> {
            GlobalEntityTypeAttributes.put(DDEntityTypes.GLOWSHROOM_MONSTER.get(), GlowshroomMonsterEntity.setCustomAttributes().create());
            GlobalEntityTypeAttributes.put(DDEntityTypes.MAGMA_MINION.get(), MagmaMinionEntity.setCustomAttributes().create());
        });

        CraftingHelper.register(new DDCompatibilty.Serializer());

        DDEntitiesSpawnPlacements.register();
        VanillaIntegrationRegistry.setup();
        CaveBiomeImplementation.addCaveBiomes();
    }

    private void clientSetup(FMLClientSetupEvent event) {

        RendererManager.onClientSetup(event);

        event.enqueueWork(DDWoodTypes::initializeAtlas);
        MinecraftForge.EVENT_BUS.addListener((LivingEvent.LivingUpdateEvent livingEvent) -> DynamicLightHandler.tick(livingEvent.getEntityLiving()));
    }
}
