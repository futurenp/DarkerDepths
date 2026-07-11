package com.naterbobber.darkerdepths.data.tags;

import com.naterbobber.darkerdepths.DarkerDepths;
import com.naterbobber.darkerdepths.block.blocksets.DDBlockSets;
import com.naterbobber.darkerdepths.block.generic.VerticalSlabBlock;
import com.naterbobber.darkerdepths.init.DDBlocks;
import com.naterbobber.darkerdepths.util.DDTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.*;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class DDBlockTagsProvider extends BlockTagsProvider {

    public DDBlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, DarkerDepths.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        generateTypeTags();
        generateMinableTags();
        generateNeedsToolTags();
        generateModTags();
        generateWoodBlockSetTags();
        generateStoneBlockSetTags();
    }

    private void generateMinableTags() {
        this.tag(BlockTags.MINEABLE_WITH_HOE).add(
                DDBlocks.GLOWSHROOM_PILEUS.get(),
                DDBlocks.WAXED_GLOWSHROOM_PILEUS.get(),
                DDBlocks.SCORCHED_REMAINS_BLOCK.get(),
                DDBlocks.SCORCHED_REMAINS.get()
        );

        this.tag(BlockTags.MINEABLE_WITH_AXE).add(
                DDBlocks.GLOWSHROOM_HEART.get()
        );

        this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(
                DDBlocks.CRYSTAL_HUSK.get(),
                DDBlocks.LIVING_CRYSTAL.get(),
                DDBlocks.AMBER_BLOCK.get(),
                DDBlocks.AMBER_CLUSTER.get(),
                DDBlocks.GEYSER.get(),
                DDBlocks.MOSSY_GRIMESTONE.get(),
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
                DDBlocks.MAGMA_PAD.get()
        );

        this.tag(BlockTags.MINEABLE_WITH_SHOVEL).add(
                DDBlocks.ASH.get(),
                DDBlocks.ASH_BLOCK.get()
        );
    }

    private void generateNeedsToolTags() {
        this.tag(BlockTags.NEEDS_STONE_TOOL).add(
                DDBlocks.GEYSER.get(),
                DDBlocks.LIVING_CRYSTAL.get(),
                DDBlocks.CRYSTAL_HUSK.get(),
                DDBlocks.STONE_MELON.get(),
                DDBlocks.GLOWSHROOM_LAMP.get(),
                DDBlocks.TOMB.get()
        );

        this.tag(BlockTags.NEEDS_IRON_TOOL).add(
                DDBlocks.FORSAKEN_BRONZE_BLOCK.get(),
                DDBlocks.DEATH_ANCHOR.get(),
                DDBlocks.CRYSTAL_MELON.get()
        );
    }

    private void generateModTags() {
        this.tag(DDTags.Blocks.GEYSER_BOOSTERS).add(Blocks.MAGMA_BLOCK);
        this.tag(DDTags.Blocks.GEYSER_ASH_PROVIDERS).add(DDBlocks.SCORCHED_REMAINS_BLOCK.get());

        this.tag(DDTags.Blocks.GEYSER_BYPASSES).add(
                Blocks.SNOW,
                Blocks.MOSS_CARPET,
                Blocks.GLOW_LICHEN,
                Blocks.SCULK_VEIN,
                DDBlocks.GLOWSHROOM.get(),
                DDBlocks.GLOWSPURS.get(),
                DDBlocks.GLIMMERING_VINES.get(),
                DDBlocks.GLIMMERING_VINE_PLANT.get(),
                DDBlocks.DRY_SPROUTS.get(),
                DDBlocks.SCORCHER_LIGHT_BLOCK.get(),
                DDBlocks.SHELF_GLOWSHROOM.get()
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
                .addTag(BlockTags.REPLACEABLE)
                .addTag(BlockTags.BUTTONS)
                .addTag(BlockTags.LEAVES)
                .addTag(BlockTags.RAILS);

        this.tag(BlockTags.BASE_STONE_OVERWORLD).add(
                DDBlocks.DARKSLATE.get()
        );

        this.tag(BlockTags.DEEPSLATE_ORE_REPLACEABLES).add(
                DDBlocks.MOSSY_GRIMESTONE.get(),
                Blocks.PACKED_MUD
        );

        this.tag(BlockTags.OVERWORLD_CARVER_REPLACEABLES).add(
                DDBlocks.ARID_DEEPSLATE.get(),
                Blocks.PACKED_MUD,
                Blocks.MAGMA_BLOCK,
                DDBlocks.MOSSY_GRIMESTONE.get()
        );

        this.tag(DDTags.Blocks.ARID_GROUND).add(
                DDBlocks.ARIDROCK.get(),
                DDBlocks.DUSKROCK.get(),
                Blocks.PACKED_MUD
        );

        this.tag(DDTags.Blocks.GRIME_GROUND).add(
                DDBlocks.GRIMESTONE.get(),
                DDBlocks.MOSSY_GRIMESTONE.get()
        ).addTag(BlockTags.BASE_STONE_OVERWORLD);

        this.tag(DDTags.Blocks.GRIME_SURFACE).add(
                Blocks.STONE,
                Blocks.DEEPSLATE,
                Blocks.GRANITE,
                Blocks.ANDESITE,
                Blocks.DIORITE
        );

        this.tag(DDTags.Blocks.HUGE_GLOWSHROOM_GROWABLE).add(
            DDBlocks.MOSSY_GRIMESTONE.get(),
            DDBlocks.GLOWSHROOM_PILEUS.get()
        ).addTag(BlockTags.BASE_STONE_OVERWORLD);

        this.tag(DDTags.Blocks.HUSKS_SPAWNABLE_ON).add(DDBlocks.ARIDROCK.get());

        this.tag(BlockTags.BEACON_BASE_BLOCKS).add(DDBlocks.FORSAKEN_BRONZE_BLOCK.get());

        this.tag(BlockTags.WALL_POST_OVERRIDE).add(
                DDBlocks.VOID_SOUL_TORCH.get());

        this.tag(BlockTags.SNAPS_GOAT_HORN).add(
                DDBlocks.PETRIFIED_LOG.get(),
                DDBlocks.PETRIFIED_WOOD.get(),
                DDBlocks.POROUS_PETRIFIED_LOG.get(),
                DDBlocks.GLOWSHROOM_STEM.get(),
                DDBlocks.GLOWSHROOM_HYPHAE.get()
        );

        this.tag(Tags.Blocks.STORAGE_BLOCKS).add(
                DDBlocks.FORSAKEN_BRONZE_BLOCK.get(),
                DDBlocks.AMBER_BLOCK.get()
        );

        this.tag(BlockTags.REPLACEABLE).add(
                DDBlocks.SCORCHED_REMAINS.get(),
                DDBlocks.DRY_SPROUTS.get(),
                DDBlocks.PETRIFIED_ROOTS.get(),
                DDBlocks.PETRIFIED_ROOTS_PLANT.get(),
                DDBlocks.MOSSY_SPROUTS.get(),
                DDBlocks.GLOWSPURS.get(),
                DDBlocks.GLIMMERING_VINES.get(),
                DDBlocks.GLIMMERING_VINE_PLANT.get()
        );

        this.tag(BlockTags.LUSH_GROUND_REPLACEABLE).add(
                DDBlocks.MOSSY_GRIMESTONE.get()
        );

        this.tag(BlockTags.SWORD_EFFICIENT).add(
                DDBlocks.SCORCHED_REMAINS.get(),
                DDBlocks.DRY_SPROUTS.get(),
                DDBlocks.PETRIFIED_ROOTS.get(),
                DDBlocks.PETRIFIED_ROOTS_PLANT.get(),
                DDBlocks.MOSSY_SPROUTS.get(),
                DDBlocks.GLOWSPURS.get(),
                DDBlocks.GLIMMERING_VINES.get(),
                DDBlocks.GLIMMERING_VINE_PLANT.get(),
                DDBlocks.SHELF_GLOWSHROOM.get(),
                DDBlocks.GLOWSHROOM.get()
        );

        this.tag(DDTags.Blocks.VERY_HIGH_HEAT).add(
                Blocks.LAVA
        );

        this.tag(DDTags.Blocks.HIGH_HEAT).add(
                DDBlocks.SCORCHER_LIGHT_BLOCK.get(),
                Blocks.MAGMA_BLOCK,
                Blocks.LAVA_CAULDRON
        );

        this.tag(DDTags.Blocks.MEDIUM_HEAT).add(
                Blocks.SOUL_FIRE,
                Blocks.SOUL_CAMPFIRE
        );

        this.tag(DDTags.Blocks.LOW_HEAT).add(
                Blocks.FIRE,
                Blocks.CAMPFIRE,
                DDBlocks.SCORCHED_REMAINS_BLOCK.get()
        );

        this.tag(DDTags.Blocks.HEATABLE).add(
                DDBlocks.CRYSTAL_HUSK.get(),
                DDBlocks.LIVING_CRYSTAL.get(),
                DDBlocks.GEYSER.get()
        );

        this.tag(DDTags.Blocks.HEAT_PROVIDER)
                .addOptionalTag(DDTags.Blocks.VERY_HIGH_HEAT)
                .addOptionalTag(DDTags.Blocks.HIGH_HEAT)
                .addOptionalTag(DDTags.Blocks.MEDIUM_HEAT)
                .addOptionalTag(DDTags.Blocks.LOW_HEAT);
    }

    private void generateStoneBlockSetTags() {
        DDBlockSets.STONE_BLOCK_SETS.forEach(set -> {
            set.getAllBlocks().stream().map(DeferredHolder::get).forEachOrdered(block -> {
                this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(block);

                if (set == DDBlockSets.DARKSLATE) {
                    this.tag(DDTags.Blocks.HEATABLE).add(block);
                }
            });

            var base = set.getStandardGroup().base();
            if (base != null) {
                var baseBlock = base.get();
                this.tag(BlockTags.DEEPSLATE_ORE_REPLACEABLES).add(baseBlock);
                this.tag(BlockTags.OVERWORLD_CARVER_REPLACEABLES).add(baseBlock);
                this.tag(BlockTags.LUSH_GROUND_REPLACEABLE).add(baseBlock);
            }
        });
    }

    private void generateWoodBlockSetTags() {
        DDBlockSets.WOOD_BLOCK_SETS.forEach(set -> {
            set.getBlocks().stream().map(DeferredHolder::get).forEachOrdered(block -> {
                this.tag(BlockTags.MINEABLE_WITH_AXE).add(block);

                if (set == DDBlockSets.PETRIFIED) {
                    this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(block);
                }
            });

            this.tag(BlockTags.LOGS).add(
                    set.getLog().get(),
                    set.getWood().get(),
                    set.getStrippedLog().get(),
                    set.getStrippedWood().get()
            );
            this.tag(BlockTags.WOODEN_DOORS).add(set.getDoor().get());
            this.tag(BlockTags.WOODEN_TRAPDOORS).add(set.getTrapdoor().get());
            this.tag(BlockTags.WOODEN_SLABS).add(set.getSlab().get());
            this.tag(BlockTags.WOODEN_STAIRS).add(set.getStairs().get());
            this.tag(BlockTags.WOODEN_BUTTONS).add(set.getButton().get());
            this.tag(BlockTags.WOODEN_PRESSURE_PLATES).add(set.getPressurePlate().get());
            this.tag(BlockTags.WOODEN_FENCES).add(set.getFence().get());
            this.tag(BlockTags.PLANKS).add(set.getPlanks().get());
            this.tag(BlockTags.WALL_POST_OVERRIDE).add(
                    set.getSign().get(),
                    set.getWallSign().get(),
                    set.getPressurePlate().get()
            );
            this.tag(BlockTags.SNAPS_GOAT_HORN).add(
                    set.getLog().get(),
                    set.getWood().get()
            );
            this.tag(BlockTags.ENCHANTMENT_POWER_PROVIDER).add(set.getBookshelf().get());

            this.tag(Tags.Blocks.BOOKSHELVES).add(set.getBookshelf().get());
            this.tag(DDTags.Blocks.WOODEN_BOOKSHELVES).add(set.getBookshelf().get());
            this.tag(DDTags.Blocks.TRIMMED_PLANKS).add(set.getTrimmedPlanks().get());
            this.tag(DDTags.Blocks.WOODEN_BOARDS).add(set.getBoards().get());
            this.tag(DDTags.Blocks.VERTICAL_PLANKS).add(set.getVerticalPlanks().get());
            this.tag(DDTags.Blocks.POSTS).add(
                    set.getPost().get(),
                    set.getStrippedPost().get()
            );
        });
    }

    private void generateTypeTags() {
        DDBlocks.BLOCKS.getEntries()
                .stream()
                .map(DeferredHolder::get)
                .forEach(block -> {
                    switch (block) {
                        case SlabBlock b -> this.tag(BlockTags.SLABS).add(block);
                        case StairBlock b -> this.tag(BlockTags.STAIRS).add(block);
                        case WallBlock b -> this.tag(BlockTags.WALLS).add(block);
                        case DoorBlock b -> this.tag(BlockTags.DOORS).add(block);
                        case TrapDoorBlock b -> this.tag(BlockTags.TRAPDOORS).add(block);
                        case ButtonBlock b -> this.tag(BlockTags.BUTTONS).add(block);
                        case PressurePlateBlock b -> this.tag(BlockTags.PRESSURE_PLATES).add(block);
                        case StandingSignBlock b -> {
                            this.tag(BlockTags.STANDING_SIGNS).add(block);
                            this.tag(BlockTags.SIGNS).add(block);
                            this.tag(BlockTags.ALL_SIGNS).add(block);
                        }
                        case WallSignBlock b -> {
                            this.tag(BlockTags.WALL_SIGNS).add(block);
                            this.tag(BlockTags.SIGNS).add(block);
                            this.tag(BlockTags.ALL_SIGNS).add(block);
                        }
                        case WallHangingSignBlock b -> {
                            this.tag(BlockTags.WALL_HANGING_SIGNS).add(block);
                            this.tag(BlockTags.ALL_HANGING_SIGNS).add(block);
                            this.tag(BlockTags.ALL_SIGNS).add(block);
                        }
                        case CeilingHangingSignBlock b -> {
                            this.tag(BlockTags.CEILING_HANGING_SIGNS).add(block);
                            this.tag(BlockTags.ALL_HANGING_SIGNS).add(block);
                            this.tag(BlockTags.ALL_SIGNS).add(block);
                        }
                        case FenceBlock b -> this.tag(BlockTags.FENCES).add(block);
                        case FenceGateBlock b -> {
                            this.tag(BlockTags.FENCE_GATES).add(block);
                            this.tag(Tags.Blocks.FENCE_GATES).add(block);
                            this.tag(Tags.Blocks.FENCE_GATES_WOODEN).add(block);
                        }
                        case VerticalSlabBlock b -> this.tag(DDTags.Blocks.VERTICAL_SLAB).add(block);
                        case FlowerPotBlock b -> this.tag(BlockTags.FLOWER_POTS).add(block);
                        default -> {}
                    }
                });
    }
}