package com.naterbobber.darkerdepths.enchantment;

import com.naterbobber.darkerdepths.init.DDEnchantmentEffects;
import com.naterbobber.darkerdepths.util.DDResourceKeys;
import com.naterbobber.darkerdepths.util.DDTags;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.LevelBasedValue;
import net.minecraft.world.item.enchantment.effects.AddValue;

import static com.naterbobber.darkerdepths.util.DDResourceKeys.Enchantments.*;

public class DDEnchantments {

    public static void bootstrap(BootstrapContext<Enchantment> context) {
        HolderGetter<Item> itemLookup = context.lookup(Registries.ITEM);
        HolderGetter<Enchantment> enchantmentLookup = context.lookup(Registries.ENCHANTMENT);

        register(context, SWIFT_STRIKE, Enchantment.enchantment(
                Enchantment.definition(
                        itemLookup.getOrThrow(DDTags.Items.STILETTO_ENCHANTABLE),
                        2,
                        1,
                        Enchantment.dynamicCost(15, 9),
                        Enchantment.dynamicCost(65, 9),
                        4,
                        EquipmentSlotGroup.MAINHAND
                )
        ).withEffect(DDEnchantmentEffects.SWIFT_STRIKE_HIT.get(), new AddValue(LevelBasedValue.perLevel(1.0F))));

        register(context, QUICK_DASH, Enchantment.enchantment(
                Enchantment.definition(
                        itemLookup.getOrThrow(DDTags.Items.STILETTO_ENCHANTABLE),
                        2,
                        3,
                        Enchantment.dynamicCost(15, 9),
                        Enchantment.dynamicCost(65, 9),
                        4,
                        EquipmentSlotGroup.MAINHAND
                )
        ).withEffect(DDEnchantmentEffects.QUICK_DASH_DURATION.get(), new AddValue(LevelBasedValue.perLevel(1.0F))));
    }

    private static void register(BootstrapContext<Enchantment> context, ResourceKey<Enchantment> key, Enchantment.Builder builder) {
        context.register(key, builder.build(key.location()));
    }
}
