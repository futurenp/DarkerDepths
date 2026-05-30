package com.naterbobber.darkerdepths.data;

import com.naterbobber.darkerdepths.init.DDBlocks;
import com.naterbobber.darkerdepths.init.DDItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ComposterBlock;
import net.neoforged.neoforge.common.data.DataMapProvider;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.datamaps.builtin.Compostable;
import net.neoforged.neoforge.registries.datamaps.builtin.FurnaceFuel;
import net.neoforged.neoforge.registries.datamaps.builtin.NeoForgeDataMaps;

import java.util.concurrent.CompletableFuture;

public class DDDataMapProvider extends DataMapProvider {

    protected DDDataMapProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(packOutput, lookupProvider);
    }

    @Override
    protected void gather(HolderLookup.Provider provider) {
        addFuel(DDBlocks.SCORCHED_REMAINS_BLOCK, 4800);
        addFuel(DDBlocks.SCORCHED_REMAINS, 2000);
        addCompostable(DDBlocks.GLOWSHROOM, 0.5F);
        addCompostable(DDItems.GLOW_GRIME, 0.5F);
        addCompostable(DDBlocks.GLOWSHROOM_BLOCK, 0.85F);
        addCompostable(DDBlocks.GLOWSPURS, 0.5F);
        addCompostable(DDBlocks.GLIMMERING_VINES, 0.3F);
        addCompostable(DDBlocks.MOSSY_SPROUTS, 0.3F);
        addCompostable(DDBlocks.PETRIFIED_ROOTS, 0.2F);
    }

    private void addFuel(DeferredHolder<?, ?> holder, int burnTime) {
        addFuel(holder.getId(), burnTime);
    }

    private void addFuel(ResourceLocation location, int burnTime) {
        builder(NeoForgeDataMaps.FURNACE_FUELS).add(location, new FurnaceFuel(burnTime), false);
    }

    private void addCompostable(DeferredHolder<?, ?> holder, float chance) {
        addCompostable(holder.getId(), chance);
    }

    private void addCompostable(ResourceLocation location, float chance) {
        builder(NeoForgeDataMaps.COMPOSTABLES).add(location, new Compostable(chance), false);
    }
}
