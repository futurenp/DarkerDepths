package com.naterbobber.darkerdepths.entities;

import com.mojang.serialization.Dynamic;
import com.naterbobber.darkerdepths.entities.ai.BodySnatcherAi;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;

public class BodySnatcher extends Monster {

    public BodySnatcher(EntityType<? extends Monster> type, Level level) {
        super(type, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 15.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.1D)
                .add(Attributes.ATTACK_DAMAGE, 3.0D);
    }

    @Override
    public Brain<BodySnatcher> getBrain() {
        return (Brain<BodySnatcher>) super.getBrain();
    }

    @Override
    protected Brain.Provider<BodySnatcher> brainProvider() {
        return BodySnatcherAi.brainProvider();
    }

    @Override
    protected Brain<?> makeBrain(Dynamic<?> dynamic) {
        return BodySnatcherAi.makeBrain(this.brainProvider().makeBrain(dynamic));
    }

    @Override
    protected void customServerAiStep() {
        this.level().getProfiler().push("bodySnatcherBrain");
        this.getBrain().tick((ServerLevel)this.level(), this);
        this.level().getProfiler().pop();
        this.level().getProfiler().push("bodySnatcherActivityUpdate");
        BodySnatcherAi.updateActivity(this);
        this.level().getProfiler().pop();
        super.customServerAiStep();
    }
}
