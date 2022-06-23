package com.naterbobber.darkerdepths.compat.init;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class ForgeItemTags {

    public static final TagKey<Item> SILVER_INGOT = bind("ingots/silver");
    public static final TagKey<Item> COAL_ORES = bind("ores/coal");
    public static final TagKey<Item> DIAMOND_ORES = bind("ores/diamond");
    public static final TagKey<Item> GOLD_ORES = bind("ores/gold");
    public static final TagKey<Item> IRON_ORES = bind("ores/iron");
    public static final TagKey<Item> LAPIS_ORES = bind("ores/lapis");
    public static final TagKey<Item> REDSTONE_ORES = bind("ores/redstone");
    public static final TagKey<Item> SILVER_ORES = bind("ores/silver");
    public static final TagKey<Item> SILVER_STORAGE_BLOCKS = bind("storage_blocks/silver");
    public static final TagKey<Item> SILVER_RAW_ORES = bind("raw_ores/silver");

    public static final TagKey<Item> INGOTS = bind("ingots");
    public static final TagKey<Item> ORES = bind("ores");
    public static final TagKey<Item> STORAGE_BLOCKS = bind("storage_blocks");
    public static final TagKey<Item> RAW_ORES = bind("raw_ores");

    private static TagKey<Item> bind(String path) {
        return TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation("forge", path));
    }

}
