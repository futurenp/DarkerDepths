package com.naterbobber.darkerdepths.entities;

import com.naterbobber.darkerdepths.init.DDBlocks;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
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
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomFlyingGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class VoidSoulEntity extends PathfinderMob implements GeoEntity {
    private boolean isCaptured = false;
    private int lifetime = 20 * 60;
    private static final double MOVEMENT_SPEED = 0.35F;

    protected static final RawAnimation IDLE_ANIM = RawAnimation.begin().thenLoop("idle");

    private final AnimatableInstanceCache geoCache = GeckoLibUtil.createInstanceCache(this);


    public VoidSoulEntity(EntityType<? extends PathfinderMob> type, Level world) {
        super(type, world);
        this.moveControl = new FlyingMoveControl(this, 20, true);
        this.setNoGravity(true);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new AvoidEntityGoal<>(this, Player.class, 8.0F, 1.2D, 1.5D));
        this.goalSelector.addGoal(2, new WaterAvoidingRandomFlyingGoal(this, 1.0D));
    }

    @Override
    public void aiStep() {
        super.aiStep();

        if (this.lifetime > 0) {
            this.lifetime--;
        } else {
            kill();
        }
    }

    @Override
    public InteractionResult mobInteract(Player pPlayer, InteractionHand pHand) {
        ItemStack itemStack = pPlayer.getItemInHand(pHand);

        if (itemStack.is(Items.GLASS_BOTTLE)) {
            this.isCaptured = true;
            this.kill();

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
            }
        }
        this.remove(RemovalReason.KILLED);
        this.gameEvent(GameEvent.ENTITY_DIE);
    }

    @Override
    public void tick() {
        super.tick();
    }

    @Override
    protected boolean shouldDespawnInPeaceful() {
        return false;
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return null;
    }

    @Override
    public boolean hurt(DamageSource pSource, float pAmount) {
        if (pSource.is(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
            return super.hurt(pSource, pAmount);
        }
        return false;
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ALLAY_AMBIENT_WITHOUT_ITEM;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MOVEMENT_SPEED, MOVEMENT_SPEED)
                .add(Attributes.FLYING_SPEED, MOVEMENT_SPEED);
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
    public void handleDamageEvent(DamageSource pDamageSource) {

    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource pDamageSource) {
        return SoundEvents.EMPTY;
    }

    @Override
    public void knockback(double pStrength, double pX, double pZ) {

    }

    @Override
    public void registerControllers(final AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "walkingController", 5, this::predicate));
    }

    protected <E extends VoidSoulEntity> PlayState predicate(final AnimationState<E> event) {
//        if (event.isMoving()) {
//            return event.setAndContinue(WALK_ANIM);
//        }

        return event.setAndContinue(IDLE_ANIM);
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.geoCache;
    }

    public boolean removeWhenFarAway(double pDistanceToClosestPlayer) {
        return false;
    }
}
