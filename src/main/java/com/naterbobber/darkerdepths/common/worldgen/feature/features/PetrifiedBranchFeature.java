package com.naterbobber.darkerdepths.common.worldgen.feature.features;

import com.mojang.serialization.Codec;
import com.naterbobber.darkerdepths.common.init.DDBlocks;
import com.naterbobber.darkerdepths.common.worldgen.feature.config.PetrifiedBranchConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.AmethystClusterBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;

public class PetrifiedBranchFeature extends Feature<PetrifiedBranchConfig> {

    public PetrifiedBranchFeature(Codec<PetrifiedBranchConfig> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<PetrifiedBranchConfig> context) {
        var world = context.level();
        var origin = context.origin();
        var random = context.random();
        var config = context.config();
        if (!world.isEmptyBlock(origin) || !world.getBlockState(origin.above()).is(BlockTags.BASE_STONE_OVERWORLD)) {
            return false;
        }

        int length = random.nextInt(Math.max(0, config.maxLength() - config.minLength()) + 1) + config.minLength();
        if (!config.large()) {
            length = Math.min(length, 5);
        }
        if (length == 0) {
            return false;
        }
        var shape = new LinkedHashMap<BlockPos, BlockState>();
        var facing = Direction.Plane.HORIZONTAL.getRandomDirection(random);
        if (config.large()) {
            buildLarge(shape, origin, facing, length, random);
            attachToCeiling(shape, world, origin);
        } else {
            if (!buildSmall(shape, world, origin, facing, length, random)) {
                return false;
            }
        }

        var connected = new LinkedHashMap<BlockPos, BlockState>();
        var visited = new HashSet<BlockPos>();
        var pending = new ArrayDeque<BlockPos>();
        pending.add(origin);
        while (!pending.isEmpty()) {
            var pos = pending.removeFirst();
            if (!visited.add(pos) || !shape.containsKey(pos) || !canPlace(world, pos)) {
                continue;
            }
            connected.put(pos, shape.get(pos));
            for (var direction : Direction.values()) {
                pending.add(pos.relative(direction));
            }
        }
        if (connected.isEmpty()) {
            return false;
        }

        addPorousLogs(connected, random);
        var placed = new ArrayList<BlockPos>();
        var placedPositions = new HashSet<BlockPos>();
        for (var entry : connected.entrySet()) {
            var pos = entry.getKey();
            boolean attached = pos.equals(origin);
            for (var direction : Direction.values()) {
                attached |= placedPositions.contains(pos.relative(direction));
            }
            if (!attached) {
                continue;
            }
            if (world.setBlock(pos, entry.getValue(), 2)) {
                placed.add(pos);
                placedPositions.add(pos);
            }
        }
        if (placed.isEmpty()) {
            return false;
        }

        for (var pos : placed) {
            if (random.nextInt(3) == 0 && world.isEmptyBlock(pos.below())) {
                int rootLength = 1 + random.nextInt(config.large() ? 4 : 2);
                if (!config.large()) {
                    rootLength = Math.min(rootLength, pos.getY() - origin.getY() + 4);
                    var top = pos;
                    while (placedPositions.contains(top.above())) {
                        top = top.above();
                    }
                    if (world.getBlockState(top).is(DDBlocks.PETRIFIED_WOOD.get())) {
                        for (var direction : Direction.Plane.HORIZONTAL) {
                            var parent = top.relative(direction);
                            var state = world.getBlockState(parent);
                            if (state.is(DDBlocks.PETRIFIED_LOG.get()) || state.is(DDBlocks.POROUS_PETRIFIED_LOG.get())) {
                                while (placedPositions.contains(parent.above())) {
                                    parent = parent.above();
                                }
                                rootLength = Math.min(rootLength, pos.getY() - parent.getY() + 4);
                            }
                        }
                    }
                    rootLength = Math.min(rootLength, pos.getY() - top.getY() + 4);
                }
                hangRoots(world, pos.below(), rootLength);
            }
        }
        if (random.nextInt(5) == 0) {
            for (var pos : placed) {
                if (!world.getBlockState(pos).is(DDBlocks.POROUS_PETRIFIED_LOG.get())) {
                    continue;
                }
                for (var direction : Direction.values()) {
                    var target = pos.relative(direction);
                    if (random.nextInt(4) == 0 && world.ensureCanWrite(target) && world.isEmptyBlock(target)) {
                        world.setBlock(target, DDBlocks.AMBER_CLUSTER.get().defaultBlockState()
                                .setValue(AmethystClusterBlock.FACING, direction), 2);
                    }
                }
            }
        }
        return true;
    }

