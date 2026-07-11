package com.naterbobber.darkerdepths.block.blocksets;

import com.naterbobber.darkerdepths.init.DDBlocks;
import com.naterbobber.darkerdepths.init.DDItems;
import com.naterbobber.darkerdepths.util.DDTags;

import java.util.List;

public class DDBlockSets {
    public static final StoneBlockSet DARKSLATE = StoneBlockSet.builder()
            .standard(new StoneBlockSet.Group(DDBlocks.DARKSLATE, DDBlocks.DARKSLATE_STAIRS, DDBlocks.DARKSLATE_SLAB, DDBlocks.DARKSLATE_VERTICAL_SLAB, DDBlocks.DARKSLATE_WALL))
            .polished(new StoneBlockSet.Group(DDBlocks.POLISHED_DARKSLATE, DDBlocks.POLISHED_DARKSLATE_STAIRS, DDBlocks.POLISHED_DARKSLATE_SLAB, DDBlocks.POLISHED_DARKSLATE_VERTICAL_SLAB, null))
            .bricks(new StoneBlockSet.Group(DDBlocks.DARKSLATE_BRICKS, DDBlocks.DARKSLATE_BRICKS_STAIRS, DDBlocks.DARKSLATE_BRICKS_SLAB, DDBlocks.DARKSLATE_BRICKS_VERTICAL_SLAB, DDBlocks.DARKSLATE_BRICKS_WALL))
            .chiseled(DDBlocks.CHISELED_DARKSLATE_BRICKS)
            .crackedBricks(DDBlocks.CRACKED_DARKSLATE_BRICKS)
            .pillar(DDBlocks.DARKSLATE_PILLAR)
            .build();

    public static final StoneBlockSet ARIDROCK = StoneBlockSet.builder()
            .standard(new StoneBlockSet.Group(DDBlocks.ARIDROCK, DDBlocks.ARIDROCK_STAIRS, DDBlocks.ARIDROCK_SLAB, DDBlocks.ARIDROCK_VERTICAL_SLAB, DDBlocks.ARIDROCK_WALL))
            .polished(new StoneBlockSet.Group(DDBlocks.POLISHED_ARIDROCK, DDBlocks.POLISHED_ARIDROCK_STAIRS, DDBlocks.POLISHED_ARIDROCK_SLAB, DDBlocks.POLISHED_ARIDROCK_VERTICAL_SLAB, null))
            .bricks(new StoneBlockSet.Group(DDBlocks.ARIDROCK_BRICKS, DDBlocks.ARIDROCK_BRICKS_STAIRS, DDBlocks.ARIDROCK_BRICKS_SLAB, DDBlocks.ARIDROCK_BRICKS_VERTICAL_SLAB, DDBlocks.ARIDROCK_BRICKS_WALL))
            .chiseled(DDBlocks.CHISELED_ARIDROCK_BRICKS)
            .crackedBricks(DDBlocks.CRACKED_ARIDROCK_BRICKS)
            .pillar(DDBlocks.ARIDROCK_PILLAR)
            .build();

    public static final StoneBlockSet DUSKROCK = StoneBlockSet.builder()
            .standard(new StoneBlockSet.Group(DDBlocks.DUSKROCK, DDBlocks.DUSKROCK_STAIRS, DDBlocks.DUSKROCK_SLAB, DDBlocks.DUSKROCK_VERTICAL_SLAB, DDBlocks.DUSKROCK_WALL))
            .polished(new StoneBlockSet.Group(DDBlocks.POLISHED_DUSKROCK, DDBlocks.POLISHED_DUSKROCK_STAIRS, DDBlocks.POLISHED_DUSKROCK_SLAB, DDBlocks.POLISHED_DUSKROCK_VERTICAL_SLAB, null))
            .bricks(new StoneBlockSet.Group(DDBlocks.DUSKROCK_BRICKS, DDBlocks.DUSKROCK_BRICKS_STAIRS, DDBlocks.DUSKROCK_BRICKS_SLAB, DDBlocks.DUSKROCK_BRICKS_VERTICAL_SLAB, DDBlocks.DUSKROCK_BRICKS_WALL))
            .chiseled(DDBlocks.CHISELED_DUSKROCK_BRICKS)
            .crackedBricks(DDBlocks.CRACKED_DUSKROCK_BRICKS)
            .pillar(DDBlocks.DUSKROCK_PILLAR)
            .build();

