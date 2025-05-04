package com.naterbobber.darkerdepths.data;

import com.naterbobber.darkerdepths.DarkerDepths;
import com.naterbobber.darkerdepths.init.DDBiomeModifiers;
import com.naterbobber.darkerdepths.init.DDBiomes;
import com.naterbobber.darkerdepths.init.DDConfiguredFeatures;
import com.naterbobber.darkerdepths.init.DDPlacedFeatures;
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
            .add(Registries.CONFIGURED_FEATURE, DDConfiguredFeatures::bootstrap)
            .add(Registries.PLACED_FEATURE, DDPlacedFeatures::bootstrap)
            .add(Registries.BIOME, DDBiomes::bootstrap)
            .add(ForgeRegistries.Keys.BIOME_MODIFIERS, DDBiomeModifiers::bootstrap);

    public DDDatapackBuiltinEntriesProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, BUILDER, Set.of(DarkerDepths.MODID));
    }

}
