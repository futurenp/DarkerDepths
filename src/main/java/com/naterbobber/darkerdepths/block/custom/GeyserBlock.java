package com.naterbobber.darkerdepths.block.custom;

import com.mojang.serialization.MapCodec;
import com.naterbobber.darkerdepths.block.DDBlockStateProperties;
import com.naterbobber.darkerdepths.block.blockentities.GeyserBlockEntity;
import com.naterbobber.darkerdepths.init.DDBlockEntityTypes;
import com.naterbobber.darkerdepths.init.DDBlocks;
import com.naterbobber.darkerdepths.util.DDTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class GeyserBlock extends BaseEntityBlock {
    public static final DirectionProperty FACING = BlockStateProperties.FACING;
    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
    public static final BooleanProperty BURSTING = DDBlockStateProperties.BURSTING;
    private static final IntegerProperty HEAT_LEVEL = DDBlockStateProperties.HEAT_LEVEL;
    private static final BooleanProperty BOOSTED = DDBlockStateProperties.BOOSTED;
    private static final BooleanProperty PROVIDES_ASH = DDBlockStateProperties.PROVIDES_ASH;
    public static final MapCodec<GeyserBlock> CODEC = simpleCodec(GeyserBlock::new);

    public GeyserBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(POWERED, false)
                .setValue(FACING, Direction.UP)
                .setValue(BURSTING, false)
                .setValue(HEAT_LEVEL, 0)
                .setValue(BOOSTED, false)
                .setValue(PROVIDES_ASH, false)
        );
    }

    @Override
    protected @NotNull MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
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
        return this.defaultBlockState()
                .setValue(POWERED, context.getLevel().hasNeighborSignal(context.getClickedPos()))
                .setValue(FACING, context.getClickedFace())
                .setValue(BOOSTED, checkModifier(context).isBoosted())
                .setValue(PROVIDES_ASH, checkModifier(context).isAshProvider());
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
            return createTickerHelper(blockEntityType, DDBlockEntityTypes.GEYSER.get(), (level, pos, blockState, blockEntity) -> blockEntity.clientTick(level, pos, blockState));
        } else {
            return createTickerHelper(blockEntityType, DDBlockEntityTypes.GEYSER.get(), (level, pos, blockState, blockEntity) -> blockEntity.tick(level, pos, blockState));
        }
    }

    @Override
    public void neighborChanged(BlockState state, Level worldIn, BlockPos pos, Block block, BlockPos fromPos, boolean p_60514_) {
        if (worldIn.isClientSide()) return;

        boolean isPowered = state.getValue(POWERED);
        if (isPowered != worldIn.hasNeighborSignal(pos)) {
            if (isPowered) {
                worldIn.scheduleTick(pos, this, 4);
            } else {
                worldIn.setBlock(pos, state.cycle(POWERED), 2);
            }
        }

        var currentModifier = checkModifier(worldIn, pos, state.getValue(FACING));

        if(state.getValue(BOOSTED) == currentModifier.isBoosted() && state.getValue(PROVIDES_ASH) == currentModifier.isAshProvider()) {
            return;
        }

        var newBlockState = state
                .setValue(BOOSTED, currentModifier.isBoosted())
                .setValue(PROVIDES_ASH, currentModifier.isAshProvider());

        worldIn.setBlock(pos, newBlockState, 3);
    }

    @Override
    public void tick(BlockState state, ServerLevel worldIn, BlockPos pos, RandomSource p_60465_) {
        if (state.getValue(POWERED)) {
            if (!worldIn.hasNeighborSignal(pos)) {
                worldIn.setBlock(pos, state.cycle(POWERED), 2);

                var newState = worldIn.getBlockState(pos);
                if(!state.getValue(BURSTING)) {
                    GeyserBlockEntity.setBursting(worldIn, newState, pos, true);
                }
            }
        }
    }

    @Override
    public void animateTick(BlockState stateIn, Level worldIn, BlockPos pos, RandomSource rand) {
        double x = pos.getX(), y = pos.getY() + 1, z = pos.getZ();
        float lavaSpeed = 2;

        if (rand.nextInt(5) == 0) {
            for (int i = 0; i < rand.nextInt(1) + 1; i++) {
                worldIn.addParticle(ParticleTypes.LAVA, x, y, z, rand.nextFloat() / lavaSpeed, rand.nextFloat() / lavaSpeed, rand.nextFloat() / lavaSpeed);
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
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource rand) {
        BlockPos blockpos = pos.above();
        if (level.getFluidState(pos).is(FluidTags.WATER)) {
            level.playSound(null, pos, SoundEvents.FIRE_EXTINGUISH, SoundSource.BLOCKS, 0.5F, 2.6F + (level.random.nextFloat() - level.random.nextFloat()) * 0.8F);
            level.sendParticles(ParticleTypes.LARGE_SMOKE, (double)blockpos.getX() + 0.5D, (double)blockpos.getY() + 0.25D, (double)blockpos.getZ() + 0.5D, 8, 0.5D, 0.25D, 0.5D, 0.0D);
        }

        if(!state.getValue(BURSTING) && !state.getValue(POWERED)) {
            GeyserBlockEntity.setBursting(level, state, pos, true);
        }
    }

    @Override
    public void onPlace(BlockState blockState, Level level, BlockPos blockPos, BlockState oldState, boolean movedByPiston) {
        level.scheduleTick(blockPos, this, 1);
    }

    private static Modifier checkModifier(BlockPlaceContext context) { {
        return checkModifier(context.getLevel(), context.getClickedPos(), context.getClickedFace());
    }}

    private static Modifier checkModifier(Level level, BlockPos blockPos, Direction direction) {
        var bottomPos = blockPos.relative(direction.getOpposite());
        var blockState = level.getBlockState(bottomPos);

        if(blockState.is(DDTags.Blocks.GEYSER_BOOSTERS)) {
            return Modifier.BOOST;
        } else if (blockState.is(DDBlocks.ASH_BLOCK)) {
            return Modifier.ASH;
        } else {
            return Modifier.NONE;
        }
    }

    @Override
    public RenderShape getRenderShape(BlockState p_49232_) {
        return RenderShape.MODEL;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(POWERED, FACING, BURSTING, HEAT_LEVEL, BOOSTED, PROVIDES_ASH);
    }

    private enum Modifier {
        BOOST,
        ASH,
        NONE;

        private boolean isBoosted() {
            return this == BOOST;
        }

        private boolean isAshProvider() {
            return this == ASH;
        }
    }

}
