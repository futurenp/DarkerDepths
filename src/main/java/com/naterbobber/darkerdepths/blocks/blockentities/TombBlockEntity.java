package com.naterbobber.darkerdepths.blocks.blockentities;

import com.naterbobber.darkerdepths.blocks.TombBlock;
import com.naterbobber.darkerdepths.init.DDBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.RenderUtils;

public class TombBlockEntity extends BlockEntity implements GeoBlockEntity {
    private static final int OPEN_ANIMATION_DURATION = 53;  // 2.625s * 20 ticks
    private static final int CLOSE_ANIMATION_DURATION = 40; // 1.9583s * 20 ticks

    private final AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);

    private boolean isOpen = false;
    private boolean isAnimating = false;
    private int animationTimer = 0;

    public TombBlockEntity(BlockPos pos, BlockState state) {
        super(DDBlockEntityTypes.TOMB.get(), pos, state);
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        this.isOpen = nbt.getBoolean("IsOpen");
        this.isAnimating = nbt.getBoolean("IsAnimating");
        this.animationTimer = nbt.getInt("AnimationTimer");
    }

    @Override
    protected void saveAdditional(CompoundTag nbt) {
        super.saveAdditional(nbt);
        nbt.putBoolean("IsOpen", this.isOpen);
        nbt.putBoolean("IsAnimating", this.isAnimating);
        nbt.putInt("AnimationTimer", this.animationTimer);
    }

    @Nullable
    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag() {
        return saveWithoutMetadata();
    }

    public static void tick(Level level, BlockPos pos, BlockState state, TombBlockEntity entity) {
        if (level.isClientSide()) {
            return;
        }

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
        }
        return PlayState.CONTINUE;
    }

    @Override
    public double getTick(Object blockEntity) {
        return RenderUtils.getCurrentTick();
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    @Override
    public AABB getRenderBoundingBox() {
        BlockPos pos = this.getBlockPos();
        BlockState state = this.getBlockState();

        if (state.getBlock() instanceof TombBlock) {
            Direction facing = state.getValue(TombBlock.FACING);

            return switch (facing) {
                case SOUTH -> new AABB(pos.offset(-2, -8, -1), pos.offset(3, 3, 8));
                case EAST -> new AABB(pos.offset(-1, -8, -2), pos.offset(8, 3, 3));
                case WEST -> new AABB(pos.offset(-7, -8, -2), pos.offset(2, 3, 3));
                default -> new AABB(pos.offset(-2, -8, -7), pos.offset(3, 3, 2));
            };
        }

        return super.getRenderBoundingBox();
    }
}