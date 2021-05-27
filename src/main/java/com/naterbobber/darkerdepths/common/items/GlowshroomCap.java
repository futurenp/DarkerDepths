package com.naterbobber.darkerdepths.common.items;

import net.minecraft.block.Block;
import net.minecraft.entity.ICrossbowUser;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.world.World;

import javax.annotation.Nullable;


public class GlowshroomCap extends BlockItem {

    public GlowshroomCap(Block blockIn, Properties builder) {
        super(blockIn, builder);
    }

    @Nullable
    @Override
    public EquipmentSlotType getEquipmentSlot(ItemStack stack) {
        return EquipmentSlotType.HEAD;
    }

    @Override
    public void onArmorTick(ItemStack stack, World world, PlayerEntity player) {
        player.addPotionEffect(new EffectInstance(Effect.get(3), 40, 0));
    }

}
