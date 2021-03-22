package com.naterbobber.darkerdepths.common.world.gen.carver;

import com.mojang.serialization.Codec;
import net.minecraft.world.gen.feature.ProbabilityConfig;

import java.util.Random;

//<>

public class BigCaveCarver extends BasicCarver {
    public BigCaveCarver(Codec<ProbabilityConfig> codec) {
        super(codec);
    }

    @Override
    protected float func_230359_a_(Random rand) {
        return super.func_230359_a_(rand) * 2.0f;
    }

    @Override
    protected double func_230360_b_() {
        return super.func_230360_b_() * 2.0d;
    }

    @Override
    protected int func_230361_b_(Random rand) {
        return super.func_230361_b_(rand) * rand.nextInt(rand.nextInt(80) + 8);
    }
}