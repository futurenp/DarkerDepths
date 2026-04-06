package com.naterbobber.darkerdepths.init;

import com.naterbobber.darkerdepths.DarkerDepths;
import com.naterbobber.darkerdepths.client.particle.*;
import com.naterbobber.darkerdepths.client.particle.geyser.GeyserBurstFlameParticle;
import com.naterbobber.darkerdepths.client.particle.geyser.GeyserBurstMistParticle;
import com.naterbobber.darkerdepths.client.particle.geyser.GeyserBurstSmokeParticle;
import com.naterbobber.darkerdepths.client.particle.geyser.GeyserPassiveSmokeParticle;
import com.naterbobber.darkerdepths.client.particle.void_soul.VoidSoulDeathParticle;
import com.naterbobber.darkerdepths.client.particle.void_soul.VoidSoulFlameParticle;
import com.naterbobber.darkerdepths.client.particle.void_soul.VoidSoulFlameSmokeParticle;
import com.naterbobber.darkerdepths.client.particle.void_soul.VoidSoulParticle;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.Registries;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.HashMap;

public class DDParticleTypes {

    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES = DeferredRegister.create(Registries.PARTICLE_TYPE, DarkerDepths.MOD_ID);
    public static final HashMap<DeferredHolder<ParticleType<?>, SimpleParticleType>, ParticleEngine.SpriteParticleRegistration<SimpleParticleType>> particleFactoryMap = new HashMap<>();

    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> DRIPPING_AMBER
            = registerParticle("dripping_amber", DrippingParticle.DrippingAmberProvider::new, false);
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> FALLING_AMBER
            = registerParticle("falling_amber", DrippingParticle.FallingAmberProvider::new, false);
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> LANDING_AMBER
            = registerParticle("landing_amber", DrippingParticle.LandingAmberProvider::new, false);
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> VOID_SOUL
            = registerParticle("void_soul", VoidSoulParticle.Provider::new, false);
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> VOID_SOUL_FLAME
            = registerParticle("void_soul_flame", VoidSoulFlameParticle.Provider::new, false);
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> VOID_SOUL_FLAME_SMOKE
            = registerParticle("void_soul_flame_smoke", VoidSoulFlameSmokeParticle.Provider::new, false);
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> GEYSER_PASSIVE_SMOKE
            = registerParticle("geyser_passive_smoke", GeyserPassiveSmokeParticle.Provider::new, false);
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> GEYSER_BURST_SMOKE
            = registerParticle("geyser_burst_smoke", GeyserBurstSmokeParticle.Provider::new, false);
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> GEYSER_BURST_SMOKE_LAVA
            = registerParticle("geyser_burst_smoke_lava", GeyserBurstSmokeParticle.LavaProvider::new, false);
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> GEYSER_BURST_MIST
            = registerParticle("geyser_burst_mist", GeyserBurstMistParticle.Provider::new, false);
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> GEYSER_BURST_FLAME
            = registerParticle("geyser_burst_flame", GeyserBurstFlameParticle.Provider::new, false);
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> SMALL_GEYSER_BURST_FLAME
            = registerParticle("small_geyser_burst_flame", GeyserBurstFlameParticle.SmallProvider::new, false);
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> GEYSER_BURST_FLAME_BOOSTED
            = registerParticle("geyser_burst_flame_boosted", GeyserBurstFlameParticle.BoostedProvider::new, false);
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> MOLTEN_ASH
            = registerParticle("molten_ash", (spriteSet) -> new ColoredAshParticle.Provider(spriteSet, 1F, 0.4F, 0.25F, ColoredAshParticle.BrightnessBehavior.FADE), false);
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> GLOW_GLIMMER
            = registerParticle("glow_glimmer", (spriteSet) -> new ColoredAshParticle.Provider(spriteSet, 0.25F, 1F, 0.55F, ColoredAshParticle.BrightnessBehavior.FULL_BRIGHT), false);
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> SCORCHER_SEARCHLIGHT
            = registerParticle("scorcher_searchlight", ScorcherSearchlightParticle.Provider::new, false);
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> VOID_SOUL_DEATH
            = registerParticle("void_soul_death", VoidSoulDeathParticle.Provider::new, false);



    public static DeferredHolder<ParticleType<?>, SimpleParticleType> registerParticle(String name, ParticleEngine.SpriteParticleRegistration<SimpleParticleType> particleMetaFactory, boolean alwaysShow) {
        var holder = PARTICLE_TYPES.register(name, () -> new SimpleParticleType(alwaysShow));
        particleFactoryMap.put(holder, particleMetaFactory);
        return holder;
    }

    public static void bootstrap(RegisterParticleProvidersEvent event) {
        particleFactoryMap.forEach((holder, particleMetaFactory) -> {
            event.registerSpriteSet(holder.get(), particleMetaFactory);
        });
    }

}
