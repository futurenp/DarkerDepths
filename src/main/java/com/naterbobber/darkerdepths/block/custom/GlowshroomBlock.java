package com.naterbobber.darkerdepths.block.custom;

import com.naterbobber.darkerdepths.block.DDBlockStateProperties;
import com.naterbobber.darkerdepths.init.DDBlocks;
import com.naterbobber.darkerdepths.util.DDResourceKeys;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class GlowshroomBlock extends Block implements BonemealableBlock {
    public static final IntegerProperty GLOWSHROOM_CLUSTERS = DDBlockStateProperties.GLOWSHROOM_CLUSTERS;
    protected static final VoxelShape SHAPE_1 = Block.box(5.0D, 0.0D, 5.0D, 11.0D, 6.0D, 11.0D);
    protected static final VoxelShape SHAPE_2 = Block.box(4.0D, 0.0D, 4.0D, 12.0D, 7.0D, 12.0D);
    protected static final VoxelShape SHAPE_3 = Block.box(3.0D, 0.0D, 3.0D, 13.0D, 7.0D, 13.0D);

    public GlowshroomBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(GLOWSHROOM_CLUSTERS, 1));
    }

    @Override
    public ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult p_60508_) {
        int i = state.getValue(GLOWSHROOM_CLUSTERS);
        if (stack.getItem() == DDBlocks.GLOWSHROOM.get().asItem() && i < 3) {
            worldIn.playSound(null, pos, SoundEvents.SLIME_BLOCK_PLACE, SoundSource.BLOCKS, 1.0F, 1.0F);
            worldIn.setBlock(pos, state.setValue(GLOWSHROOM_CLUSTERS, i + 1), 2);
            if (!player.getAbilities().instabuild) {
                stack.shrink(1);
            }
            return ItemInteractionResult.SUCCESS;
        } else {
            return ItemInteractionResult.FAIL;
        }
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter p_60556_, BlockPos p_60557_, CollisionContext p_60558_) {
        return switch (state.getValue(GLOWSHROOM_CLUSTERS)) {
            case 2 -> SHAPE_2;
            case 3 -> SHAPE_3;
            default -> SHAPE_1;
        };
    }

    @Override
    public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor worldIn, BlockPos currentPos, BlockPos facingPos) {
        return facing == Direction.DOWN && !this.canSurvive(stateIn, worldIn, currentPos) ? Blocks.AIR.defaultBlockState() : super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader worldIn, BlockPos pos) {
        return canSupportCenter(worldIn, pos.below(), Direction.UP);
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader levelReader, BlockPos blockPos, BlockState blockState) {
        return levelReader.getBrightness(LightLayer.SKY, blockPos) == 0
                && blockState.getValue(GLOWSHROOM_CLUSTERS) == 1;
    }

    @Override
    public boolean isBonemealSuccess(Level p_50901_, RandomSource random, BlockPos p_50903_, BlockState state) {
        return random.nextFloat() < 0.2D;
    }

    @Override
    public void performBonemeal(ServerLevel world, RandomSource random, BlockPos pos, BlockState state) {
        var featureKey = random.nextFloat() < 0.2D
                ? DDResourceKeys.ConfiguredFeatures.HUGE_GLOWSHROOM
                : DDResourceKeys.ConfiguredFeatures.SMUSHED_GLOWSHROOM;

        world.registryAccess().registryOrThrow(Registries.CONFIGURED_FEATURE)
                .getHolder(featureKey).ifPresent(featureHolder -> {

                    world.setBlock(pos, Blocks.CAVE_AIR.defaultBlockState(), 4);

                    boolean placedSuccessfully = featureHolder.value().place(world, world.getChunkSource().getGenerator(), random, pos);

                    if (!placedSuccessfully) {
                        world.setBlock(pos, state, 4);
                    }
                });
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(GLOWSHROOM_CLUSTERS);
    }
}
