package com.naterbobber.darkerdepths.init;

import com.naterbobber.darkerdepths.DarkerDepths;
import com.naterbobber.darkerdepths.worldgen.carvers.BlockCarver;
import com.naterbobber.darkerdepths.worldgen.carvers.BlockCarverConfiguration;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.carver.WorldCarver;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class DDWorldCarvers {
    public static final DeferredRegister<WorldCarver<?>> WORLD_CARVERS = DeferredRegister.create(Registries.CARVER, DarkerDepths.MOD_ID);

    public static final DeferredHolder<WorldCarver<?>, BlockCarver> BLOCK_CARVER = WORLD_CARVERS.register("block_carver", () -> new BlockCarver(BlockCarverConfiguration.CODEC));

}
