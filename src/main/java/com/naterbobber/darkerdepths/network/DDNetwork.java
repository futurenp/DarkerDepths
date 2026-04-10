package com.naterbobber.darkerdepths.network;

import com.naterbobber.darkerdepths.DarkerDepths;
import com.naterbobber.darkerdepths.client.fog.modifiers.ScorcherFlashModifier;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.Optional;

public class DDNetwork {
    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel INSTANCE = NetworkRegistry.ChannelBuilder.named(
                    DarkerDepths.id("network"))
            .clientAcceptedVersions(PROTOCOL_VERSION::equals)
            .serverAcceptedVersions(PROTOCOL_VERSION::equals)
            .networkProtocolVersion(() -> PROTOCOL_VERSION)
            .simpleChannel();

    protected static int packetID = 0;

    public DDNetwork() {
    }

    public static void init() {
        INSTANCE.registerMessage(getPacketID(), SendDeathAnchorPacket.class,
                SendDeathAnchorPacket::write,
                SendDeathAnchorPacket::read,
                SendDeathAnchorPacket::handle,
                Optional.of(NetworkDirection.PLAY_TO_CLIENT)
        );

        INSTANCE.registerMessage(getPacketID(), ScorcherFlashPacket.class,
                ScorcherFlashPacket::write,
                ScorcherFlashPacket::read,
                (packet, contextSupplier) -> {
                    NetworkEvent.Context context = contextSupplier.get();
                    context.enqueueWork(() -> {
                        ScorcherFlashModifier.triggerFlash(packet.durationTicks());
                    });
                    context.setPacketHandled(true);
                },
                Optional.of(NetworkDirection.PLAY_TO_CLIENT)
        );
    }

    public static int getPacketID() {
        return packetID++;
    }
}