    private static boolean buildSmall(Map<BlockPos, BlockState> shape, WorldGenLevel world, BlockPos origin, Direction facing, int length, RandomSource random) {
        var side = facing.getClockWise();
        var tops = new ArrayList<BlockPos>();
        var lengths = new int[4];
        for (int x = 0; x < 2; x++) {
            for (int z = 0; z < 2; z++) {
                var top = findCeiling(world, origin.relative(facing, x).relative(side, z));
                if (top == null) {
                    return false;
                }
                int index = tops.size();
                tops.add(top);
                lengths[index] = Math.min(Math.max(2, length - random.nextInt(2)), top.getY() - origin.getY() + 5);
                if (lengths[index] < 2) {
                    return false;
                }
            }
        }

        int commonY = origin.getY();
        for (var top : tops) {
            commonY = Math.min(commonY, top.getY());
        }
        for (int index = 0; index < tops.size(); index++) {
            lengths[index] = Math.max(lengths[index], tops.get(index).getY() - commonY + 1);
            if (lengths[index] > 5) {
                return false;
            }
        }

        int bottom = tops.getFirst().getY() - lengths[0];
        boolean varied = false;
        for (int index = 1; index < tops.size(); index++) {
            varied |= tops.get(index).getY() - lengths[index] != bottom;
        }
        if (!varied) {
            int index = 0;
            while (index < lengths.length && lengths[index] <= Math.max(2, tops.get(index).getY() - commonY + 1)) {
                index++;
            }
            if (index < lengths.length) {
                lengths[index]--;
            } else {
                index = 0;
                while (index < lengths.length && (lengths[index] == 5
                        || tops.get(index).getY() - lengths[index] + 1 <= origin.getY() - 4)) {
                    index++;
                }
                if (index == lengths.length) {
                    return false;
                }
                lengths[index]++;
            }
        }
        for (int index = 0; index < tops.size(); index++) {
            for (int depth = 0; depth < lengths[index]; depth++) {
                log(shape, tops.get(index).below(depth));
            }
        }
        if (random.nextBoolean()) {
            for (int attempt = 0; attempt < 8; attempt++) {
                int index = random.nextInt(tops.size());
                var direction = random.nextBoolean()
                        ? (index / 2 == 0 ? facing.getOpposite() : facing)
                        : (index % 2 == 0 ? side.getOpposite() : side);
                int depth = random.nextInt(lengths[index] - 1);
                var elbow = tops.get(index).below(depth).relative(direction);
                int maxDrop = Math.min(2, Math.min(4 - depth, elbow.getY() - origin.getY() + 4));
                if (maxDrop < 1 || shape.containsKey(elbow) || !canPlace(world, elbow) || !canPlace(world, elbow.below())) {
                    continue;
                }
                wood(shape, elbow);
                int drop = 1 + random.nextInt(maxDrop);
                for (int y = 1; y <= drop && canPlace(world, elbow.below(y)); y++) {
                    wood(shape, elbow.below(y));
                }
                break;
            }
        }
        return true;
    }

    private static void buildLarge(Map<BlockPos, BlockState> shape, BlockPos origin, Direction facing, int length, RandomSource random) {
        var side = facing.getClockWise();
        var spine = new ArrayList<BlockPos>();
        var center = origin;
        for (int depth = 0; depth < length; depth++) {
            if (depth > 0) {
                if (depth == length / 3 || depth == 2 * length / 3) {
                    var bend = depth == length / 3 ? facing : side;
                    center = center.relative(bend);
                    wood(shape, center);
                }
                center = center.below();
            }
            spine.add(center);
            log(shape, center);
            if (depth < Math.max(2, length / 3)) {
                for (int x = -1; x <= 1; x++) {
                    for (int z = -1; z <= 1; z++) {
                        if (Math.abs(x) + Math.abs(z) < 2) {
                            log(shape, center.offset(x, 0, z));
                        }
                    }
                }
            } else if (depth < 2 * length / 3) {
                log(shape, center.relative(facing));
                log(shape, center.relative(side));
                if (depth < length / 2) {
                    log(shape, center.relative(facing).relative(side));
                }
            }
        }
        for (var direction : Direction.Plane.HORIZONTAL) {
            if (random.nextBoolean()) {
                wood(shape, origin.relative(direction, 2));
            }
        }
        int forks = 2 + random.nextInt(2);
        for (int branch = 0; branch < forks; branch++) {
            var direction = branch == 0 ? facing.getOpposite() : branch == 1 ? side : facing;
            int depth = Math.min(length - 1, length / 4 + branch * Math.max(1, length / 5));
            var tip = spine.get(depth);
            int drop = 3 + random.nextInt(4);
            for (int step = 0; step < drop; step++) {
                if (step < 2 || step % 2 == 0) {
                    tip = tip.relative(direction);
                    wood(shape, tip);
                }
                tip = tip.below();
                wood(shape, tip);
                if (branch == 0 && step < 2) {
                    wood(shape, tip.relative(direction.getClockWise()));
                }
            }
        }
    }

