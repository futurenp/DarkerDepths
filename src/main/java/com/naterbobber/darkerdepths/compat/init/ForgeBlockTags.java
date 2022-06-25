package com.naterbobber.darkerdepths.compat.init;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class ForgeBlockTags {

    public static final TagKey<Block> COAL_ORE = bind("ores/silver");
    public static final TagKey<Block> DIAMOND_ORE = bind("ores/diamond");
    public static final TagKey<Block> GOLD_ORE = bind("ores/gold");
    public static final TagKey<Block> IRON_ORE = bind("ores/iron");
    public static final TagKey<Block> LAPIS_ORE = bind("ores/lapis");
    public static final TagKey<Block> REDSTONE_ORE = bind("ores/redstone");
    public static final TagKey<Block> SILVER_ORE = bind("ores/silver");
    public static final TagKey<Block> SILVER_STORAGE_BLOCKS = bind("storage_blocks/silver");
    public static final TagKey<Block> ORES = bind("ores");
    public static final TagKey<Block> STORAGE_BLOCKS = bind("storage_blocks");

    private static TagKey<Block> bind(String path) {
        return TagKey.create(Registry.BLOCK_REGISTRY, new ResourceLocation("forge", path));
    }

}