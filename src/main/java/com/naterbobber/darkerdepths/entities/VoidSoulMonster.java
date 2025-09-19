package com.naterbobber.darkerdepths.entities;

import com.naterbobber.darkerdepths.init.DDEntityTypes;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;

public abstract class VoidSoulMonster extends Monster {
    private double orbHeight = 0;
    private static final EntityDataAccessor<Boolean> IDLE =
            SynchedEntityData.defineId(VoidSoulMonster.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> ATTACKING =
            SynchedEntityData.defineId(VoidSoulMonster.class, EntityDataSerializers.BOOLEAN);
    protected VoidSoulMonster(EntityType<? extends Monster> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public double getOrbHeight() {
        return orbHeight;
    }

    public void setOrbHeight(double orbHeight) {
        this.orbHeight = orbHeight;
    }

    @Override
    public void die(DamageSource pDamageSource) {
        if (!this.level().isClientSide()) {
            ServerLevel serverLevel = (ServerLevel) this.level();
            ParticleOptions particle = ParticleTypes.LARGE_SMOKE;
            serverLevel.sendParticles(
                    particle,
                    this.getX(),
                    this.getY(0.5),
                    this.getZ(),
                    30,
                    this.getBbWidth() / 2.0,
                    this.getBbHeight() / 2.0,
                    this.getBbWidth() / 2.0,
                    0.05
            );
            VoidSoulEntity voidSoulEntity = DDEntityTypes.VOID_SOUL.get().create(serverLevel);
            if (voidSoulEntity != null) {
                voidSoulEntity.moveTo(this.getX(), this.getY() + this.getOrbHeight(), this.getZ(), this.getYRot(), this.getXRot());
                voidSoulEntity.setExperienceDrop(this.xpReward);

                serverLevel.addFreshEntity(voidSoulEntity);
            }
        }
        this.remove(RemovalReason.KILLED);
        this.gameEvent(GameEvent.ENTITY_DIE);
    }

    public boolean isIdle() {
        return this.entityData.get(IDLE);
    }

    public boolean isAttacking() {
        return this.entityData.get(ATTACKING);
    }

    public void setIdle(boolean idle) {
        this.entityData.set(IDLE, idle);
    }

    public void setAttacking(boolean attacking) {
        this.entityData.set(ATTACKING, attacking);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(IDLE, false);
        builder.define(ATTACKING, false);
    }
}
