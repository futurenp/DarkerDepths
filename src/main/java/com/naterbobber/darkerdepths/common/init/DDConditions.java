package com.naterbobber.darkerdepths.common.init;

import com.mojang.serialization.MapCodec;
import com.naterbobber.darkerdepths.DarkerDepths;
import com.naterbobber.darkerdepths.common.conditions.RopeMinesEnabledCondition;
import net.neoforged.neoforge.common.conditions.ICondition;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class DDConditions {
    public static final DeferredRegister<MapCodec<? extends ICondition>> CONDITIONS =
            DeferredRegister.create(NeoForgeRegistries.Keys.CONDITION_CODECS, DarkerDepths.MOD_ID);

    public static final DeferredHolder<MapCodec<? extends ICondition>, MapCodec<RopeMinesEnabledCondition>> ROPE_MINES_ENABLED =
            CONDITIONS.register("rope_mines_enabled", () -> RopeMinesEnabledCondition.CODEC);
}
