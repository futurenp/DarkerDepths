package com.naterbobber.darkerdepths.item;

import com.naterbobber.darkerdepths.DarkerDepths;
import com.naterbobber.darkerdepths.init.DDArmorMaterials;
import com.naterbobber.darkerdepths.init.DDItems;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.List;
import java.util.Map;

public class GlowshroomCapItem extends ArmorItem {
    private static final ResourceLocation TEXTURE = DarkerDepths.id("textures/models/armor/glowshroom_cap_model.png");

    public GlowshroomCapItem(Properties properties) {
        super(DDArmorMaterials.GLOWSHROOM_MATERIAL, Type.HELMET, properties);
    }

    @Override
    public void appendHoverText(ItemStack pStack, TooltipContext context, List<Component> pTooltipComponents, TooltipFlag tooltipFlag) {
        pTooltipComponents.add(Component.translatable("tooltip.darkerdepths.press_shift").withStyle(ChatFormatting.GRAY));

        if (Screen.hasShiftDown()) {
            pTooltipComponents.add(Component.translatable("tooltip.darkerdepths.glowshroom_cap.shift_desc_1").withStyle(ChatFormatting.AQUA));
            pTooltipComponents.add(Component.translatable("tooltip.darkerdepths.glowshroom_cap.shift_desc_2").withStyle(ChatFormatting.GREEN));
        }

        super.appendHoverText(pStack, context, pTooltipComponents, tooltipFlag);
    }
}