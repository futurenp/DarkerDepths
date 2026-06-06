package com.naterbobber.darkerdepths.network.packets;

import com.naterbobber.darkerdepths.DarkerDepths;
import com.naterbobber.darkerdepths.client.screen_effects.SoulBindingBlackoutHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record DeathAnchorActivatePacket() implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<DeathAnchorActivatePacket> TYPE = new CustomPacketPayload.Type<>(DarkerDepths.id("send_death_anchor_overlay"));
    public static final StreamCodec<FriendlyByteBuf, DeathAnchorActivatePacket> CODEC = CustomPacketPayload.codec(DeathAnchorActivatePacket::write, DeathAnchorActivatePacket::new);

    private DeathAnchorActivatePacket(FriendlyByteBuf buf) {
        this();
    }

    public static void write(DeathAnchorActivatePacket packet, FriendlyByteBuf buf) {
    }

    @SuppressWarnings("Convert2Lambda")
    public static void handle(DeathAnchorActivatePacket packet, IPayloadContext context) {
        if(!context.flow().isClientbound()) return;

        context.enqueueWork(new Runnable() {
            @Override
            public void run() {
                SoulBindingBlackoutHandler.start();
            }
        });
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

}
