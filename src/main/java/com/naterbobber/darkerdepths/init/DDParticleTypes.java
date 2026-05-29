package com.naterbobber.darkerdepths.init;

import com.naterbobber.darkerdepths.DarkerDepths;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.Registries;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class DDParticleTypes {

    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES = DeferredRegister.create(Registries.PARTICLE_TYPE, DarkerDepths.MOD_ID);

    private static DeferredHolder<ParticleType<?>, SimpleParticleType> register(String name) {
        return PARTICLE_TYPES.register(name, () -> new SimpleParticleType(false));
    }

    private static DeferredHolder<ParticleType<?>, SimpleParticleType> register(String name, boolean alwaysShow) {
        return PARTICLE_TYPES.register(name, () -> new SimpleParticleType(alwaysShow));
    }

    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> DRIPPING_AMBER = register("dripping_amber");
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> FALLING_AMBER = register("falling_amber");
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> LANDING_AMBER = register("landing_amber");

    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> VOID_SOUL = register("void_soul");
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> VOID_SOUL_FLAME = register("void_soul_flame");
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> VOID_SOUL_FLAME_SMOKE = register("void_soul_flame_smoke");
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> VOID_SOUL_DEATH = register("void_soul_death");

    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> GEYSER_PASSIVE_SMOKE = register("geyser_passive_smoke");
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> GEYSER_BURST_SMOKE = register("geyser_burst_smoke");
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> GEYSER_BURST_SMOKE_LAVA = register("geyser_burst_smoke_lava");
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> GEYSER_BURST_MIST = register("geyser_burst_mist");
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> GEYSER_BURST_FLAME = register("geyser_burst_flame");
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> SMALL_GEYSER_BURST_FLAME = register("small_geyser_burst_flame");
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> GEYSER_BURST_FLAME_BOOSTED = register("geyser_burst_flame_boosted");

    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> MOLTEN_ASH = register("molten_ash");
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> GLOW_GLIMMER = register("glow_glimmer");
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> SCORCHER_SEARCHLIGHT = register("scorcher_searchlight");
}