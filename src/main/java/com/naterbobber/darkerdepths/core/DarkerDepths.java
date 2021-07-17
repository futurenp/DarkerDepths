package com.naterbobber.darkerdepths.core;

import com.naterbobber.darkerdepths.client.compat.DDCompatibilty;
import com.naterbobber.darkerdepths.client.render.RendererManager;
import com.naterbobber.darkerdepths.common.entities.GlowshroomMonsterEntity;
import com.naterbobber.darkerdepths.common.entities.MagmaMinionEntity;
import com.naterbobber.darkerdepths.common.events.DynamicLightHandler;
import com.naterbobber.darkerdepths.common.events.TickEvents;
import com.naterbobber.darkerdepths.common.world.gen.GlobalBiomeFeatures;
import com.naterbobber.darkerdepths.core.registries.DDEntityTypes;
import com.naterbobber.darkerdepths.core.registries.DDSoundEvents;
import com.naterbobber.darkerdepths.core.registries.DDSurfaceBuilders;
import com.naterbobber.darkerdepths.core.registries.DDTileEntities;
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
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

//<>

@Mod(value = DarkerDepths.MODID)
@Mod.EventBusSubscriber(modid = DarkerDepths.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DarkerDepths {
    public static DarkerDepths instance;
    public static final String MODID = "darkerdepths";
    public static final ItemGroup DARKER_DEPTHS = new DarkerDepthsItemGroup("DarkerDepths");
    public static final DDRegistries REGISTRIES = new DDRegistries(MODID);

    public DarkerDepths() {
        instance = this;
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        MinecraftForge.EVENT_BUS.register(this);

        MinecraftForge.EVENT_BUS.register(new GlobalBiomeFeatures());
        MinecraftForge.EVENT_BUS.register(new TickEvents());
        MinecraftForge.EVENT_BUS.addListener((LivingEvent.LivingUpdateEvent event) -> DynamicLightHandler.tick(event.getEntityLiving()));

        REGISTRIES.getItems().register(modEventBus);
        REGISTRIES.getBlocks().register(modEventBus);
        REGISTRIES.getCarvers().register(modEventBus);
        REGISTRIES.getFeatures().register(modEventBus);
        REGISTRIES.getBiomes().register(modEventBus);
        REGISTRIES.getParticleTypes().register(modEventBus);
        REGISTRIES.getPlacements().register(modEventBus);
        REGISTRIES.getTileEntities().register(modEventBus);
        REGISTRIES.getSurfaceBuilders().register(modEventBus);
        REGISTRIES.getSoundEvents().register(modEventBus);

        //TODO: CONVERT THIS INTO CORE REGISTRIES
        DDEntityTypes.ENTITY_TYPES.register(modEventBus);

        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::clientSetup);
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        DeferredWorkQueue.runLater(() -> {
            GlobalEntityTypeAttributes.put(DDEntityTypes.GLOWSHROOM_MONSTER.get(), GlowshroomMonsterEntity.setCustomAttributes().create());
            GlobalEntityTypeAttributes.put(DDEntityTypes.MAGMA_MINION.get(), MagmaMinionEntity.setCustomAttributes().create());
        });

        CraftingHelper.register(new DDCompatibilty.Serializer());

        VanillaIntegrationRegistry.setup();
        CaveBiomeImplementation.addCaveBiomes();
    }

    private void clientSetup(FMLClientSetupEvent event) {

        RendererManager.onClientSetup(event);

        event.enqueueWork(DDWoodTypes::initializeAtlas);
    }
}