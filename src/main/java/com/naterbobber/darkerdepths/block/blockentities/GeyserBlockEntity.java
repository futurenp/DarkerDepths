package com.naterbobber.darkerdepths.block.blockentities;

import com.naterbobber.darkerdepths.block.DDBlockStateProperties;
import com.naterbobber.darkerdepths.block.custom.GeyserBlock;
import com.naterbobber.darkerdepths.init.DDBlockEntityTypes;
import com.naterbobber.darkerdepths.init.DDParticleTypes;
import com.naterbobber.darkerdepths.util.DDTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class GeyserBlockEntity extends BlockEntity {
    private static final BooleanProperty BURSTING = DDBlockStateProperties.BURSTING;
    private static final IntegerProperty HEAT_LEVEL = DDBlockStateProperties.HEAT_LEVEL;
    private static final BooleanProperty POWERED = BlockStateProperties.POWERED;
    private static final BooleanProperty BOOSTED = DDBlockStateProperties.BOOSTED;
    private static final String burstDelayTag = "burstDelay";
    private static final String burstLengthTag = "burstLength";
    private static final int minBurstLength = 60;
    private static final int minBurstDelay = 100;
    private int currentBurstLength = minBurstLength;
    private int currentBurstDelay = minBurstDelay;

    public GeyserBlockEntity(BlockPos pos, BlockState state) {
        super(DDBlockEntityTypes.GEYSER.get(), pos, state);
    }

    public void tick(Level level, BlockPos blockPos, BlockState blockState) {
        if(blockState.getValue(POWERED)) {
            setBursting(level, blockState, blockPos, false);
            currentBurstDelay = 0;
            currentBurstLength = 100;
            return;
        }

        Direction direction = blockState.getValue(GeyserBlock.FACING);

        if (blockState.getValue(BURSTING)) {
            updateBurstLength(level, blockState, blockPos);
        } else if (isExposed(level, blockPos, direction)){
            updateBurstDelay(level, blockState, blockPos);
            return;
        }

        int boostColumnLength = findColumnLength(level, blockPos, direction);
        boostEntities(level, direction, blockPos, boostColumnLength);
    }

    private static int findColumnLength(Level level, BlockPos blockPos, Direction direction){
        boolean boosted = level.getBlockState(blockPos).getValue(BOOSTED);
        int boostColumnLength = boosted ? 8 : 6;
        BlockPos relativePosition;
        BlockState relativeState;
        FluidState relativeFluidState;

        for(int i = 1; i <= boostColumnLength; i++) {
            relativePosition = blockPos.relative(direction, i);
            relativeState = level.getBlockState(relativePosition);
            relativeFluidState = level.getFluidState(relativePosition);
            if (!(relativeState.isAir() ||
                    relativeFluidState.is(FluidTags.LAVA) ||
                    relativeState.is(DDTags.Blocks.GEYSER_BYPASSES) ||
                    relativeFluidState.is(FluidTags.WATER))) {
                return i;
            }
        }

        return boostColumnLength;
    }

    private boolean isExposed(Level level, BlockPos blockPos, Direction direction) {
        var frontState = level.getBlockState(blockPos.relative(direction));
        var frontFluidState = level.getFluidState(blockPos.relative(direction));
        return frontState.isAir()
                || frontFluidState.is(FluidTags.WATER)
                || frontFluidState.is(FluidTags.LAVA);
    }

    public void clientTick(Level level, BlockPos blockPos, BlockState blockState) {
        if (blockState.getValue(BURSTING)) {
            sendDirectionalParticles(level, blockPos, blockState);
        }
    }

    private static void sendDirectionalParticles(Level level, BlockPos blockPos, BlockState blockState) {
        var direction = blockState.getValue(BlockStateProperties.FACING);
        var isUnderWater = level.getFluidState(blockPos.relative(direction)).is(FluidTags.WATER);
        var boosted = blockState.getValue(BOOSTED);

        if(isUnderWater) {
            sendParticleType(level, blockPos, ParticleTypes.CLOUD, direction,10, boosted ? 1 : 0.5);
            sendParticleType(level, blockPos, boosted
                            ? DDParticleTypes.GEYSER_BURST_SMOKE_BOOSTED.get()
                            : DDParticleTypes.GEYSER_BURST_SMOKE.get(),
                    direction, 5, 0.75);
        } else {
            sendParticleType(level, blockPos, ParticleTypes.LARGE_SMOKE, direction,5, boosted ? 1 : 0.5);
            sendParticleType(level, blockPos, boosted
                            ? DDParticleTypes.GEYSER_BURST_SMOKE_BOOSTED.get()
                            : DDParticleTypes.GEYSER_BURST_SMOKE.get(),
                    direction, 5, 1);
        }
    }

    private static void sendParticleType(Level level, BlockPos blockPos, ParticleOptions particle, Direction direction, int amount, double speed) {
        double x = blockPos.getX() + direction.getStepX();
        double y = blockPos.getY() + direction.getStepY();
        double z = blockPos.getZ() + direction.getStepZ();
        double xSpeed = direction.getStepX() * speed;
        double ySpeed = direction.getStepY() * speed;
        double zSpeed = direction.getStepZ() * speed;
        var rand = level.getRandom();

        for (int i = 0; i < amount; i++) {
            double randX = rand.nextDouble();
            double randY = rand.nextDouble();
            double randZ = rand.nextDouble();
            level.addAlwaysVisibleParticle(
                    particle,
                    x + randX,
                    y + randY,
                    z + randZ,
                    xSpeed == 0 ? 0 : xSpeed + randX/2 - 0.25,
                    ySpeed == 0 ? 0 : ySpeed + randY/2 - 0.25,
                    zSpeed == 0 ? 0 : zSpeed + randZ/2 - 0.25
            );
        }
    }


    private static void boostEntities(Level level, Direction direction, BlockPos blockPos, int length) {
        double boostSpeed = 0.14;
        boolean boosted = level.getBlockState(blockPos).getValue(BOOSTED);
        double boost = boostSpeed * direction.getAxisDirection().getStep() * (boosted ? 2 : 1);

        var boostArea = AABB.encapsulatingFullBlocks(blockPos, blockPos.relative(direction, length));
        List<Entity> nearbyEntities = level.getEntitiesOfClass(Entity.class, boostArea);

        for (Entity entity : nearbyEntities) {
            Vec3 motion = entity.getDeltaMovement();
            if(entity instanceof Player) {
                boost *= ((Player) entity).isFallFlying() ? 2 : 1;
            }
            double xBooster = direction.getAxis() == Direction.Axis.X ? boost : 0.0D;
            double yBooster = direction.getAxis() == Direction.Axis.Y ? boost : 0.0D;
            double zBooster = direction.getAxis() == Direction.Axis.Z ? boost : 0.0D;

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
            level.playSound(null, blockPos, SoundEvents.LAVA_EXTINGUISH, SoundSource.BLOCKS, 2.25f, 0.25f);

            if(level instanceof ServerLevel &&
                    level.getFluidState(blockPos.above()).is(FluidTags.LAVA)
            ) {
                ((ServerLevel) level).sendParticles(
                        ParticleTypes.LAVA,
                        blockPos.getX() + 0.5,
                        blockPos.getY() + 0.9,
                        blockPos.getZ() + 0.5,
                        50,
                        0,
                        0,
                        0,
                        2);

            }

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
            currentBurstLength = minBurstLength + (int)(Math.random() * 20);
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
