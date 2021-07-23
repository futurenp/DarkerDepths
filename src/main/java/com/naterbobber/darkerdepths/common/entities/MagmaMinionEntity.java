package com.naterbobber.darkerdepths.common.entities;


import com.naterbobber.darkerdepths.core.registries.DDBlocks;
import com.naterbobber.darkerdepths.core.registries.DDItems;
import net.minecraft.block.BlockState;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.FollowOwnerGoal;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.OwnerHurtByTargetGoal;
import net.minecraft.entity.ai.goal.OwnerHurtTargetGoal;
import net.minecraft.entity.ai.goal.SitGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.TemptGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;

public class MagmaMinionEntity extends TameableEntity {
    private int attackTick;

    public MagmaMinionEntity(EntityType<? extends TameableEntity> type, World worldIn) {
        super(type, worldIn);
        this.experienceValue = 20;
    }

    public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
        return MobEntity.func_233666_p_()
                .createMutableAttribute(Attributes.MAX_HEALTH, 15.0D)
                .createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.5D)
                .createMutableAttribute(Attributes.ATTACK_KNOCKBACK, 1.3D)
                .createMutableAttribute(Attributes.ATTACK_DAMAGE, 2.0D)
                .createMutableAttribute(Attributes.FOLLOW_RANGE, 32.0D)
                .createMutableAttribute(Attributes.KNOCKBACK_RESISTANCE, 0.75D);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(1, new SitGoal(this));
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 0.6D, true));
        this.goalSelector.addGoal(3, new FollowOwnerGoal(this, this.getAttributeValue(Attributes.MOVEMENT_SPEED), 10.0F, 2.0F, false));
        this.goalSelector.addGoal(4, new WaterAvoidingRandomWalkingGoal(this, getAttributeValue(Attributes.MOVEMENT_SPEED)));
        this.goalSelector.addGoal(5, new TemptGoal(this, 0.8D, Ingredient.fromStacks(new ItemStack(DDBlocks.AMBER.get().asItem())), false));
        this.goalSelector.addGoal(6, new LookAtGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.addGoal(7, new LookRandomlyGoal(this));
        this.targetSelector.addGoal(1, new OwnerHurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new OwnerHurtTargetGoal(this));
        this.targetSelector.addGoal(3, new HurtByTargetGoal(this).setCallsForHelp());
    }

    @Override
    public ItemStack getPickedResult(RayTraceResult target) {
        return new ItemStack(DDItems.MAGMA_MINION_SPAWN_EGG.get());
    }

    @Override
    public ActionResultType getEntityInteractionResult(PlayerEntity playerIn, Hand hand) {
        ItemStack itemstack = playerIn.getHeldItem(hand);
            if (this.isTamed()) {
                if (this.isConsumable(itemstack) && this.getHealth() < this.getMaxHealth()) {
                    if (!world.isRemote()) {
                        if (!playerIn.abilities.isCreativeMode) {
                            itemstack.shrink(1);
                        }
                        this.heal(5);
                    }
                    return ActionResultType.SUCCESS;
                }

                ActionResultType actionresulttype = super.getEntityInteractionResult(playerIn, hand);
                if ((!actionresulttype.isSuccessOrConsume() && this.isOwner(playerIn))) {
                    if (!world.isRemote()) {
                        this.setSitting(!this.isQueuedToSit());
                        this.isJumping = false;
                        this.navigator.clearPath();
                        this.setAttackTarget(null);
                    }
                    return ActionResultType.SUCCESS;
                }
                return actionresulttype;

            } else if (this.isConsumable(itemstack) && this.getAttackTarget() == null) {
                if (!world.isRemote()) {
                    if (!playerIn.abilities.isCreativeMode) {
                        itemstack.shrink(1);
                    }
                    if (this.rand.nextInt(3) == 0 && !net.minecraftforge.event.ForgeEventFactory.onAnimalTame(this, playerIn)) {
                        this.setTamedBy(playerIn);
                        this.navigator.clearPath();
                        this.setAttackTarget(null);
                        this.setSitting(true);
                        this.world.setEntityState(this, (byte) 7);
                    } else {
                        this.world.setEntityState(this, (byte) 6);
                    }
                    return ActionResultType.SUCCESS;
                }
            }
            return super.getEntityInteractionResult(playerIn, hand);
        }

    private boolean isConsumable(ItemStack stack) {
        Item item = stack.getItem();
        return item == DDBlocks.AMBER.get().asItem();
    }

    @Override
    public void setTamed(boolean tamed) {
        super.setTamed(tamed);
        if (tamed) {
            this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(30.0D);
            this.setHealth(30.0F);
        } else {
            this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(8.0D);
        }
        this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(4.0D);
    }

    @Override
    public void livingTick() {
        super.livingTick();
        if (this.attackTick > 0) {
            this.attackTick--;
        }
        if (this.isEntitySleeping()) {
            this.world.addParticle(ParticleTypes.SMOKE, this.getPosX(), this.getPosY() + 1.0D, this.getPosZ(), 0, 0, 0);
        }
    }

    @Override
    public boolean attackEntityAsMob(Entity entityIn) {
        this.attackTick = 10;
        this.world.setEntityState(this, (byte) 4);
        float f = (float) this.getAttributeValue(Attributes.ATTACK_DAMAGE);
        boolean flag = entityIn.attackEntityFrom(DamageSource.causeMobDamage(this), f);
        this.playSound(SoundEvents.ENTITY_RAVAGER_ATTACK, 1.0F, 1.3F);
        return flag;
    }

    @Override
    public void handleStatusUpdate(byte id) {
        if (id == 4) {
            this.attackTick = 10;
            this.playSound(SoundEvents.ENTITY_RAVAGER_ATTACK, 1.0F, 1.3F);
        } else {
            super.handleStatusUpdate(id);
        }
    }

    @OnlyIn(Dist.CLIENT)
    public int getAttackTick() {
        return this.attackTick;
    }

    public boolean canBeLeashedTo(PlayerEntity player) {return true;}

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

//    @Override
//    public ItemStack getPickedResult(RayTraceResult target) {
//        return new ItemStack(DDItems.MAGMA_MINION_SPAWN_EGG.get());
//    }

    @Nullable
    @Override
    public AgeableEntity createChild(ServerWorld world, AgeableEntity mate) {
        return null;
    }
}