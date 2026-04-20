package com.naterbobber.darkerdepths.entities;

import com.naterbobber.darkerdepths.DarkerDepths;
import com.naterbobber.darkerdepths.entities.goals.AttackMemoryTargetGoal;
import com.naterbobber.darkerdepths.entities.control.ConfigurableMoveControl;
import com.naterbobber.darkerdepths.init.DDSoundEvents;
import com.naterbobber.darkerdepths.util.DDTags;
import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.MoveTowardsTargetGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ambient.Bat;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.*;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class GlowshroomMonsterEntity extends Monster implements GeoEntity {
    private int damageDelay;
    private Entity attackTarget;
    private static final float REACH = 4.0F;

    private final AnimatableInstanceCache geoCache = GeckoLibUtil.createInstanceCache(this);
    protected static final RawAnimation ATTACK_SLAM = RawAnimation.begin().then("attack.slam", Animation.LoopType.PLAY_ONCE);
    protected static final RawAnimation IDLE = RawAnimation.begin().thenLoop("idle");
    protected static final RawAnimation ROAR = RawAnimation.begin().thenLoop("roar");
    protected static final RawAnimation WALK = RawAnimation.begin().thenLoop("move.walk");
    protected static final RawAnimation RUN = RawAnimation.begin().thenLoop("move.run");


    private static final EntityDataAccessor<Boolean> ATTACKING =
            SynchedEntityData.defineId(GlowshroomMonsterEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> ATTACK_TICK =
            SynchedEntityData.defineId(GlowshroomMonsterEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> HAS_TARGET =
            SynchedEntityData.defineId(GlowshroomMonsterEntity.class, EntityDataSerializers.BOOLEAN);


    public GlowshroomMonsterEntity(EntityType<? extends Monster> type, Level world) {
        super(type, world);
        this.xpReward = 20;
        this.moveControl = new ConfigurableMoveControl(this, 12.0F);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 40)
                .add(Attributes.MOVEMENT_SPEED, 0.15)
                .add(Attributes.ATTACK_KNOCKBACK, 2.0)
                .add(Attributes.ATTACK_DAMAGE, 12)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.75)
                .add(Attributes.STEP_HEIGHT, 1.2)
                .add(Attributes.FOLLOW_RANGE, 32)
                .add(Attributes.WATER_MOVEMENT_EFFICIENCY, 1.5);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 2.0, true));
        this.goalSelector.addGoal(3, new MoveTowardsTargetGoal(this, 2.0, 32.0F));
        this.goalSelector.addGoal(7, new WaterAvoidingRandomStrollGoal(this, 1));
        this.targetSelector.addGoal(2, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(4, new AttackMemoryTargetGoal<>(this, Player.class, 300, true));
        this.targetSelector.addGoal(5, new NearestAttackableTargetGoal<>(
                this,
                LivingEntity.class,
                10,
                false,
                false,
                (entity) -> entity.getType().is(DDTags.EntityTypes.GLOWSHROOM_MONSTER_TARGET)
        ));
    }

    @Override
    public void aiStep() {
        super.aiStep();

        if(level().isClientSide) {
            return;
        }

        if (!this.level().isClientSide && this.isAlive() && this.tickCount % 20 == 0) {
            this.heal(1.0F);
        }

        setHasTarget(getTarget() != null);

        if (this.getAttackTick() > 0) {
            this.setAttackTick(this.getAttackTick() - 1);

            if (this.getAttackTick() == 0) {
                this.setAttacking(false);
            }
        }

        if (this.damageDelay > 0) {
            this.damageDelay--;
        }

        if(this.attackTarget == null) {
            return;
        }

        if(!this.attackTarget.isAlive() || !this.isAlive()) {
            this.attackTarget = null;
            return;
        }

        if(this.damageDelay == 0) {
            if (this.distanceToSqr(this.attackTarget) < 16) {
                this.attackTarget.hurt(this.level().damageSources().mobAttack(this), (float) this.getAttributeValue(Attributes.ATTACK_DAMAGE));
                this.playSound(SoundEvents.GENERIC_EXPLODE.value(), 1.6f, 1.5f);
            }

            this.attackTarget = null;
        }
    }

    @Override
    public void handleEntityEvent(byte id) {
        if (id == 4) {
            this.playSound(SoundEvents.RAVAGER_ATTACK, 1.0F, 1.5F);
        } else {
            super.handleEntityEvent(id);
        }
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if(source.getEntity() != null) {
            if(source.getEntity().getType().is(EntityTypeTags.UNDEAD)) {
                amount /= 2;
            }
        }
        return super.hurt(source, amount);
    }

    @Override
    public void heal(float healAmount) {
        super.heal(healAmount);
    }

    @Override
    public boolean doHurtTarget(Entity entity) {
        if(this.getAttackTick() == 0) {
            this.setAttacking(true);
            this.triggerAnim("Attack", ATTACK_SLAM.toString());

            this.setAttackTick(50);
            this.damageDelay = 24;

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

    public static boolean checkMonsterSpawnRules(EntityType<? extends Monster> type, ServerLevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
        return level.getDifficulty() != Difficulty.PEACEFUL && (MobSpawnType.ignoresLightRequirements(spawnType) || isDarkEnoughToSpawn(level, pos, random)) && checkMobSpawnRules(type, level, spawnType, pos, random);
    }

    public static boolean isDarkEnoughToSpawn(ServerLevelAccessor level, BlockPos pos, RandomSource random) {
        if (level.getBrightness(LightLayer.SKY, pos) > random.nextInt(32)) {
            return false;
        } else {
            DimensionType dimensiontype = level.dimensionType();
            int i = 5;
            if (level.getBrightness(LightLayer.BLOCK, pos) > i) {
                return false;
            } else {
                int j = level.getLevel().isThundering() ? level.getMaxLocalRawBrightness(pos, 10) : level.getMaxLocalRawBrightness(pos);
                return j <= dimensiontype.monsterSpawnLightTest().sample(random);
            }
        }
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

    @Override
    public void registerControllers(final AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "Walk/Run/Idle", 0, this::idleRunWalkPredicate));
        controllers.add(new AnimationController<>(this, "Attack", state -> PlayState.STOP)
                .triggerableAnim(ATTACK_SLAM.toString(), ATTACK_SLAM));
    }

    protected PlayState idleRunWalkPredicate(final AnimationState<GlowshroomMonsterEntity> event) {
        if (!event.isMoving()) {
            return event.setAndContinue(IDLE);
        }
        if(this.hasTarget()) {
            return event.setAndContinue(RUN);
        } else {
            return event.setAndContinue(WALK);
        }
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.geoCache;
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(ATTACKING, false);
        builder.define(ATTACK_TICK, 0);
        builder.define(HAS_TARGET, false);
    }

    public int getAttackTick() { return this.entityData.get(ATTACK_TICK); }
    public void setAttackTick(int attackTick) {
        this.entityData.set(ATTACK_TICK, attackTick);
    }

    @Deprecated
    public boolean isAttacking() {
        return this.entityData.get(ATTACKING);
    }

    public void setAttacking(boolean attacking) {
        this.entityData.set(ATTACKING, attacking);
    }

    public boolean hasTarget() {
        return this.entityData.get(HAS_TARGET);
    }

    public void setHasTarget(boolean value) {
        this.entityData.set(HAS_TARGET, value);
    }
}