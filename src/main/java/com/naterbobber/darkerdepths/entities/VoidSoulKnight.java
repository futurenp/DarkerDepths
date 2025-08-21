package com.naterbobber.darkerdepths.entities;

import com.mojang.serialization.Dynamic;
import com.naterbobber.darkerdepths.entities.ai.VoidSoulKnightAi;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;

public class VoidSoulKnight extends Monster {

    public VoidSoulKnight(EntityType<? extends Monster> type, Level level) {
        super(type, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 15.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.1D)
                .add(Attributes.ATTACK_DAMAGE, 3.0D);
    }

    @Override
    public Brain<VoidSoulKnight> getBrain() {
        return (Brain<VoidSoulKnight>) super.getBrain();
    }

    @Override
    protected Brain.Provider<VoidSoulKnight> brainProvider() {
        return VoidSoulKnightAi.brainProvider();
    }

    @Override
    protected Brain<?> makeBrain(Dynamic<?> dynamic) {
        return VoidSoulKnightAi.makeBrain(this.brainProvider().makeBrain(dynamic));
    }

    @Override
    protected void customServerAiStep() {
        this.level().getProfiler().push("voidSoulKnightBrain");
        this.getBrain().tick((ServerLevel)this.level(), this);
        this.level().getProfiler().pop();
        this.level().getProfiler().push("voidSoulKnightActivityUpdate");
        VoidSoulKnightAi.updateActivity(this);
        this.level().getProfiler().pop();
        super.customServerAiStep();
    }
}
