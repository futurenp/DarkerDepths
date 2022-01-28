package com.naterbobber.darkerdepths.init;

import com.naterbobber.darkerdepths.DarkerDepths;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.worldgen.PlainVillagePools;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.ConfiguredStructureFeature;
import net.minecraft.world.level.levelgen.feature.configurations.JigsawConfiguration;

import java.util.concurrent.ConcurrentHashMap;

public class DDConfiguredStructures {
    private static final ConcurrentHashMap<String, ConfiguredStructureFeature<?, ?>> CONFIGURED_STRUCTURES = new ConcurrentHashMap<>();

    public static final ConfiguredStructureFeature<?, ?> CONFIGURED_ARID_CATACOMBS = registerConfiguredStructure("configured_arid_catacombs", DDStructures.ARID_CATACOMBS.get().configured(new JigsawConfiguration(() -> PlainVillagePools.START, 0)));

    public static ConfiguredStructureFeature<?, ?> registerConfiguredStructure(String name, ConfiguredStructureFeature<?, ?> structureFeature) {
        CONFIGURED_STRUCTURES.put(name, structureFeature);
        return structureFeature;
    }

    public static void registerConfiguredStructures() {
        Registry<ConfiguredStructureFeature<?, ?>> registry = BuiltinRegistries.CONFIGURED_STRUCTURE_FEATURE;
        for (String id : CONFIGURED_STRUCTURES.keySet()) {
            Registry.register(registry, new ResourceLocation(DarkerDepths.MODID, id), CONFIGURED_STRUCTURES.get(id));
        }
//        Registry.register(registry, new ResourceLocation(DarkerDepths.MODID, "configured_arid_catacombs"), CONFIGURED_ARID_CATACOMBS);
    }

}
