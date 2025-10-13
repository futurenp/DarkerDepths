package com.naterbobber.darkerdepths.init;

import com.naterbobber.darkerdepths.DarkerDepths;
import com.naterbobber.darkerdepths.network.SendDeathAnchorPacket;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

@EventBusSubscriber(modid = DarkerDepths.MOD_ID)
public class DDNetwork {
    private static final String PROTOCOL_VERSION = "1";

    @SubscribeEvent
    public static void registerPayloadHandlers(RegisterPayloadHandlersEvent event) {
        PayloadRegistrar payloadRegistrar = event.registrar(PROTOCOL_VERSION);
        payloadRegistrar.playToClient(SendDeathAnchorPacket.TYPE, SendDeathAnchorPacket.CODEC, SendDeathAnchorPacket::handle);
    }

}
