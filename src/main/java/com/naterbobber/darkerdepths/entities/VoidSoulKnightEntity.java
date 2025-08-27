package com.naterbobber.darkerdepths.entities;

import com.naterbobber.darkerdepths.entities.control.ConfigurableMoveControl;
import com.naterbobber.darkerdepths.entities.goals.AttackMemoryTargetGoal;
import com.naterbobber.darkerdepths.entities.goals.ConfigurableReachMeleeAttackGoal;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.constant.DefaultAnimations;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class VoidSoulKnightEntity extends Monster implements GeoEntity {
    private int attackTick;
    private int firstDamageDelay;
    private int secondDamageDelay;
    private boolean firstAttackDone;
    private Entity attackTarget;
    private static final double HEALTH = 80;
    private static final double MOVEMENT_SPEED = .17;
    private static final double ATTACK_DAMAGE = 10;
    private static final double ATTACK_KNOCKBACK = 2;
    private static final double KNOCKBACK_RESISTANCE = 0.85;
    private static final double FOLLOW_RANGE = 32;

    private static final EntityDataAccessor<Boolean> IS_DORMANT =
            SynchedEntityData.defineId(VoidSoulKnightEntity.class, EntityDataSerializers.BOOLEAN);
    protected static final RawAnimation WALK_ANIM = RawAnimation.begin().thenLoop("move.walk");
    protected static final RawAnimation ATTACK_ANIM = RawAnimation.begin().then("attack.swing", Animation.LoopType.PLAY_ONCE);
    protected static final RawAnimation IDLE_ANIM = RawAnimation.begin().thenLoop("idle");
    protected static final RawAnimation DORMANT_ANIM = RawAnimation.begin().thenLoop("dormant");

    private final AnimatableInstanceCache geoCache = GeckoLibUtil.createInstanceCache(this);

    public VoidSoulKnightEntity(EntityType<? extends Monster> type, Level world) {
        super(type, world);
        this.xpReward = 40;
        this.moveControl = new ConfigurableMoveControl(this, 10.0F);
    }

    @Override
    protected void registerGoals() {
        if (!this.isDormant()) {
            this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
            this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
            this.goalSelector.addGoal(2, new ConfigurableReachMeleeAttackGoal(this, 1.3D, true, 2.75f));
            this.targetSelector.addGoal(3, new AttackMemoryTargetGoal<>(this, Player.class, 640, true));
            this.goalSelector.addGoal(7, new WaterAvoidingRandomStrollGoal(this, 1.2D));
        }
    }

    @Override
    public void aiStep() {
        super.aiStep();

        if (this.attackTick > 0) {
            this.attackTick--;
        }

        if (this.firstDamageDelay > 0) {
            this.firstDamageDelay--;
        }

        if (this.secondDamageDelay > 0) {
            this.secondDamageDelay--;
        }

        if(this.attackTarget == null) return;

        if(!this.attackTarget.isAlive()) {
            this.attackTarget = null;
            return;
        }

        float damage = (float) this.getAttributeValue(Attributes.ATTACK_DAMAGE);

        if(this.firstDamageDelay == 0 && !this.firstAttackDone) {
            this.attackTarget.hurt(this.level().damageSources().mobAttack(this), damage);
            this.firstAttackDone = true;
            return;
        }

        if(this.secondDamageDelay == 0) {
            if (this.distanceToSqr(this.attackTarget) < 12) {
                this.attackTarget.hurt(this.level().damageSources().mobAttack(this), damage);
            }
            this.attackTarget = null;
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (this.isDormant() && !this.level().isClientSide) {
            Player player = this.level().getNearestPlayer(this, 5);

            if (player != null && !player.isCreative()) {
                this.setDormant(false);
                this.registerGoals();
                this.setTarget(player);
                this.playSound(SoundEvents.IRON_GOLEM_REPAIR, 1.0F, 0.4F);
            }
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
            this.attackTick = 50;
            this.firstDamageDelay = 5;
            this.firstAttackDone = false;
            this.secondDamageDelay = 25;

            if (entity instanceof LivingEntity) {
                this.attackTarget = (LivingEntity)entity;
            }

            this.level().broadcastEntityEvent(this, (byte) 4);

            return true;
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

    @Override
    public void registerControllers(final AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "walkingController", 5, this::predicate));
        controllers.add(new AnimationController<>(this, "attackingController", 0, this::attackPredicate));
    }

    protected <E extends VoidSoulKnightEntity> PlayState predicate(final AnimationState<E> event) {
        if (this.isDormant()) {
            return event.setAndContinue(DORMANT_ANIM);
        }

        if (this.getAttackTick() > 0) {
            return PlayState.STOP;
        }

        if (event.isMoving()) {
            return event.setAndContinue(WALK_ANIM);
        }

        return event.setAndContinue(IDLE_ANIM);
    }

    protected <E extends VoidSoulKnightEntity> PlayState attackPredicate(final AnimationState<E> event) {
        if (this.isDormant()) {
            return PlayState.STOP;
        }

        if (this.getAttackTick() > 0) {
            if (event.getController().getAnimationState().equals(AnimationController.State.STOPPED)) {
                event.getController().setAnimation(ATTACK_ANIM);
            }
            return PlayState.CONTINUE;
        }

        return PlayState.STOP;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.geoCache;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(IS_DORMANT, true);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putBoolean("IsDormant", this.isDormant());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        if (compound.contains("IsDormant")) {
            this.setDormant(compound.getBoolean("IsDormant"));
        }

        this.registerGoals();
    }

    public boolean isDormant() {
        return this.entityData.get(IS_DORMANT);
    }

    public void setDormant(boolean dormant) {
        this.entityData.set(IS_DORMANT, dormant);
        this.setNoAi(dormant);
    }
}
