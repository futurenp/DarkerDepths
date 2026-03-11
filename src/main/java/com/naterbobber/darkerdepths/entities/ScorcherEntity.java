package com.naterbobber.darkerdepths.entities;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
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
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

import javax.annotation.Nullable;
import java.util.Map;

public class ScorcherEntity extends Mob implements GeoEntity {
    private final AnimatableInstanceCache geoCache = GeckoLibUtil.createInstanceCache(this);
    private static final EntityDataAccessor<Boolean> DATA_STARED_AT = SynchedEntityData.defineId(ScorcherEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> DATA_TARGET_ID = SynchedEntityData.defineId(ScorcherEntity.class, EntityDataSerializers.INT);

    public boolean hasGivenItem = false;

    public ScorcherEntity(EntityType<? extends Mob> entityType, Level level) {
        super(entityType, level);
        this.setNoGravity(true);
    }

    // Unconditionally strips gravity from the entity's physics calculations
    @Override
    public boolean isNoGravity() {
        return true;
    }

    // Allows the mob to look straight up and straight down
    @Override
    public int getMaxHeadXRot() {
        return 90;
    }

    // Allows the mob to turn its head a full 90 degrees left or right
    @Override
    public int getMaxHeadYRot() {
        return 90;
    }

    // --- BRAIN AI SETUP ---
    @Override
    protected Brain.Provider<ScorcherEntity> brainProvider() {
        return Brain.provider(
                ImmutableList.of(MemoryModuleType.ATTACK_TARGET),
                ImmutableList.of(SensorType.NEAREST_PLAYERS)
        );
    }

    @Override
    protected Brain<?> makeBrain(Dynamic<?> dynamic) {
        Brain<ScorcherEntity> brain = this.brainProvider().makeBrain(dynamic);

        brain.addActivity(Activity.CORE, ImmutableList.of(
                Pair.of(0, new ScorcherStareBehavior()),
                Pair.of(1, new ScorcherIdleLookBehavior()) // Added idle scanning behavior
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
    public boolean removeWhenFarAway(double distanceToClosestPlayer) {
        return false;
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
    protected void registerGoals() {
        // COMPLETELY REMOVED:
        // We cannot have the old Goal system running LookAt goals, otherwise it will
        // fight the new Brain AI behaviors for control of the head!
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 20.0D)
                .add(Attributes.FOLLOW_RANGE, 48.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1.0D); // Grants 100% knockback immunity
    }

    @Override
    public void setTarget(@Nullable LivingEntity livingEntity) {
        super.setTarget(livingEntity);
        if (livingEntity == null) {
            this.entityData.set(DATA_STARED_AT, false);
        }
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {}

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return geoCache;
    }

    public void setBeingStaredAt() {
        this.entityData.set(DATA_STARED_AT, true);
    }

    public void setBeamTarget(int entityId) {
        this.entityData.set(DATA_TARGET_ID, entityId);
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

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DATA_STARED_AT, false);
        builder.define(DATA_TARGET_ID, 0);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putBoolean("HasGivenItem", this.hasGivenItem);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.hasGivenItem = compound.getBoolean("HasGivenItem");
    }

    @Override
    public void tick() {
        super.tick();

        if (!this.level().isClientSide()) {
            // Hard-lock the Y velocity to 0 to prevent drifting or standard fall gravity pathing
            this.setDeltaMovement(this.getDeltaMovement().multiply(1.0, 0.0, 1.0));

            if (this.isAlive() && this.tickCount % 2 == 0) {
                spawnSearchlightParticles();
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
                serverLevel.sendParticles(player, ParticleTypes.FLAME, true,
                        particlePos.x, particlePos.y, particlePos.z,
                        0, trajectory.x, trajectory.y, trajectory.z, 0.15);
            }
        }
    }

    // --- BRAIN BEHAVIORS ---

    // 1. Idle Scanning Behavior
    static class ScorcherIdleLookBehavior extends Behavior<ScorcherEntity> {
        private float currentAngle;
        private int pauseTicks;
        private int panDirection = 1;

        public ScorcherIdleLookBehavior() {
            super(Map.of());
        }

        @Override
        protected boolean checkExtraStartConditions(ServerLevel level, ScorcherEntity entity) {
            // Only idle look if no target is acquired
            return entity.getTarget() == null;
        }

        @Override
        protected void start(ServerLevel level, ScorcherEntity entity, long gameTime) {
            // Initialize the angle to roughly where the head is currently facing
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
                // Halting behavior: stay focused on the current spot
                this.pauseTicks--;
            } else {
                // Panning behavior: smoothly rotate the target angle
                this.currentAngle += 0.03f * this.panDirection;

                // 1% chance each tick to suddenly "decide" to stop and inspect a spot
                if (level.random.nextFloat() < 0.01f) {
                    this.pauseTicks = 20 + level.random.nextInt(60); // Halt for 1 to 4 seconds
                    if (level.random.nextBoolean()) {
                        this.panDirection *= -1; // 50% chance to reverse sweeping direction after halting
                    }
                }
            }

            double radius = 10.0;
            double dx = Math.cos(this.currentAngle) * radius;
            double dz = Math.sin(this.currentAngle) * radius;
            double dy = -2.0; // Biased downwards

            Vec3 lookPos = entity.getEyePosition().add(dx, dy, dz);

            // Slower look speed (10.0F) so the physical head smoothly drags behind the target point
            entity.getLookControl().setLookAt(lookPos.x, lookPos.y, lookPos.z, 10.0F, 10.0F);
        }
    }

    @Override
    protected void clampHeadRotationToBody() {
    }

    // 2. Aggressive Stare Behavior
    static class ScorcherStareBehavior extends Behavior<ScorcherEntity> {
        private Player target;
        private int losTicks = 0;
        private int losLostTicks = 0;
        private Vec3 lastKnownPos = null;
        // Added selector to strictly filter out creative and spectator players
        private final TargetingConditions targetConditions = TargetingConditions.forNonCombat().range(48.0).ignoreLineOfSight().selector((entity) -> {
            if (entity instanceof Player player) {
                return !player.isCreative() && !player.isSpectator();
            }
            return true;
        });

        public ScorcherStareBehavior() {
            super(Map.of(), 220);
        }

        @Override
        protected boolean checkExtraStartConditions(ServerLevel level, ScorcherEntity entity) {
            return true;
        }

        @Override
        protected void start(ServerLevel level, ScorcherEntity entity, long gameTime) {}

        @Override
        protected boolean canStillUse(ServerLevel level, ScorcherEntity entity, long gameTime) {
            return true;
        }

        // Custom, more forgiving Line of Sight check so it doesn't lose track of the player easily
        private boolean hasForgivingLineOfSight(ScorcherEntity entity, Player target) {
            if (entity.getSensing().hasLineOfSight(target)) return true;

            // Check chest (center of mass)
            Vec3 start = entity.getEyePosition();
            Vec3 chestPos = target.position().add(0, target.getBbHeight() * 0.5, 0);
            if (entity.level().clip(new ClipContext(start, chestPos, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, entity)).getType() == HitResult.Type.MISS) {
                return true;
            }

            // Check feet
            Vec3 feetPos = target.position().add(0, 0.1, 0);
            if (entity.level().clip(new ClipContext(start, feetPos, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, entity)).getType() == HitResult.Type.MISS) {
                return true;
            }

            return false;
        }

        @Override
        protected void tick(ServerLevel level, ScorcherEntity entity, long gameTime) {
            // Target Acquisition - explicit check to drop targets that switch to Creative mode
            if (this.target == null || !this.target.isAlive() || entity.distanceToSqr(this.target) > 48 * 48 || this.target.isSpectator() || this.target.isCreative()) {
                this.target = level.getNearestPlayer(this.targetConditions, entity);
                this.losTicks = 0;
                this.losLostTicks = 0;
                this.lastKnownPos = null;

                // CRITICAL FIX: We must inform the Mob entity of the target so that
                // the ScorcherIdleLookBehavior knows to abort and stop spinning the head!
                if (this.target != null) {
                    entity.setBeingStaredAt();
                    entity.setBeamTarget(this.target.getId());
                    entity.setTarget(this.target);
                } else {
                    entity.setBeamTarget(0);
                    entity.setTarget(null);
                }
            }

            // Logic execution while engaged
            if (this.target != null) {
                if (hasForgivingLineOfSight(entity, this.target)) {
                    this.losLostTicks = 0;
                    this.losTicks++;
                    this.lastKnownPos = this.target.getEyePosition();

                    // If the player is within 10 blocks (100 distance sqr), the eye speeds up massively to keep track!
                    float turnSpeed = entity.distanceToSqr(this.target) < 100.0 ? 120.0F : 30.0F;
                    entity.getLookControl().setLookAt(this.target, turnSpeed, turnSpeed);

                    // --- CONTINUOUS RAMPING DAMAGE LOGIC ---
                    if (this.losTicks % 20 == 0) {
                        int secondsInLos = this.losTicks / 20;
                        float damage = secondsInLos - 1f;

                        if (damage > 0) {
                            // Strip invulnerability frames so the damage ticks are guaranteed to register
                            this.target.invulnerableTime = 0;

                            this.target.hurt(entity.damageSources().onFire(), damage);
                        }
                    }

                } else {
                    // Line of Sight Broken
                    this.losLostTicks++;
                    this.losTicks = 0;

                    if (this.lastKnownPos != null) {
                        entity.getLookControl().setLookAt(this.lastKnownPos.x, this.lastKnownPos.y, this.lastKnownPos.z, 30.0F, 30.0F);
                    }

                    if (this.losLostTicks > 40) {
                        this.target = null;
                        entity.setBeamTarget(0);
                        entity.setTarget(null);
                    }
                }
            }
        }

        @Override
        protected void stop(ServerLevel level, ScorcherEntity entity, long gameTime) {
            this.target = null;
            entity.setBeamTarget(0);
            entity.setTarget(null);
        }
    }
}