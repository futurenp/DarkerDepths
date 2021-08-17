package com.naterbobber.darkerdepths.client.particle;

import com.naterbobber.darkerdepths.core.DarkerDepths;
import com.naterbobber.darkerdepths.core.registries.DDParticleTypes;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.ParticleManager;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

//<>

@Mod.EventBusSubscriber(modid = DarkerDepths.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ParticleHandler {
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void registerParticleFactories(ParticleFactoryRegisterEvent event) {
        ParticleManager particleManager = Minecraft.getInstance().particles;
        particleManager.registerFactory(DDParticleTypes.DRIPPING_RESIN.get(), DrippingParticle.DrippingResinFactory::new);
        particleManager.registerFactory(DDParticleTypes.FALLING_RESIN.get(), DrippingParticle.FallingResinFactory::new);
        particleManager.registerFactory(DDParticleTypes.LANDING_RESIN.get(), DrippingParticle.LandingResinFactory::new);
    }
}