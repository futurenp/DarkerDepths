package com.naterbobber.darkerdepths.blocks.blockentities;

import com.naterbobber.darkerdepths.init.DDBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

/**
 * The Block Entity for the Tomb. This can be used to store data like
 * inventory, spawn timers, or other custom information for the tomb.
 */
public class TombBlockEntity extends BlockEntity {

    // Add your data here, for example an ItemHandler for an inventory
    // private final ItemStackHandler itemHandler = new ItemStackHandler(27) { ... };
    // private final LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.of(() -> itemHandler);

    public TombBlockEntity(BlockPos pos, BlockState state) {
        super(DDBlockEntityTypes.TOMB.get(), pos, state);
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        // Load your data from NBT
        // itemHandler.deserializeNBT(nbt.getCompound("inventory"));
    }

    @Override
    protected void saveAdditional(CompoundTag nbt) {
        // Save your data to NBT
        // nbt.put("inventory", itemHandler.serializeNBT());
        super.saveAdditional(nbt);
    }

    // This is necessary to sync data to the client
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

        // Add server-side ticking logic here, e.g., for a mob spawner
    }
}
