package com.naterbobber.darkerdepths.core.registries;

import net.minecraft.block.ComposterBlock;
import net.minecraft.util.IItemProvider;

public class DDCompostables {

    public static void register() {
        registerCompostable(0.65F, DDBlocks.GLOWSHROOM.get().asItem());
        registerCompostable(0.65F, DDBlocks.LUSH_SPROUTS.get().asItem());
        registerCompostable(0.65F, DDBlocks.MOSSY_SPROUTS.get().asItem());
        registerCompostable(0.85F, DDBlocks.GLOWSPURS.get().asItem());
        registerCompostable(0.5F,  DDBlocks.GLOWSPIRE.get().asItem());
        registerCompostable(0.3F,  DDBlocks.ROOTS.get().asItem());
        registerCompostable(0.4F,  DDBlocks.LONG_ROOTS.get().asItem());
    }

    private static void registerCompostable(float chance, IItemProvider item) {
        ComposterBlock.CHANCES.put(item.asItem(), chance);
    }

}
