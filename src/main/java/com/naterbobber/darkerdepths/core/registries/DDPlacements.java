package com.naterbobber.darkerdepths.core.registries;

import com.naterbobber.darkerdepths.common.world.gen.placement.CaveSurfaceDecoratorConfig;
import com.naterbobber.darkerdepths.common.world.gen.placement.CaveSurfacePlacement;
import com.naterbobber.darkerdepths.core.DDRegistries;
import com.naterbobber.darkerdepths.core.DarkerDepths;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;

//<>

@Mod.EventBusSubscriber(modid = DarkerDepths.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DDPlacements {
    public static final DDRegistries HELPER = DarkerDepths.REGISTRIES;

    public static final RegistryObject<Placement<CaveSurfaceDecoratorConfig>> CAVE_SURFACE = HELPER.registerPlacement("cave_surface", () -> new CaveSurfacePlacement(CaveSurfaceDecoratorConfig.CODEC));
}