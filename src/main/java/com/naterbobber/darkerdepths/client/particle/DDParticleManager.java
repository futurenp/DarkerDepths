package com.naterbobber.darkerdepths.client.particle;

import com.naterbobber.darkerdepths.core.DarkerDepths;
import com.naterbobber.darkerdepths.core.registries.DDParticleTypes;
import net.minecraft.client.Minecraft;
import net.minecraft.particles.BasicParticleType;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

//<>

@Mod.EventBusSubscriber(modid = DarkerDepths.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DDParticleManager {

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void registerParticleFactories(ParticleFactoryRegisterEvent event) {
        Minecraft mc = Minecraft.getInstance();

        if (particleIsNotNull(DDParticleTypes.CAVE_FALLING_DUST)) {
            mc.particles.registerFactory(DDParticleTypes.CAVE_FALLING_DUST.get(), CaveFallingSandParticle.Factory::new);
        }
    }

    private static boolean particleIsNotNull(RegistryObject<BasicParticleType> particle) {
        return ObfuscationReflectionHelper.getPrivateValue(RegistryObject.class, particle, "value") != null;
    }
}