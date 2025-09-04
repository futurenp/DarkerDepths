package com.naterbobber.darkerdepths.entities;

import com.naterbobber.darkerdepths.init.DDEntityTypes;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;

public abstract class VoidSoulMonster extends Monster {
    private double orbHeight = 0;
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
            Entity voidSoulEntity = DDEntityTypes.VOID_SOUL.get().create(serverLevel);
            if (voidSoulEntity != null) {
                voidSoulEntity.moveTo(this.getX(), this.getY() + this.getOrbHeight(), this.getZ(), this.getYRot(), this.getXRot());

                serverLevel.addFreshEntity(voidSoulEntity);
            }
        }
        this.remove(RemovalReason.KILLED);
        this.gameEvent(GameEvent.ENTITY_DIE);
    }
}
