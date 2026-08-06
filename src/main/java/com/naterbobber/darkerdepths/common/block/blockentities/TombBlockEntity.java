package com.naterbobber.darkerdepths.common.block.blockentities;

import com.naterbobber.darkerdepths.common.block.DDBlockStateProperties;
import com.naterbobber.darkerdepths.common.block.blockstates.TombUtils;
import com.naterbobber.darkerdepths.common.block.unique.TombBlock;
import com.naterbobber.darkerdepths.common.init.DDBlockEntityTypes;
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
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.animation.*;
import software.bernie.geckolib.util.RenderUtil;

public class TombBlockEntity extends BlockEntity implements GeoBlockEntity, Container {
    private static final int OPEN_ANIMATION_DURATION = 53;
    private static final int CLOSE_ANIMATION_DURATION = 40;
    private static final RawAnimation IDLE_OPEN_ANIM = RawAnimation.begin().then("idle_open", Animation.LoopType.HOLD_ON_LAST_FRAME);
    private static final RawAnimation OPEN_ANIM = RawAnimation.begin().then("open", Animation.LoopType.HOLD_ON_LAST_FRAME);
    private static final RawAnimation CLOSE_ANIM = RawAnimation.begin().then("close", Animation.LoopType.HOLD_ON_LAST_FRAME);

    private final AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);
    private NonNullList<ItemStack> items = NonNullList.withSize(1, ItemStack.EMPTY);

    private boolean isOpen = false;
    private boolean isAnimating = false;
    private int animationTimer = 0;
    private boolean mirroredTop = false;

    public TombBlockEntity(BlockPos pos, BlockState state) {
        super(DDBlockEntityTypes.TOMB.get(), pos, state);
    }

    @Override
    protected void loadAdditional(CompoundTag nbt, HolderLookup.Provider registries) {
        super.loadAdditional(nbt, registries);
        this.isOpen = nbt.getBoolean("IsOpen");
        this.isAnimating = nbt.getBoolean("IsAnimating");
        this.animationTimer = nbt.getInt("AnimationTimer");
        this.mirroredTop = nbt.getBoolean("MirroredTop");
        this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        ContainerHelper.loadAllItems(nbt, this.items, registries);
    }

    @Override
    protected void saveAdditional(CompoundTag nbt, HolderLookup.Provider registries) {
        super.saveAdditional(nbt, registries);
        nbt.putBoolean("IsOpen", this.isOpen);
        nbt.putBoolean("IsAnimating", this.isAnimating);
        nbt.putInt("AnimationTimer", this.animationTimer);
        nbt.putBoolean("MirroredTop", this.mirroredTop);
        ContainerHelper.saveAllItems(nbt, this.items, registries);
    }

    @Override
    public int getContainerSize() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return this.items.getFirst().isEmpty();
    }

    @Override
    public ItemStack getItem(int slot) {
        return slot == 0 ? this.items.getFirst() : ItemStack.EMPTY;
    }

    public ItemStack getItemStack() {
        return this.items.getFirst();
    }

    @Override
    public ItemStack removeItemNoUpdate(int slot) {
        if (slot == 0) {
            ItemStack result = this.items.getFirst();
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
            this.syncToClients();
        }
    }

    @Override
    public ItemStack removeItem(int slot, int amount) {
        if (slot == 0 && !this.items.getFirst().isEmpty()) {
            ItemStack result = ContainerHelper.removeItem(this.items, slot, amount);
            if (!result.isEmpty()) {
                this.setChanged();
                this.syncToClients();
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
            int timer = entity.animationTimer;

            var facing = state.getValue(HorizontalDirectionalBlock.FACING);
            var mirrored = entity.mirroredTop;
            var firstLandingPos = TombBlock.getPartPos(pos, mirrored ? TombUtils.Part.FRONT_RIGHT : TombUtils.Part.BACK_RIGHT, facing);
            var secondLandingPos = TombBlock.getPartPos(pos, mirrored ? TombUtils.Part.FRONT_LEFT : TombUtils.Part.BACK_LEFT, facing);
            var centerPos = mirrored ? TombBlock.getPartPos(pos, TombUtils.Part.BACK_CENTER, facing) : entity.getBlockPos();

            if(entity.isOpen) {
                if(timer == 20) {
                    level.playSound(null, firstLandingPos, SoundEvents.DEEPSLATE_HIT, SoundSource.BLOCKS, 0.75F, 0.3f);
                }

                if(timer == 24) {
                    level.playSound(null, secondLandingPos, SoundEvents.DEEPSLATE_HIT, SoundSource.BLOCKS, 0.85F, 0.3f);
                }
            } else {
                if (timer == maxTicks - 3) {
                    level.playSound(null, centerPos, SoundEvents.DEEPSLATE_HIT, SoundSource.BLOCKS, 1, 0.3f);
                }
            }

            if (timer >= maxTicks) {
                entity.finishAnimation();
            }
        }
    }

    public void toggleTomb(BlockPos clickedPos, Vec3 playerPos) {
        if (this.isAnimating) {
            return;
        }

        BlockState state = getBlockState();

        if (!this.isOpen) {
            var facing =
                    state.getValue(BlockStateProperties.HORIZONTAL_FACING);

            var tombCenter = Vec3.atCenterOf(clickedPos);
            var tombToPlayer = playerPos.subtract(tombCenter);
            var facingVector = Vec3.atLowerCornerOf(facing.getNormal());

            boolean openedFromFacingSide =
                    tombToPlayer.dot(facingVector) > 0;

            this.mirroredTop = !openedFromFacingSide;

            toggleOpenState(state);
        }

        this.isOpen = !this.isOpen;
        this.startAnimation();
        this.playOpenSound();
        this.syncToClients();
    }

    public boolean isOpen() {
        return this.isOpen;
    }

    public boolean isMirroredTop() {
        return this.mirroredTop;
    }

    public void setMirroredTop(boolean mirroredTop) {
        this.mirroredTop = mirroredTop;
    }

    public boolean isAnimating() {
        return this.isAnimating;
    }

    public boolean isInhabited() {
        return this.getBlockState().getValue(DDBlockStateProperties.INHABITED);
    }

    public boolean hasBed() {
        return this.getBlockState().getValue(DDBlockStateProperties.BED);
    }

    private void startAnimation() {
        this.isAnimating = true;
        this.animationTimer = 0;
        this.setChanged();
    }

    private void finishAnimation() {
        if(!this.isOpen) {
            toggleOpenState(getBlockState());
        }

        this.isAnimating = false;
        this.animationTimer = 0;
        this.setChanged();
        this.syncToClients();
    }

    private void toggleOpenState(BlockState state) {
        if(state.getBlock() instanceof TombBlock tombBlock) {
            var newState = state.setValue(BlockStateProperties.OPEN, !state.getValue(BlockStateProperties.OPEN));
            tombBlock.updateMultiblockState(level, getBlockPos(), newState);
        }
    }

    private void playOpenSound() {
        if (this.level != null) {
            if (this.isOpen) {
                this.level.playSound(null, this.getBlockPos(), SoundEvents.GRINDSTONE_USE, SoundSource.BLOCKS, 1, 0.3f);
            } else {
                this.level.playSound(null, this.getBlockPos(), SoundEvents.GRINDSTONE_USE, SoundSource.BLOCKS, 1, 0.55f);
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
            var animation = this.isOpen ? OPEN_ANIM : CLOSE_ANIM;
            state.getController().setAnimation(animation);
        } else if (this.isOpen) {
            state.getController().setAnimation(IDLE_OPEN_ANIM);
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