package com.naterbobber.darkerdepths.data.loot;

import com.naterbobber.darkerdepths.init.DDBlocks;
import net.minecraft.data.loot.packs.VanillaBlockLoot;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraftforge.registries.RegistryObject;

import java.util.stream.Collectors;

public class DDBlockLoot extends VanillaBlockLoot {

    @Override
    protected void generate() {
//        for (RegistryObject<Block> block : DDBlocks.BLOCKS.getEntries()) {
//            this.dropSelf(block.get());
//        }

        this.dropSelf(DDBlocks.PETRIFIED_PLANKS.get());
        this.dropSelf(DDBlocks.VERTICAL_PETRIFIED_PLANKS.get());
        this.dropSelf(DDBlocks.PETRIFIED_LOG.get());
        this.dropSelf(DDBlocks.STRIPPED_PETRIFIED_LOG.get());
        this.dropSelf(DDBlocks.STRIPPED_PETRIFIED_WOOD.get());
        this.dropSelf(DDBlocks.PETRIFIED_WOOD.get());

//        this.map.remove(DDBlocks.PETRIFIED_SLAB.get());
        this.dropSlab(DDBlocks.PETRIFIED_SLAB);
    }

    private void verticalSlab(RegistryObject<Block> verticalSlab) {
        this.add(verticalSlab.get(), block -> {
            return LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)));
        });
    }

    private void dropSlab(RegistryObject<Block> slab) {
        this.add(slab.get(), this::createSlabItemTable);
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return DDBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get).collect(Collectors.toList());
    }
}
