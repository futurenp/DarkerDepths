package com.naterbobber.darkerdepths.network;

import com.naterbobber.darkerdepths.DarkerDepths;
import com.naterbobber.darkerdepths.events.ClientDeathAnchorAnimationOverlay;
import com.naterbobber.darkerdepths.init.DDSoundEvents;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.function.Supplier;

public record SendDeathAnchorPacket() implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<SendDeathAnchorPacket> TYPE = new CustomPacketPayload.Type<>(DarkerDepths.id("send_death_anchor"));
    public static final StreamCodec<FriendlyByteBuf, SendDeathAnchorPacket> CODEC = CustomPacketPayload.codec(SendDeathAnchorPacket::write, SendDeathAnchorPacket::new);

    private SendDeathAnchorPacket(FriendlyByteBuf buf) {
        this();
    }

    public static void write(SendDeathAnchorPacket packet, FriendlyByteBuf buf) {
    }

    public static void handle(SendDeathAnchorPacket msg, IPayloadContext context) {
        context.enqueueWork(() -> {
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
        });
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

}
