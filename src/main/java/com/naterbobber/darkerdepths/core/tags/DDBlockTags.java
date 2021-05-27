package com.naterbobber.darkerdepths.core.tags;

import com.naterbobber.darkerdepths.core.DDRegistries;
import com.naterbobber.darkerdepths.core.DarkerDepths;
import net.minecraft.block.Block;
import net.minecraft.tags.ITag;

//<>

public class DDBlockTags {
    public static final DDRegistries HELPER = DarkerDepths.REGISTRIES;

    public static final ITag.INamedTag<Block> UNDERGROUND_BASE_BLOCKS = HELPER.registerBlockTag("underground_base_blocks");
}