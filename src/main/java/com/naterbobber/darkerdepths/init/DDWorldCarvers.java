package com.naterbobber.darkerdepths.init;

import com.naterbobber.darkerdepths.DarkerDepths;
import com.naterbobber.darkerdepths.worldgen.carvers.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.carver.WorldCarver;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class DDWorldCarvers {
    public static final DeferredRegister<WorldCarver<?>> WORLD_CARVERS = DeferredRegister.create(Registries.CARVER, DarkerDepths.MOD_ID);

    public static final DeferredHolder<WorldCarver<?>, BlockCarver> BLOCK_CARVER = WORLD_CARVERS.register("block_carver", () -> new BlockCarver(BlockCarverConfiguration.CODEC));
    public static final DeferredHolder<WorldCarver<?>, ExposedLineCarver> EXPOSED_LINE_CARVER = WORLD_CARVERS.register("exposed_line_carver", () -> new ExposedLineCarver(BlockCarverConfiguration.CODEC));
    public static final DeferredHolder<WorldCarver<?>, SurfaceCrawlerCarver> SURFACE_CRAWLER_CARVER = WORLD_CARVERS.register("surface_crawler_carver", () -> new SurfaceCrawlerCarver(BlockCarverConfiguration.CODEC));
    public static final DeferredHolder<WorldCarver<?>, SurfaceLineCarver> SURFACE_LINE_CARVER = WORLD_CARVERS.register("surface_line_carver", () -> new SurfaceLineCarver(BlockCarverConfiguration.CODEC));
    public static final DeferredHolder<WorldCarver<?>, CurtainCarver> CURTAIN_CARVER = WORLD_CARVERS.register("curtain_carver", () -> new CurtainCarver(BlockCarverConfiguration.CODEC));

}
