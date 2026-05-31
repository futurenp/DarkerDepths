package com.naterbobber.darkerdepths.network;

import com.naterbobber.darkerdepths.network.packets.DeathAnchorActivatePacket;
import com.naterbobber.darkerdepths.network.packets.ScorcherFlashPacket;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.HandlerThread;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

public class DDNetwork {
    public static final String PROTOCOL_VERSION = "1";

    @SubscribeEvent
    public static void registerPayloadHandlers(RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar(PROTOCOL_VERSION)
                .executesOn(HandlerThread.NETWORK);
        registrar.playToClient(
                DeathAnchorActivatePacket.TYPE,
                DeathAnchorActivatePacket.CODEC,
                DeathAnchorActivatePacket::handle
        );
        registrar.playToClient(
                ScorcherFlashPacket.TYPE,
                ScorcherFlashPacket.STREAM_CODEC,
                ScorcherFlashPacket::handle
        );
    }
}
