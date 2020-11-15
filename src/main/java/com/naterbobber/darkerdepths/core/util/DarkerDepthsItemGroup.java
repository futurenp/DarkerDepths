package com.naterbobber.darkerdepths.core.util;

import com.naterbobber.darkerdepths.core.registries.DDBlocks;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class DarkerDepthsItemGroup extends ItemGroup {

    public DarkerDepthsItemGroup(String label) {
        super(label);
    }

    @Override
    public ItemStack createIcon() {
        return new ItemStack(DDBlocks.GLOWSHROOM_CAP.get().asItem());
    }
}