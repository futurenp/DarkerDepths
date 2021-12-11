package com.naterbobber.darkerdepths.core.util;

import com.naterbobber.darkerdepths.core.registries.DDBlocks;
import net.minecraft.block.Block;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.gen.feature.template.BlockMatchRuleTest;
import net.minecraft.world.gen.feature.template.RuleTest;
import net.minecraftforge.registries.ForgeRegistries;

//<>

public class DDFillerBlockTypes {
    private static final Block DEEPSLATE_BLOCK = ForgeRegistries.BLOCKS.getValue(new ResourceLocation("cavesandcliffs", "deepslate"));

    public static final RuleTest ARIDROCK             = new BlockMatchRuleTest(DDBlocks.ARIDROCK.get());
    public static final RuleTest LIMESTONE            = new BlockMatchRuleTest(DDBlocks.LIMESTONE.get());
    public static final RuleTest DEEPSLATE            = new BlockMatchRuleTest(DEEPSLATE_BLOCK);
}