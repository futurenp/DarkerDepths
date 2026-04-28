package com.naterbobber.darkerdepths.item;

import com.naterbobber.darkerdepths.entities.DDBoatEntity;
import com.naterbobber.darkerdepths.entities.DDChestBoatEntity;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class DDBoatItem extends Item {
    private static final Predicate<Entity> NON_COLLIDABLE_ENTITIES = EntitySelector.NO_SPECTATORS.and(Entity::canBeCollidedWith);
    private final DDBoatEntity.BoatType type;
    private final boolean hasChest;
    private final Supplier<? extends EntityType<? extends Boat>> entityTypeSupplier;

    public DDBoatItem(boolean hasChest, DDBoatEntity.BoatType typeIn, Supplier<? extends EntityType<? extends Boat>> entityTypeSupplier, Properties properties) {
        super(properties);
        this.type = typeIn;
        this.hasChest = hasChest;
        this.entityTypeSupplier = entityTypeSupplier;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
        ItemStack itemstack = playerIn.getItemInHand(handIn);
        HitResult raytraceresult = getPlayerPOVHitResult(worldIn, playerIn, ClipContext.Fluid.ANY);
        if (raytraceresult.getType() == HitResult.Type.MISS) {
            return InteractionResultHolder.pass(itemstack);
        } else {
            Vec3 vector3d = playerIn.getViewVector(1.0F);
            List<Entity> list = worldIn.getEntities(playerIn, playerIn.getBoundingBox().expandTowards(vector3d.scale(5.0D)).inflate(1.0D), NON_COLLIDABLE_ENTITIES);
            if (!list.isEmpty()) {
                Vec3 vector3d1 = playerIn.getEyePosition(1.0F);

                for (Entity entity : list) {
                    AABB axisalignedbb = entity.getBoundingBox().inflate(entity.getPickRadius());
                    if (axisalignedbb.contains(vector3d1)) {
                        return InteractionResultHolder.pass(itemstack);
                    }
                }
            }

            if (raytraceresult.getType() == HitResult.Type.BLOCK) {
                DDBoatEntity boatentity = this.getBoat(worldIn, raytraceresult);
                boatentity.setBoatType(this.type);
                boatentity.setYRot(playerIn.getYRot());
                if (!worldIn.noCollision(boatentity, boatentity.getBoundingBox())) {
                    return InteractionResultHolder.fail(itemstack);
                } else {
                    if (!worldIn.isClientSide) {
                        worldIn.addFreshEntity(boatentity);
                        if (!playerIn.getAbilities().instabuild) {
                            itemstack.shrink(1);
                        }
                    }

                    playerIn.awardStat(Stats.ITEM_USED.get(this));
                    return InteractionResultHolder.sidedSuccess(itemstack, worldIn.isClientSide());
                }
            } else {
                return InteractionResultHolder.pass(itemstack);
            }
        }
    }

    private DDBoatEntity getBoat(Level world, HitResult hit) {
        double x = hit.getLocation().x;
        double y = hit.getLocation().y;
        double z = hit.getLocation().z;

        if (this.hasChest) {
            return new DDChestBoatEntity((EntityType<? extends DDBoatEntity>) this.entityTypeSupplier.get(), this.type, world, x, y, z);
        } else {
            return new DDBoatEntity(this.entityTypeSupplier.get(), this.type, world, x, y, z);
        }
    }

}