package com.naterbobber.darkerdepths.common.conditions;

import com.mojang.serialization.MapCodec;
import com.naterbobber.darkerdepths.common.config.DDConfig;
import net.neoforged.neoforge.common.conditions.ICondition;

public record RopeMinesEnabledCondition() implements ICondition {
    public static final MapCodec<RopeMinesEnabledCondition> CODEC = MapCodec.unit(RopeMinesEnabledCondition::new);

    @Override
    public boolean test(IContext context) {
        return DDConfig.CONFIG.GENERATE_ROPE_MINES.get();
    }

    @Override
    public MapCodec<? extends ICondition> codec() {
        return CODEC;
    }
}
