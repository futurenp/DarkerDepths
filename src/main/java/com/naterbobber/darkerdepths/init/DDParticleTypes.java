package com.naterbobber.darkerdepths.init;

import com.naterbobber.darkerdepths.DarkerDepths;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.Registries;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;

public class DDParticleTypes {

    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES = DeferredRegister.create(Registries.PARTICLE_TYPE, DarkerDepths.MOD_ID);

    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> DRIPPING_AMBER       = registerParticle("dripping_amber", false);
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> FALLING_AMBER        = registerParticle("falling_amber", false);
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> LANDING_AMBER        = registerParticle("landing_amber", false);
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> VOID_SOUL            = registerParticle("void_soul", false);
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> VOID_SOUL_FLAME      = registerParticle("void_soul_flame", false);
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> VOID_SOUL_FLAME_SMOKE= registerParticle("void_soul_flame_smoke", false);
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> GEYSER_BURST_SMOKE   = registerParticle("geyser_burst_smoke", false);
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> GEYSER_BURST_SMOKE_BOOSTED  = registerParticle("geyser_burst_smoke_boosted", false);
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> RED_ASH  = registerParticle("red_ash", false);


    public static DeferredHolder<ParticleType<?>, SimpleParticleType> registerParticle(String name, boolean alwaysShow) {
        return PARTICLE_TYPES.register(name, () -> new SimpleParticleType(alwaysShow));
    }

}