    private static void log(Map<BlockPos, BlockState> shape, BlockPos pos) {
        shape.putIfAbsent(pos, DDBlocks.PETRIFIED_LOG.get().defaultBlockState().setValue(RotatedPillarBlock.AXIS, Direction.Axis.Y));
    }

    private static void wood(Map<BlockPos, BlockState> shape, BlockPos pos) {
        shape.putIfAbsent(pos, DDBlocks.PETRIFIED_WOOD.get().defaultBlockState());
    }

    private static BlockPos findCeiling(WorldGenLevel world, BlockPos start) {
        var pos = start;
        for (int offset = 0; offset <= 4; offset++) {
            if (canPlace(world, pos)) {
                if (world.getBlockState(pos.above()).is(BlockTags.BASE_STONE_OVERWORLD)) {
                    return pos;
                }
                pos = pos.above();
            } else if (world.getBlockState(pos).is(BlockTags.BASE_STONE_OVERWORLD)) {
                pos = pos.below();
                for (int down = 0; down < 4; down++) {
                    if (canPlace(world, pos) && world.getBlockState(pos.above()).is(BlockTags.BASE_STONE_OVERWORLD)) {
                        return pos;
                    }
                    if (!world.getBlockState(pos).is(BlockTags.BASE_STONE_OVERWORLD)) {
                        return null;
                    }
                    pos = pos.below();
                }
                return null;
            } else {
                return null;
            }
        }
        return null;
    }

    private static void attachToCeiling(Map<BlockPos, BlockState> shape, WorldGenLevel world, BlockPos origin) {
        var shoulders = new ArrayList<>(shape.keySet());
        for (var shoulder : shoulders) {
            if (shoulder.getY() != origin.getY()) {
                continue;
            }
            var top = findCeiling(world, shoulder);
            if (top == null || top.getY() <= shoulder.getY()) {
                continue;
            }
            for (int y = shoulder.getY() + 1; y <= top.getY(); y++) {
                var pos = new BlockPos(shoulder.getX(), y, shoulder.getZ());
                shape.putIfAbsent(pos, shape.get(shoulder));
            }
        }
    }

    private static boolean canPlace(WorldGenLevel world, BlockPos pos) {
        if (world.isOutsideBuildHeight(pos) || !world.ensureCanWrite(pos)) {
            return false;
        }
        var state = world.getBlockState(pos);
        return state.isAir() || state.is(DDBlocks.PETRIFIED_ROOTS.get()) || state.is(DDBlocks.PETRIFIED_ROOTS_PLANT.get());
    }

    private static void addPorousLogs(Map<BlockPos, BlockState> shape, RandomSource random) {
        if (random.nextInt(2) != 0) {
            return;
        }
        var positions = new ArrayList<BlockPos>();
        for (var entry : shape.entrySet()) {
            if (entry.getValue().is(DDBlocks.PETRIFIED_LOG.get())) {
                positions.add(entry.getKey());
            }
        }
        if (positions.isEmpty()) {
            return;
        }
        var patch = positions.get(random.nextInt(positions.size()));
        int remaining = 1 + random.nextInt(2);
        shape.put(patch, DDBlocks.POROUS_PETRIFIED_LOG.get().defaultBlockState());
        remaining--;
        for (var pos : positions) {
            if (remaining > 0 && !pos.equals(patch) && pos.distManhattan(patch) == 1 && random.nextBoolean()) {
                shape.put(pos, DDBlocks.POROUS_PETRIFIED_LOG.get().defaultBlockState()
                        .setValue(RotatedPillarBlock.AXIS, shape.get(pos).getValue(RotatedPillarBlock.AXIS)));
                remaining--;
            }
        }
    }

    private static void hangRoots(WorldGenLevel world, BlockPos start, int length) {
        int available = 0;
        while (available < length && !world.isOutsideBuildHeight(start.below(available))
                && world.ensureCanWrite(start.below(available)) && world.isEmptyBlock(start.below(available))) {
            available++;
        }
        for (int depth = 0; depth < available; depth++) {
            var block = depth == available - 1 ? DDBlocks.PETRIFIED_ROOTS.get() : DDBlocks.PETRIFIED_ROOTS_PLANT.get();
            if (!world.setBlock(start.below(depth), block.defaultBlockState(), 2)) {
                break;
            }
        }
    }
}
