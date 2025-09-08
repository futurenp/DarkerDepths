package com.naterbobber.darkerdepths.entities;

import com.naterbobber.darkerdepths.entities.goals.ConfigurableRandomFlyingGoal;
import com.naterbobber.darkerdepths.init.DDBlocks;
import com.naterbobber.darkerdepths.init.DDItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class VoidSoulEntity extends PathfinderMob implements GeoEntity {
    private boolean isCaptured = false;
    private int lifetime = 20 * 60;

    protected static final RawAnimation IDLE_ANIM = RawAnimation.begin().thenLoop("idle");
    private final AnimatableInstanceCache geoCache = GeckoLibUtil.createInstanceCache(this);
    private static final EntityDataAccessor<Boolean> EXPIRES =
            SynchedEntityData.defineId(VoidSoulKnightEntity.class, EntityDataSerializers.BOOLEAN);


    public VoidSoulEntity(EntityType<? extends PathfinderMob> type, Level world) {
        super(type, world);
        this.moveControl = new FlyingMoveControl(this, 20, true);
        this.setNoGravity(true);
        setPersistenceRequired();
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.35F)
                .add(Attributes.FLYING_SPEED, 0.35F);
    }

    @Override
    protected void registerGoals() {
        //avoid doesnt work rn
        this.goalSelector.addGoal(1, new AvoidEntityGoal<>(this, Player.class, 8.0F, 1.2D, 1.5D));
        this.goalSelector.addGoal(2, new ConfigurableRandomFlyingGoal(this, 1.0D, 10));
    }

    @Override
    public void tick() {
        super.tick();
    }

    @Override
    public void aiStep() {
        super.aiStep();

        if(expires()) {
            if (this.lifetime > 0) {
                this.lifetime--;
            } else {
                kill();
            }
        }

    }

    @Override
    protected PathNavigation createNavigation(Level pLevel) {
        FlyingPathNavigation flyingpathnavigation = new FlyingPathNavigation(this, pLevel);
        flyingpathnavigation.setCanOpenDoors(false);
        flyingpathnavigation.setCanFloat(true);
        flyingpathnavigation.setCanPassDoors(true);
        return flyingpathnavigation;
    }

    @Override
    public InteractionResult mobInteract(Player pPlayer, InteractionHand pHand) {
        ItemStack itemStack = pPlayer.getItemInHand(pHand);

        if (itemStack.is(Items.GLASS_BOTTLE)) {
            this.isCaptured = true;
            this.kill();

            playSound(SoundEvents.BOTTLE_FILL_DRAGONBREATH);

            ItemStack voidSoulJarStack = new ItemStack(DDBlocks.VOID_SOUL_JAR.get());

            if (!pPlayer.getAbilities().instabuild) {
                itemStack.shrink(1);
            }

            if (itemStack.isEmpty()) {
                pPlayer.setItemInHand(pHand, voidSoulJarStack);
            } else if (!pPlayer.getInventory().add(voidSoulJarStack)) {
                pPlayer.drop(voidSoulJarStack, false);
            }

            return InteractionResult.sidedSuccess(this.level().isClientSide());
        }

        else if(itemStack.is(DDItems.AMBER.get()) && expires()) {
            setExpires(false);

            playSound(SoundEvents.AMETHYST_BLOCK_RESONATE);

            if(!this.level().isClientSide()) {
                ServerLevel serverLevel = (ServerLevel) this.level();
                ParticleOptions particle = ParticleTypes.WAX_ON;
                serverLevel.sendParticles(
                        particle,
                        this.getX(),
                        this.getY(0.5),
                        this.getZ(),
                        7,
                        this.getBbWidth() / 2.0,
                        this.getBbHeight() / 2.0,
                        this.getBbWidth() / 2.0,
                        0.05
                );
            }

            if (!pPlayer.getAbilities().instabuild) {
                itemStack.shrink(1);
            }

            return InteractionResult.sidedSuccess(this.level().isClientSide());
        }

        return super.mobInteract(pPlayer, pHand);
    }

    @Override
    public void die(DamageSource pDamageSource) {
        if (!this.level().isClientSide()) {
            ServerLevel serverLevel = (ServerLevel) this.level();
            if(!isCaptured) {
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
            } else {
                ParticleOptions particle = ParticleTypes.SMOKE;
                serverLevel.sendParticles(
                        particle,
                        this.getX(),
                        this.getY(0.5),
                        this.getZ(),
                        5,
                        this.getBbWidth() / 3.0,
                        this.getBbHeight() / 3.0,
                        this.getBbWidth() / 3.0,
                        0.05
                );
            }
        }
        this.remove(RemovalReason.KILLED);
        this.gameEvent(GameEvent.ENTITY_DIE);
    }

    @Override
    protected void checkFallDamage(double pY, boolean pOnGround, BlockState pState, BlockPos pPos) {
    }

    @Override
    protected boolean shouldDespawnInPeaceful() {
        return false;
    }

    @Override
    public boolean hurt(DamageSource pSource, float pAmount) {
        if (pSource.is(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
            return super.hurt(pSource, pAmount);
        }
        return false;
    }

    @Override
    public void handleDamageEvent(DamageSource pDamageSource) {}

    @Override
    public void knockback(double pStrength, double pX, double pZ) {}


    public boolean removeWhenFarAway(double pDistanceToClosestPlayer) {
        return false;
    }

    public boolean isOnFire() {
        return false;
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putBoolean("expires", this.expires());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        if (compound.contains("expires")) {
            this.setExpires(compound.getBoolean("expires"));
        }

        this.registerGoals();
    }

    public boolean expires() {
        return this.entityData.get(EXPIRES);
    }

    public void setExpires(boolean expires) {
        this.entityData.set(EXPIRES, expires);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(EXPIRES, true);
    }

    @Override
    public void registerControllers(final AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "walkingController", 5, this::predicate));
    }

    protected <E extends VoidSoulEntity> PlayState predicate(final AnimationState<E> event) {
        return event.setAndContinue(IDLE_ANIM);
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.geoCache;
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return null;
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource pDamageSource) {
        return SoundEvents.EMPTY;
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ALLAY_AMBIENT_WITHOUT_ITEM;
    }
}
