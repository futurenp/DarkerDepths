package com.naterbobber.darkerdepths;

import com.naterbobber.darkerdepths.events.MiscEvents;
import com.naterbobber.darkerdepths.events.MobEvents;
import com.naterbobber.darkerdepths.init.DDBiomeModifiers;
import com.naterbobber.darkerdepths.init.DDBiomes;
import com.naterbobber.darkerdepths.init.DDBlockEntityTypes;
import com.naterbobber.darkerdepths.init.DDBlocks;
import com.naterbobber.darkerdepths.init.DDConfiguredFeatures;
import com.naterbobber.darkerdepths.init.DDEntityTypes;
import com.naterbobber.darkerdepths.init.DDFeatures;
import com.naterbobber.darkerdepths.init.DDItems;
import com.naterbobber.darkerdepths.init.DDParticleTypes;
import com.naterbobber.darkerdepths.init.DDPlacedFeatures;
import com.naterbobber.darkerdepths.init.DDSoundEvents;
import com.naterbobber.darkerdepths.init.DDVanillaIntegration;
import com.naterbobber.darkerdepths.util.DarkerDepthsTab;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(DarkerDepths.MODID)
public class DarkerDepths {

    public static final Logger LOGGER = LogManager.getLogger();
    public static final String MODID = "darkerdepths";
    public static final CreativeModeTab DARKER_DEPTHS = new DarkerDepthsTab();

    public DarkerDepths() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        IEventBus eventBus = MinecraftForge.EVENT_BUS;
        modEventBus.addListener(this::setup);

        DDBlocks.BLOCKS.register(modEventBus);
        DDBiomes.BIOMES.register(modEventBus);
        DDBiomeModifiers.BIOME_MODIFIERS.register(modEventBus);
        DDBlockEntityTypes.BLOCK_ENTITIES.register(modEventBus);
        DDEntityTypes.ENTITY_TYPES.register(modEventBus);
        DDFeatures.FEATURES.register(modEventBus);
        DDItems.ITEMS.register(modEventBus);
        DDParticleTypes.PARTICLE_TYPES.register(modEventBus);
        DDSoundEvents.SOUND_EVENTS.register(modEventBus);

        eventBus.register(this);
        eventBus.register(new MobEvents());
        eventBus.register(new MiscEvents());
    }

    private void setup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            DDVanillaIntegration.init();
            DDConfiguredFeatures.init();
            DDPlacedFeatures.init();

        });
    }

}
