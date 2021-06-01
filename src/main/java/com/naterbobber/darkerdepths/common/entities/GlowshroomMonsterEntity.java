package com.naterbobber.darkerdepths.common.entities;


import com.naterbobber.darkerdepths.core.registries.DDItems;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class GlowshroomMonsterEntity extends MonsterEntity {
    private int attackTick;

    public GlowshroomMonsterEntity(EntityType<? extends MonsterEntity> type, World worldIn) {
        super(type, worldIn);
        this.experienceValue = 20;
    }

    public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
        return MobEntity.func_233666_p_()
                .createMutableAttribute(Attributes.MAX_HEALTH, 60.0D)
                .createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.5D)
                .createMutableAttribute(Attributes.ATTACK_KNOCKBACK, 1.3D)
                .createMutableAttribute(Attributes.ATTACK_DAMAGE, 8.0D)
                .createMutableAttribute(Attributes.KNOCKBACK_RESISTANCE, 0.75D);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 0.6D, true));
        this.goalSelector.addGoal(2, new LookAtGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.addGoal(3, new LookRandomlyGoal(this));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true, false));
    }

    @Override
    protected SoundEvent getAmbientSound() { return SoundEvents.ENTITY_RAVAGER_AMBIENT; }

    @Override
    protected SoundEvent getDeathSound() { return SoundEvents.ENTITY_RAVAGER_DEATH; }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.ENTITY_RAVAGER_HURT;
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState blockIn) {
        this.playSound(SoundEvents.ENTITY_RAVAGER_STEP, 0.15F, 0.8F);
    }

    @Override
    public void livingTick() {
        super.livingTick();
        if (this.attackTick > 0) {
            this.attackTick--;
        }
    }

    @OnlyIn(Dist.CLIENT)
    public void handleStatusUpdate(byte id) {
        if (id == 4) {
            this.attackTick = 10;
            this.playSound(SoundEvents.ENTITY_RAVAGER_ATTACK, 1.0F, 1.0F);
        } else {
            super.handleStatusUpdate(id);
        }
    }

    @OnlyIn(Dist.CLIENT)
    public int getAttackTimer() {
        return this.attackTick;
    }

    @Override
    public boolean attackEntityAsMob(Entity entityIn) {
        this.attackTick = 10;
        this.world.setEntityState(this, (byte) 4);
        this.playSound(SoundEvents.ENTITY_RAVAGER_ATTACK, 1.0F, 1.0F);
        return super.attackEntityAsMob(entityIn);
    }

    public ItemStack getPickedResult(RayTraceResult target) {
        return new ItemStack(DDItems.GLOWSHROOM_MONSTER_SPAWN_EGG.get());
    }

}