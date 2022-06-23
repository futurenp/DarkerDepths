package com.naterbobber.darkerdepths.data;

import com.naterbobber.darkerdepths.DarkerDepths;
import com.naterbobber.darkerdepths.compat.init.ForgeItemTags;
import com.naterbobber.darkerdepths.init.DDBlocks;
import com.naterbobber.darkerdepths.init.DDItemTags;
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
        this.tag(DDItemTags.PETRIFIED_LOGS).add(DDBlocks.PETRIFIED_LOG.get().asItem(), DDBlocks.PETRIFIED_WOOD.get().asItem(), DDBlocks.STRIPPED_PETRIFIED_LOG.get().asItem(), DDBlocks.STRIPPED_PETRIFIED_WOOD.get().asItem());
        this.tag(ItemTags.PLANKS).add(DDBlocks.PETRIFIED_PLANKS.get().asItem());
        this.tag(ForgeItemTags.SILVER_INGOT).add(DDItems.SILVER_INGOT.get());
        this.tag(ForgeItemTags.COAL_ORES).add(DDBlocks.ARIDROCK_COAL_ORE.get().asItem()).add(DDBlocks.LIMESTONE_COAL_ORE.get().asItem());
        this.tag(ForgeItemTags.DIAMOND_ORES).add(DDBlocks.ARIDROCK_DIAMOND_ORE.get().asItem()).add(DDBlocks.LIMESTONE_DIAMOND_ORE.get().asItem());
        this.tag(ForgeItemTags.GOLD_ORES).add(DDBlocks.ARIDROCK_GOLD_ORE.get().asItem()).add(DDBlocks.LIMESTONE_GOLD_ORE.get().asItem());
        this.tag(ForgeItemTags.IRON_ORES).add(DDBlocks.ARIDROCK_IRON_ORE.get().asItem()).add(DDBlocks.LIMESTONE_IRON_ORE.get().asItem());
        this.tag(ForgeItemTags.LAPIS_ORES).add(DDBlocks.ARIDROCK_LAPIS_ORE.get().asItem()).add(DDBlocks.LIMESTONE_LAPIS_ORE.get().asItem());
        this.tag(ForgeItemTags.REDSTONE_ORES).add(DDBlocks.ARIDROCK_REDSTONE_ORE.get().asItem()).add(DDBlocks.LIMESTONE_REDSTONE_ORE.get().asItem());
        this.tag(ForgeItemTags.SILVER_ORES).add(DDBlocks.SILVER_ORE.get().asItem());
        this.tag(ForgeItemTags.INGOTS).addTags(ForgeItemTags.SILVER_INGOT);
        this.tag(ForgeItemTags.ORES).addTags(ForgeItemTags.SILVER_ORES);
        this.tag(ForgeItemTags.SILVER_STORAGE_BLOCKS).add(DDBlocks.SILVER_BLOCK.get().asItem());
        this.tag(ForgeItemTags.STORAGE_BLOCKS).addTag(ForgeItemTags.SILVER_STORAGE_BLOCKS);
        this.tag(ForgeItemTags.SILVER_RAW_ORES).add(DDItems.RAW_SILVER.get());
        this.tag(ForgeItemTags.RAW_ORES).addTag(ForgeItemTags.SILVER_RAW_ORES);
    }
}
