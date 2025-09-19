package com.naterbobber.darkerdepths.item;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.block.Block;

import java.util.List;

public class BlockItemWithHoverText extends BlockItem {

    private final List<Component> tooltips;

    public BlockItemWithHoverText(Block pBlock, Item.Properties pProperties, List<Component> tooltips) {
        super(pBlock, pProperties);
        this.tooltips = tooltips;
    }

    @Override
    public void appendHoverText(ItemStack pStack, TooltipContext context, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.translatable("tooltip.darkerdepths.press_shift").withStyle(ChatFormatting.GRAY));

        if (Screen.hasShiftDown()) {
            pTooltipComponents.addAll(tooltips);
        }

        super.appendHoverText(pStack, context, pTooltipComponents, pIsAdvanced);
    }
}
