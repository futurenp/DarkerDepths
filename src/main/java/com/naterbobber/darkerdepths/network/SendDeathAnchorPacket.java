package com.naterbobber.darkerdepths.network;

import com.naterbobber.darkerdepths.events.ClientDeathAnchorAnimationOverlay;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SendDeathAnchorPacket {

    public SendDeathAnchorPacket() {
    }

    public static SendDeathAnchorPacket read(FriendlyByteBuf buf) {
        return new SendDeathAnchorPacket();
    }

    public static void write(SendDeathAnchorPacket packet, FriendlyByteBuf buf) {
    }

    public static void handle(SendDeathAnchorPacket packet, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(ClientDeathAnchorAnimationOverlay::startOverlay);
        ctx.get().setPacketHandled(true);
    }

}
