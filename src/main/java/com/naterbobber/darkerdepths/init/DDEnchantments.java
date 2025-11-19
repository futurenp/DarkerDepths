package com.naterbobber.darkerdepths.init;

import com.google.common.collect.Lists;
import com.naterbobber.darkerdepths.DarkerDepths;
import com.naterbobber.darkerdepths.component.DDEnchantmentEffects;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.LevelBasedValue;
import net.minecraft.world.item.enchantment.effects.AddValue;

import java.util.List;

public class DDEnchantments {

    public static final List<ResourceKey<Enchantment>> ENCHANTMENTS = Lists.newArrayList();

    public static final ResourceKey<Enchantment> SWIFT_STRIKE = key("swift_strike");
    public static final ResourceKey<Enchantment> QUICK_DASH = key("quick_dash");

    public static void bootstrap(BootstrapContext<Enchantment> context) {
        HolderGetter<Item> itemLookup = context.lookup(Registries.ITEM);
        HolderGetter<Enchantment> enchantmentLookup = context.lookup(Registries.ENCHANTMENT);

        register(context, SWIFT_STRIKE, Enchantment.enchantment(
                Enchantment.definition(
                        itemLookup.getOrThrow(DDItemTags.STILETTO_ENCHANTABLE),
                        2,
                        1,
                        Enchantment.dynamicCost(15, 9),
                        Enchantment.dynamicCost(65, 9),
                        4,
                        EquipmentSlotGroup.MAINHAND
                )
        ).exclusiveWith(enchantmentLookup.getOrThrow(DDEnchantmentTags.STILETTO_EXCLUSIVE)).withEffect(DDEnchantmentEffects.SWIFT_STRIKE_HIT.get(), new AddValue(LevelBasedValue.perLevel(1.0F))));

        register(context, QUICK_DASH, Enchantment.enchantment(
                Enchantment.definition(
                        itemLookup.getOrThrow(DDItemTags.STILETTO_ENCHANTABLE),
                        2,
                        3,
                        Enchantment.dynamicCost(15, 9),
                        Enchantment.dynamicCost(65, 9),
                        4,
                        EquipmentSlotGroup.MAINHAND
                )
        ).exclusiveWith(enchantmentLookup.getOrThrow(DDEnchantmentTags.STILETTO_EXCLUSIVE)).withEffect(DDEnchantmentEffects.QUICK_DASH_DURATION.get(), new AddValue(LevelBasedValue.perLevel(1.0F))));
    }

    private static void register(BootstrapContext<Enchantment> context, ResourceKey<Enchantment> key, Enchantment.Builder builder) {
        context.register(key, builder.build(key.location()));
    }

    private static ResourceKey<Enchantment> key(String name) {
        ResourceKey<Enchantment> key = ResourceKey.create(Registries.ENCHANTMENT, DarkerDepths.id(name));
        ENCHANTMENTS.add(key);
        return key;
    }

}
