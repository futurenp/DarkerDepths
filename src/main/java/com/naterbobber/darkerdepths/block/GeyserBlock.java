package com.naterbobber.darkerdepths.block;

import com.mojang.serialization.MapCodec;
import com.naterbobber.darkerdepths.block.blockentities.GeyserBlockEntity;
import com.naterbobber.darkerdepths.init.DDBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import org.jetbrains.annotations.Nullable;

public class GeyserBlock extends BaseEntityBlock {
    public static final DirectionProperty FACING = BlockStateProperties.FACING;
    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;

    public GeyserBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(POWERED, false).setValue(FACING, Direction.UP));
    }

    //?
    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return null;
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(POWERED, context.getLevel().hasNeighborSignal(context.getClickedPos())).setValue(FACING, context.getClickedFace());
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return DDBlockEntityTypes.GEYSER.get().create(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level worldIn, BlockState state, BlockEntityType<T> blockEntityType) {
        if (worldIn.isClientSide) {
            return !state.getValue(POWERED) ? createTickerHelper(blockEntityType, DDBlockEntityTypes.GEYSER.get(), GeyserBlockEntity::geyserTick) : null;
        } else {
            return createTickerHelper(blockEntityType, DDBlockEntityTypes.GEYSER.get(), GeyserBlockEntity::geyserTick);
        }
    }

    @Override
    public void neighborChanged(BlockState state, Level worldIn, BlockPos pos, Block p_60512_, BlockPos p_60513_, boolean p_60514_) {
        if (!worldIn.isClientSide()) {
            boolean isPowered = state.getValue(POWERED);
            if (isPowered != worldIn.hasNeighborSignal(pos)) {
                if (isPowered) {
                    worldIn.scheduleTick(pos, this, 4);
                } else {
                    worldIn.setBlock(pos, state.cycle(POWERED), 2);
                }
            }
        }
    }

    @Override
    public void tick(BlockState state, ServerLevel worldIn, BlockPos pos, RandomSource p_60465_) {
        if (state.getValue(POWERED)) {
            if (!worldIn.hasNeighborSignal(pos)) {
                worldIn.setBlock(pos, state.cycle(POWERED), 2);
            } else {

            }
        }
    }

    @Override
    public void animateTick(BlockState stateIn, Level worldIn, BlockPos pos, RandomSource rand) {
        if (!stateIn.getValue(POWERED)) {
            this.addParticle(worldIn, rand, pos, stateIn.getValue(FACING));
        }
    }

    private void addParticle(Level worldIn, RandomSource rand, BlockPos pos, Direction facing) {
        BlockPos frontState = null;

        double x = pos.getX(), y = pos.getY(), z = pos.getZ();
        double xSpeed = 0, ySpeed = 0, zSpeed = 0;
        double speed = 0.07;
        float lavaSpeedX = 2, lavaSpeedY = 2, lavaSpeedZ = 2, lavaSpeedFront = 2000;

        switch (facing) {
            case UP:
                frontState = pos.above();
                ySpeed = speed;
                x += 0.5;
                z += 0.5;
                lavaSpeedY = lavaSpeedFront;
                break;
            case DOWN:
                frontState = pos.below();
                ySpeed = -speed;
                x += 0.5;
                z += 0.5;
                lavaSpeedY = -lavaSpeedFront;
                break;
            case NORTH:
                frontState = pos.north();
                zSpeed = -speed;
                y += 0.5;
                x += 0.5;
                lavaSpeedZ = lavaSpeedFront;
                break;
            case EAST:
                frontState = pos.east();
                xSpeed = speed;
                y += 0.5;
                x += 0.5;
                z += 0.5;
                lavaSpeedX = lavaSpeedFront;
                break;
            case SOUTH:
                frontState = pos.south();
                zSpeed = speed;
                y += 0.5;
                x += 0.5;
                z += 0.5;
                lavaSpeedZ = -lavaSpeedFront;
                break;
            case WEST:
                frontState = pos.west();
                xSpeed = -speed;
                y += 0.5;
                z += 0.5;
                lavaSpeedX = -lavaSpeedFront;
                break;
        }

        boolean waterlogged = worldIn.getBlockState(frontState).is(Blocks.WATER);

        if (waterlogged) {
            for (int i = 1; i < 7; i++) {
                if (worldIn.isEmptyBlock(pos.above(i))) {
                    worldIn.addParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE, x, y, z, xSpeed, ySpeed, zSpeed);
                }
            }
            worldIn.addAlwaysVisibleParticle(ParticleTypes.BUBBLE_COLUMN_UP, x, y, z, xSpeed, ySpeed/2, zSpeed);
            worldIn.addAlwaysVisibleParticle(ParticleTypes.BUBBLE_COLUMN_UP, x + (double)rand.nextFloat(), y + (double)rand.nextFloat(), z + (double)rand.nextFloat(), xSpeed, ySpeed/2, zSpeed);
            if (rand.nextInt(200) == 0) {
                worldIn.playLocalSound(x, y, z, SoundEvents.BUBBLE_COLUMN_UPWARDS_AMBIENT, SoundSource.BLOCKS, 0.2F + rand.nextFloat() * 0.2F, 0.9F + rand.nextFloat() * 0.15F, false);
            }
        } else {
            worldIn.addParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE, x, y, z, xSpeed, ySpeed, zSpeed);
            if (rand.nextInt(5) == 0) {
                for (int i = 0; i < rand.nextInt(1) + 1; i++) {
                    worldIn.addParticle(ParticleTypes.LAVA, x, y, z, rand.nextFloat() / lavaSpeedX, rand.nextFloat() / lavaSpeedY, rand.nextFloat() / lavaSpeedZ);
                }
            }
        }




    }

    @Override
    public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor worldIn, BlockPos currentPos, BlockPos facingPos) {
        if (facing == Direction.UP && facingState.is(Blocks.WATER)) {
            worldIn.scheduleTick(currentPos, this, 20);
        }
        return super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
    }

    @Override
    public void randomTick(BlockState p_60551_, ServerLevel worldIn, BlockPos pos, RandomSource rand) {
        BlockPos blockpos = pos.above();
        if (worldIn.getFluidState(pos).is(FluidTags.WATER)) {
            worldIn.playSound(null, pos, SoundEvents.FIRE_EXTINGUISH, SoundSource.BLOCKS, 0.5F, 2.6F + (worldIn.random.nextFloat() - worldIn.random.nextFloat()) * 0.8F);
            worldIn.sendParticles(ParticleTypes.LARGE_SMOKE, (double)blockpos.getX() + 0.5D, (double)blockpos.getY() + 0.25D, (double)blockpos.getZ() + 0.5D, 8, 0.5D, 0.25D, 0.5D, 0.0D);
        }
    }

    @Override
    public void onPlace(BlockState p_60566_, Level worldIn, BlockPos pos, BlockState p_60569_, boolean p_60570_) {
        worldIn.scheduleTick(pos, this, 1);
    }

    @Override
    public RenderShape getRenderShape(BlockState p_49232_) {
        return RenderShape.MODEL;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(POWERED, FACING);
    }

}
