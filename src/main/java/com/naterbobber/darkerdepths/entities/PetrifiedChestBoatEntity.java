package com.naterbobber.darkerdepths.entities;

import javax.annotation.Nullable;

import com.naterbobber.darkerdepths.init.DDEntityTypes;
import com.naterbobber.darkerdepths.init.DDItems;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.HasCustomInventoryScreen;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.monster.piglin.PiglinAi;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.ContainerEntity;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.GameEvent.Context;
import net.minecraft.world.level.storage.loot.LootTable;

public class PetrifiedChestBoatEntity extends PetrifiedBoatEntity implements HasCustomInventoryScreen, ContainerEntity {
    private static final int CONTAINER_SIZE = 27;
    private NonNullList<ItemStack> itemStacks;
    @Nullable
    private ResourceKey<LootTable> lootTable;
    private long lootTableSeed;

    public PetrifiedChestBoatEntity(EntityType<? extends PetrifiedBoatEntity> entityType, Level level) {
        super(entityType, level);
        this.itemStacks = NonNullList.withSize(27, ItemStack.EMPTY);
    }

    public PetrifiedChestBoatEntity(Level level, double x, double y, double z) {
        super(DDEntityTypes.PETRIFIED_CHEST_BOAT.get(), level);
        this.itemStacks = NonNullList.withSize(27, ItemStack.EMPTY);
        this.setPos(x, y, z);
        this.xo = x;
        this.yo = y;
        this.zo = z;
    }

    protected float getSinglePassengerXOffset() {
        return 0.15F;
    }

    protected int getMaxPassengers() {
        return 1;
    }

    protected void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        this.addChestVehicleSaveData(compound, this.registryAccess());
    }

    protected void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.readChestVehicleSaveData(compound, this.registryAccess());
    }

    public void destroy(DamageSource source) {
        this.destroy(this.getDropItem());
        this.chestVehicleDestroyed(source, this.level(), this);
    }

    public void remove(Entity.RemovalReason reason) {
        if (!this.level().isClientSide && reason.shouldDestroy()) {
            Containers.dropContents(this.level(), this, this);
        }

        super.remove(reason);
    }

    public InteractionResult interact(Player player, InteractionHand hand) {
        InteractionResult interactionresult1;
        if (!player.isSecondaryUseActive()) {
            interactionresult1 = super.interact(player, hand);
            if (interactionresult1 != InteractionResult.PASS) {
                return interactionresult1;
            }
        }

        if (this.canAddPassenger(player) && !player.isSecondaryUseActive()) {
            return InteractionResult.PASS;
        } else {
            interactionresult1 = this.interactWithContainerVehicle(player);
            if (interactionresult1.consumesAction()) {
                this.gameEvent(GameEvent.CONTAINER_OPEN, player);
                PiglinAi.angerNearbyPiglins(player, true);
            }

            return interactionresult1;
        }
    }

    public void openCustomInventoryScreen(Player player) {
        player.openMenu(this);
        if (!player.level().isClientSide) {
            this.gameEvent(GameEvent.CONTAINER_OPEN, player);
            PiglinAi.angerNearbyPiglins(player, true);
        }

    }

    public Item getDropItem() {
        return DDItems.PETRIFIED_CHEST_BOAT.get();
    }

    public void clearContent() {
        this.clearChestVehicleContent();
    }

    public int getContainerSize() {
        return 27;
    }

    public ItemStack getItem(int slot) {
        return this.getChestVehicleItem(slot);
    }

    public ItemStack removeItem(int slot, int amount) {
        return this.removeChestVehicleItem(slot, amount);
    }

    public ItemStack removeItemNoUpdate(int slot) {
        return this.removeChestVehicleItemNoUpdate(slot);
    }

    public void setItem(int slot, ItemStack stack) {
        this.setChestVehicleItem(slot, stack);
    }

    public SlotAccess getSlot(int slot) {
        return this.getChestVehicleSlot(slot);
    }

    public void setChanged() {
    }

    public boolean stillValid(Player player) {
        return this.isChestVehicleStillValid(player);
    }

    @Nullable
    public AbstractContainerMenu createMenu(int containerId, Inventory playerInventory, Player player) {
        if (this.lootTable != null && player.isSpectator()) {
            return null;
        } else {
            this.unpackLootTable(playerInventory.player);
            return ChestMenu.threeRows(containerId, playerInventory, this);
        }
    }

    public void unpackLootTable(@Nullable Player player) {
        this.unpackChestVehicleLootTable(player);
    }

    @Nullable
    public ResourceKey<LootTable> getLootTable() {
        return this.lootTable;
    }

    public void setLootTable(@Nullable ResourceKey<LootTable> lootTable) {
        this.lootTable = lootTable;
    }

    public long getLootTableSeed() {
        return this.lootTableSeed;
    }

    public void setLootTableSeed(long lootTableSeed) {
        this.lootTableSeed = lootTableSeed;
    }

    public NonNullList<ItemStack> getItemStacks() {
        return this.itemStacks;
    }

    public void clearItemStacks() {
        this.itemStacks = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
    }

    public void stopOpen(Player player) {
        this.level().gameEvent(GameEvent.CONTAINER_CLOSE, this.position(), Context.of(player));
    }
}
