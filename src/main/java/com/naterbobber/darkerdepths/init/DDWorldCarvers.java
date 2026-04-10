package com.naterbobber.darkerdepths.init;

import com.naterbobber.darkerdepths.DarkerDepths;
import com.naterbobber.darkerdepths.worldgen.carvers.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.carver.WorldCarver;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class DDWorldCarvers {
    public static final DeferredRegister<WorldCarver<?>> WORLD_CARVERS = DeferredRegister.create(Registries.CARVER, DarkerDepths.MOD_ID);

    public static final RegistryObject<BlockCarver> BLOCK_CARVER = WORLD_CARVERS.register("block_carver", () -> new BlockCarver(BlockCarverConfiguration.CODEC));
    public static final RegistryObject<ExposedLineCarver> EXPOSED_LINE_CARVER = WORLD_CARVERS.register("exposed_line_carver", () -> new ExposedLineCarver(BlockCarverConfiguration.CODEC));
    public static final RegistryObject<SurfaceCrawlerCarver> SURFACE_CRAWLER_CARVER = WORLD_CARVERS.register("surface_crawler_carver", () -> new SurfaceCrawlerCarver(BlockCarverConfiguration.CODEC));
    public static final RegistryObject<SurfaceLineCarver> SURFACE_LINE_CARVER = WORLD_CARVERS.register("surface_line_carver", () -> new SurfaceLineCarver(BlockCarverConfiguration.CODEC));
    public static final RegistryObject<CurtainCarver> CURTAIN_CARVER = WORLD_CARVERS.register("curtain_carver", () -> new CurtainCarver(BlockCarverConfiguration.CODEC));

}
