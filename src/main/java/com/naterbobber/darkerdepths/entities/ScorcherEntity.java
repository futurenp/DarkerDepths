package com.naterbobber.darkerdepths.entities;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import com.naterbobber.darkerdepths.client.screen_effects.ScorcherFlashHandler;
import com.naterbobber.darkerdepths.init.DDBlocks;
import com.naterbobber.darkerdepths.init.DDParticleTypes;
import com.naterbobber.darkerdepths.network.packets.ScorcherFlashPacket;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.Difficulty;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.network.PacketDistributor;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.*;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.util.GeckoLibUtil;

import javax.annotation.Nullable;
import java.util.Map;

public class ScorcherEntity extends Mob implements GeoEntity {
    private final AnimatableInstanceCache geoCache = GeckoLibUtil.createInstanceCache(this);
    private static final EntityDataAccessor<Boolean> DATA_STARED_AT = SynchedEntityData.defineId(ScorcherEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> DATA_TARGET_ID = SynchedEntityData.defineId(ScorcherEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> DATA_SHAKING = SynchedEntityData.defineId(ScorcherEntity.class, EntityDataSerializers.BOOLEAN);

    protected static final RawAnimation SHAKE_ANIM = RawAnimation.begin().thenLoop("attack");
    protected static final RawAnimation IDLE_ANIM = RawAnimation.begin().thenLoop("idle");

    public ScorcherEntity(EntityType<? extends Mob> entityType, Level level) {
        super(entityType, level);
        this.setNoGravity(true);
        this.xpReward = 25;
    }

    @Override
    public boolean isNoGravity() { return true; }

    @Override
    public int getMaxHeadXRot() { return 90; }

    @Override
    public int getMaxHeadYRot() { return 90; }

    @Override
    public boolean isPushable() { return false; }

    @Override
    public boolean isPushedByFluid(FluidType type) { return false; }

    @Override
    public boolean removeWhenFarAway(double distanceToClosestPlayer) { return false; }

    @Override
    public boolean canBeCollidedWith() { return false; }

    @Override
    protected void clampHeadRotationToBody() {}

    @Override
    protected Brain.Provider<ScorcherEntity> brainProvider() {
        return Brain.provider(
                ImmutableList.of(MemoryModuleType.ATTACK_TARGET),
                ImmutableList.of(SensorType.NEAREST_PLAYERS, SensorType.HURT_BY)
        );
    }

    @Override
    public SoundSource getSoundSource() {
        return SoundSource.HOSTILE;
    }

    @Override
    protected Brain<?> makeBrain(Dynamic<?> dynamic) {
        Brain<ScorcherEntity> brain = this.brainProvider().makeBrain(dynamic);

        brain.addActivity(Activity.CORE, ImmutableList.of(
                Pair.of(0, new ScorcherStareBehavior()),
                Pair.of(1, new ScorcherIdleLookBehavior())
        ));

        brain.setDefaultActivity(Activity.CORE);
        brain.useDefaultActivity();
        return brain;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Brain<ScorcherEntity> getBrain() {
        return (Brain<ScorcherEntity>) super.getBrain();
    }

    @Override
    protected void customServerAiStep() {
        ServerLevel serverlevel = (ServerLevel)this.level();
        serverlevel.getProfiler().push("scorcherBrain");
        this.getBrain().tick(serverlevel, this);
        serverlevel.getProfiler().pop();
        super.customServerAiStep();
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>(this, "baseController", 5, this::basePredicate));
        controllerRegistrar.add(new AnimationController<>(this, "shake_controller", state -> PlayState.STOP)
                .triggerableAnim("attack", SHAKE_ANIM));
    }

    protected <E extends ScorcherEntity> PlayState basePredicate(final AnimationState<E> event) {
        if (this.isShaking()) {
            return event.setAndContinue(SHAKE_ANIM);
        }
        return event.setAndContinue(IDLE_ANIM);
    }


    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return geoCache;
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DATA_STARED_AT, false);
        builder.define(DATA_SHAKING, false);
        builder.define(DATA_TARGET_ID, 0);
    }

    public void setScorcherTarget(LivingEntity target) {
        if (this.getTarget() != target) {
            this.setTarget(target);
        }
        if (!this.entityData.get(DATA_STARED_AT)) {
            this.entityData.set(DATA_STARED_AT, true);
        }
        if (!this.entityData.get(DATA_SHAKING)) {
            this.entityData.set(DATA_SHAKING, true);
        }
        if (this.entityData.get(DATA_TARGET_ID) != target.getId()) {
            this.entityData.set(DATA_TARGET_ID, target.getId());
        }
    }

