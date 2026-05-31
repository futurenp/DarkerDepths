package com.naterbobber.darkerdepths.entities;

import com.naterbobber.darkerdepths.damage.DDDamageTypes;
import com.naterbobber.darkerdepths.entities.goals.AttackMemoryTargetGoal;
import com.naterbobber.darkerdepths.entities.goals.DashGoal;
import com.naterbobber.darkerdepths.entities.goals.IDashable;
import com.naterbobber.darkerdepths.init.DDMobEffects;
import com.naterbobber.darkerdepths.util.DDResourceKeys;
import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.*;
import software.bernie.geckolib.util.GeckoLibUtil;

public class BodySnatcherEntity extends VoidSoulMonster implements GeoEntity, IDashable {
    private int attackTick;
    private int damageDelay;
    private Entity attackTarget;
    private static final float REACH = 1.65F;

    private final AnimatableInstanceCache geoCache = GeckoLibUtil.createInstanceCache(this);
    protected static final RawAnimation ATTACK_ANIM = RawAnimation.begin().then("attack.swing", Animation.LoopType.PLAY_ONCE);
    protected static final RawAnimation IDLE_ANIM = RawAnimation.begin().thenLoop("idle");
    protected static final RawAnimation PRE_DASH = RawAnimation.begin().thenLoop("pre_dash");
    protected static final RawAnimation HURT = RawAnimation.begin().thenLoop("hurt");

    private static final EntityDataAccessor<Boolean> PREPARING_TO_DASH =
            SynchedEntityData.defineId(BodySnatcherEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> DASHING =
            SynchedEntityData.defineId(BodySnatcherEntity.class, EntityDataSerializers.BOOLEAN);


    public BodySnatcherEntity(EntityType<? extends VoidSoulMonster> type, Level level) {
        super(type, level);
        this.xpReward = 10;
        this.setOrbHeight(0.35);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 16.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.275D)
                .add(Attributes.ATTACK_SPEED, 2.4D)
                .add(Attributes.FOLLOW_RANGE, 24)
                .add(Attributes.ATTACK_DAMAGE, 3.0D);
    }

    @Override
    protected void registerGoals() {
        this.targetSelector.addGoal(3, new HurtByTargetGoal(this));
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.2D, true));
        this.targetSelector.addGoal(4, new AttackMemoryTargetGoal<>(this, Player.class, 100, true));
        this.goalSelector.addGoal(1, new DashGoal(
                this,
                2.5D,
                0.2D,
                10.0D,
                15,
                60,
                5
        ));
        this.goalSelector.addGoal(7, new WaterAvoidingRandomStrollGoal(this, 1D));
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

        if (this.damageDelay > 0) {
            this.damageDelay--;
        }

        if(this.attackTarget == null) return;

        if(!this.attackTarget.isAlive() || !this.isAlive()) {
            this.attackTarget = null;
            return;
        }

        if(this.damageDelay == 0) {
            if (this.distanceToSqr(this.attackTarget) < 4) {
                this.attackTarget.hurt(DDDamageTypes.getDamageSource(level(), DDResourceKeys.DamageTypes.VOID_SOUL_DAMAGE),
                        (float) this.getAttributeValue(Attributes.ATTACK_DAMAGE));
                if(this.attackTarget instanceof Player player) {
                    player.forceAddEffect(new MobEffectInstance(DDMobEffects.PARANOIA, 200, 0, false, false, true), this);
                }
            }

            this.attackTarget = null;
        }
    }

    @Override
    public void tick() {
        super.tick();
        summonVoidSoulParticles(0.6F, 1.1F);
    }

    @Override
    public boolean doHurtTarget(Entity entity) {
        if(this.attackTick == 0) {
            this.setAttacking(true);

            this.attackTick = 20;
            this.damageDelay = 10;

            if (entity instanceof LivingEntity) {
                this.attackTarget = entity;
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
    public boolean causeFallDamage(float pFallDistance, float pMultiplier, DamageSource pSource) {
        return false;
    }

    @Override
    public void registerControllers(final AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "walkingController", 5, this::predicate));
        controllers.add(new AnimationController<>(this, "attackingController", 0, this::attackPredicate));
        controllers.add(new AnimationController<>(this, "dashController", 0, this::dashPredicate));
    }

    protected <E extends BodySnatcherEntity> PlayState predicate(final AnimationState<E> event) {
        return event.setAndContinue(IDLE_ANIM);
    }

    protected <E extends BodySnatcherEntity> PlayState dashPredicate(final AnimationState<E> event) {
        if (this.isPreparingToDash()) {
            return event.setAndContinue(PRE_DASH);
        }

        event.getController().stop();
        return PlayState.STOP;
    }

    protected <E extends BodySnatcherEntity> PlayState attackPredicate(final AnimationState<E> event) {
        if (this.isAttacking()) {
            if (event.getController().getAnimationState().equals(AnimationController.State.STOPPED)) {
                event.getController().forceAnimationReset();
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
    protected SoundEvent getDeathSound() {
        return SoundEvents.VEX_DEATH;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource pDamageSource) {
        return SoundEvents.VEX_HURT;
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.VEX_AMBIENT;
    }

    @Override
    protected void playStepSound(BlockPos pPos, BlockState pState) {
    }

    @Override
    public void setPreparingToDash(boolean isPreparing) {
        this.entityData.set(PREPARING_TO_DASH, isPreparing);
    }

    @Override
    public void setDashing(boolean isDashing) {
        this.entityData.set(DASHING, isDashing);
    }

    public boolean isPreparingToDash() {
        return this.entityData.get(PREPARING_TO_DASH);
    }

    public boolean isDashing() {
        return this.entityData.get(DASHING);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(PREPARING_TO_DASH, false);
        builder.define(DASHING, false);
    }
}
