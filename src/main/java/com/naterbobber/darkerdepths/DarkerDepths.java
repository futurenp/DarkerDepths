package com.naterbobber.darkerdepths;

import com.naterbobber.darkerdepths.events.MiscEvents;
import com.naterbobber.darkerdepths.events.MobEvents;
import com.naterbobber.darkerdepths.events.WorldEvents;
import com.naterbobber.darkerdepths.init.DDBiomes;
import com.naterbobber.darkerdepths.init.DDBlockEntities;
import com.naterbobber.darkerdepths.init.DDBlocks;
import com.naterbobber.darkerdepths.init.DDEntityTypes;
import com.naterbobber.darkerdepths.init.DDFeatures;
import com.naterbobber.darkerdepths.init.DDItems;
import com.naterbobber.darkerdepths.init.DDParticleTypes;
import com.naterbobber.darkerdepths.init.DDSoundEvents;
import com.naterbobber.darkerdepths.init.DDVanillaIntegration;
import com.naterbobber.darkerdepths.util.BiomeReagentHandler;
import com.naterbobber.darkerdepths.util.DarkerDepthsTab;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.BiomeDictionary;
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
        DDItems.ITEMS.register(modEventBus);
        DDBlockEntities.BLOCK_ENTITIES.register(modEventBus);
        DDParticleTypes.PARTICLE_TYPES.register(modEventBus);
        DDSoundEvents.SOUND_EVENTS.register(modEventBus);
        DDEntityTypes.ENTITY_TYPES.register(modEventBus);
        DDFeatures.FEATURES.register(modEventBus);
        DDBiomes.BIOMES.register(modEventBus);

        eventBus.register(this);
        eventBus.register(new WorldEvents());
        eventBus.register(new MobEvents());
        eventBus.register(new MiscEvents());
    }

    private void setup(final FMLCommonSetupEvent event) {
        DDVanillaIntegration.init();
        event.enqueueWork(() -> {
            BiomeDictionary.addTypes(BiomeReagentHandler.MOLTEN_CAVERN, BiomeDictionary.Type.OVERWORLD, BiomeDictionary.Type.UNDERGROUND);
            BiomeDictionary.addTypes(BiomeReagentHandler.SANDY_CATACOMBS, BiomeDictionary.Type.OVERWORLD, BiomeDictionary.Type.UNDERGROUND);
            BiomeDictionary.addTypes(BiomeReagentHandler.GLOWSHROOM_FOREST, BiomeDictionary.Type.OVERWORLD, BiomeDictionary.Type.UNDERGROUND);
        });
    }

}
