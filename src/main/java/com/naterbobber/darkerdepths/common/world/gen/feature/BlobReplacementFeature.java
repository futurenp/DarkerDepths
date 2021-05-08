package com.naterbobber.darkerdepths.common.world.gen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;

import javax.annotation.Nullable;
import java.util.Random;

//<>

public class BlobReplacementFeature extends Feature<BlobReplacementConfig> {
    public BlobReplacementFeature(Codec<BlobReplacementConfig> codec) {
        super(codec);
    }

    @Override
    public boolean generate(ISeedReader worldIn, ChunkGenerator generator, Random rand, BlockPos pos, BlobReplacementConfig config) {
        BlockPos position = placeBlockOn(worldIn, pos.toMutable().clampAxisCoordinate(Direction.Axis.Y, 1, worldIn.getHeight() - 1), config);
        if (position == null) {
            return false;
        } else {
            int radius = config.getRadius().getSpread(rand);
            boolean shouldPlace = false;

            for(BlockPos distance : BlockPos.getProximitySortedBoxPositionsIterator(position, radius, radius, radius)) {
                if (distance.manhattanDistance(position) > radius) {
                    break;
                }

                BlockState blockstate = worldIn.getBlockState(distance);
                if (config.target.contains(blockstate)) {
                    this.setBlockState(worldIn, distance, config.blockState);
                    shouldPlace = true;
                }
            }

            return shouldPlace;
        }
    }

    @Nullable
    private static BlockPos placeBlockOn(IWorld worldIn, BlockPos.Mutable pos, BlobReplacementConfig config) {
        while(pos.getY() > 1) {
            BlockState blockstate = worldIn.getBlockState(pos);
            if (config.target.contains(blockstate)) {
                return pos;
            }

            pos.move(Direction.DOWN);
        }

        return null;
    }
}