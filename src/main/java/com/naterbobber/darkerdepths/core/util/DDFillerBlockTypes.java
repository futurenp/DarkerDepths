package com.naterbobber.darkerdepths.core.util;

import com.naterbobber.darkerdepths.core.registries.DDBlocks;
import net.minecraft.world.gen.feature.template.BlockMatchRuleTest;
import net.minecraft.world.gen.feature.template.RuleTest;

//<>

public class DDFillerBlockTypes {
    public static final RuleTest ARIDROCK             = new BlockMatchRuleTest(DDBlocks.ARIDROCK.get());
    public static final RuleTest LIMESTONE            = new BlockMatchRuleTest(DDBlocks.LIMESTONE.get());
}