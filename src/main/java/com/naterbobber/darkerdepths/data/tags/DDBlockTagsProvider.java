package com.naterbobber.darkerdepths.data.tags;

import com.naterbobber.darkerdepths.DarkerDepths;
import com.naterbobber.darkerdepths.block.generic.VerticalSlabBlock;
import com.naterbobber.darkerdepths.init.DDBlocks;
import com.naterbobber.darkerdepths.util.DDTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.*;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class DDBlockTagsProvider extends BlockTagsProvider {

    public DDBlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, DarkerDepths.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        addType(SlabBlock.class, BlockTags.SLABS);
        addType(StairBlock.class, BlockTags.STAIRS);
        addType(WallBlock.class, BlockTags.WALLS);
        addType(DoorBlock.class, BlockTags.DOORS);
        addType(TrapDoorBlock.class, BlockTags.TRAPDOORS);
        addType(ButtonBlock.class, BlockTags.BUTTONS);
        addType(PressurePlateBlock.class, BlockTags.PRESSURE_PLATES);
        addType(StandingSignBlock.class, BlockTags.STANDING_SIGNS, BlockTags.SIGNS);
        addType(WallSignBlock.class, BlockTags.WALL_SIGNS, BlockTags.SIGNS);
        addType(WallHangingSignBlock.class, BlockTags.WALL_HANGING_SIGNS, BlockTags.ALL_HANGING_SIGNS);
        addType(CeilingHangingSignBlock.class, BlockTags.CEILING_HANGING_SIGNS, BlockTags.ALL_HANGING_SIGNS);
        addType(SignBlock.class, BlockTags.ALL_SIGNS);

        List<Block> PETRIFIED = List.of(
                DDBlocks.PETRIFIED_PLANKS.get(),
                DDBlocks.PETRIFIED_SLAB.get(),
                DDBlocks.PETRIFIED_VERTICAL_SLAB.get(),
                DDBlocks.PETRIFIED_STAIRS.get(),
                DDBlocks.PETRIFIED_PRESSURE_PLATE.get(),
                DDBlocks.PETRIFIED_BUTTON.get(),
                DDBlocks.PETRIFIED_POST.get(),
                DDBlocks.PETRIFIED_FENCE.get(),
                DDBlocks.PETRIFIED_FENCE_GATE.get(),
                DDBlocks.PETRIFIED_DOOR.get(),
                DDBlocks.PETRIFIED_TRAPDOOR.get(),
                DDBlocks.PETRIFIED_SIGN.get(),
                DDBlocks.PETRIFIED_HANGING_SIGN.get(),
                DDBlocks.PETRIFIED_WALL_SIGN.get(),
                DDBlocks.PETRIFIED_WALL_HANGING_SIGN.get(),
                DDBlocks.PETRIFIED_LOG.get(),
                DDBlocks.STRIPPED_PETRIFIED_LOG.get(),
                DDBlocks.PETRIFIED_WOOD.get(),
                DDBlocks.STRIPPED_PETRIFIED_WOOD.get(),
                DDBlocks.POROUS_PETRIFIED_LOG.get(),
                DDBlocks.PETRIFIED_BOARDS.get(),
                DDBlocks.PETRIFIED_BOOKSHELF.get(),
                DDBlocks.TRIMMED_PETRIFIED_PLANKS.get()
        );

        PETRIFIED.forEach(block -> {
            this.tag(BlockTags.MINEABLE_WITH_AXE).add(block);
            this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(block);
        });

        this.tag(BlockTags.LOGS).add(
                DDBlocks.PETRIFIED_LOG.get(),
                DDBlocks.STRIPPED_PETRIFIED_LOG.get(),
                DDBlocks.PETRIFIED_WOOD.get(),
                DDBlocks.STRIPPED_PETRIFIED_WOOD.get()
        );
        this.tag(BlockTags.BASE_STONE_OVERWORLD).add(
                DDBlocks.DARKSLATE.get(),
                DDBlocks.ARIDROCK.get(),
                DDBlocks.DUSKROCK.get(),
                DDBlocks.GRIMESTONE.get()
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
                DDBlocks.DUSKROCK.get(),
                DDBlocks.GRIMESTONE.get(),
                DDBlocks.DARKSLATE_SLAB.get(),
                DDBlocks.DARKSLATE_STAIRS.get(),
                DDBlocks.DARKSLATE_WALL.get(),
                DDBlocks.ARIDROCK_SLAB.get(),
                DDBlocks.ARIDROCK_STAIRS.get(),
                DDBlocks.ARIDROCK_WALL.get(),
                DDBlocks.DUSKROCK_SLAB.get(),
                DDBlocks.DUSKROCK_STAIRS.get(),
                DDBlocks.DUSKROCK_WALL.get(),
                DDBlocks.GRIMESTONE_SLAB.get(),
                DDBlocks.GRIMESTONE_STAIRS.get(),
                DDBlocks.GRIMESTONE_WALL.get(),
                DDBlocks.POLISHED_DARKSLATE.get(),
                DDBlocks.POLISHED_DARKSLATE_SLAB.get(),
                DDBlocks.POLISHED_DARKSLATE_STAIRS.get(),
                DDBlocks.POLISHED_ARIDROCK.get(),
                DDBlocks.POLISHED_ARIDROCK_SLAB.get(),
                DDBlocks.POLISHED_ARIDROCK_STAIRS.get(),
                DDBlocks.POLISHED_DUSKROCK.get(),
                DDBlocks.POLISHED_DUSKROCK_SLAB.get(),
                DDBlocks.POLISHED_DUSKROCK_STAIRS.get(),
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
                DDBlocks.DUSKROCK_BRICKS.get(),
                DDBlocks.DUSKROCK_BRICKS_STAIRS.get(),
                DDBlocks.DUSKROCK_BRICKS_SLAB.get(),
                DDBlocks.DUSKROCK_BRICKS_WALL.get(),
                DDBlocks.GRIMESTONE_BRICKS.get(),
                DDBlocks.GRIMESTONE_BRICKS_STAIRS.get(),
                DDBlocks.GRIMESTONE_BRICKS_SLAB.get(),
                DDBlocks.GRIMESTONE_BRICKS_WALL.get(),
                DDBlocks.AMBER_BLOCK.get(),
                DDBlocks.AMBER_CLUSTER.get(),
                DDBlocks.GEYSER.get(),
                DDBlocks.MOSSY_GRIMESTONE.get(),
                DDBlocks.CRACKED_DARKSLATE_BRICKS.get(),
                DDBlocks.CRACKED_ARIDROCK_BRICKS.get(),
                DDBlocks.CRACKED_DUSKROCK_BRICKS.get(),
                DDBlocks.CRACKED_GRIMESTONE_BRICKS.get(),
                DDBlocks.CHISELED_DARKSLATE_BRICKS.get(),
                DDBlocks.CHISELED_ARIDROCK_BRICKS.get(),
                DDBlocks.CHISELED_DUSKROCK_BRICKS.get(),
                DDBlocks.CHISELED_GRIMESTONE_BRICKS.get(),
                DDBlocks.STONE_MELON.get(),
                DDBlocks.CRYSTAL_MELON.get(),
                DDBlocks.ARID_DEEPSLATE.get(),
                DDBlocks.GLOWSHROOM_LAMP.get(),
                DDBlocks.GLOWSHROOM_LANTERN.get(),
                DDBlocks.SKULL_WALL.get(),
                DDBlocks.DEATH_ANCHOR.get(),
                DDBlocks.TOMB.get(),
                DDBlocks.VOID_SOUL_JAR.get(),
                DDBlocks.PARANOIA_ALTAR.get(),
                DDBlocks.CRYSTAL_MELON.get(),
                DDBlocks.FORSAKEN_BRONZE_BLOCK.get(),
                DDBlocks.ARIDROCK_PILLAR.get()
        );

        this.tag(BlockTags.MINEABLE_WITH_SHOVEL).add(
                DDBlocks.ASH.get(),
                DDBlocks.ASH_BLOCK.get()
        );

        this.tag(BlockTags.NEEDS_STONE_TOOL).add(
                DDBlocks.GEYSER.get(),
                DDBlocks.LIVING_CRYSTAL.get(),
                DDBlocks.DEAD_LIVING_CRYSTAL.get(),
                DDBlocks.STONE_MELON.get(),
                DDBlocks.GLOWSHROOM_LAMP.get(),
                DDBlocks.TOMB.get()
        );

        this.tag(BlockTags.NEEDS_IRON_TOOL).add(
                DDBlocks.FORSAKEN_BRONZE_BLOCK.get(),
                DDBlocks.DEATH_ANCHOR.get(),
                DDBlocks.CRYSTAL_MELON.get()
        );

        this.tag(Tags.Blocks.STORAGE_BLOCKS)
                .add(DDBlocks.FORSAKEN_BRONZE_BLOCK.get());

        this.tag(Tags.Blocks.BOOKSHELVES).add(
                DDBlocks.PETRIFIED_BOOKSHELF.get()
        );

        this.tag(DDTags.Blocks.WOODEN_BOOKSHELVES).add(
                DDBlocks.PETRIFIED_BOOKSHELF.get()
        );

        this.tag(BlockTags.ENCHANTMENT_POWER_PROVIDER).add(
                DDBlocks.PETRIFIED_BOOKSHELF.get()
        );

        this.tag(DDTags.Blocks.TRIMMED_PLANKS).add(
                DDBlocks.TRIMMED_PETRIFIED_PLANKS.get()
        );

        this.tag(DDTags.Blocks.WOODEN_BOARDS).add(
                DDBlocks.PETRIFIED_BOARDS.get()
        );

        this.tag(DDTags.Blocks.GEYSER_BOOSTERS).add(Blocks.MAGMA_BLOCK);

        this.tag(DDTags.Blocks.GEYSER_BYPASSES)
                .add(
                        Blocks.SNOW,
                        Blocks.MOSS_CARPET,
                        Blocks.GLOW_LICHEN,
                        Blocks.SCULK_VEIN,
                        DDBlocks.GLOWSHROOM.get(),
                        DDBlocks.GLOWSPURS.get(),
                        DDBlocks.GLIMMERING_VINES.get(),
                        DDBlocks.GLIMMERING_VINE_PLANT.get(),
                        DDBlocks.DRY_SPROUTS.get()
                )
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
                .addTag(BlockTags.REPLACEABLE_BY_TREES)
                .addTag(BlockTags.REPLACEABLE)
                .addTag(BlockTags.BUTTONS)
                .addTag(BlockTags.LEAVES)
                .addTag(BlockTags.RAILS);

        this.tag(DDTags.Blocks.HUSKS_SPAWNABLE_ON).add(DDBlocks.ARIDROCK.get());
        this.tag(BlockTags.BEACON_BASE_BLOCKS).add(DDBlocks.FORSAKEN_BRONZE_BLOCK.get());

        this.tag(BlockTags.WALL_POST_OVERRIDE).add(
                DDBlocks.PETRIFIED_SIGN.get(),
                DDBlocks.PETRIFIED_WALL_SIGN.get(),
                DDBlocks.VOID_SOUL_TORCH.get(),
                DDBlocks.PETRIFIED_PRESSURE_PLATE.get()
        );

        this.tag(BlockTags.WOODEN_DOORS).add(
                DDBlocks.PETRIFIED_DOOR.get()
        );

        this.tag(BlockTags.WOODEN_TRAPDOORS).add(
                DDBlocks.PETRIFIED_TRAPDOOR.get()
        );

        this.tag(BlockTags.WOODEN_SLABS).add(
                DDBlocks.PETRIFIED_SLAB.get()
        );

        this.tag(BlockTags.WOODEN_STAIRS).add(
                DDBlocks.PETRIFIED_STAIRS.get()
        );

        this.tag(BlockTags.WOODEN_BUTTONS).add(
                DDBlocks.PETRIFIED_BUTTON.get()
        );

        this.tag(BlockTags.WOODEN_PRESSURE_PLATES).add(
                DDBlocks.PETRIFIED_PRESSURE_PLATE.get()
        );

        this.tag(BlockTags.SNAPS_GOAT_HORN).add(
                DDBlocks.PETRIFIED_LOG.get(),
                DDBlocks.POROUS_PETRIFIED_LOG.get()
        );

        this.tag(BlockTags.PLANKS).add(
                DDBlocks.PETRIFIED_PLANKS.get()
        );
    }

    private void addType(Class blockType, TagKey... tags) {
        DDBlocks.BLOCKS.getEntries()
                .stream()
                .map(DeferredHolder::get)
                .filter(blockType::isInstance)
                .forEach(block -> Arrays.stream(tags).forEach(tag -> this.tag(tag).add(block)));
    }
}