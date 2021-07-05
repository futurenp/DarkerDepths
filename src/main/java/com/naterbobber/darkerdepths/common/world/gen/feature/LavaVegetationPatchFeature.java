package com.naterbobber.darkerdepths.common.world.gen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;
import java.util.function.Predicate;

//<>

public class LavaVegetationPatchFeature extends VegetationPatchFeature {
    public LavaVegetationPatchFeature(Codec<VegetationPatchConfig> config) {
        super(config);
    }

    @Override
    protected Set<BlockPos> placeGroundPatch(IWorld worldIn, VegetationPatchConfig configIn, Random rand, BlockPos pos, Predicate<BlockState> statePredicate, int xRadius, int zRadius) {
        Set<BlockPos> landGroundPatch = super.placeGroundPatch(worldIn, configIn, rand, pos, statePredicate, xRadius, zRadius);
        Set<BlockPos> waterGroundPatch = new HashSet<>();
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        Iterator<BlockPos> posIterator = landGroundPatch.iterator();

        BlockPos positions;
        while(posIterator.hasNext()) {
            positions = posIterator.next();
            if (!isSolidBlockAroundPos(worldIn, positions, mutable)) {
                waterGroundPatch.add(positions);
            }
        }

        posIterator = waterGroundPatch.iterator();

        while(posIterator.hasNext()) {
            positions = posIterator.next();
            worldIn.setBlockState(positions, Blocks.LAVA.getDefaultState(), 2);
        }

        return waterGroundPatch;
    }

    private static boolean isSolidBlockAroundPos(IWorld worldIn, BlockPos pos, BlockPos.Mutable mutable) {
        return isSolidBlockSide(worldIn, pos, mutable, Direction.NORTH) || isSolidBlockSide(worldIn, pos, mutable, Direction.EAST) || isSolidBlockSide(worldIn, pos, mutable, Direction.SOUTH) || isSolidBlockSide(worldIn, pos, mutable, Direction.WEST);
    }

    private static boolean isSolidBlockSide(IWorld worldIn, BlockPos pos, BlockPos.Mutable mutable, Direction direction) {
        mutable.setAndMove(pos, direction);
        return !worldIn.getBlockState(mutable).isSolidSide(worldIn, mutable, direction.getOpposite());
    }

    @Override
    protected boolean placeVegetation(ISeedReader worldIn, VegetationPatchConfig configIn, ChunkGenerator generator, Random rand, BlockPos pos) {
        if (super.placeVegetation(worldIn, configIn, generator, rand, pos.down())) {
            BlockState state = worldIn.getBlockState(pos);
            if (state.hasProperty(BlockStateProperties.WATERLOGGED) && !state.get(BlockStateProperties.WATERLOGGED)) {
                worldIn.setBlockState(pos, state.with(BlockStateProperties.WATERLOGGED, true), 2);
            }

            return true;
        } else {
            return false;
        }
    }
}