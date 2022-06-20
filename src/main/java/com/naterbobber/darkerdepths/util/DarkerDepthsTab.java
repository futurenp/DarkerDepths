package com.naterbobber.darkerdepths.util;

import com.naterbobber.darkerdepths.init.DDItems;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class DarkerDepthsTab extends CreativeModeTab {

    public DarkerDepthsTab() {
        super("DarkerDepths");
    }

    @Override
    public ItemStack makeIcon() {
        return new ItemStack(DDItems.GLOWSHROOM_CAP.get());
    }
}
