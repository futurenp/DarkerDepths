package com.naterbobber.darkerdepths.core.util;

import com.naterbobber.darkerdepths.core.registries.DDItems;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class DarkerDepthsItemGroup extends ItemGroup {

    public DarkerDepthsItemGroup(String label) {
        super(label);
    }

    @Override
    public ItemStack createIcon() {
        return new ItemStack(DDItems.GLOWSHROOM_CAP.get());
    }
}