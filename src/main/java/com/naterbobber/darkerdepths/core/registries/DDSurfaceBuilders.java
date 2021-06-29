package com.naterbobber.darkerdepths.core.registries;

import com.naterbobber.darkerdepths.common.world.gen.surfacebuilder.CaveSurfaceBuilder;
import com.naterbobber.darkerdepths.core.DarkerDepths;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

//<>

public class DDSurfaceBuilders {
    public static final DeferredRegister<SurfaceBuilder<?>> SURFACE_BUILDER = DeferredRegister.create(ForgeRegistries.SURFACE_BUILDERS, DarkerDepths.MODID);

    public static final RegistryObject<SurfaceBuilder<SurfaceBuilderConfig>> MOLTEN_CAVERN_SURFACE      = SURFACE_BUILDER.register("molten_cavern_surface", () -> new CaveSurfaceBuilder(DDBlocks.SHALE.get().getDefaultState(), SurfaceBuilderConfig.CODEC));
    public static final RegistryObject<SurfaceBuilder<SurfaceBuilderConfig>> SANDY_CATACOMBS_SURFACE    = SURFACE_BUILDER.register("sandy_catacombs_surface", () -> new CaveSurfaceBuilder(DDBlocks.ARIDROCK.get().getDefaultState(), DDBlocks.LIMESTONE.get().getDefaultState(), SurfaceBuilderConfig.CODEC));
    public static final RegistryObject<SurfaceBuilder<SurfaceBuilderConfig>> GLOWSHROOM_CAVE_SURFACE    = SURFACE_BUILDER.register("glowshroom_cave_surface", () -> new CaveSurfaceBuilder(DDBlocks.GRIMESTONE.get().getDefaultState(), SurfaceBuilderConfig.CODEC));

}