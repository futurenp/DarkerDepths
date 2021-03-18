package com.naterbobber.darkerdepths.common.world.gen.carver;

import com.mojang.serialization.Codec;
import net.minecraft.world.gen.carver.CaveWorldCarver;
import net.minecraft.world.gen.feature.ProbabilityConfig;

import java.util.Random;

//<>

public class LargeCaveCarver extends CaveWorldCarver {
    public LargeCaveCarver(Codec<ProbabilityConfig> codec, int maxHeight) {
        super(codec, maxHeight);
    }

    @Override
    protected float func_230359_a_(Random random) {
        return super.func_230359_a_(random) * 2.0f;
    }

    @Override
    protected double func_230360_b_() {
        return super.func_230360_b_() * 2.0d;
    }

    @Override
    protected int func_230361_b_(Random random) {
        return  random.nextInt(random.nextInt(80) + 8);
    }
}