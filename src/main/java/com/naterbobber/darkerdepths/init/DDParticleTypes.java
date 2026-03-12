package com.naterbobber.darkerdepths.init;

import com.naterbobber.darkerdepths.DarkerDepths;
import com.naterbobber.darkerdepths.client.particle.*;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.Registries;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.HashMap;

public class DDParticleTypes {

    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES = DeferredRegister.create(Registries.PARTICLE_TYPE, DarkerDepths.MOD_ID);
    public static final HashMap<DeferredHolder<ParticleType<?>, SimpleParticleType>, ParticleEngine.SpriteParticleRegistration<SimpleParticleType>> particleFactoryMap = new HashMap<>();

    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> DRIPPING_AMBER
            = registerParticle("dripping_amber", DrippingParticle.DrippingAmberFactory::new, false);
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> FALLING_AMBER
            = registerParticle("falling_amber", DrippingParticle.FallingAmberFactory::new, false);
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> LANDING_AMBER
            = registerParticle("landing_amber", DrippingParticle.LandingAmberFactory::new, false);
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> VOID_SOUL
            = registerParticle("void_soul", VoidSoulParticle.VoidSoulFactory::new, false);
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> VOID_SOUL_FLAME
            = registerParticle("void_soul_flame", VoidSoulFlameParticle.VoidSoulFlameFactory::new, false);
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> VOID_SOUL_FLAME_SMOKE
            = registerParticle("void_soul_flame_smoke", VoidSoulFlameSmokeParticle.VoidSoulFlameSmokeFactory::new, false);
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> GEYSER_BURST_SMOKE
            = registerParticle("geyser_burst_smoke", GeyserBurstSmokeParticle.GeyserBurstSmokeParticleFactory::new, false);
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> GEYSER_BURST_SMOKE_BOOSTED
            = registerParticle("geyser_burst_smoke_boosted", GeyserBurstSmokeParticle.GeyserBurstSmokeBoostedParticleFactory::new, false);
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> RED_ASH
            = registerParticle("red_ash", (spriteSet) -> new ColoredAshParticle.Provider(spriteSet, 1F, 0F, 0F), false);



    public static DeferredHolder<ParticleType<?>, SimpleParticleType> registerParticle(String name, ParticleEngine.SpriteParticleRegistration<SimpleParticleType> particleMetaFactory, boolean alwaysShow) {
        var holder = PARTICLE_TYPES.register(name, () -> new SimpleParticleType(alwaysShow));
        particleFactoryMap.put(holder, particleMetaFactory);
        return holder;
    }

    public static void bootstrap(ParticleEngine engine) {
        particleFactoryMap.forEach((holder, particleMetaFactory) -> {
            engine.register(holder.get(), particleMetaFactory);
        });
    }

}
