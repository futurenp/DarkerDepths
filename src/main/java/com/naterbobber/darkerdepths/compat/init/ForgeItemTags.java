package com.naterbobber.darkerdepths.compat.init;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class ForgeItemTags {

    public static final TagKey<Item> SILVER_INGOT = bind("ingots/silver");
    public static final TagKey<Item> SILVER_ORES = bind("ores/silver");
    public static final TagKey<Item> SILVER_STORAGE_BLOCKS = bind("storage_blocks/silver");
    public static final TagKey<Item> SILVER_RAW_ORES = bind("raw_materials/silver");

    private static TagKey<Item> bind(String path) {
        return TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("forge", path));
    }

}