    public static final StoneBlockSet GRIMESTONE = StoneBlockSet.builder()
            .standard(new StoneBlockSet.Group(DDBlocks.GRIMESTONE, DDBlocks.GRIMESTONE_STAIRS, DDBlocks.GRIMESTONE_SLAB, DDBlocks.GRIMESTONE_VERTICAL_SLAB, DDBlocks.GRIMESTONE_WALL))
            .polished(new StoneBlockSet.Group(DDBlocks.POLISHED_GRIMESTONE, DDBlocks.POLISHED_GRIMESTONE_STAIRS, DDBlocks.POLISHED_GRIMESTONE_SLAB, DDBlocks.POLISHED_GRIMESTONE_VERTICAL_SLAB, null))
            .bricks(new StoneBlockSet.Group(DDBlocks.GRIMESTONE_BRICKS, DDBlocks.GRIMESTONE_BRICKS_STAIRS, DDBlocks.GRIMESTONE_BRICKS_SLAB, DDBlocks.GRIMESTONE_BRICKS_VERTICAL_SLAB, DDBlocks.GRIMESTONE_BRICKS_WALL))
            .mossy(new StoneBlockSet.Group(DDBlocks.MOSSY_GRIMESTONE_BRICKS, DDBlocks.MOSSY_GRIMESTONE_BRICKS_STAIRS, DDBlocks.MOSSY_GRIMESTONE_BRICKS_SLAB, DDBlocks.MOSSY_GRIMESTONE_BRICKS_VERTICAL_SLAB, DDBlocks.MOSSY_GRIMESTONE_BRICKS_WALL))
            .chiseled(DDBlocks.CHISELED_GRIMESTONE_BRICKS)
            .crackedBricks(DDBlocks.CRACKED_GRIMESTONE_BRICKS)
            .pillar(DDBlocks.GRIMESTONE_PILLAR)
            .build();

    public static final StoneBlockSet GLIST = StoneBlockSet.builder()
            .standard(new StoneBlockSet.Group(DDBlocks.GLIST, DDBlocks.GLIST_STAIRS, DDBlocks.GLIST_SLAB, DDBlocks.GLIST_VERTICAL_SLAB, DDBlocks.GLIST_WALL))
            .polished(new StoneBlockSet.Group(DDBlocks.POLISHED_GLIST, DDBlocks.POLISHED_GLIST_STAIRS, DDBlocks.POLISHED_GLIST_SLAB, DDBlocks.POLISHED_GLIST_VERTICAL_SLAB, null))
            .bricks(new StoneBlockSet.Group(DDBlocks.GLIST_BRICKS, DDBlocks.GLIST_BRICKS_STAIRS, DDBlocks.GLIST_BRICKS_SLAB, DDBlocks.GLIST_BRICKS_VERTICAL_SLAB, DDBlocks.GLIST_BRICKS_WALL))
            .chiseled(DDBlocks.CHISELED_GLIST_BRICKS)
            .crackedBricks(DDBlocks.CRACKED_GLIST_BRICKS)
            .pillar(DDBlocks.GLIST_PILLAR)
            .build();

