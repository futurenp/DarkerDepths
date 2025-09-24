package com.naterbobber.darkerdepths.init;

import com.naterbobber.darkerdepths.DarkerDepths;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class DDItemTags {

    public static final TagKey<Item> PETRIFIED_LOGS = bind("petrified_logs");
    public static final TagKey<Item> STILETTO_ENCHANTABLE = bind("stiletto_enchantable");

    private static TagKey<Item> bind(String id) {
        return TagKey.create(Registries.ITEM, DarkerDepths.id(id));
    }

}
