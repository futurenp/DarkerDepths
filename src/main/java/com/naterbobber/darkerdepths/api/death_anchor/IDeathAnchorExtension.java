package com.naterbobber.darkerdepths.api.death_anchor;

import com.naterbobber.darkerdepths.DarkerDepths;
import net.minecraft.core.GlobalPos;
import net.minecraft.resources.ResourceLocation;

import java.util.Optional;

public interface IDeathAnchorExtension {
    ResourceLocation MAX_HEALTH_DEBUFF_ID = DarkerDepths.id("max_health_debuff");
    int RECOVERY_COOLDOWN = 1200;
    void darkerDepths$setHealthCoolDown(int amount);
    void darkerDepths$recoverMaxHealth(double amount);
    void darkerDepths$setDeathAnchorLocation(Optional<GlobalPos> globalPos);
    Optional<GlobalPos> darkerDepths$getDeathAnchorLocation();
}
