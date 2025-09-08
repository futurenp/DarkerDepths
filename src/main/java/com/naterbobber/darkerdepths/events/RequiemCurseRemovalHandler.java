package com.naterbobber.darkerdepths.events;

import com.naterbobber.darkerdepths.DarkerDepths;
import com.naterbobber.darkerdepths.init.DDItems;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.Map;
import java.util.stream.Collectors;

@Mod.EventBusSubscriber(modid = DarkerDepths.MOD_ID)
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