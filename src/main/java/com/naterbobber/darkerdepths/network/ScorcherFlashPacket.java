package com.naterbobber.darkerdepths.network;

import net.minecraft.network.FriendlyByteBuf;

public record ScorcherFlashPacket(int durationTicks) {

    public static ScorcherFlashPacket read(FriendlyByteBuf buf) {
        return new ScorcherFlashPacket(buf.readInt());
    }

    public void write(FriendlyByteBuf buf) {
        buf.writeInt(this.durationTicks);
    }
}