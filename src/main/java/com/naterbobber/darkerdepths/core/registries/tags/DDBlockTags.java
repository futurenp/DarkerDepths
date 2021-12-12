package com.naterbobber.darkerdepths.core.registries.tags;

import com.naterbobber.darkerdepths.core.DarkerDepths;
import com.naterbobber.darkerdepths.core.api.Registries;
import net.minecraft.block.Block;
import net.minecraft.tags.ITag;

//<>

public class DDBlockTags {
    public static final Registries HELPER = DarkerDepths.REGISTRIES;

    public static final ITag.INamedTag<Block> GRIMESTONE_REPLACEMENT = HELPER.registerBlockTag("grimestone_replacement");
    public static final ITag.INamedTag<Block> SANDY_GROUND_REPLACEABLE = HELPER.registerBlockTag("sandy_ground_replaceable");
}