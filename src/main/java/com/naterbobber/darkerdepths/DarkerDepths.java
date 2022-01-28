package com.naterbobber.darkerdepths;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMultimap;
import com.mojang.serialization.Codec;
import com.naterbobber.darkerdepths.events.MobEvents;
import com.naterbobber.darkerdepths.events.WorldEvents;
import com.naterbobber.darkerdepths.init.DDBiomes;
import com.naterbobber.darkerdepths.init.DDBlockEntities;
import com.naterbobber.darkerdepths.init.DDBlocks;
import com.naterbobber.darkerdepths.init.DDConfiguredStructures;
import com.naterbobber.darkerdepths.init.DDEntityTypes;
import com.naterbobber.darkerdepths.init.DDFeatures;
import com.naterbobber.darkerdepths.init.DDItems;
import com.naterbobber.darkerdepths.init.DDParticleTypes;
import com.naterbobber.darkerdepths.init.DDSoundEvents;
import com.naterbobber.darkerdepths.init.DDStructures;
import com.naterbobber.darkerdepths.init.DDVanillaIntegration;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.FlatLevelSource;
import net.minecraft.world.level.levelgen.StructureSettings;
import net.minecraft.world.level.levelgen.feature.ConfiguredStructureFeature;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.feature.configurations.StructureFeatureConfiguration;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

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
        DDItems.ITEMS.register(modEventBus);
        DDBlockEntities.BLOCK_ENTITIES.register(modEventBus);
        DDParticleTypes.PARTICLE_TYPES.register(modEventBus);
        DDSoundEvents.SOUND_EVENTS.register(modEventBus);
        DDEntityTypes.ENTITY_TYPES.register(modEventBus);
        DDFeatures.FEATURES.register(modEventBus);
        DDBiomes.BIOMES.register(modEventBus);
        DDStructures.STRUCTURES.register(modEventBus);

        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(new WorldEvents());
        MinecraftForge.EVENT_BUS.register(new MobEvents());
        MinecraftForge.EVENT_BUS.addListener(EventPriority.NORMAL, this::addDimensionalSpacing);
    }

    private void setup(final FMLCommonSetupEvent event) {
//        DDVanillaIntegration.init();
        event.enqueueWork(() -> {
            DDStructures.setupStructures();
            DDConfiguredStructures.registerConfiguredStructures();
        });
    }

    private static Method GETCODEC_METHOD;
    public void addDimensionalSpacing(final WorldEvent.Load event) {
        if(event.getWorld() instanceof ServerLevel serverLevel){
            ChunkGenerator chunkGenerator = serverLevel.getChunkSource().getGenerator();
            if (chunkGenerator instanceof FlatLevelSource && serverLevel.dimension().equals(Level.OVERWORLD)) {
                return;
            }

            StructureSettings worldStructureConfig = chunkGenerator.getSettings();

            HashMap<StructureFeature<?>, HashMultimap<ConfiguredStructureFeature<?, ?>, ResourceKey<Biome>>> STStructureToMultiMap = new HashMap<>();

            for(Map.Entry<ResourceKey<Biome>, Biome> biomeEntry : serverLevel.registryAccess().ownedRegistryOrThrow(Registry.BIOME_REGISTRY).entrySet()) {
                Biome.BiomeCategory biomeCategory = biomeEntry.getValue().getBiomeCategory();
                if(biomeCategory != Biome.BiomeCategory.OCEAN && biomeCategory != Biome.BiomeCategory.THEEND && biomeCategory != Biome.BiomeCategory.NETHER && biomeCategory != Biome.BiomeCategory.NONE) {
                    associateBiomeToConfiguredStructure(STStructureToMultiMap, DDConfiguredStructures.CONFIGURED_ARID_CATACOMBS, biomeEntry.getKey());
                }
            }

            associateBiomeToConfiguredStructure(STStructureToMultiMap, DDConfiguredStructures.CONFIGURED_ARID_CATACOMBS, ResourceKey.create(Registry.BIOME_REGISTRY, new ResourceLocation(DarkerDepths.MODID, "arid_catacombs")));

//            ImmutableSet<ResourceKey<Biome>> overworldBiomes = ImmutableSet.<ResourceKey<Biome>>builder()
//                    .add(Biomes.FOREST)
//                    .add(Biomes.MEADOW)
//                    .add(Biomes.PLAINS)
//                    .add(Biomes.SAVANNA)
//                    .add(Biomes.SNOWY_PLAINS)
//                    .add(Biomes.SWAMP)
//                    .add(Biomes.SUNFLOWER_PLAINS)
//                    .add(Biomes.TAIGA)
//                    .build();
//            overworldBiomes.forEach(biomeKey -> associateBiomeToConfiguredStructure(STStructureToMultiMap, STConfiguredStructures.CONFIGURED_RUN_DOWN_HOUSE, biomeKey));

            ImmutableMap.Builder<StructureFeature<?>, ImmutableMultimap<ConfiguredStructureFeature<?, ?>, ResourceKey<Biome>>> tempStructureToMultiMap = ImmutableMap.builder();
            worldStructureConfig.configuredStructures.entrySet().stream().filter(entry -> !STStructureToMultiMap.containsKey(entry.getKey())).forEach(tempStructureToMultiMap::put);

            STStructureToMultiMap.forEach((key, value) -> tempStructureToMultiMap.put(key, ImmutableMultimap.copyOf(value)));

            worldStructureConfig.configuredStructures = tempStructureToMultiMap.build();

            try {
                if(GETCODEC_METHOD == null) GETCODEC_METHOD = ObfuscationReflectionHelper.findMethod(ChunkGenerator.class, "codec");
                ResourceLocation cgRL = Registry.CHUNK_GENERATOR.getKey((Codec<? extends ChunkGenerator>) GETCODEC_METHOD.invoke(chunkGenerator));
                if(cgRL != null && cgRL.getNamespace().equals("terraforged")) return;
            }
            catch(Exception e){
                DarkerDepths.LOGGER.error("Was unable to check if " + serverLevel.dimension().location() + " is using Terraforged's ChunkGenerator.");
            }

            if(chunkGenerator instanceof FlatLevelSource &&
                    serverLevel.dimension().equals(Level.OVERWORLD)){
                return;
            }

            Map<StructureFeature<?>, StructureFeatureConfiguration> tempMap = new HashMap<>(worldStructureConfig.structureConfig());
            tempMap.putIfAbsent(DDStructures.ARID_CATACOMBS.get(), StructureSettings.DEFAULTS.get(DDStructures.ARID_CATACOMBS.get()));
            worldStructureConfig.structureConfig = tempMap;
        }
    }

    private static void associateBiomeToConfiguredStructure(Map<StructureFeature<?>, HashMultimap<ConfiguredStructureFeature<?, ?>, ResourceKey<Biome>>> STStructureToMultiMap, ConfiguredStructureFeature<?, ?> configuredStructureFeature, ResourceKey<Biome> biomeRegistryKey) {
        STStructureToMultiMap.putIfAbsent(configuredStructureFeature.feature, HashMultimap.create());
        HashMultimap<ConfiguredStructureFeature<?, ?>, ResourceKey<Biome>> configuredStructureToBiomeMultiMap = STStructureToMultiMap.get(configuredStructureFeature.feature);
        if(configuredStructureToBiomeMultiMap.containsValue(biomeRegistryKey)) {
            DarkerDepths.LOGGER.error("""
                    Detected 2 ConfiguredStructureFeatures that share the same base StructureFeature trying to be added to same biome. One will be prevented from spawning.
                    This issue happens with vanilla too and is why a Snowy Village and Plains Village cannot spawn in the same biome because they both use the Village base structure.
                    The two conflicting ConfiguredStructures are: {}, {}
                    The biome that is attempting to be shared: {}
                """,
                    BuiltinRegistries.CONFIGURED_STRUCTURE_FEATURE.getId(configuredStructureFeature),
                    BuiltinRegistries.CONFIGURED_STRUCTURE_FEATURE.getId(configuredStructureToBiomeMultiMap.entries().stream().filter(e -> e.getValue() == biomeRegistryKey).findFirst().get().getKey()),
                    biomeRegistryKey
            );
        }
        else{
            configuredStructureToBiomeMultiMap.put(configuredStructureFeature, biomeRegistryKey);
        }
    }

}