    public void clearScorcherTarget() {
        if (this.getTarget() != null) {
            this.setTarget(null);
        }
        if (this.entityData.get(DATA_STARED_AT)) {
            this.entityData.set(DATA_STARED_AT, false);
        }
        if (this.entityData.get(DATA_SHAKING)) {
            this.entityData.set(DATA_SHAKING, false);
        }
        if (this.entityData.get(DATA_TARGET_ID) != 0) {
            this.entityData.set(DATA_TARGET_ID, 0);
        }
    }

    private boolean isShaking() {
        return this.entityData.get(DATA_SHAKING);
    }

    public boolean hasBeamTarget() {
        return this.entityData.get(DATA_TARGET_ID) != 0;
    }

    @Nullable
    public LivingEntity getBeamTarget() {
        if (!this.hasBeamTarget()) {
            return null;
        } else if (this.level().isClientSide) {
            Entity target = this.level().getEntity(this.entityData.get(DATA_TARGET_ID));
            return target instanceof LivingEntity living ? living : null;
        } else {
            return this.getTarget();
        }
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 40.0D)
                .add(Attributes.FOLLOW_RANGE, 48.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1.0D);
    }

    @Override
    public boolean doHurtTarget(Entity target) {
        Vec3 initialMotion = target.getDeltaMovement();
        boolean didHurt = super.doHurtTarget(target);

        if (didHurt) {
            target.setDeltaMovement(initialMotion);
            target.hasImpulse = false;
        }
        return didHurt;
    }

    @Override
    public void die(DamageSource damageSource) {
        var state = this.level().getBlockState(getOnPos().above());
        if(state.is(DDBlocks.SCORCHER_LIGHT_BLOCK.get())) {
            this.level().setBlock(getOnPos().above(), Blocks.AIR.defaultBlockState(), 3);
        }

        if (this.level() instanceof ServerLevel serverLevel) {
            double centerX = this.getX();
            double centerY = this.getY() + (this.getBbHeight() / 2.0);
            double centerZ = this.getZ();

            double spreadX = this.getBbWidth() / 4.0;
            double spreadY = this.getBbHeight() / 1.0;
            double spreadZ = this.getBbWidth() / 4.0;

            serverLevel.sendParticles(DDParticleTypes.SCORCHER_SEARCHLIGHT.get(),
                    centerX, centerY, centerZ,
                    25,
                    spreadX, spreadY, spreadZ,
                    0.1D);

            serverLevel.sendParticles(ParticleTypes.LARGE_SMOKE,
                    centerX, centerY, centerZ,
                    25,
                    spreadX, spreadY, spreadZ,
                    0.07D);
        }

        super.die(damageSource);
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (source.is(net.minecraft.tags.DamageTypeTags.IS_PROJECTILE)) {
            amount *= 0.5F;
        }

        return super.hurt(source, amount);
    }

    @Override
    protected @org.jetbrains.annotations.Nullable SoundEvent getAmbientSound() {
        return SoundEvents.BLAZE_AMBIENT;
    }

    @Override
    protected @org.jetbrains.annotations.Nullable SoundEvent getHurtSound(DamageSource damageSource) {
        return SoundEvents.BLAZE_HURT;
    }

    @Override
    protected @org.jetbrains.annotations.Nullable SoundEvent getDeathSound() {
        return SoundEvents.BLAZE_DEATH;
    }

    @Override
    public float getVoicePitch() {
        return 0.5F;
    }

    @Override
    protected float getSoundVolume() {
        return 3.25F;
    }

    @Override
    public void tick() {
        super.tick();

        this.applyBeamPushback();

        if (!this.level().isClientSide()) {

            this.applyFlashEffect();

            if (this.isAlive() && this.hasBeamTarget()) {
                LivingEntity target = this.getBeamTarget();
                if (target != null && this.tickCount % 20 == 0) {
                    this.level().playSound(null, target.blockPosition(), net.minecraft.sounds.SoundEvents.GUARDIAN_ATTACK, net.minecraft.sounds.SoundSource.HOSTILE, 1.5F, 0.8F + this.random.nextFloat() * 0.2F);
                }
            }

            this.setDeltaMovement(this.getDeltaMovement().multiply(1.0, 0.0, 1.0));

            if (this.isAlive() && this.tickCount % 2 == 0) {
                spawnSearchlightParticles();
            }

            var state = this.level().getBlockState(getOnPos().above());
            if((state.is(BlockTags.REPLACEABLE) || state.is(BlockTags.AIR) && !state.is(DDBlocks.SCORCHER_LIGHT_BLOCK.get())) && this.isAlive()) {
                var lightState = DDBlocks.SCORCHER_LIGHT_BLOCK.get().defaultBlockState().setValue(BlockStateProperties.LEVEL, 10);
                this.level().setBlock(getOnPos().above(), lightState, 3);
            }
        }
    }

    @Override
    public void awardKillScore(Entity killed, int score, DamageSource damageSource) {
        super.awardKillScore(killed, score, damageSource);

        if (killed instanceof LivingEntity victim && this.level() instanceof ServerLevel level) {
            BlockPos pos = victim.blockPosition();
            BlockPos groundPos = null;

            for (int i = 0; i < 3; i++) {
                BlockPos checkPos = pos.below(i);
                if (level.getBlockState(checkPos).isFaceSturdy(level, checkPos, Direction.UP)) {
                    groundPos = checkPos;
                    break;
                }
            }

            if (groundPos != null) {
                BlockPos placePos = groundPos.above();
                BlockState currentState = level.getBlockState(placePos);

                if (victim.getMaxHealth() > 20.0F) {
                    BlockPos abovePos = placePos.above();
                    BlockState aboveState = level.getBlockState(abovePos);

                    if (currentState.canBeReplaced() && aboveState.canBeReplaced()) {
                        level.setBlock(placePos, DDBlocks.SCORCHED_REMAINS_BLOCK.get().defaultBlockState(), 3);
                        level.setBlock(abovePos, DDBlocks.SCORCHED_REMAINS.get().defaultBlockState(), 3);
                    }
                } else {
                    if (currentState.canBeReplaced()) {
                        level.setBlock(placePos, DDBlocks.SCORCHED_REMAINS.get().defaultBlockState(), 3);
                    }
                }
            }
        }
    }

    private void applyFlashEffect() {
        if (this.level().isClientSide
                || !this.isAlive()
                || !this.hasBeamTarget()
                || this.level().getDifficulty() == Difficulty.PEACEFUL
                || ScorcherFlashHandler.isFlashed()) {
            return;
        }

        LivingEntity target = this.getBeamTarget();

        if(target != null) {
            if(distanceToSqr(target) < 7 * 7) {
                return;
            }
        }

        if (target instanceof Player player) {
            Vec3 playerLook = player.getViewVector(1.0F).normalize();
            Vec3 toScorcher = this.getEyePosition().subtract(player.getEyePosition());

            double distance = toScorcher.length();
            toScorcher = toScorcher.normalize();

            double dotProduct = playerLook.dot(toScorcher);
            double threshold = 1.0D - (0.1D / Math.max(distance, 1.0D));

            if (dotProduct > threshold && player.hasLineOfSight(this)) {

                if (player instanceof ServerPlayer serverPlayer) {
                    PacketDistributor.sendToPlayer(
                            serverPlayer,
                            new ScorcherFlashPacket(40)
                    );
                }

            }
        }
    }

    private void applyBeamPushback() {
        if (!this.isAlive() || !this.hasBeamTarget() || this.level().getDifficulty() == Difficulty.PEACEFUL) return;

        LivingEntity target = this.getBeamTarget();
        if (target == null) return;

        double distSqr = this.distanceToSqr(target);

        double pushbackDistance = 32.0D;
        double innerDistance = 5.0D;

        if (distSqr <= pushbackDistance * pushbackDistance) {
            double dist = Math.sqrt(distSqr);

            double forceMultiplier = 1.0D;
            if (dist > innerDistance) {
                forceMultiplier = (pushbackDistance - dist) / (pushbackDistance - innerDistance);
            }

            double currentForce = 0.055D * forceMultiplier;

            Vec3 dirToScorcher = this.position().subtract(target.position()).normalize();

            target.push(dirToScorcher.x * -currentForce, 0.0D, dirToScorcher.z * -currentForce);

            if (!this.level().isClientSide) {
                target.hasImpulse = true;
            }
        }
    }

    private void spawnSearchlightParticles() {
        if (!(this.level() instanceof ServerLevel serverLevel)) return;

        Vec3 start = this.getEyePosition();
        Vec3 look = this.getViewVector(1.0F);
        double maxDist = 64.0;

        HitResult hit = this.level().clip(new ClipContext(start, start.add(look.scale(maxDist)), ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, this));
        double actualDist = hit.getType() == HitResult.Type.MISS ? maxDist : hit.getLocation().distanceTo(start);

        int numParticles = Math.min((int) (actualDist * 1.5), 40);

        Vec3 up = new Vec3(0, 1, 0);
        if (Math.abs(look.y) > 0.99) {
            up = new Vec3(1, 0, 0);
        }
        Vec3 right = look.cross(up).normalize();
        Vec3 upOrth = right.cross(look).normalize();

        for (int i = 0; i < numParticles; i++) {
            double d = this.random.nextDouble() * actualDist;
            double angle = this.random.nextDouble() * Math.PI * 2;

            Vec3 trajectory = look.add(right.scale(Math.cos(angle) * 0.10)).add(upOrth.scale(Math.sin(angle) * 0.10)).normalize();
            Vec3 particlePos = start.add(trajectory.scale(d));

            for (ServerPlayer player : serverLevel.players()) {
                serverLevel.sendParticles(player, DDParticleTypes.SCORCHER_SEARCHLIGHT.get(), true,
                        particlePos.x, particlePos.y + 0.15, particlePos.z,
                        0, trajectory.x, trajectory.y, trajectory.z, 0.25);
            }
        }
    }

    static class ScorcherIdleLookBehavior extends Behavior<ScorcherEntity> {
        private float currentAngle;
        private int pauseTicks;
        private int panDirection = 1;

        public ScorcherIdleLookBehavior() {
            super(Map.of());
        }

        @Override
        protected boolean checkExtraStartConditions(ServerLevel level, ScorcherEntity entity) {
            return entity.getTarget() == null;
        }

        @Override
        protected void start(ServerLevel level, ScorcherEntity entity, long gameTime) {
            this.currentAngle = (float) Math.toRadians(entity.getYHeadRot() + 90.0f);
            this.pauseTicks = 20 + level.random.nextInt(40);
        }

        @Override
        protected boolean canStillUse(ServerLevel level, ScorcherEntity entity, long gameTime) {
            return entity.getTarget() == null;
        }

        @Override
        protected void tick(ServerLevel level, ScorcherEntity entity, long gameTime) {
            if (this.pauseTicks > 0) {
                this.pauseTicks--;
            } else {
                this.currentAngle += 0.03f * this.panDirection;

                if (level.random.nextFloat() < 0.01f) {
                    this.pauseTicks = 20 + level.random.nextInt(60);
                    if (level.random.nextBoolean()) {
                        this.panDirection *= -1;
                    }
                }
            }

            double radius = 10.0;
            double dx = Math.cos(this.currentAngle) * radius;
            double dz = Math.sin(this.currentAngle) * radius;
            double dy = -2.0;

            Vec3 lookPos = entity.getEyePosition().add(dx, dy, dz);
            entity.getLookControl().setLookAt(lookPos.x, lookPos.y, lookPos.z, 10.0F, 10.0F);
        }
    }

    static class ScorcherStareBehavior extends Behavior<ScorcherEntity> {
        private LivingEntity target;
        private int losTicks = 0;
        private int losLostTicks = 0;
        private Vec3 lastKnownPos = null;

        private final TargetingConditions playerTargetConditions = TargetingConditions.forNonCombat().range(48.0).ignoreLineOfSight().selector((entity) -> {
            if (entity instanceof Player player) {
                return !player.isCreative() && !player.isSpectator() && player.level().getDifficulty() != Difficulty.PEACEFUL;
            }
            return true;
        });

        public ScorcherStareBehavior() {
            super(Map.of(), 320);
        }

        @Override
        protected boolean checkExtraStartConditions(ServerLevel level, ScorcherEntity entity) {
            return true;
        }

        @Override
        protected boolean canStillUse(ServerLevel level, ScorcherEntity entity, long gameTime) {
            return true;
        }

        @Override
        protected void tick(ServerLevel level, ScorcherEntity entity, long gameTime) {
            if (!isValidTarget(entity, this.target)) {
                findNewTarget(level, entity);
            }

            if (this.target == null) {
                entity.clearScorcherTarget();
                return;
            }

            if (hasForgivingLineOfSight(entity, this.target)) {
                handleLineOfSight(entity);
            } else {
                handleLostLineOfSight(entity);
            }
        }

        private boolean isValidTarget(ScorcherEntity entity, LivingEntity potentialTarget) {
            if (potentialTarget == null || !potentialTarget.isAlive() || potentialTarget.level().getDifficulty() == Difficulty.PEACEFUL) return false;
            if (potentialTarget instanceof Player player && (player.isSpectator() || player.isCreative())) return false;
            if (potentialTarget instanceof ScorcherEntity) return false;
            if (entity.distanceToSqr(potentialTarget) > 48 * 48) return false;
            return true;
        }

        private void findNewTarget(ServerLevel level, ScorcherEntity entity) {
            LivingEntity attacker = entity.getLastHurtByMob();

            if (isValidTarget(entity, attacker) && hasForgivingLineOfSight(entity, attacker)) {
                this.target = attacker;
            } else {
                this.target = level.getNearestPlayer(this.playerTargetConditions, entity);
            }

            this.losTicks = 0;
            this.losLostTicks = 0;
            this.lastKnownPos = null;

            if (this.target != null && hasForgivingLineOfSight(entity, this.target)) {
                entity.setScorcherTarget(this.target);
            } else {
                this.target = null;
                entity.clearScorcherTarget();
            }
        }

        private void handleLineOfSight(ScorcherEntity entity) {
            this.losLostTicks = 0;
            this.losTicks++;
            this.lastKnownPos = this.target.getEyePosition();

            entity.setScorcherTarget(this.target);

            float turnSpeed = entity.distanceToSqr(this.target) < 100.0 ? 120.0F : 30.0F;
            entity.getLookControl().setLookAt(this.target, turnSpeed, turnSpeed);

            this.applyBeamDamage(entity);
        }

        private void applyBeamDamage(ScorcherEntity entity) {
            if (this.losTicks % 20 != 0) return;

            int secondsInLos = this.losTicks / 20;
            float damage = secondsInLos - 1f;

            if (damage <= 0) return;

            this.target.invulnerableTime = 0;

            if (this.target.isBlocking()) {
                ItemStack shield = this.target.getUseItem();
                int shieldDamage = (int) (damage * 4);

                InteractionHand hand = this.target.getUsedItemHand();
                EquipmentSlot slot = hand == InteractionHand.MAIN_HAND ?
                        EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND;

                shield.hurtAndBreak(shieldDamage, this.target, slot);

                this.target.level().playSound(null, this.target.blockPosition(), SoundEvents.SHIELD_BLOCK, SoundSource.PLAYERS, 1.0F, 0.8F + entity.getRandom().nextFloat() * 0.4F);
            } else {
                this.target.hurt(entity.damageSources().mobAttack(entity), damage);
            }
        }

        private void handleLostLineOfSight(ScorcherEntity entity) {
            this.losLostTicks++;
            this.losTicks = 0;

            if (this.lastKnownPos != null) {
                entity.getLookControl().setLookAt(this.lastKnownPos.x, this.lastKnownPos.y, this.lastKnownPos.z, 30.0F, 30.0F);
            }

            if (this.losLostTicks > 20) {
                // If it loses line of sight on its attacker long enough, it forgets them
                if (this.target == entity.getLastHurtByMob()) {
                    entity.setLastHurtByMob(null);
                }
                this.target = null;
                entity.clearScorcherTarget();
            } else {
                entity.entityData.set(DATA_SHAKING, false);
            }
        }

        private boolean hasForgivingLineOfSight(ScorcherEntity entity, LivingEntity target) {
            if (entity.getSensing().hasLineOfSight(target)) return true;

            Vec3 start = entity.getEyePosition();
            Vec3 chestPos = target.position().add(0, target.getBbHeight() * 0.5, 0);
            if (entity.level().clip(new ClipContext(start, chestPos, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, entity)).getType() == HitResult.Type.MISS) {
                return true;
            }

            Vec3 feetPos = target.position().add(0, 0.1, 0);
            if (entity.level().clip(new ClipContext(start, feetPos, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, entity)).getType() == HitResult.Type.MISS) {
                return true;
            }

            return false;
        }

        @Override
        protected void stop(ServerLevel level, ScorcherEntity entity, long gameTime) {
            this.target = null;
            entity.clearScorcherTarget();
        }
    }
}