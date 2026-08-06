package com.naterbobber.darkerdepths.common.util;

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

    public static <T extends Comparable<T>> BlockState copyProperty(BlockState source, BlockState target, Property<T> property) {
        return target.setValue(property, source.getValue(property));
    }
}
