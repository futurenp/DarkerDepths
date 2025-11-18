package com.naterbobber.darkerdepths.network;

import com.naterbobber.darkerdepths.DarkerDepths;
import com.naterbobber.darkerdepths.client.ClientDeathAnchorAnimationOverlay;
import com.naterbobber.darkerdepths.init.DDSoundEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.sounds.SoundSource;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record SendDeathAnchorPacket() implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<SendDeathAnchorPacket> TYPE = new CustomPacketPayload.Type<>(DarkerDepths.id("send_death_anchor_overlay"));
    public static final StreamCodec<FriendlyByteBuf, SendDeathAnchorPacket> CODEC = CustomPacketPayload.codec(SendDeathAnchorPacket::write, SendDeathAnchorPacket::new);

    private SendDeathAnchorPacket(FriendlyByteBuf buf) {
        this();
    }

    public static void write(SendDeathAnchorPacket packet, FriendlyByteBuf buf) {
    }

    @SuppressWarnings("Convert2Lambda")
    public static void handle(SendDeathAnchorPacket msg, IPayloadContext context) {
        if(!context.flow().isClientbound()) return;

        context.enqueueWork(new Runnable() {
            @Override
            public void run() {
                LocalPlayer player = Minecraft.getInstance().player;

                if (player == null) return;

                player.level().playSound(
                        player,
                        player.getX(), player.getY(), player.getZ(),
                        DDSoundEvents.BLOCK_DEATH_ANCHOR_SOUL_BINDING.get(),
                        SoundSource.PLAYERS,
                        1.0f,
                        1.0f
                );

                ClientDeathAnchorAnimationOverlay.startOverlay();
            }
        });
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

}
