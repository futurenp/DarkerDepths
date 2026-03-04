package com.naterbobber.darkerdepths.block.blockentities;

import com.naterbobber.darkerdepths.block.DDBlockStateProperties;
import com.naterbobber.darkerdepths.block.custom.GeyserBlock;
import com.naterbobber.darkerdepths.init.DDBlockEntityTypes;
import com.naterbobber.darkerdepths.init.DDParticleTypes;
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
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.levelgen.feature.DripstoneUtils;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class GeyserBlockEntity extends BlockEntity {
    private static final BooleanProperty BURSTING = DDBlockStateProperties.BURSTING;
    private static final IntegerProperty HEAT_LEVEL = DDBlockStateProperties.HEAT_LEVEL;
    private static final String burstDelayTag = "burstDelay";
    private static final String burstLengthTag = "burstLength";
    private static final int minBurstLength = 100;
    private static final int minBurstDelay = 20;
    private int currentBurstLength = minBurstLength;
    private int currentBurstDelay = minBurstDelay;

    public GeyserBlockEntity(BlockPos pos, BlockState state) {
        super(DDBlockEntityTypes.GEYSER.get(), pos, state);
    }

    public void tick(Level level, BlockPos blockPos, BlockState blockState) {
        if (blockState.getValue(BURSTING)) {
            updateBurstLength(level, blockState, blockPos);
        } else {
            updateBurstDelay(level, blockState, blockPos);
            return;
        }

        Direction direction = blockState.getValue(GeyserBlock.FACING);
        BlockPos relativePosition;
        BlockState relativeState;
        int boostColumnLength = 6;

        double boostSpeed = 0.06;
        double booster = (level.getBlockState(blockPos.relative(direction.getOpposite())).is(DDTags.Blocks.GEYSER_BOOSTERS)
                ? boostSpeed * 2
                : boostSpeed) * direction.getAxisDirection().getStep();

        for (int i = 0; i < boostColumnLength; i++) {
            relativePosition = blockPos.relative(direction, i + 1);
            relativeState = level.getBlockState(relativePosition);
            if (level.isStateAtPosition(relativePosition, DripstoneUtils::isEmptyOrWaterOrLava) ||
                    relativeState.getTags().anyMatch(DDTags.Blocks.GEYSER_BYPASSES::equals)) {
                boostEntities(level, direction, relativePosition, booster);
            } else {
                break;
            }
        }
    }

    public void clientTick(Level level, BlockPos blockPos, BlockState blockState) {
        if (blockState.getValue(BURSTING)) {
            double x = blockPos.getX();
            double y = blockPos.getY();
            double z = blockPos.getZ();
            double ySpeed = 1;

            var rand = level.getRandom();
            for (int i = 0; i < 5; i++) {
                double randX = rand.nextDouble();
                double randY = rand.nextDouble();
                double randZ = rand.nextDouble();
                level.addAlwaysVisibleParticle(DDParticleTypes.GEYSER_BURST_SMOKE.get(), x + randX, y + randY, z + randZ, 0, ySpeed + randY/2, 0);
            }
        }
    }

    private static void boostEntities(Level level, Direction direction, BlockPos blockPos, double booster) {
        List<Entity> nearbyEntities = level.getEntitiesOfClass(Entity.class, new AABB(blockPos));

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
            if(blockState.getValue(HEAT_LEVEL) > 0 && currentBurstDelay % 20 == 0) {
                updateHeatLevel(level, blockState, blockPos);
            }
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
        BlockState newBlockState = blockState.setValue(BURSTING, value).setValue(HEAT_LEVEL, 4);
        level.setBlock(blockPos, newBlockState, 3);
    }

    private void updateHeatLevel(Level level, BlockState blockState, BlockPos blockPos) {
        int heatLevel = blockState.getValue(HEAT_LEVEL);
        if(heatLevel > 0) {
            heatLevel--;
        }
        BlockState newBlockState = blockState.setValue(HEAT_LEVEL, heatLevel);
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
