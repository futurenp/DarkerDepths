package com.naterbobber.darkerdepths.core;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.RegistryObject;

import java.util.function.Supplier;

//<>

public class DDRegistries extends CoreRegistries {

    public DDRegistries(String modId) {
        super(modId);
    }

    public <B extends Block> RegistryObject<B> registerCompatBlock(String modId, String key, Supplier<? extends B> block, ItemGroup group) {
        RegistryObject<B> blocks = this.blocks.register(key, block);
        this.items.register(key, () -> new BlockItem(blocks.get(), new Item.Properties().group(getGroup(modId, group))));
        return blocks;
    }

    public ItemGroup getGroup(String modId, ItemGroup group) {
        return ModList.get().isLoaded(modId) ? group : null;
    }
}