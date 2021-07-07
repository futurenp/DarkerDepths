package com.naterbobber.darkerdepths.common.items;

import com.naterbobber.darkerdepths.client.entity.model.CapModel;
import com.naterbobber.darkerdepths.core.DarkerDepths;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.enchantment.IArmorVanishable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class GlowshroomCapItem extends ArmorItem implements IArmorVanishable {
    public static GlowshroomMaterial material = new GlowshroomMaterial();

    public GlowshroomCapItem(Properties builder) {
        super(material, EquipmentSlotType.HEAD, builder);
    }

    @Override
    public void onArmorTick(ItemStack stack, World world, PlayerEntity player) {
        player.addPotionEffect(new EffectInstance(Effect.get(3), 40, 0));
    }

    @Nullable
    @Override
    public <A extends BipedModel<?>> A getArmorModel(LivingEntity entityLiving, ItemStack itemStack, EquipmentSlotType armorSlot, A _default) {
        CapModel<?> cap = new CapModel<>();
        cap.bipedHead.showModel = armorSlot == EquipmentSlotType.HEAD;
        return (A) cap;
    }

    @Nullable
    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
        return DarkerDepths.MODID + ":textures/block/cap_model.png";
    }

    private static class GlowshroomMaterial implements IArmorMaterial {
        @Override
        public int getDurability(EquipmentSlotType slotIn) {
            return 798;
        }

        @Override
        public int getDamageReductionAmount(EquipmentSlotType slotIn) {
            return 0;
        }

        @Override
        public int getEnchantability() {
            return 0;
        }

        @Override
        public SoundEvent getSoundEvent() {
            return SoundEvents.ITEM_ARMOR_EQUIP_LEATHER;
        }

        @Override
        public Ingredient getRepairMaterial() {
            return null;
        }

        @Override
        public String getName() {
            return "glowshroom";
        }

        @Override
        public float getToughness() {
            return 0;
        }

        @Override
        public float getKnockbackResistance() {
            return 0;
        }
    }
}
