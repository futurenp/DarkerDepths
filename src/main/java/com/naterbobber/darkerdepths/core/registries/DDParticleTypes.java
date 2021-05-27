package com.naterbobber.darkerdepths.core.registries;

import com.naterbobber.darkerdepths.core.DDRegistries;
import com.naterbobber.darkerdepths.core.DarkerDepths;
import net.minecraft.particles.BasicParticleType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;

//<>

@Mod.EventBusSubscriber(modid = DarkerDepths.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DDParticleTypes {
    public static final DDRegistries HELPER = DarkerDepths.REGISTRIES;

    public static final RegistryObject<BasicParticleType> CAVE_FALLING_DUST = HELPER.registerParticle("cave_falling_dust", false);
}