package com.naterbobber.darkerdepths.events;

import com.naterbobber.darkerdepths.DarkerDepths;
import com.naterbobber.darkerdepths.init.DDItems;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Map;
import java.util.stream.Collectors;

@Mod.EventBusSubscriber(modid = DarkerDepths.MODID)
public class RequiemCurseRemovalHandler {

    @SubscribeEvent
    public static void onAnvilUpdate(AnvilUpdateEvent event) {
        ItemStack leftStack = event.getLeft();
        ItemStack rightStack = event.getRight();

        if (leftStack.isEmpty() || !rightStack.is(DDItems.VOID_SOUL_REQUIEM.get())) {
            return;
        }

        boolean isDamaged = leftStack.getDamageValue() > 0;
        boolean isCursed = hasCurse(leftStack);

        if (!isDamaged && !isCursed) {
            return;
        }

        final int repairCost = 3;
        final int cleanseCost = 10;

        int finalCost;

        if (isCursed) {
            finalCost = cleanseCost;
        } else {
            finalCost = repairCost;
        }

        ItemStack output = leftStack.copy();
        output.setDamageValue(0);
        removeCurses(output);

        event.setOutput(output);
        event.setCost(finalCost);
        event.setMaterialCost(1);
    }

    private static void removeCurses(ItemStack stack) {
        if (!hasCurse(stack)) return;

        Map<Enchantment, Integer> enchantments = EnchantmentHelper.getEnchantments(stack);
        Map<Enchantment, Integer> nonCursedEnchantments = enchantments.entrySet().stream()
                .filter(entry -> !entry.getKey().isCurse())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        EnchantmentHelper.setEnchantments(nonCursedEnchantments, stack);
    }

    private static boolean hasCurse(ItemStack stack) {
        if (stack.isEmpty() || !stack.isEnchanted()) {
            return false;
        }
        return EnchantmentHelper.getEnchantments(stack).keySet().stream().anyMatch(Enchantment::isCurse);
    }
}