package com.naterbobber.darkerdepths.core.registries;

import com.naterbobber.darkerdepths.core.api.Registries;
import com.naterbobber.darkerdepths.core.DarkerDepths;
import net.minecraft.particles.BasicParticleType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;

//<>

@Mod.EventBusSubscriber(modid = DarkerDepths.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DDParticleTypes {
    public static final Registries HELPER = DarkerDepths.REGISTRIES;

    public static final RegistryObject<BasicParticleType> DRIPPING_RESIN    = HELPER.registerParticle("dripping_resin", false);
    public static final RegistryObject<BasicParticleType> FALLING_RESIN     = HELPER.registerParticle("falling_resin", false);
    public static final RegistryObject<BasicParticleType> LANDING_RESIN     = HELPER.registerParticle("landing_resin", false);
}