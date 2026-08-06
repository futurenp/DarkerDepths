package com.naterbobber.darkerdepths.common.mixin;

import com.naterbobber.darkerdepths.DarkerDepths;
import com.naterbobber.darkerdepths.common.api.ITombBedExtension;
import com.naterbobber.darkerdepths.common.api.death_anchor.IDeathAnchorExtension;
import net.minecraft.core.GlobalPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;

@Mixin(Player.class)
public class PlayerMixin implements IDeathAnchorExtension, ITombBedExtension {
    @Unique
    private Optional<GlobalPos> darkerDepths$deathAnchorLocation = Optional.empty();
    @Unique
    private int darkerDepths$healthCooldown;
    private boolean darkerDepths$hasSleptInTombBed;

    @Inject(at = @At("HEAD"), method = "addAdditionalSaveData")
    private void darkerdepths$addAdditionalSaveData(CompoundTag tag, CallbackInfo ci) {
        this.darkerDepths$getDeathAnchorLocation().flatMap((globalPos) ->
                GlobalPos.CODEC
                        .encodeStart(NbtOps.INSTANCE, globalPos)
                        .resultOrPartial(DarkerDepths.LOGGER::error))
                .ifPresent((location) -> tag.put("DeathAnchorLocation", location));
        tag.putInt("HealthCooldown", this.darkerDepths$healthCooldown);
        tag.putBoolean("SleptInTombBed", this.darkerDepths$hasSleptInTombBed);
    }

    @Inject(at = @At("HEAD"), method = "readAdditionalSaveData")
    private void darkerdepths$readAdditionalSaveData(CompoundTag tag, CallbackInfo ci) {
        if (tag.contains("DeathAnchorLocation", 10)) {
            this.darkerDepths$setDeathAnchorLocation(GlobalPos.CODEC.parse(NbtOps.INSTANCE, tag.get("DeathAnchorLocation")).resultOrPartial(DarkerDepths.LOGGER::error));
        }
        if(tag.contains("HealthCooldown", 10)) {
            this.darkerDepths$healthCooldown = tag.getInt("HealthCooldown");
        }
        if(tag.contains("SleptInTombBed")) {
            this.darkerDepths$hasSleptInTombBed = tag.getBoolean("SleptInTombBed");
        }
    }

    @Inject(at = @At("HEAD"), method = "tick")
    private void darkerdepths$tick(CallbackInfo ci) {
        Player player = (Player)(Object)this;
        if(player.level().isClientSide) return;
        if(player.getAttribute(Attributes.MAX_HEALTH).hasModifier(IDeathAnchorExtension.MAX_HEALTH_DEBUFF_ID)) {
            if(this.darkerDepths$healthCooldown <= 0) {
                darkerDepths$recoverMaxHealth(2);
            } else if(player.isAlive()) {
                this.darkerDepths$healthCooldown--;
            }
        }
    }

    @Override
    public void darkerDepths$setDeathAnchorLocation(Optional<GlobalPos> globalPos) {
        this.darkerDepths$deathAnchorLocation = globalPos;
    }

    @Override
    public Optional<GlobalPos> darkerDepths$getDeathAnchorLocation() {
        return this.darkerDepths$deathAnchorLocation;
    }

    @Override
    public void darkerDepths$setHealthCoolDown(int amount) {
        darkerDepths$healthCooldown = amount;
    }

    @Override
    public void darkerDepths$recoverMaxHealth(double amount) {
        Player player = (Player)(Object)this;
        var healthAttribute = player.getAttribute(Attributes.MAX_HEALTH);
        if(healthAttribute != null) {
            var modifier = healthAttribute.getModifier(IDeathAnchorExtension.MAX_HEALTH_DEBUFF_ID);
            var newAmount = modifier.amount() + 0.1;
            if(newAmount < 0) {
                healthAttribute.addOrReplacePermanentModifier(
                        new AttributeModifier(IDeathAnchorExtension.MAX_HEALTH_DEBUFF_ID, newAmount, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));
            } else {
                healthAttribute.removeModifier(IDeathAnchorExtension.MAX_HEALTH_DEBUFF_ID);
            }
            darkerDepths$setHealthCoolDown(IDeathAnchorExtension.RECOVERY_COOLDOWN);
        }
    }

    @Override
    public void darkerdepths$setHasLastSleptInTombBed(boolean hasLastSlept) {
        darkerDepths$hasSleptInTombBed = hasLastSlept;
    }

    @Override
    public boolean darkerdepths$hasLastSleptInTombBed() {
        return darkerDepths$hasSleptInTombBed;
    }
}
