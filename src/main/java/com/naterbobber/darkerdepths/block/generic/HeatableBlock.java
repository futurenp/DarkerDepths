package com.naterbobber.darkerdepths.block.generic;

import com.naterbobber.darkerdepths.block.DDBlockStateProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.FireBlock;
import net.minecraft.world.level.block.MagmaBlock;
import net.minecraft.world.level.block.state.BlockState;

public interface HeatableBlock {

    default void sendHeatUpdate(Level level, BlockPos pos, BlockState currentState) {
        if (!currentState.hasProperty(DDBlockStateProperties.HEAT_LEVEL)) return;

        int highestNeighborHeat = getHighestNeighborHeat(level, pos);
        int newHeat = calculateNewHeat(highestNeighborHeat);

        if (currentState.getValue(DDBlockStateProperties.HEAT_LEVEL) != newHeat) {
            level.setBlock(pos, currentState.setValue(DDBlockStateProperties.HEAT_LEVEL, newHeat), 3);
        }
    }

    default int calculateNewHeat(int neighborHeat) {
        return neighborHeat - (neighborHeat > 0 ? 1 : 0);
    }

    default int getHighestNeighborHeat(LevelAccessor level, BlockPos pos) {
        int highestLevel = 0;

        for (Direction direction : Direction.values()) {
            BlockPos neighborPos = pos.relative(direction);
            BlockState neighborState = level.getBlockState(neighborPos);
            int neighborHeatLevel = 0;

            if (neighborState.getFluidState().is(FluidTags.LAVA)) {
                neighborHeatLevel = 5;
            } else if (neighborState.getBlock() instanceof MagmaBlock) {
                neighborHeatLevel = 4;
            } else if (neighborState.getBlock() instanceof FireBlock) {
                neighborHeatLevel = 3;
            } else if (neighborState.hasProperty(DDBlockStateProperties.HEAT_LEVEL)) {
                neighborHeatLevel = neighborState.getValue(DDBlockStateProperties.HEAT_LEVEL);
            }

            if (neighborHeatLevel > highestLevel) {
                highestLevel = neighborHeatLevel;
            }
        }

        return highestLevel;
    }
}