package com.naterbobber.darkerdepths.init;

import com.naterbobber.darkerdepths.DarkerDepths;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.Tag;
import net.minecraft.world.level.block.Block;

public class DDBlockTags {

    public static final Tag.Named<Block> GRIMESTONE_REPLACEMENT = registerBlockTag("grimestone_replacement");
    public static final Tag.Named<Block> SANDY_GROUND_REPLACEABLE = registerBlockTag("sandy_ground_replaceable");

    public static Tag.Named<Block> registerBlockTag(String key) {
        return BlockTags.bind(new ResourceLocation(DarkerDepths.MODID, key).toString());
    }

}
