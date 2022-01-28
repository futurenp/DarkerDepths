package com.naterbobber.darkerdepths.init;

import com.naterbobber.darkerdepths.DarkerDepths;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.worldgen.PlainVillagePools;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.ConfiguredStructureFeature;
import net.minecraft.world.level.levelgen.feature.configurations.JigsawConfiguration;

public class DDConfiguredStructures {

    public static final ConfiguredStructureFeature<?, ?> CONFIGURED_ARID_CATACOMBS = DDStructures.ARID_CATACOMBS.get()
            .configured(new JigsawConfiguration(() -> PlainVillagePools.START, 0));

    public static void registerConfiguredStructures() {
        Registry<ConfiguredStructureFeature<?, ?>> registry = BuiltinRegistries.CONFIGURED_STRUCTURE_FEATURE;
        Registry.register(registry, new ResourceLocation(DarkerDepths.MODID, "configured_arid_catacombs"), CONFIGURED_ARID_CATACOMBS);
    }

}
