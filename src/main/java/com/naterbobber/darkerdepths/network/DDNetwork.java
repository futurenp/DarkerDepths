package com.naterbobber.darkerdepths.network;

import com.naterbobber.darkerdepths.DarkerDepths;
import com.naterbobber.darkerdepths.client.fog.modifiers.ScorcherFlashModifier;
import com.naterbobber.darkerdepths.network.SendDeathAnchorPacket;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
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
                SendDeathAnchorPacket.TYPE,
                SendDeathAnchorPacket.CODEC,
                SendDeathAnchorPacket::handle
        );
        registrar.playToClient(
                ScorcherFlashPacket.TYPE,
                ScorcherFlashPacket.STREAM_CODEC,
                (payload, context) -> {
                    context.enqueueWork(() -> {
                        ScorcherFlashModifier.triggerFlash(payload.durationTicks());
                    });
                }
        );
    }
}
