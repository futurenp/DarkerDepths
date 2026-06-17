package com.naterbobber.darkerdepths.entities;

import com.naterbobber.darkerdepths.damage.DDDamageTypes;
import com.naterbobber.darkerdepths.entities.control.ConfigurableMoveControl;
import com.naterbobber.darkerdepths.entities.goals.AttackMemoryTargetGoal;
import com.naterbobber.darkerdepths.util.DDResourceKeys;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.*;
import software.bernie.geckolib.util.GeckoLibUtil;

public class VoidSoulKnightEntity extends VoidSoulMonster implements GeoEntity {
    private int attackTick;
    private int firstDamageDelay;
    private int secondDamageDelay;
    private boolean firstAttackDone;
    private Entity attackTarget;
    private int dormantCheckCooldown = 0;
    private static final int PERSISTENCE = 20 * 30; // tick * seconds
    private static final float REACH = 2.25F;

    protected static final RawAnimation WALK_ANIM = RawAnimation.begin().thenLoop("move.walk");
    protected static final RawAnimation ATTACK_ANIM = RawAnimation.begin().then("attack.swing", Animation.LoopType.PLAY_ONCE);
    protected static final RawAnimation IDLE_ANIM = RawAnimation.begin().thenLoop("idle");
    protected static final RawAnimation DORMANT_ANIM = RawAnimation.begin().thenLoop("dormant");

    private final AnimatableInstanceCache geoCache = GeckoLibUtil.createInstanceCache(this);

    private static final EntityDataAccessor<Boolean> IS_DORMANT =
            SynchedEntityData.defineId(VoidSoulKnightEntity.class, EntityDataSerializers.BOOLEAN);


    public VoidSoulKnightEntity(EntityType<? extends Monster> type, Level world) {
        super(type, world);
        this.xpReward = 30;
        this.moveControl = new ConfigurableMoveControl(this, 10.0F);
        this.setOrbHeight(1.5);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 80)
                .add(Attributes.MOVEMENT_SPEED, .17)
                .add(Attributes.ATTACK_DAMAGE, 10)
                .add(Attributes.ATTACK_KNOCKBACK, 2)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.85)
                .add(Attributes.FOLLOW_RANGE, 32);
    }

    @Override
    protected void registerGoals() {
        if (!this.isDormant()) {
            this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
            this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.3D, true));
            this.targetSelector.addGoal(3, new AttackMemoryTargetGoal<>(this, Player.class, PERSISTENCE, true));
            this.goalSelector.addGoal(7, new WaterAvoidingRandomStrollGoal(this, 1.2D));
        }
    }

    @Override
    public void aiStep() {
        super.aiStep();

        if (this.attackTick > 0) {
            this.attackTick--;

            if (this.attackTick == 0) {
                this.setAttacking(false);
            }
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
            this.attackTarget.hurt(DDDamageTypes.getDamageSource(level(), DDResourceKeys.DamageTypes.VOID_SOUL_DAMAGE), damage);
            this.firstAttackDone = true;
            return;
        }

        if(this.secondDamageDelay == 0) {
            if (this.distanceToSqr(this.attackTarget) < 12) {
                this.attackTarget.hurt(DDDamageTypes.getDamageSource(level(), DDResourceKeys.DamageTypes.VOID_SOUL_DAMAGE), damage);
            }
            this.attackTarget = null;
        }
    }

    @Override
    public void tick() {
        super.tick();
        summonVoidSoulParticles(0.8F, 2F);

        if (this.level().isClientSide) return;

        if(dormantCheckCooldown != 0) {
            this.dormantCheckCooldown--;
            return;
        }

        boolean isPeaceful = level().getDifficulty() == Difficulty.PEACEFUL;

        if(!this.isDormant()) {
            if(isPeaceful) {
                setDormant(true);
            }
            return;
        }

        this.setYBodyRot(this.getYRot());
        this.setYHeadRot(this.getYRot());

        if(isPeaceful) return;

        Player player = this.level().getNearestPlayer(this, 8);

        boolean hpChanged = this.getHealth() < this.getMaxHealth();

        if ((player != null && !player.isCreative())) {
            this.awakeFromDormant();
            this.setTarget(player);
            return;
        }

        if (hpChanged) {
            this.awakeFromDormant();
        }
    }

    private void awakeFromDormant() {
        this.setDormant(false);
        this.registerGoals();
        this.playSound(SoundEvents.IRON_GOLEM_REPAIR, 1.0F, 0.4F);
        this.dormantCheckCooldown = 20;
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
        if(this.getAttackTick() == 0) {
            this.setAttacking(true);
            this.stopTriggeredAnim("attack_controller", "attack");
            this.triggerAnim("attack_controller", "attack");

            this.attackTick = 50;
            this.firstDamageDelay = 10;
            this.firstAttackDone = false;
            this.secondDamageDelay = 38;

            if (entity instanceof LivingEntity) {
                this.attackTarget = (LivingEntity)entity;
            }

            this.level().broadcastEntityEvent(this, (byte) 4);

            return true;
        }
        return false;
    }

    protected AABB getAttackBoundingBox() {
        Entity entity = this.getVehicle();
        if (entity != null) {
            AABB aabb1 = entity.getBoundingBox();
            AABB aabb2 = this.getBoundingBox();
            return new AABB(Math.min(aabb2.minX, aabb1.minX), aabb2.minY, Math.min(aabb2.minZ, aabb1.minZ), Math.max(aabb2.maxX, aabb1.maxX), aabb2.maxY, Math.max(aabb2.maxZ, aabb1.maxZ));
        } else {
            return this.getBoundingBox().inflate(REACH, 0.0, REACH);
        }
    }

    @Override
    protected boolean shouldDespawnInPeaceful() {
        return false;
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.IRON_GOLEM_DEATH;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.IRON_GOLEM_HURT;
    }

    @Override
    public void registerControllers(final AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "movement_controller", 5, this::movementPredicate));
        controllers.add(new AnimationController<>(this, "attack_controller", 0, state -> PlayState.STOP)
                .triggerableAnim("attack", ATTACK_ANIM));
    }

    protected <E extends VoidSoulKnightEntity> PlayState movementPredicate(final AnimationState<E> event) {
        if (this.isDormant()) {
            return event.setAndContinue(DORMANT_ANIM);
        }

        if (event.isMoving()) {
            return event.setAndContinue(WALK_ANIM);
        }

        return event.setAndContinue(IDLE_ANIM);
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.geoCache;
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(IS_DORMANT, true);
    }

    public boolean isDormant() {
        return this.entityData.get(IS_DORMANT);
    }

    public void setDormant(boolean dormant) {
        this.entityData.set(IS_DORMANT, dormant);
        this.setNoAi(dormant);
    }

    public boolean removeWhenFarAway(double pDistanceToClosestPlayer) {
        return false;
    }
}
