package com.naterbobber.darkerdepths.init;

import com.naterbobber.darkerdepths.DarkerDepths;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class DDBlockTags {

    public static final TagKey<Block> GEYSER_BOOSTERS = create("geyser_boosters");
    public static final TagKey<Block> GEYSER_BYPASSES = create("geyser_bypasses");

    private static TagKey<Block> create(String name) {
        return TagKey.create(Registry.BLOCK_REGISTRY, new ResourceLocation(DarkerDepths.MODID, name));
    }

}
