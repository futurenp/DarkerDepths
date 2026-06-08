package com.naterbobber.darkerdepths.block.generic;

import com.naterbobber.darkerdepths.block.DDBlockStateProperties;
import com.naterbobber.darkerdepths.util.DDTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

public interface IHeatableBlock {
    IntegerProperty HEAT_LEVEL = DDBlockStateProperties.HEAT_LEVEL;

    default void sendHeatUpdate(Level level, BlockPos pos, BlockState currentState) {
        if (!currentState.hasProperty(HEAT_LEVEL)) return;

        int highestNeighborHeat = getHighestNeighborHeat(level, pos);
        int newHeat = calculateNewHeat(highestNeighborHeat);

        if (currentState.getValue(HEAT_LEVEL) != newHeat) {
            level.setBlock(pos, currentState.setValue(HEAT_LEVEL, newHeat), 3);
        }
    }

    static int calculateNewHeat(int neighborHeat) {
        return neighborHeat - (neighborHeat > 0 ? 1 : 0);
    }

    default int getHighestNeighborHeat(LevelAccessor level, BlockPos pos) {
        int highestLevel = 0;

        BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos();

        for (Direction direction : Direction.values()) {
            mutablePos.setWithOffset(pos, direction);
            BlockState neighborState = level.getBlockState(mutablePos);

            int neighborHeatLevel = determineHeat(neighborState);

            if (neighborHeatLevel > highestLevel) {
                highestLevel = neighborHeatLevel;
            }
        }

        return highestLevel;
    }

    static int determineHeat(BlockState state) {
        if(state.is(DDTags.Blocks.HEATABLE)) {
            if(state.hasProperty(HEAT_LEVEL)) {
                return state.getValue(HEAT_LEVEL);
            }
        }
        else if(state.is(BlockTags.AIR)) {
            return 0;
        }
        else if(state.is(DDTags.Blocks.HEAT_PROVIDER)){
            if(state.is(DDTags.Blocks.VERY_HIGH_HEAT))   return 5;
            else if(state.is(DDTags.Blocks.HIGH_HEAT))   return 4;
            else if(state.is(DDTags.Blocks.MEDIUM_HEAT)) return 3;
            else if(state.is(DDTags.Blocks.LOW_HEAT))    return 2;
        }
        else {
            var fluidState = state.getFluidState();
            if(fluidState.is(DDTags.Fluids.HEAT_PROVIDER)) {
                if (fluidState.is(DDTags.Fluids.VERY_HIGH_HEAT))   return 5;
                else if (fluidState.is(DDTags.Fluids.HIGH_HEAT))   return 4;
                else if (fluidState.is(DDTags.Fluids.MEDIUM_HEAT)) return 3;
                else if (fluidState.is(DDTags.Fluids.LOW_HEAT))    return 2;
            }
        }

        return 0;
    }
}