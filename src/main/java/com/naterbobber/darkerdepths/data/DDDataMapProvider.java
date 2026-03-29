package com.naterbobber.darkerdepths.data;

import com.naterbobber.darkerdepths.init.DDBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.DataMapProvider;
import net.neoforged.neoforge.registries.DeferredBlock;
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
    }

    private void addFuel(DeferredBlock<? extends Block> holder, int burnTime) {
        addFuel(holder.getId(), burnTime);
    }

    private void addFuel(ResourceLocation location, int burnTime) {
        builder(NeoForgeDataMaps.FURNACE_FUELS).add(location, new FurnaceFuel(burnTime), false);
    }
}
