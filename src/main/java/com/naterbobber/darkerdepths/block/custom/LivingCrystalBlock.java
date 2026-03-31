package com.naterbobber.darkerdepths.block.custom;

import com.naterbobber.darkerdepths.block.DDBlockStateProperties;
import com.naterbobber.darkerdepths.block.generic.HeatableBlock;
import com.naterbobber.darkerdepths.init.DDBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import org.jetbrains.annotations.Nullable;

public class LivingCrystalBlock extends Block implements HeatableBlock {

    private static final IntegerProperty HEAT_LEVEL = DDBlockStateProperties.HEAT_LEVEL;

    public LivingCrystalBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(HEAT_LEVEL, 0));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(HEAT_LEVEL);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        int neighborHeat = getHighestNeighborHeat(context.getLevel(), context.getClickedPos());
        return this.defaultBlockState().setValue(HEAT_LEVEL, HeatableBlock.calculateNewHeat(neighborHeat));
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos currentPos, BlockPos neighborPos) {
        if (updatedByHeat(level, state)) {
            level.scheduleTick(currentPos, this, 10);
        }
        return super.updateShape(state, direction, neighborState, level, currentPos, neighborPos);
    }

    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        sendHeatUpdate(level, pos, state);
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos blockPos, RandomSource random) {
        if(state.getValue(HEAT_LEVEL) > 0) {
            return;
        }

        if (random.nextInt(5) != 0) {
            return;
        }

        BlockPos blockpos = blockPos.relative(Direction.getRandom(random));
        BlockState blockstate = level.getBlockState(blockpos);
        BlockState block = null;

        if (blockstate.is(BlockTags.DIAMOND_ORES)) {
            block = DDBlocks.CRYSTAL_HUSK.get().defaultBlockState().setValue(DDBlockStateProperties.CRYSTAL_GROWTH_LEVEL, 1);
            level.playSound(null, blockpos, SoundEvents.AMETHYST_BLOCK_PLACE, SoundSource.BLOCKS, 1.0F, 1.0F);
            level.levelEvent(2001, blockpos, getId(blockstate));
        }
        else if (blockstate.is(Blocks.MELON)) {
            block = DDBlocks.STONE_MELON.get().defaultBlockState();
        }
        else if (blockstate.is(DDBlocks.STONE_MELON.get())) {
            int stoneMelonGrowthLevel = blockstate.getValue(DDBlockStateProperties.MELON_GROWTH_LEVEL);

            if (stoneMelonGrowthLevel >= 2) {
                block = DDBlocks.CRYSTAL_MELON.get().defaultBlockState();
                level.setBlock(blockPos, DDBlocks.CRYSTAL_HUSK.get().defaultBlockState(), 2);
            }
            else {
                block = blockstate.setValue(DDBlockStateProperties.MELON_GROWTH_LEVEL, stoneMelonGrowthLevel + 1);
            }

            level.playSound(null, blockpos, SoundEvents.AMETHYST_BLOCK_PLACE, SoundSource.BLOCKS, 1.0F, 1.0F);
            level.levelEvent(2001, blockpos, getId(blockstate));
        }

        if (block != null) {
            level.setBlockAndUpdate(blockpos, block);
        }
    }
}