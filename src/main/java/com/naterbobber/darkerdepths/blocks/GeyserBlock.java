package com.naterbobber.darkerdepths.blocks;

import com.naterbobber.darkerdepths.blocks.blockentities.GeyserBlockEntity;
import com.naterbobber.darkerdepths.init.DDBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import org.jetbrains.annotations.Nullable;

import net.minecraft.util.RandomSource;

public class GeyserBlock extends BaseEntityBlock {
    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;

    public GeyserBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(POWERED, false));
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(POWERED, context.getLevel().hasNeighborSignal(context.getClickedPos()));
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
        if (state.getValue(POWERED) && !worldIn.hasNeighborSignal(pos)) {
            worldIn.setBlock(pos, state.cycle(POWERED), 2);
        }
    }

    @Override
    public void animateTick(BlockState stateIn, Level worldIn, BlockPos pos, RandomSource rand) {
        int xPos = pos.getX();
        int yPos = pos.getY();
        int zPos = pos.getZ();
        double x = xPos + 0.5D;
        double y = yPos + rand.nextDouble() + rand.nextDouble();
        double z = zPos + 0.5D;
        if (!stateIn.getValue(POWERED)) {
            this.addParticle(worldIn, rand, x, y, z, pos, worldIn.getBlockState(pos.above()).is(Blocks.WATER));
        }
    }

    private void addParticle(Level worldIn, RandomSource rand, double x, double y, double z, BlockPos pos, boolean waterlogged) {
        if (waterlogged) {
            for (int i = 1; i < 7; i++) {
                if (worldIn.isEmptyBlock(pos.above(i))) {
                    worldIn.addParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE, x, y, z, 0.0D, 0.07D, 0.0D);
                }
            }
            worldIn.addAlwaysVisibleParticle(ParticleTypes.BUBBLE_COLUMN_UP, x, y, z, 0.0D, 0.04D, 0.0D);
            worldIn.addAlwaysVisibleParticle(ParticleTypes.BUBBLE_COLUMN_UP, x + (double)rand.nextFloat(), y + (double)rand.nextFloat(), z + (double)rand.nextFloat(), 0.0D, 0.04D, 0.0D);
            if (rand.nextInt(200) == 0) {
                worldIn.playLocalSound(x, y, z, SoundEvents.BUBBLE_COLUMN_UPWARDS_AMBIENT, SoundSource.BLOCKS, 0.2F + rand.nextFloat() * 0.2F, 0.9F + rand.nextFloat() * 0.15F, false);
            }
        } else {
            worldIn.addParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE, x, y, z, 0.0D, 0.07D, 0.0D);
            if (rand.nextInt(5) == 0) {
                for (int i = 0; i < rand.nextInt(1) + 1; i++) {
                    worldIn.addParticle(ParticleTypes.LAVA, (double) pos.getX() + 0.5D, (double) pos.getY() + 0.5D, (double) pos.getZ() + 0.5D, rand.nextFloat() / 2.0F, 5.0E-5D, rand.nextFloat() / 2.0F);
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
        builder.add(POWERED);
    }
}
