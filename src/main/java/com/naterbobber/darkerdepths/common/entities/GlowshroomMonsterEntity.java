package com.naterbobber.darkerdepths.common.entities;


import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.AbstractRaiderEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.pathfinding.*;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class GlowshroomMonsterEntity extends MonsterEntity {
    private int attackTick;

    public GlowshroomMonsterEntity(EntityType<? extends MonsterEntity> type, World worldIn) {
        super(type, worldIn);
        this.stepHeight = 1.0F;
        this.experienceValue = 20;
    }

    public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
        return MobEntity.func_233666_p_()
                .createMutableAttribute(Attributes.MAX_HEALTH, 60.0D)
                .createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.50D)
                .createMutableAttribute(Attributes.ATTACK_KNOCKBACK, 1.3D)
                .createMutableAttribute(Attributes.ATTACK_DAMAGE, 8.0D)
                .createMutableAttribute(Attributes.KNOCKBACK_RESISTANCE, 0.75D);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(8, new LookAtGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.addGoal(8, new LookRandomlyGoal(this));
        this.goalSelector.addGoal(2, new GlowshroomMonsterEntity.AttackGoal());
        this.goalSelector.addGoal(7, new WaterAvoidingRandomWalkingGoal(this, 0.4D));
        this.targetSelector.addGoal(1, (new HurtByTargetGoal(this, AbstractRaiderEntity.class)).setCallsForHelp());
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolemEntity.class, true));
    }

    @Override
    protected net.minecraft.util.SoundEvent getAmbientSound() { return SoundEvents.ENTITY_RAVAGER_AMBIENT; }

    @Override
    protected net.minecraft.util.SoundEvent getDeathSound() { return SoundEvents.ENTITY_RAVAGER_DEATH; }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.ENTITY_RAVAGER_HURT;
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState blockIn) {
        this.playSound(SoundEvents.ENTITY_RAVAGER_STEP, 0.15F, 0.8F);
    }

    @OnlyIn(Dist.CLIENT)
    public int getAttackTimer() {
        return this.attackTick;
    }

    @Override
    public boolean attackEntityAsMob(Entity entityIn) {
        this.attackTick = 40;
        this.world.setEntityState(this, (byte)4);
        this.playSound(SoundEvents.ENTITY_RAVAGER_ATTACK, 1.0F, 1.0F);
        return super.attackEntityAsMob(entityIn);
    }

    @Override
    public void livingTick() {
        super.livingTick();
        if(this.attackTick > 0) {
            --this.attackTick;
        }
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void handleStatusUpdate(byte id) {
        if(id == 4){
            this.attackTick = 40;
            this.playSound(SoundEvents.ENTITY_RAVAGER_ATTACK, 1.0F, 1.0F);
        } else{
            super.handleStatusUpdate(id);
        }
    }

    class AttackGoal extends MeleeAttackGoal {
        public AttackGoal() {
            super(GlowshroomMonsterEntity.this, 0.60D, true);
        }

        @Override
        public void resetTask() {
            super.resetTask();
            GlowshroomMonsterEntity.this.setAggroed(false);
        }

        public void tick() {
            super.tick();
            ++GlowshroomMonsterEntity.this.attackTick;
            if (GlowshroomMonsterEntity.this.attackTick >= 5 && this.func_234041_j_() < this.func_234042_k_() / 2) {
                GlowshroomMonsterEntity.this.setAggroed(true);
            } else {
                GlowshroomMonsterEntity.this.setAggroed(false);
            }

        }
    }

}