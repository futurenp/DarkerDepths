package com.naterbobber.darkerdepths.block.blockentities;

import com.naterbobber.darkerdepths.block.TombBlock;
import com.naterbobber.darkerdepths.init.DDBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.animation.*;
import software.bernie.geckolib.util.RenderUtil;

public class TombBlockEntity extends BlockEntity implements GeoBlockEntity, Container {
    private static final int OPEN_ANIMATION_DURATION = 53;
    private static final int CLOSE_ANIMATION_DURATION = 40;

    private final AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);
    private NonNullList<ItemStack> items = NonNullList.withSize(1, ItemStack.EMPTY);

    private boolean isOpen = false;
    private boolean isAnimating = false;
    private int animationTimer = 0;

    public TombBlockEntity(BlockPos pos, BlockState state) {
        super(DDBlockEntityTypes.TOMB.get(), pos, state);
    }

    @Override
    protected void loadAdditional(CompoundTag nbt, HolderLookup.Provider registries) {
        super.loadAdditional(nbt, registries);
        this.isOpen = nbt.getBoolean("IsOpen");
        this.isAnimating = nbt.getBoolean("IsAnimating");
        this.animationTimer = nbt.getInt("AnimationTimer");
        this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        ContainerHelper.loadAllItems(nbt, this.items, registries);
    }

    @Override
    protected void saveAdditional(CompoundTag nbt, HolderLookup.Provider registries) {
        super.saveAdditional(nbt, registries);
        nbt.putBoolean("IsOpen", this.isOpen);
        nbt.putBoolean("IsAnimating", this.isAnimating);
        nbt.putInt("AnimationTimer", this.animationTimer);
        ContainerHelper.saveAllItems(nbt, this.items, registries);
    }

    // Container implementation
    @Override
    public int getContainerSize() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return this.items.get(0).isEmpty();
    }

    @Override
    public ItemStack getItem(int slot) {
        return slot == 0 ? this.items.get(0) : ItemStack.EMPTY;
    }

    @Override
    public ItemStack removeItemNoUpdate(int slot) {
        if (slot == 0) {
            ItemStack result = this.items.get(0);
            this.items.set(0, ItemStack.EMPTY);
            return result;
        }
        return ItemStack.EMPTY;
    }

    @Override
    public void setItem(int slot, ItemStack stack) {
        if (slot == 0) {
            this.items.set(0, stack);
            if (stack.getCount() > this.getMaxStackSize()) {
                stack.setCount(this.getMaxStackSize());
            }
            this.setChanged();
            this.syncToClients(); // Add this line to sync item changes
        }
    }

    @Override
    public ItemStack removeItem(int slot, int amount) {
        if (slot == 0 && !this.items.get(0).isEmpty()) {
            ItemStack result = ContainerHelper.removeItem(this.items, slot, amount);
            if (!result.isEmpty()) {
                this.setChanged();
                this.syncToClients(); // Add this line to sync item changes
            }
            return result;
        }
        return ItemStack.EMPTY;
    }

    @Override
    public boolean stillValid(Player player) {
        return this.isOpen && this.isInhabited() &&
            this.level != null &&
            this.level.getBlockEntity(this.worldPosition) == this &&
            player.distanceToSqr(this.worldPosition.getX() + 0.5, this.worldPosition.getY() + 0.5, this.worldPosition.getZ() + 0.5) <= 64.0;
    }

    @Override
    public void clearContent() {
        this.items.clear();
    }

    // Convenience methods
    public ItemStack getStoredItem() {
        return this.getItem(0);
    }

    public boolean hasStoredItem() {
        return !this.getStoredItem().isEmpty();
    }

    public boolean canTakeItem() {
        return this.isOpen && this.isInhabited() && this.hasStoredItem();
    }

    @Nullable
    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        return saveWithoutMetadata(registries);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, TombBlockEntity entity) {
        if (level.isClientSide()) return;

        if (entity.isAnimating) {
            entity.animationTimer++;
            int maxTicks = entity.isOpen ? OPEN_ANIMATION_DURATION : CLOSE_ANIMATION_DURATION;

            if (entity.animationTimer >= maxTicks) {
                entity.finishAnimation();
            }
        }
    }

    public void toggleTomb() {
        if (this.isAnimating) return;

        this.isOpen = !this.isOpen;
        this.startAnimation();
        this.playSound();
        this.syncToClients();
    }

    public boolean isOpen() {
        return this.isOpen;
    }

    public boolean isAnimating() {
        return this.isAnimating;
    }

    public boolean isInhabited() {
        return this.getBlockState().getValue(TombBlock.INHABITED);
    }

    private void startAnimation() {
        this.isAnimating = true;
        this.animationTimer = 0;
        this.setChanged();
    }

    private void finishAnimation() {
        this.isAnimating = false;
        this.animationTimer = 0;
        this.setChanged();
        this.syncToClients();
    }

    private void playSound() {
        if (this.level != null) {
            if (this.isOpen) {
                this.level.playSound(null, this.getBlockPos(), SoundEvents.GRINDSTONE_USE, SoundSource.BLOCKS, 1, 0.4f);
            } else {
                this.level.playSound(null, this.getBlockPos(), SoundEvents.GRINDSTONE_USE, SoundSource.BLOCKS, 1, 0.7f);
            }
        }
    }

    private void syncToClients() {
        if (this.level != null) {
            this.level.sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), 3);
        }
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar registrar) {
        registrar.add(new AnimationController<>(this, "controller", 0, this::animationPredicate));
    }

    private PlayState animationPredicate(AnimationState<TombBlockEntity> state) {
        if (this.isAnimating) {
            String animationName = this.isOpen ? "open" : "close";
            state.getController().setAnimation(RawAnimation.begin().then(animationName, Animation.LoopType.HOLD_ON_LAST_FRAME));
        } else if (this.isOpen) {
            state.getController().setAnimation(RawAnimation.begin().then("idle_open", Animation.LoopType.LOOP));
        }
        return PlayState.CONTINUE;
    }

    @Override
    public double getTick(Object blockEntity) {
        return RenderUtil.getCurrentTick();
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }
}