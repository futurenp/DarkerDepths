package com.naterbobber.darkerdepths.entities;

import com.naterbobber.darkerdepths.init.DDBlocks;
import com.naterbobber.darkerdepths.init.DDSoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ambient.Bat;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import org.jetbrains.annotations.Nullable;

public class GlowshroomMonsterEntity extends Monster {
    private int attackTick;

    public GlowshroomMonsterEntity(EntityType<? extends Monster> type, Level world) {
        super(type, world);
        this.xpReward = 20;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 0.7D, true));
        this.goalSelector.addGoal(2, new WaterAvoidingRandomStrollGoal(this, 0.5D));
        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Mob.class, 10, false, false, (entity) -> !(entity instanceof Creeper) && !(entity instanceof GlowshroomMonsterEntity) && !(entity instanceof Bat)));
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
        }
        else {
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
        boolean flag = entity.hurt(this.level().damageSources().mobAttack(this), f);
        this.playSound(SoundEvents.RAVAGER_ATTACK, 1.0F, 1.0F);
        return flag;
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

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 45.0D).add(Attributes.MOVEMENT_SPEED, 0.5D).add(Attributes.ATTACK_KNOCKBACK, 1.3D).add(Attributes.ATTACK_DAMAGE, 8.0D).add(Attributes.KNOCKBACK_RESISTANCE, 0.75D);
    }

    public static boolean canSpawn(EntityType<? extends Mob> typeIn, ServerLevelAccessor worldIn, MobSpawnType reason, BlockPos pos, RandomSource randomIn) {
        return worldIn.getDifficulty() != Difficulty.PEACEFUL && pos.getY() <= 40 && worldIn.getBlockState(pos.below()).is(DDBlocks.MOSSY_GRIMESTONE.get());
    }

}
