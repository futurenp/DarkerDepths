package com.naterbobber.darkerdepths;

import com.naterbobber.darkerdepths.entities.GlowshroomMonsterEntity;
import com.naterbobber.darkerdepths.events.MobEvents;
import com.naterbobber.darkerdepths.init.DDBiomeModifiers;
import com.naterbobber.darkerdepths.init.DDBiomes;
import com.naterbobber.darkerdepths.init.DDBlockEntities;
import com.naterbobber.darkerdepths.init.DDBlocks;
import com.naterbobber.darkerdepths.init.DDConfiguredFeatures;
import com.naterbobber.darkerdepths.init.DDEntityTypes;
import com.naterbobber.darkerdepths.init.DDFeatures;
import com.naterbobber.darkerdepths.init.DDItems;
import com.naterbobber.darkerdepths.init.DDParticleTypes;
import com.naterbobber.darkerdepths.init.DDPlacedFeatures;
import com.naterbobber.darkerdepths.init.DDSoundEvents;
import com.naterbobber.darkerdepths.init.DDVanillaIntegration;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.levelgen.Heightmap;
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
    public static final CreativeModeTab DARKER_DEPTHS = new CreativeModeTab("DarkerDepths") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(DDItems.GLOWSHROOM_CAP.get());
        }
    };

    public DarkerDepths() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::setup);

        DDBlocks.BLOCKS.register(modEventBus);
        DDBiomeModifiers.BIOME_MODIFIERS.register(modEventBus);
        DDItems.ITEMS.register(modEventBus);
        DDBlockEntities.BLOCK_ENTITIES.register(modEventBus);
        DDParticleTypes.PARTICLE_TYPES.register(modEventBus);
        DDSoundEvents.SOUND_EVENTS.register(modEventBus);
        DDEntityTypes.ENTITY_TYPES.register(modEventBus);
        DDFeatures.FEATURES.register(modEventBus);
        DDBiomes.BIOMES.register(modEventBus);

        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(new MobEvents());
    }

    private void setup(final FMLCommonSetupEvent event) {
        DDVanillaIntegration.init();
        event.enqueueWork(() -> {
            DDConfiguredFeatures.init();
            DDPlacedFeatures.init();

            SpawnPlacements.register(DDEntityTypes.GLOWSHROOM_MONSTER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, GlowshroomMonsterEntity::canSpawn);
        });
    }

}
