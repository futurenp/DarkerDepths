package com.naterbobber.darkerdepths.data.recipes;

import com.naterbobber.darkerdepths.DarkerDepths;
import com.naterbobber.darkerdepths.init.DDBlocks;
import com.naterbobber.darkerdepths.init.DDItems;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class UniqueRecipes {
    public static void create(RecipeOutput recipeOutput) {
        ShapedRecipeBuilder
                .shaped(RecipeCategory.BUILDING_BLOCKS, DDBlocks.POROUS_PETRIFIED_LOG.get())
                .define('#', DDItems.AMBER.get())
                .define('C', DDBlocks.PETRIFIED_LOG.get())
                .pattern("###")
                .pattern("#C#")
                .pattern("###")
                .unlockedBy("has_amber", has(DDItems.AMBER.get())).save(recipeOutput);

        ShapedRecipeBuilder
                .shaped(RecipeCategory.BUILDING_BLOCKS, DDBlocks.ARIDROCK.get(), 4)
                .define('#', Blocks.COBBLESTONE)
                .define('C', Blocks.SAND)
                .pattern("#C")
                .pattern("C#")
                .unlockedBy("has_cobblestone", has(Blocks.COBBLESTONE)).save(recipeOutput);

        ShapedRecipeBuilder
                .shaped(RecipeCategory.BUILDING_BLOCKS, DDBlocks.DUSKROCK.get(), 4)
                .define('#', Blocks.MUD)
                .define('C', DDBlocks.ARIDROCK.get())
                .pattern("#C")
                .pattern("C#")
                .unlockedBy("has_aridrock", has(DDBlocks.ARIDROCK.get())).save(recipeOutput);

        ShapedRecipeBuilder
                .shaped(RecipeCategory.BUILDING_BLOCKS, DDBlocks.DARKSLATE.get(), 4)
                .define('#', Blocks.COBBLED_DEEPSLATE)
                .define('C', Blocks.BLACKSTONE)
                .pattern("#C")
                .pattern("C#")
                .unlockedBy("has_cobbled_deepslate", has(Blocks.COBBLED_DEEPSLATE)).save(recipeOutput);

        ShapedRecipeBuilder
                .shaped(RecipeCategory.BUILDING_BLOCKS, DDBlocks.GRIMESTONE.get(), 4)
                .define('#', DDItems.GLOW_GRIME.get())
                .define('C', Blocks.COBBLESTONE)
                .pattern("#C")
                .pattern("C#")
                .unlockedBy("has_glow_grime", has(DDItems.GLOW_GRIME.get())).save(recipeOutput);

        ShapedRecipeBuilder
                .shaped(RecipeCategory.BUILDING_BLOCKS, DDBlocks.GLOWSHROOM_PILEUS.get(), 2)
                .define('#', DDItems.GLOW_GRIME.get())
                .pattern("###")
                .pattern("# #")
                .pattern("###")
                .unlockedBy("has_glow_grime", has(DDItems.GLOW_GRIME.get())).save(recipeOutput);

        ShapedRecipeBuilder
                .shaped(RecipeCategory.DECORATIONS, DDBlocks.GLOWSHROOM_LANTERN.get())
                .define('#', Items.IRON_NUGGET)
                .define('C', DDItems.GLOW_GRIME.get())
                .pattern("###")
                .pattern("#C#")
                .pattern("###")
                .unlockedBy("has_glow_grime", has(DDItems.GLOW_GRIME.get()))
                .save(recipeOutput);

        ShapedRecipeBuilder
                .shaped(RecipeCategory.DECORATIONS, DDBlocks.GLOWSHROOM_LAMP.get())
                .define('#', Items.IRON_INGOT)
                .define('R', Items.REDSTONE)
                .define('G', DDItems.GLOW_GRIME.get())
                .pattern("#R#")
                .pattern("RGR")
                .pattern("#R#")
                .unlockedBy("has_glow_grime", has(DDItems.GLOW_GRIME.get()))
                .save(recipeOutput);

        ShapedRecipeBuilder
                .shaped(RecipeCategory.BUILDING_BLOCKS, DDBlocks.GEYSER.get().asItem())
                .define('D', DDBlocks.DARKSLATE.get().asItem())
                .define('M', Blocks.MAGMA_BLOCK)
                .define('L', Items.LAVA_BUCKET)
                .pattern("MMM")
                .pattern("DLD")
                .pattern("DDD")
                .unlockedBy("has_lava_bucket", has(Items.LAVA_BUCKET))
                .save(recipeOutput);

        ShapedRecipeBuilder
                .shaped(RecipeCategory.DECORATIONS, DDBlocks.MAGMA_PAD.get(), 4)
                .define('D', DDBlocks.DARKSLATE.get())
                .define('M', Blocks.MAGMA_BLOCK)
                .pattern(" D ")
                .pattern("DMD")
                .pattern(" D ")
                .unlockedBy("has_darkslate", has(DDBlocks.DARKSLATE.get())).save(recipeOutput);

        ShapelessRecipeBuilder
                .shapeless(RecipeCategory.MISC, DDItems.GLOW_GRIME.get())
                .requires(DDBlocks.GLOWSHROOM.get())
                .unlockedBy("has_glowshroom", has(DDBlocks.GLOWSHROOM.get()))
                .save(recipeOutput);

        ShapelessRecipeBuilder
                .shapeless(RecipeCategory.MISC, DDItems.GLOW_GRIME.get(), 2)
                .requires(DDBlocks.GLOWSHROOM_PILEUS.get())
                .unlockedBy("has_glowshroom_block", has(DDBlocks.GLOWSHROOM_PILEUS.get()))
                .save(recipeOutput, DarkerDepths.id("glow_grime_from_glowshroom_block"));

        ShapelessRecipeBuilder
                .shapeless(RecipeCategory.MISC, Items.GLOW_INK_SAC, 2)
                .requires(DDItems.GLOW_GRIME.get())
                .requires(Items.INK_SAC)
                .unlockedBy("has_glow_grime", has(DDItems.GLOW_GRIME.get()))
                .save(recipeOutput, DarkerDepths.id("glow_ink_sac_from_glow_grime"));

        ShapelessRecipeBuilder
                .shapeless(RecipeCategory.MISC, Items.SLIME_BALL)
                .requires(DDItems.GLOW_GRIME.get())
                .requires(Items.CLAY_BALL)
                .unlockedBy("has_glow_grime", has(DDItems.GLOW_GRIME.get()))
                .save(recipeOutput, DarkerDepths.id("slime_ball_from_glow_grime"));

        ShapelessRecipeBuilder
                .shapeless(RecipeCategory.MISC, DDItems.FORSAKEN_BRONZE_INGOT.get())
                .requires(DDItems.FORSAKEN_BRONZE_SCRAP.get())
                .requires(Items.COPPER_INGOT)
                .requires(Items.COPPER_INGOT)
                .requires(Items.COPPER_INGOT)
                .requires(Items.COPPER_INGOT)
                .unlockedBy("has_forsaken_bronze_scrap", has(DDItems.FORSAKEN_BRONZE_SCRAP.get()))
                .save(recipeOutput,DarkerDepths.id("forsaken_bronze_ingot_from_scrap"));

        ShapelessRecipeBuilder
                .shapeless(RecipeCategory.MISC, DDBlocks.SCORCHED_REMAINS.get())
                .requires(Items.ROTTEN_FLESH)
                .requires(Items.CHARCOAL)
                .unlockedBy("has_rotten_flesh", has(Items.ROTTEN_FLESH))
                .save(recipeOutput);

        ShapelessRecipeBuilder
                .shapeless(RecipeCategory.MISC, DDBlocks.SCORCHED_REMAINS_BLOCK.get())
                .requires(Items.ROTTEN_FLESH)
                .requires(Items.CHARCOAL)
                .requires(Items.ROTTEN_FLESH)
                .requires(Items.CHARCOAL)
                .unlockedBy("has_rotten_flesh", has(Items.ROTTEN_FLESH))
                .save(recipeOutput);

        ShapedRecipeBuilder
                .shaped(RecipeCategory.DECORATIONS, DDItems.ROPE.get(), 12)
                .define('#', Items.STRING)
                .define('G', Items.STICK)
                .pattern(" ##")
                .pattern("#G#")
                .pattern("## ")
                .unlockedBy("has_string", has(Items.STRING)).save(recipeOutput);

        ShapedRecipeBuilder
                .shaped(RecipeCategory.MISC, DDItems.VOID_SOUL_REQUIEM.get(), 1)
                .define('A', DDBlocks.AMBER_BLOCK.get())
                .define('B', DDBlocks.VOID_SOUL_JAR.get().asItem())
                .pattern(" B ")
                .pattern("BAB")
                .pattern(" B ")
                .unlockedBy("has_amber", has(DDItems.AMBER.get())).save(recipeOutput);

        ShapedRecipeBuilder
                .shaped(RecipeCategory.MISC, DDItems.VOID_SOUL_TORCH.get(), 4)
                .define('A', Items.STICK)
                .define('B', DDBlocks.VOID_SOUL_JAR.get().asItem())
                .define('C', DDItems.AMBER.get())
                .pattern("B")
                .pattern("C")
                .pattern("A")
                .unlockedBy("has_void_soul_jar", has(DDItems.AMBER.get())).save(recipeOutput);

        ShapedRecipeBuilder
                .shaped(RecipeCategory.MISC, DDBlocks.DEATH_ANCHOR.get().asItem())
                .define('B', DDItems.FORSAKEN_BRONZE_INGOT.get())
                .define('D', DDBlocks.DUSKROCK.get().asItem())
                .define('O', Blocks.OBSIDIAN.asItem())
                .define('R', DDItems.VOID_SOUL_REQUIEM.get())
                .pattern("BBB")
                .pattern("DRD")
                .pattern("DOD")
                .unlockedBy("has_forsaken_bronze_ingot", has(DDItems.FORSAKEN_BRONZE_INGOT.get()))
                .save(recipeOutput);

        ShapedRecipeBuilder
                .shaped(RecipeCategory.MISC, DDBlocks.TOMB.get().asItem())
                .define('B', DDItems.FORSAKEN_BRONZE_INGOT.get())
                .define('D', DDBlocks.DUSKROCK.get().asItem())
                .define('S', DDBlocks.DARKSLATE.get().asItem())
                .pattern(" B ")
                .pattern("DDD")
                .pattern("SSS")
                .unlockedBy("has_forsaken_bronze_ingot", has(DDItems.FORSAKEN_BRONZE_INGOT.get()))
                .save(recipeOutput);

        ShapedRecipeBuilder
                .shaped(RecipeCategory.COMBAT, DDItems.STILETTO.get())
                .define('B', DDItems.FORSAKEN_BRONZE_INGOT.get())
                .define('S', Items.STICK)
                .pattern("B")
                .pattern("B")
                .pattern("S")
                .unlockedBy("has_forsaken_bronze_ingot", has(DDItems.FORSAKEN_BRONZE_INGOT.get()))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, DDBlocks.SKULL_WALL.get(), 4)
                .define('#', DDBlocks.ARIDROCK.get())
                .define('X', Blocks.BONE_BLOCK)
                .pattern(" # ")
                .pattern("#X#")
                .pattern(" # ")
                .unlockedBy("has_aridrock", has(DDBlocks.ARIDROCK.get())).save(recipeOutput);
    }

    public static Criterion<InventoryChangeTrigger.TriggerInstance> has(MinMaxBounds.Ints count, ItemLike item) {
        return inventoryTrigger(ItemPredicate.Builder.item().of(item).withCount(count));
    }

    public static Criterion<InventoryChangeTrigger.TriggerInstance> has(ItemLike itemLike) {
        return inventoryTrigger(ItemPredicate.Builder.item().of(itemLike));
    }

    public static Criterion<InventoryChangeTrigger.TriggerInstance> has(TagKey<Item> tag) {
        return inventoryTrigger(ItemPredicate.Builder.item().of(tag));
    }

    public static Criterion<InventoryChangeTrigger.TriggerInstance> inventoryTrigger(ItemPredicate.Builder... items) {
        return inventoryTrigger(Arrays.stream(items).map(ItemPredicate.Builder::build).toArray(ItemPredicate[]::new));
    }

    public static Criterion<InventoryChangeTrigger.TriggerInstance> inventoryTrigger(ItemPredicate... predicates) {
        return CriteriaTriggers.INVENTORY_CHANGED.createCriterion(
                new InventoryChangeTrigger.TriggerInstance(Optional.empty(), InventoryChangeTrigger.TriggerInstance.Slots.ANY, List.of(predicates)));
    }
}
