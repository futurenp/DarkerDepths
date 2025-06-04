package com.naterbobber.darkerdepths.init;

import com.naterbobber.darkerdepths.DarkerDepths;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = DarkerDepths.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DDParticleTypes {

    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, DarkerDepths.MODID);

    public static final RegistryObject<SimpleParticleType> DRIPPING_RESIN    = registerParticle("dripping_resin", false);
    public static final RegistryObject<SimpleParticleType> FALLING_RESIN     = registerParticle("falling_resin", false);
    public static final RegistryObject<SimpleParticleType> LANDING_RESIN     = registerParticle("landing_resin", false);
    public static final RegistryObject<SimpleParticleType> VOID_SOUL_FLAME     = registerParticle("void_soul_flame", false);

    public static RegistryObject<SimpleParticleType> registerParticle(String name, boolean alwaysShow) {
        return PARTICLE_TYPES.register(name, () -> new SimpleParticleType(alwaysShow));
    }

}
