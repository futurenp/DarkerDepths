package com.naterbobber.darkerdepths.block.blockstates;

import net.minecraft.core.Direction;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public enum VerticalSlabState implements StringRepresentable {
    NORTH(Direction.NORTH),
    SOUTH(Direction.SOUTH),
    WEST(Direction.WEST),
    EAST(Direction.EAST),
    DOUBLE(null);

    private final String name;
    public final Direction direction;
    public final VoxelShape shape;

    VerticalSlabState(Direction direction) {
        this.name = direction == null ? "double" : direction.getName();
        this.direction = direction;

        if(direction == null)
            shape = Shapes.block();
        else {
            double min = 0;
            double max = 8;
            if(direction.getAxisDirection() == Direction.AxisDirection.NEGATIVE) {
                min = 8;
                max = 16;
            }

            if(direction.getAxis() == Direction.Axis.X)
                shape = Block.box(min, 0, 0, max, 16, 16);
            else shape = Block.box(0, 0, min, 16, 16, max);
        }
    }

    @Override
    public String toString() {
        return name;
    }


    public static VerticalSlabState fromDirection(Direction direction) {
        for(VerticalSlabState type : VerticalSlabState.values())
            if (type.direction != null && direction == type.direction)
                return type;
        return null;
    }

    @Override
    public String getSerializedName() {
        return name;
    }
}