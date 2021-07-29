package com.naterbobber.darkerdepths.core.registries;

import com.naterbobber.darkerdepths.common.world.gen.surfacebuilder.CaveSurfaceBuilder;
import com.naterbobber.darkerdepths.core.CoreRegistries;
import com.naterbobber.darkerdepths.core.DarkerDepths;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;

//<>

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class DDSurfaceBuilders {
    public static final CoreRegistries HELPER = DarkerDepths.REGISTRIES;

    public static final RegistryObject<SurfaceBuilder<SurfaceBuilderConfig>> MOLTEN_CAVERN_SURFACE      = HELPER.registerSurfaceBuilder("molten_cavern_surface", () -> new CaveSurfaceBuilder(DDBlocks.SHALE.get().getDefaultState(), SurfaceBuilderConfig.field_237203_a_));
    public static final RegistryObject<SurfaceBuilder<SurfaceBuilderConfig>> SANDY_CATACOMBS_SURFACE    = HELPER.registerSurfaceBuilder("sandy_catacombs_surface", () -> new CaveSurfaceBuilder(DDBlocks.ARIDROCK.get().getDefaultState(), DDBlocks.LIMESTONE.get().getDefaultState(), SurfaceBuilderConfig.field_237203_a_));
    public static final RegistryObject<SurfaceBuilder<SurfaceBuilderConfig>> GLOWSHROOM_CAVE_SURFACE    = HELPER.registerSurfaceBuilder("glowshroom_cave_surface", () -> new CaveSurfaceBuilder(DDBlocks.GRIMESTONE.get().getDefaultState(), SurfaceBuilderConfig.field_237203_a_));

}