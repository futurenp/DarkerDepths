package com.naterbobber.darkerdepths.core;

//<>

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.RegistryObject;

import java.util.function.Supplier;

public class DDRegistries extends CoreRegistries {

    public DDRegistries(String modId) {
        super(modId);
    }

    public <B extends Block> RegistryObject<B> registerCompatBlock(String modid, String key, Supplier<? extends B> block, ItemGroup group) {
        RegistryObject<B> blocks = this.blocks.register(key, block);
        if (ModList.get().isLoaded(modid)) {
            this.items.register(key, () -> new BlockItem(blocks.get(), new Item.Properties().group(group)));
        }
        return blocks;
    }

}