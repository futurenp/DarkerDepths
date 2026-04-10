package com.naterbobber.darkerdepths.worldgen.surfacerules;

import net.minecraft.util.KeyDispatchDataCodec;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.SurfaceRules;
import java.util.List;

public record RepeatingYPatternRule(List<BlockState> pattern) implements SurfaceRules.RuleSource {

    public static final KeyDispatchDataCodec<RepeatingYPatternRule> CODEC = KeyDispatchDataCodec.of(
            BlockState.CODEC.listOf().xmap(RepeatingYPatternRule::new, RepeatingYPatternRule::pattern).fieldOf("pattern")
    );

    @Override
    public KeyDispatchDataCodec<? extends SurfaceRules.RuleSource> codec() {
        return CODEC;
    }

    @Override
    public SurfaceRules.SurfaceRule apply(SurfaceRules.Context context) {
        BlockState[] patternArray = pattern.toArray(new BlockState[0]);
        int length = patternArray.length;

        return (x, y, z) -> {
            int index = Math.floorMod(y, length);
            return patternArray[index];
        };
    }
}