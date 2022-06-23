package com.naterbobber.darkerdepths.data;

import com.naterbobber.darkerdepths.DarkerDepths;
import com.naterbobber.darkerdepths.compat.init.ForgeBlockTags;
import com.naterbobber.darkerdepths.init.DDBlocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

public class DDBlockTagsProvider extends BlockTagsProvider {

    public DDBlockTagsProvider(DataGenerator dataGenerator, @Nullable ExistingFileHelper existingFileHelper) {
        super(dataGenerator, DarkerDepths.MODID, existingFileHelper);
    }

    @Override
    protected void addTags() {
        this.tag(BlockTags.LOGS).add(DDBlocks.PETRIFIED_LOG.get(), DDBlocks.STRIPPED_PETRIFIED_LOG.get(), DDBlocks.PETRIFIED_WOOD.get(), DDBlocks.STRIPPED_PETRIFIED_WOOD.get());
        this.tag(BlockTags.BASE_STONE_OVERWORLD).add(DDBlocks.SHALE.get(), DDBlocks.ARIDROCK.get(), DDBlocks.LIMESTONE.get(), DDBlocks.GRIMESTONE.get());
        this.tag(BlockTags.BEACON_BASE_BLOCKS).add(DDBlocks.SILVER_BLOCK.get());
        this.tag(BlockTags.WALLS).add(DDBlocks.SHALE_WALL.get(), DDBlocks.ARIDROCK_WALL.get(), DDBlocks.LIMESTONE_WALL.get(), DDBlocks.GRIMESTONE_WALL.get(), DDBlocks.SHALE_BRICKS_WALL.get(), DDBlocks.ARIDROCK_BRICKS_WALL.get(), DDBlocks.LIMESTONE_BRICKS_WALL.get(), DDBlocks.GRIMESTONE_BRICKS_WALL.get());
        this.tag(BlockTags.WOODEN_FENCES).add(DDBlocks.PETRIFIED_FENCE.get());
        this.tag(BlockTags.FENCE_GATES).add(DDBlocks.PETRIFIED_FENCE_GATE.get());
        this.tag(BlockTags.MINEABLE_WITH_HOE).add(DDBlocks.GLOWSHROOM_BLOCK.get(), DDBlocks.GLOWSHROOM_STEM.get());
        this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(DDBlocks.SHALE.get(), DDBlocks.ARIDROCK.get(), DDBlocks.LIMESTONE.get(), DDBlocks.GRIMESTONE.get(), DDBlocks.SHALE_SLAB.get(), DDBlocks.SHALE_STAIRS.get(), DDBlocks.SHALE_WALL.get(), DDBlocks.ARIDROCK_SLAB.get(), DDBlocks.ARIDROCK_STAIRS.get(), DDBlocks.ARIDROCK_WALL.get(), DDBlocks.LIMESTONE_SLAB.get(), DDBlocks.LIMESTONE_STAIRS.get(), DDBlocks.LIMESTONE_WALL.get(), DDBlocks.GRIMESTONE_SLAB.get(), DDBlocks.GRIMESTONE_STAIRS.get(), DDBlocks.GRIMESTONE_WALL.get(), DDBlocks.POLISHED_SHALE.get(), DDBlocks.POLISHED_SHALE_SLAB.get(), DDBlocks.POLISHED_SHALE_STAIRS.get(), DDBlocks.POLISHED_ARIDROCK.get(), DDBlocks.POLISHED_ARIDROCK_SLAB.get(), DDBlocks.POLISHED_ARIDROCK_STAIRS.get(), DDBlocks.POLISHED_LIMESTONE.get(), DDBlocks.POLISHED_LIMESTONE_SLAB.get(), DDBlocks.POLISHED_LIMESTONE_STAIRS.get(), DDBlocks.POLISHED_GRIMESTONE.get(), DDBlocks.POLISHED_GRIMESTONE_SLAB.get(), DDBlocks.POLISHED_GRIMESTONE_STAIRS.get(), DDBlocks.SHALE_BRICKS.get(), DDBlocks.SHALE_BRICKS_STAIRS.get(), DDBlocks.SHALE_BRICKS_SLAB.get(), DDBlocks.SHALE_BRICKS_WALL.get(), DDBlocks.ARIDROCK_BRICKS.get(), DDBlocks.ARIDROCK_BRICKS_STAIRS.get(), DDBlocks.ARIDROCK_BRICKS_SLAB.get(), DDBlocks.ARIDROCK_BRICKS_WALL.get(), DDBlocks.LIMESTONE_BRICKS.get(), DDBlocks.LIMESTONE_BRICKS_STAIRS.get(), DDBlocks.LIMESTONE_BRICKS_SLAB.get(), DDBlocks.LIMESTONE_BRICKS_WALL.get(), DDBlocks.GRIMESTONE_BRICKS.get(), DDBlocks.GRIMESTONE_BRICKS_STAIRS.get(), DDBlocks.GRIMESTONE_BRICKS_SLAB.get(), DDBlocks.GRIMESTONE_BRICKS_WALL.get(), DDBlocks.SILVER_BLOCK.get(), DDBlocks.SILVER_ORE.get(), DDBlocks.RAW_SILVER_BLOCK.get(), DDBlocks.ARIDROCK_COAL_ORE.get(), DDBlocks.LIMESTONE_COAL_ORE.get(), DDBlocks.ARIDROCK_IRON_ORE.get(), DDBlocks.LIMESTONE_IRON_ORE.get(), DDBlocks.ARIDROCK_GOLD_ORE.get(), DDBlocks.LIMESTONE_GOLD_ORE.get(), DDBlocks.ARIDROCK_REDSTONE_ORE.get(), DDBlocks.LIMESTONE_REDSTONE_ORE.get(), DDBlocks.ARIDROCK_LAPIS_ORE.get(), DDBlocks.LIMESTONE_REDSTONE_ORE.get(), DDBlocks.ARIDROCK_DIAMOND_ORE.get(), DDBlocks.LIMESTONE_DIAMOND_ORE.get(), DDBlocks.ARIDROCK_SILVER_ORE.get(), DDBlocks.LIMESTONE_SILVER_ORE.get(), DDBlocks.AMBER_BLOCK.get(), DDBlocks.AMBER.get(), DDBlocks.GEYSER.get(), DDBlocks.PETRIFIED_PLANKS.get(), DDBlocks.PETRIFIED_SLAB.get(), DDBlocks.PETRIFIED_STAIRS.get(), DDBlocks.PETRIFIED_FENCE.get(), DDBlocks.PETRIFIED_FENCE_GATE.get(), DDBlocks.PETRIFIED_TRAPDOOR.get(), DDBlocks.PETRIFIED_SIGN.get(), DDBlocks.PETRIFIED_WALL_SIGN.get(), DDBlocks.PETRIFIED_DOOR.get(), DDBlocks.PETRIFIED_BUTTON.get(), DDBlocks.PETRIFIED_PRESSURE_PLATE.get(), DDBlocks.PETRIFIED_WOOD.get(), DDBlocks.PETRIFIED_LOG.get(), DDBlocks.STRIPPED_PETRIFIED_WOOD.get(), DDBlocks.STRIPPED_PETRIFIED_LOG.get(), DDBlocks.POROUS_PETRIFIED_LOG.get(), DDBlocks.MOSSY_GRIMESTONE.get(), DDBlocks.CRACKED_SHALE_BRICKS.get(), DDBlocks.CRACKED_ARIDROCK_BRICKS.get(), DDBlocks.CRACKED_LIMESTONE_BRICKS.get(), DDBlocks.CRACKED_GRIMESTONE_BRICKS.get(), DDBlocks.CHISELED_SHALE_BRICKS.get(), DDBlocks.CHISELED_ARIDROCK_BRICKS.get(), DDBlocks.CHISELED_LIMESTONE_BRICKS.get(), DDBlocks.CHISELED_GRIMESTONE_BRICKS.get(), DDBlocks.STONE_BRICK_PILLAR.get(), DDBlocks.CRYSTAL_MELON.get(), DDBlocks.ARID_DEEPSLATE.get());
        this.tag(BlockTags.MINEABLE_WITH_SHOVEL).add(DDBlocks.ASH.get(), DDBlocks.ASH_BLOCK.get());

        this.tag(ForgeBlockTags.COAL_ORE).add(DDBlocks.ARIDROCK_COAL_ORE.get()).add(DDBlocks.LIMESTONE_COAL_ORE.get());
        this.tag(ForgeBlockTags.DIAMOND_ORE).add(DDBlocks.ARIDROCK_DIAMOND_ORE.get()).add(DDBlocks.LIMESTONE_DIAMOND_ORE.get());
        this.tag(ForgeBlockTags.GOLD_ORE).add(DDBlocks.ARIDROCK_GOLD_ORE.get()).add(DDBlocks.LIMESTONE_GOLD_ORE.get());
        this.tag(ForgeBlockTags.IRON_ORE).add(DDBlocks.ARIDROCK_IRON_ORE.get()).add(DDBlocks.LIMESTONE_IRON_ORE.get());
        this.tag(ForgeBlockTags.LAPIS_ORE).add(DDBlocks.ARIDROCK_LAPIS_ORE.get()).add(DDBlocks.LIMESTONE_LAPIS_ORE.get());
        this.tag(ForgeBlockTags.REDSTONE_ORE).add(DDBlocks.ARIDROCK_REDSTONE_ORE.get()).add(DDBlocks.LIMESTONE_REDSTONE_ORE.get());
        this.tag(ForgeBlockTags.SILVER_ORE).add(DDBlocks.SILVER_ORE.get());
        this.tag(ForgeBlockTags.SILVER_STORAGE_BLOCKS).add(DDBlocks.SILVER_BLOCK.get());
        this.tag(ForgeBlockTags.ORES).addTags(ForgeBlockTags.SILVER_ORE);
        this.tag(ForgeBlockTags.STORAGE_BLOCKS).addTags(ForgeBlockTags.SILVER_STORAGE_BLOCKS);
    }
}
