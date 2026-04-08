package com.naterbobber.darkerdepths.worldgen.surfacerules;

import com.naterbobber.darkerdepths.init.DDBlocks;
import com.naterbobber.darkerdepths.util.DDResourceKeys;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.placement.CaveSurface;

public class DDSurfaceRules {

    private static final SurfaceRules.RuleSource ARID_DEEPSLATE_FILL =
            SurfaceRules.state(DDBlocks.ARID_DEEPSLATE.get().defaultBlockState());
    private static final SurfaceRules.RuleSource ARIDROCK =
            SurfaceRules.state(DDBlocks.ARIDROCK.get().defaultBlockState());
    private static final SurfaceRules.RuleSource PACKED_MUD =
            SurfaceRules.state(Blocks.PACKED_MUD.defaultBlockState());
    private static final SurfaceRules.RuleSource GRIMESTONE =
            SurfaceRules.state(DDBlocks.GRIMESTONE.get().defaultBlockState());
    private static final SurfaceRules.RuleSource MOSSY_GRIMESTONE =
            SurfaceRules.state(DDBlocks.MOSSY_GRIMESTONE.get().defaultBlockState());
    private static final SurfaceRules.RuleSource TUFF =
            SurfaceRules.state(Blocks.TUFF.defaultBlockState());

    private static final SurfaceRules.RuleSource SANDY_FILL = SurfaceRules.ifTrue(
            SurfaceRules.isBiome(DDResourceKeys.Biomes.SANDY_CATACOMBS),
            SurfaceRules.sequence(
                    SurfaceRules.ifTrue(
                            SurfaceRules.stoneDepthCheck(0, false, 0, CaveSurface.FLOOR),
                            ARIDROCK
                    ),
                    SurfaceRules.ifTrue(
                            SurfaceRules.stoneDepthCheck(3, false, 0, CaveSurface.FLOOR),
                            PACKED_MUD
                    ),

                    SurfaceRules.ifTrue(
                            SurfaceRules.not(SurfaceRules.yBlockCheck(VerticalAnchor.absolute(0), 0)),
                            SurfaceRules.ifTrue(
                                    SurfaceRules.stoneDepthCheck(4, false, 0, CaveSurface.FLOOR),
                                    ARID_DEEPSLATE_FILL
                            )
                    )
            )
    );

    public static final SurfaceRules.RuleSource MOLTEN_FILL = SurfaceRules.ifTrue(
            SurfaceRules.isBiome(DDResourceKeys.Biomes.MOLTEN_CAVERN),
            SurfaceRules.state(DDBlocks.DARKSLATE.get().defaultBlockState())
    );

    public static final SurfaceRules.RuleSource CHALK_FILL = SurfaceRules.ifTrue(
            SurfaceRules.isBiome(DDResourceKeys.Biomes.CHALK_CAVES),
            SurfaceRules.state(DDBlocks.BLUE_CHALK.get().defaultBlockState())
    );

    public static final SurfaceRules.RuleSource GLOWSHROOM_FILL = SurfaceRules.ifTrue(
            SurfaceRules.isBiome(DDResourceKeys.Biomes.GLOWSHROOM_FOREST),
            SurfaceRules.sequence(
                    SurfaceRules.ifTrue(
                            SurfaceRules.stoneDepthCheck(1, false, 0, CaveSurface.FLOOR),
                            GRIMESTONE
                    ),
                    SurfaceRules.ifTrue(
                            SurfaceRules.stoneDepthCheck(3, false, 0, CaveSurface.FLOOR),
                            TUFF
                    )
            )
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

    public static final SurfaceRules.RuleSource GLOWSHROOM_RULES = SurfaceRules.sequence(
            BEDROCK_BOTTOM,
            GLOWSHROOM_FILL
    );

    public static final SurfaceRules.RuleSource CHALK_RULES = SurfaceRules.sequence(
            BEDROCK_BOTTOM,
            CHALK_FILL
    );

    public static SurfaceRules.RuleSource makeRules() {
        return SurfaceRules.sequence(MOLTEN_CAVERN_RULES, SANDY_CATACOMBS_RULES, GLOWSHROOM_RULES, CHALK_RULES, BEDROCK_BOTTOM);
    }
}
