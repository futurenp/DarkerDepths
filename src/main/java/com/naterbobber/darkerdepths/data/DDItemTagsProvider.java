package com.naterbobber.darkerdepths.data;

import com.naterbobber.darkerdepths.DarkerDepths;
import com.naterbobber.darkerdepths.compat.init.ForgeItemTags;
import com.naterbobber.darkerdepths.init.DDBlocks;
import com.naterbobber.darkerdepths.init.DDItemTags;
import com.naterbobber.darkerdepths.init.DDItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class DDItemTagsProvider extends ItemTagsProvider {

    public DDItemTagsProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> provider, CompletableFuture<TagLookup<Block>> blockTagsProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(packOutput, provider, blockTagsProvider, DarkerDepths.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        this.tag(ItemTags.LOGS).add(DDBlocks.PETRIFIED_LOG.get().asItem(), DDBlocks.STRIPPED_PETRIFIED_LOG.get().asItem(), DDBlocks.PETRIFIED_WOOD.get().asItem(), DDBlocks.STRIPPED_PETRIFIED_WOOD.get().asItem());
        this.tag(ItemTags.BEACON_PAYMENT_ITEMS).add(DDItems.SILVER_INGOT.get());
        this.tag(ItemTags.STONE_CRAFTING_MATERIALS).add(DDBlocks.SHALE.get().asItem(), DDBlocks.ARIDROCK.get().asItem(), DDBlocks.LIMESTONE.get().asItem(), DDBlocks.GRIMESTONE.get().asItem());
        this.tag(ItemTags.STONE_TOOL_MATERIALS).add(DDBlocks.SHALE.get().asItem(), DDBlocks.ARIDROCK.get().asItem(), DDBlocks.LIMESTONE.get().asItem(), DDBlocks.GRIMESTONE.get().asItem());
        this.tag(DDItemTags.PETRIFIED_LOGS).add(DDBlocks.PETRIFIED_LOG.get().asItem(), DDBlocks.PETRIFIED_WOOD.get().asItem(), DDBlocks.STRIPPED_PETRIFIED_LOG.get().asItem(), DDBlocks.STRIPPED_PETRIFIED_WOOD.get().asItem());
        this.tag(ItemTags.PLANKS).add(DDBlocks.PETRIFIED_PLANKS.get().asItem());
        this.tag(ForgeItemTags.SILVER_INGOT).add(DDItems.SILVER_INGOT.get());
        this.tag(Tags.Items.ORES_COAL).add(DDBlocks.ARIDROCK_COAL_ORE.get().asItem()).add(DDBlocks.LIMESTONE_COAL_ORE.get().asItem());
        this.tag(Tags.Items.ORES_DIAMOND).add(DDBlocks.ARIDROCK_DIAMOND_ORE.get().asItem()).add(DDBlocks.LIMESTONE_DIAMOND_ORE.get().asItem());
        this.tag(Tags.Items.ORES_GOLD).add(DDBlocks.ARIDROCK_GOLD_ORE.get().asItem()).add(DDBlocks.LIMESTONE_GOLD_ORE.get().asItem());
        this.tag(Tags.Items.ORES_IRON).add(DDBlocks.ARIDROCK_IRON_ORE.get().asItem()).add(DDBlocks.LIMESTONE_IRON_ORE.get().asItem());
        this.tag(Tags.Items.ORES_LAPIS).add(DDBlocks.ARIDROCK_LAPIS_ORE.get().asItem()).add(DDBlocks.LIMESTONE_LAPIS_ORE.get().asItem());
        this.tag(Tags.Items.ORES_REDSTONE).add(DDBlocks.ARIDROCK_REDSTONE_ORE.get().asItem()).add(DDBlocks.LIMESTONE_REDSTONE_ORE.get().asItem());
        this.tag(ForgeItemTags.SILVER_ORES).add(DDBlocks.SILVER_ORE.get().asItem());
        this.tag(Tags.Items.INGOTS).addTag(ForgeItemTags.SILVER_INGOT);
        this.tag(Tags.Items.ORES).addTag(ForgeItemTags.SILVER_ORES);
        this.tag(ForgeItemTags.SILVER_STORAGE_BLOCKS).add(DDBlocks.SILVER_BLOCK.get().asItem());
        this.tag(Tags.Items.STORAGE_BLOCKS).addTag(ForgeItemTags.SILVER_STORAGE_BLOCKS);
        this.tag(ForgeItemTags.SILVER_RAW_ORES).add(DDItems.RAW_SILVER.get());
        this.tag(Tags.Items.RAW_MATERIALS).addTag(ForgeItemTags.SILVER_RAW_ORES);
    }

}
