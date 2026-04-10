package com.naterbobber.darkerdepths.data;

import com.naterbobber.darkerdepths.DarkerDepths;
import com.naterbobber.darkerdepths.damage.DDDamageTypes;
import com.naterbobber.darkerdepths.init.DDStructures;
import com.naterbobber.darkerdepths.worldgen.biomes.DDBiomeModifiers;
import com.naterbobber.darkerdepths.worldgen.biomes.DDBiomes;
import com.naterbobber.darkerdepths.worldgen.carvers.DDCarvers;
import com.naterbobber.darkerdepths.worldgen.feature.DDConfiguredFeatures;
import com.naterbobber.darkerdepths.worldgen.feature.DDPlacedFeatures;
import com.naterbobber.darkerdepths.worldgen.structures.processors.DDProcessorLists;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class DDDatapackBuiltinEntriesProvider extends DatapackBuiltinEntriesProvider {

    public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(Registries.BIOME, DDBiomes::bootstrap)
            .add(Registries.CONFIGURED_FEATURE, DDConfiguredFeatures::bootstrap)
            .add(Registries.PLACED_FEATURE, DDPlacedFeatures::bootstrap)
            .add(ForgeRegistries.Keys.BIOME_MODIFIERS, DDBiomeModifiers::bootstrap)
            .add(Registries.TEMPLATE_POOL, DDStructures::bootstrapTemplatePool)
            .add(Registries.STRUCTURE, DDStructures::bootstrap)
            .add(Registries.STRUCTURE_SET, DDStructures::bootstrapStructureSet)
            .add(Registries.PROCESSOR_LIST, DDProcessorLists::bootstrap)
            .add(Registries.CONFIGURED_CARVER, DDCarvers::bootstrap)
            .add(Registries.DAMAGE_TYPE, DDDamageTypes::bootstrap);

    public DDDatapackBuiltinEntriesProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, BUILDER, Set.of(DarkerDepths.MOD_ID));
    }

}
