package com.naterbobber.darkerdepths.compat.init;

import com.naterbobber.darkerdepths.DarkerDepths;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class NeoForgeItemTags {

    public static final TagKey<Item> PETRIFIED_LOGS = bind("petrified_logs");
    public static final TagKey<Item> ROPES = neoforgeTag("ropes");

    private static TagKey<Item> bind(String path) {
        return TagKey.create(Registries.ITEM, DarkerDepths.id(path));
    }

    private static TagKey<Item> neoforgeTag(String name) {
        return ItemTags.create(ResourceLocation.fromNamespaceAndPath("c", name));
    }
}