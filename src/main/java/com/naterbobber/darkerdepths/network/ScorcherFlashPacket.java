package com.naterbobber.darkerdepths.network;

import com.naterbobber.darkerdepths.DarkerDepths;
import com.naterbobber.darkerdepths.client.ClientDeathAnchorAnimationOverlay;
import com.naterbobber.darkerdepths.client.fog.modifiers.ScorcherFlashModifier;
import com.naterbobber.darkerdepths.init.DDSoundEvents;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record ScorcherFlashPacket(int durationTicks) implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<ScorcherFlashPacket> TYPE =
            new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(DarkerDepths.MOD_ID, "scorcher_flash"));

    public static final StreamCodec<ByteBuf, ScorcherFlashPacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT,
            ScorcherFlashPacket::durationTicks,
            ScorcherFlashPacket::new
    );

    @SuppressWarnings("Convert2Lambda")
    public static void handle(ScorcherFlashPacket packet, IPayloadContext context) {
        if(!context.flow().isClientbound()) return;

        context.enqueueWork(new Runnable() {
            @Override
            public void run() {
                ScorcherFlashModifier.triggerFlash(packet.durationTicks());            }
        });
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}