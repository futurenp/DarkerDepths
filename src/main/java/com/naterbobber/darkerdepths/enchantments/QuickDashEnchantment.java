package com.naterbobber.darkerdepths.enchantments;

import com.naterbobber.darkerdepths.item.StilettoItem;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class QuickDashEnchantment extends Enchantment {

    public QuickDashEnchantment() {
        super(Rarity.RARE, EnchantmentCategory.WEAPON, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }
    @Override
    public int getMaxLevel() {
        return 3;
    }

    @Override
    public int getMinCost(int p_44679_) {
        return 10 + (p_44679_ - 1) * 11;
    }

    @Override
    public int getMaxCost(int p_44691_) {
        return this.getMinCost(p_44691_) + 10;
    }

    @Override
    public boolean isTreasureOnly() {
        return false;
    }

    @Override
    public boolean isDiscoverable() {
        return true;
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        return stack.getItem() instanceof StilettoItem;
    }

    @Override
    public boolean canEnchant(ItemStack stack){
        return stack.getItem() instanceof StilettoItem;
    }
}
