package com.naterbobber.darkerdepths.blocks.blockentities;

import com.naterbobber.darkerdepths.blocks.TombBlock;
import com.naterbobber.darkerdepths.init.DDBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
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

    private AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);
    public TombBlockEntity(BlockPos pos, BlockState state) {
        super(DDBlockEntityTypes.TOMB.get(), pos, state);
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
    }

    @Override
    protected void saveAdditional(CompoundTag nbt) {
        super.saveAdditional(nbt);
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

    public static void tick(Level level, BlockPos blockPos, BlockState blockState, TombBlockEntity pEntity) {
        if(level.isClientSide()) {
            return;
        }
    }

    @Override
    public double getTick(Object blockEntity) {
        return RenderUtils.getCurrentTick();
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>(this, "controller", 0, this::predicate));
    }

    private PlayState predicate(AnimationState<TombBlockEntity> tombBlockEntityAnimationState) {
        return PlayState.CONTINUE;


    }

    @Override
    public AABB getRenderBoundingBox() {
        BlockPos pos = getBlockPos();
        BlockState state = getBlockState();

        if (state.getBlock() instanceof TombBlock) {
            Direction facing = state.getValue(TombBlock.FACING);

            return switch (facing) {
                case NORTH -> new AABB(pos.offset(-1, 0, -1), pos.offset(2, 1, 1));
                case SOUTH -> new AABB(pos.offset(-1, 0, 0), pos.offset(2, 1, 2));
                case EAST -> new AABB(pos.offset(0, 0, -1), pos.offset(2, 1, 2));
                case WEST -> new AABB(pos.offset(-1, 0, -1), pos.offset(1, 1, 2));
                default -> new AABB(pos.offset(-1, 0, -1), pos.offset(2, 1, 2));
            };
        }

        return super.getRenderBoundingBox();
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }
}
