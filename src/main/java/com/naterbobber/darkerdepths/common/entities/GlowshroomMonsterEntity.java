package com.naterbobber.darkerdepths.common.entities;

import com.naterbobber.darkerdepths.core.registries.DDBlocks;
import com.naterbobber.darkerdepths.core.registries.DDItems;
import com.naterbobber.darkerdepths.core.registries.DDSoundEvents;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.monster.CreeperEntity;
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

//<>

public class GlowshroomMonsterEntity extends MonsterEntity {
    private int attackTick;

    public GlowshroomMonsterEntity(EntityType<? extends MonsterEntity> type, World worldIn) {
        super(type, worldIn);
        this.stepHeight = 1.0F;
        this.experienceValue = 20;
    }

    public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
        return MobEntity.func_233666_p_()
                .createMutableAttribute(Attributes.MAX_HEALTH, 45.0D)
                .createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.5D)
                .createMutableAttribute(Attributes.ATTACK_KNOCKBACK, 1.3D)
                .createMutableAttribute(Attributes.ATTACK_DAMAGE, 8.0D)
                .createMutableAttribute(Attributes.KNOCKBACK_RESISTANCE, 0.75D);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
//        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, this.getAttributeValue(Attributes.MOVEMENT_SPEED) + 0.2D, true));
//        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.0D, true));
//        this.goalSelector.addGoal(2, new MoveTowardsTargetGoal(this, 0.9D, 32.0F));
//        this.goalSelector.addGoal(3, new WaterAvoidingRandomWalkingGoal(this, this.getAttributeValue(Attributes.MOVEMENT_SPEED)));
//        this.goalSelector.addGoal(4, new LookAtGoal(this, PlayerEntity.class, 6.0F));
//        this.goalSelector.addGoal(5, new LookRandomlyGoal(this));
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, this.getAttributeValue(Attributes.MOVEMENT_SPEED) + 0.2D, true));
        this.goalSelector.addGoal(2, new WaterAvoidingRandomWalkingGoal(this, this.getAttributeValue(Attributes.MOVEMENT_SPEED)));
        this.goalSelector.addGoal(2, new LookAtGoal(this, PlayerEntity.class, 6.0F));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, MobEntity.class, 0, false, false, (entityPredicate) -> {
            return !(entityPredicate instanceof CreeperEntity);
        }));
    }

    @Override
    protected SoundEvent getAmbientSound() { return DDSoundEvents.ENTITY_GLOWSHROOM_MONSTER_AMBIENT.get(); }

    @Override
    protected SoundEvent getDeathSound() { return DDSoundEvents.ENTITY_GLOWSHROOM_MONSTER_DEATH.get(); }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return DDSoundEvents.ENTITY_GLOWSHROOM_MONSTER_HURT.get();
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState blockIn) {
        this.playSound(SoundEvents.ENTITY_RAVAGER_STEP, 0.15F, 0.8F);
    }

    @Override
    public void livingTick() {
        super.livingTick();
//        if (this.isAlive()) {
//            if (this.isMovementBlocked()) {
//                this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.0D);
//            } else {
//                double endSpeed = this.getAttackTarget() != null ? 0.35D : 0.3D;
//                double startSpeed = this.getAttribute(Attributes.MOVEMENT_SPEED).getBaseValue();
//                this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(MathHelper.lerp(0.1D, startSpeed, endSpeed));
//            }
//
//            if (this.collidedHorizontally && ForgeEventFactory.getMobGriefingEvent(this.world, this)) {
//                boolean shouldDestroy = false;
//                AxisAlignedBB boundingBox = this.getBoundingBox().grow(0.2D);
//
//                for(BlockPos blockpos : BlockPos.getAllInBoxMutable(MathHelper.floor(boundingBox.minX), MathHelper.floor(boundingBox.minY), MathHelper.floor(boundingBox.minZ), MathHelper.floor(boundingBox.maxX), MathHelper.floor(boundingBox.maxY), MathHelper.floor(boundingBox.maxZ))) {
//                    BlockState blockstate = this.world.getBlockState(blockpos);
//                    Block block = blockstate.getBlock();
//                    if (block.isIn(BlockTags.BASE_STONE_OVERWORLD)) {
//                        shouldDestroy = this.world.destroyBlock(blockpos, true, this) || shouldDestroy;
//                    }
//                }
//
//                if (!shouldDestroy && this.onGround) {
//                    this.jump();
//                }
//            }
//        }

        if (this.attackTick > 0) {
            this.attackTick--;
        }
    }

    @Override
    protected void dropSpecialItems(DamageSource source, int looting, boolean recentlyHitIn) {
        super.dropSpecialItems(source, looting, recentlyHitIn);
        ItemEntity itemEntity = this.entityDropItem(DDItems.GLOWSHROOM_CAP.get());
        if (itemEntity != null) {
            itemEntity.setNoDespawn();
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
        float f = (float) this.getAttributeValue(Attributes.ATTACK_DAMAGE);
        boolean flag = entityIn.attackEntityFrom(DamageSource.causeMobDamage(this), f);
        this.playSound(SoundEvents.ENTITY_RAVAGER_ATTACK, 1.0F, 1.0F);
        return flag;
    }

    @Override
    public ItemStack getPickedResult(RayTraceResult target) {
        return new ItemStack(DDItems.GLOWSHROOM_MONSTER_SPAWN_EGG.get());
    }

//    @Override
//    protected PathNavigator createNavigator(World worldIn) {
//        return new GlowshroomMonsterEntity.Navigator(this, worldIn);
//    }
//
//    class AttackGoal extends MeleeAttackGoal {
//        public AttackGoal() {
//            super(GlowshroomMonsterEntity.this, 1.0D, true);
//        }
//
//        @Override
//        protected double getAttackReachSqr(LivingEntity attackTarget) {
//            float width = GlowshroomMonsterEntity.this.getWidth() - 0.1F;
//            return width * 2.0F * width * 2.0F + attackTarget.getWidth();
//        }
//    }
//
//    static class Navigator extends GroundPathNavigator {
//        public Navigator(MobEntity entitylivingIn, World worldIn) {
//            super(entitylivingIn, worldIn);
//        }
//
//        @Override
//        protected PathFinder getPathFinder(int range) {
//            this.nodeProcessor = new GlowshroomMonsterEntity.Processor();
//            return new PathFinder(this.nodeProcessor, range);
//        }
//    }
//
//    static class Processor extends WalkNodeProcessor {
//        private Processor() {}
//
//        @Override
//        protected PathNodeType refineNodeType(IBlockReader worldIn, boolean canOpenDoors, boolean canEnterDoors, BlockPos pos, PathNodeType nodeType) {
//            return nodeType == PathNodeType.BLOCKED ? PathNodeType.OPEN : super.refineNodeType(worldIn, canOpenDoors, canEnterDoors, pos, nodeType);
//        }
//    }
}