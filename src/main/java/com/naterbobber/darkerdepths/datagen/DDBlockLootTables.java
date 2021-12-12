package com.naterbobber.darkerdepths.datagen;

import net.minecraft.block.Block;
import net.minecraft.data.loot.BlockLootTables;

public class DDBlockLootTables extends BlockLootTables {

    @Override
    protected void addTables() {
    }

    //TODO: Deferred Registries should be in static
    @Override
    protected Iterable<Block> getKnownBlocks() {
        return super.getKnownBlocks();
    }
}
