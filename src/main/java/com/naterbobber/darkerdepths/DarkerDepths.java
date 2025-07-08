package com.naterbobber.darkerdepths;

import com.naterbobber.darkerdepths.config.DDConfigs;
import com.naterbobber.darkerdepths.events.MiscEvents;
import com.naterbobber.darkerdepths.events.MobEvents;
import com.naterbobber.darkerdepths.init.DDBlockEntityTypes;
import com.naterbobber.darkerdepths.init.DDBlocks;
import com.naterbobber.darkerdepths.init.DDCreativeModeTabs;
import com.naterbobber.darkerdepths.init.DDEnchantments;
import com.naterbobber.darkerdepths.init.DDEntityTypes;
import com.naterbobber.darkerdepths.init.DDFeatures;
import com.naterbobber.darkerdepths.init.DDItems;
import com.naterbobber.darkerdepths.init.DDMobEffects;
import com.naterbobber.darkerdepths.init.DDParticleTypes;
import com.naterbobber.darkerdepths.init.DDPoiTypes;
import com.naterbobber.darkerdepths.init.DDSoundEvents;
import com.naterbobber.darkerdepths.init.DDVanillaIntegration;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// TODO: Add recipe and loot table for glowshroom lantern and lamp
@Mod(DarkerDepths.MODID)
public class DarkerDepths {

    public static final Logger LOGGER = LogManager.getLogger();
    public static final String MODID = "darkerdepths";

    public DarkerDepths(FMLJavaModLoadingContext context) {
        context.registerConfig(ModConfig.Type.COMMON, DDConfigs.SPEC, "darkerdepths-common.toml");
        IEventBus modEventBus = context.getModEventBus();
        IEventBus eventBus = MinecraftForge.EVENT_BUS;
        modEventBus.addListener(this::commonSetup);

        DDBlocks.BLOCKS.register(modEventBus);
        DDBlockEntityTypes.BLOCK_ENTITIES.register(modEventBus);
        DDCreativeModeTabs.CREATIVE_MODE_TABS.register(modEventBus);
        DDEntityTypes.ENTITY_TYPES.register(modEventBus);
        DDEnchantments.ENCHANTMENTS.register(modEventBus);
        DDFeatures.FEATURES.register(modEventBus);
        DDItems.ITEMS.register(modEventBus);
        DDPoiTypes.POI_TYPES.register(modEventBus);
        DDParticleTypes.PARTICLE_TYPES.register(modEventBus);
        DDMobEffects.MOB_EFFECTS.register(modEventBus);
        DDSoundEvents.SOUND_EVENTS.register(modEventBus);

        eventBus.register(this);
        eventBus.register(new MobEvents());
        eventBus.register(new MiscEvents());
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(DDVanillaIntegration::init);
    }

    public static ResourceLocation id(String name) {
        return ResourceLocation.fromNamespaceAndPath(MODID, name);
    }

}
