package com.naterbobber.darkerdepths.init;

import com.naterbobber.darkerdepths.DarkerDepths;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = DarkerDepths.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DDParticleTypes {

    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, DarkerDepths.MOD_ID);

    // Amber Particles
    public static final RegistryObject<SimpleParticleType> DRIPPING_AMBER = registerParticle("dripping_amber", false);
    public static final RegistryObject<SimpleParticleType> FALLING_AMBER = registerParticle("falling_amber", false);
    public static final RegistryObject<SimpleParticleType> LANDING_AMBER = registerParticle("landing_amber", false);

    // Void Soul Particles
    public static final RegistryObject<SimpleParticleType> VOID_SOUL = registerParticle("void_soul", false);
    public static final RegistryObject<SimpleParticleType> VOID_SOUL_FLAME = registerParticle("void_soul_flame", false);
    public static final RegistryObject<SimpleParticleType> VOID_SOUL_FLAME_SMOKE = registerParticle("void_soul_flame_smoke", false);
    public static final RegistryObject<SimpleParticleType> VOID_SOUL_DEATH = registerParticle("void_soul_death", false);

    // Geyser Particles
    public static final RegistryObject<SimpleParticleType> GEYSER_PASSIVE_SMOKE = registerParticle("geyser_passive_smoke", false);
    public static final RegistryObject<SimpleParticleType> GEYSER_BURST_SMOKE = registerParticle("geyser_burst_smoke", false);
    public static final RegistryObject<SimpleParticleType> GEYSER_BURST_SMOKE_LAVA = registerParticle("geyser_burst_smoke_lava", false);
    public static final RegistryObject<SimpleParticleType> GEYSER_BURST_MIST = registerParticle("geyser_burst_mist", false);
    public static final RegistryObject<SimpleParticleType> GEYSER_BURST_FLAME = registerParticle("geyser_burst_flame", false);
    public static final RegistryObject<SimpleParticleType> SMALL_GEYSER_BURST_FLAME = registerParticle("small_geyser_burst_flame", false);
    public static final RegistryObject<SimpleParticleType> GEYSER_BURST_FLAME_BOOSTED = registerParticle("geyser_burst_flame_boosted", false);

    // Misc
    public static final RegistryObject<SimpleParticleType> MOLTEN_ASH = registerParticle("molten_ash", false);
    public static final RegistryObject<SimpleParticleType> GLOW_GLIMMER = registerParticle("glow_glimmer", false);
    public static final RegistryObject<SimpleParticleType> SCORCHER_SEARCHLIGHT = registerParticle("scorcher_searchlight", false);

    // Helper method matching the old class structure
    public static RegistryObject<SimpleParticleType> registerParticle(String name, boolean alwaysShow) {
        return PARTICLE_TYPES.register(name, () -> new SimpleParticleType(alwaysShow));
    }
}