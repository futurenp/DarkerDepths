package com.naterbobber.darkerdepths.core.registries;

import com.blackgear.bgcore.core.CoreRegistries;
import com.naterbobber.darkerdepths.common.world.gen.placement.CaveDecoratorConfig;
import com.naterbobber.darkerdepths.common.world.gen.placement.CaveSurfacePlacement;
import com.naterbobber.darkerdepths.core.DarkerDepths;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;

//<>

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class DDPlacements {
    public static final CoreRegistries HELPER = DarkerDepths.REGISTRY_HELPER;

    public static final RegistryObject<Placement<CaveDecoratorConfig>> CAVE_SURFACE = HELPER.registerPlacement("cave_surface", () -> new CaveSurfacePlacement(CaveDecoratorConfig.CODEC));
}