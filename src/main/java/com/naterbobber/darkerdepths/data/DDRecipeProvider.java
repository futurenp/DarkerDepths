package com.naterbobber.darkerdepths.data;

import com.google.common.collect.ImmutableList;
import com.naterbobber.darkerdepths.DarkerDepths;
import com.naterbobber.darkerdepths.block.generic.ConnectedRotatablePillarBlock;
import com.naterbobber.darkerdepths.block.generic.VerticalSlabBlock;
import com.naterbobber.darkerdepths.block.generic.relational.DDBlockSets;
import com.naterbobber.darkerdepths.block.generic.relational.DDStoneBlockSet;
import com.naterbobber.darkerdepths.compat.DDCompat;
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
import net.minecraft.world.level.block.*;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.conditions.ModLoadedCondition;
import net.neoforged.neoforge.common.conditions.OrCondition;
import net.neoforged.neoforge.registries.DeferredBlock;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

public class DDRecipeProvider extends RecipeProvider {

    public DDRecipeProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> registries) {
        super(packOutput, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput recipeOutput) {
        DDBlockSets.STONE_BLOCK_SETS.forEach(blockSet -> {
            stoneBlockSet(recipeOutput, blockSet);
        });

        stairsBlock(recipeOutput, DDBlocks.PETRIFIED_STAIRS.get(), DDBlocks.PETRIFIED_PLANKS.get().asItem());
        slabBlock(recipeOutput, DDBlocks.PETRIFIED_SLAB.get(), DDBlocks.PETRIFIED_PLANKS.get().asItem());

        fenceBlock(recipeOutput, DDBlocks.PETRIFIED_FENCE.get(), DDBlocks.PETRIFIED_PLANKS.get().asItem());

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

        ShapedRecipeBuilder
                .shaped(RecipeCategory.DECORATIONS, DDBlocks.MAGMA_PAD.get(), 4)
                .define('D', DDBlocks.DARKSLATE.get())
                .define('M', Blocks.MAGMA_BLOCK)
                .pattern(" D ")
                .pattern("DMD")
                .pattern(" D ")
                .unlockedBy("has_darkslate", has(DDBlocks.DARKSLATE.get())).save(recipeOutput);

        hangingSignBlock(recipeOutput, DDItems.PETRIFIED_HANGING_SIGN.get(), DDBlocks.STRIPPED_PETRIFIED_LOG.get().asItem(), Items.CHAIN);
        trimmedPlanksBlock(recipeOutput, DDBlocks.TRIMMED_PETRIFIED_PLANKS.asItem(), DDBlocks.PETRIFIED_PLANKS.asItem());
        bookshelfBlock(recipeOutput, DDBlocks.PETRIFIED_BOOKSHELF.asItem(), DDBlocks.PETRIFIED_PLANKS.asItem());
        boardsBlock(recipeOutput, DDBlocks.PETRIFIED_BOARDS.asItem(), DDBlocks.PETRIFIED_PLANKS.asItem(), DDBlocks.PETRIFIED_SLAB.asItem());

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, DDItems.GLOW_GRIME.get())
                .requires(DDBlocks.GLOWSHROOM.get())
                .unlockedBy("has_glowshroom", has(DDBlocks.GLOWSHROOM.get()))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, DDItems.GLOW_GRIME.get(), 2)
                .requires(DDBlocks.GLOWSHROOM_PILEUS.get())
                .unlockedBy("has_glowshroom_block", has(DDBlocks.GLOWSHROOM_PILEUS.get()))
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
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, DDBlocks.SCORCHED_REMAINS_BLOCK.get())
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

    protected static void stoneBlockSet(RecipeOutput recipeOutput, DDStoneBlockSet blockSet) {
        generateBaseAndMiscRecipes(recipeOutput, blockSet);
        generateStairsRecipes(recipeOutput, blockSet);
        generateSlabRecipes(recipeOutput, blockSet);
        generateVerticalSlabRecipes(recipeOutput, blockSet);
        generateWallRecipes(recipeOutput, blockSet);
        generatePillarRecipes(recipeOutput, blockSet);
    }

    public static void generateStairsRecipes(RecipeOutput recipeOutput, DDStoneBlockSet blockSet) {
        var base = blockSet.getBase();
        var polishedBase = blockSet.getPolished();
        var bricksBase = blockSet.getBricks();
        var mossyBase = blockSet.getMossyBricks();

        var baseStairs = blockSet.getBaseStairs();
        if (baseStairs != null && base != null) {
            stonecutterResultFromBase(recipeOutput, baseStairs, base);
            stairsBlock(recipeOutput, baseStairs, base.get().asItem());
        }

        var polishedStairs = blockSet.getPolishedStairs();
        if (polishedStairs != null && base != null && polishedBase != null) {
            stonecutterResultFromBase(recipeOutput, polishedStairs, base);
            stonecutterResultFromBase(recipeOutput, polishedStairs, polishedBase);
            stairsBlock(recipeOutput, polishedStairs, polishedBase.get().asItem());
        }

        var bricksStairs = blockSet.getBricksStairs();
        if (bricksStairs != null && base != null && polishedBase != null && bricksBase != null) {
            stonecutterResultFromBase(recipeOutput, bricksStairs, base);
            stonecutterResultFromBase(recipeOutput, bricksStairs, polishedBase);
            stonecutterResultFromBase(recipeOutput, bricksStairs, bricksBase);
            stairsBlock(recipeOutput, bricksStairs, bricksBase.get().asItem());
        }

        var mossyStairs = blockSet.getMossyBricksStairs();
        if (mossyStairs != null && mossyBase != null) {
            stonecutterResultFromBase(recipeOutput, mossyStairs, mossyBase);
            stairsBlock(recipeOutput, mossyStairs, mossyBase.get().asItem());
        }
    }

    public static void generateSlabRecipes(RecipeOutput recipeOutput, DDStoneBlockSet blockSet) {
        var base = blockSet.getBase();
        var polishedBase = blockSet.getPolished();
        var bricksBase = blockSet.getBricks();
        var mossyBase = blockSet.getMossyBricks();

        var baseSlab = blockSet.getBaseSlab();
        if (baseSlab != null && base != null) {
            stonecutterResultFromBase(recipeOutput, baseSlab, base, 2);
            slabBlock(recipeOutput, baseSlab, base.get().asItem());
        }

        var polishedSlab = blockSet.getPolishedSlab();
        if (polishedSlab != null && base != null && polishedBase != null) {
            stonecutterResultFromBase(recipeOutput, polishedSlab, base, 2);
            stonecutterResultFromBase(recipeOutput, polishedSlab, polishedBase, 2);
            slabBlock(recipeOutput, polishedSlab, polishedBase.get().asItem());
        }

        var bricksSlab = blockSet.getBricksSlab();
        if (bricksSlab != null && base != null && polishedBase != null && bricksBase != null) {
            stonecutterResultFromBase(recipeOutput, bricksSlab, base, 2);
            stonecutterResultFromBase(recipeOutput, bricksSlab, polishedBase, 2);
            stonecutterResultFromBase(recipeOutput, bricksSlab, bricksBase, 2);
            slabBlock(recipeOutput, bricksSlab, bricksBase.get().asItem());
        }

        var mossySlab = blockSet.getMossyBricksSlab();
        if (mossySlab != null && mossyBase != null) {

        }
    }

    public static void generateVerticalSlabRecipes(RecipeOutput recipeOutput, DDStoneBlockSet blockSet) {
        var baseVerticalSlab = blockSet.getBaseVerticalSlab();
        if (baseVerticalSlab != null) {

        }

        var polishedVerticalSlab = blockSet.getPolishedVerticalSlab();
        if (polishedVerticalSlab != null) {

        }

        var bricksVerticalSlab = blockSet.getBricksVerticalSlab();
        if (bricksVerticalSlab != null) {

        }

        var mossyVerticalSlab = blockSet.getMossyBricksVerticalSlab();
        if (mossyVerticalSlab != null) {

        }
    }

    public static void generateWallRecipes(RecipeOutput recipeOutput, DDStoneBlockSet blockSet) {
        var base = blockSet.getBase();
        var polishedBase = blockSet.getPolished();
        var bricksBase = blockSet.getBricks();

        var baseWall = blockSet.getBaseWall();
        if (baseWall != null && base != null) {
            stonecutterResultFromBase(recipeOutput, baseWall, base);
            wallBlock(recipeOutput, baseWall, base.get().asItem());
        }

        var bricksWall = blockSet.getBricksWall();
        if (bricksWall != null && base != null && polishedBase != null && bricksBase != null) {
            stonecutterResultFromBase(recipeOutput, bricksWall, base);
            stonecutterResultFromBase(recipeOutput, bricksWall, bricksBase);
            stonecutterResultFromBase(recipeOutput, bricksWall, polishedBase);
            wallBlock(recipeOutput, bricksWall, bricksBase.get().asItem());
        }
    }

    public static void generatePillarRecipes(RecipeOutput recipeOutput, DDStoneBlockSet blockSet) {
        var base = blockSet.getBase();
        var polishedBase = blockSet.getPolished();
        var pillar = blockSet.getPillar();

        if (pillar != null && base != null && polishedBase != null) {
            stonecutterResultFromBase(recipeOutput, pillar, base);
            stonecutterResultFromBase(recipeOutput, pillar, polishedBase);
            pillarBlock(recipeOutput, pillar, polishedBase.get().asItem());
        }
    }

    public static void generateBaseAndMiscRecipes(RecipeOutput recipeOutput, DDStoneBlockSet blockSet) {
        var base = blockSet.getBase();
        var polishedBase = blockSet.getPolished();
        var bricksBase = blockSet.getBricks();
        var mossyBase = blockSet.getMossyBricks();
        var chiseled = blockSet.getChiseled();
        var cracked = blockSet.getCrackedBricks();

        if (polishedBase != null && base != null) {
            stonecutterResultFromBase(recipeOutput, polishedBase, base);
            twoXtwo(recipeOutput, polishedBase, base.get().asItem(), 4);
        }

        if (bricksBase != null && base != null && polishedBase != null) {
            stonecutterResultFromBase(recipeOutput, bricksBase, base);
            stonecutterResultFromBase(recipeOutput, bricksBase, polishedBase);
            twoXtwo(recipeOutput, bricksBase, polishedBase.get().asItem(), 4);
        }

        if(mossyBase != null) {

        }

        if (cracked != null && bricksBase != null) {
            oreSmelting(recipeOutput,
                    ImmutableList.of(bricksBase.get()),
                    RecipeCategory.BUILDING_BLOCKS,
                    cracked,
                    0.1F,
                    200,
                    cracked.getId().getPath()
            );
        }

        if (chiseled != null && base != null && polishedBase != null && bricksBase != null) {
            stonecutterResultFromBase(recipeOutput, chiseled, base);
            stonecutterResultFromBase(recipeOutput, chiseled, polishedBase);
            stonecutterResultFromBase(recipeOutput, chiseled, bricksBase);

            chiseled(recipeOutput, blockSet.getBricksSlab().asItem(), chiseled.asItem());
        }
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

    private static void chiseled(RecipeOutput recipeOutput, Item slab, Item result) {
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, result)
                .define('#', slab)
                .pattern("#")
                .pattern("#")
                .unlockedBy("has_" + BuiltInRegistries.ITEM.getKey(slab).getPath(), has(slab))
                .save(recipeOutput);
    }

    private static void wallBlock(RecipeOutput recipeOutput, ItemLike result, Item item) {
        ShapedRecipeBuilder
                .shaped(RecipeCategory.BUILDING_BLOCKS, result, 6)
                .define('#', item)
                .pattern("###")
                .pattern("###")
                .unlockedBy("has_" + BuiltInRegistries.ITEM.getKey(item).getPath(), has(item))
                .save(recipeOutput);
    }

    private static void twoXtwo(RecipeOutput recipeOutput, ItemLike result, Item item, int count) {
        ShapedRecipeBuilder
                .shaped(RecipeCategory.BUILDING_BLOCKS, result, count)
                .define('S', item)
                .pattern("SS")
                .pattern("SS")
                .unlockedBy("has_" + BuiltInRegistries.ITEM.getKey(item).getPath(), has(item))
                .save(recipeOutput);
    }



    private void threeXthree(RecipeOutput recipeOutput, ItemLike result, Item item) {
        ShapedRecipeBuilder
                .shaped(RecipeCategory.BUILDING_BLOCKS, result)
                .define('S', item)
                .pattern("SSS")
                .pattern("SSS")
                .pattern("SSS")
                .unlockedBy("has_" + BuiltInRegistries.ITEM.getKey(item).getPath(), has(item))
                .save(recipeOutput);
    }

    private static void stairsBlock(RecipeOutput recipeOutput, ItemLike result, Item item) {
        ShapedRecipeBuilder
                .shaped(RecipeCategory.BUILDING_BLOCKS, result, 4)
                .define('#', item)
                .pattern("#  ")
                .pattern("## ")
                .pattern("###")
                .unlockedBy("has_" + BuiltInRegistries.ITEM.getKey(item).getPath(), has(item))
                .save(recipeOutput);
    }

    private void fenceBlock(RecipeOutput recipeOutput, ItemLike result, Item item) {
        ShapedRecipeBuilder
                .shaped(RecipeCategory.BUILDING_BLOCKS, result, 3)
                .define('#', item)
                .define('S', Items.STICK)
                .pattern("#S#")
                .pattern("#S#")
                .unlockedBy("has_" + BuiltInRegistries.ITEM.getKey(item).getPath(), has(item))
                .save(recipeOutput);
    }

    private static void slabBlock(RecipeOutput recipeOutput, ItemLike result, Item item) {
        ShapedRecipeBuilder
                .shaped(RecipeCategory.BUILDING_BLOCKS, result, 6)
                .define('#', item)
                .pattern("###")
                .unlockedBy("has_" + BuiltInRegistries.ITEM.getKey(item).getPath(), has(item))
                .save(recipeOutput);
    }

    private static void pillarBlock(RecipeOutput recipeOutput, ItemLike result, Item item) {
        ShapedRecipeBuilder
                .shaped(RecipeCategory.BUILDING_BLOCKS, result, 2)
                .define('#', item)
                .pattern("#")
                .pattern("#")
                .unlockedBy("has_" + BuiltInRegistries.ITEM.getKey(item).getPath(), has(item))
                .save(recipeOutput);
    }

    private void trimmedPlanksBlock(RecipeOutput recipeOutput, ItemLike result, Item planks) {
        ShapedRecipeBuilder
                .shaped(RecipeCategory.BUILDING_BLOCKS, result, 3)
                .define('#', planks)
                .pattern("#")
                .pattern("#")
                .pattern("#")
                .unlockedBy(getHasName(planks), has(planks))
                .save(recipeOutput.withConditions(new ModLoadedCondition(DDCompat.NO_MANS_LAND.toString())));
    }

    private void bookshelfBlock(RecipeOutput recipeOutput, ItemLike result, Item planks) {
        ShapedRecipeBuilder
                .shaped(RecipeCategory.BUILDING_BLOCKS, result, 1)
                .define('#', planks)
                .define('b', Items.BOOK)
                .pattern("###")
                .pattern("bbb")
                .pattern("###")
                .unlockedBy(getHasName(planks), has(planks))
                .save(recipeOutput.withConditions(new OrCondition(List.of(new ModLoadedCondition(DDCompat.NO_MANS_LAND.toString()), new ModLoadedCondition(DDCompat.QUARK.toString())))));
    }

    private void boardsBlock(RecipeOutput recipeOutput, ItemLike result, Item planks, Item slab) {
        ShapedRecipeBuilder
                .shaped(RecipeCategory.BUILDING_BLOCKS, result, 1)
                .define('#', slab)
                .pattern("#")
                .pattern("#")
                .unlockedBy(getHasName(planks), has(planks))
                .save(recipeOutput.withConditions(new ModLoadedCondition(DDCompat.WOODWORKS.toString())));
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
                .unlockedBy("has_" + BuiltInRegistries.ITEM.getKey(item).getPath(), has(item))
                .save(recipeOutput);
    }
}