package com.naterbobber.darkerdepths.network;

import com.naterbobber.darkerdepths.DarkerDepths;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record ScorcherFlashPacket(int durationTicks) implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<ScorcherFlashPacket> TYPE =
            new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(DarkerDepths.MOD_ID, "scorcher_flash"));

    public static final StreamCodec<ByteBuf, ScorcherFlashPacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT,
            ScorcherFlashPacket::durationTicks,
            ScorcherFlashPacket::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}