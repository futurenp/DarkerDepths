package com.naterbobber.darkerdepths.worldgen.surfacerules;

import com.naterbobber.darkerdepths.init.DDBlocks;
import com.naterbobber.darkerdepths.util.DDResourceKeys;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.minecraft.world.level.levelgen.VerticalAnchor;

import java.util.List;

public class DDSurfaceRules {

    private static final List<BlockState> SANDY_FILL_LIST = List.of(
            DDBlocks.ARIDROCK.get().defaultBlockState(),
            Blocks.PACKED_MUD.defaultBlockState(),
            DDBlocks.ARIDROCK.get().defaultBlockState(),
            DDBlocks.ARIDROCK.get().defaultBlockState(),
            Blocks.PACKED_MUD.defaultBlockState(),
            Blocks.PACKED_MUD.defaultBlockState(),
            DDBlocks.DUSKROCK.get().defaultBlockState(),
            DDBlocks.DUSKROCK.get().defaultBlockState(),
            Blocks.PACKED_MUD.defaultBlockState(),
            Blocks.PACKED_MUD.defaultBlockState(),
            DDBlocks.ARIDROCK.get().defaultBlockState(),
            Blocks.PACKED_MUD.defaultBlockState(),
            Blocks.PACKED_MUD.defaultBlockState(),
            DDBlocks.DUSKROCK.get().defaultBlockState(),
            Blocks.PACKED_MUD.defaultBlockState(),
            Blocks.PACKED_MUD.defaultBlockState(),
            DDBlocks.ARIDROCK.get().defaultBlockState(),
            DDBlocks.ARIDROCK.get().defaultBlockState()
    );

    private static final SurfaceRules.RuleSource SANDY_PATTERN_FILL = new RepeatingYPatternRule(SANDY_FILL_LIST);

    private static final SurfaceRules.RuleSource SANDY_FILL = SurfaceRules.ifTrue(
            SurfaceRules.isBiome(DDResourceKeys.Biomes.SANDY_CATACOMBS),
            SANDY_PATTERN_FILL
    );

    private static final SurfaceRules.RuleSource MOLTEN_FILL = SurfaceRules.ifTrue(
            SurfaceRules.isBiome(DDResourceKeys.Biomes.MOLTEN_CAVERN),
            SurfaceRules.state(DDBlocks.DARKSLATE.get().defaultBlockState())
    );

    private static final SurfaceRules.RuleSource BEDROCK_BOTTOM = SurfaceRules.ifTrue(
            SurfaceRules.verticalGradient("bedrock_floor", VerticalAnchor.bottom(), VerticalAnchor.aboveBottom(5)),
            SurfaceRules.state(Blocks.BEDROCK.defaultBlockState())
    );

    public static final SurfaceRules.RuleSource SANDY_CATACOMBS_RULES = SurfaceRules.sequence(
            BEDROCK_BOTTOM,
            SANDY_FILL
    );

    public static final SurfaceRules.RuleSource MOLTEN_CAVERN_RULES = SurfaceRules.sequence(
            BEDROCK_BOTTOM,
            MOLTEN_FILL
    );

    public static SurfaceRules.RuleSource makeRules() {
        return SurfaceRules.sequence(MOLTEN_CAVERN_RULES, SANDY_CATACOMBS_RULES, BEDROCK_BOTTOM);
    }
}
