package com.naterbobber.darkerdepths.core.util;

import net.minecraft.block.BlockState;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.IWorldGenerationReader;

import java.util.Optional;
import java.util.OptionalInt;
import java.util.function.Predicate;

//<>

public abstract class CaveSurface {
    public static Bounded createBounded(int floor, int ceiling) {
        return new Bounded(floor, ceiling);
    }

    public static CaveSurface createHalfWithCeiling(int ceiling) {
        return new Half(ceiling, false);
    }

    public static CaveSurface createHalfWithFloor(int floor) {
        return new Half(floor, true);
    }

    public static CaveSurface createEmpty() {
        return Empty.INSTANCE;
    }

    public static CaveSurface create(OptionalInt ceilingHeight, OptionalInt floorHeight) {
        if (ceilingHeight.isPresent() && floorHeight.isPresent()) {
            return createBounded(ceilingHeight.getAsInt(), floorHeight.getAsInt());
        } else if (ceilingHeight.isPresent()) {
            return createHalfWithFloor(ceilingHeight.getAsInt());
        } else {
            return floorHeight.isPresent() ? createHalfWithCeiling(floorHeight.getAsInt()) : createEmpty();
        }
    }

    public abstract OptionalInt getCeilingHeight();

    public abstract OptionalInt getFloorHeight();

    public abstract OptionalInt getOptionalHeight();

    public CaveSurface withFloor(OptionalInt floor) {
        return create(floor, this.getCeilingHeight());
    }

    public CaveSurface withCeiling(OptionalInt ceiling) {
        return create(this.getFloorHeight(), ceiling);
    }

    public static Optional<CaveSurface> create(IWorldGenerationReader worldIn, BlockPos pos, int height, Predicate<BlockState> canGenerate, Predicate<BlockState> canReplace) {
        BlockPos.Mutable mutable = pos.toMutable();
        if (!worldIn.hasBlockState(pos, canGenerate)) {
            return Optional.empty();
        } else {
            int y = pos.getY();
            OptionalInt floor   = getCaveSurface(worldIn, height, canGenerate, canReplace, mutable, y, Direction.UP);
            OptionalInt ceiling = getCaveSurface(worldIn, height, canGenerate, canReplace, mutable, y, Direction.DOWN);
            return Optional.of(create(ceiling, floor));
        }
    }

    private static OptionalInt getCaveSurface(IWorldGenerationReader worldIn, int height, Predicate<BlockState> canGenerate, Predicate<BlockState> canReplace, BlockPos.Mutable mutable, int y, Direction direction) {
        mutable.setY(y);

        for (int i = 1; i < height && worldIn.hasBlockState(mutable, canGenerate); i++) {
            mutable.move(direction);
        }

        return worldIn.hasBlockState(mutable, canReplace) ? OptionalInt.of(mutable.getY()) : OptionalInt.empty();
    }

    public static final class Bounded extends CaveSurface {
        private final int floor;
        private final int ceiling;

        protected Bounded(int floor, int ceiling) {
            this.floor = floor;
            this.ceiling = ceiling;
            if (this.getHeight() < 0) {
                throw new IllegalArgumentException("Column of negative height: " + this);
            }
        }

        @Override
        public OptionalInt getCeilingHeight() {
            return OptionalInt.of(this.ceiling);
        }

        @Override
        public OptionalInt getFloorHeight() {
            return OptionalInt.of(this.floor);
        }

        @Override
        public OptionalInt getOptionalHeight() {
            return OptionalInt.of(this.getHeight());
        }

        public int getCeiling() {
            return this.ceiling;
        }

        public int getFloor() {
            return this.floor;
        }

        public int getHeight() {
            return this.ceiling - this.floor - 1;
        }

        @Override
        public String toString() {
            return "C(" + this.ceiling + "-" + this.floor + ')';
        }
    }

    public static final class Half extends CaveSurface {
        private final int height;
        private final boolean floor;

        public Half(int height, boolean floor) {
            this.height = height;
            this.floor = floor;
        }

        @Override
        public OptionalInt getCeilingHeight() {
            return this.floor ? OptionalInt.empty() : OptionalInt.of(this.height);
        }

        @Override
        public OptionalInt getFloorHeight() {
            return this.floor ? OptionalInt.of(this.height) : OptionalInt.empty();
        }

        @Override
        public OptionalInt getOptionalHeight() {
            return OptionalInt.empty();
        }

        @Override
        public String toString() {
            return this.floor ? "C(" + this.height + "-)" : "C(-" + this.height + ")";
        }
    }

    public static final class Empty extends CaveSurface {
        private static final Empty INSTANCE = new Empty();

        private Empty() {}

        @Override
        public OptionalInt getCeilingHeight() {
            return OptionalInt.empty();
        }

        @Override
        public OptionalInt getFloorHeight() {
            return OptionalInt.empty();
        }

        @Override
        public OptionalInt getOptionalHeight() {
            return OptionalInt.empty();
        }

        @Override
        public String toString() {
            return "C(-)";
        }
    }
}