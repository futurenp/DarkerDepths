package com.naterbobber.darkerdepths.init;

import com.naterbobber.darkerdepths.DarkerDepths;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class DDBlockTags {

    public static final TagKey<Block> GRIMESTONE_REPLACEMENT = registerBlockTag("grimestone_replacement");
    public static final TagKey<Block> SANDY_GROUND_REPLACEABLE = registerBlockTag("sandy_ground_replaceable");

    public static TagKey<Block> registerBlockTag(String key) {
        return TagKey.create(Registry.BLOCK_REGISTRY, new ResourceLocation(DarkerDepths.MODID, key));
    }

}
