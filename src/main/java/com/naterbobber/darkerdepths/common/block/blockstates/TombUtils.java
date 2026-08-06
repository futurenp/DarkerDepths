package com.naterbobber.darkerdepths.common.block.blockstates;

import com.naterbobber.darkerdepths.common.util.VoxelShapeUtils;
import net.minecraft.core.Direction;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class TombUtils {
    private static final Shape CORNER = Shape.of(
            Block.box(2, 0, 5, 16, 3, 16),
            Shapes.or(Block.box(3, 3, 6, 16, 10, 8), Block.box(3, 3, 8, 5, 10, 16)),
            Block.box(5, 3, 8, 16, 8, 16),
            Block.box(3, 3, 6, 16, 10, 16),
            Block.box(1, 10, 4, 16, 15, 16));

    private static final Shape CENTER = Shape.of(
            Block.box(0, 0, 5, 16, 3, 16),
            Block.box(0, 3, 6, 16, 10, 8),
            Block.box(0, 3, 8, 16, 8, 16),
            Block.box(0, 3, 6, 16, 10, 16),
            Block.box(0, 10, 4, 16, 15, 16));

    public enum Part implements StringRepresentable {
        FRONT_CENTER("front_center", CENTER, 0, 0),
        FRONT_LEFT("front_left", CORNER, -1, 0),
        FRONT_RIGHT("front_right", CORNER.mirrored(Direction.Axis.X), 1, 0),
        BACK_CENTER("back_center", CENTER.mirrored(Direction.Axis.Z),0, -1),
        BACK_LEFT("back_left", CORNER.mirrored(Direction.Axis.Z), -1, -1),
        BACK_RIGHT("back_right", CORNER.mirrored(Direction.Axis.X).mirrored(Direction.Axis.Z), 1, -1);

        private final String name;
        private final Shape shape;
        private final int xOffset;
        private final int zOffset;


        Part(String name, Shape shape, int xOffset, int zOffset) {
            this.name = name;
            this.shape = shape;
            this.xOffset = xOffset;
            this.zOffset = zOffset;
        }

        @Override
        public String getSerializedName() {
            return this.name;
        }

        public int xOffset() {
            return this.xOffset;
        }

        public int zOffset() {
            return this.zOffset;
        }

        public Shape getShape() {
            return this.shape;
        }
    }

    public record Shape(
            VoxelShape closed,
            VoxelShape openEmpty,
            VoxelShape openBed
    ) {
        public static Shape of(
                VoxelShape bottom,
                VoxelShape edgeWalls,
                VoxelShape bed,
                VoxelShape edgeWallsFull,
                VoxelShape top
        ) {
            return new Shape(
                    Shapes.or(bottom, edgeWallsFull, top).optimize(),
                    Shapes.or(bottom, edgeWalls).optimize(),
                    Shapes.or(bottom, edgeWalls, bed).optimize()
            );
        }

        public Shape mirrored(Direction.Axis axis) {
            return new Shape(
                    VoxelShapeUtils.mirror(closed, axis).optimize(),
                    VoxelShapeUtils.mirror(openEmpty, axis).optimize(),
                    VoxelShapeUtils.mirror(openBed, axis).optimize()
            );
        }

        public VoxelShape getVoxelShape(boolean bed, boolean open) {
            if (!open) {
                return closed;
            }

            return bed ? openBed : openEmpty;
        }
    }
}