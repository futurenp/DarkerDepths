package com.naterbobber.darkerdepths.util;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;

public class BlockStateUtils {

    public static BlockState copyState(BlockState oldState, BlockState newState) {
        for (var entry : oldState.getValues().entrySet()) {
            Property property = entry.getKey();
            Comparable value = entry.getValue();

            if (newState.hasProperty(property)) {
                newState = newState.setValue(property, value);
            }
        }

        return newState;
    }
}
