package com.naterbobber.darkerdepths;

import com.naterbobber.darkerdepths.config.DDConfigs;
import com.naterbobber.darkerdepths.events.ClientDeathAnchorAnimationOverlay;
import com.naterbobber.darkerdepths.events.MobEvents;
import com.naterbobber.darkerdepths.events.RegisterEvents;
import com.naterbobber.darkerdepths.init.*;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(DarkerDepths.MOD_ID)
public class DarkerDepths {

    public static final Logger LOGGER = LogManager.getLogger();
    public static final String MOD_ID = "darkerdepths";

    public DarkerDepths(IEventBus modEventBus, ModContainer container) {
        container.registerConfig(ModConfig.Type.COMMON, DDConfigs.SPEC);

        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::clientSetup);

        DDCreativeModeTabs.CREATIVE_MODE_TABS.register(modEventBus);
        DDArmorMaterials.ARMOR_MATERIALS.register(modEventBus);
        DDBlocks.BLOCKS.register(modEventBus);
        DDItems.ITEMS.register(modEventBus);
        DDBlockEntityTypes.BLOCK_ENTITIES.register(modEventBus);
        DDEntityTypes.ENTITY_TYPES.register(modEventBus);
        DDFeatures.FEATURES.register(modEventBus);
        DDPoiTypes.POI_TYPES.register(modEventBus);
        DDParticleTypes.PARTICLE_TYPES.register(modEventBus);
        DDMobEffects.MOB_EFFECTS.register(modEventBus);
        DDSoundEvents.SOUND_EVENTS.register(modEventBus);
        DDStructures.STRUCTURE_TYPES.register(modEventBus);
        DDStructureProcessorTypes.STRUCTURE_PROCESSORS.register(modEventBus);
        DDActivities.ACTIVITIES.register(modEventBus);
        DDMemoryModuleTypes.MEMORY_MODULE_TYPES.register(modEventBus);
        DDDataComponents.DATA_COMPONENTS.register(modEventBus);

        //should be registered client only
        NeoForge.EVENT_BUS.register(new ClientDeathAnchorAnimationOverlay());
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(DDVanillaIntegration::init);
    }

    private void clientSetup(final FMLClientSetupEvent event) {
    }

    public static ResourceLocation id(String name) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, name);
    }
}