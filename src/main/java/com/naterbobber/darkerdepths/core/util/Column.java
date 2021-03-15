package com.naterbobber.darkerdepths.core.util;

import net.minecraft.block.BlockState;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.IWorldGenerationReader;

import java.util.Optional;
import java.util.OptionalInt;
import java.util.function.Predicate;

//<>

public abstract class Column {
    public static Column.Range inside(int floor, int ceiling) {
        return new Column.Range(floor, ceiling);
    }

    public static Column below(int ceiling) {
        return new Column.Ray(ceiling, false);
    }

    public static Column above(int floor) {
        return new Column.Ray(floor, true);
    }

    public static Column line() {
        return Line.INSTANCE;
    }

    public static Column create(OptionalInt floor, OptionalInt ceiling) {
        if (floor.isPresent() && ceiling.isPresent()) {
            return inside(floor.getAsInt(), ceiling.getAsInt());
        } else if (floor.isPresent()) {
            return above(floor.getAsInt());
        } else {
            return ceiling.isPresent() ? below(ceiling.getAsInt()) : line();
        }
    }

    public abstract OptionalInt getCeiling();

    public abstract OptionalInt getFloor();

    public abstract OptionalInt getHeight();

    public Column withFloor(OptionalInt floor) {
        return create(floor, this.getCeiling());
    }

    public static Optional<Column> scan(IWorldGenerationReader worldReader, BlockPos pos, int height, Predicate<BlockState> canGenerate, Predicate<BlockState> canReplace) {
        BlockPos.Mutable mutablePos = pos.toMutable();
        if (!worldReader.hasBlockState(pos, canGenerate)) {
            return Optional.empty();
        } else {
            int y = pos.getY();
            mutablePos.setY(y);

            for (int i = 1; i < height && worldReader.hasBlockState(mutablePos, canGenerate); i++) {
                mutablePos.move(Direction.UP);
            }

            OptionalInt replaceCeiling = worldReader.hasBlockState(mutablePos, canReplace) ? OptionalInt.of(mutablePos.getY()) : OptionalInt.empty();
            mutablePos.setY(y);

            for (int i = 1; i < height && worldReader.hasBlockState(mutablePos, canGenerate); i++) {
                mutablePos.move(Direction.DOWN);
            }

            OptionalInt replaceFloor = worldReader.hasBlockState(mutablePos, canReplace) ? OptionalInt.of(mutablePos.getY()) : OptionalInt.empty();
            return Optional.of(create(replaceFloor, replaceCeiling));
        }
    }

    public  static final class Ray extends Column {
        private final int edge;
        private final boolean pointingUp;

        public Ray(int edge, boolean pointingUp) {
            this.edge = edge;
            this.pointingUp = pointingUp;
        }

        @Override
        public OptionalInt getCeiling() {
            return this.pointingUp ? OptionalInt.empty() : OptionalInt.of(this.edge);
        }

        @Override
        public OptionalInt getFloor() {
            return this.pointingUp ? OptionalInt.of(this.edge) : OptionalInt.empty();
        }

        @Override
        public OptionalInt getHeight() {
            return OptionalInt.empty();
        }

        @Override
        public String toString() {
            return this.pointingUp ? "C(" + this.edge + "-)" : "C(-" + this.edge + ")";
        }
    }

    public static final class Line extends Column {
        private static final Column.Line INSTANCE = new Column.Line();

        private Line() {}

        @Override
        public OptionalInt getCeiling() {
            return OptionalInt.empty();
        }

        @Override
        public OptionalInt getFloor() {
            return OptionalInt.empty();
        }

        @Override
        public OptionalInt getHeight() {
            return OptionalInt.empty();
        }

        @Override
        public String toString() {
            return "C(-)";
        }
    }

    public static final class Range extends Column {
        private final int floor;
        private final int ceiling;

        protected Range(int floor, int ceiling) {
            this.floor = floor;
            this.ceiling = ceiling;
            if (this.height() < 0) {
                throw new IllegalArgumentException("Column of negative height: " + this);
            }
        }

        @Override
        public OptionalInt getCeiling() {
            return OptionalInt.of(this.ceiling);
        }

        @Override
        public OptionalInt getFloor() {
            return OptionalInt.of(this.floor);
        }

        @Override
        public OptionalInt getHeight() {
            return OptionalInt.of(this.height());
        }

        public int ceiling() {
            return this.ceiling;
        }

        public int floor() {
            return this.floor;
        }

        public int height() {
            return this.ceiling - this.floor - 1;
        }

        @Override
        public String toString() {
            return "C(" + this.ceiling + "-" + this.floor + ')';
        }
    }
}