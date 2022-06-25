package com.naterbobber.darkerdepths.init;

import com.naterbobber.darkerdepths.DarkerDepths;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class DDItemTags {

    public static final TagKey<Item> PETRIFIED_LOGS = bind("petrified_logs");

    private static TagKey<Item> bind(String id) {
        return TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation(DarkerDepths.MODID, id));
    }

}