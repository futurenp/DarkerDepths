package com.naterbobber.darkerdepths.compat.init;

import com.naterbobber.darkerdepths.DarkerDepths;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class ForgeItemTags {

    public static final TagKey<Item> PETRIFIED_LOGS = bind("petrified_logs");
    public static final TagKey<Item> ROPES = forge("ropes");
    private static TagKey<Item> bind(String path) {
        return TagKey.create(Registries.ITEM, DarkerDepths.id(path));
    }

    private static TagKey<Item> forge(String path) {
        return TagKey.create(Registries.ITEM, new ResourceLocation("forge", path));
    }
}