package com.naterbobber.darkerdepths.core.util;

import com.naterbobber.darkerdepths.core.registries.DDBlocks;
import com.naterbobber.darkerdepths.core.tags.DDBlockTags;
import net.minecraft.block.pattern.BlockMatcher;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.feature.template.BlockMatchRuleTest;
import net.minecraft.world.gen.feature.template.RuleTest;
import net.minecraft.world.gen.feature.template.TagMatchRuleTest;

//<>

public class DDFillerBlockTypes {
    public static final RuleTest ARIDROCK             = new BlockMatchRuleTest(DDBlocks.ARIDROCK.get());
    public static final RuleTest LIMESTONE            = new BlockMatchRuleTest(DDBlocks.LIMESTONE.get());
    public static final RuleTest UNDERGROUND_BASE_BLOCKS = new TagMatchRuleTest(DDBlockTags.UNDERGROUND_BASE_BLOCKS);
}