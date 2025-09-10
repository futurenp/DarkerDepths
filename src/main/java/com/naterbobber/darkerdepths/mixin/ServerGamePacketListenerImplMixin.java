package com.naterbobber.darkerdepths.mixin;

import com.naterbobber.darkerdepths.init.DDMobEffects;
import net.minecraft.network.protocol.game.ServerboundMovePlayerPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.util.Mth;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerGamePacketListenerImpl.class)
public class ServerGamePacketListenerImplMixin {

    @Shadow public ServerPlayer player;

    @Shadow private double lastGoodX;

    @Shadow private double lastGoodY;

    @Shadow private double lastGoodZ;

    @Inject(
            at = @At(value = "INVOKE", target = "Lnet/minecraft/server/network/ServerGamePacketListenerImpl;teleport(DDDFF)V"),
            method = "handleMovePlayer"
    )
    private void G$handleMovePlayer(ServerboundMovePlayerPacket packet, CallbackInfo ci) {
        if (this.player.hasEffect(DDMobEffects.SOUL_BINDING.get())) {
            double d0 = clampHorizontal(packet.getX(this.player.getX()));
            double d1 = clampVertical(packet.getY(this.player.getY()));
            double d2 = clampHorizontal(packet.getZ(this.player.getZ()));
            float f = Mth.wrapDegrees(packet.getYRot(this.player.getYRot()));
            float f1 = Mth.wrapDegrees(packet.getXRot(this.player.getXRot()));

            this.player.absMoveTo(d0, d1, d2, f, f1);

            this.player.serverLevel().getChunkSource().move(this.player);

            this.player.resetFallDistance();

            this.lastGoodX = this.player.getX();
            this.lastGoodY = this.player.getY();
            this.lastGoodZ = this.player.getZ();
        }
    }

    @Unique
    private double clampHorizontal(double value) {
        return Mth.clamp(value, -3.0E7D, 3.0E7D);
    }

    @Unique
    private double clampVertical(double value) {
        return Mth.clamp(value, -2.0E7D, 2.0E7D);
    }

}
