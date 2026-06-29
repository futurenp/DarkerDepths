package com.naterbobber.darkerdepths.worldgen.surfacerules;

import com.naterbobber.darkerdepths.init.DDBlocks;
import com.naterbobber.darkerdepths.util.DDResourceKeys;
import com.naterbobber.darkerdepths.worldgen.surfacerules.conditions.AxisNoiseConditionSource;
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
    private static final SurfaceRules.RuleSource MAGMA =
            SurfaceRules.state(Blocks.MAGMA_BLOCK.defaultBlockState());
    private static final SurfaceRules.RuleSource GRIMESTONE =
            SurfaceRules.state(DDBlocks.GRIMESTONE.get().defaultBlockState());
    private static final SurfaceRules.RuleSource DUSKROCK =
            SurfaceRules.state(DDBlocks.DUSKROCK.get().defaultBlockState());
    private static final SurfaceRules.RuleSource MOSSY_GRIMESTONE =
            SurfaceRules.state(DDBlocks.MOSSY_GRIMESTONE.get().defaultBlockState());
    private static final SurfaceRules.RuleSource DARKSLATE =
            SurfaceRules.state(DDBlocks.DARKSLATE.get().defaultBlockState());
    private static final SurfaceRules.RuleSource TUFF =
            SurfaceRules.state(Blocks.TUFF.defaultBlockState());

    private static final SurfaceRules.RuleSource DUSKROCK_NOISE = SurfaceRules.ifTrue(
            new AxisNoiseConditionSource(DDResourceKeys.Noises.DUSKROCK, -0.15, 0.15, true, true, true),
            SurfaceRules.sequence(
                    SurfaceRules.ifTrue(
                            new AxisNoiseConditionSource(DDResourceKeys.Noises.DUSKROCK, -0.09, 0.09, true, true, true),
                            DUSKROCK
                    ),
                    PACKED_MUD
            )
    );

    private static final SurfaceRules.RuleSource MAGMA_NOISE = SurfaceRules.ifTrue(
            new AxisNoiseConditionSource(DDResourceKeys.Noises.MAGMA, -0.0075, 0.0075, true, true, true),
            MAGMA
    );

    private static final SurfaceRules.RuleSource TUFF_NOISE = SurfaceRules.ifTrue(
            new AxisNoiseConditionSource(DDResourceKeys.Noises.TUFF, -0.075, 0.075, true, true, true),
            TUFF
    );

    private static final SurfaceRules.RuleSource SANDY_FILL = SurfaceRules.ifTrue(
            SurfaceRules.isBiome(DDResourceKeys.Biomes.SANDY_CATACOMBS),
            SurfaceRules.sequence(
                    SurfaceRules.ifTrue(
                            SurfaceRules.stoneDepthCheck(0, false, 0, CaveSurface.FLOOR),
                            SurfaceRules.sequence(
                                    DUSKROCK_NOISE,
                                    ARIDROCK
                            )
                    ),
                    SurfaceRules.ifTrue(
                            SurfaceRules.stoneDepthCheck(3, false, 0, CaveSurface.FLOOR),
                            SurfaceRules.sequence(
                                    DUSKROCK_NOISE,
                                    PACKED_MUD
                            )
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
            SurfaceRules.sequence(
                    MAGMA_NOISE,
                    TUFF_NOISE,
                    DARKSLATE
            )
    );

    private static final SurfaceRules.RuleSource GRIMESTONE_TUFF_NOISE = SurfaceRules.ifTrue(
            new AxisNoiseConditionSource(DDResourceKeys.Noises.GRIMESTONE_TUFF, -0.13, 0.13, true, true, true),
            SurfaceRules.sequence(
                    SurfaceRules.ifTrue(
                            new AxisNoiseConditionSource(DDResourceKeys.Noises.GRIMESTONE_TUFF, -0.07, 0.07, true, true, true),
                            TUFF
                    ),
                    GRIMESTONE
            )
    );

    private static final SurfaceRules.RuleSource GRIMESTONE_MOSSY_NOISE = SurfaceRules.ifTrue(
            new AxisNoiseConditionSource(DDResourceKeys.Noises.GRIMESTONE_MOSSY, -0.13, 0.13, true, true, true),
            GRIMESTONE
    );

    public static final SurfaceRules.RuleSource GLOWSHROOM_FILL = SurfaceRules.ifTrue(
            SurfaceRules.isBiome(DDResourceKeys.Biomes.GLOWSHROOM_FOREST),
            SurfaceRules.sequence(
                    SurfaceRules.ifTrue(
                            SurfaceRules.stoneDepthCheck(0, false, 0, CaveSurface.FLOOR),
                            SurfaceRules.sequence(
                                    //grimestone not spawning when in water caves
                                    SurfaceRules.ifTrue(SurfaceRules.waterBlockCheck(1, 0),
                                            SurfaceRules.sequence(
                                                    GRIMESTONE_TUFF_NOISE,
                                                    GRIMESTONE_MOSSY_NOISE,
                                                    MOSSY_GRIMESTONE)
                                    ),
                                    GRIMESTONE
                                    )
                    ),
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

    public static SurfaceRules.RuleSource makeRules() {
        return SurfaceRules.sequence(MOLTEN_CAVERN_RULES, SANDY_CATACOMBS_RULES, GLOWSHROOM_RULES, BEDROCK_BOTTOM);
    }
}
