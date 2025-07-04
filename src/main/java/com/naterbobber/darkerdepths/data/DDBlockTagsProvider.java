package com.naterbobber.darkerdepths.data;

import com.naterbobber.darkerdepths.DarkerDepths;
import com.naterbobber.darkerdepths.compat.init.ForgeBlockTags;
import com.naterbobber.darkerdepths.init.DDBlockTags;
import com.naterbobber.darkerdepths.init.DDBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class DDBlockTagsProvider extends BlockTagsProvider {

    public DDBlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, DarkerDepths.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        this.tag(BlockTags.LOGS).add(
                DDBlocks.PETRIFIED_LOG.get(),
                DDBlocks.STRIPPED_PETRIFIED_LOG.get(),
                DDBlocks.PETRIFIED_WOOD.get(),
                DDBlocks.STRIPPED_PETRIFIED_WOOD.get()
        );
        this.tag(BlockTags.BASE_STONE_OVERWORLD).add(
                DDBlocks.DARKSLATE.get(),
                DDBlocks.ARIDROCK.get(),
                DDBlocks.LIMESTONE.get(),
                DDBlocks.GRIMESTONE.get()
        );
        this.tag(BlockTags.WALLS).add(
                DDBlocks.DARKSLATE_WALL.get(),
                DDBlocks.ARIDROCK_WALL.get(),
                DDBlocks.LIMESTONE_WALL.get(),
                DDBlocks.GRIMESTONE_WALL.get(),
                DDBlocks.DARKSLATE_BRICKS_WALL.get(),
                DDBlocks.ARIDROCK_BRICKS_WALL.get(),
                DDBlocks.LIMESTONE_BRICKS_WALL.get(),
                DDBlocks.GRIMESTONE_BRICKS_WALL.get()
        );
        this.tag(BlockTags.WOODEN_FENCES).add(DDBlocks.PETRIFIED_FENCE.get());
        this.tag(BlockTags.FENCE_GATES).add(DDBlocks.PETRIFIED_FENCE_GATE.get());

        this.tag(BlockTags.MINEABLE_WITH_HOE).add(
                DDBlocks.GLOWSHROOM_BLOCK.get(),
                DDBlocks.GLOWSHROOM_STEM.get(),
                DDBlocks.GLOWSHROOM_HEART.get()
        );
        this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(
                DDBlocks.DEAD_LIVING_CRYSTAL.get(),
                DDBlocks.LIVING_CRYSTAL.get(),
                DDBlocks.DARKSLATE.get(),
                DDBlocks.ARIDROCK.get(),
                DDBlocks.LIMESTONE.get(),
                DDBlocks.GRIMESTONE.get(),
                DDBlocks.DARKSLATE_SLAB.get(),
                DDBlocks.DARKSLATE_STAIRS.get(),
                DDBlocks.DARKSLATE_WALL.get(),
                DDBlocks.ARIDROCK_SLAB.get(),
                DDBlocks.ARIDROCK_STAIRS.get(),
                DDBlocks.ARIDROCK_WALL.get(),
                DDBlocks.LIMESTONE_SLAB.get(),
                DDBlocks.LIMESTONE_STAIRS.get(),
                DDBlocks.LIMESTONE_WALL.get(),
                DDBlocks.GRIMESTONE_SLAB.get(),
                DDBlocks.GRIMESTONE_STAIRS.get(),
                DDBlocks.GRIMESTONE_WALL.get(),
                DDBlocks.POLISHED_DARKSLATE.get(),
                DDBlocks.POLISHED_DARKSLATE_SLAB.get(),
                DDBlocks.POLISHED_DARKSLATE_STAIRS.get(),
                DDBlocks.POLISHED_ARIDROCK.get(),
                DDBlocks.POLISHED_ARIDROCK_SLAB.get(),
                DDBlocks.POLISHED_ARIDROCK_STAIRS.get(),
                DDBlocks.POLISHED_LIMESTONE.get(),
                DDBlocks.POLISHED_LIMESTONE_SLAB.get(),
                DDBlocks.POLISHED_LIMESTONE_STAIRS.get(),
                DDBlocks.POLISHED_GRIMESTONE.get(),
                DDBlocks.POLISHED_GRIMESTONE_SLAB.get(),
                DDBlocks.POLISHED_GRIMESTONE_STAIRS.get(),
                DDBlocks.DARKSLATE_BRICKS.get(),
                DDBlocks.DARKSLATE_BRICKS_STAIRS.get(),
                DDBlocks.DARKSLATE_BRICKS_SLAB.get(),
                DDBlocks.DARKSLATE_BRICKS_WALL.get(),
                DDBlocks.ARIDROCK_BRICKS.get(),
                DDBlocks.ARIDROCK_BRICKS_STAIRS.get(),
                DDBlocks.ARIDROCK_BRICKS_SLAB.get(),
                DDBlocks.ARIDROCK_BRICKS_WALL.get(),
                DDBlocks.LIMESTONE_BRICKS.get(),
                DDBlocks.LIMESTONE_BRICKS_STAIRS.get(),
                DDBlocks.LIMESTONE_BRICKS_SLAB.get(),
                DDBlocks.LIMESTONE_BRICKS_WALL.get(),
                DDBlocks.GRIMESTONE_BRICKS.get(),
                DDBlocks.GRIMESTONE_BRICKS_STAIRS.get(),
                DDBlocks.GRIMESTONE_BRICKS_SLAB.get(),
                DDBlocks.GRIMESTONE_BRICKS_WALL.get(),
                DDBlocks.AMBER_BLOCK.get(),
                DDBlocks.AMBER_CLUSTER.get(),
                DDBlocks.GEYSER.get(),
                DDBlocks.PETRIFIED_PLANKS.get(),
                DDBlocks.PETRIFIED_SLAB.get(),
                DDBlocks.PETRIFIED_STAIRS.get(),
                DDBlocks.PETRIFIED_FENCE.get(),
                DDBlocks.PETRIFIED_FENCE_GATE.get(),
                DDBlocks.PETRIFIED_TRAPDOOR.get(),
                DDBlocks.PETRIFIED_SIGN.get(),
                DDBlocks.PETRIFIED_WALL_SIGN.get(),
                DDBlocks.PETRIFIED_DOOR.get(),
                DDBlocks.PETRIFIED_BUTTON.get(),
                DDBlocks.PETRIFIED_PRESSURE_PLATE.get(),
                DDBlocks.PETRIFIED_WOOD.get(),
                DDBlocks.PETRIFIED_LOG.get(),
                DDBlocks.STRIPPED_PETRIFIED_WOOD.get(),
                DDBlocks.STRIPPED_PETRIFIED_LOG.get(),
                DDBlocks.POROUS_PETRIFIED_LOG.get(),
                DDBlocks.MOSSY_GRIMESTONE.get(),
                DDBlocks.CRACKED_DARKSLATE_BRICKS.get(),
                DDBlocks.CRACKED_ARIDROCK_BRICKS.get(),
                DDBlocks.CRACKED_LIMESTONE_BRICKS.get(),
                DDBlocks.CRACKED_GRIMESTONE_BRICKS.get(),
                DDBlocks.CHISELED_DARKSLATE_BRICKS.get(),
                DDBlocks.CHISELED_ARIDROCK_BRICKS.get(),
                DDBlocks.CHISELED_LIMESTONE_BRICKS.get(),
                DDBlocks.CHISELED_GRIMESTONE_BRICKS.get(),
                DDBlocks.STONE_MELON.get(),
                DDBlocks.CRYSTAL_MELON.get(),
                DDBlocks.ARID_DEEPSLATE.get(),
                DDBlocks.GLOWSHROOM_LAMP.get(),
                DDBlocks.GLOWSHROOM_LANTERN.get(),
                DDBlocks.SKULL_WALL.get(),
                DDBlocks.DEATH_ANCHOR.get()
        );
        this.tag(BlockTags.MINEABLE_WITH_SHOVEL).add(
                DDBlocks.ASH.get(),
                DDBlocks.ASH_BLOCK.get()
        );

        this.tag(DDBlockTags.GEYSER_BOOSTERS).add(Blocks.MAGMA_BLOCK);

        //only works on snow?
        //Also only works with 1 snow layer
        //fix later ig
        this.tag(DDBlockTags.GEYSER_BYPASSES).add(
                Blocks.SNOW,
                Blocks.MOSS_CARPET,
                Blocks.GLOW_LICHEN,
                Blocks.SCULK_VEIN,
                DDBlocks.GLOWSHROOM.get(),
                DDBlocks.GLOWSPURS.get(),
                DDBlocks.GLIMMERING_VINES.get(),
                DDBlocks.GLIMMERING_VINE_PLANT.get(),
                DDBlocks.DRY_SPROUTS.get())
                .addTag(BlockTags.WOOL_CARPETS)
                .addTag(BlockTags.ALL_SIGNS)
                .addTag(BlockTags.ALL_HANGING_SIGNS)
                .addTag(BlockTags.BEDS)
                .addTag(BlockTags.CROPS)
                .addTag(BlockTags.CAMPFIRES)
                .addTag(BlockTags.CANDLES)
                .addTag(BlockTags.BEDS)
                .addTag(BlockTags.CORAL_PLANTS)
                .addTag(BlockTags.FENCES)
                .addTag(BlockTags.FENCE_GATES)
                .addTag(BlockTags.REPLACEABLE_BY_TREES)
                .addTag(BlockTags.LUSH_GROUND_REPLACEABLE)
                .addTag(BlockTags.SAPLINGS);
        this.tag(DDBlockTags.HUSKS_SPAWNABLE_ON).add(DDBlocks.ARIDROCK.get());
    }
}
