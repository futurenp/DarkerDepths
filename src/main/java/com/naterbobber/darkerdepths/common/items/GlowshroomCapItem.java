package com.naterbobber.darkerdepths.common.items;

import net.minecraft.enchantment.IArmorVanishable;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

import javax.annotation.Nullable;

//<>

public class GlowshroomCapItem extends Item implements IArmorVanishable {
    public GlowshroomCapItem(Properties properties) {
        super(properties);
    }

    @Nullable
    @Override
    public EquipmentSlotType getEquipmentSlot(ItemStack stack) {
        return EquipmentSlotType.HEAD;
    }

    @Override
    public void onArmorTick(ItemStack stack, World world, PlayerEntity player) {
        player.addPotionEffect(new EffectInstance(Effect.get(3), 1, 0, false, false, false));
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack heldItem = playerIn.getHeldItem(handIn);
        EquipmentSlotType slot = MobEntity.getSlotForItemStack(heldItem);
        ItemStack stack = playerIn.getItemStackFromSlot(slot);
        if (stack.isEmpty()) {
            playerIn.setItemStackToSlot(slot, heldItem.copy());
            heldItem.setCount(0);
            return ActionResult.func_233538_a_(heldItem, worldIn.isRemote());
        } else {
            return ActionResult.resultFail(heldItem);
        }
    }
}