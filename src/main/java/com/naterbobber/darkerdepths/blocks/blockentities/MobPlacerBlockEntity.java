package com.naterbobber.darkerdepths.blocks.blockentities;

import com.naterbobber.darkerdepths.init.DDBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class MobPlacerBlockEntity extends BlockEntity {
    private String entityTypeId = "";
    private float rotation = 0.0f;
    private CompoundTag entityData = new CompoundTag();
    private boolean hasSpawned = false;
    private int ticksExisted = 0;

    public MobPlacerBlockEntity(BlockPos pos, BlockState blockState) {
        super(DDBlockEntityTypes.MOB_PLACER.get(), pos, blockState);
    }

    public MobPlacerBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState blockState) {
        super(type, pos, blockState);
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, MobPlacerBlockEntity blockEntity) {
        if (!(level instanceof ServerLevel serverLevel)) return;

        blockEntity.ticksExisted++;

        if (blockEntity.ticksExisted < 5) return;

        if (!blockEntity.hasSpawned && !blockEntity.entityTypeId.isEmpty()) {
            blockEntity.spawnEntity(serverLevel);
            blockEntity.hasSpawned = true;

            level.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
        }
    }

    private void spawnEntity(ServerLevel level) {
        try {
            ResourceLocation entityId = ResourceLocation.parse(entityTypeId);
            EntityType<?> entityType = BuiltInRegistries.ENTITY_TYPE.get(entityId);

            if (entityType == null) {
                return;
            }

            Entity entity = entityType.create(level);
            if (entity == null) {
                return;
            }

            Vec3 spawnPos = new Vec3(
                    worldPosition.getX() + 0.5,
                    worldPosition.getY(),
                    worldPosition.getZ() + 0.5
            );
            entity.setPos(spawnPos);

            if (!entityData.isEmpty()) {
                entity.load(entityData);
            }

            entity.setYRot(rotation);
            entity.yRotO = rotation;
            entity.setXRot(0.0f);
            entity.xRotO = 0.0f;

            entity.setPos(spawnPos);

            if (entity instanceof net.minecraft.world.entity.Mob mob) {
                mob.finalizeSpawn(level, level.getCurrentDifficultyAt(worldPosition), MobSpawnType.STRUCTURE, null, null);
                mob.setPersistenceRequired();

                mob.setYRot(rotation);
                mob.yRotO = rotation;
                mob.setYHeadRot(rotation);
                mob.yHeadRotO = rotation;
            }

            level.addFreshEntity(entity);

        } catch (Exception e) {
            System.err.println("Error spawning entity " + entityTypeId + ": " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        this.entityTypeId = nbt.getString("EntityType");
        this.rotation = nbt.getFloat("Rotation");
        this.hasSpawned = nbt.getBoolean("HasSpawned");

        if (nbt.contains("EntityData")) {
            this.entityData = nbt.getCompound("EntityData");
        }
    }

    @Override
    protected void saveAdditional(CompoundTag nbt) {
        super.saveAdditional(nbt);
        nbt.putString("EntityType", this.entityTypeId);
        nbt.putFloat("Rotation", this.rotation);
        nbt.putBoolean("HasSpawned", this.hasSpawned);

        if (!this.entityData.isEmpty()) {
            nbt.put("EntityData", this.entityData);
        }
    }

    public void setEntityType(String entityTypeId) {
        this.entityTypeId = entityTypeId;
        setChanged();
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
        setChanged();
    }

    public void setEntityData(CompoundTag data) {
        this.entityData = data.copy();
        setChanged();
    }

    public String getEntityTypeId() {
        return entityTypeId;
    }

    public float getRotation() {
        return rotation;
    }

    public CompoundTag getEntityData() {
        return entityData.copy();
    }
}