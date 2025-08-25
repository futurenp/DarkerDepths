package com.naterbobber.darkerdepths.entities;

import com.naterbobber.darkerdepths.entities.ai.AttackMemoryTargetGoal;
import com.naterbobber.darkerdepths.entities.control.ConfigurableMoveControl;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ambient.Bat;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class VoidSoulKnightEntity extends Monster {

    private int attackTick;
    private static final double HEALTH = 80;
    private static final double MOVEMENT_SPEED = .185;
    private static final double ATTACK_DAMAGE = 14;
    private static final double ATTACK_KNOCKBACK = 2;
    private static final double KNOCKBACK_RESISTANCE = 0.85;
    private static final double FOLLOW_RANGE = 32;

    public VoidSoulKnightEntity(EntityType<? extends Monster> type, Level world) {
        super(type, world);
        this.xpReward = 35;
        this.moveControl = new ConfigurableMoveControl(this, 10.0F);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.3D, true));
        this.goalSelector.addGoal(2, new WaterAvoidingRandomStrollGoal(this, 1.2D));
        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 10.0F));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new AttackMemoryTargetGoal<>(this, Player.class, 640, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Mob.class, 10, false, false, (entity) -> !(entity instanceof Creeper) && !(entity instanceof GlowshroomMonsterEntity) && !(entity instanceof Bat)));
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (this.attackTick > 0) {
            this.attackTick--;
        }
    }

    @Override
    public void handleEntityEvent(byte id) {
        if (id == 4) {
            this.playSound(SoundEvents.IRON_GOLEM_ATTACK, 1.0F, 1.5F);
        } else {
            super.handleEntityEvent(id);
        }
    }

    public int getAttackTick() {
        return this.attackTick;
    }

    @Override
    public boolean doHurtTarget(Entity entity) {
        if(attackTick <= 0) {
            this.attackTick = 30;
            this.level().broadcastEntityEvent(this, (byte) 4);
            float f = (float) this.getAttributeValue(Attributes.ATTACK_DAMAGE);
            return entity.hurt(this.level().damageSources().mobAttack(this), f);
        }
        return false;
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.IRON_GOLEM_STEP;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.IRON_GOLEM_DEATH;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.IRON_GOLEM_HURT;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, HEALTH)
                .add(Attributes.MOVEMENT_SPEED, MOVEMENT_SPEED)
                .add(Attributes.ATTACK_KNOCKBACK, ATTACK_KNOCKBACK)
                .add(Attributes.ATTACK_DAMAGE, ATTACK_DAMAGE)
                .add(Attributes.KNOCKBACK_RESISTANCE, KNOCKBACK_RESISTANCE)
                .add(Attributes.FOLLOW_RANGE, FOLLOW_RANGE);
    }
}
