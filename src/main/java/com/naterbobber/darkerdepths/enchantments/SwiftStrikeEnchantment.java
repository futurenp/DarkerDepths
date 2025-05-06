package com.naterbobber.darkerdepths.enchantments;

import com.naterbobber.darkerdepths.item.StilettoItem;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class SwiftStrikeEnchantment extends DDEnchantment {

    public SwiftStrikeEnchantment() {
        super(Rarity.VERY_RARE, EnchantmentCategory.WEAPON, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }

    @Override
    public int getMinCost(int p_44679_) {
        return p_44679_ * 25;
    }

    @Override
    public int getMaxCost(int p_44691_) {
        return this.getMinCost(p_44691_) + 50;
    }

    @Override
    public boolean isTreasureOnly() {
        return true;
    }

    @Override
    public boolean isTradeable() {
        return false;
    }

    @Override
    public boolean isDiscoverable() {
        return false;
    }

    @Override
    public boolean canEnchant(ItemStack stack){
        return stack.getItem() instanceof StilettoItem;
    }
}
