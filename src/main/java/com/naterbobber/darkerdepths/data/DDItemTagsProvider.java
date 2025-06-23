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
        this.tag(ItemTags.STONE_CRAFTING_MATERIALS).add(DDBlocks.DARKSLATE.get().asItem(), DDBlocks.ARIDROCK.get().asItem(), DDBlocks.LIMESTONE.get().asItem(), DDBlocks.GRIMESTONE.get().asItem());
        this.tag(ItemTags.STONE_TOOL_MATERIALS).add(DDBlocks.DARKSLATE.get().asItem(), DDBlocks.ARIDROCK.get().asItem(), DDBlocks.LIMESTONE.get().asItem(), DDBlocks.GRIMESTONE.get().asItem());
        this.tag(DDItemTags.PETRIFIED_LOGS).add(DDBlocks.PETRIFIED_LOG.get().asItem(), DDBlocks.PETRIFIED_WOOD.get().asItem(), DDBlocks.STRIPPED_PETRIFIED_LOG.get().asItem(), DDBlocks.STRIPPED_PETRIFIED_WOOD.get().asItem());
        this.tag(ItemTags.PLANKS).add(DDBlocks.PETRIFIED_PLANKS.get().asItem());
        this.tag(ForgeItemTags.ROPES).add(DDItems.ROPE.get());
    }
}
