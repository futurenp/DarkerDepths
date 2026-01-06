package com.naterbobber.darkerdepths.network;

import com.naterbobber.darkerdepths.client.ClientDeathAnchorAnimationOverlay;
import com.naterbobber.darkerdepths.init.DDSoundEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.sounds.SoundSource;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
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

    public static void handle(SendDeathAnchorPacket msg, Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() ->
                DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> ClientPacketHandler.execute(msg))
        );
        context.get().setPacketHandled(true);
    }

    private static class ClientPacketHandler {
        public static void execute(SendDeathAnchorPacket msg) {
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
    }
}
