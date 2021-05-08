package com.naterbobber.darkerdepths.common.entities;


import net.minecraft.block.BlockState;
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

public class MagmaMinionEntity extends MonsterEntity {

    public MagmaMinionEntity(EntityType<? extends MonsterEntity> type, World worldIn) {
        super(type, worldIn);
        this.stepHeight = 1.0F;
        this.experienceValue = 20;
    }

    //func_233666_p_ ---> registerAttributes()
    public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
        return MobEntity.func_233666_p_()
                .createMutableAttribute(Attributes.MAX_HEALTH, 15.0D)
                .createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.50D)
                .createMutableAttribute(Attributes.ATTACK_KNOCKBACK, 1.3D)
                .createMutableAttribute(Attributes.ATTACK_DAMAGE, 2.0D)
                .createMutableAttribute(Attributes.FOLLOW_RANGE, 32.0D)
                .createMutableAttribute(Attributes.KNOCKBACK_RESISTANCE, 0.75D);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(4, new AttackGoal());
        this.goalSelector.addGoal(5, new WaterAvoidingRandomWalkingGoal(this, 0.4D));
        this.goalSelector.addGoal(6, new LookAtGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.addGoal(10, new LookAtGoal(this, MobEntity.class, 8.0F));
        this.targetSelector.addGoal(2, (new HurtByTargetGoal(this, AbstractRaiderEntity.class)).setCallsForHelp());
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, IronGolemEntity.class, true));
    }

    @Override
    public boolean isBurning() {
        return false;
    }

    @Override
    protected SoundEvent getAmbientSound() { return SoundEvents.ENTITY_RAVAGER_AMBIENT; }

    @Override
    protected SoundEvent getDeathSound() { return SoundEvents.ENTITY_RAVAGER_DEATH; }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) { return SoundEvents.ENTITY_RAVAGER_HURT; }

    @Override
    protected void playStepSound(BlockPos pos, BlockState blockIn) {
        this.playSound(SoundEvents.ENTITY_RAVAGER_STEP, 0.15F, 0.8F);
    }

    static class TargetGoal<T extends LivingEntity> extends NearestAttackableTargetGoal<T> {
        public TargetGoal(MagmaMinionEntity magma_minion, Class<T> classTarget) {
            super(magma_minion, classTarget, true);
        }
    }

    class AttackGoal extends MeleeAttackGoal {
        public AttackGoal() {
            super(MagmaMinionEntity.this, 0.60D, true);
        }

        protected double getAttackReachSqr(LivingEntity attackTarget) {
            float f = MagmaMinionEntity.this.getWidth() - 0.5F;
            return (double)(f * 2.0F * f * 2.0F + attackTarget.getWidth());
        }
    }
    static class Navigator extends GroundPathNavigator {
        public Navigator(MobEntity p_i50754_1_, World p_i50754_2_) {
            super(p_i50754_1_, p_i50754_2_);
        }

        protected PathFinder getPathFinder(int p_179679_1_) {
            this.nodeProcessor = new Processor();
            return new PathFinder(this.nodeProcessor, p_179679_1_);
        }
    }
    protected PathNavigator createNavigator(World worldIn) {
        return new Navigator(this, worldIn);
    }

    static class Processor extends WalkNodeProcessor {
        private Processor() {
        }

        @Override
        protected PathNodeType refineNodeType(IBlockReader worldIn, boolean canOpenDoors, boolean canEnterDoors, BlockPos pos, PathNodeType nodeType) {
            return nodeType == PathNodeType.LEAVES ? PathNodeType.OPEN : super.refineNodeType(worldIn, canOpenDoors, canEnterDoors, pos, nodeType);
        }
    }
}