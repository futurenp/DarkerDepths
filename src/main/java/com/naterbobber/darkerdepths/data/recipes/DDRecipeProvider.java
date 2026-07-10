package com.naterbobber.darkerdepths.data.recipes;

import com.google.common.collect.ImmutableList;
import com.naterbobber.darkerdepths.DarkerDepths;
import com.naterbobber.darkerdepths.block.generic.relational.DDBlockSets;
import com.naterbobber.darkerdepths.block.generic.relational.StoneBlockSet;
import com.naterbobber.darkerdepths.block.generic.relational.WoodBlockSet;
import com.naterbobber.darkerdepths.compat.DDCompat;
import com.naterbobber.darkerdepths.init.DDBlocks;
import com.naterbobber.darkerdepths.init.DDItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.BlastingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.conditions.ModLoadedCondition;
import net.neoforged.neoforge.common.conditions.OrCondition;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class DDRecipeProvider extends RecipeProvider {

    public DDRecipeProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> registries) {
        super(packOutput, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput recipeOutput) {
        DDBlockSets.STONE_BLOCK_SETS.forEach(blockSet ->
                StoneBlockSetRecipes.create(recipeOutput, blockSet));

        DDBlockSets.WOOD_BLOCK_SETS.forEach(blockSet ->
                WoodBlockSetRecipes.create(recipeOutput, blockSet));

        threeXthree(recipeOutput, DDBlocks.AMBER_BLOCK.get(), DDItems.AMBER.get());
        threeXthree(recipeOutput, DDBlocks.FORSAKEN_BRONZE_BLOCK.get(), DDItems.FORSAKEN_BRONZE_INGOT.get());

        shaplessOne(recipeOutput, DDItems.AMBER.get(), DDBlocks.AMBER_BLOCK.get().asItem(), 9);
        shaplessOne(recipeOutput, DDItems.FORSAKEN_BRONZE_INGOT.get(), DDBlocks.FORSAKEN_BRONZE_BLOCK.get().asItem(), 9);

        UniqueRecipes.create(recipeOutput);
    }

    private static class WoodBlockSetRecipes {
        public static void create(RecipeOutput recipeOutput, WoodBlockSet blockSet) {
            hangingSign(recipeOutput, blockSet.getHangingSign().get(), blockSet.getStrippedLog().get().asItem());
            stairsBlock(recipeOutput, blockSet.getStairs().get(), blockSet.getPlanks().get().asItem());
            slabBlock(recipeOutput, blockSet.getSlab().get(), blockSet.getPlanks().get().asItem());
            fenceBlock(recipeOutput, blockSet.getFence().get(), blockSet.getPlanks().get().asItem());

            woodItemFromBuilder(recipeOutput, blockSet,
                    fenceGateBuilder(blockSet.getFenceGate().get(), Ingredient.of(blockSet.getPlanks().get())));

            woodItemFromBuilder(recipeOutput, blockSet,
                    buttonBuilder(blockSet.getButton().get(), Ingredient.of(blockSet.getPlanks().get())));

            woodItemFromBuilder(recipeOutput, blockSet,
                    trapdoorBuilder(blockSet.getTrapdoor().get(), Ingredient.of(blockSet.getPlanks().get())));

            woodItemFromBuilder(recipeOutput, blockSet,
                    doorBuilder(blockSet.getDoor().get(), Ingredient.of(blockSet.getPlanks().get())));

            woodItemFromBuilder(recipeOutput, blockSet,
                    signBuilder(blockSet.getSign().get(), Ingredient.of(blockSet.getPlanks().get())));

            pressurePlate(recipeOutput, blockSet.getPressurePlate().get(), blockSet.getPlanks().get());
            planksFromLog(recipeOutput, blockSet.getPlanks().get(), blockSet.getLogTag(), 4);

            woodFromLogs(recipeOutput, blockSet.getWood().get(), blockSet.getLog().get());
            woodFromLogs(recipeOutput, blockSet.getStrippedWood().get(), blockSet.getStrippedLog().get());

            chestBoat(recipeOutput, blockSet.getChestBoat().get(), blockSet.getBoat().get());
            woodenBoat(recipeOutput, blockSet.getBoat().get(), blockSet.getPlanks().get());

            trimmedPlanksBlock(recipeOutput, blockSet.getTrimmedPlanks().get().asItem(), blockSet.getPlanks().get().asItem());
            bookshelfBlock(recipeOutput, blockSet.getBookshelf().get().asItem(), blockSet.getPlanks().get().asItem());
            boardsBlock(recipeOutput, blockSet.getBoards().get().asItem(), blockSet.getPlanks().get().asItem(), blockSet.getSlab().get().asItem());
        }

        private static void woodItemFromBuilder(RecipeOutput recipeOutput, WoodBlockSet blockSet, RecipeBuilder builder) {
            builder.unlockedBy("has_planks", has(blockSet.getPlanks().get().asItem())).save(recipeOutput);
        }
    }

    private static class StoneBlockSetRecipes {
        public static void create(RecipeOutput recipeOutput, StoneBlockSet blockSet) {
            baseAndMisc(recipeOutput, blockSet);
            stairs(recipeOutput, blockSet);
            slabs(recipeOutput, blockSet);
            verticalSlabs(recipeOutput, blockSet);
            walls(recipeOutput, blockSet);
            pillars(recipeOutput, blockSet);
        }

        public static void baseAndMisc(RecipeOutput recipeOutput, StoneBlockSet blockSet) {
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

            if (mossyBase != null) {

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

        public static void stairs(RecipeOutput recipeOutput, StoneBlockSet blockSet) {
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

        public static void slabs(RecipeOutput recipeOutput, StoneBlockSet blockSet) {
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
                stonecutterResultFromBase(recipeOutput, mossySlab, mossyBase, 2);
                slabBlock(recipeOutput, mossySlab, mossyBase.get().asItem());
            }
        }

        public static void verticalSlabs(RecipeOutput recipeOutput, StoneBlockSet blockSet) {
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

        public static void walls(RecipeOutput recipeOutput, StoneBlockSet blockSet) {
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

            var mossyBase = blockSet.getMossyBricks();
            var mossyWall = blockSet.getMossyBricksWall();
            if (mossyWall != null && mossyBase != null) {
                stonecutterResultFromBase(recipeOutput, mossyWall, mossyBase);
                wallBlock(recipeOutput, mossyWall, mossyBase.get().asItem());
            }
        }

        public static void pillars(RecipeOutput recipeOutput, StoneBlockSet blockSet) {
            var base = blockSet.getBase();
            var polishedBase = blockSet.getPolished();
            var pillar = blockSet.getPillar();

            if (pillar != null && base != null && polishedBase != null) {
                stonecutterResultFromBase(recipeOutput, pillar, base);
                stonecutterResultFromBase(recipeOutput, pillar, polishedBase);
                pillarBlock(recipeOutput, pillar, polishedBase.get().asItem());
            }
        }
    }

    private static void stonecutterResultFromBase(RecipeOutput recipeOutput, ItemLike result, ItemLike ingredient) {
        stonecutterResultFromBase(recipeOutput, result, ingredient, 1);
    }

    private static void stonecutterResultFromBase(RecipeOutput recipeOutput, ItemLike result, ItemLike ingredient, int count) {
        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, result, ingredient, count);
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



    private static void threeXthree(RecipeOutput recipeOutput, ItemLike result, Item item) {
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

    private static void fenceBlock(RecipeOutput recipeOutput, ItemLike result, Item item) {
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

    private static void trimmedPlanksBlock(RecipeOutput recipeOutput, ItemLike result, Item planks) {
        ShapedRecipeBuilder
                .shaped(RecipeCategory.BUILDING_BLOCKS, result, 3)
                .define('#', planks)
                .pattern("#")
                .pattern("#")
                .pattern("#")
                .unlockedBy(getHasName(planks), has(planks))
                .save(recipeOutput.withConditions(new ModLoadedCondition(DDCompat.NO_MANS_LAND.toString())));
    }

    private static void bookshelfBlock(RecipeOutput recipeOutput, ItemLike result, Item planks) {
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

    private static void boardsBlock(RecipeOutput recipeOutput, ItemLike result, Item planks, Item slab) {
        ShapedRecipeBuilder
                .shaped(RecipeCategory.BUILDING_BLOCKS, result, 1)
                .define('#', slab)
                .pattern("#")
                .pattern("#")
                .unlockedBy(getHasName(planks), has(planks))
                .save(recipeOutput.withConditions(new ModLoadedCondition(DDCompat.WOODWORKS.toString())));
    }

    protected static void oreSmelting(RecipeOutput recipeOutput, List<ItemLike> items, RecipeCategory recipeCategory, ItemLike p_176594_, float p_176595_, int p_176596_, String p_176597_) {
        oreCooking(recipeOutput, recipeCategory, RecipeSerializer.SMELTING_RECIPE, SmeltingRecipe::new, items, p_176594_, p_176595_, p_176596_, p_176597_, "_from_smelting");
    }

    protected static void oreBlasting(RecipeOutput recipeOutput, List<ItemLike> items, RecipeCategory recipeCategory, ItemLike p_176628_, float p_176629_, int p_176630_, String p_176631_) {
        oreCooking(recipeOutput, recipeCategory, RecipeSerializer.BLASTING_RECIPE, BlastingRecipe::new, items, p_176628_, p_176629_, p_176630_, p_176631_, "_from_blasting");
    }

    private static <T extends AbstractCookingRecipe> void oreCooking(RecipeOutput recipeOutput, RecipeCategory recipeCategory, RecipeSerializer<T> serializer, AbstractCookingRecipe.Factory<T> recipeFactory, List<ItemLike> itemLike, ItemLike item, float experience, int time, String group, String name) {
        for (ItemLike itemlike : itemLike) {
            SimpleCookingRecipeBuilder
                    .generic(Ingredient.of(itemlike), recipeCategory, item, experience, time, serializer, recipeFactory)
                    .group(group)
                    .unlockedBy(getHasName(itemlike), has(itemlike))
                    .save(recipeOutput, DarkerDepths.id(getItemName(item) + name + "_" + getItemName(itemlike)));
        }
    }

    private static void shaplessOne(RecipeOutput recipeOutput, Item result, Item item, int count) {
        ShapelessRecipeBuilder
                .shapeless(RecipeCategory.MISC, result, count)
                .requires(item)
                .unlockedBy("has_" + BuiltInRegistries.ITEM.getKey(item).getPath(), has(item))
                .save(recipeOutput);
    }
}