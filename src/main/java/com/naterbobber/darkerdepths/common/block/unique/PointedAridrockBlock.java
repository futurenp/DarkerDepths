package com.naterbobber.darkerdepths.common.block.unique;

import com.naterbobber.darkerdepths.common.init.DDBlocks;
import net.minecraft.world.level.block.*;

import com.google.common.annotations.VisibleForTesting;
import com.mojang.serialization.MapCodec;
import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ThrownTrident;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.DripstoneThickness;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class PointedAridrockBlock extends Block implements Fallable, SimpleWaterloggedBlock {
    public static final MapCodec<PointedAridrockBlock> CODEC = simpleCodec(PointedAridrockBlock::new);
    public static final DirectionProperty TIP_DIRECTION = BlockStateProperties.VERTICAL_DIRECTION;
    public static final EnumProperty<DripstoneThickness> THICKNESS = BlockStateProperties.DRIPSTONE_THICKNESS;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    private static final int MAX_SEARCH_LENGTH_WHEN_CHECKING_DRIP_TYPE = 11;
    private static final int DELAY_BEFORE_FALLING = 2;
    private static final float DRIP_PROBABILITY_PER_ANIMATE_TICK = 0.02F;
    private static final float DRIP_PROBABILITY_PER_ANIMATE_TICK_IF_UNDER_LIQUID_SOURCE = 0.12F;
    private static final int MAX_SEARCH_LENGTH_BETWEEN_STALACTITE_TIP_AND_CAULDRON = 11;
    public static final float WATER_TRANSFER_PROBABILITY_PER_RANDOM_TICK = 0.17578125F;
    private static final double MIN_TRIDENT_VELOCITY_TO_BREAK_DRIPSTONE = 0.6;
    private static final float STALACTITE_DAMAGE_PER_FALL_DISTANCE_AND_SIZE = 1.0F;
    private static final int STALACTITE_MAX_DAMAGE = 40;
    private static final int MAX_STALACTITE_HEIGHT_FOR_DAMAGE_CALCULATION = 6;
    private static final float STALAGMITE_FALL_DISTANCE_OFFSET = 2.0F;
    private static final int STALAGMITE_FALL_DAMAGE_MODIFIER = 2;
    private static final float AVERAGE_DAYS_PER_GROWTH = 5.0F;
    private static final float GROWTH_PROBABILITY_PER_RANDOM_TICK = 0.011377778F;
    private static final int MAX_GROWTH_LENGTH = 7;
    private static final int MAX_STALAGMITE_SEARCH_RANGE_WHEN_GROWING = 10;
    private static final float STALACTITE_DRIP_START_PIXEL = 0.6875F;
    private static final VoxelShape TIP_MERGE_SHAPE = Block.box(5.0, 0.0, 5.0, 11.0, 16.0, 11.0);
    private static final VoxelShape TIP_SHAPE_UP = Block.box(5.0, 0.0, 5.0, 11.0, 11.0, 11.0);
    private static final VoxelShape TIP_SHAPE_DOWN = Block.box(5.0, 5.0, 5.0, 11.0, 16.0, 11.0);
    private static final VoxelShape FRUSTUM_SHAPE = Block.box(4.0, 0.0, 4.0, 12.0, 16.0, 12.0);
    private static final VoxelShape MIDDLE_SHAPE = Block.box(3.0, 0.0, 3.0, 13.0, 16.0, 13.0);
    private static final VoxelShape BASE_SHAPE = Block.box(2.0, 0.0, 2.0, 14.0, 16.0, 14.0);
    private static final float MAX_HORIZONTAL_OFFSET = 0.125F;
    private static final VoxelShape REQUIRED_SPACE_TO_DRIP_THROUGH_NON_SOLID_BLOCK = Block.box(6.0, 0.0, 6.0, 10.0, 16.0, 10.0);

    @Override
    public MapCodec<PointedAridrockBlock> codec() {
        return CODEC;
    }

    public PointedAridrockBlock(BlockBehaviour.Properties properties) {
        super(properties);
        this.registerDefaultState(
            this.stateDefinition
                .any()
                .setValue(TIP_DIRECTION, Direction.UP)
                .setValue(THICKNESS, DripstoneThickness.TIP)
                .setValue(WATERLOGGED, Boolean.valueOf(false))
        );
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(TIP_DIRECTION, THICKNESS, WATERLOGGED);
    }

    @Override
    protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        return isValidPointedDripstonePlacement(level, pos, state.getValue(TIP_DIRECTION));
    }

    @Override
    protected BlockState updateShape(
        BlockState state, Direction p_direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos
    ) {
        if (state.getValue(WATERLOGGED)) {
            level.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        }

        if (p_direction != Direction.UP && p_direction != Direction.DOWN) {
            return state;
        } else {
            var direction = state.getValue(TIP_DIRECTION);
            if (direction == Direction.DOWN && level.getBlockTicks().hasScheduledTick(pos, this)) {
                return state;
            } else if (p_direction == direction.getOpposite() && !this.canSurvive(state, level, pos)) {
                if (direction == Direction.DOWN) {
                    level.scheduleTick(pos, this, 2);
                } else {
                    level.scheduleTick(pos, this, 1);
                }

                return state;
            } else {
                boolean flag = state.getValue(THICKNESS) == DripstoneThickness.TIP_MERGE;
                var dripstonethickness = calculateDripstoneThickness(level, pos, direction, flag);
                return state.setValue(THICKNESS, dripstonethickness);
            }
        }
    }

    @Override
    protected void onProjectileHit(Level level, BlockState state, BlockHitResult hit, Projectile projectile) {
        if (!level.isClientSide) {
            var blockpos = hit.getBlockPos();
            if (projectile.mayInteract(level, blockpos)
                && projectile.mayBreak(level)
                && projectile instanceof ThrownTrident
                && projectile.getDeltaMovement().length() > 0.6) {
                level.destroyBlock(blockpos, true);
            }
        }
    }

    @Override
    public void fallOn(Level level, BlockState state, BlockPos pos, Entity entity, float fallDistance) {
        if (state.getValue(TIP_DIRECTION) == Direction.UP && state.getValue(THICKNESS) == DripstoneThickness.TIP) {
            entity.causeFallDamage(fallDistance + 2.0F, 2.0F, level.damageSources().stalagmite());
        } else {
            super.fallOn(level, state, pos, entity, fallDistance);
        }
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        if (canDrip(state)) {
            float f = random.nextFloat();
            if (!(f > 0.12F)) {
                getFluidAboveStalactite(level, pos, state)
                    .filter(p_221848_ -> f < 0.02F || canFillCauldron(p_221848_.fluid))
                    .ifPresent(p_221881_ -> spawnDripParticle(level, pos, state, p_221881_.fluid));
            }
        }
    }

    @Override
    protected void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (isStalagmite(state) && !this.canSurvive(state, level, pos)) {
            level.destroyBlock(pos, true);
        } else {
            spawnFallingStalactite(state, level, pos);
        }
    }

    @Override
    protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        maybeTransferFluid(state, level, pos, random.nextFloat());
        if (random.nextFloat() < 0.011377778F && isStalactiteStartPos(state, level, pos)) {
            growStalactiteOrStalagmiteIfPossible(state, level, pos, random);
        }
    }

    @VisibleForTesting
    public static void maybeTransferFluid(BlockState state, ServerLevel level, BlockPos pos, float randChance) {
        if (!isStalactiteStartPos(state, level, pos)) {
            return;
        }
        var optional = getFluidAboveStalactite(level, pos, state).filter(info -> info.fluid == Fluids.WATER);
        if (optional.isEmpty()) {
            return;
        }
        var info = optional.get();
        var dripInfo = info.fluid.getFluidType().getDripInfo();
        if (dripInfo == null || randChance >= dripInfo.chance()) {
            return;
        }
        var tipPos = findTip(state, level, pos, 11, false);
        if (tipPos == null) {
            return;
        }
        if (info.sourceState.is(Blocks.MUD)) {
            var clay = Blocks.CLAY.defaultBlockState();
            level.setBlockAndUpdate(info.pos, clay);
            Block.pushEntitiesUp(info.sourceState, clay, level, info.pos);
            level.gameEvent(GameEvent.BLOCK_CHANGE, info.pos, GameEvent.Context.of(clay));
            level.levelEvent(1504, tipPos, 0);
        } else {
            var cauldronPos = findFillableCauldronBelowStalactiteTip(level, tipPos, info.fluid);
            if (cauldronPos != null) {
                level.levelEvent(1504, tipPos, 0);
                int delay = 50 + tipPos.getY() - cauldronPos.getY();
                var cauldronState = level.getBlockState(cauldronPos);
                level.scheduleTick(cauldronPos, cauldronState.getBlock(), delay);
            }
        }
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        var levelaccessor = context.getLevel();
        var blockpos = context.getClickedPos();
        var direction = context.getNearestLookingVerticalDirection().getOpposite();
        var direction1 = calculateTipDirection(levelaccessor, blockpos, direction);
        if (direction1 == null) {
            return null;
        } else {
            boolean flag = !context.isSecondaryUseActive();
            var dripstonethickness = calculateDripstoneThickness(levelaccessor, blockpos, direction1, flag);
            return dripstonethickness == null
                ? null
                : this.defaultBlockState()
                    .setValue(TIP_DIRECTION, direction1)
                    .setValue(THICKNESS, dripstonethickness)
                    .setValue(WATERLOGGED, Boolean.valueOf(levelaccessor.getFluidState(blockpos).getType() == Fluids.WATER));
        }
    }

    @Override
    protected FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    protected VoxelShape getOcclusionShape(BlockState state, BlockGetter level, BlockPos pos) {
        return Shapes.empty();
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        var dripstonethickness = state.getValue(THICKNESS);
        var voxelshape = switch (dripstonethickness) {
            case TIP_MERGE -> TIP_MERGE_SHAPE;
            case TIP -> state.getValue(TIP_DIRECTION) == Direction.DOWN ? TIP_SHAPE_DOWN : TIP_SHAPE_UP;
            case FRUSTUM -> FRUSTUM_SHAPE;
            case MIDDLE -> MIDDLE_SHAPE;
            case BASE -> BASE_SHAPE;
        };

        var vec3 = state.getOffset(level, pos);
        return voxelshape.move(vec3.x, 0.0, vec3.z);
    }

    @Override
    protected boolean isCollisionShapeFullBlock(BlockState state, BlockGetter level, BlockPos pos) {
        return false;
    }

    @Override
    protected float getMaxHorizontalOffset() {
        return 0.125F;
    }

    @Override
    public void onBrokenAfterFall(Level level, BlockPos pos, FallingBlockEntity fallingBlock) {
        if (!fallingBlock.isSilent()) {
            level.levelEvent(1045, pos, 0);
        }
    }

    @Override
    public DamageSource getFallDamageSource(Entity entity) {
        return entity.damageSources().fallingStalactite(entity);
    }

    private static void spawnFallingStalactite(BlockState state, ServerLevel level, BlockPos pos) {
        var blockpos$mutableblockpos = pos.mutable();
        var blockstate = state;

        while (isStalactite(blockstate)) {
            var fallingblockentity = FallingBlockEntity.fall(level, blockpos$mutableblockpos, blockstate);
            if (isTip(blockstate, true)) {
                int i = Math.max(1 + pos.getY() - blockpos$mutableblockpos.getY(), 6);
                float f = 1.0F * (float)i;
                fallingblockentity.setHurtsEntities(f, 40);
                break;
            }

            blockpos$mutableblockpos.move(Direction.DOWN);
            blockstate = level.getBlockState(blockpos$mutableblockpos);
        }
    }

    @VisibleForTesting
    public static void growStalactiteOrStalagmiteIfPossible(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        var blockstate = level.getBlockState(pos.above(1));
        var blockstate1 = level.getBlockState(pos.above(2));
        if (canGrow(blockstate, blockstate1)) {
            var blockpos = findTip(state, level, pos, 7, false);
            if (blockpos != null) {
                var blockstate2 = level.getBlockState(blockpos);
                if (canDrip(blockstate2) && canTipGrow(blockstate2, level, blockpos)) {
                    if (random.nextBoolean()) {
                        grow(level, blockpos, Direction.DOWN);
                    } else {
                        growStalagmiteBelow(level, blockpos);
                    }
                }
            }
        }
    }

    private static void growStalagmiteBelow(ServerLevel level, BlockPos pos) {
        var blockpos$mutableblockpos = pos.mutable();

        for (int i = 0; i < 10; i++) {
            blockpos$mutableblockpos.move(Direction.DOWN);
            var blockstate = level.getBlockState(blockpos$mutableblockpos);
            if (!blockstate.getFluidState().isEmpty()) {
                return;
            }

            if (isUnmergedTipWithDirection(blockstate, Direction.UP) && canTipGrow(blockstate, level, blockpos$mutableblockpos)) {
                grow(level, blockpos$mutableblockpos, Direction.UP);
                return;
            }

            if (isValidPointedDripstonePlacement(level, blockpos$mutableblockpos, Direction.UP) && !level.isWaterAt(blockpos$mutableblockpos.below())) {
                grow(level, blockpos$mutableblockpos.below(), Direction.UP);
                return;
            }

            if (!canDripThrough(level, blockpos$mutableblockpos, blockstate)) {
                return;
            }
        }
    }

    private static void grow(ServerLevel server, BlockPos pos, Direction direction) {
        var blockpos = pos.relative(direction);
        var blockstate = server.getBlockState(blockpos);
        if (isUnmergedTipWithDirection(blockstate, direction.getOpposite())) {
            createMergedTips(blockstate, server, blockpos);
        } else if (blockstate.isAir() || blockstate.is(Blocks.WATER)) {
            createDripstone(server, blockpos, direction, DripstoneThickness.TIP);
        }
    }

    private static void createDripstone(LevelAccessor level, BlockPos pos, Direction direction, DripstoneThickness thickness) {
        var blockstate = DDBlocks.POINTED_ARIDROCK.get()
            .defaultBlockState()
            .setValue(TIP_DIRECTION, direction)
            .setValue(THICKNESS, thickness)
            .setValue(WATERLOGGED, Boolean.valueOf(level.getFluidState(pos).getType() == Fluids.WATER));
        level.setBlock(pos, blockstate, 3);
    }

    private static void createMergedTips(BlockState state, LevelAccessor level, BlockPos pos) {
        var blockpos = state.getValue(TIP_DIRECTION) == Direction.UP ? pos.above() : pos;
        var blockpos1 = state.getValue(TIP_DIRECTION) == Direction.UP ? pos : pos.below();

        createDripstone(level, blockpos, Direction.DOWN, DripstoneThickness.TIP_MERGE);
        createDripstone(level, blockpos1, Direction.UP, DripstoneThickness.TIP_MERGE);
    }

    public static void spawnDripParticle(Level level, BlockPos pos, BlockState state) {
        getFluidAboveStalactite(level, pos, state).ifPresent(p_221856_ -> spawnDripParticle(level, pos, state, p_221856_.fluid));
    }

    private static void spawnDripParticle(Level level, BlockPos pos, BlockState state, Fluid p_fluid) {
        var vec3 = state.getOffset(level, pos);
        double d0 = 0.0625;
        double d1 = (double)pos.getX() + 0.5 + vec3.x;
        double d2 = (double)((float)(pos.getY() + 1) - 0.6875F) - 0.0625;
        double d3 = (double)pos.getZ() + 0.5 + vec3.z;
        var fluid = getDripFluid(level, p_fluid);
        if (fluid != Fluids.WATER) {
            return;
        }
        var particleoptions = fluid.getFluidType().getDripInfo() != null ? fluid.getFluidType().getDripInfo().dripParticle() : ParticleTypes.DRIPPING_DRIPSTONE_WATER;
        if (particleoptions != null)
        level.addParticle(particleoptions, d1, d2, d3, 0.0, 0.0, 0.0);
    }

    @Nullable
    private static BlockPos findTip(BlockState state, LevelAccessor level, BlockPos pos, int maxIterations, boolean isTipMerge) {
        if (isTip(state, isTipMerge)) {
            return pos;
        } else {
            var direction = state.getValue(TIP_DIRECTION);
            var bipredicate = (BiPredicate<BlockPos, BlockState>) (p_202023_, p_202024_) -> p_202024_.is(DDBlocks.POINTED_ARIDROCK.get())
                    && p_202024_.getValue(TIP_DIRECTION) == direction;
            return findBlockVertical(level, pos, direction.getAxisDirection(), bipredicate, p_154168_ -> isTip(p_154168_, isTipMerge), maxIterations)
                .orElse(null);
        }
    }

    @Nullable
    private static Direction calculateTipDirection(LevelReader level, BlockPos pos, Direction dir) {
        if (isValidPointedDripstonePlacement(level, pos, dir)) {
            return dir;
        }
        return isValidPointedDripstonePlacement(level, pos, dir.getOpposite()) ? dir.getOpposite() : null;
    }

    private static DripstoneThickness calculateDripstoneThickness(LevelReader level, BlockPos pos, Direction dir, boolean isTipMerge) {
        var direction = dir.getOpposite();
        var blockstate = level.getBlockState(pos.relative(dir));
        if (isPointedDripstoneWithDirection(blockstate, direction)) {
            return !isTipMerge && blockstate.getValue(THICKNESS) != DripstoneThickness.TIP_MERGE ? DripstoneThickness.TIP : DripstoneThickness.TIP_MERGE;
        } else if (!isPointedDripstoneWithDirection(blockstate, dir)) {
            return DripstoneThickness.TIP;
        } else {
            var dripstonethickness = blockstate.getValue(THICKNESS);
            if (dripstonethickness != DripstoneThickness.TIP && dripstonethickness != DripstoneThickness.TIP_MERGE) {
                var blockstate1 = level.getBlockState(pos.relative(direction));
                return !isPointedDripstoneWithDirection(blockstate1, dir) ? DripstoneThickness.BASE : DripstoneThickness.MIDDLE;
            } else {
                return DripstoneThickness.FRUSTUM;
            }
        }
    }

    public static boolean canDrip(BlockState state) {
        return isStalactite(state) && state.getValue(THICKNESS) == DripstoneThickness.TIP && !state.getValue(WATERLOGGED);
    }

    private static boolean canTipGrow(BlockState state, ServerLevel level, BlockPos pos) {
        var direction = state.getValue(TIP_DIRECTION);
        var blockpos = pos.relative(direction);
        var blockstate = level.getBlockState(blockpos);
        if (!blockstate.getFluidState().isEmpty()) {
            return false;
        } else {
            return blockstate.isAir() ? true : isUnmergedTipWithDirection(blockstate, direction.getOpposite());
        }
    }

    private static Optional<BlockPos> findRootBlock(Level level, BlockPos pos, BlockState state, int maxIterations) {
        var direction = state.getValue(TIP_DIRECTION);
        var bipredicate = (BiPredicate<BlockPos, BlockState>) (p_202015_, p_202016_) -> p_202016_.is(DDBlocks.POINTED_ARIDROCK.get())
                && p_202016_.getValue(TIP_DIRECTION) == direction;
        return findBlockVertical(
            level, pos, direction.getOpposite().getAxisDirection(), bipredicate, p_154245_ -> !p_154245_.is(DDBlocks.POINTED_ARIDROCK.get()), maxIterations
        );
    }

    private static boolean isValidPointedDripstonePlacement(LevelReader level, BlockPos pos, Direction dir) {
        var blockpos = pos.relative(dir.getOpposite());
        var blockstate = level.getBlockState(blockpos);
        return blockstate.isFaceSturdy(level, blockpos, dir) || isPointedDripstoneWithDirection(blockstate, dir);
    }

    private static boolean isTip(BlockState state, boolean isTipMerge) {
        if (!state.is(DDBlocks.POINTED_ARIDROCK.get())) {
            return false;
        } else {
            var dripstonethickness = state.getValue(THICKNESS);
            return dripstonethickness == DripstoneThickness.TIP || isTipMerge && dripstonethickness == DripstoneThickness.TIP_MERGE;
        }
    }

    private static boolean isUnmergedTipWithDirection(BlockState state, Direction dir) {
        return isTip(state, false) && state.getValue(TIP_DIRECTION) == dir;
    }

    private static boolean isStalactite(BlockState state) {
        return isPointedDripstoneWithDirection(state, Direction.DOWN);
    }

    private static boolean isStalagmite(BlockState state) {
        return isPointedDripstoneWithDirection(state, Direction.UP);
    }

    private static boolean isStalactiteStartPos(BlockState state, LevelReader level, BlockPos pos) {
        return isStalactite(state) && !level.getBlockState(pos.above()).is(DDBlocks.POINTED_ARIDROCK.get());
    }

    @Override
    protected boolean isPathfindable(BlockState state, PathComputationType pathComputationType) {
        return false;
    }

    private static boolean isPointedDripstoneWithDirection(BlockState state, Direction dir) {
        return state.is(DDBlocks.POINTED_ARIDROCK.get()) && state.getValue(TIP_DIRECTION) == dir;
    }

    @Nullable
    private static BlockPos findFillableCauldronBelowStalactiteTip(Level level, BlockPos pos, Fluid fluid) {
        if (fluid != Fluids.WATER) {
            return null;
        }
        var predicate = (Predicate<BlockState>) cauldronState -> cauldronState.is(Blocks.CAULDRON)
                || cauldronState.is(Blocks.WATER_CAULDRON);
        var bipredicate = (BiPredicate<BlockPos, BlockState>) (p_202034_, p_202035_) -> canDripThrough(level, p_202034_, p_202035_);
        return findBlockVertical(level, pos, Direction.DOWN.getAxisDirection(), bipredicate, predicate, 11).orElse(null);
    }

    @Nullable
    public static BlockPos findStalactiteTipAboveCauldron(Level level, BlockPos pos) {
        var bipredicate = (BiPredicate<BlockPos, BlockState>) (p_202030_, p_202031_) -> canDripThrough(level, p_202030_, p_202031_);
        return findBlockVertical(level, pos, Direction.UP.getAxisDirection(), bipredicate, PointedAridrockBlock::canDrip, 11).orElse(null);
    }

    public static Fluid getCauldronFillFluidType(ServerLevel level, BlockPos pos) {
        return getFluidAboveStalactite(level, pos, level.getBlockState(pos))
            .map(p_221858_ -> p_221858_.fluid)
            .filter(PointedAridrockBlock::canFillCauldron)
            .orElse(Fluids.EMPTY);
    }

    private static Optional<PointedAridrockBlock.FluidInfo> getFluidAboveStalactite(Level level, BlockPos pos, BlockState state) {
        return !isStalactite(state) ? Optional.empty() : findRootBlock(level, pos, state, 11).map(p_221876_ -> {
            var blockpos = p_221876_.above();
            var blockstate = level.getBlockState(blockpos);
            var fluid = blockstate.is(Blocks.MUD) && !level.dimensionType().ultraWarm()
                    ? Fluids.WATER : level.getFluidState(blockpos).getType();

            return new PointedAridrockBlock.FluidInfo(blockpos, fluid, blockstate);
        });
    }

    private static boolean canFillCauldron(Fluid fluid) {
        return fluid == Fluids.WATER;
    }

    private static boolean canGrow(BlockState dripstoneState, BlockState state) {
        return dripstoneState.is(DDBlocks.ARIDROCK.get()) && state.is(Blocks.WATER) && state.getFluidState().isSource();
    }

    private static Fluid getDripFluid(Level level, Fluid fluid) {
        if (fluid.isSame(Fluids.EMPTY)) {
            return level.dimensionType().ultraWarm() ? Fluids.EMPTY : Fluids.WATER;
        } else {
            return fluid == Fluids.WATER ? fluid : Fluids.EMPTY;
        }
    }

    private static Optional<BlockPos> findBlockVertical(
        LevelAccessor level,
        BlockPos pos,
        Direction.AxisDirection axis,
        BiPredicate<BlockPos, BlockState> positionalStatePredicate,
        Predicate<BlockState> statePredicate,
        int maxIterations
    ) {
        var direction = Direction.get(axis, Direction.Axis.Y);
        var blockpos$mutableblockpos = pos.mutable();

        for (int i = 1; i < maxIterations; i++) {
            blockpos$mutableblockpos.move(direction);
            var blockstate = level.getBlockState(blockpos$mutableblockpos);
            if (statePredicate.test(blockstate)) {
                return Optional.of(blockpos$mutableblockpos.immutable());
            }

            if (level.isOutsideBuildHeight(blockpos$mutableblockpos.getY()) || !positionalStatePredicate.test(blockpos$mutableblockpos, blockstate)) {
                return Optional.empty();
            }
        }

        return Optional.empty();
    }

    private static boolean canDripThrough(BlockGetter level, BlockPos pos, BlockState state) {
        if (state.isAir()) {
            return true;
        } else if (state.isSolidRender(level, pos)) {
            return false;
        } else if (!state.getFluidState().isEmpty()) {
            return false;
        } else {
            var voxelshape = state.getCollisionShape(level, pos);
            return !Shapes.joinIsNotEmpty(REQUIRED_SPACE_TO_DRIP_THROUGH_NON_SOLID_BLOCK, voxelshape, BooleanOp.AND);
        }
    }

    static record FluidInfo(BlockPos pos, Fluid fluid, BlockState sourceState) {
    }
}

