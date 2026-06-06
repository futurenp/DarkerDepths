package com.naterbobber.darkerdepths.network.packets;

import com.naterbobber.darkerdepths.DarkerDepths;
import com.naterbobber.darkerdepths.client.screen_effects.ScorcherFlashHandler;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
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
                if(!ScorcherFlashHandler.isFlashed()) {
                    ScorcherFlashHandler.triggerFlash(packet.durationTicks());
                } else if(ScorcherFlashHandler.getTicksRemaining() < ScorcherFlashHandler.getMaxTicks() - 20) {
                    ScorcherFlashHandler.setFlashTicksRemaining(ScorcherFlashHandler.getMaxTicks() - 20);
                }
            }
        });
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}