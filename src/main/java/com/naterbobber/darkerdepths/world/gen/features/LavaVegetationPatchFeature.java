package com.naterbobber.darkerdepths.world.gen.features;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.VegetationPatchFeature;
import net.minecraft.world.level.levelgen.feature.configurations.VegetationPatchConfiguration;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.function.Predicate;

public class LavaVegetationPatchFeature extends VegetationPatchFeature {

    public LavaVegetationPatchFeature(Codec<VegetationPatchConfiguration> codec) {
        super(codec);
    }

    @Override
    protected Set<BlockPos> placeGroundPatch(WorldGenLevel world, VegetationPatchConfiguration config, RandomSource random, BlockPos pos, Predicate<BlockState> statePredicate, int xRadius, int zRadius) {
        Set<BlockPos> landGroundPatch = super.placeGroundPatch(world, config, random, pos, statePredicate, xRadius, zRadius);
        Set<BlockPos> lavaGroundPatch = new HashSet<>();
        BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();
        Iterator<BlockPos> posIterator = landGroundPatch.iterator();

        BlockPos positions;
        while (posIterator.hasNext()) {
            positions = posIterator.next();
            if (!isExposed(world, positions, mutable)) {
                lavaGroundPatch.add(positions);
            }
        }

        posIterator = lavaGroundPatch.iterator();

        while (posIterator.hasNext()) {
            positions = posIterator.next();
            world.setBlock(positions, Blocks.LAVA.defaultBlockState(), 2);
        }

        return lavaGroundPatch;
    }

    private static boolean isExposed(WorldGenLevel world, BlockPos pos, BlockPos.MutableBlockPos mutable) {
        return isExposedDirection(world, pos, mutable, Direction.NORTH) || isExposedDirection(world, pos, mutable, Direction.EAST) || isExposedDirection(world, pos, mutable, Direction.SOUTH) || isExposedDirection(world, pos, mutable, Direction.WEST) || isExposedDirection(world, pos, mutable, Direction.DOWN);
    }

    private static boolean isExposedDirection(WorldGenLevel world, BlockPos pos, BlockPos.MutableBlockPos mutable, Direction direction) {
        mutable.setWithOffset(pos, direction);
        return !world.getBlockState(mutable).isFaceSturdy(world, mutable, direction.getOpposite());
    }

    @Override
    protected boolean placeVegetation(WorldGenLevel world, VegetationPatchConfiguration config, ChunkGenerator generator, RandomSource random, BlockPos pos) {
        return random.nextFloat() > 0.85F && super.placeVegetation(world, config, generator, random, pos.below(2));
    }
}
