package com.naterbobber.darkerdepths;
import com.naterbobber.darkerdepths.init.ItemInit;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class DarkerDepthsItemGroup extends ItemGroup {

    public DarkerDepthsItemGroup(String label) {
        super(label);
    }

    @Override
    public ItemStack createIcon() {
        return new ItemStack(ItemInit.glowshroom_cap.getItem());
    }
}