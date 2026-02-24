package com.naterbobber.darkerdepths.block.blockentities;

import com.naterbobber.darkerdepths.block.DDBlockStateProperties;
import com.naterbobber.darkerdepths.block.custom.GeyserBlock;
import com.naterbobber.darkerdepths.init.DDBlockEntityTypes;
import com.naterbobber.darkerdepths.util.DDTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.levelgen.feature.DripstoneUtils;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class GeyserBlockEntity extends BlockEntity {
    private static final BooleanProperty BURSTING = DDBlockStateProperties.BURSTING;
    private static final String burstDelayTag = "burstDelay";
    private static final String burstLengthTag = "burstLength";
    private static final int minBurstLength = 100;
    private static final int minBurstDelay = 500;
    private int currentBurstLength = minBurstLength;
    private int currentBurstDelay = minBurstDelay;

    public GeyserBlockEntity(BlockPos pos, BlockState state) {
        super(DDBlockEntityTypes.GEYSER.get(), pos, state);
    }

    public void tick(Level level, BlockPos blockPos, BlockState blockState) {
        if (level.isClientSide()) return;

        if (isBursting()) {
            updateBurstLength(level, blockState, blockPos);
        } else {
            updateBurstDelay(level, blockState, blockPos);
            return;
        }

        Direction direction = blockState.getValue(GeyserBlock.FACING);
        BlockPos relativePosition;
        BlockState relativeState;
        int boostColumnLength = 6;

        for (int i = 0; i < boostColumnLength; i++) {
            relativePosition = blockPos.relative(direction, i + 1);
            relativeState = level.getBlockState(relativePosition);
            if (level.isStateAtPosition(relativePosition, DripstoneUtils::isEmptyOrWaterOrLava) ||
                    relativeState.getTags().anyMatch(DDTags.Blocks.GEYSER_BYPASSES::equals)) {
                boostEntities(level, direction, blockPos, relativePosition);
            }
        }
    }

    private boolean isBursting() {
        return this.getBlockState().getValue(BURSTING);
    }

    private static void boostEntities(Level level, Direction direction, BlockPos originPos, BlockPos blockPos) {
        List<Entity> nearbyEntities = level.getEntitiesOfClass(Entity.class, new AABB(blockPos));
        double boostSpeed = 0.06;
        double booster = (level.getBlockState(originPos.relative(direction.getOpposite())).is(DDTags.Blocks.GEYSER_BOOSTERS)
                ? boostSpeed * 2
                : boostSpeed) * direction.getAxisDirection().getStep();


        for (Entity entity : nearbyEntities) {
            Vec3 motion = entity.getDeltaMovement();
            double xBooster = direction.getAxis() == Direction.Axis.X ? booster : 0.0D;
            double yBooster = direction.getAxis() == Direction.Axis.Y ? booster : 0.0D;
            double zBooster = direction.getAxis() == Direction.Axis.Z ? booster : 0.0D;

            entity.setDeltaMovement(motion.x + xBooster, motion.y + yBooster, motion.z + zBooster);
            entity.fallDistance = 0.0F;

            if (entity instanceof ServerPlayer serverPlayer) {
                serverPlayer.connection.send(new ClientboundSetEntityMotionPacket(entity));
            }
        }
    }

    private void updateBurstDelay(Level level, BlockState blockState, BlockPos blockPos) {
        if(currentBurstDelay == 0) {
            setBursting(level, blockState, blockPos, true);
            currentBurstDelay = minBurstDelay + (int)(Math.random() * 1000);
        } else {
            currentBurstDelay--;
        }
        setChanged();
    }

    private void updateBurstLength(Level level, BlockState blockState, BlockPos blockPos) {
        if(currentBurstLength == 0) {
            setBursting(level, blockState, blockPos, false);
            currentBurstLength = minBurstLength + (int)(Math.random() * 100);
        } else {
            currentBurstLength--;
        }
        setChanged();
    }

    private void setBursting(Level level, BlockState blockState, BlockPos blockPos, boolean value) {
        BlockState newBlockState = blockState.setValue(BURSTING, value);
        level.setBlock(blockPos, newBlockState, 3);
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.putInt(burstDelayTag, currentBurstDelay);
        tag.putInt(burstLengthTag, currentBurstLength);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        currentBurstDelay = tag.getInt(burstDelayTag);
        currentBurstLength = tag.getInt(burstLengthTag);
    }
}
