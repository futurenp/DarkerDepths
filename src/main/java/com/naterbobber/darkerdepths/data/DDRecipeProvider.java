package com.naterbobber.darkerdepths.data;

import com.google.common.collect.ImmutableList;
import com.naterbobber.darkerdepths.DarkerDepths;
import com.naterbobber.darkerdepths.init.DDBlocks;
import com.naterbobber.darkerdepths.init.DDItems;
import com.naterbobber.darkerdepths.util.DDTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.data.recipes.SingleItemRecipeBuilder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.BlastingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.Tags;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class DDRecipeProvider extends RecipeProvider {

    public DDRecipeProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> registries) {
        super(packOutput, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput recipeOutput) {
        twoXtwo(recipeOutput, DDBlocks.POLISHED_DARKSLATE.get(), DDBlocks.DARKSLATE.get().asItem(), 4);
        twoXtwo(recipeOutput, DDBlocks.POLISHED_ARIDROCK.get(), DDBlocks.ARIDROCK.get().asItem(), 4);
        twoXtwo(recipeOutput, DDBlocks.POLISHED_DUSKROCK.get(), DDBlocks.DUSKROCK.get().asItem(), 4);
        twoXtwo(recipeOutput, DDBlocks.POLISHED_GRIMESTONE.get(), DDBlocks.GRIMESTONE.get().asItem(), 4);
        twoXtwo(recipeOutput, DDBlocks.DARKSLATE_BRICKS.get(), DDBlocks.POLISHED_DARKSLATE.get().asItem(), 4);
        twoXtwo(recipeOutput, DDBlocks.ARIDROCK_BRICKS.get(), DDBlocks.POLISHED_ARIDROCK.get().asItem(), 4);
        twoXtwo(recipeOutput, DDBlocks.DUSKROCK_BRICKS.get(), DDBlocks.POLISHED_DUSKROCK.get().asItem(), 4);
        twoXtwo(recipeOutput, DDBlocks.GRIMESTONE_BRICKS.get(), DDBlocks.POLISHED_GRIMESTONE.get().asItem(), 4);

        stairsBlock(recipeOutput, DDBlocks.DARKSLATE_STAIRS.get(), DDBlocks.DARKSLATE.get().asItem());
        stairsBlock(recipeOutput, DDBlocks.ARIDROCK_STAIRS.get(), DDBlocks.ARIDROCK.get().asItem());
        stairsBlock(recipeOutput, DDBlocks.DUSKROCK_STAIRS.get(), DDBlocks.DUSKROCK.get().asItem());
        stairsBlock(recipeOutput, DDBlocks.GRIMESTONE_STAIRS.get(), DDBlocks.GRIMESTONE.get().asItem());
        stairsBlock(recipeOutput, DDBlocks.POLISHED_DARKSLATE_STAIRS.get(), DDBlocks.POLISHED_DARKSLATE.get().asItem());
        stairsBlock(recipeOutput, DDBlocks.POLISHED_ARIDROCK_STAIRS.get(), DDBlocks.POLISHED_ARIDROCK.get().asItem());
        stairsBlock(recipeOutput, DDBlocks.POLISHED_DUSKROCK_STAIRS.get(), DDBlocks.POLISHED_DUSKROCK.get().asItem());
        stairsBlock(recipeOutput, DDBlocks.POLISHED_GRIMESTONE_STAIRS.get(), DDBlocks.POLISHED_GRIMESTONE.get().asItem());
        stairsBlock(recipeOutput, DDBlocks.DARKSLATE_BRICKS_STAIRS.get(), DDBlocks.DARKSLATE_BRICKS.get().asItem());
        stairsBlock(recipeOutput, DDBlocks.ARIDROCK_BRICKS_STAIRS.get(), DDBlocks.ARIDROCK_BRICKS.get().asItem());
        stairsBlock(recipeOutput, DDBlocks.DUSKROCK_BRICKS_STAIRS.get(), DDBlocks.DUSKROCK_BRICKS.get().asItem());
        stairsBlock(recipeOutput, DDBlocks.GRIMESTONE_BRICKS_STAIRS.get(), DDBlocks.GRIMESTONE_BRICKS.get().asItem());
        stairsBlock(recipeOutput, DDBlocks.PETRIFIED_STAIRS.get(), DDBlocks.PETRIFIED_PLANKS.get().asItem());

        slabBlock(recipeOutput, DDBlocks.DARKSLATE_SLAB.get(), DDBlocks.DARKSLATE.get().asItem());
        slabBlock(recipeOutput, DDBlocks.ARIDROCK_SLAB.get(), DDBlocks.ARIDROCK.get().asItem());
        slabBlock(recipeOutput, DDBlocks.DUSKROCK_SLAB.get(), DDBlocks.DUSKROCK.get().asItem());
        slabBlock(recipeOutput, DDBlocks.GRIMESTONE_SLAB.get(), DDBlocks.GRIMESTONE.get().asItem());
        slabBlock(recipeOutput, DDBlocks.POLISHED_DARKSLATE_SLAB.get(), DDBlocks.POLISHED_DARKSLATE.get().asItem());
        slabBlock(recipeOutput, DDBlocks.POLISHED_ARIDROCK_SLAB.get(), DDBlocks.POLISHED_ARIDROCK.get().asItem());
        slabBlock(recipeOutput, DDBlocks.POLISHED_DUSKROCK_SLAB.get(), DDBlocks.POLISHED_DUSKROCK.get().asItem());
        slabBlock(recipeOutput, DDBlocks.POLISHED_GRIMESTONE_SLAB.get(), DDBlocks.POLISHED_GRIMESTONE.get().asItem());
        slabBlock(recipeOutput, DDBlocks.DARKSLATE_BRICKS_SLAB.get(), DDBlocks.DARKSLATE_BRICKS.get().asItem());
        slabBlock(recipeOutput, DDBlocks.ARIDROCK_BRICKS_SLAB.get(), DDBlocks.ARIDROCK_BRICKS.get().asItem());
        slabBlock(recipeOutput, DDBlocks.DUSKROCK_BRICKS_SLAB.get(), DDBlocks.DUSKROCK_BRICKS.get().asItem());
        slabBlock(recipeOutput, DDBlocks.GRIMESTONE_BRICKS_SLAB.get(), DDBlocks.GRIMESTONE_BRICKS.get().asItem());
        slabBlock(recipeOutput, DDBlocks.PETRIFIED_SLAB.get(), DDBlocks.PETRIFIED_PLANKS.get().asItem());

        pillarBlock(recipeOutput, DDBlocks.ARIDROCK_PILLAR.get(), DDBlocks.POLISHED_ARIDROCK.get().asItem());
        pillarBlock(recipeOutput, DDBlocks.GRIMESTONE_PILLAR.get(), DDBlocks.POLISHED_GRIMESTONE.get().asItem());
        pillarBlock(recipeOutput, DDBlocks.DARKSLATE_PILLAR.get(), DDBlocks.POLISHED_DARKSLATE.get().asItem());

        wallBlock(recipeOutput, DDBlocks.DARKSLATE_WALL.get(), DDBlocks.DARKSLATE.get().asItem());
        wallBlock(recipeOutput, DDBlocks.ARIDROCK_WALL.get(), DDBlocks.ARIDROCK.get().asItem());
        wallBlock(recipeOutput, DDBlocks.DUSKROCK_WALL.get(), DDBlocks.DUSKROCK.get().asItem());
        wallBlock(recipeOutput, DDBlocks.GRIMESTONE_WALL.get(), DDBlocks.GRIMESTONE.get().asItem());
        wallBlock(recipeOutput, DDBlocks.DARKSLATE_BRICKS_WALL.get(), DDBlocks.DARKSLATE_BRICKS.get().asItem());
        wallBlock(recipeOutput, DDBlocks.ARIDROCK_BRICKS_WALL.get(), DDBlocks.ARIDROCK_BRICKS.get().asItem());
        wallBlock(recipeOutput, DDBlocks.DUSKROCK_BRICKS_WALL.get(), DDBlocks.DUSKROCK_BRICKS.get().asItem());
        wallBlock(recipeOutput, DDBlocks.GRIMESTONE_BRICKS_WALL.get(), DDBlocks.GRIMESTONE_BRICKS.get().asItem());

        oreSmelting(recipeOutput, ImmutableList.of(DDBlocks.DARKSLATE_BRICKS.get()), RecipeCategory.BUILDING_BLOCKS, DDBlocks.CRACKED_DARKSLATE_BRICKS.get(), 0.1F, 200, "cracked_darkslate_bricks");
        oreSmelting(recipeOutput, ImmutableList.of(DDBlocks.ARIDROCK_BRICKS.get()), RecipeCategory.BUILDING_BLOCKS, DDBlocks.CRACKED_ARIDROCK_BRICKS.get(), 0.1F, 200, "cracked_aridrock_bricks");
        oreSmelting(recipeOutput, ImmutableList.of(DDBlocks.DUSKROCK_BRICKS.get()), RecipeCategory.BUILDING_BLOCKS, DDBlocks.CRACKED_DUSKROCK_BRICKS.get(), 0.1F, 200, "cracked_limestone_bricks");
        oreSmelting(recipeOutput, ImmutableList.of(DDBlocks.GRIMESTONE_BRICKS.get()), RecipeCategory.BUILDING_BLOCKS, DDBlocks.CRACKED_GRIMESTONE_BRICKS.get(), 0.1F, 200, "cracked_grimestone_bricks");



        fenceBlock(recipeOutput, DDBlocks.PETRIFIED_FENCE.get(), DDBlocks.PETRIFIED_PLANKS.get().asItem());

        chiseled(recipeOutput, DDBlocks.DARKSLATE_BRICKS_SLAB.get().asItem(), DDBlocks.CHISELED_DARKSLATE_BRICKS.get().asItem());
        chiseled(recipeOutput, DDBlocks.ARIDROCK_BRICKS_SLAB.get().asItem(), DDBlocks.CHISELED_ARIDROCK_BRICKS.get().asItem());
        chiseled(recipeOutput, DDBlocks.DUSKROCK_BRICKS_SLAB.get().asItem(), DDBlocks.CHISELED_DUSKROCK_BRICKS.get().asItem());
        chiseled(recipeOutput, DDBlocks.GRIMESTONE_BRICKS_SLAB.get().asItem(), DDBlocks.CHISELED_GRIMESTONE_BRICKS.get().asItem());

        threeXthree(recipeOutput, DDBlocks.AMBER_BLOCK.get(), DDItems.AMBER.get());
        threeXthree(recipeOutput, DDBlocks.FORSAKEN_BRONZE_BLOCK.get(), DDItems.FORSAKEN_BRONZE_INGOT.get());

        shaplessOne(recipeOutput, DDItems.AMBER.get(), DDBlocks.AMBER_BLOCK.get().asItem(), 9);
        shaplessOne(recipeOutput, DDItems.FORSAKEN_BRONZE_INGOT.get(), DDBlocks.FORSAKEN_BRONZE_BLOCK.get().asItem(), 9);

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
                .shaped(RecipeCategory.BUILDING_BLOCKS, DDBlocks.GLOWSHROOM_BLOCK.get(), 2)
                .define('#', DDItems.GLOW_GRIME.get())
                .pattern("###")
                .pattern("# #")
                .pattern("###")
                .unlockedBy("has_glow_grime", has(DDItems.GLOW_GRIME.get())).save(recipeOutput);

//        ShapedRecipeBuilder
//                .shaped(RecipeCategory.TOOLS, DDItems.QUICKROPE.get())
//                .define('#', DDItems.ROPE.get())
//                .define('G', Items.GOLD_INGOT)
//                .define('C', Items.COPPER_INGOT)
//                .pattern(" # ")
//                .pattern("GCG")
//                .pattern(" # ")
//                .unlockedBy("has_rope", has(DDItems.ROPE.get())).save(recipeOutput);

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
                .shaped(RecipeCategory.BUILDING_BLOCKS, DDBlocks.PETRIFIED_FENCE_GATE.get().asItem())
                .define('#', Items.STICK)
                .define('W', DDBlocks.PETRIFIED_PLANKS.get().asItem())
                .pattern("#W#")
                .pattern("#W#")
                .unlockedBy("has_planks", has(DDBlocks.PETRIFIED_PLANKS.get().asItem()))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, DDBlocks.PETRIFIED_BUTTON.get())
                .requires(DDBlocks.PETRIFIED_PLANKS.get().asItem())
                .unlockedBy("has_planks", has(DDBlocks.PETRIFIED_PLANKS.get())).save(recipeOutput);

        ShapedRecipeBuilder
                .shaped(RecipeCategory.DECORATIONS, DDItems.PETRIFIED_SIGN.get(), 3)
                .group("sign")
                .define('#', DDBlocks.PETRIFIED_PLANKS.get())
                .define('X', Items.STICK)
                .pattern("###")
                .pattern("###")
                .pattern(" X ")
                .unlockedBy("has_planks", has(DDBlocks.PETRIFIED_PLANKS.get()))
                .save(recipeOutput);

        ShapedRecipeBuilder
                .shaped(RecipeCategory.REDSTONE, DDBlocks.PETRIFIED_PRESSURE_PLATE.get())
                .define('#', DDBlocks.PETRIFIED_PLANKS.get())
                .pattern("##")
                .unlockedBy("has_planks", has(DDBlocks.PETRIFIED_PLANKS.get()))
                .save(recipeOutput);

        ShapedRecipeBuilder
                .shaped(RecipeCategory.REDSTONE, DDBlocks.PETRIFIED_DOOR.get(), 3)
                .group("wooden_door")
                .define('#', DDBlocks.PETRIFIED_PLANKS.get())
                .pattern("##")
                .pattern("##")
                .pattern("##")
                .unlockedBy("has_planks", has(DDBlocks.PETRIFIED_PLANKS.get())).save(recipeOutput);

        ShapedRecipeBuilder
                .shaped(RecipeCategory.REDSTONE, DDBlocks.PETRIFIED_TRAPDOOR.get(), 2)
                .group("wooden_trapdoor")
                .define('#', DDBlocks.PETRIFIED_PLANKS.get())
                .pattern("###")
                .pattern("###")
                .unlockedBy("has_planks", has(DDBlocks.PETRIFIED_PLANKS.get())).save(recipeOutput);

        ShapelessRecipeBuilder
                .shapeless(RecipeCategory.BUILDING_BLOCKS, DDBlocks.PETRIFIED_PLANKS.get(), 4)
                .requires(DDTags.Items.PETRIFIED_LOGS)
                .group("planks")
                .unlockedBy("has_logs", has(DDTags.Items.PETRIFIED_LOGS)).save(recipeOutput);

        ShapedRecipeBuilder
                .shaped(RecipeCategory.BUILDING_BLOCKS, DDBlocks.PETRIFIED_WOOD.get(), 3)
                .group("bark")
                .define('#', DDBlocks.PETRIFIED_LOG.get())
                .pattern("##")
                .pattern("##")
                .unlockedBy("has_log", has(DDBlocks.PETRIFIED_LOG.get())).save(recipeOutput);

        ShapedRecipeBuilder
                .shaped(RecipeCategory.BUILDING_BLOCKS, DDBlocks.STRIPPED_PETRIFIED_WOOD.get(), 3)
                .group("bark")
                .define('#', DDBlocks.STRIPPED_PETRIFIED_LOG.get())
                .pattern("##")
                .pattern("##")
                .unlockedBy("has_log", has(DDBlocks.STRIPPED_PETRIFIED_LOG.get())).save(recipeOutput);

        hangingSignBlock(recipeOutput, DDItems.PETRIFIED_HANGING_SIGN.get(), DDBlocks.STRIPPED_PETRIFIED_LOG.get().asItem(), Items.CHAIN);
        trimmedPlanksBlock(recipeOutput, DDBlocks.TRIMMED_PETRIFIED_PLANKS.asItem(), DDBlocks.PETRIFIED_PLANKS.asItem());
        bookshelfBlock(recipeOutput, DDBlocks.PETRIFIED_BOOKSHELF.asItem(), DDBlocks.PETRIFIED_PLANKS.asItem());
        boardsBlock(recipeOutput, DDBlocks.PETRIFIED_BOARDS.asItem(), DDBlocks.PETRIFIED_PLANKS.asItem(), DDBlocks.PETRIFIED_SLAB.asItem());

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, DDItems.GLOW_GRIME.get())
                .requires(DDBlocks.GLOWSHROOM.get())
                .unlockedBy("has_glowshroom", has(DDBlocks.GLOWSHROOM.get()))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, DDItems.GLOW_GRIME.get(), 2)
                .requires(DDBlocks.GLOWSHROOM_BLOCK.get())
                .unlockedBy("has_glowshroom_block", has(DDBlocks.GLOWSHROOM_BLOCK.get()))
                .save(recipeOutput, DarkerDepths.id("glow_grime_from_glowshroom_block"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.GLOW_INK_SAC, 2)
                .requires(DDItems.GLOW_GRIME.get())
                .requires(Items.INK_SAC)
                .unlockedBy("has_glow_grime", has(DDItems.GLOW_GRIME.get()))
                .save(recipeOutput, DarkerDepths.id("glow_ink_sac_from_glow_grime"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.SLIME_BALL)
                .requires(DDItems.GLOW_GRIME.get())
                .requires(Items.CLAY_BALL)
                .unlockedBy("has_glow_grime", has(DDItems.GLOW_GRIME.get()))
                .save(recipeOutput, DarkerDepths.id("slime_ball_from_glow_grime"));

        ShapedRecipeBuilder
                .shaped(RecipeCategory.MISC, DDItems.PETRIFIED_BOAT.get())
                .define('#', DDBlocks.PETRIFIED_PLANKS.get().asItem())
                .pattern("# #")
                .pattern("###")
                .unlockedBy("has_petrified_planks", has(DDBlocks.PETRIFIED_PLANKS.get())).save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, DDItems.PETRIFIED_CHEST_BOAT.get())
                .requires(DDItems.PETRIFIED_BOAT.get())
                .requires(Tags.Items.CHESTS_WOODEN)
                .unlockedBy("has_petrified_planks", has(DDBlocks.PETRIFIED_PLANKS.get()))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, DDItems.FORSAKEN_BRONZE_INGOT.get())
                .requires(DDItems.FORSAKEN_BRONZE_SCRAP.get())
                .requires(Items.COPPER_INGOT)
                .requires(Items.COPPER_INGOT)
                .requires(Items.COPPER_INGOT)
                .requires(Items.COPPER_INGOT)
                .unlockedBy("has_forsaken_bronze_scrap", has(DDItems.FORSAKEN_BRONZE_SCRAP.get()))
                .save(recipeOutput,DarkerDepths.id("forsaken_bronze_ingot_from_scrap"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, DDBlocks.SCORCHED_REMAINS.get())
                .requires(Items.ROTTEN_FLESH)
                .requires(Items.CHARCOAL)
                .unlockedBy("has_rotten_flesh", has(Items.ROTTEN_FLESH))
                .save(recipeOutput,DarkerDepths.id("scorched_remains"));

        ShapedRecipeBuilder
                .shaped(RecipeCategory.MISC, DDBlocks.SCORCHED_REMAINS_BLOCK.get(), 1)
                .define('F', Items.ROTTEN_FLESH)
                .define('C', Items.CHARCOAL)
                .pattern("FCF")
                .pattern("CFC")
                .pattern("FCF")
                .unlockedBy("has_rotten_flesh", has(Items.ROTTEN_FLESH)).save(recipeOutput);

        stonecutterResultFromBase(recipeOutput, DDBlocks.DARKSLATE_STAIRS.get(), DDBlocks.DARKSLATE.get());
        stonecutterResultFromBase(recipeOutput, DDBlocks.ARIDROCK_STAIRS.get(), DDBlocks.ARIDROCK.get());
        stonecutterResultFromBase(recipeOutput, DDBlocks.DUSKROCK_STAIRS.get(), DDBlocks.DUSKROCK.get());
        stonecutterResultFromBase(recipeOutput, DDBlocks.GRIMESTONE_STAIRS.get(), DDBlocks.GRIMESTONE.get());
        stonecutterResultFromBase(recipeOutput, DDBlocks.DARKSLATE_SLAB.get(), DDBlocks.DARKSLATE.get(), 2);
        stonecutterResultFromBase(recipeOutput, DDBlocks.ARIDROCK_SLAB.get(), DDBlocks.ARIDROCK.get(), 2);
        stonecutterResultFromBase(recipeOutput, DDBlocks.DUSKROCK_SLAB.get(), DDBlocks.DUSKROCK.get(), 2);
        stonecutterResultFromBase(recipeOutput, DDBlocks.GRIMESTONE_SLAB.get(), DDBlocks.GRIMESTONE.get(), 2);
        stonecutterResultFromBase(recipeOutput, DDBlocks.DARKSLATE_WALL.get(), DDBlocks.DARKSLATE.get());
        stonecutterResultFromBase(recipeOutput, DDBlocks.ARIDROCK_WALL.get(), DDBlocks.ARIDROCK.get());
        stonecutterResultFromBase(recipeOutput, DDBlocks.DUSKROCK_WALL.get(), DDBlocks.DUSKROCK.get());
        stonecutterResultFromBase(recipeOutput, DDBlocks.GRIMESTONE_WALL.get(), DDBlocks.GRIMESTONE.get());

        stonecutterResultFromBase(recipeOutput, DDBlocks.POLISHED_DARKSLATE.get(), DDBlocks.DARKSLATE.get());
        stonecutterResultFromBase(recipeOutput, DDBlocks.POLISHED_ARIDROCK.get(), DDBlocks.ARIDROCK.get());
        stonecutterResultFromBase(recipeOutput, DDBlocks.POLISHED_DUSKROCK.get(), DDBlocks.DUSKROCK.get());
        stonecutterResultFromBase(recipeOutput, DDBlocks.POLISHED_GRIMESTONE.get(), DDBlocks.GRIMESTONE.get());
        stonecutterResultFromBase(recipeOutput, DDBlocks.POLISHED_DARKSLATE_STAIRS.get(), DDBlocks.DARKSLATE.get());
        stonecutterResultFromBase(recipeOutput, DDBlocks.POLISHED_ARIDROCK_STAIRS.get(), DDBlocks.ARIDROCK.get());
        stonecutterResultFromBase(recipeOutput, DDBlocks.POLISHED_DUSKROCK_STAIRS.get(), DDBlocks.DUSKROCK.get());
        stonecutterResultFromBase(recipeOutput, DDBlocks.POLISHED_GRIMESTONE_STAIRS.get(), DDBlocks.GRIMESTONE.get());
        stonecutterResultFromBase(recipeOutput, DDBlocks.POLISHED_DARKSLATE_SLAB.get(), DDBlocks.DARKSLATE.get(), 2);
        stonecutterResultFromBase(recipeOutput, DDBlocks.POLISHED_ARIDROCK_SLAB.get(), DDBlocks.ARIDROCK.get(), 2);
        stonecutterResultFromBase(recipeOutput, DDBlocks.POLISHED_DUSKROCK_SLAB.get(), DDBlocks.DUSKROCK.get(), 2);
        stonecutterResultFromBase(recipeOutput, DDBlocks.POLISHED_GRIMESTONE_SLAB.get(), DDBlocks.GRIMESTONE.get(), 2);

        stonecutterResultFromBase(recipeOutput, DDBlocks.POLISHED_DARKSLATE_STAIRS.get(), DDBlocks.POLISHED_DARKSLATE.get());
        stonecutterResultFromBase(recipeOutput, DDBlocks.POLISHED_ARIDROCK_STAIRS.get(), DDBlocks.POLISHED_ARIDROCK.get());
        stonecutterResultFromBase(recipeOutput, DDBlocks.POLISHED_DUSKROCK_STAIRS.get(), DDBlocks.POLISHED_DUSKROCK.get());
        stonecutterResultFromBase(recipeOutput, DDBlocks.POLISHED_GRIMESTONE_STAIRS.get(), DDBlocks.POLISHED_GRIMESTONE.get());
        stonecutterResultFromBase(recipeOutput, DDBlocks.POLISHED_DARKSLATE_SLAB.get(), DDBlocks.POLISHED_DARKSLATE.get(), 2);
        stonecutterResultFromBase(recipeOutput, DDBlocks.POLISHED_ARIDROCK_SLAB.get(), DDBlocks.POLISHED_ARIDROCK.get(), 2);
        stonecutterResultFromBase(recipeOutput, DDBlocks.POLISHED_DUSKROCK_SLAB.get(), DDBlocks.POLISHED_DUSKROCK.get(), 2);
        stonecutterResultFromBase(recipeOutput, DDBlocks.POLISHED_GRIMESTONE_SLAB.get(), DDBlocks.POLISHED_GRIMESTONE.get(), 2);

        stonecutterResultFromBase(recipeOutput, DDBlocks.DARKSLATE_BRICKS.get(), DDBlocks.DARKSLATE.get());
        stonecutterResultFromBase(recipeOutput, DDBlocks.ARIDROCK_BRICKS.get(), DDBlocks.ARIDROCK.get());
        stonecutterResultFromBase(recipeOutput, DDBlocks.DUSKROCK_BRICKS.get(), DDBlocks.DUSKROCK.get());
        stonecutterResultFromBase(recipeOutput, DDBlocks.GRIMESTONE_BRICKS.get(), DDBlocks.GRIMESTONE.get());
        stonecutterResultFromBase(recipeOutput, DDBlocks.DARKSLATE_BRICKS_STAIRS.get(), DDBlocks.DARKSLATE.get());
        stonecutterResultFromBase(recipeOutput, DDBlocks.ARIDROCK_BRICKS_STAIRS.get(), DDBlocks.ARIDROCK.get());
        stonecutterResultFromBase(recipeOutput, DDBlocks.DUSKROCK_BRICKS_STAIRS.get(), DDBlocks.DUSKROCK.get());
        stonecutterResultFromBase(recipeOutput, DDBlocks.GRIMESTONE_BRICKS_STAIRS.get(), DDBlocks.GRIMESTONE.get());
        stonecutterResultFromBase(recipeOutput, DDBlocks.DARKSLATE_BRICKS_SLAB.get(), DDBlocks.DARKSLATE.get(), 2);
        stonecutterResultFromBase(recipeOutput, DDBlocks.ARIDROCK_BRICKS_SLAB.get(), DDBlocks.ARIDROCK.get(), 2);
        stonecutterResultFromBase(recipeOutput, DDBlocks.DUSKROCK_BRICKS_SLAB.get(), DDBlocks.DUSKROCK.get(), 2);
        stonecutterResultFromBase(recipeOutput, DDBlocks.GRIMESTONE_BRICKS_SLAB.get(), DDBlocks.GRIMESTONE.get(), 2);
        stonecutterResultFromBase(recipeOutput, DDBlocks.DARKSLATE_BRICKS_WALL.get(), DDBlocks.DARKSLATE.get());
        stonecutterResultFromBase(recipeOutput, DDBlocks.ARIDROCK_BRICKS_WALL.get(), DDBlocks.ARIDROCK.get());
        stonecutterResultFromBase(recipeOutput, DDBlocks.DUSKROCK_BRICKS_WALL.get(), DDBlocks.DUSKROCK.get());
        stonecutterResultFromBase(recipeOutput, DDBlocks.GRIMESTONE_BRICKS_WALL.get(), DDBlocks.GRIMESTONE.get());
        stonecutterResultFromBase(recipeOutput, DDBlocks.CHISELED_DARKSLATE_BRICKS.get(), DDBlocks.DARKSLATE.get());
        stonecutterResultFromBase(recipeOutput, DDBlocks.CHISELED_ARIDROCK_BRICKS.get(), DDBlocks.ARIDROCK.get());
        stonecutterResultFromBase(recipeOutput, DDBlocks.CHISELED_DUSKROCK_BRICKS.get(), DDBlocks.DUSKROCK.get());
        stonecutterResultFromBase(recipeOutput, DDBlocks.CHISELED_GRIMESTONE_BRICKS.get(), DDBlocks.GRIMESTONE.get());

        stonecutterResultFromBase(recipeOutput, DDBlocks.ARIDROCK_PILLAR.get(), DDBlocks.ARIDROCK.get());
        stonecutterResultFromBase(recipeOutput, DDBlocks.GRIMESTONE_PILLAR.get(), DDBlocks.GRIMESTONE.get());
        stonecutterResultFromBase(recipeOutput, DDBlocks.DARKSLATE_PILLAR.get(), DDBlocks.DARKSLATE.get());

        stonecutterResultFromBase(recipeOutput, DDBlocks.DARKSLATE_BRICKS.get(), DDBlocks.POLISHED_DARKSLATE.get());
        stonecutterResultFromBase(recipeOutput, DDBlocks.ARIDROCK_BRICKS.get(), DDBlocks.POLISHED_ARIDROCK.get());
        stonecutterResultFromBase(recipeOutput, DDBlocks.DUSKROCK_BRICKS.get(), DDBlocks.POLISHED_DUSKROCK.get());
        stonecutterResultFromBase(recipeOutput, DDBlocks.GRIMESTONE_BRICKS.get(), DDBlocks.POLISHED_GRIMESTONE.get());
        stonecutterResultFromBase(recipeOutput, DDBlocks.DARKSLATE_BRICKS_STAIRS.get(), DDBlocks.POLISHED_DARKSLATE.get());
        stonecutterResultFromBase(recipeOutput, DDBlocks.ARIDROCK_BRICKS_STAIRS.get(), DDBlocks.POLISHED_ARIDROCK.get());
        stonecutterResultFromBase(recipeOutput, DDBlocks.DUSKROCK_BRICKS_STAIRS.get(), DDBlocks.POLISHED_DUSKROCK.get());
        stonecutterResultFromBase(recipeOutput, DDBlocks.GRIMESTONE_BRICKS_STAIRS.get(), DDBlocks.POLISHED_GRIMESTONE.get());
        stonecutterResultFromBase(recipeOutput, DDBlocks.DARKSLATE_BRICKS_SLAB.get(), DDBlocks.POLISHED_DARKSLATE.get(), 2);
        stonecutterResultFromBase(recipeOutput, DDBlocks.ARIDROCK_BRICKS_SLAB.get(), DDBlocks.POLISHED_ARIDROCK.get(), 2);
        stonecutterResultFromBase(recipeOutput, DDBlocks.DUSKROCK_BRICKS_SLAB.get(), DDBlocks.POLISHED_DUSKROCK.get(), 2);
        stonecutterResultFromBase(recipeOutput, DDBlocks.GRIMESTONE_BRICKS_SLAB.get(), DDBlocks.POLISHED_GRIMESTONE.get(), 2);
        stonecutterResultFromBase(recipeOutput, DDBlocks.DARKSLATE_BRICKS_WALL.get(), DDBlocks.POLISHED_DARKSLATE.get());
        stonecutterResultFromBase(recipeOutput, DDBlocks.ARIDROCK_BRICKS_WALL.get(), DDBlocks.POLISHED_ARIDROCK.get());
        stonecutterResultFromBase(recipeOutput, DDBlocks.DUSKROCK_BRICKS_WALL.get(), DDBlocks.POLISHED_DUSKROCK.get());
        stonecutterResultFromBase(recipeOutput, DDBlocks.GRIMESTONE_BRICKS_WALL.get(), DDBlocks.POLISHED_GRIMESTONE.get());
        stonecutterResultFromBase(recipeOutput, DDBlocks.CHISELED_DARKSLATE_BRICKS.get(), DDBlocks.POLISHED_DARKSLATE.get());
        stonecutterResultFromBase(recipeOutput, DDBlocks.CHISELED_ARIDROCK_BRICKS.get(), DDBlocks.POLISHED_ARIDROCK.get());
        stonecutterResultFromBase(recipeOutput, DDBlocks.CHISELED_DUSKROCK_BRICKS.get(), DDBlocks.POLISHED_DUSKROCK.get());
        stonecutterResultFromBase(recipeOutput, DDBlocks.CHISELED_GRIMESTONE_BRICKS.get(), DDBlocks.POLISHED_GRIMESTONE.get());

        stonecutterResultFromBase(recipeOutput, DDBlocks.DARKSLATE_BRICKS_STAIRS.get(), DDBlocks.DARKSLATE_BRICKS.get());
        stonecutterResultFromBase(recipeOutput, DDBlocks.ARIDROCK_BRICKS_STAIRS.get(), DDBlocks.ARIDROCK_BRICKS.get());
        stonecutterResultFromBase(recipeOutput, DDBlocks.DUSKROCK_BRICKS_STAIRS.get(), DDBlocks.DUSKROCK_BRICKS.get());
        stonecutterResultFromBase(recipeOutput, DDBlocks.GRIMESTONE_BRICKS_STAIRS.get(), DDBlocks.GRIMESTONE_BRICKS.get());
        stonecutterResultFromBase(recipeOutput, DDBlocks.DARKSLATE_BRICKS_SLAB.get(), DDBlocks.DARKSLATE_BRICKS.get(), 2);
        stonecutterResultFromBase(recipeOutput, DDBlocks.ARIDROCK_BRICKS_SLAB.get(), DDBlocks.ARIDROCK_BRICKS.get(), 2);
        stonecutterResultFromBase(recipeOutput, DDBlocks.DUSKROCK_BRICKS_SLAB.get(), DDBlocks.DUSKROCK_BRICKS.get(), 2);
        stonecutterResultFromBase(recipeOutput, DDBlocks.GRIMESTONE_BRICKS_SLAB.get(), DDBlocks.GRIMESTONE_BRICKS.get(), 2);
        stonecutterResultFromBase(recipeOutput, DDBlocks.DARKSLATE_BRICKS_WALL.get(), DDBlocks.DARKSLATE_BRICKS.get());
        stonecutterResultFromBase(recipeOutput, DDBlocks.ARIDROCK_BRICKS_WALL.get(), DDBlocks.ARIDROCK_BRICKS.get());
        stonecutterResultFromBase(recipeOutput, DDBlocks.DUSKROCK_BRICKS_WALL.get(), DDBlocks.DUSKROCK_BRICKS.get());
        stonecutterResultFromBase(recipeOutput, DDBlocks.GRIMESTONE_BRICKS_WALL.get(), DDBlocks.GRIMESTONE_BRICKS.get());
        stonecutterResultFromBase(recipeOutput, DDBlocks.CHISELED_DARKSLATE_BRICKS.get(), DDBlocks.DARKSLATE_BRICKS.get());
        stonecutterResultFromBase(recipeOutput, DDBlocks.CHISELED_ARIDROCK_BRICKS.get(), DDBlocks.ARIDROCK_BRICKS.get());
        stonecutterResultFromBase(recipeOutput, DDBlocks.CHISELED_DUSKROCK_BRICKS.get(), DDBlocks.DUSKROCK_BRICKS.get());
        stonecutterResultFromBase(recipeOutput, DDBlocks.CHISELED_GRIMESTONE_BRICKS.get(), DDBlocks.GRIMESTONE_BRICKS.get());

        stonecutterResultFromBase(recipeOutput, DDBlocks.ARIDROCK_PILLAR.get(), DDBlocks.POLISHED_ARIDROCK.get());
        stonecutterResultFromBase(recipeOutput, DDBlocks.GRIMESTONE_PILLAR.get(), DDBlocks.POLISHED_GRIMESTONE.get());
        stonecutterResultFromBase(recipeOutput, DDBlocks.DARKSLATE_PILLAR.get(), DDBlocks.POLISHED_DARKSLATE.get());

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


    protected static void stonecutterResultFromBase(RecipeOutput recipeOutput, ItemLike result, ItemLike ingredient) {
        stonecutterResultFromBase(recipeOutput, result, ingredient, 1);
    }

    protected static void stonecutterResultFromBase(RecipeOutput recipeOutput, ItemLike result, ItemLike ingredient, int count) {
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ingredient), RecipeCategory.BUILDING_BLOCKS, result, count)
                .unlockedBy(getHasName(ingredient), has(ingredient))
                .save(recipeOutput, DarkerDepths.id(getConversionRecipeName(result, ingredient) + "_stonecutting"));
    }

    protected static String getConversionRecipeName(ItemLike result, ItemLike ingredient) {
        return getItemName(result) + "_from_" + getItemName(ingredient);
    }

    protected static String getItemName(ItemLike item) {
        return BuiltInRegistries.ITEM.getKey(item.asItem()).getPath();
    }

    private void chiseled(RecipeOutput recipeOutput, Item slab, Item result) {
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, result)
                .define('#', slab)
                .pattern("#")
                .pattern("#")
                .unlockedBy("has_" + BuiltInRegistries.ITEM.getKey(slab).getPath(), has(slab))
                .save(recipeOutput);
    }

    private void wallBlock(RecipeOutput recipeOutput, ItemLike result, Item item) {
        ShapedRecipeBuilder
                .shaped(RecipeCategory.BUILDING_BLOCKS, result, 6)
                .define('#', item)
                .pattern("###")
                .pattern("###")
                .unlockedBy("has_" + BuiltInRegistries.ITEM.getKey(item).getPath(), has(item)).save(recipeOutput);
    }

    private void twoXtwo(RecipeOutput recipeOutput, ItemLike result, Item item, int count) {
        ShapedRecipeBuilder
                .shaped(RecipeCategory.BUILDING_BLOCKS, result, count)
                .define('S', item)
                .pattern("SS")
                .pattern("SS")
                .unlockedBy("has_" + BuiltInRegistries.ITEM.getKey(item).getPath(), has(item)).save(recipeOutput);
    }



    private void threeXthree(RecipeOutput recipeOutput, ItemLike result, Item item) {
        ShapedRecipeBuilder
                .shaped(RecipeCategory.BUILDING_BLOCKS, result)
                .define('S', item)
                .pattern("SSS")
                .pattern("SSS")
                .pattern("SSS")
                .unlockedBy("has_" + BuiltInRegistries.ITEM.getKey(item).getPath(), has(item)).save(recipeOutput);
    }

    private void stairsBlock(RecipeOutput recipeOutput, ItemLike result, Item item) {
        ShapedRecipeBuilder
                .shaped(RecipeCategory.BUILDING_BLOCKS, result, 4)
                .define('#', item)
                .pattern("#  ")
                .pattern("## ")
                .pattern("###")
                .unlockedBy("has_" + BuiltInRegistries.ITEM.getKey(item).getPath(), has(item)).save(recipeOutput);
    }

    private void fenceBlock(RecipeOutput recipeOutput, ItemLike result, Item item) {
        ShapedRecipeBuilder
                .shaped(RecipeCategory.BUILDING_BLOCKS, result, 3)
                .define('#', item)
                .define('S', Items.STICK)
                .pattern("#S#")
                .pattern("#S#")
                .unlockedBy("has_" + BuiltInRegistries.ITEM.getKey(item).getPath(), has(item)).save(recipeOutput);
    }

    private void slabBlock(RecipeOutput recipeOutput, ItemLike result, Item item) {
        ShapedRecipeBuilder
                .shaped(RecipeCategory.BUILDING_BLOCKS, result, 6)
                .define('#', item)
                .pattern("###")
                .unlockedBy("has_" + BuiltInRegistries.ITEM.getKey(item).getPath(), has(item)).save(recipeOutput);
    }

    private void pillarBlock(RecipeOutput recipeOutput, ItemLike result, Item item) {
        ShapedRecipeBuilder
                .shaped(RecipeCategory.BUILDING_BLOCKS, result, 2)
                .define('#', item)
                .pattern("#")
                .pattern("#")
                .unlockedBy("has_" + BuiltInRegistries.ITEM.getKey(item).getPath(), has(item)).save(recipeOutput);
    }

    private void trimmedPlanksBlock(RecipeOutput recipeOutput, ItemLike result, Item planks) {
        ShapedRecipeBuilder
                .shaped(RecipeCategory.BUILDING_BLOCKS, result, 3)
                .define('#', planks)
                .pattern("#")
                .pattern("#")
                .pattern("#")
                .unlockedBy("has_" + BuiltInRegistries.ITEM.getKey(planks).getPath(), has(planks)).save(recipeOutput);
    }

    private void bookshelfBlock(RecipeOutput recipeOutput, ItemLike result, Item planks) {
        ShapedRecipeBuilder
                .shaped(RecipeCategory.BUILDING_BLOCKS, result, 1)
                .define('#', planks)
                .define('b', Items.BOOK)
                .pattern("###")
                .pattern("bbb")
                .pattern("###")
                .unlockedBy("has_" + BuiltInRegistries.ITEM.getKey(planks).getPath(), has(planks)).save(recipeOutput);
    }

    private void boardsBlock(RecipeOutput recipeOutput, ItemLike result, Item planks, Item slab) {
        ShapedRecipeBuilder
                .shaped(RecipeCategory.BUILDING_BLOCKS, result, 1)
                .define('#', slab)
                .pattern("#")
                .pattern("#")
                .unlockedBy("has_" + BuiltInRegistries.ITEM.getKey(planks).getPath(), has(planks)).save(recipeOutput);
    }

    private void hangingSignBlock(RecipeOutput recipeOutput, ItemLike result, Item strippedLog, Item chain) {
        ShapedRecipeBuilder
                .shaped(RecipeCategory.BUILDING_BLOCKS, result, 6)
                .define('#', strippedLog)
                .define('!', chain)
                .pattern("! !")
                .pattern("###")
                .pattern("###")
                .unlockedBy("has_" + BuiltInRegistries.ITEM.getKey(strippedLog).getPath(), has(strippedLog)).save(recipeOutput);
    }

    protected static void oreSmelting(RecipeOutput recipeOutput, List<ItemLike> items, RecipeCategory recipeCategory, ItemLike p_176594_, float p_176595_, int p_176596_, String p_176597_) {
        oreCooking(recipeOutput, recipeCategory, RecipeSerializer.SMELTING_RECIPE, SmeltingRecipe::new, items, p_176594_, p_176595_, p_176596_, p_176597_, "_from_smelting");
    }

    protected static void oreBlasting(RecipeOutput recipeOutput, List<ItemLike> items, RecipeCategory recipeCategory, ItemLike p_176628_, float p_176629_, int p_176630_, String p_176631_) {
        oreCooking(recipeOutput, recipeCategory, RecipeSerializer.BLASTING_RECIPE, BlastingRecipe::new, items, p_176628_, p_176629_, p_176630_, p_176631_, "_from_blasting");
    }

    protected static <T extends AbstractCookingRecipe> void oreCooking(RecipeOutput recipeOutput, RecipeCategory recipeCategory, RecipeSerializer<T> serializer, AbstractCookingRecipe.Factory<T> recipeFactory, List<ItemLike> itemLike, ItemLike item, float experience, int time, String group, String name) {
        for (ItemLike itemlike : itemLike) {
            SimpleCookingRecipeBuilder
                    .generic(Ingredient.of(itemlike), recipeCategory, item, experience, time, serializer, recipeFactory)
                    .group(group)
                    .unlockedBy(getHasName(itemlike), has(itemlike))
                    .save(recipeOutput, DarkerDepths.id(getItemName(item) + name + "_" + getItemName(itemlike)));
        }
    }

    private void shaplessOne(RecipeOutput recipeOutput, Item result, Item item, int count) {
        ShapelessRecipeBuilder
                .shapeless(RecipeCategory.MISC, result, count)
                .requires(item)
                .unlockedBy("has_" + BuiltInRegistries.ITEM.getKey(item).getPath(), has(item)).save(recipeOutput);
    }
}