package com.naterbobber.darkerdepths.data.tags;

import com.naterbobber.darkerdepths.init.DDEnchantmentTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.VanillaEnchantmentTagsProvider;
import net.minecraft.world.item.enchantment.Enchantments;

import java.util.concurrent.CompletableFuture;

public class DDEnchantmentTagsProvider extends VanillaEnchantmentTagsProvider {

    public DDEnchantmentTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
//        this.tag(DDEnchantmentTags.STILETTO_EXCLUSIVE).add(Enchantments.SWEEPING_EDGE);
    }
}
