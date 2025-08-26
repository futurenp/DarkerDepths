package com.naterbobber.darkerdepths.network;

import com.naterbobber.darkerdepths.events.ClientDeathAnchorAnimationOverlay;
import com.naterbobber.darkerdepths.init.DDSoundEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
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
        ctx.get().enqueueWork(() -> {
            Player player = Minecraft.getInstance().player;

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
        ctx.get().setPacketHandled(true);
    }

}
