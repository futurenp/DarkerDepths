package com.naterbobber.darkerdepths.common.block.custom;

import com.mojang.serialization.MapCodec;
import com.naterbobber.darkerdepths.common.block.DDBlockStateProperties;
import com.naterbobber.darkerdepths.common.block.blockentities.TombBlockEntity;
import com.naterbobber.darkerdepths.common.block.blockstates.TombUtils;
import com.naterbobber.darkerdepths.common.init.DDBlockEntityTypes;
import com.naterbobber.darkerdepths.common.util.BlockStateUtils;
import com.naterbobber.darkerdepths.common.util.VoxelShapeUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.common.Tags;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class TombBlock extends BaseEntityBlock implements SimpleWaterloggedBlock {
    private static final EnumProperty<TombUtils.Part> TOMB_PART = DDBlockStateProperties.TOMB_PART;
    private static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    private static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    private static final BooleanProperty OPEN = BlockStateProperties.OPEN;
    private static final BooleanProperty INHABITED = DDBlockStateProperties.INHABITED;
    private static final BooleanProperty BED = DDBlockStateProperties.BED;
    private static final BooleanProperty OCCUPIED = BlockStateProperties.OCCUPIED;

    private record MultiblockPartData(BlockPos pos, BlockState state) {}

    public TombBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(TOMB_PART, TombUtils.Part.FRONT_CENTER)
                .setValue(FACING, Direction.NORTH)
                .setValue(WATERLOGGED, false)
                .setValue(OPEN, false)
                .setValue(INHABITED, false)
                .setValue(BED, false)
                .setValue(OCCUPIED, false));
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return null;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, TOMB_PART, WATERLOGGED, INHABITED, OPEN, BED, OCCUPIED);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        var part = state.getValue(TOMB_PART);
        var shape = part.getShape().getVoxelShape(state.getValue(BED), state.getValue(OPEN));
        var facing = state.getValue(FACING);

        return VoxelShapeUtils.rotateHorizontal(shape, facing);
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return state.getValue(TOMB_PART) == TombUtils.Part.FRONT_CENTER ? RenderShape.ENTITYBLOCK_ANIMATED : RenderShape.MODEL;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        var fluid = context.getLevel().getFluidState(context.getClickedPos());
        return this.defaultBlockState()
                .setValue(FACING, context.getHorizontalDirection().getOpposite())
                .setValue(TOMB_PART, TombUtils.Part.FRONT_CENTER)
                .setValue(WATERLOGGED, fluid.getType() == Fluids.WATER);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        var facing = state.getValue(FACING);
        var parts = generateMultiblockPartData(getMainBlockPos(pos, state), facing, level);

        for (var partData : parts.values()) {
            var block = level.getBlockState(partData.pos);
            if(block.is(this)) {
                if (
                        block.getValue(FACING) != partData.state.getValue(FACING) ||
                        block.getValue(TOMB_PART) != partData.state.getValue(TOMB_PART)
                ) return false;
            }
            else if (!block.canBeReplaced()) return false;

            if(!super.canSurvive(partData.state, level, partData.pos)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean isMoving) {
        super.onPlace(state, level, pos, oldState, isMoving);
        if (!level.isClientSide && !isMoving && state.getValue(TOMB_PART) == TombUtils.Part.FRONT_CENTER) {
            this.placeMultiblockParts(level, pos, state);
        }
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
        if (!level.isClientSide && !state.is(newState.getBlock())) {
            if (state.getValue(TOMB_PART) == TombUtils.Part.FRONT_CENTER) {
                var blockEntity = level.getBlockEntity(pos);
                if (blockEntity instanceof TombBlockEntity tombEntity && tombEntity.hasStoredItem()) {
                    var storedItem = tombEntity.getStoredItem();
                    popResource(level, pos, storedItem);
                }
            }

            this.removeMultiblockParts(level, pos, state);
        }

        super.onRemove(state, level, pos, newState, isMoving);
    }

    @Override
    public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor level, BlockPos currentPos, BlockPos facingPos) {
        if (state.getValue(WATERLOGGED)) {
            level.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        }

        var part = state.getValue(TOMB_PART);
        if (part != TombUtils.Part.FRONT_CENTER) {
            var mainPos = getMainBlockPos(currentPos, state);
            if (!level.getBlockState(mainPos).is(this)) {
                return Blocks.AIR.defaultBlockState();
            }
        }

        return super.updateShape(state, facing, facingState, level, currentPos, facingPos);
    }

    @Override
    public ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (level.isClientSide) {
            return ItemInteractionResult.SUCCESS;
        }

        var mainPos = getMainBlockPos(pos, state);
        var blockEntity = level.getBlockEntity(mainPos);
        if (blockEntity instanceof TombBlockEntity tombEntity) {
            if (player.isSecondaryUseActive() && tombEntity.isOpen() && tombEntity.isInhabited() && tombEntity.hasStoredItem()) {
                toggleTomb(level, tombEntity, pos, player.position());

                return ItemInteractionResult.SUCCESS;
            }

            if (stack.is(ItemTags.BEDS) && tombEntity.isOpen() && !tombEntity.isInhabited()) {
                boolean hasBed = state.getValue(BED);

                if(hasBed) {
                    var bedInTomb = tombEntity.getItemStack();

                    if(stack.getItem().equals(bedInTomb.getItem())) {
                        return ItemInteractionResult.SUCCESS;
                    }

                    if(!player.isCreative()) {
                        Block.popResourceFromFace(level, mainPos, Direction.UP, bedInTomb);
                    }

                } else {
                    var newState = level.getBlockState(mainPos).setValue(BED, true);
                    updateMultiblockState(level, mainPos, newState);
                }

                tombEntity.setItem(0, new ItemStack(stack.getItem()));
                if(!player.isCreative()) {
                    stack.shrink(1);
                }
                level.playSound(null, pos, SoundEvents.WOOL_PLACE, SoundSource.BLOCKS, 1.0F, 1.0F);

                return ItemInteractionResult.CONSUME;
            }

            if(stack.is(Tags.Items.TOOLS_SHEAR) && tombEntity.isOpen() && tombEntity.hasBed()) {
                var bedInTomb = tombEntity.getItem(0);
                if(!player.isCreative()) {
                    Block.popResourceFromFace(level, mainPos, Direction.UP, bedInTomb);
                }

                level.playSound(null, mainPos, SoundEvents.SHEEP_SHEAR, SoundSource.BLOCKS, 1.0F, 1.0F);

                tombEntity.setItem(0, new ItemStack(Items.AIR));
                var newState = level.getBlockState(mainPos).setValue(BED, false);
                updateMultiblockState(level, mainPos, newState);

                return ItemInteractionResult.SUCCESS;
            }

            if (player.isSecondaryUseActive()) {
                if(tombEntity.hasBed() && tombEntity.isOpen()) {
                    toggleTomb(level, tombEntity, pos, player.position());
                }
                return ItemInteractionResult.SUCCESS;
            }

            if (tombEntity.isOpen() && tombEntity.isInhabited()) {
                var heldItem = player.getItemInHand(hand);
                if (tombEntity.hasStoredItem() && heldItem.isEmpty()) {
                    var storedItem = tombEntity.removeItem(0, 64);
                    if (!storedItem.isEmpty()) {
                        if (!player.getInventory().add(storedItem)) {
                            player.drop(storedItem, false);
                        }
                        return ItemInteractionResult.SUCCESS;
                    }
                }
            }

            if(tombEntity.hasBed() && tombEntity.isOpen()) {
                //sleep
                if (!BedBlock.canSetSpawn(level)) {
                    level.removeBlock(pos, false);
                    BlockPos blockpos = pos.relative(state.getValue(FACING).getOpposite());
                    if (level.getBlockState(blockpos).is(this)) {
                        level.removeBlock(blockpos, false);
                    }

                    var vec3 = pos.getCenter();
                    level.explode(null, level.damageSources().badRespawnPointExplosion(vec3), null, vec3, 5.0F, true, Level.ExplosionInteraction.BLOCK);
                    return ItemInteractionResult.SUCCESS;
                } else if (state.getValue(OCCUPIED)) {
                    if (!this.kickVillagerOutOfBed(level, pos)) {
                        player.displayClientMessage(Component.translatable("block.minecraft.bed.occupied"), true);
                    }

                    return ItemInteractionResult.SUCCESS;
                } else {
                    player.startSleepInBed(mainPos).ifLeft((bedSleepingProblem) -> {
                        if (bedSleepingProblem.getMessage() != null) {
                            player.displayClientMessage(bedSleepingProblem.getMessage(), true);
                        }
                    });

                    return ItemInteractionResult.SUCCESS;
                }
            }

            toggleTomb(level, tombEntity, pos, player.position());
        }

        return ItemInteractionResult.SUCCESS;
    }

    private void toggleTomb(Level level, TombBlockEntity tombEntity, BlockPos clickedPos, Vec3 playerPos) {
        var state = tombEntity.getBlockState();
        if(!isObstructed(level, tombEntity.getBlockPos(), state.getValue(HorizontalDirectionalBlock.FACING)) && !state.getValue(OCCUPIED)) {
            tombEntity.toggleTomb(clickedPos, playerPos);
        }
    }

    @Override
    public boolean isBed(BlockState state, BlockGetter level, BlockPos pos, LivingEntity sleeper) {
        return state.getValue(BED);
    }

    @Override
    public Direction getBedDirection(BlockState state, LevelReader level, BlockPos pos) {
        return state.getValue(HorizontalDirectionalBlock.FACING).getCounterClockWise();
    }

    @Override
    public Optional<ServerPlayer.RespawnPosAngle> getRespawnPosition(BlockState state, EntityType<?> type, LevelReader levelReader, BlockPos pos, float orientation) {
        return BedBlock.findStandUpPosition(EntityType.PLAYER, levelReader, pos, state.getValue(HorizontalDirectionalBlock.FACING), orientation)
                .map(vec3 -> ServerPlayer.RespawnPosAngle.of(vec3, pos));
    }

    private boolean kickVillagerOutOfBed(Level level, BlockPos pos) {
        List<Villager> list = level.getEntitiesOfClass(Villager.class, new AABB(pos), LivingEntity::isSleeping);
        if (list.isEmpty()) {
            return false;
        } else {
            list.getFirst().stopSleeping();
            return true;
        }
    }

    private boolean isObstructed(LevelReader level, BlockPos mainPos, Direction facing ) {
        var parts = generateMultiblockPartData(mainPos, facing, level);

        for(var part : parts.values()) {
            var aboveState = level.getBlockState(part.pos.above());
            if(aboveState.isFaceSturdy(level, part.pos.above(), Direction.DOWN)) {
                return true;
            }
        }

        return false;
    }

    public void updateMultiblockState(Level level, BlockPos mainPos, BlockState mainState) {
        level.setBlockAndUpdate(mainPos, mainState);
        placeMultiblockParts(level, mainPos, mainState);
    }

    @Override
    public List<ItemStack> getDrops(BlockState state, LootParams.Builder params) {
        return super.getDrops(state, params);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return state.getValue(TOMB_PART) == TombUtils.Part.FRONT_CENTER ? new TombBlockEntity(pos, state) : null;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        if (state.getValue(TOMB_PART) == TombUtils.Part.FRONT_CENTER) {
            return createTickerHelper(type, DDBlockEntityTypes.TOMB.get(), TombBlockEntity::tick);
        }

        return null;
    }

    private Map<TombUtils.Part, MultiblockPartData> generateMultiblockPartData(BlockPos mainPos, Direction facing, LevelReader level) {
        Map<TombUtils.Part, MultiblockPartData> parts = new HashMap<>();

        var mainState = level.getBlockState(mainPos);

        for (var part : TombUtils.Part.values()) {
            var partPos = getPartPos(mainPos, part, facing);
            boolean isWaterlogged = level.getFluidState(partPos).getType() == Fluids.WATER;

            var partState = this.defaultBlockState()
                    .setValue(FACING, facing)
                    .setValue(TOMB_PART, part)
                    .setValue(WATERLOGGED, isWaterlogged);

            for(var property : mainState.getValues().keySet()) {
                if(!property.equals(TOMB_PART) && !property.equals(WATERLOGGED) && !property.equals(FACING)) {
                    if (partState.hasProperty(property)) {
                        partState = BlockStateUtils.copyProperty(mainState, partState, property);
                    }
                }
            }

            parts.put(part, new MultiblockPartData(partPos, partState));
        }

        return parts;
    }

    public Set<StructureTemplate.StructureBlockInfo> generateMultiblockForProcessor(BlockPos mainPos, Direction facing, LevelReader level, BlockPos relativePos) {
        Map<TombUtils.Part, MultiblockPartData> parts = generateMultiblockPartData(mainPos, facing, level);
        Set<StructureTemplate.StructureBlockInfo> targets = new HashSet<>();

        for (MultiblockPartData partData : parts.values()) {
            targets.add(new StructureTemplate.StructureBlockInfo(relativePos.offset(partData.pos.subtract(mainPos)), partData.state, null));
        }
        return targets;
    }

    private void placeMultiblockParts(Level level, BlockPos mainPos, BlockState mainState) {
        var facing = mainState.getValue(FACING);
        var parts = generateMultiblockPartData(mainPos, facing, level);

        for (var partData : parts.values()) {
            if (partData.state.getValue(TOMB_PART) != TombUtils.Part.FRONT_CENTER) {
                level.setBlock(partData.pos, partData.state, Block.UPDATE_ALL);
            }
        }
    }

    private void removeMultiblockParts(Level level, BlockPos pos, BlockState state) {
        var facing = state.getValue(FACING);
        var parts = generateMultiblockPartData(getMainBlockPos(pos, state), facing, level);

        for (var partData : parts.values()) {
            if (partData.pos.equals(pos)) continue;

            var currentBlock = level.getBlockState(partData.pos);

            if (currentBlock.is(this)) {
                level.removeBlock(partData.pos, false);
            }
        }
    }

    public static BlockPos getPartPos(BlockPos mainPos, TombUtils.Part part, Direction facing) {
        var right = facing.getClockWise();
        return mainPos.relative(part.xOffset() > 0 ? right : right.getOpposite(), Math.abs(part.xOffset()))
                .relative(part.zOffset() > 0 ? facing : facing.getOpposite(), Math.abs(part.zOffset()));
    }

    private BlockPos getMainBlockPos(BlockPos partPos, BlockState state) {
        var part = state.getValue(TOMB_PART);
        var facing = state.getValue(FACING);
        var left = facing.getCounterClockWise();
        return partPos.relative(part.xOffset() > 0 ? left : left.getOpposite(), Math.abs(part.xOffset()))
                .relative(part.zOffset() > 0 ? facing.getOpposite() : facing, Math.abs(part.zOffset()));
    }
}