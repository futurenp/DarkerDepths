package com.naterbobber.darkerdepths.events;

import com.naterbobber.darkerdepths.DarkerDepths;
import com.naterbobber.darkerdepths.init.DDItems;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.AnvilUpdateEvent;

@EventBusSubscriber(modid = DarkerDepths.MOD_ID)
public class RequiemCurseRemovalHandler {

    @SubscribeEvent
    public static void onAnvilUpdate(AnvilUpdateEvent event) {
        ItemStack leftStack = event.getLeft();
        ItemStack rightStack = event.getRight();

        if (leftStack.isEmpty() || !rightStack.is(DDItems.VOID_SOUL_REQUIEM.get())) {
            return;
        }

        if (!hasCurse(leftStack)) {
            return;
        }

        final int cost = 25;

        ItemStack output = leftStack.copy();
        removeCurses(output);
        event.setOutput(output);
        event.setCost(cost);
        event.setMaterialCost(1);
    }

    private static void removeCurses(ItemStack stack) {
        if (!hasCurse(stack)) return;

        ItemEnchantments enchantments = stack.getOrDefault(DataComponents.ENCHANTMENTS, ItemEnchantments.EMPTY);
        ItemEnchantments.Mutable builder = new ItemEnchantments.Mutable(ItemEnchantments.EMPTY);

        enchantments.entrySet().forEach(entry -> {
            Holder<Enchantment> enchantmentHolder = entry.getKey();
            int level = entry.getIntValue();
            if (!enchantmentHolder.is(net.minecraft.tags.EnchantmentTags.CURSE)) {
                builder.set(enchantmentHolder, level);
            }
        });

        stack.set(DataComponents.ENCHANTMENTS, builder.toImmutable());
    }

    private static boolean hasCurse(ItemStack stack) {
        if (stack.isEmpty()) {
            return false;
        }

        ItemEnchantments enchantments = stack.getOrDefault(DataComponents.ENCHANTMENTS, ItemEnchantments.EMPTY);

        if (enchantments.isEmpty()) {
            return false;
        }

        return enchantments.entrySet().stream()
                .anyMatch(entry -> entry.getKey().is(net.minecraft.tags.EnchantmentTags.CURSE));
    }
}