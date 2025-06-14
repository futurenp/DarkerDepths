package com.naterbobber.darkerdepths.mixin;

import com.naterbobber.darkerdepths.DarkerDepths;
import com.naterbobber.darkerdepths.api.DeathAnchorLocation;
import net.minecraft.core.GlobalPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;

@Mixin(Player.class)
public class PlayerMixin implements DeathAnchorLocation {
    @Unique
    private Optional<GlobalPos> deathAnchorLocation = Optional.empty();

    @Inject(at = @At("HEAD"), method = "addAdditionalSaveData")
    private void G$addAdditionalSaveData(CompoundTag tag, CallbackInfo ci) {
        this.getDeathAnchorLocation().flatMap((globalPos) -> {
            return GlobalPos.CODEC.encodeStart(NbtOps.INSTANCE, globalPos).resultOrPartial(DarkerDepths.LOGGER::error);
        }).ifPresent((location) -> {
            tag.put("DeathAnchorLocation", location);
        });
    }

    @Inject(at = @At("HEAD"), method = "readAdditionalSaveData")
    private void G$readAdditionalSaveData(CompoundTag tag, CallbackInfo ci) {
        if (tag.contains("DeathAnchorLocation", 10)) {
            this.setDeathAnchorLocation(GlobalPos.CODEC.parse(NbtOps.INSTANCE, tag.get("DeathAnchorLocation")).resultOrPartial(DarkerDepths.LOGGER::error));
        }
    }

    @Override
    public void setDeathAnchorLocation(Optional<GlobalPos> globalPos) {
        this.deathAnchorLocation = globalPos;
    }

    @Override
    public Optional<GlobalPos> getDeathAnchorLocation() {
        return this.deathAnchorLocation;
    }
}
