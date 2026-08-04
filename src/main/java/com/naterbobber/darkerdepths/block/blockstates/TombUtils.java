package com.naterbobber.darkerdepths.block.blockstates;

import com.naterbobber.darkerdepths.util.VoxelShapeUtils;
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

    public record Shape(VoxelShape bottom, VoxelShape edgeWalls, VoxelShape bed, VoxelShape edgeWallsFull,
                        VoxelShape top) {
        public static Shape of(VoxelShape bottom, VoxelShape edgeWalls, VoxelShape bed, VoxelShape edgeWallsFull, VoxelShape top) {
            return new Shape(bottom, edgeWalls, bed, edgeWallsFull, top);
        }

        public static Shape mirrored(Shape existing, Direction.Axis axisMirror) {
            return new Shape(
                    VoxelShapeUtils.mirror(existing.bottom, axisMirror),
                    VoxelShapeUtils.mirror(existing.edgeWalls, axisMirror),
                    VoxelShapeUtils.mirror(existing.bed, axisMirror),
                    VoxelShapeUtils.mirror(existing.edgeWallsFull, axisMirror),
                    VoxelShapeUtils.mirror(existing.top, axisMirror)
            );
        }

        public Shape mirrored(Direction.Axis axisMirror) {
            return new Shape(
                    VoxelShapeUtils.mirror(this.bottom, axisMirror),
                    VoxelShapeUtils.mirror(this.edgeWalls, axisMirror),
                    VoxelShapeUtils.mirror(this.bed, axisMirror),
                    VoxelShapeUtils.mirror(this.edgeWallsFull, axisMirror),
                    VoxelShapeUtils.mirror(this.top, axisMirror)
            );
        }

        public VoxelShape closed() {
            return Shapes.or(bottom, edgeWallsFull, top).optimize();
        }

        public VoxelShape openEmpty() {
            return Shapes.or(bottom, edgeWalls);
        }

        public VoxelShape openBed() {
            return Shapes.or(bottom, edgeWalls, bed).optimize();
        }

        public VoxelShape getVoxelShape(boolean bed, boolean open) {
            if(!open) {
                return closed();
            } else {
                return bed ? openBed() : openEmpty();
            }
        }
    }
}