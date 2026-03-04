package com.naterbobber.darkerdepths.block.custom;

import com.naterbobber.darkerdepths.block.DDBlockStateProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.MagmaBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import org.jetbrains.annotations.Nullable;

import java.util.Set;

public class DarkslateBlock extends RotatedPillarBlock {
    private static final IntegerProperty HEAT_LEVEL = DDBlockStateProperties.HEAT_LEVEL;

    public DarkslateBlock(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(this.defaultBlockState()
                .setValue(HEAT_LEVEL, 0)
        );
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockState state = super.getStateForPlacement(context);

        int neighborHeat = getHighestNeighborState(context.getClickedPos(), context.getLevel());
        return state.setValue(HEAT_LEVEL, newHeat(neighborHeat));
    }

    private void sendHeatUpdate(BlockPos blockPos, Level level) {
        int heat = newHeat(getHighestNeighborState(blockPos, level));
        BlockState blockState = level.getBlockState(blockPos);

        if (blockState.getValue(HEAT_LEVEL) != heat) {
            level.setBlock(blockPos, blockState.setValue(HEAT_LEVEL, heat), 3);
        }
    }

    private int newHeat(int heat){
        return heat - (heat > 0 ? 1 : 0);
    }

    private int getHighestNeighborState(BlockPos blockPos, Level level) {
        Set<BlockPos> neighbors = Set.of(
                blockPos.above(),
                blockPos.below(),
                blockPos.north(),
                blockPos.south(),
                blockPos.east(),
                blockPos.west()
        );

        int highestLevel = 0;
        for (BlockPos neighborPos : neighbors) {
            BlockState neighborBlockState = level.getBlockState(neighborPos);
            Block neighborBlock = neighborBlockState.getBlock();

            int neighborHeatLevel;
            if (neighborBlockState.getFluidState().is(FluidTags.LAVA)) {
                neighborHeatLevel = 5;
            } else {
                switch (neighborBlock) {
                    case DarkslateBlock b -> neighborHeatLevel = neighborBlockState.getValue(HEAT_LEVEL);
                    case MagmaBlock b -> neighborHeatLevel = 4;
                    case GeyserBlock b -> neighborHeatLevel = neighborBlockState.getValue(DDBlockStateProperties.HEAT_LEVEL);
                    default -> neighborHeatLevel = 0;
                }
            }

            if (neighborHeatLevel > highestLevel) {
                highestLevel = neighborHeatLevel;
            }
        }

        return highestLevel;
    }

    @Override
    protected void neighborChanged(BlockState state, Level level, BlockPos pos, Block neighborBlock, BlockPos neighborPos, boolean movedByPiston) {
        sendHeatUpdate(pos, level);
        super.neighborChanged(state, level, pos, neighborBlock, neighborPos, movedByPiston);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(HEAT_LEVEL);
    }
}
