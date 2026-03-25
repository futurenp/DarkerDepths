package com.naterbobber.darkerdepths;

import com.naterbobber.darkerdepths.compat.DDCompat;
import com.naterbobber.darkerdepths.init.DDEnchantmentEffects;
import com.naterbobber.darkerdepths.config.DDConfig;
import com.naterbobber.darkerdepths.events.RegisterEvents;
import com.naterbobber.darkerdepths.init.*;
import com.naterbobber.darkerdepths.network.DDNetwork;
import com.naterbobber.darkerdepths.init.DDFeatures;
import com.naterbobber.darkerdepths.init.DDStructureProcessorTypes;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(DarkerDepths.MOD_ID)
public class DarkerDepths {

    public static final Logger LOGGER = LogManager.getLogger();
    public static final String MOD_ID = "darkerdepths";

    public DarkerDepths(IEventBus modEventBus, ModContainer container) {
        container.registerConfig(ModConfig.Type.COMMON, DDConfig.CONFIG_SPEC);

        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::clientSetup);

        modEventBus.register(DDNetwork.class);
        modEventBus.register(RegisterEvents.class);

        DDArmorMaterials.ARMOR_MATERIALS.register(modEventBus);
        DDBlocks.BLOCKS.register(modEventBus);
        DDItems.ITEMS.register(modEventBus);
        DDCreativeModeTabs.CREATIVE_MODE_TABS.register(modEventBus);
        DDBlockEntityTypes.BLOCK_ENTITIES.register(modEventBus);
        DDEntityTypes.ENTITY_TYPES.register(modEventBus);
        DDEnchantmentEffects.DATA_COMPONENTS.register(modEventBus);
        DDFeatures.FEATURES.register(modEventBus);
        DDPoiTypes.POI_TYPES.register(modEventBus);
        DDParticleTypes.PARTICLE_TYPES.register(modEventBus);
        DDMobEffects.MOB_EFFECTS.register(modEventBus);
        DDSoundEvents.SOUND_EVENTS.register(modEventBus);
        DDStructureTypes.STRUCTURE_TYPES.register(modEventBus);
        DDStructureProcessorTypes.STRUCTURE_PROCESSORS.register(modEventBus);
        DDActivities.ACTIVITIES.register(modEventBus);
        DDMemoryModuleTypes.MEMORY_MODULE_TYPES.register(modEventBus);
        DDDataComponents.DATA_COMPONENTS.register(modEventBus);
        DDCriteria.TRIGGERS.register(modEventBus);
        DDWorldCarvers.WORLD_CARVERS.register(modEventBus);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(DDVanillaIntegration::init);

        boolean hasBiolith = ModList.get().isLoaded(DDCompat.BIOLITH.toString());
        boolean hasTerrablender = ModList.get().isLoaded(DDCompat.TERRABLENDER.toString());

        if (!hasBiolith && !hasTerrablender) {
            throw new IllegalStateException("Missing Worldgen API! Install TerraBlender or Biolith.");
        }

        if (DDConfig.CONFIG.PRIORITIZE_BIOLITH.get()) {
            if (hasBiolith) {
                safeInitBiolith(event);
            } else {
                safeInitTerrablender(event);
            }
        } else {
            if (hasTerrablender) {
                safeInitTerrablender(event);
            } else {
                safeInitBiolith(event);
            }
        }
    }

    private void safeInitBiolith(FMLCommonSetupEvent event) {
        event.enqueueWork(DDBiolithIntegration::init);
    }

    private void safeInitTerrablender(FMLCommonSetupEvent event) {
        event.enqueueWork(DDTerrablenderIntegration::init);
    }

    private void clientSetup(final FMLClientSetupEvent event) {
    }

    public static ResourceLocation id(String name) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, name);
    }
}