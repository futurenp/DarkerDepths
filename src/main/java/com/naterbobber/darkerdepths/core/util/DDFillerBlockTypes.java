package com.naterbobber.darkerdepths.core.util;

import com.naterbobber.darkerdepths.core.registries.DDBlocks;
import net.minecraft.block.pattern.BlockMatcher;
import net.minecraft.world.gen.feature.OreFeatureConfig;

//<>

public class DDFillerBlockTypes {
    public static final OreFeatureConfig.FillerBlockType ARIDROCK   = OreFeatureConfig.FillerBlockType.create("aridrock", "aridrock", new BlockMatcher(DDBlocks.ARIDROCK.get()));
    public static final OreFeatureConfig.FillerBlockType LIMESTONE  = OreFeatureConfig.FillerBlockType.create("limestone", "limestone", new BlockMatcher(DDBlocks.LIMESTONE.get()));
}