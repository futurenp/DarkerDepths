package com.naterbobber.darkerdepths.registry;

import com.google.common.collect.Maps;
import com.naterbobber.darkerdepths.init.BlockInit;
import net.minecraft.block.Block;
import net.minecraft.item.AxeItem;

public class VanillaIntegrationRegistry {
    public static void setup() {
        // STRIPPED LOGS
        addStrippable(BlockInit.petrified_log, BlockInit.stripped_petrified_log);
        addStrippable(BlockInit.petrified_wood, BlockInit.stripped_petrified_wood);
    }

    private static void addStrippable(Block unstrippedBlock, Block strippedBlock) {
        AxeItem.BLOCK_STRIPPING_MAP = Maps.newHashMap(AxeItem.BLOCK_STRIPPING_MAP);
        AxeItem.BLOCK_STRIPPING_MAP.put(unstrippedBlock, strippedBlock);
    }
}

