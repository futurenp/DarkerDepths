package com.naterbobber.darkerdepths.registry;

import com.google.common.collect.Maps;
import com.naterbobber.darkerdepths.init.BlockInit;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.FireBlock;
import net.minecraft.item.AxeItem;

public class VanillaIntegrationRegistry {
    public static void setup() {
        // STRIPPED LOGS
        addStrippable(BlockInit.petrified_log, BlockInit.stripped_petrified_log);
        addStrippable(BlockInit.petrified_wood, BlockInit.stripped_petrified_wood);

        addFlammables(BlockInit.rope, 60, 100);
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

