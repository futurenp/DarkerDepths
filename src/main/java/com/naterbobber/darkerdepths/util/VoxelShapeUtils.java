package com.naterbobber.darkerdepths.util;

import net.minecraft.core.Direction;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.ArrayList;
import java.util.List;

public class VoxelShapeUtils {

    public static VoxelShape rotateHorizontal(VoxelShape shape, Direction to) {
        if (to == Direction.NORTH) {
            return shape;
        }

        var buffer = new VoxelShape[]{shape, Shapes.empty()};

        int times = switch (to) {
            case EAST -> 1;
            case SOUTH -> 2;
            case WEST -> 3;
            default -> 0;
        };

        for (int i = 0; i < times; i++) {
            buffer[1] = Shapes.empty();
            buffer[0].forAllBoxes((minX, minY, minZ, maxX, maxY, maxZ) -> {
                buffer[1] = Shapes.or(buffer[1], Shapes.box(1 - maxZ, minY, minX, 1 - minZ, maxY, maxX));
            });
            buffer[0] = buffer[1];
        }

        return buffer[0];
    }

    public static VoxelShape mirror(VoxelShape originalShape, Direction.Axis axis) {
        var buffer = new VoxelShape[]{Shapes.empty()};

        originalShape.forAllBoxes((minX, minY, minZ, maxX, maxY, maxZ) -> {
            buffer[0] = Shapes.or(buffer[0], switch (axis) {
                case X -> Shapes.box(1.0 - maxX, minY, minZ, 1.0 - minX, maxY, maxZ);
                case Y -> Shapes.box(minX, 1.0 - maxY, minZ, maxX, 1.0 - minY, maxZ);
                case Z -> Shapes.box(minX, minY, 1.0 - maxZ, maxX, maxY, 1.0 - minZ);
            });
        });

        return buffer[0];
    }
}