package com.naterbobber.darkerdepths.entities;

import com.naterbobber.darkerdepths.init.DDBlocks;
import com.naterbobber.darkerdepths.init.DDEntityTypes;
import com.naterbobber.darkerdepths.init.DDItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.Vec3;

public class DDBoatEntity extends Boat {
    private static final EntityDataAccessor<Integer> BOAT_TYPE = SynchedEntityData.defineId(DDBoatEntity.class, EntityDataSerializers.INT);

    public DDBoatEntity(EntityType<? extends Boat> entityType, Level world) {
        super(entityType, world);
        this.blocksBuilding = true;
    }

    public DDBoatEntity(EntityType<? extends Boat> entityType, BoatType type, Level world, double x, double y, double z) {
        this(entityType, world);
        this.setPos(x, y, z);
        this.setDeltaMovement(Vec3.ZERO);
        this.xo = x;
        this.yo = y;
        this.zo = z;
        this.setBoatType(type);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(BOAT_TYPE, BoatType.PETRIFIED.ordinal());
    }

    @Override
    public Item getDropItem() {
        return switch (this.getBoatTypeDropItem()) {
            case GLOWSHROOM -> DDItems.GLOWSHROOM_BOAT.get();
            default -> DDItems.PETRIFIED_BOAT.get();
        };
    }

    public void setBoatType(BoatType type) {
        this.entityData.set(BOAT_TYPE, type.ordinal());
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putString("Type", this.getBoatTypeDropItem().getName());
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        if (tag.contains("Type", 8)) {
            this.setBoatType(BoatType.byName(tag.getString("Type")));
        }
    }

    public BoatType getBoatTypeDropItem() {
        return BoatType.byId(this.entityData.get(BOAT_TYPE));
    }

    public enum BoatType {
        PETRIFIED(DDBlocks.PETRIFIED_PLANKS.get(), "petrified"),
        GLOWSHROOM(DDBlocks.GLOWSHROOM_PLANKS.get(), "glowshroom");

        private final String name;
        private final Block planks;

        BoatType(Block block, String name) {
            this.name = name;
            this.planks = block;
        }

        public String getName() {
            return this.name;
        }

        public Block getPlanks() {
            return this.planks;
        }

        @Override
        public String toString() {
            return this.name;
        }

        public static BoatType byId(int id) {
            BoatType[] types = values();
            if (id < 0 || id >= types.length) {
                id = 0;
            }
            return types[id];
        }

        public static BoatType byName(String name) {
            for (BoatType type : values()) {
                if (type.getName().equals(name)) {
                    return type;
                }
            }
            return values()[0];
        }
    }
}