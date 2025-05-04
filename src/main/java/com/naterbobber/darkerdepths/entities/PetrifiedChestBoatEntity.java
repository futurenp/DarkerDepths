package com.naterbobber.darkerdepths.entities;

import com.naterbobber.darkerdepths.init.DDEntityTypes;
import com.naterbobber.darkerdepths.init.DDItems;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
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
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.entity.vehicle.ContainerEntity;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;

import javax.annotation.Nullable;

public class PetrifiedChestBoatEntity extends PetrifiedBoatEntity implements HasCustomInventoryScreen, ContainerEntity {
    private NonNullList<ItemStack> itemStacks = NonNullList.withSize(27, ItemStack.EMPTY);
    @Nullable
    private ResourceLocation lootTable;
    private long lootTableSeed;

    public PetrifiedChestBoatEntity(EntityType<? extends Boat> p_219869_, Level p_219870_) {
        super(p_219869_, p_219870_);
    }

    public PetrifiedChestBoatEntity(Level p_219872_, double p_219873_, double p_219874_, double p_219875_) {
        this(DDEntityTypes.PETRIFIED_CHEST_BOAT.get(), p_219872_);
        this.setPos(p_219873_, p_219874_, p_219875_);
        this.xo = p_219873_;
        this.yo = p_219874_;
        this.zo = p_219875_;
    }

    @Override
    protected float getSinglePassengerXOffset() {
        return 0.15F;
    }

    @Override
    protected int getMaxPassengers() {
        return 1;
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag p_219908_) {
        super.addAdditionalSaveData(p_219908_);
        this.addChestVehicleSaveData(p_219908_);
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag p_219901_) {
        super.readAdditionalSaveData(p_219901_);
        this.readChestVehicleSaveData(p_219901_);
    }

    @Override
    public void destroy(DamageSource p_219892_) {
        super.destroy(p_219892_);
        this.chestVehicleDestroyed(p_219892_, this.level(), this);
    }

    @Override
    public void remove(Entity.RemovalReason p_219894_) {
        if (!this.level().isClientSide && p_219894_.shouldDestroy()) {
            Containers.dropContents(this.level(), this, this);
        }

        super.remove(p_219894_);
    }

    @Override
    public InteractionResult interact(Player player, InteractionHand hand) {
        if (this.canAddPassenger(player) && !player.isSecondaryUseActive()) {
            return super.interact(player, hand);
        } else {
            InteractionResult interactionresult = this.interactWithContainerVehicle(player);
            if (interactionresult.consumesAction()) {
                this.gameEvent(GameEvent.CONTAINER_OPEN, player);
                PiglinAi.angerNearbyPiglins(player, true);
            }
            return interactionresult;
        }
    }

    @Override
    public void openCustomInventoryScreen(Player player) {
        player.openMenu(this);
        if (!player.level().isClientSide) {
            this.gameEvent(GameEvent.CONTAINER_OPEN, player);
            PiglinAi.angerNearbyPiglins(player, true);
        }

    }

    @Override
    public Item getDropItem() {
        return DDItems.PETRIFIED_CHEST_BOAT.get();
    }

    @Override
    public void clearContent() {
        this.clearChestVehicleContent();
    }

    @Override
    public int getContainerSize() {
        return 27;
    }

    @Override
    public ItemStack getItem(int p_219880_) {
        return this.getChestVehicleItem(p_219880_);
    }

    @Override
    public ItemStack removeItem(int p_219882_, int p_219883_) {
        return this.removeChestVehicleItem(p_219882_, p_219883_);
    }

    @Override
    public ItemStack removeItemNoUpdate(int p_219904_) {
        return this.removeChestVehicleItemNoUpdate(p_219904_);
    }

    @Override
    public void setItem(int p_219885_, ItemStack p_219886_) {
        this.setChestVehicleItem(p_219885_, p_219886_);
    }

    @Override
    public SlotAccess getSlot(int p_219918_) {
        return this.getChestVehicleSlot(p_219918_);
    }

    @Override
    public void setChanged() {
    }

    @Override
    public boolean stillValid(Player p_219896_) {
        return this.isChestVehicleStillValid(p_219896_);
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int p_219910_, Inventory p_219911_, Player p_219912_) {
        if (this.lootTable != null && p_219912_.isSpectator()) {
            return null;
        } else {
            this.unpackLootTable(p_219911_.player);
            return ChestMenu.threeRows(p_219910_, p_219911_, this);
        }
    }

    public void unpackLootTable(@Nullable Player p_219914_) {
        this.unpackChestVehicleLootTable(p_219914_);
    }

    @Nullable
    @Override
    public ResourceLocation getLootTable() {
        return this.lootTable;
    }

    @Override
    public void setLootTable(@Nullable ResourceLocation p_219890_) {
        this.lootTable = p_219890_;
    }

    @Override
    public long getLootTableSeed() {
        return this.lootTableSeed;
    }

    @Override
    public void setLootTableSeed(long p_219888_) {
        this.lootTableSeed = p_219888_;
    }

    @Override
    public NonNullList<ItemStack> getItemStacks() {
        return this.itemStacks;
    }

    @Override
    public void clearItemStacks() {
        this.itemStacks = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
    }

}