    public static final WoodBlockSet PETRIFIED = WoodBlockSet.builder()
            .log(DDBlocks.PETRIFIED_LOG)
            .wood(DDBlocks.PETRIFIED_WOOD)
            .strippedLog(DDBlocks.STRIPPED_PETRIFIED_LOG)
            .strippedWood(DDBlocks.STRIPPED_PETRIFIED_WOOD)
            .planks(DDBlocks.PETRIFIED_PLANKS)
            .boards(DDBlocks.PETRIFIED_BOARDS)
            .verticalPlanks(DDBlocks.VERTICAL_PETRIFIED_PLANKS)
            .stairs(DDBlocks.PETRIFIED_STAIRS)
            .slab(DDBlocks.PETRIFIED_SLAB)
            .verticalSlab(DDBlocks.PETRIFIED_VERTICAL_SLAB)
            .trimmedPlanks(DDBlocks.TRIMMED_PETRIFIED_PLANKS)
            .fence(DDBlocks.PETRIFIED_FENCE)
            .fenceGate(DDBlocks.PETRIFIED_FENCE_GATE)
            .door(DDBlocks.PETRIFIED_DOOR)
            .trapdoor(DDBlocks.PETRIFIED_TRAPDOOR)
            .bookshelf(DDBlocks.PETRIFIED_BOOKSHELF)
            .pressurePlate(DDBlocks.PETRIFIED_PRESSURE_PLATE)
            .button(DDBlocks.PETRIFIED_BUTTON)
            .sign(DDBlocks.PETRIFIED_SIGN)
            .wallSign(DDBlocks.PETRIFIED_WALL_SIGN)
            .hangingSign(DDBlocks.PETRIFIED_HANGING_SIGN)
            .wallHangingSign(DDBlocks.PETRIFIED_WALL_HANGING_SIGN)
            .post(DDBlocks.PETRIFIED_POST)
            .strippedPost(DDBlocks.STRIPPED_PETRIFIED_POST)
            .boat(DDItems.PETRIFIED_BOAT)
            .chestBoat(DDItems.PETRIFIED_CHEST_BOAT)
            .logTag(DDTags.Items.PETRIFIED_LOGS)
            .build();

    public static final WoodBlockSet GLOWSHROOM = WoodBlockSet.builder()
            .log(DDBlocks.GLOWSHROOM_STEM)
            .wood(DDBlocks.GLOWSHROOM_HYPHAE)
            .strippedLog(DDBlocks.STRIPPED_GLOWSHROOM_STEM)
            .strippedWood(DDBlocks.STRIPPED_GLOWSHROOM_HYPHAE)
            .planks(DDBlocks.GLOWSHROOM_PLANKS)
            .boards(DDBlocks.GLOWSHROOM_BOARDS)
            .verticalPlanks(DDBlocks.VERTICAL_GLOWSHROOM_PLANKS)
            .stairs(DDBlocks.GLOWSHROOM_STAIRS)
            .slab(DDBlocks.GLOWSHROOM_SLAB)
            .verticalSlab(DDBlocks.GLOWSHROOM_VERTICAL_SLAB)
            .trimmedPlanks(DDBlocks.TRIMMED_GLOWSHROOM_PLANKS)
            .fence(DDBlocks.GLOWSHROOM_FENCE)
            .fenceGate(DDBlocks.GLOWSHROOM_FENCE_GATE)
            .door(DDBlocks.GLOWSHROOM_DOOR)
            .trapdoor(DDBlocks.GLOWSHROOM_TRAPDOOR)
            .bookshelf(DDBlocks.GLOWSHROOM_BOOKSHELF)
            .pressurePlate(DDBlocks.GLOWSHROOM_PRESSURE_PLATE)
            .button(DDBlocks.GLOWSHROOM_BUTTON)
            .sign(DDBlocks.GLOWSHROOM_SIGN)
            .wallSign(DDBlocks.GLOWSHROOM_WALL_SIGN)
            .hangingSign(DDBlocks.GLOWSHROOM_HANGING_SIGN)
            .wallHangingSign(DDBlocks.GLOWSHROOM_WALL_HANGING_SIGN)
            .post(DDBlocks.GLOWSHROOM_POST)
            .strippedPost(DDBlocks.STRIPPED_GLOWSHROOM_POST)
            .boat(DDItems.GLOWSHROOM_BOAT)
            .chestBoat(DDItems.GLOWSHROOM_CHEST_BOAT)
            .logTag(DDTags.Items.GLOWSHROOM_STEMS)
            .build();

    public static final List<StoneBlockSet> STONE_BLOCK_SETS = List.of(
            DARKSLATE,
            ARIDROCK,
            DUSKROCK,
            GRIMESTONE,
            GLIST
    );

    public static final List<WoodBlockSet> WOOD_BLOCK_SETS = List.of(
            PETRIFIED,
            GLOWSHROOM
    );
}