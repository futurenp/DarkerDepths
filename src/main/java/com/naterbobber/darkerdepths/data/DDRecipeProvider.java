package com.naterbobber.darkerdepths.data;

import com.google.common.collect.ImmutableList;
import com.naterbobber.darkerdepths.DarkerDepths;
import com.naterbobber.darkerdepths.init.DDBlocks;
import com.naterbobber.darkerdepths.init.DDItemTags;
import com.naterbobber.darkerdepths.init.DDItems;
import net.minecraft.core.Registry;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.data.recipes.SingleItemRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleCookingSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;
import java.util.function.Consumer;

public class DDRecipeProvider extends RecipeProvider {

    public DDRecipeProvider(DataGenerator generator) {
        super(generator);
    }

    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> consumer) {
        twoXtwo(consumer, DDBlocks.POLISHED_SHALE.get(), DDBlocks.SHALE.get().asItem(), 4);
        twoXtwo(consumer, DDBlocks.POLISHED_ARIDROCK.get(), DDBlocks.ARIDROCK.get().asItem(), 4);
        twoXtwo(consumer, DDBlocks.POLISHED_LIMESTONE.get(), DDBlocks.LIMESTONE.get().asItem(), 4);
        twoXtwo(consumer, DDBlocks.POLISHED_GRIMESTONE.get(), DDBlocks.GRIMESTONE.get().asItem(), 4);
        twoXtwo(consumer, DDBlocks.SHALE_BRICKS.get(), DDBlocks.POLISHED_SHALE.get().asItem(), 4);
        twoXtwo(consumer, DDBlocks.ARIDROCK_BRICKS.get(), DDBlocks.POLISHED_ARIDROCK.get().asItem(), 4);
        twoXtwo(consumer, DDBlocks.LIMESTONE_BRICKS.get(), DDBlocks.POLISHED_LIMESTONE.get().asItem(), 4);
        twoXtwo(consumer, DDBlocks.GRIMESTONE_BRICKS.get(), DDBlocks.POLISHED_GRIMESTONE.get().asItem(), 4);
        threeXthree(consumer, DDBlocks.SILVER_BLOCK.get(), DDItems.SILVER_INGOT.get());
        threeXthree(consumer, DDBlocks.AMBER_BLOCK.get(), DDBlocks.AMBER.get().asItem());
        oreSmelting(consumer, ImmutableList.of(DDBlocks.AMBER.get()), DDItems.RESIN.get(), 0.1F, 200, "resin");
        oreBlasting(consumer, ImmutableList.of(DDBlocks.AMBER.get()), DDItems.RESIN.get(), 0.1F, 100, "resin");

        stairsBlock(consumer, DDBlocks.SHALE_STAIRS.get(), DDBlocks.SHALE.get().asItem());
        stairsBlock(consumer, DDBlocks.ARIDROCK_STAIRS.get(), DDBlocks.ARIDROCK.get().asItem());
        stairsBlock(consumer, DDBlocks.LIMESTONE_STAIRS.get(), DDBlocks.LIMESTONE.get().asItem());
        stairsBlock(consumer, DDBlocks.GRIMESTONE_STAIRS.get(), DDBlocks.GRIMESTONE.get().asItem());
        stairsBlock(consumer, DDBlocks.POLISHED_SHALE_STAIRS.get(), DDBlocks.POLISHED_SHALE.get().asItem());
        stairsBlock(consumer, DDBlocks.POLISHED_ARIDROCK_STAIRS.get(), DDBlocks.POLISHED_ARIDROCK.get().asItem());
        stairsBlock(consumer, DDBlocks.POLISHED_LIMESTONE_STAIRS.get(), DDBlocks.POLISHED_LIMESTONE.get().asItem());
        stairsBlock(consumer, DDBlocks.POLISHED_GRIMESTONE_STAIRS.get(), DDBlocks.POLISHED_GRIMESTONE.get().asItem());
        stairsBlock(consumer, DDBlocks.SHALE_BRICKS_STAIRS.get(), DDBlocks.SHALE_BRICKS.get().asItem());
        stairsBlock(consumer, DDBlocks.ARIDROCK_BRICKS_STAIRS.get(), DDBlocks.ARIDROCK_BRICKS.get().asItem());
        stairsBlock(consumer, DDBlocks.LIMESTONE_BRICKS_STAIRS.get(), DDBlocks.LIMESTONE_BRICKS.get().asItem());
        stairsBlock(consumer, DDBlocks.GRIMESTONE_BRICKS_STAIRS.get(), DDBlocks.GRIMESTONE_BRICKS.get().asItem());
        stairsBlock(consumer, DDBlocks.PETRIFIED_STAIRS.get(), DDBlocks.PETRIFIED_PLANKS.get().asItem());

        slabBlock(consumer, DDBlocks.SHALE_SLAB.get(), DDBlocks.SHALE.get().asItem());
        slabBlock(consumer, DDBlocks.ARIDROCK_SLAB.get(), DDBlocks.ARIDROCK.get().asItem());
        slabBlock(consumer, DDBlocks.LIMESTONE_SLAB.get(), DDBlocks.LIMESTONE.get().asItem());
        slabBlock(consumer, DDBlocks.GRIMESTONE_SLAB.get(), DDBlocks.GRIMESTONE.get().asItem());
        slabBlock(consumer, DDBlocks.POLISHED_SHALE_SLAB.get(), DDBlocks.POLISHED_SHALE.get().asItem());
        slabBlock(consumer, DDBlocks.POLISHED_ARIDROCK_SLAB.get(), DDBlocks.POLISHED_ARIDROCK.get().asItem());
        slabBlock(consumer, DDBlocks.POLISHED_LIMESTONE_SLAB.get(), DDBlocks.POLISHED_LIMESTONE.get().asItem());
        slabBlock(consumer, DDBlocks.POLISHED_GRIMESTONE_SLAB.get(), DDBlocks.POLISHED_GRIMESTONE.get().asItem());
        slabBlock(consumer, DDBlocks.SHALE_BRICKS_SLAB.get(), DDBlocks.SHALE_BRICKS.get().asItem());
        slabBlock(consumer, DDBlocks.ARIDROCK_BRICKS_SLAB.get(), DDBlocks.ARIDROCK_BRICKS.get().asItem());
        slabBlock(consumer, DDBlocks.LIMESTONE_BRICKS_SLAB.get(), DDBlocks.LIMESTONE_BRICKS.get().asItem());
        slabBlock(consumer, DDBlocks.GRIMESTONE_BRICKS_SLAB.get(), DDBlocks.GRIMESTONE_BRICKS.get().asItem());
        slabBlock(consumer, DDBlocks.PETRIFIED_SLAB.get(), DDBlocks.PETRIFIED_PLANKS.get().asItem());

        wallBlock(consumer, DDBlocks.SHALE_WALL.get(), DDBlocks.SHALE.get().asItem());
        wallBlock(consumer, DDBlocks.ARIDROCK_WALL.get(), DDBlocks.ARIDROCK.get().asItem());
        wallBlock(consumer, DDBlocks.LIMESTONE_WALL.get(), DDBlocks.LIMESTONE.get().asItem());
        wallBlock(consumer, DDBlocks.GRIMESTONE_WALL.get(), DDBlocks.GRIMESTONE.get().asItem());
        wallBlock(consumer, DDBlocks.SHALE_BRICKS_WALL.get(), DDBlocks.SHALE_BRICKS.get().asItem());
        wallBlock(consumer, DDBlocks.ARIDROCK_BRICKS_WALL.get(), DDBlocks.ARIDROCK_BRICKS.get().asItem());
        wallBlock(consumer, DDBlocks.LIMESTONE_BRICKS_WALL.get(), DDBlocks.LIMESTONE_BRICKS.get().asItem());
        wallBlock(consumer, DDBlocks.GRIMESTONE_BRICKS_WALL.get(), DDBlocks.GRIMESTONE_BRICKS.get().asItem());

        oreSmelting(consumer, ImmutableList.of(DDBlocks.ARIDROCK_COAL_ORE.get()), Items.COAL, 0.1F, 200, "coal");
        oreSmelting(consumer, ImmutableList.of(DDBlocks.ARIDROCK_IRON_ORE.get()), Items.IRON_INGOT, 0.1F, 200, "iron_ingot");
        oreSmelting(consumer, ImmutableList.of(DDBlocks.ARIDROCK_GOLD_ORE.get()), Items.GOLD_INGOT, 0.1F, 200, "gold_ingot");
        oreSmelting(consumer, ImmutableList.of(DDBlocks.ARIDROCK_REDSTONE_ORE.get()), Items.REDSTONE, 0.1F, 200, "redstone");
        oreSmelting(consumer, ImmutableList.of(DDBlocks.ARIDROCK_LAPIS_ORE.get()), Items.LAPIS_LAZULI, 0.1F, 200, "lapis_lazuli");
        oreSmelting(consumer, ImmutableList.of(DDBlocks.ARIDROCK_DIAMOND_ORE.get()), Items.DIAMOND, 0.1F, 200, "diamond");
        oreSmelting(consumer, ImmutableList.of(DDBlocks.ARIDROCK_SILVER_ORE.get()), DDItems.SILVER_INGOT.get(), 0.1F, 200, "silver_ingot");

        oreBlasting(consumer, ImmutableList.of(DDBlocks.ARIDROCK_COAL_ORE.get()), Items.COAL, 0.1F, 100, "coal");
        oreBlasting(consumer, ImmutableList.of(DDBlocks.ARIDROCK_IRON_ORE.get()), Items.IRON_INGOT, 0.1F, 100, "iron_ingot");
        oreBlasting(consumer, ImmutableList.of(DDBlocks.ARIDROCK_GOLD_ORE.get()), Items.GOLD_INGOT, 0.1F, 100, "gold_ingot");
        oreBlasting(consumer, ImmutableList.of(DDBlocks.ARIDROCK_REDSTONE_ORE.get()), Items.REDSTONE, 0.1F, 100, "redstone");
        oreBlasting(consumer, ImmutableList.of(DDBlocks.ARIDROCK_LAPIS_ORE.get()), Items.LAPIS_LAZULI, 0.1F, 100, "lapis_lazuli");
        oreBlasting(consumer, ImmutableList.of(DDBlocks.ARIDROCK_DIAMOND_ORE.get()), Items.DIAMOND, 0.1F, 100, "diamond");
        oreBlasting(consumer, ImmutableList.of(DDBlocks.ARIDROCK_SILVER_ORE.get()), DDItems.SILVER_INGOT.get(), 0.1F, 100, "silver_ingot");

        oreSmelting(consumer, ImmutableList.of(DDBlocks.LIMESTONE_COAL_ORE.get()), Items.COAL, 0.1F, 200, "coal");
        oreSmelting(consumer, ImmutableList.of(DDBlocks.LIMESTONE_IRON_ORE.get()), Items.IRON_INGOT, 0.1F, 200, "iron_ingot");
        oreSmelting(consumer, ImmutableList.of(DDBlocks.LIMESTONE_GOLD_ORE.get()), Items.GOLD_INGOT, 0.1F, 200, "gold_ingot");
        oreSmelting(consumer, ImmutableList.of(DDBlocks.LIMESTONE_REDSTONE_ORE.get()), Items.REDSTONE, 0.1F, 200, "redstone");
        oreSmelting(consumer, ImmutableList.of(DDBlocks.LIMESTONE_LAPIS_ORE.get()), Items.LAPIS_LAZULI, 0.1F, 200, "lapis_lazuli");
        oreSmelting(consumer, ImmutableList.of(DDBlocks.LIMESTONE_DIAMOND_ORE.get()), Items.DIAMOND, 0.1F, 200, "diamond");
        oreSmelting(consumer, ImmutableList.of(DDBlocks.LIMESTONE_SILVER_ORE.get()), DDItems.SILVER_INGOT.get(), 0.1F, 200, "silver_ingot");

        oreSmelting(consumer, ImmutableList.of(DDBlocks.SHALE_BRICKS.get()), DDBlocks.CRACKED_SHALE_BRICKS.get(), 0.1F, 200, "cracked_shale_bricks");
        oreSmelting(consumer, ImmutableList.of(DDBlocks.ARIDROCK_BRICKS.get()), DDBlocks.CRACKED_ARIDROCK_BRICKS.get(), 0.1F, 200, "cracked_aridrock_bricks");
        oreSmelting(consumer, ImmutableList.of(DDBlocks.LIMESTONE_BRICKS.get()), DDBlocks.CRACKED_LIMESTONE_BRICKS.get(), 0.1F, 200, "cracked_limestone_bricks");
        oreSmelting(consumer, ImmutableList.of(DDBlocks.GRIMESTONE_BRICKS.get()), DDBlocks.CRACKED_GRIMESTONE_BRICKS.get(), 0.1F, 200, "cracked_grimestone_bricks");

        oreBlasting(consumer, ImmutableList.of(DDBlocks.LIMESTONE_COAL_ORE.get()), Items.COAL, 0.1F, 100, "coal");
        oreBlasting(consumer, ImmutableList.of(DDBlocks.LIMESTONE_IRON_ORE.get()), Items.IRON_INGOT, 0.1F, 100, "iron_ingot");
        oreBlasting(consumer, ImmutableList.of(DDBlocks.LIMESTONE_GOLD_ORE.get()), Items.GOLD_INGOT, 0.1F, 100, "gold_ingot");
        oreBlasting(consumer, ImmutableList.of(DDBlocks.LIMESTONE_REDSTONE_ORE.get()), Items.REDSTONE, 0.1F, 100, "redstone");
        oreBlasting(consumer, ImmutableList.of(DDBlocks.LIMESTONE_LAPIS_ORE.get()), Items.LAPIS_LAZULI, 0.1F, 100, "lapis_lazuli");
        oreBlasting(consumer, ImmutableList.of(DDBlocks.LIMESTONE_DIAMOND_ORE.get()), Items.DIAMOND, 0.1F, 100, "diamond");
        oreBlasting(consumer, ImmutableList.of(DDBlocks.LIMESTONE_SILVER_ORE.get()), DDItems.SILVER_INGOT.get(), 0.1F, 100, "silver_ingot");

        shaplessOne(consumer, DDItems.SILVER_INGOT.get(), DDBlocks.SILVER_BLOCK.get().asItem(), 9);
        shaplessOne(consumer, DDItems.RAW_SILVER.get(), DDBlocks.RAW_SILVER_BLOCK.get().asItem(), 9);

        fenceBlock(consumer, DDBlocks.PETRIFIED_FENCE.get(), DDBlocks.PETRIFIED_PLANKS.get().asItem());

        chiseled(consumer, DDBlocks.POLISHED_SHALE_SLAB.get().asItem(), DDBlocks.CHISELED_SHALE_BRICKS.get().asItem());
        chiseled(consumer, DDBlocks.POLISHED_ARIDROCK_SLAB.get().asItem(), DDBlocks.CHISELED_ARIDROCK_BRICKS.get().asItem());
        chiseled(consumer, DDBlocks.POLISHED_LIMESTONE_SLAB.get().asItem(), DDBlocks.CHISELED_LIMESTONE_BRICKS.get().asItem());
        chiseled(consumer, DDBlocks.POLISHED_GRIMESTONE_SLAB.get().asItem(), DDBlocks.CHISELED_GRIMESTONE_BRICKS.get().asItem());

        ShapedRecipeBuilder
                .shaped(DDBlocks.PETRIFIED_FENCE_GATE.get().asItem())
                .define('#', Items.STICK)
                .define('W', DDBlocks.PETRIFIED_PLANKS.get().asItem())
                .pattern("#W#")
                .pattern("#W#")
                .unlockedBy("has_planks", has(DDBlocks.PETRIFIED_PLANKS.get().asItem()))
                .save(consumer);

        ShapelessRecipeBuilder.shapeless(DDBlocks.PETRIFIED_BUTTON.get())
                        .requires(DDBlocks.PETRIFIED_PLANKS.get().asItem())
                        .unlockedBy("has_planks", has(DDBlocks.PETRIFIED_PLANKS.get())).save(consumer);

        ShapedRecipeBuilder
                .shaped(DDItems.PETRIFIED_SIGN.get(), 3)
                .group("sign")
                .define('#', DDBlocks.PETRIFIED_PLANKS.get())
                .define('X', Items.STICK)
                .pattern("###")
                .pattern("###")
                .pattern(" X ")
                .unlockedBy("has_planks", has(DDBlocks.PETRIFIED_PLANKS.get()))
                .save(consumer);

        ShapedRecipeBuilder
                .shaped(DDBlocks.PETRIFIED_PRESSURE_PLATE.get())
                .define('#', DDBlocks.PETRIFIED_PLANKS.get())
                .pattern("##")
                .unlockedBy("has_planks", has(DDBlocks.PETRIFIED_PLANKS.get()))
                .save(consumer);

        ShapedRecipeBuilder
                .shaped(DDBlocks.PETRIFIED_DOOR.get(), 3)
                .group("wooden_door")
                .define('#', DDBlocks.PETRIFIED_PLANKS.get())
                .pattern("##")
                .pattern("##")
                .pattern("##")
                .unlockedBy("has_planks", has(DDBlocks.PETRIFIED_PLANKS.get())).save(consumer);

        ShapedRecipeBuilder
                .shaped(DDBlocks.PETRIFIED_TRAPDOOR.get(), 2)
                .group("wooden_trapdoor")
                .define('#', DDBlocks.PETRIFIED_PLANKS.get())
                .pattern("###")
                .pattern("###")
                .unlockedBy("has_planks", has(DDBlocks.PETRIFIED_PLANKS.get())).save(consumer);

        ShapelessRecipeBuilder
                .shapeless(DDBlocks.PETRIFIED_PLANKS.get(), 4)
                .requires(DDItemTags.PETRIFIED_LOGS)
                .group("planks")
                .unlockedBy("has_logs", has(DDItemTags.PETRIFIED_LOGS)).save(consumer);

        stonecutterResultFromBase(consumer, DDBlocks.CHISELED_SHALE_BRICKS.get(), DDBlocks.SHALE.get());
        stonecutterResultFromBase(consumer, DDBlocks.CHISELED_ARIDROCK_BRICKS.get(), DDBlocks.ARIDROCK.get());
        stonecutterResultFromBase(consumer, DDBlocks.CHISELED_LIMESTONE_BRICKS.get(), DDBlocks.LIMESTONE.get());
        stonecutterResultFromBase(consumer, DDBlocks.CHISELED_GRIMESTONE_BRICKS.get(), DDBlocks.GRIMESTONE.get());
        stonecutterResultFromBase(consumer, DDBlocks.CHISELED_SHALE_BRICKS.get(), DDBlocks.POLISHED_SHALE.get());
        stonecutterResultFromBase(consumer, DDBlocks.CHISELED_ARIDROCK_BRICKS.get(), DDBlocks.POLISHED_ARIDROCK.get());
        stonecutterResultFromBase(consumer, DDBlocks.CHISELED_LIMESTONE_BRICKS.get(), DDBlocks.POLISHED_LIMESTONE.get());
        stonecutterResultFromBase(consumer, DDBlocks.CHISELED_GRIMESTONE_BRICKS.get(), DDBlocks.POLISHED_GRIMESTONE.get());

        stonecutterResultFromBase(consumer, DDBlocks.SHALE_STAIRS.get(), DDBlocks.SHALE.get());
        stonecutterResultFromBase(consumer, DDBlocks.ARIDROCK_STAIRS.get(), DDBlocks.ARIDROCK.get());
        stonecutterResultFromBase(consumer, DDBlocks.LIMESTONE_STAIRS.get(), DDBlocks.LIMESTONE.get());
        stonecutterResultFromBase(consumer, DDBlocks.GRIMESTONE_STAIRS.get(), DDBlocks.GRIMESTONE.get());
        stonecutterResultFromBase(consumer, DDBlocks.SHALE_SLAB.get(), DDBlocks.SHALE.get(), 2);
        stonecutterResultFromBase(consumer, DDBlocks.ARIDROCK_SLAB.get(), DDBlocks.ARIDROCK.get(), 2);
        stonecutterResultFromBase(consumer, DDBlocks.LIMESTONE_SLAB.get(), DDBlocks.LIMESTONE.get(), 2);
        stonecutterResultFromBase(consumer, DDBlocks.GRIMESTONE_SLAB.get(), DDBlocks.GRIMESTONE.get(), 2);
        stonecutterResultFromBase(consumer, DDBlocks.SHALE_WALL.get(), DDBlocks.SHALE.get());
        stonecutterResultFromBase(consumer, DDBlocks.ARIDROCK_WALL.get(), DDBlocks.ARIDROCK.get());
        stonecutterResultFromBase(consumer, DDBlocks.LIMESTONE_WALL.get(), DDBlocks.LIMESTONE.get());
        stonecutterResultFromBase(consumer, DDBlocks.GRIMESTONE_WALL.get(), DDBlocks.GRIMESTONE.get());

        stonecutterResultFromBase(consumer, DDBlocks.POLISHED_SHALE.get(), DDBlocks.SHALE.get());
        stonecutterResultFromBase(consumer, DDBlocks.POLISHED_ARIDROCK.get(), DDBlocks.ARIDROCK.get());
        stonecutterResultFromBase(consumer, DDBlocks.POLISHED_LIMESTONE.get(), DDBlocks.LIMESTONE.get());
        stonecutterResultFromBase(consumer, DDBlocks.POLISHED_GRIMESTONE.get(), DDBlocks.GRIMESTONE.get());
        stonecutterResultFromBase(consumer, DDBlocks.POLISHED_SHALE_STAIRS.get(), DDBlocks.SHALE.get());
        stonecutterResultFromBase(consumer, DDBlocks.POLISHED_ARIDROCK_STAIRS.get(), DDBlocks.ARIDROCK.get());
        stonecutterResultFromBase(consumer, DDBlocks.POLISHED_LIMESTONE_STAIRS.get(), DDBlocks.LIMESTONE.get());
        stonecutterResultFromBase(consumer, DDBlocks.POLISHED_GRIMESTONE_STAIRS.get(), DDBlocks.GRIMESTONE.get());
        stonecutterResultFromBase(consumer, DDBlocks.POLISHED_SHALE_SLAB.get(), DDBlocks.SHALE.get(), 2);
        stonecutterResultFromBase(consumer, DDBlocks.POLISHED_ARIDROCK_SLAB.get(), DDBlocks.ARIDROCK.get(), 2);
        stonecutterResultFromBase(consumer, DDBlocks.POLISHED_LIMESTONE_SLAB.get(), DDBlocks.LIMESTONE.get(), 2);
        stonecutterResultFromBase(consumer, DDBlocks.POLISHED_GRIMESTONE_SLAB.get(), DDBlocks.GRIMESTONE.get(), 2);

        stonecutterResultFromBase(consumer, DDBlocks.POLISHED_SHALE_STAIRS.get(), DDBlocks.POLISHED_SHALE.get());
        stonecutterResultFromBase(consumer, DDBlocks.POLISHED_ARIDROCK_STAIRS.get(), DDBlocks.POLISHED_ARIDROCK.get());
        stonecutterResultFromBase(consumer, DDBlocks.POLISHED_LIMESTONE_STAIRS.get(), DDBlocks.POLISHED_LIMESTONE.get());
        stonecutterResultFromBase(consumer, DDBlocks.POLISHED_GRIMESTONE_STAIRS.get(), DDBlocks.POLISHED_GRIMESTONE.get());
        stonecutterResultFromBase(consumer, DDBlocks.POLISHED_SHALE_SLAB.get(), DDBlocks.POLISHED_SHALE.get(), 2);
        stonecutterResultFromBase(consumer, DDBlocks.POLISHED_ARIDROCK_SLAB.get(), DDBlocks.POLISHED_ARIDROCK.get(), 2);
        stonecutterResultFromBase(consumer, DDBlocks.POLISHED_LIMESTONE_SLAB.get(), DDBlocks.POLISHED_LIMESTONE.get(), 2);
        stonecutterResultFromBase(consumer, DDBlocks.POLISHED_GRIMESTONE_SLAB.get(), DDBlocks.POLISHED_GRIMESTONE.get(), 2);

        stonecutterResultFromBase(consumer, DDBlocks.SHALE_BRICKS.get(), DDBlocks.SHALE.get());
        stonecutterResultFromBase(consumer, DDBlocks.ARIDROCK_BRICKS.get(), DDBlocks.ARIDROCK.get());
        stonecutterResultFromBase(consumer, DDBlocks.LIMESTONE_BRICKS.get(), DDBlocks.LIMESTONE.get());
        stonecutterResultFromBase(consumer, DDBlocks.GRIMESTONE_BRICKS.get(), DDBlocks.GRIMESTONE.get());
        stonecutterResultFromBase(consumer, DDBlocks.SHALE_BRICKS_STAIRS.get(), DDBlocks.SHALE.get());
        stonecutterResultFromBase(consumer, DDBlocks.ARIDROCK_BRICKS_STAIRS.get(), DDBlocks.ARIDROCK.get());
        stonecutterResultFromBase(consumer, DDBlocks.LIMESTONE_BRICKS_STAIRS.get(), DDBlocks.LIMESTONE.get());
        stonecutterResultFromBase(consumer, DDBlocks.GRIMESTONE_BRICKS_STAIRS.get(), DDBlocks.GRIMESTONE.get());
        stonecutterResultFromBase(consumer, DDBlocks.SHALE_BRICKS_SLAB.get(), DDBlocks.SHALE.get(), 2);
        stonecutterResultFromBase(consumer, DDBlocks.ARIDROCK_BRICKS_SLAB.get(), DDBlocks.ARIDROCK.get(), 2);
        stonecutterResultFromBase(consumer, DDBlocks.LIMESTONE_BRICKS_SLAB.get(), DDBlocks.LIMESTONE.get(), 2);
        stonecutterResultFromBase(consumer, DDBlocks.GRIMESTONE_BRICKS_SLAB.get(), DDBlocks.GRIMESTONE.get(), 2);
        stonecutterResultFromBase(consumer, DDBlocks.SHALE_BRICKS_WALL.get(), DDBlocks.SHALE.get());
        stonecutterResultFromBase(consumer, DDBlocks.ARIDROCK_BRICKS_WALL.get(), DDBlocks.ARIDROCK.get());
        stonecutterResultFromBase(consumer, DDBlocks.LIMESTONE_BRICKS_WALL.get(), DDBlocks.LIMESTONE.get());
        stonecutterResultFromBase(consumer, DDBlocks.GRIMESTONE_BRICKS_WALL.get(), DDBlocks.GRIMESTONE.get());

        stonecutterResultFromBase(consumer, DDBlocks.SHALE_BRICKS.get(), DDBlocks.POLISHED_SHALE.get());
        stonecutterResultFromBase(consumer, DDBlocks.ARIDROCK_BRICKS.get(), DDBlocks.POLISHED_ARIDROCK.get());
        stonecutterResultFromBase(consumer, DDBlocks.LIMESTONE_BRICKS.get(), DDBlocks.POLISHED_LIMESTONE.get());
        stonecutterResultFromBase(consumer, DDBlocks.GRIMESTONE_BRICKS.get(), DDBlocks.POLISHED_GRIMESTONE.get());
        stonecutterResultFromBase(consumer, DDBlocks.SHALE_BRICKS_STAIRS.get(), DDBlocks.POLISHED_SHALE.get());
        stonecutterResultFromBase(consumer, DDBlocks.ARIDROCK_BRICKS_STAIRS.get(), DDBlocks.POLISHED_ARIDROCK.get());
        stonecutterResultFromBase(consumer, DDBlocks.LIMESTONE_BRICKS_STAIRS.get(), DDBlocks.POLISHED_LIMESTONE.get());
        stonecutterResultFromBase(consumer, DDBlocks.GRIMESTONE_BRICKS_STAIRS.get(), DDBlocks.POLISHED_GRIMESTONE.get());
        stonecutterResultFromBase(consumer, DDBlocks.SHALE_BRICKS_SLAB.get(), DDBlocks.POLISHED_SHALE.get(), 2);
        stonecutterResultFromBase(consumer, DDBlocks.ARIDROCK_BRICKS_SLAB.get(), DDBlocks.POLISHED_ARIDROCK.get(), 2);
        stonecutterResultFromBase(consumer, DDBlocks.LIMESTONE_BRICKS_SLAB.get(), DDBlocks.POLISHED_LIMESTONE.get(), 2);
        stonecutterResultFromBase(consumer, DDBlocks.GRIMESTONE_BRICKS_SLAB.get(), DDBlocks.POLISHED_GRIMESTONE.get(), 2);
        stonecutterResultFromBase(consumer, DDBlocks.SHALE_BRICKS_WALL.get(), DDBlocks.POLISHED_SHALE.get());
        stonecutterResultFromBase(consumer, DDBlocks.ARIDROCK_BRICKS_WALL.get(), DDBlocks.POLISHED_ARIDROCK.get());
        stonecutterResultFromBase(consumer, DDBlocks.LIMESTONE_BRICKS_WALL.get(), DDBlocks.POLISHED_LIMESTONE.get());
        stonecutterResultFromBase(consumer, DDBlocks.GRIMESTONE_BRICKS_WALL.get(), DDBlocks.POLISHED_GRIMESTONE.get());

        stonecutterResultFromBase(consumer, DDBlocks.SHALE_BRICKS.get(), DDBlocks.SHALE_BRICKS.get());
        stonecutterResultFromBase(consumer, DDBlocks.ARIDROCK_BRICKS.get(), DDBlocks.ARIDROCK_BRICKS.get());
        stonecutterResultFromBase(consumer, DDBlocks.LIMESTONE_BRICKS.get(), DDBlocks.LIMESTONE_BRICKS.get());
        stonecutterResultFromBase(consumer, DDBlocks.GRIMESTONE_BRICKS.get(), DDBlocks.GRIMESTONE_BRICKS.get());
        stonecutterResultFromBase(consumer, DDBlocks.SHALE_BRICKS_STAIRS.get(), DDBlocks.SHALE_BRICKS.get());
        stonecutterResultFromBase(consumer, DDBlocks.ARIDROCK_BRICKS_STAIRS.get(), DDBlocks.ARIDROCK_BRICKS.get());
        stonecutterResultFromBase(consumer, DDBlocks.LIMESTONE_BRICKS_STAIRS.get(), DDBlocks.LIMESTONE_BRICKS.get());
        stonecutterResultFromBase(consumer, DDBlocks.GRIMESTONE_BRICKS_STAIRS.get(), DDBlocks.GRIMESTONE_BRICKS.get());
        stonecutterResultFromBase(consumer, DDBlocks.SHALE_BRICKS_SLAB.get(), DDBlocks.SHALE_BRICKS.get(), 2);
        stonecutterResultFromBase(consumer, DDBlocks.ARIDROCK_BRICKS_SLAB.get(), DDBlocks.ARIDROCK_BRICKS.get(), 2);
        stonecutterResultFromBase(consumer, DDBlocks.LIMESTONE_BRICKS_SLAB.get(), DDBlocks.LIMESTONE_BRICKS.get(), 2);
        stonecutterResultFromBase(consumer, DDBlocks.GRIMESTONE_BRICKS_SLAB.get(), DDBlocks.GRIMESTONE_BRICKS.get(), 2);
        stonecutterResultFromBase(consumer, DDBlocks.SHALE_BRICKS_WALL.get(), DDBlocks.SHALE_BRICKS.get());
        stonecutterResultFromBase(consumer, DDBlocks.ARIDROCK_BRICKS_WALL.get(), DDBlocks.ARIDROCK_BRICKS.get());
        stonecutterResultFromBase(consumer, DDBlocks.LIMESTONE_BRICKS_WALL.get(), DDBlocks.LIMESTONE_BRICKS.get());
        stonecutterResultFromBase(consumer, DDBlocks.GRIMESTONE_BRICKS_WALL.get(), DDBlocks.GRIMESTONE_BRICKS.get());

        ShapedRecipeBuilder.
                shaped(DDItems.ROPE.get(), 2)
                .define('#', DDItems.RESIN.get())
                .define('G', Items.STICK)
                .pattern(" # ")
                .pattern(" G ")
                .pattern(" # ")
                .unlockedBy("has_resin", has(DDItems.RESIN.get())).save(consumer);
    }

    protected static void stonecutterResultFromBase(Consumer<FinishedRecipe> consumer, ItemLike result, ItemLike ingredient) {
        stonecutterResultFromBase(consumer, result, ingredient, 1);
    }

    protected static void stonecutterResultFromBase(Consumer<FinishedRecipe> consumer, ItemLike result, ItemLike ingredient, int count) {
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ingredient), result, count).unlockedBy(getHasName(ingredient), has(ingredient)).save(consumer, new ResourceLocation(DarkerDepths.MODID, getConversionRecipeName(result, ingredient) + "_stonecutting"));
    }

    protected static String getConversionRecipeName(ItemLike result, ItemLike ingredient) {
        return getItemName(result) + "_from_" + getItemName(ingredient);
    }

    protected static String getItemName(ItemLike item) {
        return ForgeRegistries.ITEMS.getKey(item.asItem()).getPath();
    }

    private void chiseled(Consumer<FinishedRecipe> consumer, Item slab, Item result) {
        ShapedRecipeBuilder.shaped(result, 1)
                .define('#', slab)
                .pattern("#")
                .pattern("#")
                .unlockedBy("has_" + Registry.ITEM.getKey(slab).getPath(), has(slab))
                .save(consumer);
    }

    private void wallBlock(Consumer<FinishedRecipe> consumer, ItemLike result, Item item) {
        ShapedRecipeBuilder
                .shaped(result, 6)
                .define('#', item)
                .pattern("###")
                .pattern("###")
                .unlockedBy("has_" + Registry.ITEM.getKey(item).getPath(), has(item)).save(consumer);
    }

    private void twoXtwo(Consumer<FinishedRecipe> consumer, ItemLike result, Item item, int count) {
        ShapedRecipeBuilder.
                shaped(result, count)
                .define('S', item)
                .pattern("SS")
                .pattern("SS")
                .unlockedBy("has_" + Registry.ITEM.getKey(item).getPath(), has(item)).save(consumer);
    }

    private void threeXthree(Consumer<FinishedRecipe> consumer, ItemLike result, Item item) {
        ShapedRecipeBuilder.
                shaped(result)
                .define('S', item)
                .pattern("SSS")
                .pattern("SSS")
                .pattern("SSS")
                .unlockedBy("has_" + Registry.ITEM.getKey(item).getPath(), has(item)).save(consumer);
    }

    private void stairsBlock(Consumer<FinishedRecipe> consumer, ItemLike result, Item item) {
        ShapedRecipeBuilder.
                shaped(result, 4)
                .define('#', item)
                .pattern("#  ")
                .pattern("## ")
                .pattern("###")
                .unlockedBy("has_" + Registry.ITEM.getKey(item).getPath(), has(item)).save(consumer);
    }

    private void fenceBlock(Consumer<FinishedRecipe> consumer, ItemLike result, Item item) {
        ShapedRecipeBuilder.
                shaped(result, 3)
                .define('#', item)
                .define('S', Items.STICK)
                .pattern("#S#")
                .pattern("#S#")
                .unlockedBy("has_" + Registry.ITEM.getKey(item).getPath(), has(item)).save(consumer);
    }

    private void slabBlock(Consumer<FinishedRecipe> consumer, ItemLike result, Item item) {
        ShapedRecipeBuilder.
                shaped(result, 6)
                .define('#', item)
                .pattern("###")
                .unlockedBy("has_" + Registry.ITEM.getKey(item).getPath(), has(item)).save(consumer);
    }

    protected static void oreSmelting(Consumer<FinishedRecipe> consumer, List<ItemLike> items, ItemLike p_176594_, float p_176595_, int p_176596_, String p_176597_) {
        oreCooking(consumer, RecipeSerializer.SMELTING_RECIPE, items, p_176594_, p_176595_, p_176596_, p_176597_, "_from_smelting");
    }

    protected static void oreBlasting(Consumer<FinishedRecipe> consumer, List<ItemLike> items, ItemLike p_176628_, float p_176629_, int p_176630_, String p_176631_) {
        oreCooking(consumer, RecipeSerializer.BLASTING_RECIPE, items, p_176628_, p_176629_, p_176630_, p_176631_, "_from_blasting");
    }

    protected static void oreCooking(Consumer<FinishedRecipe> consumer, SimpleCookingSerializer<?> serializer, List<ItemLike> itemLike, ItemLike item, float experience, int time, String group, String name) {
        for (ItemLike itemlike : itemLike) {
            SimpleCookingRecipeBuilder.cooking(Ingredient.of(itemlike), item, experience, time, serializer).group(group).unlockedBy(getHasName(itemlike), has(itemlike)).save(consumer, new ResourceLocation(DarkerDepths.MODID, getItemName(item) + name + "_" + getItemName(itemlike)));
        }
    }

    private void shaplessOne(Consumer<FinishedRecipe> consumer, Item result, Item item, int count) {
        ShapelessRecipeBuilder.
                shapeless(result, count)
                .requires(item)
                .unlockedBy("has_" + Registry.ITEM.getKey(item).getPath(), has(item)).save(consumer);
    }
}
