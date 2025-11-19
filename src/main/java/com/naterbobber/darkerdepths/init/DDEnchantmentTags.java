package com.naterbobber.darkerdepths.init;

import com.naterbobber.darkerdepths.DarkerDepths;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.enchantment.Enchantment;

public class DDEnchantmentTags {

//    public static final TagKey<Enchantment> STILETTO_EXCLUSIVE = create("stiletto_exclusive");

    private static TagKey<Enchantment> create(String name) {
        return TagKey.create(Registries.ENCHANTMENT, DarkerDepths.id(name));
    }

}
