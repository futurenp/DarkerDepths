package com.naterbobber.darkerdepths.entities;

import com.naterbobber.darkerdepths.entities.control.ConfigurableMoveControl;
import com.naterbobber.darkerdepths.entities.goals.AttackMemoryTargetGoal;
import com.naterbobber.darkerdepths.entities.goals.ConfigurableReachMeleeAttackGoal;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class VoidSoulKnightEntity extends Monster implements GeoEntity {

    private int attackTick;
    private static final double HEALTH = 80;
    private static final double MOVEMENT_SPEED = .17;
    private static final double ATTACK_DAMAGE = 16;
    private static final double ATTACK_KNOCKBACK = 2;
    private static final double KNOCKBACK_RESISTANCE = 0.85;
    private static final double FOLLOW_RANGE = 32;

    protected static final RawAnimation WALK_ANIM = RawAnimation.begin().thenLoop("move.walk");

    private final AnimatableInstanceCache geoCache = GeckoLibUtil.createInstanceCache(this);

    public VoidSoulKnightEntity(EntityType<? extends Monster> type, Level world) {
        super(type, world);
        this.xpReward = 40;
        this.moveControl = new ConfigurableMoveControl(this, 10.0F);
    }

    @Override
    protected void registerGoals() {
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.goalSelector.addGoal(2, new ConfigurableReachMeleeAttackGoal(this, 1.3D, true, 2.75f));
        this.targetSelector.addGoal(3, new AttackMemoryTargetGoal<>(this, Player.class, 640, true));
        this.goalSelector.addGoal(7, new WaterAvoidingRandomStrollGoal(this, 1.2D));
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

    @Override
    public void registerControllers(final AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "Walking", 5, this::walkAnimController));
    }

    protected <E extends VoidSoulKnightEntity> PlayState walkAnimController(final AnimationState<E> event) {
        if (event.isMoving())
            return event.setAndContinue(WALK_ANIM);

        return PlayState.STOP;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.geoCache;
    }
}
