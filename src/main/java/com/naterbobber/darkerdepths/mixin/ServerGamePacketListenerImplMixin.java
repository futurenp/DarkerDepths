package com.naterbobber.darkerdepths.mixin;

import com.naterbobber.darkerdepths.init.DDMobEffects;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ServerGamePacketListenerImpl.class)
public class ServerGamePacketListenerImplMixin {

    @Shadow public ServerPlayer player;

    @ModifyVariable(
            method = "handleMovePlayer(Lnet/minecraft/network/protocol/game/ServerboundMovePlayerPacket;)V",
            at = @At("STORE"),
            ordinal = 10
    )
    private double DD$modifyMovementValue(double original) {
        boolean flag = this.player.hasEffect(DDMobEffects.SOUL_BINDING);
        return flag ? 0.0D : original;
    }

}
