package com.naterbobber.darkerdepths.entities;

import com.naterbobber.darkerdepths.entities.goals.AttackMemoryTargetGoal;
import com.naterbobber.darkerdepths.entities.control.ConfigurableMoveControl;
import com.naterbobber.darkerdepths.init.DDSoundEvents;
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
import net.minecraft.world.entity.ai.goal.MoveTowardsTargetGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ambient.Bat;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class GlowshroomMonsterEntity extends Monster {
    private int attackTick;

    public GlowshroomMonsterEntity(EntityType<? extends Monster> type, Level world) {
        super(type, world);
        this.xpReward = 20;
        this.moveControl = new ConfigurableMoveControl(this, 12.0F);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 40)
                .add(Attributes.MOVEMENT_SPEED, 0.25)
                .add(Attributes.ATTACK_KNOCKBACK, 1.3)
                .add(Attributes.ATTACK_DAMAGE, 8)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.75)
                .add(Attributes.FOLLOW_RANGE, 48);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.25D, true));
        this.goalSelector.addGoal(3, new MoveTowardsTargetGoal(this, 0.9, 48.0F));
        this.goalSelector.addGoal(7, new WaterAvoidingRandomStrollGoal(this, 0.9D));
        this.targetSelector.addGoal(2, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(4, new AttackMemoryTargetGoal<>(this, Player.class, 300, true));
        this.targetSelector.addGoal(5, new NearestAttackableTargetGoal<>(this, Mob.class, 10, false, false, (entity) -> !(entity instanceof Creeper) && !(entity instanceof GlowshroomMonsterEntity) && !(entity instanceof Bat)));
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
            this.attackTick = 10;
            this.playSound(SoundEvents.RAVAGER_ATTACK, 1.0F, 1.0F);
        } else {
            super.handleEntityEvent(id);
        }
    }

    public int getAttackTick() {
        return this.attackTick;
    }

    @Override
    public boolean doHurtTarget(Entity entity) {
        this.attackTick = 10;
        this.level().broadcastEntityEvent(this, (byte) 4);
        float f = (float) this.getAttributeValue(Attributes.ATTACK_DAMAGE);
        return entity.hurt(this.level().damageSources().mobAttack(this), f);
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return DDSoundEvents.ENTITY_GLOWSHROOM_MONSTER_AMBIENT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return DDSoundEvents.ENTITY_GLOWSHROOM_MONSTER_DEATH.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource p_33034_) {
        return DDSoundEvents.ENTITY_GLOWSHROOM_MONSTER_HURT.get();
    }
}