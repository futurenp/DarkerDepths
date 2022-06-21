package com.naterbobber.darkerdepths.data;

import com.naterbobber.darkerdepths.DarkerDepths;
import com.naterbobber.darkerdepths.init.DDBlocks;
import com.naterbobber.darkerdepths.init.DDItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

public class DDItemTagsProvider extends ItemTagsProvider {

    public DDItemTagsProvider(DataGenerator p_126530_, @Nullable ExistingFileHelper existingFileHelper) {
        super(p_126530_, new DDBlockTagsProvider(p_126530_, existingFileHelper), DarkerDepths.MODID, existingFileHelper);
    }

    @Override
    protected void addTags() {
        this.tag(ItemTags.LOGS).add(DDBlocks.PETRIFIED_LOG.get().asItem(), DDBlocks.STRIPPED_PETRIFIED_LOG.get().asItem(), DDBlocks.PETRIFIED_WOOD.get().asItem(), DDBlocks.STRIPPED_PETRIFIED_WOOD.get().asItem());
        this.tag(ItemTags.BEACON_PAYMENT_ITEMS).add(DDItems.SILVER_INGOT.get());
        this.tag(ItemTags.STONE_CRAFTING_MATERIALS).add(DDBlocks.SHALE.get().asItem(), DDBlocks.ARIDROCK.get().asItem(), DDBlocks.LIMESTONE.get().asItem(), DDBlocks.GRIMESTONE.get().asItem());
        this.tag(ItemTags.STONE_TOOL_MATERIALS).add(DDBlocks.SHALE.get().asItem(), DDBlocks.ARIDROCK.get().asItem(), DDBlocks.LIMESTONE.get().asItem(), DDBlocks.GRIMESTONE.get().asItem());
    }
}
