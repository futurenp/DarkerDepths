package com.naterbobber.darkerdepths.core.registries;

import com.google.common.collect.Maps;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.FireBlock;
import net.minecraft.item.AxeItem;

public class VanillaIntegrationRegistry {
    public static void setup() {
        // STRIPPED LOGS
        addStrippable(DDBlocks.PETRIFIED_LOG.get(), DDBlocks.STRIPPED_PETRIFIED_LOG.get());
        addStrippable(DDBlocks.PETRIFIED_WOOD.get(), DDBlocks.STRIPPED_PETRIFIED_WOOD.get());
        addStrippable(DDBlocks.PETRIFIED_POST.get(), DDBlocks.STRIPPED_PETRIFIED_POST.get());

        addFlammables(DDBlocks.ROPE.get(), 60, 100);
        addFlammables(DDBlocks.ROOTS.get(), 60, 100);
        addFlammables(DDBlocks.LONG_ROOTS.get(), 60, 100);
        addFlammables(DDBlocks.DETRITUS.get(), 60, 100);
    }

    private static void addStrippable(Block unstrippedBlock, Block strippedBlock) {
        AxeItem.BLOCK_STRIPPING_MAP = Maps.newHashMap(AxeItem.BLOCK_STRIPPING_MAP);
        AxeItem.BLOCK_STRIPPING_MAP.put(unstrippedBlock, strippedBlock);
    }

    public static void addFlammables(Block blockIn, int encouragement, int flammability)
    {
        FireBlock fireblock = (FireBlock) Blocks.FIRE;
        fireblock.setFireInfo(blockIn, encouragement, flammability);
    }

}

